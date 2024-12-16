
package Interfaces;

import Entidades.Cliente;
import Excepciones.DAOException;
import java.util.List;

/**
 * Interfaz que define las operaciones básicas para la gestión de clientes en 
 * la capa de acceso a datos (DAO). Esta interfaz proporciona un contrato 
 * para las implementaciones que manejarán la persistencia y recuperación de 
 * información de clientes.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IClienteDAO {
    
    /**
     * Obtiene los detalles de un cliente específico a partir de su objeto.
     * 
     * @param id id del cliente.
     * @return Cliente.
     * @throws Excepciones.DAOException En caso de error en la base de datos.
     */
    public Cliente obtenerCliente(Long id) throws DAOException;
    
    /**
     * Recupera una lista de todos los clientes almacenados en la base de datos.
     * 
     * @return Una lista de objetos {@link Cliente} que representa todos los 
     *         clientes.
     * @throws Excepciones.DAOException En caso de error en la base de datos.
     */
    public List<Cliente> obtenerClientes() throws DAOException;
    
    public void insercionMasivaClientes(List<Cliente> clientes) throws DAOException;
    
}
