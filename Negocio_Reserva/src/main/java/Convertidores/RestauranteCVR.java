/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Convertidores;


import DTOs.RestauranteDTO;
import Entidades.Restaurante;
import Excepciones.ConversionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestauranteCVR {

    //metodo logger para la registrar cambios.
    private static final Logger LOG = Logger.
            getLogger(RestauranteCVR.class.getName());
    

    /**
     * Constructor por defecto.
     */
    public RestauranteCVR() {
       
    }
    
    /**
     * Convierte todos los atributos de RestauranteDTO a Restaurante, los elementos que 
     * pueden ser nulos como la lista de mesas se pasan directamente como 
     * nulos en caso de serlo.
     * 
     * @param restauranteDTO RestauranteDTO a convertir.
     * @return Restaurante de tipo entidad.
     * @throws Excepciones.ConversionException
     */
    public Restaurante toEntity(RestauranteDTO restauranteDTO) throws ConversionException{
        try{
        
        if (restauranteDTO == null) {
            return null;
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setId(Long.valueOf(restauranteDTO.getId()));
        restaurante.setNombre(restauranteDTO.getNombre());
        restaurante.setDireccion(restauranteDTO.getDireccion());
        restaurante.setTelefono(restauranteDTO.getTelefono());
        restaurante.setHoraApertura(restauranteDTO.getHoraApertura());
        restaurante.setHoraCierre(restauranteDTO.getHoraCierre());
        
        LOG.log(Level.INFO, "Exito en la conversion de DTO a Entidad "
                + "Restaurante");
        
        return restaurante;
        
        }catch(NullPointerException ne){
            LOG.log(Level.SEVERE, "Error en la conversion a Entidad "
                    + "Restaurante");
            throw new ConversionException();
        }
    }

    /**
     * Convierte todos los atributos de Restaurante a RestauranteDTO, los elementos que 
     * pueden ser nulos como la lista de mesas se pasan directamente como 
     * nulos en caso de serlo.
     * 
     * @param restaurante Restaurante a convertir.
     * @return RestauranteDTO convertida en DTO.
     * @throws Excepciones.ConversionException
     */
    public RestauranteDTO toDTO(Restaurante restaurante) throws ConversionException{
        try{
        
        if (restaurante == null) {
            return null;
        }

        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(String.valueOf(restaurante.getId()));
        restauranteDTO.setNombre(restaurante.getNombre());
        restauranteDTO.setDireccion(restaurante.getDireccion());
        restauranteDTO.setTelefono(restaurante.getTelefono());
        restauranteDTO.setHoraApertura(restaurante.getHoraApertura());
        restauranteDTO.setHoraCierre(restaurante.getHoraCierre());
        
        LOG.log(Level.INFO, "Exito en la conversion de Entidad Restaurante "
                + "a DTO");
        
        return restauranteDTO;
        
        }catch(NullPointerException ne){
            LOG.log(Level.SEVERE, "Error en la conversion a RestauranteDTO");
            throw new ConversionException();
        }
    }
    
}

