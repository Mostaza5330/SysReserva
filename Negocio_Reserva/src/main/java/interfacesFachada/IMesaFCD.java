/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesFachada;

import Excepciones.FacadeException;
import javax.swing.JTable;

/**
 * Interfaz `IMesaFCD` que define los métodos necesarios para gestionar
 * operaciones relacionadas con mesas en la capa de fachada.
 * Permite la carga, cálculo y adición de mesas en la aplicación.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public interface IMesaFCD {

    /**
     * Carga una tabla con la lista de mesas disponibles.
     *
     * @param table La tabla (`JTable`) que se llenará con las mesas.
     * @throws FacadeException Excepción lanzada en caso de error al cargar los datos.
     */
    public void cargarTablaMesas(JTable table) throws FacadeException;

    /**
     * Calcula el precio de una mesa en función de su tamaño.
     *
     * @param tamaño El tamaño de la mesa (pequeño, mediano, grande, etc.).
     * @return El precio correspondiente al tamaño de la mesa como cadena de texto.
     */
    public String calcularPrecio(String tamaño);

    /**
     * Agrega una nueva mesa al sistema con las especificaciones dadas.
     *
     * @param numero    Número identificador de la mesa.
     * @param tamaño    Tamaño de la mesa (pequeño, mediano, grande, etc.).
     * @param ubicacion Ubicación de la mesa en el establecimiento.
     * @throws FacadeException Excepción lanzada en caso de error al agregar la mesa.
     */
    public void agregarMesas(int numero, String tamaño, String ubicacion) 
            throws FacadeException;
    
}

