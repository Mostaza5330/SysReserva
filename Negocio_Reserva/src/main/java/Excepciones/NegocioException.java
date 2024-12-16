package Excepciones;

/**
 * Clase que representa una excepción personalizada para errores en la lógica de negocio.
 * Esta clase extiende de {@code Exception} y permite manejar errores específicos 
 * relacionados con las operaciones de la capa de negocio de la aplicación.
 * 
 * Proporciona múltiples constructores para personalizar los mensajes de error y las 
 * causas de la excepción.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class NegocioException extends Exception {

    /**
     * Constructor por defecto que crea una nueva instancia de {@code NegocioException}
     * sin un mensaje de error o causa específica.
     */
    public NegocioException() {
        super();
    }

    /**
     * Constructor que crea una nueva instancia de {@code NegocioException} con un mensaje específico.
     * 
     * @param message el mensaje descriptivo del error.
     */
    public NegocioException(String message) {
        super(message);
    }

    /**
     * Constructor que crea una nueva instancia de {@code NegocioException} con un mensaje 
     * específico y una causa subyacente.
     * 
     * @param message el mensaje descriptivo del error.
     * @param cause la excepción original que causó este error.
     */
    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor que crea una nueva instancia de {@code NegocioException} con una causa específica.
     * 
     * @param cause la excepción original que causó este error.
     */
    public NegocioException(Throwable cause) {
        super(cause);
    }
}
