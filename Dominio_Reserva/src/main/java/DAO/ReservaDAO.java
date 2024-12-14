
package DAO;

import Conexion.Conexion;
import Entidades.Cliente;
import Entidades.Mesa;
import Entidades.Reserva;
import Entidades.Restaurante;
import Excepciones.ConexionException;
import Excepciones.DAOException;
import Interfaces.IReservaDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Clase de acceso a datos para la entidad de Reserva.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ReservaDAO implements IReservaDAO {

    //instancia de logger para hacer informes en consola
    private static final Logger LOG = Logger.
            getLogger(ReservaDAO.class.getName());

    // instancia para establecer conexion
    Conexion conexion;

    /**
     * Constructor por defecto
     */
    public ReservaDAO() {
        this.conexion = new Conexion();
    }

    /**
     * Metodo que agrega una reserva a la base de datos.
     *
     * @param reserva reserva a agregar.
     */
    @Override
    public void agregarReserva(Reserva reserva) throws DAOException {
        EntityManager em = null;
        try {
            em = conexion.getEntityManager();
            em.getTransaction().begin();

            // Buscar la mesa por su código en lugar de ID
            TypedQuery<Mesa> query = em.createQuery(
                    "SELECT m FROM Mesa m WHERE m.codigoMesa = :codigoMesa", Mesa.class);
            query.setParameter("codigoMesa", reserva.getMesa().getCodigoMesa());
            Mesa mesa = query.getSingleResult();

            if (mesa == null) {
                throw new DAOException("Mesa no encontrada con código: " + reserva.getMesa().getCodigoMesa());
            }

            reserva.setMesa(mesa); // Asignar la mesa obtenida a la reserva
            em.persist(reserva); // Persistir la reserva con la mesa asociada
            em.getTransaction().commit();

            LOG.log(Level.INFO, "Reserva agregada con éxito: {0}", reserva);
        } catch (NoResultException nre) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.log(Level.SEVERE, "No se encontró una mesa con el código proporcionado: {0}", reserva.getMesa().getCodigoMesa());
            throw new DAOException("No se encontró la mesa con el código proporcionado.", nre);
        } catch (PersistenceException pe) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.log(Level.SEVERE, "Error al agregar la reserva: {0}", pe.getMessage());
            throw new DAOException("Error al agregar la reserva", pe);
        } catch (ConexionException ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al obtener el EntityManager para agregar la reserva.", ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Metodo qye consulta las reservas realizadas en un intervalo de tiempo 
     * que estan dados por el parametro.
     * 
     * @param inicio fecha en la que se quiere empezar a buscar.
     * @param fin fecha de termino para la busqueda.
     * @return
     */
    @Override
    public List<Reserva> consultarPorFecha(LocalDateTime inicio,
            LocalDateTime fin) throws DAOException {
        EntityManager em = null;
        List<Reserva> reservas = null;
        try {
            em = conexion.getEntityManager(); // Obtener el EntityManager
            reservas = em.createQuery("SELECT r FROM Reserva r WHERE "
                    + "r.fechaHoraReserva BETWEEN :inicio AND :fin",
                    Reserva.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .getResultList(); // Ejecutar la consulta
            LOG.log(Level.INFO, "{0} reservas encontradas entre {1} y {2}",
                    new Object[]{reservas.size(), inicio, fin});
        } catch (PersistenceException pe) {

            LOG.log(Level.SEVERE, "Error al consultar las reservas: {0}",
                    pe.getMessage());

            throw new DAOException("Error al consultar las reservas");

        } catch (ConexionException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
        return reservas; // Devolver la lista de reservas    }
    }

    /**
     * Verifica si una mesa está disponible para una reserva en una fecha y hora
     * específicas. No permite reservas para la misma mesa en las siguientes 5
     * horas.
     *
     * @param mesa La mesa que se desea reservar.
     * @param dia La fecha y hora deseada para la reserva.
     * @return true si la mesa está disponible, false si ya existe una reserva
     * en el intervalo de 5 horas.
     * @throws DAOException Si ocurre un error en la conexión o en la consulta.
     */
    @Override
    public boolean verificarPorDia(Mesa mesa, LocalDateTime dia) throws DAOException {
        EntityManager em = null;
        try {
            em = conexion.getEntityManager();

            // Obtener las reservas de la mesa en el día especificado
            LocalDateTime inicioDelDia = dia.toLocalDate().atStartOfDay();
            LocalDateTime finDelDia = dia.toLocalDate().atTime(23, 59, 59); // Fin del día

            TypedQuery<Reserva> query = em.createQuery(
                    "SELECT r FROM Reserva r "
                    + "WHERE r.fechaHoraReserva BETWEEN :inicioDelDia AND :finDelDia "
                    + "AND r.estado = 'ACTIVA'", Reserva.class);

            query.setParameter("inicioDelDia", inicioDelDia);
            query.setParameter("finDelDia", finDelDia);

            // Obtener todas las reservas activas para la mesa en el día especificado
            List<Reserva> reservas = query.getResultList();

            if (reservas.isEmpty()) {
                return true; // No hay reservas activas, se puede reservar
            }

            // Hora de la nueva reserva
            LocalTime miHora = dia.toLocalTime();

            // Verificar si la hora de la nueva reserva está dentro del intervalo de alguna reserva activa
            for (Reserva reserva : reservas) {
                LocalTime minimo = reserva.getFechaHoraReserva().toLocalTime();
                LocalTime maximo = minimo.plusHours(5L);

                if (miHora.isAfter(minimo) && miHora.isBefore(maximo)) {
                    return false; // La nueva reserva está dentro del rango de 5 horas
                }
            }

            return true; // Si no encontramos reservas que interfieran, se puede reservar

        } catch (PersistenceException pe) {
            LOG.log(Level.SEVERE, "Error al verificar la disponibilidad de la mesa", pe);
            throw new DAOException("Error al verificar la disponibilidad de la mesa.", pe);
        } catch (ConexionException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al obtener el EntityManager para verificar la disponibilidad de la mesa.", ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Metodo que mediante filtros obtiene las reservas que mas se adapten
     * algunos datos del parametro pueden ser nulos pero aun asi se adapta a lo
     * requerido
     *
     * @param nombreCliente Nombre del cliente.
     * @param telefonoCliente Telefono del cliente.
     * @param fechaReserva Fecha en que fue realizada la reserva.
     * @param areaRestaurante Area del restaurante.
     * @param fechaInicio Fecha de inicio para la busqueda.
     * @param fechaFin Fecha de fin para la busqueda.
     * @param tamanoMesa Tamaño de la mesa.
     *
     * @return Retorna lista de reservaciones que mas se adapten a los
     * parametros
     */
    @Override
    public List<Reserva> buscarReservasPorFiltros(String nombreCliente,
            String telefonoCliente, LocalDate fechaReserva,
            String areaRestaurante, LocalDate fechaInicio,
            LocalDate fechaFin, Integer tamanoMesa) throws DAOException {

        EntityManager em = null;
        List<Reserva> resultados = null;

        try {
            em = conexion.getEntityManager();

            // CriteriaBuilder para crear la consulta
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Reserva> cq = cb.createQuery(Reserva.class);
            Root<Reserva> reserva = cq.from(Reserva.class);
            Join<Reserva, Cliente> cliente = reserva.join("cliente");
            Join<Reserva, Mesa> mesa = reserva.join("mesa");
            Join<Mesa, Restaurante> restaurante = mesa.join("restaurante");

            // Lista de predicados (filtros)
            List<Predicate> predicates = new ArrayList<>();

            // Filtros de cliente
            if (nombreCliente != null && !nombreCliente.isEmpty()) {
                predicates.add(cb.like(cliente.get("nombre"), "%"
                        + nombreCliente + "%"));
            }

            if (telefonoCliente != null && !telefonoCliente.isEmpty()) {
                predicates.add(cb.equal(cliente.get("telefono"),
                        telefonoCliente));
            }

            // Filtros de reserva
            if (fechaReserva != null) {
                predicates.add(cb.equal(reserva.get("fechaHoraReserva"),
                        fechaReserva.atStartOfDay()));
            }

            if (areaRestaurante != null && !areaRestaurante.isEmpty()) {
                predicates.add(cb.equal(restaurante.get("area"),
                        areaRestaurante));
            }

            if (fechaInicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(reserva.
                        get("fechaHoraReserva"), fechaInicio.atStartOfDay()));
            }

            if (fechaFin != null) {
                predicates.add(cb.lessThanOrEqualTo(reserva.
                        get("fechaHoraReserva"), fechaFin.atTime(23, 59, 59)));
            }

            if (tamanoMesa != null) {
                predicates.add(cb.equal(mesa.get("tamano"), tamanoMesa));
            }

            // Aplicar los predicados a la consulta
            cq.where(predicates.toArray(new Predicate[0]));

            // Ejecutar la consulta y obtener resultados
            resultados = em.createQuery(cq).getResultList();

        } catch (PersistenceException pe) {
            LOG.log(Level.SEVERE, "Error al buscar reservas por filtros: {0}",
                    pe.getMessage());

            throw new DAOException("Error al buscar reservas");

        } catch (ConexionException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);

        } finally {
            if (em != null) {
                em.close();
            }
        }

        return resultados; // Devolver la lista de reservas
    }

    /**
     * Verifica que el cliente dado en el parametro ya no tenga mas
     * reservaciones a partir de la hora y fecha dada, en caso de tener
     * reservaciones a partir de esa fecha y hora se regresara un nulo indicando
     * que hay reservaciones activas, por otro lado en caso de no haber
     * encontrado ninguna retornara un false.
     *
     * @param cliente Cliente de cual queremos buscar las resarvaciones.
     * @return True en caso de que haya reservaciones, false en caso contrario
     */
    @Override
    public boolean verificarReservaciones(Cliente cliente) throws DAOException {
        EntityManager em = null;
        try {
            LocalDateTime horaActual = LocalDateTime.now();
            em = conexion.getEntityManager();

            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(r) FROM Reserva r "
                    + "WHERE r.cliente = :cliente "
                    + "AND r.estado = 'ACTIVA' "
                    + "AND r.fechaHoraReserva >= :horaActual", Long.class); // Ajuste en el operador
            query.setParameter("cliente", cliente);
            query.setParameter("horaActual", horaActual);

            return query.getSingleResult() > 0;

        } catch (PersistenceException pe) {
            LOG.log(Level.SEVERE, "Error al verificar las reservaciones "
                    + "de cliente", pe);
            throw new DAOException("Error al verificar las reservaciones "
                    + "del cliente.", pe);
        } catch (ConexionException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
            throw new DAOException("Error al obtener el EntityManager para "
                    + "verificar las reservaciones del cliente.", ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Obtiene todas las reservas dentro de la base de datos.
     *
     * @return Lista de reservas registradas.
     */
    @Override
    public List<Reserva> obtenerReservas() throws DAOException {
        EntityManager em = null;
        List<Reserva> reservas = new ArrayList<>(); // Inicializar la lista para almacenar las reservas
        try {
            em = conexion.getEntityManager(); // Obtener el EntityManager
            em.getTransaction().begin(); // Iniciar la transacción

            // Crear y ejecutar la consulta
            reservas = em.createQuery("SELECT r FROM Reserva r", Reserva.class).getResultList();

            em.getTransaction().commit(); // Confirmar la transacción
        } catch (PersistenceException pe) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revertir la transacción en caso de error
            }
            LOG.log(Level.SEVERE, "Error al obtener reservas: {0}", pe.getMessage());

            throw new DAOException("Error al consultar las reservas");

        } catch (ConexionException ex) {

            LOG.log(Level.SEVERE, "Error al realizar la conexion", ex);

            throw new DAOException("Error al consultar las reservas");

        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
        return reservas; // Devolver la lista de reservas
    }

    /**
     * Buscar reservas por nombre y intervalo de fechas. Esta opción permite
     * buscar únicamente por nombre, buscar por intervalo de fechas o todos en
     * conjunto.
     *
     * @param nombre nombre del cliente.
     * @param inicio fecha de inicio para buscar.
     * @param fin fecha de fin para buscar.
     * @return lista de reservas filtradas
     * @throws DAOException En caso de error
     */
    @Override
    public List<Reserva> buscarReservas(String nombre, LocalDateTime inicio,
            LocalDateTime fin) throws DAOException {
        EntityManager em = null;
        List<Reserva> reservas = new ArrayList<>();

        try {
            em = conexion.getEntityManager();
            em.getTransaction().begin();

            // Construir la consulta base
            StringBuilder jpql = new StringBuilder("SELECT r FROM Reserva r WHERE 1=1");

            // Filtros dinámicos
            if (nombre != null && !nombre.isEmpty()) {
                jpql.append(" AND r.cliente.nombre LIKE :nombre");
            }
            if (inicio != null) {
                jpql.append(" AND r.fechaHoraReserva >= :inicio");
            }
            if (fin != null) {
                jpql.append(" AND r.fechaHoraReserva <= :fin");
            }

            // Crear la consulta con el JPQL construido
            TypedQuery<Reserva> query = em.createQuery(jpql.toString(), Reserva.class);

            // Establecer los parámetros según corresponda
            if (nombre != null && !nombre.isEmpty()) {
                query.setParameter("nombre", "%" + nombre + "%");
            }
            if (inicio != null) {
                query.setParameter("inicio", inicio);
            }
            if (fin != null) {
                query.setParameter("fin", fin);
            }

            // Ejecutar y obtener resultados
            reservas = query.getResultList();

            em.getTransaction().commit();
        } catch (PersistenceException pe) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DAOException("Error al buscar reservas: " + pe.getMessage(), pe);
        } catch (ConexionException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return reservas;
    }

    /**
     * Actualiza la reserva dada en el parámetro.
     *
     * @param reserva La reserva a actualizar.
     * @throws DAOException En caso de error en la base de datos.
     */
    @Override
    public void actualizarReserva(Reserva reserva) throws DAOException {
        EntityManager em = null;
        try {
            // Obtener el EntityManager
            em = conexion.getEntityManager();
            em.getTransaction().begin();

            // Buscar la reserva existente por su ID
            Reserva reservaExistente = em.find(Reserva.class, reserva.getId());

            if (reservaExistente == null) {
                throw new DAOException("No se encontró la reserva con ID: " + reserva.getId());
            }

            // Verificar y actualizar los atributos de la reserva
            reservaExistente.setFechaHoraReserva(reserva.getFechaHoraReserva());
            reservaExistente.setNumeroPersonas(reserva.getNumeroPersonas());
            reservaExistente.setCosto(reserva.getCosto());
            reservaExistente.setEstado(reserva.getEstado());
            reservaExistente.setMesa(reserva.getMesa()); // Asignar la mesa actualizada si es necesario

            // Persistir la reserva actualizada
            em.merge(reservaExistente);

            // Confirmar la transacción
            em.getTransaction().commit();

            LOG.log(Level.INFO, "Reserva actualizada con éxito: {0}", reservaExistente);

        } catch (PersistenceException pe) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DAOException("Error al buscar reservas: " + pe.getMessage(), pe);
        } catch (ConexionException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }    
}
