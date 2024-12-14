package DTOs;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de transferencia de datos para el cliente.
 *
 * Proporciona una representación simplificada de un cliente para transferir 
 * datos entre la capa de presentación y la capa de negocio. Incluye 
 * información básica del cliente y una lista de sus reservas.
 * 
 * Este DTO se utiliza en escenarios donde se necesita encapsular y transportar 
 * los datos del cliente sin exponer la entidad de la base de datos directamente.
 *
 * @author skevi
 * @author sebastian
 */
public class ClienteDTO {

    // Identificador único del cliente.
    private String id;

    // Nombre del cliente.
    private String nombre;

    // Teléfono de contacto del cliente.
    private String telefono;

    // Lista de reservas asociadas al cliente.
    private List<ReservaDTO> reservas;

    /**
     * Constructor por defecto de ClienteDTO.
     */
    public ClienteDTO() {
    }

    /**
     * Constructor que inicializa el nombre del cliente.
     * 
     * @param nombre El nombre del cliente.
     */
    public ClienteDTO(String nombre) {
        this.nombre = nombre;
        this.reservas = new ArrayList<>(); // Inicializa la lista de reservas vacía
    }

    /**
     * Constructor que inicializa el nombre y el teléfono del cliente.
     * 
     * @param nombre El nombre del cliente.
     * @param telefono El teléfono de contacto del cliente.
     */
    public ClienteDTO(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.reservas = new ArrayList<>(); // Inicializa la lista de reservas vacía
    }

    /**
     * Constructor completo que inicializa todos los atributos del cliente.
     * 
     * @param id El identificador único del cliente.
     * @param nombre El nombre del cliente.
     * @param telefono El teléfono de contacto del cliente.
     * @param reservas Lista de reservas asociadas al cliente. Si es null, se 
     *                 inicializa como una lista vacía.
     */
    public ClienteDTO(String id, String nombre, String telefono, List<ReservaDTO> reservas) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.reservas = reservas != null ? reservas : new ArrayList<>(); // Inicializa la lista de reservas
    }

    /**
     * Obtiene el identificador único del cliente.
     * 
     * @return El identificador único del cliente.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único del cliente.
     * 
     * @param id El identificador único del cliente.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del cliente.
     * 
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     * 
     * @param nombre El nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el teléfono de contacto del cliente.
     * 
     * @return El teléfono de contacto del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono de contacto del cliente.
     * 
     * @param telefono El teléfono de contacto del cliente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve una representación en cadena de los datos básicos del cliente.
     * 
     * @return Una cadena con el nombre y teléfono del cliente.
     */
    @Override
    public String toString() {
        return nombre + ", " + telefono;
    }
}

