/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Clase encargada de modelar la entidad Reserva.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
@Entity
@Table(name = "reservas")
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id de la reserva.

    @Column(name = "fecha_hora_reserva", nullable = false)
    private LocalDateTime fechaHoraReserva; // Fecha y hora de la reserva.

    @Column(name = "numero_personas", nullable = false)
    private int numeroPersonas; // Numero de personas.

    @Column(nullable = false)
    private double costo; // Costo de la reserva.

    @Column(nullable = false)
    private String estado; // Estado de la reserva Ejemplo: activa, cancelada.

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // Cliente al que le pertenece la reserva.

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa; // Mesa a la que le pertenece la reserva.

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante; // Restaurante al que pertenece la reserva.

    /**
     * Constructor por defecto de la clase.
     *
     */
    public Reserva() {
    }

    /**
     * Constructor para inicializar los atributos necesarios para realizar una
     * insercion.
     *
     * @param fechaHoraReserva Fecha y hora de la reserva.
     * @param numeroPersonas Numero de personas.
     * @param costo Costo de la reserva.
     * @param estado Estado de la reserva.
     * @param cliente Cliente al que le pertenece la reserva.
     * @param mesa Mesa a la que le pertenece la reserva.
     * @param restaurante Restaurante al que pertenece la reserva.
     */
    public Reserva(LocalDateTime fechaHoraReserva, int numeroPersonas,
            double costo, String estado, Cliente cliente,
            Mesa mesa, Restaurante restaurante) {
        this.fechaHoraReserva = fechaHoraReserva;
        this.numeroPersonas = numeroPersonas;
        this.costo = costo;
        this.estado = estado;
        this.cliente = cliente;
        this.mesa = mesa;
        this.restaurante = restaurante;
    }

    /**
     * Constructor para inializar todos los atributos de la clase.
     *
     * @param id id de la reserva.
     * @param fechaHoraReserva Fecha y hora de la reserva.
     * @param numeroPersonas Numero de personas.
     * @param costo Costo de la reserva.
     * @param estado Estado de la reserva.
     * @param cliente Cliente al que le pertenece la reserva.
     * @param mesa Mesa a la que le pertenece la reserva.
     * @param restaurante Restaurante al que pertenece la reserva.
     */
    public Reserva(Long id, LocalDateTime fechaHoraReserva,
            int numeroPersonas, double costo, String estado,
            Cliente cliente, Mesa mesa, Restaurante restaurante) {
        this.id = id;
        this.fechaHoraReserva = fechaHoraReserva;
        this.numeroPersonas = numeroPersonas;
        this.costo = costo;
        this.estado = estado;
        this.cliente = cliente;
        this.mesa = mesa;
        this.restaurante = restaurante;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHoraReserva() {
        return fechaHoraReserva;
    }

    public void setFechaHoraReserva(LocalDateTime fechaHoraReserva) {
        this.fechaHoraReserva = fechaHoraReserva;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    /**
     * Metodo toString para representar en linea de texto los atributos de la
     * clase.
     *
     * @return String con todos los atributos de la clase.
     */
    @Override
    public String toString() {
        return "Reserva{" + "id=" + id + ", fechaHoraReserva="
                + fechaHoraReserva + ", numeroPersonas=" + numeroPersonas
                + ", costo=" + costo + ", estado=" + estado
                + ", cliente=" + cliente + ", mesa="
                + mesa + ", restaurante="
                + restaurante + '}';
    }

}
