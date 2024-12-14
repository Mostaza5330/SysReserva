/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase encargada de modelar la entidad Cliente.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id del cliente
    
    @Column(name = "nombre",nullable = false)
    private String nombre; // nombre del cliente.
    
    @Column(name = "telefono", nullable = false, unique = true)
    private String telefono; // telefono del cliente 
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private List<Reserva> reservas; // lista de reservas del cliente.

    /**
     * Constructor por defecto de la clase 
     */
    public Cliente() {
    }
    
    /**
     * Constructor que se utilizara para inserciones
     * 
     * @param nombre nombre del cliente.
     * @param telefono telefono del cliente.
     */
    public Cliente(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }
    
    /**
     * Metodo completo para obtener el cliente de la base de datos con todos
     * sus atributos.
     * 
     * @param id id del cliente
     * @param nombreCompleto nombre del cliente.
     * @param telefono telefono del cliente. 
     * @param reservas lista de reservas del cliente.
     */
    public Cliente(Long id, String nombreCompleto, String telefono, 
            List<Reserva> reservas) {
        this.id = id;
        this.nombre = nombreCompleto;
        this.telefono = telefono;
        this.reservas = reservas;
    }
    
    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    /**
     * Metodo toString para obtener los atributos de la clase.
     * 
     * @return String con todos los atributos de la clase.
     */
    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombreCompleto=" + nombre + 
                ", telefono=" + telefono + ", reservas=" + reservas + '}';
    }
    
}
