package Fachada;

import BO.ReservaBO;
import DTOs.ReservaDTO;
import Excepciones.BOException;
import interfacesFachada.IFiltrosFCD;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Clase para filtrar reservas según criterios de cliente, teléfono y fecha.
 * Implementa el patrón Facade para simplificar la interfaz de filtrado de
 * reservas.
 * 
 * <p>
 * Esta clase proporciona métodos para filtrar reservas basándose en 
 * varios criterios, como el nombre del cliente, el número de teléfono y 
 * la fecha de la reserva. También permite filtrar por tipo de mesa, 
 * ubicación y rango de fechas.
 * </p>
 * 
 * <p>
 * Se utilizan dos constructores: uno que acepta una lista de reservas 
 * y otro que carga las reservas desde la base de datos.
 * </p>
 * 
 * <p>
 * Los métodos de filtrado devuelven listas de reservas que cumplen con 
 * los criterios especificados.
 * </p>
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class FiltrosFCD implements IFiltrosFCD {

    private static final Logger LOG = Logger.getLogger(FiltrosFCD.class.getName());
    private List<ReservaDTO> reservas;
    private final ReservaBO reservaBO;  // Cambiamos a usar BO en lugar de DAO directamente

    /**
     * Constructor que inicializa con una lista específica de reservas.
     * 
     * @param reservas Lista predefinida de reservas
     */
    public FiltrosFCD(List<ReservaDTO> reservas) {
        this.reservaBO = new ReservaBO();
        this.reservas = reservas != null ? reservas : Collections.emptyList();
    }

    /**
     * Constructor por defecto que carga las reservas desde la base de datos.
     */
    public FiltrosFCD() {
        this.reservaBO = new ReservaBO();
        cargarReservas();
    }

    /**
     * Carga las reservas desde la base de datos. Si ocurre un error, inicializa
     * una lista vacía y registra el error.
     */
    private void cargarReservas() {
        try {
            this.reservas = reservaBO.obtenerReservas();
        } catch (BOException e) {
            LOG.log(Level.SEVERE, "Error al cargar las reservas: {0}", e.getMessage());
            this.reservas = Collections.emptyList();
        }
    }

    /**
     * Refresca la lista de reservas desde la base de datos. Útil cuando se
     * necesita actualizar los datos.
     * 
     * @throws BOException si ocurre un error al recargar las reservas
     */
    @Override
    public void refrescarReservas() throws BOException {
        cargarReservas();
    }

    /**
     * Filtra las reservas según los criterios especificados. Si la lista de
     * reservas está vacía, intenta cargarla primero.
     * 
     * @param nombreCliente Nombre del cliente a filtrar (puede ser null)
     * @param telefono Teléfono del cliente a filtrar (puede ser null)
     * @param fecha Fecha de la reserva a filtrar (puede ser null)
     * @return Lista filtrada de reservas
     */
    @Override
    public List<ReservaDTO> filtrarReservas(String nombreCliente, String telefono, Date fecha) {
        if (reservas == null || reservas.isEmpty()) {
            cargarReservas();
        }

        return reservas.stream()
                .filter(reserva -> cumpleCriteriosFiltro(reserva, nombreCliente, telefono, fecha))
                .collect(Collectors.toList());
    }

    /**
     * Verifica si una reserva cumple con los criterios de filtrado
     * especificados.
     * 
     * @param reserva Reserva a verificar
     * @param nombreCliente Nombre del cliente a filtrar
     * @param telefono Teléfono del cliente a filtrar
     * @param fecha Fecha de la reserva a filtrar
     * @return true si la reserva cumple con los criterios, false en caso
     * contrario
     */
    private boolean cumpleCriteriosFiltro(ReservaDTO reserva, String nombreCliente,
            String telefono, Date fecha) {

        boolean cumpleNombre = nombreCliente == null
                || (reserva.getCliente() != null
                && reserva.getCliente().getNombre() != null
                && reserva.getCliente().getNombre().toLowerCase()
                        .contains(nombreCliente.toLowerCase()));

        boolean cumpleTelefono = telefono == null
                || (reserva.getCliente() != null
                && reserva.getCliente().getTelefono() != null
                && reserva.getCliente().getTelefono().equals(telefono));

        boolean cumpleFecha = fecha == null
                || (reserva.getFechaHoraReserva() != null
                && reserva.getFechaHoraReserva().toLocalDate()
                        .equals(fecha.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()));

        return cumpleNombre && cumpleTelefono && cumpleFecha;
    }

    /**
     * Obtiene la lista actual de reservas.
     * 
     * @return Lista de reservas actual
     */
    public List<ReservaDTO> getReservas() {
        return Collections.unmodifiableList(reservas);
    }

    /**
     * Filtra reservas por tipo de mesa, ubicación y rango de fechas.
     * 
     * @param tipoMesa Tipo de mesa a filtrar (puede ser null o vacío)
     * @param ubicacion Ubicación a filtrar (puede ser null o vacío)
     * @param fechaInicio Fecha de inicio del rango a filtrar (formato dd/MM/yyyy)
     * @param fechaFin Fecha de fin del rango a filtrar (formato dd/MM/yyyy)
     * @return Lista filtrada de reservas
     */
    @Override
    public List<ReservaDTO> filtrarReservasPorMesaUbicacionFecha(String tipoMesa, String ubicacion, String fechaInicio, String fechaFin) {
        if (reservas == null || reservas.isEmpty()) {
            cargarReservas();  // Cargar las reservas si no están cargadas
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime inicio = LocalDate.parse(fechaInicio, formatter).atStartOfDay();
        LocalDateTime fin = LocalDate.parse(fechaFin, formatter).atTime(23, 59);

        // Filtrar por los nuevos criterios
        return reservas.stream()
                .filter(reserva -> cumpleCriteriosMesaUbicacionFecha(reserva, tipoMesa, ubicacion, inicio, fin))
                .collect(Collectors.toList());
    }

    /**
     * Verifica si una reserva cumple con los criterios de filtrado por mesa,
     * ubicación y fechas.
     * 
     * @param reserva Reserva a verificar
     * @param tipoMesa Tipo de mesa a filtrar
     * @param ubicacion Ubicación a filtrar
     * @param inicio Fecha de inicio del rango
     * @param fin Fecha de fin del rango
     * @return true si cumple con los criterios, false en caso contrario
     */
    private boolean cumpleCriteriosMesaUbicacionFecha(ReservaDTO reserva, String tipoMesa, String ubicacion, LocalDateTime inicio, LocalDateTime fin) {
        boolean cumpleTipoMesa = tipoMesa == null || tipoMesa.isEmpty()
                || (reserva.getMesa() != null && reserva.getMesa().getTipoMesa().equalsIgnoreCase(tipoMesa));

        boolean cumpleUbicacion = ubicacion == null || ubicacion.isEmpty()
                || (reserva.getMesa() != null && reserva.getMesa().getUbicacion().equalsIgnoreCase(ubicacion));

        boolean cumpleFechas = (reserva.getFechaHoraReserva() != null
                && (reserva.getFechaHoraReserva().isEqual(inicio) || reserva.getFechaHoraReserva().isAfter(inicio))
                && (reserva.getFechaHoraReserva().isEqual(fin) || reserva.getFechaHoraReserva().isBefore(fin)));

        return cumpleTipoMesa && cumpleUbicacion && cumpleFechas;
    }
}
