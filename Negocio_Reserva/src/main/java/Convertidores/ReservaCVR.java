/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Convertidores;

import DTOs.ReservaDTO;
import Entidades.Reserva;
import Excepciones.ConversionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que contiene los metodos de conversion para La entidad de Reserva.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ReservaCVR {

    //metodo logger para la registrar cambios.
    private static final Logger LOG = Logger.
            getLogger(ReservaCVR.class.getName());
    
    private final ClienteCVR clienteCVR; // convertidor de cliente
    private final MesaCVR mesaCVR; // convertidor de mesa
    private final RestauranteCVR restauranteCVR; // convertidor de restaurante

    /**
     * Constructor que inicializa los atributos de la clase.
     */
    public ReservaCVR() {
        this.clienteCVR = new ClienteCVR();
        this.mesaCVR = new MesaCVR();
        this.restauranteCVR = new RestauranteCVR();
    }
    
    /**
     * Convierte todos los atributos de ReservaDTO a Reserva, 
     * los elementos que pueden ser nulos se pasan directamente como nulos 
     * en caso de serlo
     * 
     * @param reservaDTO ReservaDTO a convertir.
     * @return Reserva de tipo entidad.
     */
    public Reserva toEntity(ReservaDTO reservaDTO) {
        try{
        
        if (reservaDTO == null) {
            return null; // Devuelve null si el DTO es nulo
        }

        Reserva reserva = new Reserva();
        reserva.setId(reservaDTO.getId() != null && !reservaDTO.getId().
                isEmpty() ? Long.valueOf(reservaDTO.getId()) : null);
        reserva.setFechaHoraReserva(reservaDTO.getFechaHoraReserva());
        reserva.setNumeroPersonas(reservaDTO.getNumeroPersonas());
        reserva.setCosto(reservaDTO.getCosto());
        reserva.setEstado(reservaDTO.getEstado());
        
        // Conversión de cliente
        if (reservaDTO.getCliente() != null) {
            reserva.setCliente(clienteCVR.toEntity(reservaDTO.getCliente()));
        }

        // Conversión de mesa
        if (reservaDTO.getMesa() != null) {
            reserva.setMesa(mesaCVR.toEntity(reservaDTO.getMesa()));
        }

        // Conversión de restaurante
        if (reservaDTO.getRestaurante() != null) {
            reserva.setRestaurante(restauranteCVR.
                    toEntity(reservaDTO.getRestaurante()));
        }

        LOG.log(Level.INFO, "Exito en la conversion de DTO a Entidad Reserva");
        
        return reserva;
        
        }catch(NullPointerException ex){
            LOG.log(Level.SEVERE, "Error en la conversion a Entidad Resereva");
        }
        catch(ConversionException ce){
            LOG.log(Level.SEVERE, ce.getMessage());
        }
        return null;
    }

    /**
     * Convierte todos los atributos de Reserva a ReservaDTO, 
     * los elementos que pueden ser nulos se pasan directamente como nulos 
     * @param reserva Reserva a convertir.
     * @return Reserva convertida en DTO.
     */
    public ReservaDTO toDTO(Reserva reserva) {
       try{
        
        if (reserva == null) {
            return null; // Devuelve null si la reserva es nula
        }

        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setId(String.valueOf(reserva.getId()));
        reservaDTO.setFechaHoraReserva(reserva.getFechaHoraReserva());
        reservaDTO.setNumeroPersonas(reserva.getNumeroPersonas());
        reservaDTO.setCosto(reserva.getCosto());
        reservaDTO.setEstado(reserva.getEstado());

        // Conversión de cliente
        if (reserva.getCliente() != null) {
            reservaDTO.setCliente(clienteCVR.toDTO(reserva.getCliente()));
        }

        // Conversión de mesa
        if (reserva.getMesa() != null) {
            reservaDTO.setMesa(mesaCVR.toDTO(reserva.getMesa()));
        }

        // Conversión de restaurante
        if (reserva.getRestaurante() != null) {
            reservaDTO.setRestaurante(restauranteCVR.
                    toDTO(reserva.getRestaurante()));
        }

        LOG.log(Level.INFO, "Exito en la conversion de Entidad Reserva a DTO");
        
        return reservaDTO;
        
       }catch(NullPointerException ex){
           LOG.log(Level.SEVERE, "Error en la conversion a ReserevaDTO");
       }
       catch(ConversionException ce){
           LOG.log(Level.SEVERE, ce.getMessage());
       }
        return null;
    }
    
}
