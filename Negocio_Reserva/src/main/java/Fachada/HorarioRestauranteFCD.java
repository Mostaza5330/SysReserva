package Fachada;

import DTOs.RestauranteDTO;
import interfacesFachada.IHorarioRestauranteFCD;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
     * @param horaAperturaString la hora de apertura en formato "hh:mm a"
     * @throws IllegalArgumentException si el formato de la hora no es válido
     */
    @Override
    public void establecerHoraApertura(RestauranteDTO restaurante, String horaAperturaString)
            throws IllegalArgumentException {
        if (horaAperturaString == null || !esFormatoHoraValido(horaAperturaString)) {
            logger.severe("Error: La hora de apertura '" + horaAperturaString + "' no es válida.");
            throw new IllegalArgumentException("Formato de hora inválido: " + horaAperturaString);
        }

        try {
            LocalTime horaApertura = LocalTime.parse(horaAperturaString, HORA_FORMATO);
            restaurante.setHoraApertura(horaApertura);
            logger.info("Hora de apertura actualizada: " + horaApertura);
        } catch (Exception e) {
            logger.severe("Error al establecer la hora de apertura: " + e.getMessage());
            throw new IllegalArgumentException("Error al procesar la hora de apertura: " + e.getMessage());
        }
    }

    /**
     * Establece la hora de cierre del restaurante.
     *
     * @param restaurante el restaurante cuyo horario se va a establecer
     * @param horaCierreString la hora de cierre en formato "hh:mm a"
     * @throws IllegalArgumentException si el formato de la hora no es válido o
     * si la hora de cierre es anterior a la hora de apertura
     */
    @Override
    public void establecerHoraCierre(RestauranteDTO restaurante, String horaCierreString)
            throws IllegalArgumentException {
        if (horaCierreString == null || !esFormatoHoraValido(horaCierreString)) {
            logger.severe("Error: La hora de cierre '" + horaCierreString + "' no es válida.");
            throw new IllegalArgumentException("Formato de hora inválido: " + horaCierreString);
        }

        try {
            LocalTime horaCierre = LocalTime.parse(horaCierreString, HORA_FORMATO);
            if (!isHoraCierreValida(restaurante, horaCierre)) {
                logger.severe("Error: La hora de cierre debe ser posterior a la de apertura.");
                throw new IllegalArgumentException("La hora de cierre debe ser posterior a la hora de apertura");
            }

            restaurante.setHoraCierre(horaCierre);
            logger.info("Hora de cierre actualizada: " + horaCierre);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.severe("Error al establecer la hora de cierre: " + e.getMessage());
            throw new IllegalArgumentException("Error al procesar la hora de cierre: " + e.getMessage());
        }
    }

    /**
     * Obtiene la hora de apertura del restaurante como String.
     *
     * @param restaurante el DTO del restaurante
     * @return String con la hora de apertura en formato "hh:mm a", o null si no
     * está establecida
     */
    public String obtenerHoraAperturaString(RestauranteDTO restaurante) {
        LocalTime horaApertura = restaurante.getHoraApertura();
        return horaApertura != null ? horaApertura.format(HORA_FORMATO) : null;
    }

    /**
     * Obtiene la hora de cierre del restaurante como String.
     *
     * @param restaurante el DTO del restaurante
     * @return String con la hora de cierre en formato "hh:mm a", o null si no
     * está establecida
     */
    public String obtenerHoraCierreString(RestauranteDTO restaurante) {
        LocalTime horaCierre = restaurante.getHoraCierre();
        return horaCierre != null ? horaCierre.format(HORA_FORMATO) : null;
    }

    /**
     * Obtiene una lista de horarios disponibles en intervalos de 30 minutos
     * entre la hora de apertura y cierre.
     *
     * @param restaurante el DTO del restaurante
     * @return List<String> con los horarios disponibles en formato "hh:mm a"
     * @throws IllegalStateException si no hay horarios establecidos
     */
    public List<String> obtenerHorariosDisponibles(RestauranteDTO restaurante)
            throws IllegalStateException {
        LocalTime horaApertura = restaurante.getHoraApertura();
        LocalTime horaCierre = restaurante.getHoraCierre();

        if (horaApertura == null || horaCierre == null) {
            logger.severe("No hay horarios establecidos para el restaurante");
            throw new IllegalStateException("No hay horarios establecidos para el restaurante");
        }

        List<String> horariosDisponibles = new ArrayList<>();
        LocalTime tiempoActual = horaApertura;

        while (!tiempoActual.isAfter(horaCierre)) {
            horariosDisponibles.add(tiempoActual.format(HORA_FORMATO));
            tiempoActual = tiempoActual.plusMinutes(30);
            if (tiempoActual.isAfter(horaCierre)) {
                break;
            }
        }

        return horariosDisponibles;
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
