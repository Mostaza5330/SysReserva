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
import javax.persistence.TypedQuery;

/**
 * Clase de acceso a datos (DAO) para gestionar operaciones CRUD relacionadas
 * con la entidad {@link Cliente}. Implementa la interfaz {@link IClienteDAO}.
 *
 * Proporciona métodos para insertar, recuperar y listar clientes, incluyendo la
 * capacidad de cifrar y descifrar los números de teléfono para mayor seguridad.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ClienteDAO implements IClienteDAO {

    private static final Logger LOG = Logger.getLogger(ClienteDAO.class.getName());
    private Conexion conexion;

    // Leer contraseña desde variable de entorno o archivo de configuración
    private static final String CLAVE_ENCRYPTION = System.getenv()
            .getOrDefault("ENCRYPTION_KEY", "sebas123");

    /**
     * Constructor por defecto que inicializa la conexión con la base de datos.
     */
    public ClienteDAO() {
        this.conexion = new Conexion();
    }

    /**
     * Inserta una lista de clientes en la base de datos de manera masiva. Cada
     * cliente es procesado individualmente, permitiendo continuar con la
     * operación incluso si ocurre un error con algún cliente específico.
     *
     * Se realiza un cifrado del número de teléfono antes de almacenarlo.
     *
     * @param clientes Lista de clientes a insertar en la base de datos.
     * @throws DAOException Si ocurre un error durante la inserción masiva.
     */
    @Override
    public void insercionMasivaClientes(List<Cliente> clientes) throws DAOException {
        EntityManager em = null;
        try {
            em = conexion.getEntityManager();
            em.getTransaction().begin();

            for (Cliente cliente : clientes) {
                try {
                    // Validaciones previas
                    if (cliente == null) {
                        LOG.warning("Intento de insertar cliente nulo");
                        continue;
                    }

                    // Cifrar teléfono solo si no está vacío
                    if (cliente.getTelefono() != null && !cliente.getTelefono().trim().isEmpty()) {
                        String telefonoCifrado = DesEncryptionUtil.cifrarTelefono(
                                cliente.getTelefono(),
                                CLAVE_ENCRYPTION
                        );
                        cliente.setTelefono(telefonoCifrado);
                    }

                    em.persist(cliente);
                } catch (Exception e) {
                    LOG.log(Level.SEVERE, "Error al procesar cliente individual", e);
                    // Continuar con el siguiente cliente en caso de error
                }
            }

            em.getTransaction().commit();
            LOG.info("Inserción masiva de clientes completada exitosamente.");
        } catch (PersistenceException | ConexionException e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.log(Level.SEVERE, "Error en la inserción masiva de clientes", e);
            throw new DAOException("Error en la inserción masiva de clientes", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recupera un cliente específico de la base de datos utilizando su ID. Si
     * el cliente tiene un número de teléfono cifrado, este se descifra antes de
     * devolverlo.
     *
     * @param id Identificador único del cliente a recuperar.
     * @return Una instancia de {@link Cliente} si se encuentra el cliente, o
     * {@code null} si no existe.
     * @throws DAOException Si ocurre un error al intentar recuperar el cliente.
     */
    @Override
    public Cliente obtenerCliente(Long id) throws DAOException {
        EntityManager em = null;
        try {
            em = conexion.getEntityManager();
            Cliente cliente = em.find(Cliente.class, id);

            // Descifrar teléfono si existe
            if (cliente != null && cliente.getTelefono() != null) {
                try {
                    String telefonoDescifrado = DesEncryptionUtil.descifrarTelefono(
                            cliente.getTelefono(),
                            CLAVE_ENCRYPTION
                    );
                    cliente.setTelefono(telefonoDescifrado);
                } catch (Exception e) {
                    LOG.log(Level.WARNING, "No se pudo descifrar el teléfono", e);
                }
            }

            return cliente;
        } catch (PersistenceException | ConexionException e) {
            LOG.log(Level.SEVERE, "Error al obtener el cliente", e);
            throw new DAOException("Error al obtener el cliente", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recupera todos los clientes almacenados en la base de datos. Los números
     * de teléfono de los clientes se descifran antes de devolver la lista.
     *
     * @return Una lista de clientes ({@link Cliente}) existentes en la base de
     * datos. Si no hay clientes, devuelve una lista vacía.
     * @throws DAOException Si ocurre un error al intentar recuperar los
     * clientes.
     */
    @Override
    public List<Cliente> obtenerClientes() throws DAOException {
        EntityManager em = null;
        try {
            em = conexion.getEntityManager();
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
            List<Cliente> clientes = query.getResultList();

            // Descifrar teléfonos
            for (Cliente cliente : clientes) {
                if (cliente.getTelefono() != null) {
                    try {
                        String telefonoDescifrado = DesEncryptionUtil.descifrarTelefono(
                                cliente.getTelefono(),
                                CLAVE_ENCRYPTION
                        );
                        cliente.setTelefono(telefonoDescifrado);
                    } catch (Exception e) {
                        LOG.log(Level.WARNING, "No se pudo descifrar un teléfono", e);
                    }
                }
            }

            return clientes;
        } catch (PersistenceException | ConexionException e) {
            LOG.log(Level.SEVERE, "Error al obtener los clientes", e);
            throw new DAOException("Error al obtener los clientes", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
