package DTOs;


/**
 * Objeto de transferencia de datos para una mesa en un restaurante.
 *
 * Representa los datos de una mesa, incluyendo su código, tipo, capacidad,
 * ubicación y el restaurante al que pertenece. Este DTO se utiliza para 
 * transferir los datos de una mesa entre la capa de presentación y la capa 
 * de negocio sin exponer la entidad de la base de datos directamente.
 * 
 * @author skevi
 */
public class MesaDTO {

    // Identificador único de la mesa.
    private String id;

    // Código identificador de la mesa.
    private String codigoMesa;

    // Tipo de mesa, por ejemplo: "interior" o "exterior".
    private String tipoMesa;

    // Capacidad mínima de ocupantes para la mesa.
    private int capacidadMinima;

    // Capacidad máxima de ocupantes para la mesa.
    private int capacidadMaxima;

    // Ubicación específica de la mesa dentro del restaurante.
    private String ubicacion;

    // Restaurante al que pertenece la mesa.
    private RestauranteDTO restaurante;  

    /**
     * Constructor por defecto.
     */
    public MesaDTO() {
    }

    /**
     * Constructor que inicializa todos los atributos excepto el identificador.
     * 
     * @param codigoMesa Código de la mesa.
     * @param tipoMesa Tipo de mesa.
     * @param capacidadMinima Capacidad mínima de la mesa.
     * @param capacidadMaxima Capacidad máxima de la mesa.
     * @param ubicacion Ubicación de la mesa.
     * @param restaurante Restaurante al que pertenece la mesa.
     */
    public MesaDTO(String codigoMesa, String tipoMesa, int capacidadMinima, 
                   int capacidadMaxima, String ubicacion, RestauranteDTO restaurante) {
        this.codigoMesa = codigoMesa;
        this.tipoMesa = tipoMesa;
        this.capacidadMinima = capacidadMinima;
        this.capacidadMaxima = capacidadMaxima;
        this.ubicacion = ubicacion;
        this.restaurante = restaurante;
    }

    /**
     * Constructor que inicializa todos los atributos, incluyendo el 
     * identificador.
     * 
     * @param id Identificador único de la mesa.
     * @param codigoMesa Código de la mesa.
     * @param tipoMesa Tipo de mesa.
     * @param capacidadMinima Capacidad mínima de la mesa.
     * @param capacidadMaxima Capacidad máxima de la mesa.
     * @param ubicacion Ubicación de la mesa.
     * @param restaurante Restaurante al que pertenece la mesa.
     */
    public MesaDTO(String id, String codigoMesa, String tipoMesa, 
                   int capacidadMinima, int capacidadMaxima, String ubicacion, 
                   RestauranteDTO restaurante) {
        this.id = id;
        this.codigoMesa = codigoMesa;
        this.tipoMesa = tipoMesa;
        this.capacidadMinima = capacidadMinima;
        this.capacidadMaxima = capacidadMaxima;
        this.ubicacion = ubicacion;
        this.restaurante = restaurante;
    }

    /**
     * Constructor que inicializa los atributos de la mesa sin el restaurante y
     * el identificador.
     * 
     * @param codigoMesa Código de la mesa.
     * @param tipoMesa Tipo de mesa.
     * @param capacidadMinima Capacidad mínima de la mesa.
     * @param capacidadMaxima Capacidad máxima de la mesa.
     * @param ubicacion Ubicación de la mesa.
     */
    public MesaDTO(String codigoMesa, String tipoMesa, int capacidadMinima, 
                   int capacidadMaxima, String ubicacion) {
        this.codigoMesa = codigoMesa;
        this.tipoMesa = tipoMesa;
        this.capacidadMinima = capacidadMinima;
        this.capacidadMaxima = capacidadMaxima;
        this.ubicacion = ubicacion;
    }
    
    // Getters y Setters

    /**
     * Obtiene el identificador único de la mesa.
     * 
     * @return El identificador único de la mesa.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único de la mesa.
     * 
     * @param id El identificador único de la mesa.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el código identificador de la mesa.
     * 
     * @return El código de la mesa.
     */
    public String getCodigoMesa() {
        return codigoMesa;
    }

    /**
     * Establece el código identificador de la mesa.
     * 
     * @param codigoMesa El código de la mesa.
     */
    public void setCodigoMesa(String codigoMesa) {
        this.codigoMesa = codigoMesa;
    }

    /**
     * Obtiene el tipo de la mesa.
     * 
     * @return El tipo de la mesa.
     */
    public String getTipoMesa() {
        return tipoMesa;
    }

    /**
     * Establece el tipo de la mesa.
     * 
     * @param tipoMesa El tipo de la mesa.
     */
    public void setTipoMesa(String tipoMesa) {
        this.tipoMesa = tipoMesa;
    }

    /**
     * Obtiene la capacidad mínima de la mesa.
     * 
     * @return La capacidad mínima de la mesa.
     */
    public int getCapacidadMinima() {
        return capacidadMinima;
    }

    /**
     * Establece la capacidad mínima de la mesa.
     * 
     * @param capacidadMinima La capacidad mínima de la mesa.
     */
    public void setCapacidadMinima(int capacidadMinima) {
        this.capacidadMinima = capacidadMinima;
    }

    /**
     * Obtiene la capacidad máxima de la mesa.
     * 
     * @return La capacidad máxima de la mesa.
     */
    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    /**
     * Establece la capacidad máxima de la mesa.
     * 
     * @param capacidadMaxima La capacidad máxima de la mesa.
     */
    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    /**
     * Obtiene la ubicación de la mesa dentro del restaurante.
     * 
     * @return La ubicación de la mesa.
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * Establece la ubicación de la mesa dentro del restaurante.
     * 
     * @param ubicacion La ubicación de la mesa.
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * Obtiene el restaurante al que pertenece la mesa.
     * 
     * @return El restaurante asociado a la mesa.
     */
    public RestauranteDTO getRestaurante() {
        return restaurante;
    }

    /**
     * Establece el restaurante al que pertenece la mesa.
     * 
     * @param restaurante El restaurante asociado a la mesa.
     */
    public void setRestaurante(RestauranteDTO restaurante) {
        this.restaurante = restaurante;
    }

    /**
     * Representación en cadena del objeto MesaDTO, mostrando sus atributos 
     * principales.
     * 
     * @return Una cadena con los datos de la mesa.
     */
    @Override
    public String toString() {
        return "MesaDTO{" + "id=" + id + ", codigoMesa=" + codigoMesa + 
               ", tipoMesa=" + tipoMesa + ", capacidadMinima=" + capacidadMinima + 
               ", capacidadMaxima=" + capacidadMaxima + ", ubicacion=" + ubicacion + 
               ", restaurante=" + restaurante + '}';
    }
}

