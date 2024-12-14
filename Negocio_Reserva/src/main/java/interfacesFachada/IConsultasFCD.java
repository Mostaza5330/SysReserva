/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesFachada;

import DTOs.ReservaDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz que define los métodos para consultar reservas en un sistema de
 * gestión de reservas. Esta interfaz proporciona métodos para filtrar reservas
 * según diferentes criterios como fechas, tipo de mesa y ubicación.
 *
 * <p>
 * Implementaciones de esta interfaz deben proporcionar la lógica necesaria para
 * realizar las consultas sobre una lista de reservas.
 * </p>
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IConsultasFCD {

    /**
     * Consulta reservas en un rango de fechas especificado.
     *
     * @param fechaInicio La fecha y hora de inicio del rango.
     * @param fechaFin La fecha y hora de fin del rango.
     * @return Lista de reservas que se encuentran en el rango de fechas
     * especificado.
     */
    List<ReservaDTO> consultarReservasPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Consulta reservas según el tipo de mesa especificado.
     *
     * @param tipoMesa El tipo de mesa para filtrar las reservas.
     * @return Lista de reservas que coinciden con el tipo de mesa especificado.
     */
    List<ReservaDTO> consultarReservasPorTipoMesa(String tipoMesa);

    /**
     * Consulta reservas según la ubicación especificada.
     *
     * @param ubicacion La ubicación para filtrar las reservas.
     * @return Lista de reservas que coinciden con la ubicación especificada.
     */
    List<ReservaDTO> consultarReservasPorUbicacion(String ubicacion);

    /**
     * Consulta reservas según un rango de fechas, tipo de mesa y ubicación.
     *
     * @param fechaInicio La fecha y hora de inicio del rango.
     * @param fechaFin La fecha y hora de fin del rango.
     * @param tipoMesa El tipo de mesa para filtrar las reservas.
     * @param ubicacion La ubicación para filtrar las reservas.
     * @return Lista de reservas que cumplen todos los criterios de filtrado.
     */
    List<ReservaDTO> consultarReservas(LocalDateTime fechaInicio, LocalDateTime fechaFin, String tipoMesa, String ubicacion);
}
