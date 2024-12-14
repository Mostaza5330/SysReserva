package DTOs;

import DTOs.RestauranteDTO;
import java.time.LocalDateTime;

/**
 * Objeto de transferencia de datos para la entidad Reserva.
 *
 * Esta clase se utiliza para transferir datos de una reserva entre las
 * diferentes capas de la aplicación, como la capa de presentación y la capa de
 * negocio.
 *
 * Contiene información sobre la fecha y hora de la reserva, número de personas,
 * costo, estado de la reserva, y referencias a los objetos Cliente, Mesa y
 * Restaurante asociados.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ReservaDTO {

    // Identificador único de la reserva
    private String id;

    // Fecha y hora de la reserva
    private LocalDateTime fechaHoraReserva;

    // Número de personas para la reserva
    private int numeroPersonas;

    // Costo total de la reserva
    private double costo;

    // Estado de la reserva (ejemplo: "Confirmada", "Cancelada")
    private String estado;

    // Cliente asociado a la reserva
    private ClienteDTO cliente;

    // Mesa reservada
    private MesaDTO mesa;

    // Restaurante donde se realizó la reserva
    private RestauranteDTO restaurante;

    /**
     * Constructor por defecto de la clase.
     */
    public ReservaDTO() {
    }

    /**
     * Constructor que inicializa una reserva sin identificador. Se utiliza
     * generalmente para crear nuevas reservas en la base de datos.
     *
     * @param fechaHoraReserva Fecha y hora de la reserva
     * @param numeroPersonas Número de personas para la reserva
     * @param costo Costo de la reserva
     * @param estado Estado actual de la reserva
     * @param cliente Cliente asociado a la reserva
     * @param mesa Mesa reservada
     * @param restaurante Restaurante donde se realiza la reserva
     */
    public ReservaDTO(LocalDateTime fechaHoraReserva, int numeroPersonas,
            double costo, String estado,
            ClienteDTO cliente, MesaDTO mesa, RestauranteDTO restaurante) {
        this.fechaHoraReserva = fechaHoraReserva;
        this.numeroPersonas = numeroPersonas;
        this.costo = costo;
        this.estado = estado;
        this.cliente = cliente;
        this.mesa = mesa;
        this.restaurante = restaurante;
    }

    /**
     * Constructor completo que inicializa todos los campos, incluido el ID. Se
     * utiliza para cargar reservas existentes desde la base de datos.
     *
     * @param id Identificador de la reserva
     * @param fechaHoraReserva Fecha y hora de la reserva
     * @param numeroPersonas Número de personas para la reserva
     * @param costo Costo de la reserva
     * @param estado Estado actual de la reserva
     * @param cliente Cliente asociado a la reserva
     * @param mesa Mesa reservada
     * @param restaurante Restaurante donde se realiza la reserva
     */
    public ReservaDTO(String id, LocalDateTime fechaHoraReserva,
            int numeroPersonas, double costo, String estado,
            ClienteDTO cliente, MesaDTO mesa,
            RestauranteDTO restaurante) {
        this.id = id;
        this.fechaHoraReserva = fechaHoraReserva;
        this.numeroPersonas = numeroPersonas;
        this.costo = costo;
        this.estado = estado;
        this.cliente = cliente;
        this.mesa = mesa;
        this.restaurante = restaurante;
    }

    // Getters y Setters
    /**
     * Obtiene el ID de la reserva.
     *
     * @return ID de la reserva
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el ID de la reserva.
     *
     * @param id Nuevo ID de la reserva
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha y hora de la reserva.
     *
     * @return Fecha y hora de la reserva
     */
    public LocalDateTime getFechaHoraReserva() {
        return fechaHoraReserva;
    }

    /**
     * Establece la fecha y hora de la reserva.
     *
     * @param fechaHoraReserva Nueva fecha y hora de la reserva
     */
    public void setFechaHoraReserva(LocalDateTime fechaHoraReserva) {
        this.fechaHoraReserva = fechaHoraReserva;
    }

    /**
     * Obtiene el número de personas para la reserva.
     *
     * @return Número de personas
     */
    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    /**
     * Establece el número de personas para la reserva.
     *
     * @param numeroPersonas Nuevo número de personas
     */
    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    /**
     * Obtiene el costo de la reserva.
     *
     * @return Costo de la reserva
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Establece el costo de la reserva.
     *
     * @param costo Nuevo costo de la reserva
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * Obtiene el estado de la reserva.
     *
     * @return Estado de la reserva
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la reserva.
     *
     * @param estado Nuevo estado de la reserva
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el cliente asociado a la reserva.
     *
     * @return Cliente de la reserva
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente asociado a la reserva.
     *
     * @param cliente Nuevo cliente de la reserva
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene la mesa asociada a la reserva.
     *
     * @return Mesa reservada
     */
    public MesaDTO getMesa() {
        return mesa;
    }

    /**
     * Establece la mesa asociada a la reserva.
     *
     * @param mesa Nueva mesa reservada
     */
    public void setMesa(MesaDTO mesa) {
        this.mesa = mesa;
    }

    /**
     * Obtiene el restaurante donde se realizó la reserva.
     *
     * @return Restaurante de la reserva
     */
    public RestauranteDTO getRestaurante() {
        return restaurante;
    }

    /**
     * Establece el restaurante donde se realizó la reserva.
     *
     * @param restaurante Nuevo restaurante de la reserva
     */
    public void setRestaurante(RestauranteDTO restaurante) {
        this.restaurante = restaurante;
    }

    /**
     * Devuelve una representación en cadena de los detalles de la reserva.
     *
     * @return Representación en cadena de la reserva
     */
    @Override
    public String toString() {
        return "ReservaDTO{" + "id=" + id + ", fechaHoraReserva="
                + fechaHoraReserva + ", numeroPersonas=" + numeroPersonas
                + ", costo=" + costo + ", estado=" + estado
                + ", cliente=" + cliente + ", mesa=" + mesa
                + ", restaurante=" + restaurante + '}';
    }

}
