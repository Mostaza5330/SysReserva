/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 * Clase que representa una excepción personalizada para manejar errores 
 * relacionados con la capa de acceso a datos (DAO). Extiende la clase 
 * {@link Exception} para proporcionar un manejo de excepciones específico 
 * en operaciones de acceso a datos. Esta clase puede ser utilizada para 
 * señalar problemas en la interacción con bases de datos o sistemas de 
 * almacenamiento.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class DAOException extends Exception {

    /**
     * Constructor por defecto que crea una nueva instancia de 
     * {@code DAOException}.
     * Este constructor no proporciona un mensaje de error ni una causa 
     * subyacente.
     */
    public DAOException() {
    }

    /**
     * Constructor que crea una nueva instancia de {@code DAOException} 
     * con un mensaje específico.
     * 
     * @param message El mensaje de error que describe la excepción.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructor que crea una nueva instancia de {@code DAOException} con un 
     * mensaje y una causa específica.
     * 
     * @param message El mensaje de error que describe la excepción.
     * @param cause La causa subyacente de la excepción 
     *              (otra excepción que la causó).
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor que crea una nueva instancia de {@code DAOException} con 
     * una causa específica.
     * 
     * @param cause La causa subyacente de la excepción 
     *              (otra excepción que la causó).
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor que crea una nueva instancia de {@code DAOException} con un 
     * mensaje, una causa, y opciones para habilitar la supresión de 
     * excepciones y habilitar o deshabilitar la escritura del stack trace.
     * 
     * @param message El mensaje de error que describe la excepción.
     * @param cause La causa subyacente de la excepción (otra excepción que la causó).
     * @param enableSuppression Indica si se permite la supresión de excepciones.
     * @param writableStackTrace Indica si el stack trace es escribible.
     */
    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

