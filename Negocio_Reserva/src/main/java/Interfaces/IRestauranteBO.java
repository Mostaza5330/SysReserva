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
     * @return El objeto que representa el restaurante consultado.
     * @throws Excepciones.BOException En caso de error uno nunca sabe.
     */
    public RestauranteDTO consultar() throws BOException;

    void cambiarHoraRestaurante(LocalTime horaApertura, LocalTime horaCierre) throws BOException;

    boolean estaAbierto(LocalTime hora) throws BOException;

    long tiempoHastaCierre(LocalTime horaActual) throws BOException;
}
