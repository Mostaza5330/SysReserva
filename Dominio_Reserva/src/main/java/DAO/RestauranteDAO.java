package DAO;

import Conexion.Conexion;
import Entidades.Restaurante;
import Excepciones.ConexionException;
import Excepciones.DAOException;
import Interfaces.IRestauranteDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 * Clase de acceso a datos para la entidad de Restaurante.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class RestauranteDAO implements IRestauranteDAO {

    // Instancia de logger para hacer informes en consola
    private static final Logger LOG = Logger.getLogger(RestauranteDAO.class.getName());

    // Instancia para establecer conexión
    Conexion conexion;

    /**
     * Constructor por defecto
     */
    public RestauranteDAO() {
        this.conexion = new Conexion();
    }

    /**
     * Método para consultar el único restaurante en existencia.
     *
     * @return Restaurante.
     */
    @Override
    public Restaurante consultar() throws DAOException {
        EntityManager em = null;
        Restaurante restaurante = null;
        try {
            em = conexion.getEntityManager(); // Obtener el EntityManager
            restaurante = em.createQuery("SELECT r FROM Restaurante r", Restaurante.class)
                    .setMaxResults(1)
                    .getSingleResult(); // Consultar el único restaurante

            LOG.log(Level.INFO, "Restaurante obtenido con éxito: {0}", restaurante);
        } catch (PersistenceException pe) {
            LOG.log(Level.SEVERE, "Error al obtener el restaurante: {0}", pe.getMessage());
            throw new DAOException("Error al consultar el restaurante");
        } catch (ConexionException ex) {
            LOG.log(Level.SEVERE, "Error al obtener conexión: {0}", ex.getMessage());
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
        return restaurante;
    }

    /**
     * Método para agregar un restaurante a la base de datos.
     *
     * @param restaurante Restaurante a agregar.
     */
    @Override
    public void agregar(Restaurante restaurante) throws DAOException {
        EntityManager em = null;
        try {
            em = conexion.getEntityManager(); // Obtener el EntityManager
            em.getTransaction().begin(); // Iniciar la transacción
            em.persist(restaurante); // Persistir el restaurante
            em.getTransaction().commit(); // Confirmar la transacción
            LOG.log(Level.INFO, "Restaurante agregado con éxito: {0}", restaurante);
        } catch (PersistenceException pe) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revertir la transacción en caso de error
            }
            LOG.log(Level.SEVERE, "Error al agregar el restaurante: {0}", pe.getMessage());
            throw new DAOException("Error al agregar el restaurante");
        } catch (ConexionException ex) {
            LOG.log(Level.SEVERE, "Error al obtener conexión: {0}", ex.getMessage());
            throw new DAOException("Error al agregar el restaurante");
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
    }

    /**
     * Método para actualizar un restaurante existente.
     *
     * @param restaurante Restaurante a actualizar.
     */
    @Override
    public void actualizar(Restaurante restaurante) throws DAOException {
        EntityManager em = null;
        try {
            em = conexion.getEntityManager(); // Obtener el EntityManager
            em.getTransaction().begin(); // Iniciar la transacción
            em.merge(restaurante); // Actualizar el restaurante
            em.getTransaction().commit(); // Confirmar la transacción
            LOG.log(Level.INFO, "Restaurante actualizado con éxito: {0}", restaurante);
        } catch (PersistenceException pe) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revertir la transacción en caso de error
            }
            LOG.log(Level.SEVERE, "Error al actualizar el restaurante: {0}", pe.getMessage());
            throw new DAOException("Error al actualizar el restaurante");
        } catch (ConexionException ex) {
            LOG.log(Level.SEVERE, "Error al obtener conexión: {0}", ex.getMessage());
            throw new DAOException("Error al actualizar el restaurante");
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
    }
}
