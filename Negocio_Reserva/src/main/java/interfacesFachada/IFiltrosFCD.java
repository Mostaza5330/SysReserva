package interfacesFachada;

import DTOs.ReservaDTO;

import java.util.Date;
import java.util.List;

/**
 * Interfaz para filtrar reservas. Define los métodos que deben implementarse
 * para realizar el filtrado de reservas.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IFiltrosFCD {

    /**
     * Filtra las reservas según los criterios especificados.
     *
     * @param nombreCliente Nombre del cliente a filtrar (puede ser null)
     * @param telefono Teléfono del cliente a filtrar (puede ser null)
     * @param fecha Fecha de la reserva a filtrar (puede ser null)
     * @return Lista filtrada de reservas
     */
    List<ReservaDTO> filtrarReservas(String nombreCliente, String telefono, Date fecha);

    
    /**
     * Filtra las reservas según los criterios de tipo de mesa, ubicación y
     * rango de fechas.
     *
     * @param tipoMesa Tipo de mesa a filtrar (puede ser null o vacío)
     * @param ubicacion Ubicación a filtrar (puede ser null o vacío)
     * @param fechaInicio Fecha de inicio del rango a filtrar
     * @param fechaFin Fecha de fin del rango a filtrar
     * @return Lista filtrada de reservas
     */
    List<ReservaDTO> filtrarReservasPorMesaUbicacionFecha(String tipoMesa, String ubicacion, String fechaInicio, String fechaFin);

    /**
     * Refresca la lista de reservas desde la base de datos.
     *
     * @throws Excepciones.BOException si ocurre un error al recargar las
     * reservas
     */
    void refrescarReservas() throws Excepciones.BOException;
}
