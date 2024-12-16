package Fachada;

import DTOs.RestauranteDTO;
import interfacesFachada.IHorarioRestauranteFCD;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase fachada para gestionar el horario de apertura y cierre de un
 * restaurante utilizando LocalTime para operaciones internas.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class HorarioRestauranteFCD implements IHorarioRestauranteFCD {

    private static final Logger logger = Logger.getLogger(HorarioRestauranteFCD.class.getName());
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a").withLocale(Locale.US);

    /**
     * Obtiene una lista de horarios disponibles en intervalos de 30 minutos
     * entre la hora de apertura y cierre de un restaurante.
     *
     * @param restaurante el DTO del restaurante que contiene las horas de apertura y cierre
     * @return una lista de horarios disponibles formateados en "hh:mm a"
     * @throws IllegalStateException si no hay horarios establecidos en el restaurante
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
            horariosDisponibles.add(tiempoActual.format(DISPLAY_FORMATTER));
            tiempoActual = tiempoActual.plusMinutes(30);

            if (tiempoActual.isAfter(horaCierre)) {
                break;
            }
        }

        return horariosDisponibles;
    }

    /**
     * Establece la hora de apertura para un restaurante.
     *
     * @param restaurante el DTO del restaurante al que se asignará la hora de apertura
     * @param horaApertura la hora de apertura a establecer
     * @throws IllegalArgumentException si la hora de apertura es nula
     */
    @Override
    public void establecerHoraApertura(RestauranteDTO restaurante, LocalTime horaApertura)
            throws IllegalArgumentException {
        if (horaApertura == null) {
            logger.severe("Error: La hora de apertura no puede ser nula");
            throw new IllegalArgumentException("La hora de apertura no puede ser nula");
        }

        try {
            restaurante.setHoraApertura(horaApertura);
            logger.info("Hora de apertura actualizada: " + horaApertura.format(DISPLAY_FORMATTER));
        } catch (Exception e) {
            logger.severe("Error al establecer la hora de apertura: " + e.getMessage());
            throw new IllegalArgumentException("Error al establecer la hora de apertura");
        }
    }

    /**
     * Establece la hora de cierre para un restaurante.
     *
     * @param restaurante el DTO del restaurante al que se asignará la hora de cierre
     * @param horaCierre la hora de cierre a establecer
     * @throws IllegalArgumentException si la hora de cierre es nula o no es válida
     */
    @Override
    public void establecerHoraCierre(RestauranteDTO restaurante, LocalTime horaCierre)
            throws IllegalArgumentException {
        if (horaCierre == null) {
            logger.severe("Error: La hora de cierre no puede ser nula");
            throw new IllegalArgumentException("La hora de cierre no puede ser nula");
        }

        try {
            if (!isHoraCierreValida(restaurante, horaCierre)) {
                logger.severe("Error: La hora de cierre debe ser posterior a la de apertura");
                throw new IllegalArgumentException("La hora de cierre debe ser posterior a la hora de apertura");
            }

            restaurante.setHoraCierre(horaCierre);
            logger.info("Hora de cierre actualizada: " + horaCierre.format(DISPLAY_FORMATTER));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.severe("Error al establecer la hora de cierre: " + e.getMessage());
            throw new IllegalArgumentException("Error al establecer la hora de cierre");
        }
    }

    /**
     * Valida si la hora de cierre es posterior a la hora de apertura.
     *
     * @param restaurante el restaurante que se está verificando
     * @param horaCierre la hora de cierre a validar
     * @return true si la hora de cierre es posterior a la hora de apertura; false en caso contrario
     */
    private boolean isHoraCierreValida(RestauranteDTO restaurante, LocalTime horaCierre) {
        LocalTime horaApertura = restaurante.getHoraApertura();
        return horaApertura == null || horaCierre.isAfter(horaApertura);
    }

    /**
     * Convierte un objeto LocalTime a un formato de texto para mostrar en la interfaz de usuario.
     *
     * @param time la hora a convertir
     * @return una cadena formateada de la hora en "hh:mm a" o una cadena vacía si la hora es nula
     */
    public String formatearHoraParaMostrar(LocalTime time) {
        if (time == null) {
            return "";
        }
        return time.format(DISPLAY_FORMATTER);
    }
}
