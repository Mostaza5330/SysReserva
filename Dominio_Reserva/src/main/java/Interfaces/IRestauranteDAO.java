
package Interfaces;

import Entidades.Restaurante;
import Excepciones.DAOException;

/**
 * Interfaz que define las operaciones básicas para la gestión de restaurantes 
 * en la capa de acceso a datos (DAO). Esta interfaz proporciona un contrato 
 * para las implementaciones que manejarán la persistencia y recuperación de 
 * información sobre restaurantes.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IRestauranteDAO {
    
    /**
     * Consulta y recupera la información del restaurante.
     * 
     * @return El objeto {@link Restaurante} que representa el restaurante 
     *         consultado.
     * @throws Excepciones.DAOException En caso de error en la base de datos.
     */
    public Restaurante consultar() throws DAOException;
    public void actualizar(Restaurante restaurante) throws DAOException ;
}

