package Fachada;

import DTOs.RestauranteDTO;
import interfacesFachada.IHorarioRestauranteFCD;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * Clase fachada para gestionar el horario de apertura y cierre de un
 * restaurante. Esta clase permite establecer las horas de apertura y cierre de
 * un restaurante, asegurándose de que las horas sean válidas.
 * 
 * @autor Sebastian Murrieta Verduzco - 233463
 */
public class HorarioRestauranteFCD implements IHorarioRestauranteFCD {

    private static final Logger logger = Logger.getLogger(HorarioRestauranteFCD.class.getName());
    private static final DateTimeFormatter HORA_FORMATO = DateTimeFormatter.ofPattern("hh:mm a");

    /**
     * Establece la hora de apertura del restaurante.
     *
     * @param restaurante el restaurante cuyo horario se va a establecer
     * @param horaAperturaString la hora de apertura a establecer
     */
    @Override
    public void establecerHoraApertura(RestauranteDTO restaurante, String horaAperturaString) {
        if (horaAperturaString == null || !esFormatoHoraValido(horaAperturaString)) {
            logger.severe("Error: La hora de apertura '" + horaAperturaString + "' no es válida. Formato esperado: hh:mm a.");
            return;
        }

        LocalTime horaApertura = LocalTime.parse(horaAperturaString, HORA_FORMATO);
        restaurante.setHoraApertura(horaApertura);
        logger.info("Hora de apertura establecida: " + horaApertura);
    }

    /**
     * Establece la hora de cierre del restaurante.
     *
     * @param restaurante el restaurante cuyo horario se va a establecer
     * @param horaCierreString la hora de cierre a establecer en formato String
     */
    @Override
    public void establecerHoraCierre(RestauranteDTO restaurante, String horaCierreString) {
        if (horaCierreString == null || !esFormatoHoraValido(horaCierreString)) {
            logger.severe("Error: La hora de cierre '" + horaCierreString + "' no es válida. Formato esperado: hh:mm a.");
            return;
        }

        LocalTime horaCierre = LocalTime.parse(horaCierreString, HORA_FORMATO);

        if (!isHoraCierreValida(restaurante, horaCierre)) {
            logger.severe("Error: La hora de cierre debe ser posterior a la de apertura. Hora de apertura: " + restaurante.getHoraApertura() + ", Hora de cierre: " + horaCierre);
            return;
        }

        restaurante.setHoraCierre(horaCierre);
        logger.info("Hora de cierre establecida: " + horaCierre);
    }

    /**
     * Valida si la hora de cierre es posterior a la hora de apertura.
     *
     * @param restaurante el restaurante que se está verificando
     * @param horaCierre la hora de cierre a validar
     * @return true si la hora de cierre es válida; false en caso contrario
     */
    private boolean isHoraCierreValida(RestauranteDTO restaurante, LocalTime horaCierre) {
        LocalTime horaApertura = restaurante.getHoraApertura();
        return horaApertura != null && horaCierre.isAfter(horaApertura);
    }

    /**
     * Valida si el formato de la hora es correcto (hh:mm a).
     *
     * @param hora la cadena de hora a validar
     * @return true si el formato es válido, false de lo contrario
     */
    private boolean esFormatoHoraValido(String hora) {
        try {
            LocalTime.parse(hora, HORA_FORMATO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
