package BO;

import Convertidores.ClienteCVR;
import Convertidores.MesaCVR;
import Convertidores.ReservaCVR;
import DAO.ReservaDAO;
import DTOs.*;
import Entidades.*;
import Excepciones.BOException;
import Excepciones.ConversionException;
import Excepciones.DAOException;
import Interfaces.IReservaBO;
import Interfaces.IReservaDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Clase Business Object que actúa como intermediario entre la capa DAO y la
 * capa de presentación para la gestión de reservas. Se encarga de convertir
 * entre DTOs y Entidades mientras implementa la lógica de negocio.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ReservaBO implements IReservaBO {

    private static final Logger LOG = Logger.getLogger(ReservaBO.class.getName());

    private final IReservaDAO reservaDAO;
    private final ReservaCVR reservaCVR;
    private final ClienteCVR clienteCVR;
    private final MesaCVR mesaCVR;

    /**
     * Constructor que inicializa las dependencias necesarias para la gestión de
     * reservas, incluyendo el DAO y los convertidores de entidades.
     */
    public ReservaBO() {
        this.reservaDAO = new ReservaDAO();
        this.reservaCVR = new ReservaCVR();
        this.clienteCVR = new ClienteCVR();
        this.mesaCVR = new MesaCVR();
    }

    /**
     * Agrega una nueva reserva al sistema convirtiendo el DTO en entidad y
     * delegando la persistencia al DAO.
     *
     * @param reserva El objeto {@link ReservaDTO} que contiene los datos de la
     * reserva a agregar. No debe ser {@code null}.
     * @throws BOException Si ocurre un error durante la conversión del DTO a
     * entidad o en la operación de persistencia.
     */
    @Override
    public void agregarReserva(ReservaDTO reserva) throws BOException {
        try {
            Reserva reservaEntity = reservaCVR.toEntity(reserva);
            reservaDAO.agregarReserva(reservaEntity);
        } catch (DAOException ex) {
            logAndThrowBOException("Error adding reservation", ex);
        }
    }

    /**
     * Consulta las reservas dentro de un rango de fechas específico.
     *
     * @param inicio La fecha y hora de inicio del rango. No debe ser
     * {@code null}.
     * @param fin La fecha y hora de fin del rango. No debe ser {@code null}.
     * @return Una lista de {@link ReservaDTO} que representan las reservas
     * encontradas. La lista estará vacía si no se encuentran reservas.
     * @throws BOException Si ocurre un error en la operación de consulta de la
     * base de datos.
     */
    @Override
    public List<ReservaDTO> consultarPorFecha(LocalDateTime inicio,
            LocalDateTime fin) throws BOException {
        try {
            return reservaDAO.consultarPorFecha(inicio, fin).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (DAOException ex) {
            logAndThrowBOException("Error querying reservations by date", ex);
            return null; // Never reached, just to satisfy compiler
        }
    }

    /**
     * Verifica la disponibilidad de una mesa en una fecha específica.
     *
     * @param mesa El objeto {@link MesaDTO} que representa la mesa a verificar.
     * No debe ser {@code null}.
     * @param dia La fecha y hora en la que se desea verificar la
     * disponibilidad. No debe ser {@code null}.
     * @return {@code true} si la mesa está disponible en la fecha especificada,
     * {@code false} en caso contrario.
     * @throws BOException Si ocurre un error durante la conversión del DTO a
     * entidad o en la consulta de disponibilidad.
     */
    @Override
    public boolean verificarPorDia(MesaDTO mesa, LocalDateTime dia) throws BOException {
        try {
            Mesa mesaEntidad = mesaCVR.toEntity(mesa);
            return reservaDAO.verificarPorDia(mesaEntidad, dia);
        } catch (DAOException | ConversionException ex) {
            logAndThrowBOException("Error verifying table availability", ex);
            return false; // Never reached, just to satisfy compiler
        }
    }

    /**
     * Busca reservas aplicando filtros específicos como nombre del cliente,
     * teléfono, fecha y tamaño de la mesa.
     *
     * @param nombreCliente El nombre del cliente asociado a las reservas. Puede
     * ser {@code null} para ignorar este filtro.
     * @param telefonoCliente El teléfono del cliente. Puede ser {@code null}
     * para ignorar este filtro.
     * @param fechaReserva La fecha de la reserva exacta. Puede ser {@code null}
     * para ignorar este filtro.
     * @param areaRestaurante El área del restaurante. Puede ser {@code null}
     * para ignorar este filtro.
     * @param fechaInicio La fecha inicial del rango de reservas. Puede ser
     * {@code null}.
     * @param fechaFin La fecha final del rango de reservas. Puede ser
     * {@code null}.
     * @param tamanoMesa El tamaño de la mesa en cantidad de asientos. Puede ser
     * {@code null} para ignorar este filtro.
     * @return Una lista de {@link ReservaDTO} que coinciden con los filtros
     * proporcionados.
     * @throws BOException Si ocurre un error en la consulta de la base de
     * datos.
     */
    @Override
    public List<ReservaDTO> buscarReservasPorFiltros(
            String nombreCliente,
            String telefonoCliente,
            LocalDate fechaReserva,
            String areaRestaurante,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Integer tamanoMesa) throws BOException {
        try {
            return reservaDAO.buscarReservasPorFiltros(
                    nombreCliente, telefonoCliente, fechaReserva,
                    areaRestaurante, fechaInicio, fechaFin, tamanoMesa)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (DAOException ex) {
            logAndThrowBOException("Error searching reservations by filters", ex);
            return null; // Never reached, just to satisfy compiler
        }
    }

    /**
     * Verifica si un cliente tiene reservas activas en el sistema.
     *
     * @param cliente el objeto ClienteDTO que representa al cliente a
     * verificar.
     * @return true si el cliente tiene reservas activas, false en caso
     * contrario.
     * @throws BOException si ocurre un error durante la conversión o la
     * operación en la base de datos.
     */
    @Override
    public boolean verificarReservaciones(ClienteDTO cliente) throws BOException {
        try {
            Cliente clienteEntidad = clienteCVR.toEntity(cliente);
            return reservaDAO.verificarReservaciones(clienteEntidad);
        } catch (ConversionException | DAOException ex) {
            logAndThrowBOException("Error verifying customer reservations", ex);
            return false; // Never reached, just to satisfy compiler
        }
    }

    /**
     * Recupera todas las reservas registradas en el sistema.
     *
     * @return una lista de objetos ReservaDTO que representan todas las
     * reservas.
     * @throws BOException si ocurre un error durante la operación en la base de
     * datos.
     */
    @Override
    public List<ReservaDTO> obtenerReservas() throws BOException {
        try {
            return reservaDAO.obtenerReservas().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (DAOException ex) {
            logAndThrowBOException("Error retrieving all reservations", ex);
            return null; // Never reached, just to satisfy compiler
        }
    }

    /**
     * Busca reservas que coincidan con un nombre y rango de fechas específicos.
     *
     * @param nombre el nombre asociado a las reservas.
     * @param inicio la fecha y hora de inicio del rango.
     * @param fin la fecha y hora de fin del rango.
     * @return una lista de objetos ReservaDTO que cumplen con los criterios de
     * búsqueda.
     * @throws BOException si ocurre un error durante la operación en la base de
     * datos.
     */
    @Override
    public List<ReservaDTO> buscarReservas(String nombre,
            LocalDateTime inicio,
            LocalDateTime fin) throws BOException {
        try {
            return reservaDAO.buscarReservas(nombre, inicio, fin).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (DAOException ex) {
            logAndThrowBOException("Error searching reservations", ex);
            return null; // Never reached, just to satisfy compiler
        }
    }

    /**
     * Actualiza los datos de una reserva existente.
     *
     * @param reservaDTO el objeto ReservaDTO que contiene los datos
     * actualizados de la reserva.
     * @throws BOException si ocurre un error durante la conversión o la
     * operación en la base de datos.
     */
    @Override
    public void actualizarReserva(ReservaDTO reservaDTO) throws BOException {
        try {
            Reserva reserva = reservaCVR.toEntity(reservaDTO);
            reservaDAO.actualizarReserva(reserva);
        } catch (DAOException ex) {
            logAndThrowBOException("Error updating reservation", ex);
        }
    }

    /**
     * Convierte una entidad {@link Reserva} en su correspondiente DTO
     * {@link ReservaDTO}.
     *
     * @param reserva La entidad {@link Reserva} a convertir. No debe ser
     * {@code null}.
     * @return Un objeto {@link ReservaDTO} que representa la entidad
     * convertida.
     * @throws RuntimeException Si ocurre un error inesperado durante la
     * conversión.
     */
    private ReservaDTO convertToDTO(Reserva reserva) {
        try {
            return reservaCVR.toDTO(reserva);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error converting reservation to DTO", ex);
            throw new RuntimeException("Error converting reservation to DTO", ex);
        }
    }

    /**
     * Registra un error en los logs y lanza una excepción BOException.
     *
     * @param mensaje el mensaje descriptivo del error.
     * @param ex la excepción que causó el error.
     * @throws BOException siempre lanza esta excepción con el mensaje y
     * detalles del error.
     */
    private void logAndThrowBOException(String message, Exception ex) throws BOException {
        LOG.log(Level.SEVERE, message, ex);
        throw new BOException(message + ": " + ex.getMessage());
    }
}
