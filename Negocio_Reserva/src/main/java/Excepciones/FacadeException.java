/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 * Excepción personalizada para errores de la capa de fachada.
 * 
 * Esta clase extiende de `Exception` y se utiliza para manejar errores
 * específicos relacionados con la capa de fachada en la aplicación. Proporciona
 * múltiples constructores para la creación de excepciones con diferentes niveles
 * de detalle, como mensajes personalizados, causas y opciones avanzadas como la
 * supresión de excepciones y la trazabilidad de la pila.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class FacadeException extends Exception {

    /**
     * Constructor por defecto para FacadeException.
     * 
     * Crea una excepción sin mensaje ni causa.
     */
    public FacadeException() {
    }

    /**
     * Constructor que permite especificar un mensaje para la excepción.
     * 
     * @param message Mensaje que describe la causa de la excepción de fachada
     */
    public FacadeException(String message) {
        super(message);
    }

    /**
     * Constructor que permite especificar un mensaje y una causa para la excepción.
     * 
     * @param message Mensaje que describe la causa de la excepción
     * @param cause Causa original de la excepción
     */
    public FacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor que permite especificar una causa para la excepción.
     * 
     * @param cause Causa original de la excepción
     */
    public FacadeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor avanzado que permite personalizar detalles de la excepción.
     * 
     * @param message Mensaje que describe la causa de la excepción
     * @param cause Causa original de la excepción
     * @param enableSuppression Habilita o deshabilita la supresión de excepciones
     * @param writableStackTrace Indica si la traza de la excepción es modificable
     */
    public FacadeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}

