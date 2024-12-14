/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import BO.ClienteBO;
import DTOs.ClienteDTO;
import Excepciones.BOException;
import Excepciones.FacadeException;
import Interfaces.IClienteBO;
import interfacesFachada.IClienteFCD;
import java.util.List;
import javax.swing.JComboBox;

/**
 * Clase de fachada para la gestión de clientes.
 * 
 * La clase `ClienteFCD` implementa la interfaz `IClienteFCD` y actúa como una
 * capa intermedia (fachada) entre la interfaz de usuario y la lógica de negocio 
 * relacionada con la gestión de clientes. Facilita la interacción con los clientes 
 * y maneja la carga de clientes en componentes de interfaz gráfica.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ClienteFCD implements IClienteFCD {

    // Objeto de lógica de negocio para gestionar operaciones de cliente
    IClienteBO clienteBO;

    /**
     * Constructor por defecto que inicializa el objeto de lógica de negocio.
     */
    public ClienteFCD() {
        this.clienteBO = new ClienteBO();
    }

    /**
     * Carga una lista de clientes en un componente JComboBox.
     * 
     * Este método llama a la capa de lógica de negocio para obtener una lista
     * de clientes y la carga en el JComboBox especificado, limpiando los 
     * elementos existentes antes de agregar los nuevos.
     * 
     * @param combo JComboBox en el que se cargarán los clientes
     * @throws FacadeException si ocurre un error en la capa de negocio al 
     *                         obtener la lista de clientes
     */
    @Override
    public void cargarComboBoxClientes(JComboBox combo) throws FacadeException {
        try {
            // Obtener lista de clientes desde la lógica de negocio
            List<ClienteDTO> clientes = clienteBO.obtenerClientes();

            // Limpiar el combo antes de cargar nuevos elementos
            combo.removeAllItems();

            // Agregar los clientes al combo
            for (ClienteDTO cliente : clientes) {
                combo.addItem(cliente);
            }
        } catch (BOException be) {
            // Lanzar una excepción de fachada en caso de error en la capa de negocio
            throw new FacadeException("Error al cargar los clientes: " + be.getMessage(), be);
        }
    }
}
