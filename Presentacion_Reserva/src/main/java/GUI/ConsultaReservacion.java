/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BO.ClienteBO;
import BO.ReservaBO;
import DTOs.ReservaDTO;
import Excepciones.BOException;
import Excepciones.FacadeException;
import Fachada.ClienteFCD;
import Fachada.FiltrosFCD;
import Interfaces.IClienteBO;
import Interfaces.IReservaBO;
import interfacesFachada.IClienteFCD;
import interfacesFachada.IFiltrosFCD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * La clase <code>ConsultaReservacion</code> representa una ventana que permite
 * consultar las reservaciones realizadas. Esta ventana permite a los usuarios
 * ver la información de las reservaciones, así como buscar reservas por cliente
 * y fecha.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class ConsultaReservacion extends javax.swing.JFrame {

    private IFiltrosFCD filtros;
    private IClienteFCD clienteFCD;
    private IClienteBO clienteBO;
    private List<ReservaDTO> reservas;
    private IReservaBO reservaBO;

    /**
     * Crea una nueva instancia de <code>ConsultaReservacion</code>. Inicializa
     * los componentes de la interfaz gráfica y carga los clientes y datos
     * iniciales.
     */
    public ConsultaReservacion() {

        initComponents();
        filtros = new FiltrosFCD(); // Inicialización correcta
        clienteFCD = new ClienteFCD(); // Inicialización de clienteFCD
        this.reservaBO = new ReservaBO();
        cargarClientes();
        cargarDatosIniciales();
    }

    /**
     * Carga las reservas iniciales desde la capa de negocio y actualiza la
     * tabla con la información de las reservas.
     */
    public void cargarDatosIniciales() {
        try {
            // Obtener las reservas iniciales desde la capa de negocio
            this.reservas = reservaBO.obtenerReservas();
            this.clienteBO = new ClienteBO();
            // Cargar los datos en la tabla
            cargarTabla();
        } catch (BOException be) {
            // Mostrar el mensaje de error en caso de excepción
            JOptionPane.showMessageDialog(this, be.getMessage());
        }
    }

    /**
     * Carga los datos de las reservas en la tabla de la interfaz gráfica.
     */
    private void cargarTabla() {
        // Usamos el DefaultTableModel con nombres de columnas definidos en el constructor
        DefaultTableModel model = new DefaultTableModel(new Object[]{
            "No. Mesa", "Fecha y hora", "Tamaño de mesa", "Lugar", "Cliente"
        }, 0);

        // Agregar filas a la tabla
        for (ReservaDTO reserva : this.reservas) {
            model.addRow(new Object[]{
                reserva.getMesa().getCodigoMesa(),
                reserva.getFechaHoraReserva(),
                reserva.getMesa().getTipoMesa(),
                reserva.getMesa().getUbicacion(),
                reserva.getCliente().getNombre()
            });
        }

        // Asignar el modelo a la tabla
        reservacionesTabla.setModel(model);
    }

    /**
     * Carga los clientes en el JComboBox de la interfaz gráfica y establece un
     * ActionListener para manejar la selección de clientes.
     */
    private void cargarClientes() {
        try{
        DefaultComboBoxModel<String> combo = new DefaultComboBoxModel<>();
        clienteFCD.cargarComboBoxClientes(nombreClienteCB);
        nombreClienteCB.setModel(combo);

        // Agregar ActionListener al JComboBox
        nombreClienteCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Si se selecciona "Seleccione un cliente", no hacer nada
                if ("Seleccione un cliente".equals(nombreClienteCB.getSelectedItem())) {
                    return; // Salir si es la opción predeterminada
                }

                // Verificar si se ha hecho clic en el JComboBox
                if (e.getSource() == nombreClienteCB) {
                    // Limpiar el modelo actual
                    combo.removeAllElements();

                    // Agregar opción predeterminada
                    combo.addElement("Seleccione un cliente");

                    // Cargar clientes desde la base de datos
                    try {
                        clienteFCD.cargarComboBoxClientes(nombreClienteCB); // Llama al método de la interfaz para cargar los clientes
                    } catch (FacadeException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + ex.getMessage());
                    }

                    // Restablecer el JComboBox a la opción predeterminada
                    nombreClienteCB.setSelectedItem("Seleccione un cliente");
                }
            }
        });

//         Establecer el estado por defecto en "Seleccione un cliente"
        nombreClienteCB.setSelectedItem("Seleccione un cliente");
        }
        catch(FacadeException fe){
            JOptionPane.showMessageDialog(this, fe.getMessage());
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

        mesasPanel = new Control.PanelRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        reservacionesTabla = new javax.swing.JTable();
        tituloPanel = new Control.PanelRound();
        Titulo = new javax.swing.JLabel();
        Fondo = new javax.swing.JPanel();
        buscarBtn = new javax.swing.JButton();
        cancelarBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        nombreClienteCB = new javax.swing.JComboBox<>();
        numeroTelefonoTxt = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mesasPanel.setBackground(new java.awt.Color(51, 51, 51));
        mesasPanel.setForeground(new java.awt.Color(102, 102, 102));
        mesasPanel.setRoundBottomLeft(50);
        mesasPanel.setRoundBottomRight(50);
        mesasPanel.setRoundTopLeft(50);
        mesasPanel.setRoundTopRight(50);

        reservacionesTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No.Mesa", "Fecha y hora", "Tamaño de mesa", "Lugar", "Cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(reservacionesTabla);
        if (reservacionesTabla.getColumnModel().getColumnCount() > 0) {
            reservacionesTabla.getColumnModel().getColumn(0).setMinWidth(20);
        }

        javax.swing.GroupLayout mesasPanelLayout = new javax.swing.GroupLayout(mesasPanel);
        mesasPanel.setLayout(mesasPanelLayout);
        mesasPanelLayout.setHorizontalGroup(
            mesasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mesasPanelLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        mesasPanelLayout.setVerticalGroup(
            mesasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mesasPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        getContentPane().add(mesasPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 46, 570, -1));

        tituloPanel.setBackground(new java.awt.Color(102, 102, 102));
        tituloPanel.setRoundBottomLeft(50);
        tituloPanel.setRoundBottomRight(50);
        tituloPanel.setRoundTopLeft(50);
        tituloPanel.setRoundTopRight(50);

        Titulo.setBackground(new java.awt.Color(255, 255, 255));
        Titulo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 255, 255));
        Titulo.setText("Consulta Reservaciones");

        javax.swing.GroupLayout tituloPanelLayout = new javax.swing.GroupLayout(tituloPanel);
        tituloPanel.setLayout(tituloPanelLayout);
        tituloPanelLayout.setHorizontalGroup(
            tituloPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tituloPanelLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(Titulo)
                .addContainerGap(41, Short.MAX_VALUE))
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

        buscarBtn.setBackground(new java.awt.Color(102, 102, 102));
        buscarBtn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        buscarBtn.setForeground(new java.awt.Color(255, 255, 255));
        buscarBtn.setText("Buscar");
        buscarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarBtnActionPerformed(evt);
            }
        });
        Fondo.add(buscarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 620, 140, 50));

        cancelarBtn.setBackground(new java.awt.Color(102, 102, 102));
        cancelarBtn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cancelarBtn.setForeground(new java.awt.Color(255, 255, 255));
        cancelarBtn.setText("Regresar");
        cancelarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarBtnActionPerformed(evt);
            }
        });
        Fondo.add(cancelarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 540, 140, 50));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Fecha de reservación");
        Fondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Numero de telefono");
        Fondo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nombre del cliente");
        Fondo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, -1, -1));

        nombreClienteCB.setForeground(new java.awt.Color(255, 255, 255));
        Fondo.add(nombreClienteCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 330, 300, 40));

        numeroTelefonoTxt.setBackground(new java.awt.Color(86, 86, 86));
        Fondo.add(numeroTelefonoTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, 300, 40));

        jDateChooser1.setBackground(new java.awt.Color(86, 86, 86));
        jDateChooser1.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Fondo.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(343, 200, 290, 40));

        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
 /**
     * Maneja el evento de acción del botón de cancelar. Este método crea una
     * nueva instancia de la ventana de administrador y cierra la ventana
     * actual.
     *
     * @param evt El evento de acción que se produce al hacer clic en el botón
     * de cancelar.
     */
    private void cancelarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarBtnActionPerformed
        new Admistrador().setVisible(true);
        dispose();
    }//GEN-LAST:event_cancelarBtnActionPerformed
    /**
     * Maneja el evento de acción del botón de buscar. Este método aplica
     * filtros a las reservas basados en el cliente seleccionado, el número de
     * teléfono ingresado y la fecha seleccionada. Luego, actualiza la tabla con
     * las reservas filtradas.
     *
     * @param evt El evento de acción que se produce al hacer clic en el botón
     * de buscar.
     */
    private void buscarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarBtnActionPerformed
// Aplicación de filtros
        String clienteSeleccionado = nombreClienteCB.getSelectedItem() != null ? nombreClienteCB.getSelectedItem().toString() : null;
        String telefono = numeroTelefonoTxt.getText().isEmpty() ? null : numeroTelefonoTxt.getText();
        Date fechaSeleccionada = jDateChooser1.getDate(); // Puede ser null si no se selecciona fecha

        // Usar el objeto de filtros existente
        List<ReservaDTO> reservasFiltradas = filtros.filtrarReservas(clienteSeleccionado, telefono, fechaSeleccionada);

        // Actualización de tabla con reservas
        String[] columnas = {"No.Mesa", "Fecha y hora", "Tamaño de mesa", "Lugar", "Cliente"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        if (reservasFiltradas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron reservas con los filtros aplicados.");
        } else {
            for (ReservaDTO reserva : reservasFiltradas) {
                model.addRow(new Object[]{
                    reserva.getMesa().getCodigoMesa(),
                    reserva.getFechaHoraReserva(), // Asegúrate de tener el método getFechaHoraReserva() en ReservaDTO
                    reserva.getMesa().getTipoMesa(),
                    reserva.getMesa().getUbicacion(),
                    reserva.getCliente().getNombre() // Asegúrate de tener el método getNombre() en ClienteDTO
                });
            }
        }
        reservacionesTabla.setModel(model);
    }//GEN-LAST:event_buscarBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Fondo;
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton buscarBtn;
    private javax.swing.JButton cancelarBtn;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private Control.PanelRound mesasPanel;
    private javax.swing.JComboBox<String> nombreClienteCB;
    private javax.swing.JTextField numeroTelefonoTxt;
    private javax.swing.JTable reservacionesTabla;
    private Control.PanelRound tituloPanel;
    // End of variables declaration//GEN-END:variables
}
