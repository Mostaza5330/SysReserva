package interfacesFachada;

import DTOs.RestauranteDTO;

/**
 * Interfaz para establecer las horas de apertura y cierre de un restaurante.
 * 
 * @Autor: Sebastian Murrieta Verduzco - 233463
 */
public interface IHorarioRestauranteFCD {
    
    /**
     * Establece la hora de apertura del restaurante.
     *
     * @param restaurante el restaurante cuyo horario se va a establecer
     * @param horaAperturaString la hora de apertura en formato "hh:mm a"
     * @throws IllegalArgumentException si el formato de la hora no es válido
     */
    void establecerHoraApertura(RestauranteDTO restaurante, String horaAperturaString) throws IllegalArgumentException;

    /**
     * Establece la hora de cierre del restaurante.
     *
     * @param restaurante el restaurante cuyo horario se va a establecer
     * @param horaCierreString la hora de cierre en formato "hh:mm a"
     * @throws IllegalArgumentException si el formato de la hora no es válido
     */
    void establecerHoraCierre(RestauranteDTO restaurante, String horaCierreString) throws IllegalArgumentException;
}
