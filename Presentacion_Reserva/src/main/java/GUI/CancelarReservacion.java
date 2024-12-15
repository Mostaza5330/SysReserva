/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BO.ReservaBO;
import DTOs.ReservaDTO;
import Excepciones.BOException;
import Excepciones.FacadeException;
import Fachada.ReservaFCD;
import Interfaces.IReservaBO;
import interfacesFachada.IReservaFCD;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que representa la interfaz gráfica para cancelar reservaciones. Permite
 * al usuario buscar, visualizar y eliminar reservaciones existentes.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class CancelarReservacion extends javax.swing.JFrame {

    private IReservaBO reservaBO;
    private IReservaFCD reservaFCD;
    private List<ReservaDTO> reservas;

    /**
     * Crea una nueva instancia de CancelarReservacion. Inicializa los
     * componentes de la interfaz gráfica y carga los datos iniciales.
     */
    public CancelarReservacion() {
        initComponents();
        cargarDependencias();
        cargarDatosIniciales();
    }

    /**
     * Carga las dependencias necesarias para el funcionamiento de la clase.
     */
    private void cargarDependencias() {
        this.reservaBO = new ReservaBO();
        this.reservaFCD = new ReservaFCD();
    }

    /**
     * Carga los datos iniciales de las reservas y actualiza la tabla de
     * visualización.
     */
    public void cargarDatosIniciales() {
        try {
            this.reservas = reservaBO.obtenerReservas();
            cargarTablaReservas();
        } catch (BOException be) {
            JOptionPane.showMessageDialog(this, be.getMessage());
        }
    }

    /**
     * Verifica que los campos de búsqueda sean válidos.
     *
     * @return true si los campos son válidos, false en caso contrario.
     */
    private boolean verificarCampos() {
        if (this.dcInicio.getDate() == null && this.dcFin.getDate() != null) {
            JOptionPane.showMessageDialog(this, "Seleccione la fecha de inicio para buscar");
            return false;
        } else if (this.dcInicio.getDate() != null && this.dcFin.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione la fecha de fin para buscar");
            return false;
        } else if (this.dcInicio.getDate() == null && this.dcFin.getDate() != null && txfNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un filtro para buscar");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Obtiene la reserva seleccionada en la tabla.
     *
     * @return la reserva seleccionada, o null si no hay ninguna selección.
     */
    private ReservaDTO reservaSeleccionada() {
        int selectedRow = tblReservas.getSelectedRow();

        if (selectedRow != -1) { // Cambié == a !=
            ReservaDTO reserva = this.reservas.get(selectedRow);
            return reserva;
        }
        return null;
    }

    /**
     * Carga las reservas en la tabla de visualización.
     */
    public void cargarTablaReservas() {
        List<ReservaDTO> reservaciones = this.reservas;
        String[] columnNames = {"Fecha", "Hora", "Personas", "Costo", "Estado", "Cliente", "Mesa"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        model.setRowCount(0);

        for (ReservaDTO reserva : reservaciones) {
            Object[] rowData = {
                reserva.getFechaHoraReserva().toLocalDate(),
                reserva.getFechaHoraReserva().toLocalTime(),
                reserva.getNumeroPersonas(),
                reserva.getCosto(),
                reserva.getEstado(),
                reserva.getCliente().getNombre(),
                reserva.getMesa().getCodigoMesa()
            };
            model.addRow(rowData);
        }

        tblReservas.setModel(model);
    }

    /**
     * Forma la fecha de inicio para la búsqueda a partir del componente de
     * selección de fecha.
     *
     * @return la fecha de inicio como LocalDateTime, o null si no se ha
     * seleccionado.
     */
    private LocalDateTime formarFechainicio() {
        if (dcInicio.getDate() != null) {
            return dcInicio.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate().atTime(12, 00);
        } else {
            return null;
        }
    }

    /**
     * Forma la fecha de fin para la búsqueda a partir del componente de
     * selección de fecha.
     *
     * @return la fecha de fin como LocalDateTime, o null si no se ha
     * seleccionado.
     */
    private LocalDateTime formarFechaFin() {
        if (dcFin.getDate() != null) {
            return dcFin.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate().atTime(22, 00);
        } else {
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tituloPanel = new Control.PanelRound();
        Titulo = new javax.swing.JLabel();
        Fondo = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        regresarBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dcInicio = new com.toedter.calendar.JDateChooser();
        dcFin = new com.toedter.calendar.JDateChooser();
        txfNombre = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        mesasPanel = new Control.PanelRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();
        btnLimpiar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tituloPanel.setBackground(new java.awt.Color(102, 102, 102));
        tituloPanel.setRoundBottomLeft(50);
        tituloPanel.setRoundBottomRight(50);
        tituloPanel.setRoundTopLeft(50);
        tituloPanel.setRoundTopRight(50);

        Titulo.setBackground(new java.awt.Color(255, 255, 255));
        Titulo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 255, 255));
        Titulo.setText("Cancelar Reservaciones");

        javax.swing.GroupLayout tituloPanelLayout = new javax.swing.GroupLayout(tituloPanel);
        tituloPanel.setLayout(tituloPanelLayout);
        tituloPanelLayout.setHorizontalGroup(
            tituloPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tituloPanelLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(Titulo)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        tituloPanelLayout.setVerticalGroup(
            tituloPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tituloPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(Titulo)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        getContentPane().add(tituloPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 46, 480, 110));

        Fondo.setBackground(new java.awt.Color(22, 22, 22));
        Fondo.setForeground(new java.awt.Color(51, 51, 51));
        Fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBuscar.setBackground(new java.awt.Color(102, 102, 102));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        Fondo.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 420, 140, 50));

        regresarBtn.setBackground(new java.awt.Color(102, 102, 102));
        regresarBtn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        regresarBtn.setForeground(new java.awt.Color(255, 255, 255));
        regresarBtn.setText("Regresar");
        regresarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regresarBtnActionPerformed(evt);
            }
        });
        Fondo.add(regresarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 630, 140, 50));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Fecha de reservación");
        Fondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Hora de reservación");
        Fondo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, -1, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nombre del cliente");
        Fondo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, -1, -1));
        Fondo.add(dcInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, 300, 40));
        Fondo.add(dcFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 260, 300, 40));

        txfNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Fondo.add(txfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, 300, 40));

        btnEliminar.setBackground(new java.awt.Color(102, 102, 102));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Cancelar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        Fondo.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 640, 140, 50));

        mesasPanel.setBackground(new java.awt.Color(51, 51, 51));
        mesasPanel.setForeground(new java.awt.Color(102, 102, 102));
        mesasPanel.setRoundBottomLeft(50);
        mesasPanel.setRoundBottomRight(50);
        mesasPanel.setRoundTopLeft(50);
        mesasPanel.setRoundTopRight(50);

        tblReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No.Mesa", "Tamaño de mesa", "Disponibilidad", "Lugar"
            }
        ));
        jScrollPane1.setViewportView(tblReservas);

        javax.swing.GroupLayout mesasPanelLayout = new javax.swing.GroupLayout(mesasPanel);
        mesasPanel.setLayout(mesasPanelLayout);
        mesasPanelLayout.setHorizontalGroup(
            mesasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mesasPanelLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        mesasPanelLayout.setVerticalGroup(
            mesasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mesasPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        Fondo.add(mesasPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 650, -1));

        btnLimpiar.setBackground(new java.awt.Color(102, 102, 102));
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        Fondo.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 140, 50));
        Fondo.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 550, 20));

        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action handler for the "Regresar" button. This method creates a new
     * instance of the Admistrador window and disposes of the current window.
     *
     * @param evt the event that triggered this action
     */
    private void regresarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regresarBtnActionPerformed
        new Admistrador().setVisible(true);
        dispose();
    }//GEN-LAST:event_regresarBtnActionPerformed

    /**
     * Action handler for the "Buscar" button. This method checks if the input
     * fields are valid, retrieves the client's name, start date, and end date,
     * then searches for filtered reservations. The results are assigned to a
     * local list and the reservation table is updated.
     *
     * @param evt the event that triggered this action
     */
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            if (verificarCampos()) {
                // Define the client's name
                String nombre = txfNombre.getText();

                // Define the start date
                LocalDateTime inicio = formarFechainicio();

                // Define the end date
                LocalDateTime fin = formarFechaFin();

                // Search for filtered data
                List<ReservaDTO> reservasFiltradas = reservaBO.buscarReservas(nombre,
                        inicio, fin);

                // Assign the results to the local list
                this.reservas = reservasFiltradas;

                // Load the reservation data back into the table
                cargarTablaReservas();
            }
        } catch (BOException be) {
            JOptionPane.showMessageDialog(this, be.getMessage());
        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    /**
     * Action handler for the "Eliminar" button. This method retrieves the
     * selected reservation, cancels it, retrieves the updated reservation data,
     * and reloads the reservation table.
     *
     * @param evt the event that triggered this action
     */
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            // Retrieve the selected reservation
            ReservaDTO reserva = reservaSeleccionada();

            // Cancel the reservation
            reservaFCD.cancelarReserva(reserva);

            // Retrieve the reservation data again
            this.reservas = reservaBO.obtenerReservas();

            // Reload the table
            cargarTablaReservas();
        } catch (FacadeException fe) {
            JOptionPane.showMessageDialog(this, fe.getMessage());
        } catch (BOException be) {
            JOptionPane.showMessageDialog(this, be.getMessage());
        } 
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        this.txfNombre.setText("");
        
        //refrescamos la tabla de reservaciones a su primer estado.
        try{
            this.reservas = reservaBO.obtenerReservas();
        }
        catch(BOException be){
            JOptionPane.showMessageDialog(this, be.getMessage());
        }
        
        //ahora si recargamos la tabla.
        cargarTablaReservas();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Fondo;
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private com.toedter.calendar.JDateChooser dcFin;
    private com.toedter.calendar.JDateChooser dcInicio;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private Control.PanelRound mesasPanel;
    private javax.swing.JButton regresarBtn;
    private javax.swing.JTable tblReservas;
    private Control.PanelRound tituloPanel;
    private javax.swing.JTextField txfNombre;
    // End of variables declaration//GEN-END:variables
}
