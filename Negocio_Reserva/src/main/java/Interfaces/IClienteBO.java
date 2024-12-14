/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.ClienteDTO;
import Excepciones.BOException;
import java.util.List;

/**
 * Interfaz que define las operaciones básicas para la gestión de clientes en 
 * la capa de negocio (BO). Esta interfaz proporciona un contrato para las 
 * implementaciones que manejarán la persistencia y recuperación de 
 * información de clientes.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IClienteBO {
       
    /**
     * Obtiene los detalles de un cliente específico a partir de su objeto.
     * 
     * @param id id del cliente.
     * @return Cliente.
     * @throws Excepciones.BOException En caso de error uno nunca sabe.
     */
    public ClienteDTO obtenerCliente(Long id) throws BOException;
    
    /**
     * Recupera una lista de todos los clientes almacenados en la base de datos.
     * 
     * @return Una lista de objetos que representa todos los 
     * clientes.
     * @throws Excepciones.BOException En caso de error uno nunca sabe.
     */
    public List<ClienteDTO> obtenerClientes()throws BOException;
    
}
 