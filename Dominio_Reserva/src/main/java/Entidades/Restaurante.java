/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.time.LocalTime;
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
 * Clase encargada de modelar la entidad Restaurante.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
@Entity
@Table(name = "restaurantes")
public class Restaurante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id del restaurante.

    @Column(nullable = false, unique = true)
    private String nombre; // Nombre del restaurante.

    @Column(nullable = false)
    private String direccion; // Direccion del restaurante.

    @Column(nullable = false)
    private String telefono; // telefono del restaurante.

    @Column(name = "hora_apertura", nullable = false)
    private LocalTime horaApertura; // Hora de apertura del restaurante.

    @Column(name = "hora_cierre", nullable = false)
    private LocalTime horaCierre; // Hora de cierre del restaurante. 

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.PERSIST)
    private List<Mesa> mesas;  // Lista de mesas del restaurante.


    /**
     * Constructor por defecto de la clase.
     * 
     */
    public Restaurante() {
    }

    /**
     * Constructor que se utilizara para inserciones.
     * 
     * @param nombre Nombre del restaurante.
     * @param direccion Direccion del restaurante.
     * @param telefono telefono del restaurante.
     * @param horaApertura Hora de apertura del restaurante.
     * @param horaCierre Hora de cierre del restaurante. 
     */
    public Restaurante(String nombre, String direccion, String telefono, 
            LocalTime horaApertura, LocalTime horaCierre) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    /**
     * Constructor que inicializa todos los atributos de la clase.
     * 
     * @param id id del restaurante.
     * @param nombre Nombre del restaurante.
     * @param direccion Direccion del restaurante.
     * @param telefono telefono del restaurante.
     * @param horaApertura Hora de apertura del restaurante.
     * @param horaCierre Hora de cierre del restaurante. 
     * @param mesas Lista de mesas del restaurante.
     */
    public Restaurante(Long id, String nombre, String direccion, 
            String telefono, LocalTime horaApertura, LocalTime horaCierre, 
            List<Mesa> mesas) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.mesas = mesas;
    }

    //Getters y Setters
    
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    /**
     * Metodo toString para representar en linea de texto los atributos 
     * de la clase.
     * 
     * @return String con todos los atributos de la clase.
     */
    @Override
    public String toString() {
        return "Restaurante{" + "id=" + id + ", nombre=" + nombre + 
                ", direccion=" + direccion + ", telefono=" + telefono + 
                ", horaApertura=" + horaApertura + ", horaCierre=" + 
                horaCierre + ", mesas=" + mesas + '}';
    }
    
}
