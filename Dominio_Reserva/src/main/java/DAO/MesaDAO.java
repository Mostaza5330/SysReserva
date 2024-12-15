package DAO;

import Conexion.Conexion;
import Entidades.Mesa;
import Excepciones.ConexionException;
import Excepciones.DAOException;
import Interfaces.IMesaDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Clase de acceso a datos para la entidad de Mesa.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class MesaDAO implements IMesaDAO {

    //instancia de logger para hacer informes en consola
    private static final Logger LOG = Logger.
            getLogger(ClienteDAO.class.getName());

    // instancia para establecer conexion
    Conexion conexion;

    /**
     * Constructor por defecto
     */
    public MesaDAO() {
        this.conexion = new Conexion();
    }

    /**
     * Metodo para agregar una lista de mesas a la base de datos.
     *
     * @param mesas
     */
    @Override
    public void agregarMesas(List<Mesa> mesas) throws DAOException {
        EntityManager em = null;
        try {
            em = conexion.getEntityManager();
            em.getTransaction().begin();

            int agregadas = 0;
            int duplicadas = 0;

            for (Mesa mesa : mesas) {
                try {
                    // Verificar si la mesa ya existe
                    Query query = em.createQuery("SELECT COUNT(m) FROM Mesa m WHERE m.codigoMesa = :codigo");
                    query.setParameter("codigo", mesa.getCodigoMesa());
                    Long count = (Long) query.getSingleResult();

                    if (count == 0) {
                        em.persist(mesa);
                        agregadas++;
                    } else {
                        duplicadas++;
                        LOG.log(Level.WARNING, "Mesa con código {0} ya existe. Omitiendo.", mesa.getCodigoMesa());
                    }

                } catch (PersistenceException pe) {
                    LOG.log(Level.SEVERE, "Error al procesar mesa: {0}", pe.getMessage());
                }
            }

            em.getTransaction().commit();
            LOG.log(Level.INFO, "Mesas procesadas: {0} agregadas, {1} duplicadas",
                    new Object[]{agregadas, duplicadas});

        } catch (PersistenceException | ConexionException pe) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.log(Level.SEVERE, "Error al agregar las mesas: {0}", pe.getMessage());
            throw new DAOException("Error al agregar la lista de mesas");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Metodo para consultar todas las mesas dentro de la base de datos.
     *
     * @return lista de mesas.
     */
    @Override
    public List<Mesa> consultarMesas() throws DAOException {
        EntityManager em = null;
        List<Mesa> mesas = null;
        try {
            em = conexion.getEntityManager(); // Obtener el EntityManager
            mesas = em.createQuery("SELECT m FROM Mesa m",
                    Mesa.class).getResultList(); // Consultar todas las mesas
            LOG.log(Level.INFO, "{0} mesas encontradas.", mesas.size());

        } catch (PersistenceException pe) {
            LOG.log(Level.SEVERE, "Error al consultar las mesas: {0}",
                    pe.getMessage());

            throw new DAOException("error al consultar las mesas");

        } catch (ConexionException ex) {
            Logger.getLogger(MesaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
        return mesas; // Devolver la lista de mesas
    }

    /**
     * Retorna la cantidad de mesas por su ubicacion
     *
     * @param ubicacion
     * @return Entero con la cantidad de mesas en esa ubicacion.
     */
    @Override
    public int cantidadMesasPorUbicacion(String ubicacion) throws DAOException {
        EntityManager em = null;
        int cantidad = 0;
        try {
            em = conexion.getEntityManager(); // Obtener el EntityManager
            Query query = em.createQuery("SELECT COUNT(m) FROM Mesa m "
                    + "WHERE m.ubicacion = :ubicacion"); // Crear la consulta
            query.setParameter("ubicacion", ubicacion); // Pasar el parámetro
            cantidad = ((Long) query.getSingleResult()).intValue();

        } catch (PersistenceException pe) {
            LOG.log(Level.SEVERE,
                    "Error al obtener la cantidad de mesas por tipo: {0}",
                    pe.getMessage());

            throw new DAOException("error al consultar las mesas");

        } catch (ConexionException ex) {
            Logger.getLogger(MesaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
        return cantidad;
    }

    /**
     * Obtiene una lista de mesas dependiendo su tipo especificado en el
     * parámetro.
     *
     * @param tipo Tipo de mesa.
     * @return Lista de mesas filtradas por tipo.
     */
    @Override
    public List<Mesa> obtenerMesasPorTipo(String tipo) throws DAOException {
        EntityManager em = null;
        List<Mesa> mesas = new ArrayList<>();
        try {
            em = conexion.getEntityManager(); // Obtener el EntityManager
            Query query
                    = em.createQuery("SELECT m FROM Mesa m WHERE m.tipoMesa = "
                            + ":tipo"); // Crear la consulta
            query.setParameter("tipo", tipo); // Pasar el parámetro
            mesas = query.getResultList(); // Obtener el resultado
        } catch (PersistenceException pe) {
            LOG.log(Level.SEVERE,
                    "Error al obtener las mesas por tipo: {0}",
                    pe.getMessage());

            throw new DAOException("error al consultar las por tipo");

        } catch (ConexionException ex) {
            Logger.getLogger(MesaDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
        return mesas;
    }

}
