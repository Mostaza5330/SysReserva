/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import BO.MesaBO;
import BO.RestauranteBO;
import DTOs.MesaDTO;
import DTOs.RestauranteDTO;
import Excepciones.BOException;
import Excepciones.FacadeException;
import Interfaces.IMesaBO;
import Interfaces.IRestauranteBO;
import interfacesFachada.IMesaFCD;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase de fachada para la gestión de mesas en el sistema de un restaurante.
 * 
 * Esta clase implementa la interfaz `IMesaFCD` y actúa como una capa de fachada 
 * que abstrae las interacciones entre la interfaz de usuario y la lógica de negocio 
 * relacionada con las mesas, proporcionando métodos para cargar mesas en una tabla, 
 * calcular el precio según el tamaño de la mesa, agregar mesas y generar códigos de mesa.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class MesaFCD implements IMesaFCD {

    private final IMesaBO mesaBO;             // Lógica de negocio para las mesas
    private final IRestauranteBO restauranteBO; // Lógica de negocio para el restaurante

    /**
     * Constructor que inicializa las dependencias de negocio.
     */
    public MesaFCD() {
        this.mesaBO = new MesaBO();
        this.restauranteBO = new RestauranteBO();
    }

    /**
     * Carga los datos de las mesas en una tabla de la interfaz de usuario.
     * 
     * Obtiene una lista de mesas, limpia el modelo de la tabla para evitar 
     * duplicados y luego agrega las mesas obtenidas en la tabla.
     * 
     * @param table JTable en el que se cargarán los datos de las mesas.
     * @throws FacadeException si ocurre un error en la capa de negocio al 
     *                         consultar las mesas.
     */
    @Override
    public void cargarTablaMesas(JTable table) throws FacadeException {
        try {
            List<MesaDTO> mesas = mesaBO.consultarMesas();
            
            String[] columnNames = {"Codigo", "Tipo", "Min Personas", 
                "Max Personas", "Ubicacion"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            model.setRowCount(0);

            for (MesaDTO mesa : mesas) {
                Object[] rowData = {
                    mesa.getCodigoMesa(),
                    mesa.getTipoMesa(),
                    mesa.getCapacidadMinima(),
                    mesa.getCapacidadMaxima(),
                    mesa.getUbicacion()
                };
                model.addRow(rowData);
            }
            table.setModel(model);
            
        } catch (BOException be) {
            throw new FacadeException(be.getMessage());
        }
    }

    /**
     * Calcula el precio de una mesa basado en su tamaño.
     * 
     * @param tamaño Tamaño de la mesa (PEQUEÑA, MEDIANA o GRANDE).
     * @return String que representa el precio de la mesa.
     */
    @Override
    public String calcularPrecio(String tamaño) {
        switch (tamaño.toUpperCase()) {
            case "PEQUEÑA": return "300.0";
            case "MEDIANA": return "500.0";
            case "GRANDE": return "700.0";
            default: return null;
        }
    }

    /**
     * Agrega una cantidad especificada de mesas con un tamaño y ubicación.
     * 
     * Genera las mesas según los parámetros proporcionados, asigna las 
     * capacidades mínima y máxima según el tamaño, y las agrega al sistema.
     * 
     * @param numero Cantidad de mesas a agregar.
     * @param tamaño Tamaño de las mesas a agregar.
     * @param ubicacion Ubicación de las mesas.
     * @throws FacadeException si ocurre un error en la generación o adición 
     *                         de las mesas.
     */
    @Override
    public void agregarMesas(int numero, String tamaño, String ubicacion) 
            throws FacadeException {
        try {
            List<MesaDTO> mesas = new ArrayList<>();
            RestauranteDTO restaurante = restauranteBO.consultar();

            int capacidadMinima;
            int capacidadMaxima;
            if (tamaño.equalsIgnoreCase("PEQUEÑA")) {
                capacidadMinima = 1;
                capacidadMaxima = 2;    
            } else if (tamaño.equalsIgnoreCase("MEDIANA")) {
                capacidadMinima = 3;
                capacidadMaxima = 4;    
            } else {
                capacidadMinima = 5;
                capacidadMaxima = 8;    
            }

            int respuesta = JOptionPane.showConfirmDialog(null, 
                    "¿Está seguro de que desea cancelar la reservación?", 
                    "Cancelar reservación", JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                
                 List<String> codigos = generarCodigos(tamaño, ubicacion, numero);
                
                 for (int i = 0; i < numero; i++) {
                 mesas.add(new MesaDTO(
                    codigos.get(i),
                    tamaño,
                    capacidadMinima,
                    capacidadMaxima,
                    ubicacion,
                    restaurante
                 ));
                }
               
                //agregamos las mesas que que ya han sido generadas
                mesaBO.agregarMesas(mesas);
               
            }        
            
        } catch (BOException be) {
            throw new FacadeException(be.getMessage());
        }
    }

    /**
     * Genera una lista de códigos únicos para cada mesa basada en el tamaño y 
     * la ubicación, asegurándose de que no se supere un límite.
     * 
     * @param tamaño Tamaño de las mesas.
     * @param ubicacion Ubicación de las mesas.
     * @param cantidad Cantidad de códigos a generar.
     * @return Lista de códigos únicos generados para las mesas.
     * @throws FacadeException si ocurre un error durante la generación de 
     *                         códigos.
     */
    private List<String> generarCodigos(String tamaño, String ubicacion, 
            int cantidad) 
            throws FacadeException {
        List<String> codigos = new ArrayList<>();

        try {
            int cantidadActual = mesaBO.cantidadMesasPorUbicacion(ubicacion);
            System.out.println("Cantidad actual: " + cantidadActual);

            if (cantidadActual + cantidad > 1000) {
                throw new FacadeException("No se pueden generar más de 1000 "
                        + "mesas en esta ubicación.");
            }

            String codigoUbicacion;
            switch (ubicacion.toUpperCase()) {
                case "VENTANA": codigoUbicacion = "VEN"; break;
                case "TERRAZA": codigoUbicacion = "TER"; break;
                case "GENERAL": codigoUbicacion = "GEN"; break;
                default: throw new FacadeException("Ubicación desconocida: " + 
                        ubicacion);
            }

            for (int i = 1; i <= cantidad; i++) {
                int numeroActual = cantidadActual + i;
                String numeroFormateado = String.format("%03d", numeroActual);
                String codigo = codigoUbicacion + "-" + tamaño + "-" + 
                        numeroFormateado;
                codigos.add(codigo);
            }

        } catch (BOException be) {
            throw new FacadeException("Error generando códigos de mesa: " + 
                    be.getMessage(), be);
        }

        return codigos;
    }
}

