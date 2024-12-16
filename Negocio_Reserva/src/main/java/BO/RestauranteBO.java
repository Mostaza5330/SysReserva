package BO;

import Convertidores.RestauranteCVR;
import DAO.RestauranteDAO;
import DTOs.RestauranteDTO;
import Entidades.Restaurante;
import Excepciones.BOException;
import Excepciones.ConversionException;
import Excepciones.DAOException;
import Interfaces.IRestauranteBO;
import Interfaces.IRestauranteDAO;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase intermediaria entre la capa de DAO para la entidad Restaurante que 
 * maneja la lógica de negocio relacionada con los restaurantes.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 *
 */
public class RestauranteBO implements IRestauranteBO {

    private static final Logger LOG = Logger.getLogger(RestauranteBO.class.getName());
    private final IRestauranteDAO restauranteDAO;
    private final RestauranteCVR restauranteCVR;

    /**
     * Constructor por defecto de la clase.
     */
    public RestauranteBO() {
        this.restauranteDAO = new RestauranteDAO();
        this.restauranteCVR = new RestauranteCVR();
    }

    /**
     * Consulta los datos del restaurante.
     * 
     * @return RestauranteDTO con los datos del restaurante.
     * @throws BOException Si ocurre un error en la consulta o conversión.
     */
    @Override
    public RestauranteDTO consultar() throws BOException {
        try {
            Restaurante restaurante = restauranteDAO.consultar();
            RestauranteDTO restauranteDTO = restauranteCVR.toDTO(restaurante);
            LOG.log(Level.INFO, "Consulta exitosa del restaurante");
            return restauranteDTO;
        } catch (DAOException de) {
            LOG.log(Level.SEVERE, "Error en la consulta del restaurante", de);
            throw new BOException("Error al consultar los datos del restaurante: " + de.getMessage());
        } catch (ConversionException ce) {
            LOG.log(Level.SEVERE, "Error en la conversión de datos del restaurante", ce);
            throw new BOException("Error al procesar los datos del restaurante: " + ce.getMessage());
        }
    }

    /**
     * Cambia el horario del restaurante validando las reglas de negocio.
     *
     * @param horaApertura Nueva hora de apertura.
     * @param horaCierre Nueva hora de cierre.
     * @throws BOException Si ocurre un error o se violan las reglas de negocio.
     */
    public void cambiarHoraRestaurante(LocalTime horaApertura, LocalTime horaCierre) throws BOException {
        if (horaApertura == null || horaCierre == null) {
            LOG.log(Level.WARNING, "Intento de establecer horario con valores nulos");
            throw new BOException("Los horarios de apertura y cierre son obligatorios");
        }

        if (!horaCierre.isAfter(horaApertura)) {
            LOG.log(Level.WARNING, "Intento de establecer hora de cierre anterior o igual a la hora de apertura");
            throw new BOException("La hora de cierre debe ser posterior a la hora de apertura");
        }

        try {
            Restaurante restaurante = restauranteDAO.consultar();

            if (restaurante == null) {
                LOG.log(Level.SEVERE, "No se encontró el restaurante en la base de datos");
                throw new BOException("No se encontró el restaurante para actualizar");
            }

            restaurante.setHoraApertura(horaApertura);
            restaurante.setHoraCierre(horaCierre);

            restauranteDAO.actualizar(restaurante);

            LOG.log(Level.INFO, "Horario actualizado exitosamente. Apertura: {0}, Cierre: {1}", 
                    new Object[]{horaApertura, horaCierre});
        } catch (DAOException de) {
            LOG.log(Level.SEVERE, "Error al actualizar los horarios en la base de datos", de);
            throw new BOException("Error al guardar los horarios: " + de.getMessage());
        }
    }

    /**
     * Valida si un restaurante está abierto en una hora específica.
     *
     * @param hora Hora a validar.
     * @return true si el restaurante está abierto, false si está cerrado.
     * @throws BOException Si ocurre un error al consultar los horarios.
     */
    public boolean estaAbierto(LocalTime hora) throws BOException {
        try {
            Restaurante restaurante = restauranteDAO.consultar();

            if (restaurante.getHoraApertura() == null || restaurante.getHoraCierre() == null) {
                LOG.log(Level.WARNING, "Horarios no establecidos en el restaurante");
                return false;
            }

            boolean estaAbierto = !hora.isBefore(restaurante.getHoraApertura()) && 
                                 hora.isBefore(restaurante.getHoraCierre());

            LOG.log(Level.INFO, "Consulta de estado del restaurante para hora {0}: {1}", 
                    new Object[]{hora, estaAbierto ? "Abierto" : "Cerrado"});

            return estaAbierto;

        } catch (DAOException de) {
            LOG.log(Level.SEVERE, "Error al consultar los horarios del restaurante", de);
            throw new BOException("Error al verificar el horario del restaurante: " + de.getMessage());
        }
    }

    /**
     * Obtiene el tiempo restante hasta el cierre del restaurante.
     *
     * @param horaActual Hora actual para calcular el tiempo restante.
     * @return Minutos restantes hasta el cierre, o -1 si el restaurante está cerrado.
     * @throws BOException Si ocurre un error al consultar los horarios.
     */
    public long tiempoHastaCierre(LocalTime horaActual) throws BOException {
        try {
            Restaurante restaurante = restauranteDAO.consultar();

            if (!estaAbierto(horaActual)) {
                return -1;
            }

            return java.time.Duration.between(horaActual, restaurante.getHoraCierre()).toMinutes();

        } catch (DAOException de) {
            LOG.log(Level.SEVERE, "Error al calcular el tiempo hasta el cierre", de);
            throw new BOException("Error al calcular el tiempo hasta el cierre: " + de.getMessage());
        }
    }
}
