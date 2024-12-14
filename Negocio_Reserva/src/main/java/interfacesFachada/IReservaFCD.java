/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesFachada;

import DTOs.ClienteDTO;
import DTOs.MesaDTO;
import DTOs.ReservaDTO;
import Excepciones.FacadeException;
import java.time.LocalDateTime;

/**
 * Interfaz `IReservaFCD` que define los métodos necesarios para gestionar
 * operaciones relacionadas con reservas en la capa de fachada.
 * Permite la creación y cancelación de reservas en la aplicación.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IReservaFCD {

    /**
     * Agrega una nueva reserva para un cliente en una mesa específica
     * en una fecha y hora determinadas.
     *
     * @param cliente    El cliente que realiza la reserva.
     * @param mesa       La mesa que se reserva.
     * @param horaFecha  La fecha y hora de la reserva.
     * @param numPersonas El número de personas para la reserva.
     * @param costo      El costo asociado a la reserva.
     * @throws FacadeException Excepción lanzada en caso de error al agregar la reserva.
     */
    public void agregarReserva(ClienteDTO cliente, MesaDTO mesa, 
            LocalDateTime horaFecha, int numPersonas, double costo) 
            throws FacadeException;

    /**
     * Cancela una reserva existente.
     *
     * @param reserva La reserva que se desea cancelar.
     * @throws FacadeException Excepción lanzada en caso de error al cancelar la reserva.
     */
    public void cancelarReserva(ReservaDTO reserva) throws FacadeException;
}

