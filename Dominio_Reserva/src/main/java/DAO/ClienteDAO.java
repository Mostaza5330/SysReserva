package DAO;

import Cifrado.DesEncryptionUtil;
import Conexion.Conexion;
import Entidades.Cliente;
import static Entidades.Reserva_.cliente;
import Excepciones.ConexionException;
import Excepciones.DAOException;
import Interfaces.IClienteDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 * Clase de acceso a datos para la entidad de Cliente.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ClienteDAO implements IClienteDAO {

    private static final Logger LOG = Logger.getLogger(ClienteDAO.class.getName());
    Conexion conexion;

    public ClienteDAO() {
        this.conexion = new Conexion();
    }

    private static final String CLAVE_DES = "sebas123";  // Clave para DES (8 caracteres)

    /**
     * Inserta masivamente una lista de clientes en la base de datos.
     *
     * @param clientes Lista de clientes a insertar.
     * @throws DAOException En caso de error durante la inserción.
     */
    @Override
    public void insercionMasivaClientes(List<Cliente> clientes) throws DAOException {
        EntityManager em = null;
        try {
            em = conexion.getEntityManager();
            em.getTransaction().begin();

            for (Cliente cliente : clientes) {
                // Cifrar el teléfono antes de guardar en la base de datos
                String telefonoCifrado = DesEncryptionUtil.cifrarTelefono(cliente.getTelefono(), CLAVE_DES);
                cliente.setTelefono(telefonoCifrado); // Almacenar el teléfono cifrado en el objeto cliente
                em.persist(cliente); // Inserta cada cliente
            }

            em.getTransaction().commit();
            LOG.info("Inserción masiva de clientes completada exitosamente.");
        } catch (PersistenceException pe) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.log(Level.SEVERE, "Error en la inserción masiva de clientes - PersistenceException", pe);
            throw new DAOException("Error en la inserción masiva de clientes", pe);
        } catch (ConexionException ce) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.log(Level.SEVERE, "Error en la inserción masiva de clientes - ConexionException", ce);
            throw new DAOException("Error en la conexión", ce);
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.log(Level.SEVERE, "Error en la inserción masiva de clientes - Exception", e);
            throw new DAOException("Error inesperado en la inserción de clientes", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Obtiene un cliente en especifico de la base de datos.
     *
     * @param id Id del cliente a obtener.
     * @return Cliente obtenido o null si no se encuentra.
     */
    @Override
    public Cliente obtenerCliente(Long id) throws DAOException {
        EntityManager em = null;
        Cliente cliente = null;
        try {
            em = conexion.getEntityManager();
            cliente = em.find(Cliente.class, id);
            if (cliente != null) {
                LOG.info("Cliente obtenido exitosamente.");
            } else {
                LOG.warning("Cliente no encontrado.");
            }
        } catch (PersistenceException pe) {
            LOG.log(Level.SEVERE, "Error al obtener el cliente - PersistenceException", pe);
            throw new DAOException("Error al obtener el cliente", pe);
        } catch (ConexionException ce) {
            LOG.log(Level.SEVERE, "Error al obtener el cliente - ConexionException", ce);
            throw new DAOException("Error en la conexión", ce);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al obtener el cliente - Exception", e);
            throw new DAOException("Error inesperado al obtener el cliente", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return cliente;
    }

    /**
     * Obtiene una lista de todos los clientes en la base de datos.
     *
     * @return Lista de todos los clientes.
     */
    @Override
    public List<Cliente> obtenerClientes() throws DAOException {
        EntityManager em = null;
        List<Cliente> clientes = null;
        try {
            em = conexion.getEntityManager();
            clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
            LOG.info("Clientes obtenidos exitosamente.");
        } catch (PersistenceException pe) {
            LOG.log(Level.SEVERE, "Error al obtener los clientes - PersistenceException", pe);
            throw new DAOException("Error al obtener a los clientes", pe);
        } catch (ConexionException ce) {
            LOG.log(Level.SEVERE, "Error al obtener los clientes - ConexionException", ce);
            throw new DAOException("Error en la conexión", ce);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al obtener los clientes - Exception", e);
            throw new DAOException("Error inesperado al obtener los clientes", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return clientes;
    }
}
