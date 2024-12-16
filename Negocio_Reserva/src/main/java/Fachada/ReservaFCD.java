package Fachada;

import BO.ReservaBO;
import BO.RestauranteBO;
import DTOs.*;
import Excepciones.BOException;
import Excepciones.FacadeException;
import Interfaces.IReservaBO;
import Interfaces.IRestauranteBO;
import interfacesFachada.IReservaFCD;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import javax.swing.JOptionPane;

/**
 * Fachada que maneja las operaciones relacionadas con reservas. Implementa el
 * patrón Facade para simplificar la interfaz del sistema de reservas. Permite
 * agregar y cancelar reservas, validando diversos parámetros antes de realizar
 * las operaciones.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ReservaFCD implements IReservaFCD {

    private final IReservaBO reservaBO;
    private final IRestauranteBO restauranteBO;

    private static final int HORAS_ANTICIPACION = 24;
    private static final int MESES_MAXIMOS = 1;
    private static final int HORAS_MARGEN_CIERRE = 1;

    /**
     * Constructor de la clase que inicializa los objetos de negocio para
     * gestionar reservas y restaurante.
     */
    public ReservaFCD() {
        this.reservaBO = new ReservaBO();
        this.restauranteBO = new RestauranteBO();
    }

    /**
     * Agrega una nueva reserva después de realizar todas las validaciones
     * necesarias.
     *
     * @param cliente El cliente que realiza la reserva.
     * @param mesa La mesa que se reserva.
     * @param horaFecha La fecha y hora en que se solicita la reserva.
     * @param numPersonas El número de personas para la reserva.
     * @param costo El costo de la reserva.
     * @throws FacadeException Si ocurre un error durante el proceso de
     * validación o agregación.
     */
    @Override
    public void agregarReserva(ClienteDTO cliente, MesaDTO mesa, LocalDateTime horaFecha,
            int numPersonas, double costo) throws FacadeException {
        System.out.println("Iniciando proceso de agregar reserva...");
        validarParametrosNoNulos(cliente, mesa, horaFecha);

        try {
            System.out.println("Validando reservaciones existentes para el cliente...");
            validarReservacionesExistentes(cliente);

            System.out.println("Validando tiempo de reservación...");
            validarTiempoReservacion(horaFecha);

            System.out.println("Validando horario del restaurante...");
            validarHorarioRestaurante(horaFecha);

            System.out.println("Validando capacidad de la mesa...");
            validarCapacidadMesa(mesa, numPersonas);

            System.out.println("Validando disponibilidad de la mesa...");
            validarDisponibilidadMesa(mesa, horaFecha);

            System.out.println("Procesando la reserva...");
            procesarReserva(cliente, mesa, horaFecha, numPersonas, costo);
        } catch (FacadeException ex) {
            System.err.println("Error de validación o proceso: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            System.err.println("Error inesperado al procesar la reserva: " + ex.getMessage());
            throw new FacadeException("Error inesperado al procesar la reserva: " + ex.getMessage());
        }
    }

    /**
     * Valida que los parámetros necesarios para la reserva no sean nulos.
     *
     * @param cliente El cliente que realiza la reserva.
     * @param mesa La mesa que se reserva.
     * @param horaFecha La fecha y hora de la reserva.
     * @throws FacadeException Si alguno de los parámetros es nulo.
     */
    private void validarParametrosNoNulos(ClienteDTO cliente, MesaDTO mesa, LocalDateTime horaFecha) throws FacadeException {
        if (cliente == null || mesa == null || horaFecha == null) {
            System.err.println("Error: Los datos de la reserva no pueden ser nulos");
            throw new FacadeException("Los datos de la reserva no pueden ser nulos");
        }
    }

    /**
     * Valida si el cliente ya tiene una reserva activa.
     *
     * @param cliente El cliente a verificar.
     * @throws FacadeException Si el cliente ya tiene una reserva activa.
     */
    private void validarReservacionesExistentes(ClienteDTO cliente) throws FacadeException {
        try {
            if (reservaBO.verificarReservaciones(cliente)) {
                System.err.println("El cliente ya tiene una reservación activa");
                throw new FacadeException("El cliente ya tiene una reservación activa");
            }
        } catch (BOException e) {
            System.err.println("Error al verificar reservaciones previas: " + e.getMessage());
            throw new FacadeException("Error al verificar reservaciones previas: " + e.getMessage());
        }
    }

    /**
     * Valida el tiempo de anticipación de la reserva. Las reservas deben ser
     * realizadas con al menos 24 horas de anticipación y no más de 1 mes.
     *
     * @param horaFecha La fecha y hora de la reserva.
     * @throws FacadeException Si el tiempo de la reserva no está dentro de los
     * límites permitidos.
     */
    private void validarTiempoReservacion(LocalDateTime horaFecha) throws FacadeException {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limiteMinimo = ahora.plusHours(HORAS_ANTICIPACION);
        LocalDateTime limiteMaximo = ahora.plusMonths(MESES_MAXIMOS);

        if (horaFecha.isBefore(limiteMinimo)) {
            String mensaje = "Las reservaciones requieren " + HORAS_ANTICIPACION + " horas de anticipación";
            System.err.println(mensaje);
            throw new FacadeException(mensaje);
        }
        if (horaFecha.isAfter(limiteMaximo)) {
            String mensaje = "No se permiten reservaciones con más de " + MESES_MAXIMOS + " mes de anticipación";
            System.err.println(mensaje);
            throw new FacadeException(mensaje);
        }
    }

    /**
     * Valida que la reserva esté dentro del horario de operación del
     * restaurante.
     *
     * @param horaFecha La fecha y hora de la reserva.
     * @throws FacadeException Si la hora de la reserva está fuera del horario
     * permitido.
     */
    private void validarHorarioRestaurante(LocalDateTime horaFecha) throws FacadeException {
        try {
            RestauranteDTO restaurante = restauranteBO.consultar();
            LocalTime horaElegida = horaFecha.toLocalTime();
            LocalTime horaCierreEfectivo = restaurante.getHoraCierre().minusHours(HORAS_MARGEN_CIERRE);

            if (horaElegida.isAfter(horaCierreEfectivo)) {
                String mensaje = "La última reservación debe ser " + HORAS_MARGEN_CIERRE
                        + " hora antes del cierre (" + restaurante.getHoraCierre() + ")";
                System.err.println(mensaje);
                throw new FacadeException(mensaje);
            }
            if (horaElegida.isBefore(restaurante.getHoraApertura())) {
                String mensaje = "No se aceptan reservaciones antes de abrir (" + restaurante.getHoraApertura() + ")";
                System.err.println(mensaje);
                throw new FacadeException(mensaje);
            }
        } catch (BOException e) {
            System.err.println("Error al verificar horario del restaurante: " + e.getMessage());
            throw new FacadeException("Error al verificar horario del restaurante: " + e.getMessage());
        }
    }

    /**
     * Valida la capacidad de la mesa para la cantidad de personas solicitada.
     *
     * @param mesa La mesa que se quiere reservar.
     * @param numPersonas El número de personas para la reserva.
     * @throws FacadeException Si el número de personas no es compatible con la
     * capacidad de la mesa.
     */
    private void validarCapacidadMesa(MesaDTO mesa, int numPersonas) throws FacadeException {
        if (numPersonas <= 0) {
            System.err.println("Error: El número de personas debe ser positivo");
            throw new FacadeException("El número de personas debe ser positivo");
        }
        if (numPersonas < mesa.getCapacidadMinima()) {
            String mensaje = String.format("El número de personas (%d) es menor que el mínimo para la mesa (%d)",
                    numPersonas, mesa.getCapacidadMinima());
            System.err.println(mensaje);
            throw new FacadeException(mensaje);
        }
        if (numPersonas > mesa.getCapacidadMaxima()) {
            String mensaje = String.format("El número de personas (%d) excede la capacidad de la mesa (%d)",
                    numPersonas, mesa.getCapacidadMaxima());
            System.err.println(mensaje);
            throw new FacadeException(mensaje);
        }
    }

    /**
     * Verifica si la mesa está disponible para la fecha y hora de la reserva.
     *
     * @param mesa La mesa que se desea reservar.
     * @param horaFecha La fecha y hora de la reserva.
     * @throws FacadeException Si la mesa no está disponible para el horario
     * solicitado.
     */
    private void validarDisponibilidadMesa(MesaDTO mesa, LocalDateTime horaFecha) throws FacadeException {
        try {
            if (!reservaBO.verificarPorDia(mesa, horaFecha)) {
                String mensaje = String.format("Mesa %s no disponible para la fecha y hora seleccionada", mesa.getCodigoMesa());
                System.err.println(mensaje);
                throw new FacadeException(mensaje);
            }
        } catch (BOException e) {
            System.err.println("Error al verificar disponibilidad: " + e.getMessage());
            throw new FacadeException("Error al verificar disponibilidad: " + e.getMessage());
        }
    }

    /**
     * Procesa la reserva una vez que todas las validaciones se han superado.
     *
     * @param cliente El cliente que realiza la reserva.
     * @param mesa La mesa a reservar.
     * @param horaFecha La fecha y hora de la reserva.
     * @param numPersonas El número de personas para la reserva.
     * @param costo El costo de la reserva.
     * @throws FacadeException Si ocurre un error al procesar la reserva.
     */
    private void procesarReserva(ClienteDTO cliente, MesaDTO mesa, LocalDateTime horaFecha,
            int numPersonas, double costo) throws FacadeException {
        try {
            if (confirmarReserva(horaFecha, numPersonas)) {
                RestauranteDTO restaurante = restauranteBO.consultar();
                ReservaDTO reserva = new ReservaDTO(horaFecha, numPersonas, costo, "ACTIVA", cliente, mesa, restaurante);

                System.out.println("Registrando la reserva en el sistema...");
                reservaBO.agregarReserva(reserva);
                mostrarMensajeExito();
            }
        } catch (BOException e) {
            System.err.println("Error al procesar la reserva: " + e.getMessage());
            throw new FacadeException("Error al procesar la reserva: " + e.getMessage());
        }
    }

    /**
     * Solicita al usuario la confirmación para realizar la reserva.
     *
     * @param horaFecha La fecha y hora de la reserva.
     * @param numPersonas El número de personas de la reserva.
     * @return true si el usuario confirma la reserva, false si la cancela.
     */
    private boolean confirmarReserva(LocalDateTime horaFecha, int numPersonas) {
        System.out.println("Solicitando confirmación del usuario para la reserva...");
        return JOptionPane.showConfirmDialog(null,
                String.format("¿Confirmar reserva para %d personas el %s?",
                        numPersonas, horaFecha.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))),
                "Confirmar Reservación",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Muestra un mensaje indicando que la reserva ha sido registrada
     * exitosamente.
     */
    private void mostrarMensajeExito() {
        System.out.println("Reservación registrada exitosamente.");
        JOptionPane.showMessageDialog(null, "Reservación registrada exitosamente");
    }

    /**
     * Cancela una reserva previamente realizada.
     *
     * @param reserva La reserva a cancelar.
     * @throws FacadeException Si ocurre un error durante la cancelación.
     */
    @Override
    public void cancelarReserva(ReservaDTO reserva) throws FacadeException {
        System.out.println("Iniciando proceso de cancelación de reserva...");
        try {
            validarReservaCancelable(reserva);

            if (confirmarCancelacion()) {
                System.out.println("Actualizando estado de la reserva a 'CANCELADA'...");
                reserva.setEstado("CANCELADA");
                reservaBO.actualizarReserva(reserva);
                mostrarMensajeCancelacionExitosa();
            }
        } catch (BOException e) {
            System.err.println("Error al cancelar la reserva: " + e.getMessage());
            throw new FacadeException("Error al cancelar la reserva: " + e.getMessage());
        }
    }

    /**
     * Valida si una reserva puede ser cancelada.
     *
     * @param reserva La reserva a verificar.
     * @throws FacadeException Si la reserva no puede ser cancelada.
     */
    private void validarReservaCancelable(ReservaDTO reserva) throws FacadeException {
        Objects.requireNonNull(reserva, "La reserva no puede ser nula");

        if ("CANCELADA".equalsIgnoreCase(reserva.getEstado())) {
            System.err.println("Error: La reserva ya está cancelada");
            throw new FacadeException("La reserva ya está cancelada");
        }
        if (reserva.getFechaHoraReserva().isBefore(LocalDateTime.now())) {
            System.err.println("Error: No se pueden cancelar reservas pasadas");
            throw new FacadeException("No se pueden cancelar reservas pasadas");
        }
    }

    /**
     * Solicita al usuario la confirmación para cancelar la reserva.
     *
     * @return true si el usuario confirma la cancelación, false si la cancela.
     */
    private boolean confirmarCancelacion() {
        System.out.println("Solicitando confirmación del usuario para la cancelación...");
        return JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea cancelar la reservación?",
                "Cancelar Reservación",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Muestra un mensaje indicando que la cancelación de la reserva fue
     * exitosa.
     */
    private void mostrarMensajeCancelacionExitosa() {
        System.out.println("Reservación cancelada exitosamente.");
        JOptionPane.showMessageDialog(null, "Reservación cancelada exitosamente");
    }
}
