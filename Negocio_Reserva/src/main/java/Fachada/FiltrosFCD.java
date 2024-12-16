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
 * 
 * <p>
 * Implementa el patrón Facade para proporcionar una interfaz simplificada para
 * gestionar el filtrado de reservas. Utiliza una lista de {@code ReservaDTO} y
 * un objeto de lógica de negocio ({@code ReservaBO}) para realizar las
 * operaciones de filtrado.
 * </p>
 * 
 * <p>
 * Ofrece múltiples métodos de filtrado que permiten buscar reservas por
 * cliente, teléfono, fecha, tipo de mesa, ubicación y rango de fechas. También
 * incluye funcionalidades para recargar reservas desde la base de datos.
 * </p>
 * 
 * @author Sebastian Murrieta Verduzco - 233463

 */
public class FiltrosFCD implements IFiltrosFCD {

    private static final Logger LOG = Logger.getLogger(FiltrosFCD.class.getName());
    private List<ReservaDTO> reservas;
    private final ReservaBO reservaBO; // Capa de lógica de negocio para reservas.

    /**
     * Constructor que inicializa la clase con una lista de reservas existente.
     * 
     * @param reservas Lista predefinida de reservas, puede ser {@code null}.
     */
    public FiltrosFCD(List<ReservaDTO> reservas) {
        this.reservaBO = new ReservaBO();
        this.reservas = reservas != null ? reservas : Collections.emptyList();
    }

    /**
     * Constructor por defecto que inicializa la clase cargando las reservas desde la base de datos.
     */
    public FiltrosFCD() {
        this.reservaBO = new ReservaBO();
        cargarReservas();
    }

    /**
     * Carga las reservas desde la base de datos utilizando la capa de lógica de negocio.
     * En caso de error, registra un mensaje en el log y establece la lista de reservas como vacía.
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
     * Recarga las reservas desde la base de datos, actualizando la lista interna.
     * 
     * @throws BOException Si ocurre un error al obtener las reservas.
     */
    @Override
    public void refrescarReservas() throws BOException {
        cargarReservas();
    }

    /**
     * Filtra las reservas basándose en los criterios especificados: nombre del cliente, teléfono y fecha.
     * 
     * @param nombreCliente Nombre del cliente a buscar, puede ser {@code null}.
     * @param telefono Número de teléfono a buscar, puede ser {@code null}.
     * @param fecha Fecha de la reserva a buscar, puede ser {@code null}.
     * @return Lista de reservas que cumplen con los criterios.
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
     * Verifica si una reserva cumple con los criterios de filtrado especificados.
     * 
     * @param reserva Objeto {@code ReservaDTO} a evaluar.
     * @param nombreCliente Nombre del cliente a buscar.
     * @param telefono Número de teléfono a buscar.
     * @param fecha Fecha de la reserva a buscar.
     * @return {@code true} si la reserva cumple los criterios, de lo contrario {@code false}.
     */
    private boolean cumpleCriteriosFiltro(ReservaDTO reserva, String nombreCliente, String telefono, Date fecha) {
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
     * Obtiene la lista de reservas actuales.
     * 
     * @return Lista inmutable de reservas.
     */
    public List<ReservaDTO> getReservas() {
        return Collections.unmodifiableList(reservas);
    }

    /**
     * Filtra reservas basándose en el tipo de mesa, ubicación y rango de fechas.
     * 
     * @param tipoMesa Tipo de mesa a buscar, puede ser {@code null} o vacío.
     * @param ubicacion Ubicación a buscar, puede ser {@code null} o vacío.
     * @param fechaInicio Fecha de inicio del rango de búsqueda (formato "dd/MM/yyyy").
     * @param fechaFin Fecha de fin del rango de búsqueda (formato "dd/MM/yyyy").
     * @return Lista de reservas que cumplen con los criterios especificados.
     */
    @Override
    public List<ReservaDTO> filtrarReservasPorMesaUbicacionFecha(String tipoMesa, String ubicacion, String fechaInicio, String fechaFin) {
        if (reservas == null || reservas.isEmpty()) {
            cargarReservas();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime inicio = LocalDate.parse(fechaInicio, formatter).atStartOfDay();
        LocalDateTime fin = LocalDate.parse(fechaFin, formatter).atTime(23, 59);

        return reservas.stream()
                .filter(reserva -> cumpleCriteriosMesaUbicacionFecha(reserva, tipoMesa, ubicacion, inicio, fin))
                .collect(Collectors.toList());
    }

    /**
     * Verifica si una reserva cumple con los criterios de tipo de mesa, ubicación y rango de fechas.
     * 
     * @param reserva Objeto {@code ReservaDTO} a evaluar.
     * @param tipoMesa Tipo de mesa a buscar.
     * @param ubicacion Ubicación a buscar.
     * @param inicio Fecha y hora de inicio del rango de búsqueda.
     * @param fin Fecha y hora de fin del rango de búsqueda.
     * @return {@code true} si la reserva cumple con los criterios, de lo contrario {@code false}.
     */
    private boolean cumpleCriteriosMesaUbicacionFecha(ReservaDTO reserva, String tipoMesa, String ubicacion, LocalDateTime inicio, LocalDateTime fin) {
        boolean cumpleTipoMesa = tipoMesa == null || tipoMesa.isEmpty()
                || (reserva.getMesa() != null && reserva.getMesa().getTipoMesa().equalsIgnoreCase(tipoMesa));

        boolean cumpleUbicacion = ubicacion == null || ubicacion.isEmpty()
                || (reserva.getMesa() != null && reserva.getMesa().getUbicacion().equalsIgnoreCase(ubicacion));

        boolean cumpleFechas = reserva.getFechaHoraReserva() != null
                && (reserva.getFechaHoraReserva().isEqual(inicio) || reserva.getFechaHoraReserva().isAfter(inicio))
                && (reserva.getFechaHoraReserva().isEqual(fin) || reserva.getFechaHoraReserva().isBefore(fin));

        return cumpleTipoMesa && cumpleUbicacion && cumpleFechas;
    }
}
