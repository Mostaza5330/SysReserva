
package DAO;

import Conexion.Conexion;
import Entidades.Cliente;
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
public class ClienteDAO implements IClienteDAO{
    
    //instancia de logger para hacer informes en consola
    private static final Logger LOG = Logger.
            getLogger(ClienteDAO.class.getName());
    
    //instancia de la conexion
    Conexion conexion;

    public ClienteDAO() {
        this.conexion = new Conexion();
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
            em = conexion.getEntityManager(); // Obtener el EntityManager
            cliente = em.find(Cliente.class, id); // Buscar el cliente por su id
            if (cliente != null) {
                LOG.info("Cliente obtenido exitosamente.");
            } else {
                LOG.warning("Cliente no encontrado.");
            }
        } catch (PersistenceException pe) {
            LOG.log(Level.SEVERE, "Error al obtener el cliente: {0}", 
                    pe.getMessage());
            
            throw new DAOException("Error al obtener el cliente");
            
        } catch (ConexionException ex) {
            
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, 
                    null, ex);
            
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
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
            em = conexion.getEntityManager(); // Obtener el EntityManager
            clientes = em.createQuery("SELECT c FROM Cliente c", 
                    Cliente.class).getResultList(); 
            LOG.info("Clientes obtenidos exitosamente.");
        } catch (PersistenceException pe) {
            
            LOG.log(Level.SEVERE, "Error al obtener los clientes: {0}", 
                    pe.getMessage());
            
            throw new DAOException("Error al obtener a los clientes");
            
        } catch (ConexionException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
        return clientes;
    }
    
}
