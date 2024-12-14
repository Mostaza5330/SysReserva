/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Convertidores;

import DTOs.MesaDTO;
import Entidades.Mesa;
import Excepciones.ConversionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que contiene los metodos de conversion para La entidad de Mesa.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class MesaCVR {

    //metodo logger para la registrar cambios.
    private static final Logger LOG = Logger.
            getLogger(MesaCVR.class.getName());
   
   
    private RestauranteCVR restauranteCVR; // Convertidor de restaurante.
    private ReservaCVR reservaCVR; // Convertidor de reserva.

    public MesaCVR() {
        this.restauranteCVR = new RestauranteCVR();
    }
    
    /**
     * Convierte todos los atributos de MesaDTO a Mesa, 
     * los elementos que pueden ser nulos como la lista de reservas se pasan 
     * directamente como nulos en caso de serlo.
     * 
     * @param mesaDTO MesaDTO a convertir.
     * @return Mesa de tipo entidad.
     * @throws Excepciones.ConversionException
     */
    public Mesa toEntity(MesaDTO mesaDTO) throws ConversionException {
        try{
        if (mesaDTO == null) {
            return null; // Retorna null si el DTO es nulo
        }

        Mesa mesa = new Mesa();
        mesa.setId(mesaDTO.getId() != null ? Long.valueOf(mesaDTO.getId()) : null);
        mesa.setCodigoMesa(mesaDTO.getCodigoMesa()); // Asigna el código de mesa
        mesa.setTipoMesa(mesaDTO.getTipoMesa()); // Asigna el tipo de mesa
        mesa.setCapacidadMinima(mesaDTO.getCapacidadMinima()); 
        mesa.setCapacidadMaxima(mesaDTO.getCapacidadMaxima()); 
        mesa.setUbicacion(mesaDTO.getUbicacion()); // Asigna la ubicación

        // Convierte el restaurante utilizando el convertidor
        if (mesaDTO.getRestaurante() != null) {
            mesa.setRestaurante(restauranteCVR.
                    toEntity(mesaDTO.getRestaurante()));
        }
        else{
            mesa.setRestaurante(null);
        }

//        // Convierte la lista de reservas usando el convertidor
//        if (mesaDTO.getReservas() != null) {
//            List<Reserva> reservas = mesaDTO.getReservas()
//                                              .stream()
//                                              .map(reservaCVR::toEntity)
//                                              .collect(Collectors.toList());
//            mesa.setReservas(reservas);
//        }
        
        LOG.log(Level.INFO, "Exito en la conversion de DTO a Entidad");
        
        return mesa;
        
        }catch(NullPointerException ex){
            LOG.log(Level.SEVERE, "Error en la conversion a Mesa");
            throw new ConversionException();
        }
    }

    /**
     * Convierte todos los atributos de Mesa a MesaDTO, 
     * los elementos que pueden ser nulos como la lista de reservas se pasan 
     * directamente como nulos en caso de serlo.
     * 
     * @param mesa Mesa a convertir.
     * @return MesaDTO convertida en DTO.
     * @throws Excepciones.ConversionException
     */
    public MesaDTO toDTO(Mesa mesa) throws ConversionException {
        try{
        
        if (mesa == null) {
            return null; // Retorna null si la entidad es nula
        }

        MesaDTO mesaDTO = new MesaDTO();
        mesaDTO.setId(String.valueOf(mesa.getId()) != null ? String.valueOf(mesa.getId()) : null); // Asigna el ID
        mesaDTO.setCodigoMesa(mesa.getCodigoMesa()); // Asigna el código de mesa
        mesaDTO.setTipoMesa(mesa.getTipoMesa()); // Asigna el tipo de mesa
        mesaDTO.setCapacidadMinima(mesa.getCapacidadMinima()); // Asigna capacidad mínima
        mesaDTO.setCapacidadMaxima(mesa.getCapacidadMaxima()); // Asigna capacidad máxima
        mesaDTO.setUbicacion(mesa.getUbicacion()); // Asigna la ubicación

        // Convierte el restaurante utilizando el convertidor
        if (mesa.getRestaurante() != null) {
            mesaDTO.setRestaurante(restauranteCVR.toDTO(mesa.getRestaurante()));
        }

//        // Convierte la lista de reservas usando el convertidor
//        if (mesa.getReservas() != null) {
//            List<ReservaDTO> reservasDTO = mesa.getReservas()
//                                                 .stream()
//                                                 .map(reservaCVR::toDTO)
//                                                 .collect(Collectors.toList());
//            mesaDTO.setReservas(reservasDTO);
//        }

        LOG.log(Level.INFO, "Exito en la conversion de Cliente a DTO");
        
        return mesaDTO;
        
        }catch(NullPointerException ex){
            LOG.log(Level.SEVERE, "Error en la conversion a MesaDTO");
            throw new ConversionException();
        }
    }
    
}
