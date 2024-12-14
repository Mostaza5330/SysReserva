/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.MesaDTO;
import Excepciones.BOException;
import java.util.List;

/**
 * Interfaz que define las operaciones básicas para la gestión de mesas en la 
 * capa de negocio (BO). Esta interfaz proporciona un contrato para las 
 * implementaciones que manejarán la persistencia y recuperación de 
 * información sobre mesas.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IMesaBO {

    
    /**
     * Agrega una lista de mesas a la base de datos.
     * 
     * @param mesas La lista de objetos que se desea agregar.
     * @throws Excepciones.BOException En caso de error uno nunca sabe.
     */
    public void agregarMesas(List<MesaDTO> mesas) throws BOException;
    
    /**
     * Consulta y recupera una lista de todas las mesas almacenadas en la base de datos.
     * 
     * @return Una lista de objetos que representa todas las mesas.
     * @throws Excepciones.BOException En caso de error uno nunca sabe.
     */
    public List<MesaDTO> consultarMesas() throws BOException;
    
    /**
     * Devuelve la cantidad de mesas dependiendo de su ubicacion;
     * 
     * @param ubicacion ubicacion de la mesa a buscar.
     * @return El número de mesas clasificadas como pequeñas.
     * @throws Excepciones.BOException En caso de error uno nunca sabe.
     */
    public int cantidadMesasPorUbicacion(String ubicacion) throws BOException;
    
    /**
     * Obtiene una lista de mesas dependiendo su tipo especidicado en el 
     * parametro.
     * 
     * @param tipo Tipo de mesa.
     * @return Lista de mesas filtradas por tipo.
     * @throws Excepciones.BOException En caso de error uno nunca sabe.
     */
    public List<MesaDTO> obtenerMesasPorTipo(String tipo) throws BOException;
    
}
