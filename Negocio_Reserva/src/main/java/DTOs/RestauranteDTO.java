/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalTime;

/**
 * Objeto de transferencia de datos para la entidad Restaurante.
 * 
 * Esta clase representa la información básica de un restaurante,
 * incluyendo su nombre, dirección, teléfono, y horarios de apertura
 * y cierre. Se utiliza para transferir datos entre las distintas capas
 * de la aplicación.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class RestauranteDTO {

    // Identificador único del restaurante
    private String id;
    
    // Nombre del restaurante
    private String nombre;
    
    // Dirección del restaurante
    private String direccion;
    
    // Teléfono del restaurante
    private String telefono;
    
    // Hora de apertura del restaurante
    private LocalTime horaApertura;
    
    // Hora de cierre del restaurante
    private LocalTime horaCierre;

    /**
     * Constructor por defecto de la clase.
     * 
     * Este constructor permite crear un objeto vacío de tipo RestauranteDTO.
     */
    public RestauranteDTO() {
    }

    /**
     * Constructor que inicializa un restaurante sin ID.
     * 
     * Se utiliza generalmente para registrar nuevos restaurantes en la base de datos,
     * donde el ID se asignará automáticamente.
     * 
     * @param nombre Nombre del restaurante
     * @param direccion Dirección del restaurante
     * @param telefono Teléfono de contacto del restaurante
     * @param horaApertura Hora de apertura del restaurante
     * @param horaCierre Hora de cierre del restaurante
     */
    public RestauranteDTO(String nombre, String direccion, String telefono, 
                          LocalTime horaApertura, LocalTime horaCierre) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    /**
     * Constructor completo que inicializa todos los atributos, incluido el ID.
     * 
     * Se utiliza para cargar un restaurante existente desde la base de datos.
     * 
     * @param id Identificador único del restaurante
     * @param nombre Nombre del restaurante
     * @param direccion Dirección del restaurante
     * @param telefono Teléfono de contacto del restaurante
     * @param horaApertura Hora de apertura del restaurante
     * @param horaCierre Hora de cierre del restaurante
     */
    public RestauranteDTO(String id, String nombre, String direccion, 
                          String telefono, LocalTime horaApertura, 
                          LocalTime horaCierre) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    // Getters y Setters

    /**
     * Obtiene el ID del restaurante.
     * 
     * @return ID del restaurante
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el ID del restaurante.
     * 
     * @param id Nuevo ID del restaurante
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del restaurante.
     * 
     * @return Nombre del restaurante
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del restaurante.
     * 
     * @param nombre Nuevo nombre del restaurante
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la dirección del restaurante.
     * 
     * @return Dirección del restaurante
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del restaurante.
     * 
     * @param direccion Nueva dirección del restaurante
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el teléfono del restaurante.
     * 
     * @return Teléfono del restaurante
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del restaurante.
     * 
     * @param telefono Nuevo teléfono del restaurante
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la hora de apertura del restaurante.
     * 
     * @return Hora de apertura
     */
    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    /**
     * Establece la hora de apertura del restaurante.
     * 
     * @param horaApertura Nueva hora de apertura
     */
    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    /**
     * Obtiene la hora de cierre del restaurante.
     * 
     * @return Hora de cierre
     */
    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    /**
     * Establece la hora de cierre del restaurante.
     * 
     * @param horaCierre Nueva hora de cierre
     */
    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    /**
     * Devuelve una representación en cadena de los detalles del restaurante.
     * 
     * @return Representación en cadena del restaurante
     */
    @Override
    public String toString() {
        return "RestauranteDTO{" + "id=" + id + ", nombre=" + nombre 
                + ", direccion=" + direccion + ", telefono=" + telefono 
                + ", horaApertura=" + horaApertura + ", horaCierre=" 
                + horaCierre + '}';
    }

}

