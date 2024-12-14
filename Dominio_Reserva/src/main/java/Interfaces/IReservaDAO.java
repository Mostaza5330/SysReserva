
package Interfaces;

import Entidades.Cliente;
import Entidades.Mesa;
import Entidades.Reserva;
import Excepciones.DAOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz que define las operaciones básicas para la gestión de reservas en 
 * la capa de acceso a datos (DAO). Esta interfaz proporciona un contrato para 
 * las implementaciones que manejarán la persistencia y recuperación de 
 * información sobre reservas.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IReservaDAO {
    
    /**
     * Agrega una nueva reserva a la base de datos.
     * 
     * @param reserva El objeto {@link Reserva} que se desea agregar.
     * @throws Excepciones.DAOException En caso de error en la base de datos.
     */
    public void agregarReserva(Reserva reserva) throws DAOException;
    
    /**
     * Consulta y recupera una lista de reservas en un rango de fechas 
     * específico.
     * 
     * @param inicio La fecha de inicio del rango de búsqueda.
     * @param fin La fecha de fin del rango de búsqueda.
     * @return Una lista de objetos {@link Reserva} que representan las reservas 
     * dentro del rango de fechas especificado.
     * @throws Excepciones.DAOException En caso de error en la base de datos.
     */
    public List<Reserva> consultarPorFecha(LocalDateTime inicio, 
            LocalDateTime fin)throws DAOException;
    
    /**
     * Consulta y recupera una lista de reservas para un día específico.
     * 
     * @param mesa
     * @param dia La fecha del día para el cual se desean consultar las 
     *            reservas.
     * @return Una lista de objetos {@link Reserva} que representan las 
     * reservas del día especificado.
     * @throws Excepciones.DAOException En caso de error en la base de datos.
     */
    public boolean verificarPorDia(Mesa mesa, LocalDateTime dia) 
            throws DAOException;
    
    /**
     * Metodo que mediante filtros obtiene las reservas que mas se adapten
     * algunos datos del parametro pueden ser nulos pero aun asi se adapta
     * a lo requerido.
     * 
     * @param nombreCliente Nombre del cliente.
     * @param telefonoCliente Telefono del cliente.
     * @param fechaReserva Fecha en que fue realizada la reserva.
     * @param areaRestaurante Area del restaurante.
     * @param fechaInicio Fecha de inicio para la busqueda.
     * @param fechaFin Fecha de fin para la busqueda.
     * @param tamanoMesa Tamaño de la mesa.
     * 
     * @return Retorna lista de reservaciones que mas se adapten a 
     *         los parametros.
     * @throws Excepciones.DAOException En caso de error uno no sabe.
     */
    public List<Reserva> buscarReservasPorFiltros(String nombreCliente, 
        String telefonoCliente, LocalDate fechaReserva,
        String areaRestaurante, LocalDate fechaInicio, 
        LocalDate fechaFin, Integer tamanoMesa) throws DAOException;
    
    /**
     * Verifica que el cliente dado en el parametro ya no tenga mas 
     * reservaciones a partir de la hora y fecha dada, en caso de tener 
     * reservaciones a partir de esa fecha y hora se regresara un nulo
     * indicando que hay reservaciones activas, por otro lado en caso de no 
     * haber encontrado ninguna retornara un false.
     * 
     * @param cliente Cliente de cual queremos buscar las resarvaciones.
     * @return True en caso de que haya reservaciones, false en caso contrario
     * @throws Excepciones.DAOException
     */
    public boolean verificarReservaciones(Cliente cliente)throws DAOException;
    
    /**
     * Metodo de modelo para obtener todas las reservas de la base de datos.
     * 
     * @return Lista de todas las reservas.
     * @throws DAOException En caso de error en la base de datos.
     */
    public List<Reserva> obtenerReservas() throws DAOException;
    
    /**
     * Buscar por nombre por nombre y intervalo de fechas, esta la opcion de
     * buscar unicamente por Nombre o buscar por intervalo de fechas o 
     * todos en conjunto.
     * 
     * @param nombre nombre del cliente.
     * @param inicio fecha de inicio para buscar.
     * @param fin fecha de fin para buscar.
     * @return lista de reservas filtradas
     * @throws DAOException En caso de error
     */
    public List<Reserva> buscarReservas(String nombre, LocalDateTime inicio, 
            LocalDateTime fin) throws DAOException;
    
    /**
     * Actualiza la reserva dada en el parametro con sus nuevos atributos.
     * 
     * @param reserva Reserva a actualizar.
     * @throws DAOException En caso de error en la actualizacion en la 
     *         base de datos.
     */
    public void actualizarReserva(Reserva reserva) throws DAOException;
}


