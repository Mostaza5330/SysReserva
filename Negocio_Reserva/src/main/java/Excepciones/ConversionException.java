/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 * Excepción personalizada para errores de conversión.
 * 
 * Esta clase extiende de `Exception` y se utiliza para manejar errores
 * específicos relacionados con la conversión de datos en la aplicación.
 * Proporciona varios constructores para permitir la creación de excepciones
 * con mensajes personalizados, causas específicas y opciones adicionales como
 * la supresión y la trazabilidad de la pila.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ConversionException extends Exception {

    /**
     * Constructor por defecto para ConversionException.
     * 
     * Crea una excepción sin mensaje ni causa.
     */
    public ConversionException() {
    }

    /**
     * Constructor que permite especificar un mensaje para la excepción.
     * 
     * @param message Mensaje que describe la causa de la excepción de conversión
     */
    public ConversionException(String message) {
        super(message);
    }

    /**
     * Constructor que permite especificar un mensaje y una causa para la excepción.
     * 
     * @param message Mensaje que describe la causa de la excepción
     * @param cause Causa original de la excepción
     */
    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor que permite especificar una causa para la excepción.
     * 
     * @param cause Causa original de la excepción
     */
    public ConversionException(Throwable cause) {
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
    public ConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}

