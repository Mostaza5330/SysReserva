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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase encargada de modelar la entidad Mesa
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
@Entity
@Table(name = "mesas")
public class Mesa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id de la mesa.

    @Column(name = "codigo_mesa", nullable = false, unique = true)
    private String codigoMesa; // codigo de la mesa.

    @Column(name = "tipo_mesa", nullable = false)
    private String tipoMesa; // tipo de mesa

    @Column(name = "capacidad_minima", nullable = false)
    private int capacidadMinima; // capacidad minima de la mesa.
    
    @Column(name = "capacidad_maxima", nullable = false)
    private int capacidadMaxima; // capacidad maxima de la mesa.

    @Column(nullable = false)
    private String ubicacion; // ubicacion de la mesa.

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante; // restaurante asociado a la mesa.

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.PERSIST)
    private List<Reserva> reservas; // lista de reservas asociada a la mesa.

    /**
     * Constructor por defecto de la clase.
     */
    public Mesa() {
    }

    /**
     * Constructor dedicado a las inserciones de nuevas mesas.
     * 
     * @param codigoMesa  codigo de la mesa.
     * @param tipoMesa tipo de mesa
     * @param capacidadMinima capacidad minima de la mesa.
     * @param capacidadMaxima capacidad maxima de la mesa.
     * @param ubicacion ubicacion de la mesa.
     * @param restaurante restaurante asociado a la mesa.
     */
    public Mesa(String codigoMesa, String tipoMesa, int capacidadMinima,
            int capacidadMaxima, String ubicacion, Restaurante restaurante) {
        this.codigoMesa = codigoMesa;
        this.tipoMesa = tipoMesa;
        this.capacidadMinima = capacidadMinima;
        this.capacidadMaxima = capacidadMaxima;
        this.ubicacion = ubicacion;
        this.restaurante = restaurante;
    }

    /**
     * Constructor completo para obtener todos los atributos de la clase.
     * 
     * @param id id de la mesa.
     * @param codigoMesa  codigo de la mesa.
     * @param tipoMesa tipo de mesa
     * @param capacidadMinima capacidad minima de la mesa.
     * @param capacidadMaxima capacidad maxima de la mesa.
     * @param ubicacion ubicacion de la mesa.
     * @param restaurante restaurante asociado a la mesa. 
     * @param reservas lista de reservas asociada a la mesa.
     */
    public Mesa(Long id, String codigoMesa, String tipoMesa, 
            int capacidadMinima, int capacidadMaxima, String ubicacion, 
            Restaurante restaurante, List<Reserva> reservas) {
        this.id = id;
        this.codigoMesa = codigoMesa;
        this.tipoMesa = tipoMesa;
        this.capacidadMinima = capacidadMinima;
        this.capacidadMaxima = capacidadMaxima;
        this.ubicacion = ubicacion;
        this.restaurante = restaurante;
        this.reservas = reservas;
    }

    //Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoMesa() {
        return codigoMesa;
    }

    public void setCodigoMesa(String codigoMesa) {
        this.codigoMesa = codigoMesa;
    }

    public String getTipoMesa() {
        return tipoMesa;
    }

    public void setTipoMesa(String tipoMesa) {
        this.tipoMesa = tipoMesa;
    }

    public int getCapacidadMinima() {
        return capacidadMinima;
    }

    public void setCapacidadMinima(int capacidadMinima) {
        this.capacidadMinima = capacidadMinima;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    /**
     * Metodo toString para representar en linea de texto los atributos 
     * de la clase.
     * 
     * @return String con todos los atributos de la clase.
     */
    @Override
    public String toString() {
        return "Mesa{" + "id=" + id + ", codigoMesa=" + codigoMesa + 
                ", tipoMesa=" + tipoMesa + ", capacidadMinima=" + 
                capacidadMinima + ", capacidadMaxima=" + capacidadMaxima + 
                ", ubicacion=" + ubicacion + ", restaurante=" + restaurante + 
                ", reservas=" + reservas + '}';
    }

        
}
