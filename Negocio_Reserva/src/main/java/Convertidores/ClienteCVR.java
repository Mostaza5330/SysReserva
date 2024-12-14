/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Convertidores;

import DTOs.ClienteDTO;
import Entidades.Cliente;
import Excepciones.ConversionException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Clase que contiene los metodos de conversion para La entidad de Cliente.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ClienteCVR {

    private static final Logger LOG = Logger.
            getLogger(ClienteCVR.class.getName());
    
    /**
     * Constructor por defecto.
     */
    public ClienteCVR() {
    }
            
    /**
     * Convierte todos los atributos de ClienteDTO a Cliente, 
     * los elementos que pueden ser nulos como la lista de reservas se pasan 
     * directamente como nulos en caso de serlo.
     * 
     * @param clienteDTO ClienteDTO a convertir.
     * @return Cliente de tipo entidad.
     * @throws Excepciones.ConversionException
     */
    public Cliente toEntity(ClienteDTO clienteDTO) throws ConversionException {
        try{
            
        if (clienteDTO == null) {
            return null; 
        }

        Cliente cliente = new Cliente();
        cliente.setId(Long.valueOf(clienteDTO.getId())); // Asigna el ID
        cliente.setNombre(clienteDTO.getNombre()); // Asigna el nombre
        cliente.setTelefono(clienteDTO.getTelefono()); // Asigna el teléfono
        
        LOG.log(Level.INFO, "Exito en la conversion de DTO a Cliente");
        
        return cliente; // retorna cliente convertido.
        
        } catch(NullPointerException ex){
            LOG.log(Level.SEVERE, "Error en la conversion a Cliente");
            throw new ConversionException();
        }
    }

    /**
     * Convierte todos los atributos de Cliente a ClienteDTO, 
     * los elementos que pueden ser nulos como la lista de reservas se pasan 
     * directamente como nulos en caso de serlo.
     * 
     * @param cliente Cliente a convertir.
     * @return ClienteDTO convertido.
     * @throws Excepciones.ConversionException
     */
    public ClienteDTO toDTO(Cliente cliente) throws ConversionException{
        if (cliente == null) {
            return null; // Alternativamente, podrías lanzar una excepción aquí
        }

        try {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setId(String.valueOf(cliente.getId())); 
            clienteDTO.setNombre(cliente.getNombre() != null ? cliente.getNombre() : "");
            clienteDTO.setTelefono(cliente.getTelefono() != null ? cliente.getTelefono() : "");

            LOG.log(Level.INFO, "Éxito en la conversión de Cliente a DTO");
            return clienteDTO;

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error en la conversión a DTO", ex);
            throw new ConversionException("Error en la conversión a DTO: " + ex.getMessage());
        }
    }
}
