/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.RestauranteDTO;
import Excepciones.BOException;
import java.time.LocalTime;

/**
 * Interfaz que define las operaciones básicas para la gestión de restaurantes
 * en la capa de negocio (BO). Esta interfaz proporciona un contrato para las
 * implementaciones que manejarán la persistencia y recuperación de información
 * sobre restaurantes.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IRestauranteBO {

    /**
     * Consulta y recupera la información del restaurante.
     *
     * @return El objeto RestauranteDTO que representa la información del
     * restaurante.
     * @throws BOException En caso de error al consultar el restaurante.
     */
    public RestauranteDTO consultar() throws BOException;

    /**
     * Cambia el horario de apertura y cierre del restaurante.
     *
     * @param horaApertura La nueva hora de apertura del restaurante.
     * @param horaCierre La nueva hora de cierre del restaurante.
     * @throws BOException En caso de error al cambiar el horario del
     * restaurante.
     */
    void cambiarHoraRestaurante(LocalTime horaApertura, LocalTime horaCierre) throws BOException;

    /**
     * Verifica si el restaurante está abierto en el momento dado.
     *
     * @param hora La hora a verificar.
     * @return true si el restaurante está abierto, false en caso contrario.
     * @throws BOException En caso de error al verificar el estado del
     * restaurante.
     */
    boolean estaAbierto(LocalTime hora) throws BOException;

    /**
     * Calcula el tiempo restante hasta el cierre del restaurante.
     *
     * @param horaActual La hora actual.
     * @return El tiempo en minutos hasta el cierre del restaurante.
     * @throws BOException En caso de error al calcular el tiempo hasta el
     * cierre.
     */
    long tiempoHastaCierre(LocalTime horaActual) throws BOException;
}
