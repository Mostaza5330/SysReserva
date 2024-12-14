/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import BO.ReservaBO;
import BO.RestauranteBO;
import DTOs.ClienteDTO;
import DTOs.MesaDTO;
import DTOs.ReservaDTO;
import DTOs.RestauranteDTO;
import Excepciones.BOException;
import Excepciones.FacadeException;
import Interfaces.IReservaBO;
import Interfaces.IRestauranteBO;
import interfacesFachada.IReservaFCD;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.JOptionPane;

/**
 * Clase `ReservaFCD` que implementa la interfaz `IReservaFCD`.
 * Sirve como fachada para gestionar las reservas, encapsulando
 * la lógica de negocio de validación y ejecución de reservas en 
 * restaurantes. Realiza operaciones de validación, creación y 
 * cancelación de reservas.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ReservaFCD implements IReservaFCD {

    IReservaBO reservaBO; // Interfaz de lógica de negocio de reserva
    IRestauranteBO restauranteBO; // Interfaz de lógica de negocio de restaurante

    /**
     * Constructor de `ReservaFCD`.
     * Inicializa las implementaciones de lógica de negocio para reservas
     * y restaurantes.
     */
    public ReservaFCD() {
        this.reservaBO = new ReservaBO();
        this.restauranteBO = new RestauranteBO();
    }

    /**
     * Agrega una reserva verificando varias condiciones necesarias para
     * garantizar la validez de la misma.
     *
     * @param cliente Cliente que hace la reserva.
     * @param mesa Mesa que se quiere reservar.
     * @param horaFecha Fecha y hora de la reserva.
     * @param numPersonas Número de personas que ocuparán la mesa.
     * @param costo Costo de la reserva.
     * @throws FacadeException Excepción lanzada en caso de error.
     */
    @Override
    public void agregarReserva(ClienteDTO cliente, MesaDTO mesa, 
            LocalDateTime horaFecha, int numPersonas, double costo) 
            throws FacadeException {
        try {
            verificarTamañoMesa(mesa, numPersonas);
            verificarFechaReservacion(horaFecha);
            verificarHoraReservacion(horaFecha);
            verificarReservacionesCliente(cliente);
            verificarDisponibilidad(mesa, horaFecha);
            hacerReserva(cliente, mesa, horaFecha, numPersonas, costo);
        } catch (FacadeException ex) {
            throw new FacadeException(ex.getMessage());
        }
    }

    /**
     * Verifica si la cantidad de personas es compatible con la capacidad
     * mínima y máxima de la mesa seleccionada.
     *
     * @param mesa Mesa a verificar.
     * @param numPersonas Número de personas en la reserva.
     * @throws FacadeException Excepción si el número de personas es inválido.
     */
    private void verificarTamañoMesa(MesaDTO mesa, int numPersonas)
            throws FacadeException {
        if (numPersonas < mesa.getCapacidadMinima()) {
            throw new FacadeException("El número de personas es menor a "
                    + "la capacidad de la mesa");
        } else if (numPersonas > mesa.getCapacidadMaxima()) {
            throw new FacadeException("El número de personas es mayor a "
                    + "la capacidad de la mesa");
        }
    }

    /**
     * Verifica que la reserva se haga con al menos 24 horas de anticipación y
     * a no más de un mes.
     *
     * @param horaFecha Fecha y hora de la reserva.
     * @throws FacadeException Excepción si la fecha es inválida.
     */
    private void verificarFechaReservacion(LocalDateTime horaFecha) 
            throws FacadeException {
        LocalDateTime masUnDia = LocalDateTime.now().plusHours(24);
        LocalDateTime masUnMes = LocalDateTime.now().plusMonths(1);

        if (horaFecha.isBefore(masUnDia)) {
            throw new FacadeException("La reservación no se puede realizar a "
                    + "menos de 24 horas, seleccione otra hora");
        } else if (horaFecha.isAfter(masUnMes)) {
            throw new FacadeException("La reservación no se puede realizar "
                    + "a fechas mayores de un mes desde hoy");
        }
    }

    /**
     * Verifica que la hora de la reserva esté dentro del horario de apertura
     * y cierre del restaurante.
     *
     * @param horaFecha Fecha y hora de la reserva.
     * @throws FacadeException Excepción si la hora es inválida.
     */
    private void verificarHoraReservacion(LocalDateTime horaFecha) 
            throws FacadeException {
        try{
        RestauranteDTO restaurante = restauranteBO.consultar();
        LocalTime horaElegida = horaFecha.toLocalTime();
        LocalTime horaCierre = restaurante.getHoraCierre();
        LocalTime horaApertura = restaurante.getHoraApertura();

        if (horaElegida.isAfter(horaCierre)) {
            throw new FacadeException("La hora de la reservación no puede ser "
                    + "después del cierre del restaurante");
        } else if (horaElegida.isBefore(horaApertura)) {
            throw new FacadeException("La hora de la reservación no puede ser "
                    + "antes de la apertura del restaurante");
        }
        }catch(BOException be){
            JOptionPane.showMessageDialog(null, be.getMessage());
        }
    }

    /**
     * Verifica si el cliente ya tiene una reservación activa en la misma fecha,
     * evitando que se dupliquen las reservaciones.
     *
     * @param cliente Cliente a verificar.
     * @throws FacadeException Excepción si el cliente ya tiene reservaciones.
     */
    private void verificarReservacionesCliente(ClienteDTO cliente) 
            throws FacadeException {
        try {
            boolean resultado = reservaBO.verificarReservaciones(cliente);
            if (resultado) {
                throw new FacadeException("El cliente ya tiene reservaciones "
                        + "activas en esta fecha");
            }
        } catch (BOException be) {
            throw new FacadeException(be.getMessage());
        }
    }

    /**
     * Verifica si la mesa está disponible en la fecha y hora deseada.
     *
     * @param mesa Mesa a verificar.
     * @param horaFecha Fecha y hora de la reserva.
     * @throws FacadeException Excepción si la mesa no está disponible.
     */
    private void verificarDisponibilidad(MesaDTO mesa, 
            LocalDateTime horaFecha) throws FacadeException {
        try {
            boolean resultado = reservaBO.verificarPorDia(mesa, horaFecha);
            if (!resultado) {
                throw new FacadeException("La mesa no está disponible "
                        + "en la hora especificada");
            }
        } catch (BOException be) {
            throw new FacadeException(be.getMessage());
        }
    }

    /**
     * Realiza la reserva si pasa todas las verificaciones. Presenta una
     * confirmación antes de completar el proceso.
     *
     * @param cliente Cliente que hace la reserva.
     * @param mesa Mesa a reservar.
     * @param horaFecha Fecha y hora de la reserva.
     * @param numPersonas Número de personas en la reserva.
     * @param costo Costo de la reserva.
     * @throws FacadeException Excepción en caso de error en la reserva.
     */
    private void hacerReserva(ClienteDTO cliente, MesaDTO mesa, 
            LocalDateTime horaFecha, int numPersonas, double costo) 
            throws FacadeException {
        try {
            int respuesta = JOptionPane.showConfirmDialog(null, 
                    "¿Está seguro de que desea agregar la reservación?", 
                    "Agregar reservación", JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                RestauranteDTO restaurante = restauranteBO.consultar();
                ReservaDTO reserva = new ReservaDTO(horaFecha, numPersonas, costo, 
                        "ACTIVA", cliente, mesa, restaurante);
                reservaBO.agregarReserva(reserva);
                JOptionPane.showMessageDialog(null, "Reservación agregada "
                        + "con éxito");
            }
        } catch (BOException be) {
            throw new FacadeException(be.getMessage());
        }
    }

    /**
     * Cancela una reserva.
     *
     * @param reserva Reserva a cancelar.
     * @throws FacadeException Excepción si la cancelación no es posible.
     */
    @Override
    public void cancelarReserva(ReservaDTO reserva) throws FacadeException {
        try {
            if (reserva.getEstado().equalsIgnoreCase("CANCELADA")) {
                throw new FacadeException("La reserva ya se encuentra "
                        + "cancelada");
            }

            LocalDateTime hoy = LocalDateTime.now();
            if (reserva.getFechaHoraReserva().isBefore(hoy)) {
                throw new FacadeException("La reserva ya ha pasado, no puede "
                        + "ser cancelada");
            }

            int respuesta = JOptionPane.showConfirmDialog(null, 
                    "¿Está seguro de que desea cancelar la reservación? " , 
                    "Cancelar reservación", JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                reserva.setEstado("CANCELADA");
                JOptionPane.showMessageDialog(null, "Reservación cancelada"
                        + " con éxito");
                reservaBO.actualizarReserva(reserva);
            }
        } catch (BOException be) {
            throw new FacadeException(be.getMessage());
        }
    }


}

