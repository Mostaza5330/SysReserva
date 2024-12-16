package interfacesFachada;

import DTOs.RestauranteDTO;
import java.time.LocalTime;
import java.util.List;

/**
 * Interfaz para establecer las horas de apertura y cierre de un restaurante.
 *
 * @Autor: Sebastian Murrieta Verduzco - 233463
 */
public interface IHorarioRestauranteFCD {

    /**
     * Establece la hora de apertura del restaurante.
     *
     * @param restaurante el DTO del restaurante
     * @param horaApertura la hora de apertura a establecer
     * @throws IllegalArgumentException si la hora de apertura es inválida
     */
    void establecerHoraApertura(RestauranteDTO restaurante, LocalTime horaApertura) throws IllegalArgumentException;

    /**
     * Establece la hora de cierre del restaurante.
     *
     * @param restaurante el DTO del restaurante
     * @param horaCierre la hora de cierre a establecer
     * @throws IllegalArgumentException si la hora de cierre es inválida
     */
    void establecerHoraCierre(RestauranteDTO restaurante, LocalTime horaCierre) throws IllegalArgumentException;

    /**
     * Obtiene una lista de horarios disponibles en intervalos de 30 minutos
     * entre la hora de apertura y cierre.
     *
     * @param restaurante el DTO del restaurante
     * @return List<String> con los horarios disponibles en formato "hh:mm a"
     * @throws IllegalStateException si no hay horarios establecidos
     */
    List<String> obtenerHorariosDisponibles(RestauranteDTO restaurante) throws IllegalStateException;

    /**
     * Convierte LocalTime a String para mostrar en la interfaz de usuario.
     *
     * @param time la hora a convertir
     * @return String formateado para mostrar
     */
    String formatearHoraParaMostrar(LocalTime time);
}
