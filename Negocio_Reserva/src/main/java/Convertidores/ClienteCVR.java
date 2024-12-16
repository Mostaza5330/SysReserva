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

    private static final Logger LOG = Logger.getLogger(ClienteCVR.class.getName());
    private static final String ERROR_CONVERSION = "Error en la conversión";

    /**
     * Constructor por defecto.
     */
    public ClienteCVR() {
    }

    /**
     * Convierte un ClienteDTO a una entidad Cliente. Los atributos nulos se
     * manejan apropiadamente.
     *
     * @param clienteDTO ClienteDTO a convertir.
     * @return Cliente convertido o null si el DTO es null.
     * @throws ConversionException Si ocurre un error durante la conversión.
     */
    public Cliente toEntity(ClienteDTO clienteDTO) throws ConversionException {
        if (clienteDTO == null) {
            return null;
        }

        try {
            Cliente cliente = new Cliente();
            cliente.setId(Long.valueOf(clienteDTO.getId()));
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setTelefono(clienteDTO.getTelefono());

            LOG.log(Level.INFO, "Conversión exitosa de ClienteDTO a Cliente");
            return cliente;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ERROR_CONVERSION + " a Cliente", ex);
            throw new ConversionException(ERROR_CONVERSION + " a Cliente: " + ex.getMessage(), ex);
        }
    }

    /**
     * Convierte una entidad Cliente a ClienteDTO. Los atributos nulos se
     * manejan apropiadamente.
     *
     * @param cliente Cliente a convertir.
     * @return ClienteDTO convertido o null si el cliente es null.
     * @throws ConversionException Si ocurre un error durante la conversión.
     */
    public ClienteDTO toDTO(Cliente cliente) throws ConversionException {
        if (cliente == null) {
            return null;
        }

        try {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setId(String.valueOf(cliente.getId()));
            clienteDTO.setNombre(cliente.getNombre() != null ? cliente.getNombre() : "");
            clienteDTO.setTelefono(cliente.getTelefono() != null ? cliente.getTelefono() : "");

            LOG.log(Level.INFO, "Conversión exitosa de Cliente a ClienteDTO");
            return clienteDTO;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ERROR_CONVERSION + " a ClienteDTO", ex);
            throw new ConversionException(ERROR_CONVERSION + " a ClienteDTO: " + ex.getMessage(), ex);
        }
    }
}
