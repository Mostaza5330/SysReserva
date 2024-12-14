/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.ClienteDTO;
import DTOs.MesaDTO;
import DTOs.ReservaDTO;
import Excepciones.BOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz que define las operaciones básicas para la gestión de reservas en 
 * la capa de negocio (BO). Esta interfaz proporciona un contrato para las 
 * implementaciones que manejarán la persistencia y recuperación de 
 * información sobre reservas.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IReservaBO {
    
    /**
     * Agrega una nueva reserva a la base de datos.
     * 
     * @param reserva El objeto que se desea agregar.
     * @throws Excepciones.BOException En caso de error uno nunca sabe.
     */
    public void agregarReserva(ReservaDTO reserva) throws BOException;
    
    /**
     * Consulta y recupera una lista de reservas en un rango de fechas 
     * específico.
     * 
     * @param inicio La fecha de inicio del rango de búsqueda.
     * @param fin La fecha de fin del rango de búsqueda.
     * @return Una lista de objetos que representan las 
     * reservas dentro del rango de fechas especificado.
     * @throws Excepciones.BOException En caso de error uno nunca sabe.
     */
    public List<ReservaDTO> consultarPorFecha(LocalDateTime inicio, 
            LocalDateTime fin) throws BOException;
    
    /**
     * Consulta y recupera una lista de reservas para un día específico.
     * 
     * @param dia La fecha del día para el cual se desean consultar las 
     *            reservas.
     * @return Una lista de objetos que representan las 
     *         reservas del día especificado.
     * @throws Excepciones.BOException En caso de error uno nunca sabe.
     */
    public boolean verificarPorDia(MesaDTO mesa, LocalDateTime dia) 
            throws BOException;
    
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
     * @throws Excepciones.BOException En caso de error uno nunca sabe.
     */
    public List<ReservaDTO> buscarReservasPorFiltros(String nombreCliente, 
        String telefonoCliente, LocalDate fechaReserva,
        String areaRestaurante, LocalDate fechaInicio, 
        LocalDate fechaFin, Integer tamanoMesa) throws BOException;
    
    
    /**
     * Verifica que el cliente dado en el parametro ya no tenga mas 
     * reservaciones a partir de la hora y fecha dada, en caso de tener 
     * reservaciones a partir de esa fecha y hora se regresara un nulo
     * indicando que hay reservaciones activas, por otro lado en caso de no 
     * haber encontrado ninguna retornara un false.
     * 
     * @param cliente Cliente de cual queremos buscar las resarvaciones.
     * @return True en caso de que haya reservaciones, false en caso contrario
     * @throws Excepciones.BOException
     */
    public boolean verificarReservaciones(ClienteDTO cliente)throws BOException;
    
    /**
     * 
     * @return
     * @throws BOException 
     */
    public List<ReservaDTO> obtenerReservas() throws BOException;
    
    
    /**
     * Buscar por nombre por nombre y intervalo de fechas, esta la opcion de
     * buscar unicamente por Nombre o buscar por intervalo de fechas o 
     * todos en conjunto.
     * 
     * @param nombre nombre del cliente.
     * @param inicio fecha de inicio para buscar.
     * @param fin fecha de fin para buscar.
     * @return lista de reservas filtradas
     * @throws Excepciones.BOException En caso de erro en capas inferirores
     */
    public List<ReservaDTO> buscarReservas(String nombre, LocalDateTime inicio, 
            LocalDateTime fin) throws BOException;
    
    /**
     * Actualiza la reserva dada en el parametro.
     * 
     * @param reservaDTO Reserva a actualizar.
     * @throws BOException En caso de error en conversiones o en la capa de 
     *                     datos.
     */
    public void actualizarReserva(ReservaDTO reservaDTO) throws BOException;
    
}
