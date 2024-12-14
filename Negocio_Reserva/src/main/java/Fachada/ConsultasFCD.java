package Fachada;

import DTOs.ReservaDTO;
import interfacesFachada.IConsultasFCD;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que implementa el sistema de consultas de reservas.
 * Proporciona métodos para consultar reservas según diferentes criterios
 * como fechas, tipo de mesa y ubicación.
 * 
 * <p>
 * Esta clase permite realizar consultas sobre una lista simulada de reservas 
 * utilizando flujos (streams) para filtrar los datos de forma eficiente.
 * </p>
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ConsultasFCD implements IConsultasFCD {

    private List<ReservaDTO> reservas; // Lista simulada de reservas

    /**
     * Constructor que inicializa la lista de reservas.
     * 
     * @param reservas Lista de reservas a utilizar en las consultas.
     */
    public ConsultasFCD(List<ReservaDTO> reservas) {
        this.reservas = reservas;
    }

    /**
     * Consulta reservas en un rango de fechas especificado.
     * 
     * @param fechaInicio La fecha y hora de inicio del rango.
     * @param fechaFin La fecha y hora de fin del rango.
     * @return Lista de reservas que se encuentran en el rango de fechas especificado.
     */
    @Override
    public List<ReservaDTO> consultarReservasPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reservas.stream()
                .filter(reserva -> reserva.getFechaHoraReserva().isAfter(fechaInicio) &&
                                   reserva.getFechaHoraReserva().isBefore(fechaFin))
                .collect(Collectors.toList());
    }

    /**
     * Consulta reservas según el tipo de mesa especificado.
     * 
     * @param tipoMesa El tipo de mesa para filtrar las reservas.
     * @return Lista de reservas que coinciden con el tipo de mesa especificado.
     */
    @Override
    public List<ReservaDTO> consultarReservasPorTipoMesa(String tipoMesa) {
        return reservas.stream()
                .filter(reserva -> reserva.getMesa().getTipoMesa().equalsIgnoreCase(tipoMesa))
                .collect(Collectors.toList());
    }

    /**
     * Consulta reservas según la ubicación especificada.
     * 
     * @param ubicacion La ubicación para filtrar las reservas.
     * @return Lista de reservas que coinciden con la ubicación especificada.
     */
    @Override
    public List<ReservaDTO> consultarReservasPorUbicacion(String ubicacion) {
        return reservas.stream()
                .filter(reserva -> reserva.getMesa().getUbicacion().equalsIgnoreCase(ubicacion))
                .collect(Collectors.toList());
    }

    /**
     * Consulta reservas según un rango de fechas, tipo de mesa y ubicación.
     * 
     * @param fechaInicio La fecha y hora de inicio del rango.
     * @param fechaFin La fecha y hora de fin del rango.
     * @param tipoMesa El tipo de mesa para filtrar las reservas.
     * @param ubicacion La ubicación para filtrar las reservas.
     * @return Lista de reservas que cumplen todos los criterios de filtrado.
     */
    @Override
    public List<ReservaDTO> consultarReservas(LocalDateTime fechaInicio, LocalDateTime fechaFin, String tipoMesa, String ubicacion) {
        return reservas.stream()
                .filter(reserva -> reserva.getFechaHoraReserva().isAfter(fechaInicio) &&
                                   reserva.getFechaHoraReserva().isBefore(fechaFin) &&
                                   reserva.getMesa().getTipoMesa().equalsIgnoreCase(tipoMesa) &&
                                   reserva.getMesa().getUbicacion().equalsIgnoreCase(ubicacion))
                .collect(Collectors.toList());
    }
}
