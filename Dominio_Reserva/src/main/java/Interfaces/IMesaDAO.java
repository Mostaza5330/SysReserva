
package Interfaces;

import Entidades.Mesa;
import Excepciones.DAOException;
import java.util.List;

/**
 * Interfaz que define las operaciones básicas para la gestión de mesas en la 
 * capa de acceso a datos (DAO). Esta interfaz proporciona un contrato para 
 * las implementaciones que manejarán la persistencia y recuperación de 
 * información sobre mesas.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IMesaDAO {
    
    
    /**
     * Agrega una lista de mesas a la base de datos.
     * 
     * @param mesas La lista de objetos {@link Mesa} que se desea agregar.
     * @throws Excepciones.DAOException
     */
    public void agregarMesas(List<Mesa> mesas) throws DAOException;
    
    /**
     * Consulta y recupera una lista de todas las mesas almacenadas en la base 
     * de datos.
     * 
     * @return Una lista de objetos {@link Mesa} que representa todas las mesas.
     * @throws Excepciones.DAOException En caso de error en la base de datos.
     */
    public List<Mesa> consultarMesas() throws DAOException;
    
    /**
     * Devuelve la cantidad de mesas dependiendo de su ubicacion;
     * 
     * @param ubicacion
     * @return El número de mesas clasificadas como pequeñas.
     * @throws Excepciones.DAOException En caso de error en la base de datos.
     */
    public int cantidadMesasPorUbicacion(String ubicacion) throws DAOException;
    
    /**
     * Obtiene una lista de mesas dependiendo su tipo especidicado en el 
     * parametro.
     * 
     * @param tipo Tipo de mesa.
     * @return Lista de mesas filtradas por tipo.
     * @throws Excepciones.DAOException En caso de error en la base de datos.
     */
    public List<Mesa> obtenerMesasPorTipo(String tipo) throws DAOException;

}

