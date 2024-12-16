package BO;

import DAO.DesEncryptionUtil;
import DTOs.ClienteDTO;
import Excepciones.NegocioException;
import Interfaces.IClienteBO;
import DAO.ClienteDAO;
import Convertidores.ClienteCVR;
import Entidades.Cliente;
import Excepciones.BOException;
import Excepciones.DAOException;
import Excepciones.ConversionException;
import Interfaces.IClienteDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase intermediaria entre la capa de DAO para la entidad Cliente que
 * convierte los métodos de la clase DAO a DTO o a Entidad dependiendo del
 * flujo. Sirve como puente para la lógica de negocio en la gestión de clientes,
 * coordinando interacciones entre la capa de acceso a datos (DAO) y la de
 * presentación.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ClienteBO implements IClienteBO {

    // Logger para la depuración y seguimiento de eventos durante la ejecución
    private static final Logger LOG = Logger.getLogger(ClienteBO.class.getName());

    // Interfaz del DAO para operaciones con clientes en la capa de datos
    private final IClienteDAO clienteDAO;
    // Convertidor para transformar entre Cliente y ClienteDTO
    private final ClienteCVR clienteCVR;

    /**
     * Constructor por defecto de la clase. Inicializa las instancias de
     * clienteDAO y clienteCVR.
     */
    public ClienteBO() {
        this.clienteDAO = new ClienteDAO();  // Se instancia un DAO de cliente
        this.clienteCVR = new ClienteCVR();  // Se instancia un convertidor de cliente
    }

    public void insercionMasivaClientes(List<ClienteDTO> clientes) throws NegocioException {
        try {
            // Convert ClienteDTO to Cliente entities with phone encryption
            List<Cliente> clientesEntidad = new ArrayList<>();
            for (ClienteDTO clienteDTO : clientes) {
                // Encrypt phone number before converting to entity
                String telefonoCifrado = DesEncryptionUtil.cifrarTelefono(
                        clienteDTO.getTelefono(),
                        "sebas123" // Use the same key as in ClienteDAO
                );

                // Update DTO with encrypted phone number
                clienteDTO.setTelefono(telefonoCifrado);

                // Convert to entity
                Cliente cliente = clienteCVR.toEntity(clienteDTO);
                clientesEntidad.add(cliente);
            }

            // Perform mass insertion using DAO
            clienteDAO.insercionMasivaClientes(clientesEntidad);

            LOG.log(Level.INFO, "Inserción masiva de clientes exitosa");
        } catch (Exception ex) {
            if (ex instanceof ConversionException) {
                LOG.log(Level.SEVERE, "Error en la conversión de DTO a Entidad", ex);
                throw new NegocioException("Error al convertir clientes para inserción masiva");
            } else if (ex instanceof DAOException) {
                LOG.log(Level.SEVERE, "Error en la inserción masiva de clientes", ex);
                throw new NegocioException("Error al insertar clientes masivamente");
            } else {
                LOG.log(Level.SEVERE, "Error al cifrar teléfono", ex);
                throw new NegocioException("Error al cifrar número de teléfono");
            }
        }
    }

    /**
     * Método para obtener un cliente por su ID. Interactúa con la capa DAO para
     * recuperar los datos de un cliente y luego convierte esos datos a un DTO
     * (Data Transfer Object) para su uso en las capas superiores (como la capa
     * de presentación o controladores).
     *
     * @param id Identificador único del cliente que se busca.
     * @return Retorna un ClienteDTO que representa al cliente.
     * @throws BOException Si ocurre algún error en la capa DAO o durante la
     * conversión.
     */
    @Override
    public ClienteDTO obtenerCliente(Long id) throws BOException {
        try {
            // Obtener el cliente desde el DAO
            Cliente cliente = clienteDAO.obtenerCliente(id);

            // Descifrar el teléfono si está cifrado
            if (cliente.getTelefono() != null) {
                String telefonoDescifrado = DesEncryptionUtil.descifrarTelefono(
                        cliente.getTelefono(),
                        "sebas123"
                );
                cliente.setTelefono(telefonoDescifrado);
            }

            // Convertir el cliente en una instancia DTO
            ClienteDTO clienteDTO = clienteCVR.toDTO(cliente);

            LOG.log(Level.INFO, "Éxito al obtener al cliente por ID en BO");

            return clienteDTO;
        } catch (Exception e) {
            // Handle potential decryption or conversion errors
            LOG.log(Level.SEVERE, "Error al obtener o descifrar el cliente", e);
            throw new BOException("Error al obtener el cliente por ID");
        }
    }

    /**
     * Método para obtener una lista de todos los clientes. Recupera los
     * clientes desde la capa DAO, los convierte a DTO y los retorna en una
     * lista.
     *
     * @return Lista de objetos ClienteDTO que representan los clientes.
     * @throws BOException Si ocurre un error al recuperar o convertir los
     * datos.
     */
    @Override
    public List<ClienteDTO> obtenerClientes() throws BOException {
        try {
            List<Cliente> entidades = clienteDAO.obtenerClientes();
            List<ClienteDTO> dto = new ArrayList<>();

            for (Cliente cliente : entidades) {
                try {
                    // Verificación adicional antes de descifrar
                    if (cliente.getTelefono() != null && !cliente.getTelefono().isEmpty()) {
                        String telefonoDescifrado = DesEncryptionUtil.descifrarTelefono(
                                cliente.getTelefono(),
                                "sebas123"
                        );
                        cliente.setTelefono(telefonoDescifrado);
                    }
                    dto.add(clienteCVR.toDTO(cliente));
                } catch (Exception ex) {
                    // Log detallado
                    LOG.log(Level.SEVERE,
                            "Error al procesar cliente: "
                            + (cliente != null ? cliente.getNombre() : "Cliente nulo"),
                            ex
                    );
                    // Opcional: mantener el cliente original sin descifrar
                    // dto.add(clienteCVR.toDTO(cliente));
                }
            }

            return dto;
        } catch (DAOException de) {
            LOG.log(Level.SEVERE, "Error al obtener los clientes en BO", de);
            throw new BOException("Error al obtener los clientes", de);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error inesperado al obtener los clientes", ex);
            throw new BOException("Error inesperado al obtener los clientes", ex);
        }
    }
}
