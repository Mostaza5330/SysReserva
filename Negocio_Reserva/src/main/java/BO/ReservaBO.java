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
 * Business Object class that acts as an intermediary between the DAO layer and
 * presentation layer for Reservation management. Handles conversion between DTOs
 * and Entities while implementing business logic.
 */
public class ReservaBO implements IReservaBO {

    private static final Logger LOG = Logger.getLogger(ReservaBO.class.getName());
    
    private final IReservaDAO reservaDAO;
    private final ReservaCVR reservaCVR;
    private final ClienteCVR clienteCVR;
    private final MesaCVR mesaCVR;

    public ReservaBO() {
        this.reservaDAO = new ReservaDAO();
        this.reservaCVR = new ReservaCVR();
        this.clienteCVR = new ClienteCVR();
        this.mesaCVR = new MesaCVR();
    }

    @Override
    public void agregarReserva(ReservaDTO reserva) throws BOException {
        try {
            Reserva reservaEntity = reservaCVR.toEntity(reserva);
            reservaDAO.agregarReserva(reservaEntity);
        } catch (DAOException ex) {
            logAndThrowBOException("Error adding reservation", ex);
        }
    }

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

    @Override
    public void actualizarReserva(ReservaDTO reservaDTO) throws BOException {
        try {
            Reserva reserva = reservaCVR.toEntity(reservaDTO);
            reservaDAO.actualizarReserva(reserva);
        } catch (DAOException ex) {
            logAndThrowBOException("Error updating reservation", ex);
        }
    }

    private ReservaDTO convertToDTO(Reserva reserva) {
        try {
            return reservaCVR.toDTO(reserva);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error converting reservation to DTO", ex);
            throw new RuntimeException("Error converting reservation to DTO", ex);
        }
    }

    private void logAndThrowBOException(String message, Exception ex) throws BOException {
        LOG.log(Level.SEVERE, message, ex);
        throw new BOException(message + ": " + ex.getMessage());
    }
}