/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesFachada;

import Excepciones.FacadeException;
import javax.swing.JComboBox;

/**
 * Interfaz `IClienteFCD` que define los métodos necesarios para gestionar
 * operaciones relacionadas con clientes en la capa de fachada.
 * Sirve como punto de interacción para cargar y manipular datos de clientes.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IClienteFCD {

    /**
     * Carga un ComboBox con la lista de clientes.
     *
     * @param combo El ComboBox que se llenará con los clientes.
     * @throws FacadeException Excepción lanzada en caso de error al cargar los datos.
     */
    public void cargarComboBoxClientes(JComboBox combo) throws FacadeException;
    
}

