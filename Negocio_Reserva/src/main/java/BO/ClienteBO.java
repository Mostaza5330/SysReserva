package BO;

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
            // Convert ClienteDTO to Cliente entities
            List<Cliente> clientesEntidad = new ArrayList<>();
            for (ClienteDTO clienteDTO : clientes) {
                Cliente cliente = clienteCVR.toEntity(clienteDTO);
                clientesEntidad.add(cliente);
            }

            // Perform mass insertion using DAO
            clienteDAO.insercionMasivaClientes(clientesEntidad);

            LOG.log(Level.INFO, "Inserción masiva de clientes exitosa");
        } catch (ConversionException ex) {
            LOG.log(Level.SEVERE, "Error en la conversión de DTO a Entidad", ex);
            throw new NegocioException("Error al convertir clientes para inserción masiva");
        } catch (DAOException de) {
            LOG.log(Level.SEVERE, "Error en la inserción masiva de clientes", de);
            throw new NegocioException("Error al insertar clientes masivamente");
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
            // Convertir el cliente en una instancia DTO
            ClienteDTO clienteDTO = clienteCVR.toDTO(cliente);

            // Registrar un mensaje de éxito en el log
            LOG.log(Level.INFO, "Éxito al obtener al cliente por ID en BO");

            return clienteDTO;
        } catch (DAOException de) {

            // Registrar un error en el log si hay un problema en la capa DAO
            LOG.log(Level.SEVERE, "Error al obtener el cliente por ID en BO", de);
            throw new BOException(de.getMessage());  // Re-lanzar como BOException

        } catch (ConversionException ce) {

            // Registrar un error si falla la conversión de entidad a DTO
            LOG.log(Level.SEVERE, "Error en la conversión al obtener el cliente por ID", ce);
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
            // Obtener la lista de entidades Cliente desde el DAO
            List<Cliente> entidades = clienteDAO.obtenerClientes();

            List<ClienteDTO> dto = new ArrayList<>();

            // Convertir cada entidad Cliente a DTO
            for (Cliente cliente : entidades) {
                dto.add(clienteCVR.toDTO(cliente));
            }

            return dto; // Retornar la lista de clientes en formato DTO

        } catch (DAOException de) {

            // Registrar un error si ocurre un problema en la capa DAO
            LOG.log(Level.SEVERE, "Error al obtener los clientes en BO", de);
            throw new BOException(de.getMessage());

        } catch (ConversionException ce) {

            // Registrar un error si falla la conversión de entidad a DTO
            LOG.log(Level.SEVERE, "Error en la conversión al obtener los clientes", ce);
            throw new BOException("Error al obtener los clientes");

        }

    }
}
