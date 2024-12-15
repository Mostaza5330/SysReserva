package GUI;

import DTOs.ClienteDTO;
import DTOs.MesaDTO;
import Excepciones.FacadeException;
import Fachada.ClienteFCD;
import Fachada.MesaFCD;
import Fachada.ReservaFCD;
import interfacesFachada.IClienteFCD;
import interfacesFachada.IMesaFCD;
import interfacesFachada.IReservaFCD;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 * Clase para la ventana principal del sistema de reservas.
 *
 * Esta clase se encarga de gestionar la interfaz gráfica y las interacciones
 * relacionadas con las reservaciones de mesas en el sistema. Permite a los
 * usuarios seleccionar un cliente, una mesa, una fecha y una hora para realizar
 * la reserva. También maneja la carga de datos iniciales y la validación de
 * entrada.
 * 
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class Reservaciones extends javax.swing.JFrame {

    private IClienteFCD clienteFCD; // Fachada para gestionar clientes
    private IMesaFCD mesaFCD; // Fachada para gestionar mesas
    private IReservaFCD reservaFCD; // Fachada para gestionar reservaciones

    /**
     * Constructor de la clase Reservaciones. Inicializa los componentes de la
     * ventana y carga los datos iniciales de clientes y mesas.
     */
    public Reservaciones() {
        initComponents();

        this.clienteFCD = new ClienteFCD();
        this.mesaFCD = new MesaFCD();
        this.reservaFCD = new ReservaFCD();

        cargarDatosIniciales();
    }

    /**
     * Carga los datos iniciales en los componentes de la interfaz. Se cargan
     * los clientes en el combo box y las mesas en la tabla.
     */
    private void cargarDatosIniciales() {
        try {
            clienteFCD.cargarComboBoxClientes(cbxClientes);
            mesaFCD.cargarTablaMesas(tblMesas);
        } catch (FacadeException fe) {
            JOptionPane.showMessageDialog(this, "Error al cargar los "
                    + "datos de inicio", "Error", JOptionPane.ERROR);
        }
    }

    /**
     * Verifica que no haya datos nulos en los campos de entrada.
     *
     * @return true si todos los datos son válidos; false en caso contrario.
     */
    private boolean VerificarDatos() {
        if (dcFecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una "
                    + "fecha");
            return false;
        } else if (tblMesas.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una mesa antes "
                    + "de continuar");
            return false; // Añadido retorno false en caso de no seleccionar mesa
        }
        
        return true;
    }

    /**
     * Forma un objeto MesaDTO a partir de la mesa seleccionada en la tabla.
     *
     * @return un objeto MesaDTO con la información de la mesa seleccionada.
     */
    private MesaDTO formarMesa() {
        int selectedRow = tblMesas.getSelectedRow();

        String codigoMesa = tblMesas.getValueAt(selectedRow, 0).toString(); // Cambia el índice según tu tabla
        String tipoMesa = tblMesas.getValueAt(selectedRow, 1).toString();   // Cambia el índice según tu tabla
        int capacidadMinima = Integer.parseInt(tblMesas.getValueAt(selectedRow, 2).toString()); // Cambia el índice según tu tabla
        int capacidadMaxima = Integer.parseInt(tblMesas.getValueAt(selectedRow, 3).toString()); // Cambia el índice según tu tabla
        String ubicacion = tblMesas.getValueAt(selectedRow, 4).toString();   // Cambia el índice según tu tabla

        MesaDTO mesa = new MesaDTO(codigoMesa, tipoMesa, capacidadMinima,
                capacidadMaxima, ubicacion);

        // retornamos la mesa.
        return mesa;
    }

    /**
     * Forma un objeto LocalDateTime a partir de la fecha y hora seleccionadas.
     *
     * @return un objeto LocalDateTime que representa la fecha y hora
     * seleccionadas por el usuario.
     */
    private LocalDateTime formarFechaHora() {
        // convertimos la fecha del selector de fechas a instant
        Instant instant = dcFecha.getDate().toInstant();

        // convertir Instant a LocalDate en la zona horaria del sistema
        LocalDate fecha = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        LocalTime time = LocalTime.parse((String) cbxHoras.getSelectedItem());

        // formamos el LocalDateTime
        LocalDateTime horaFecha = fecha.atTime(time);

        // devolvemos el LocalDateTime formado
        return horaFecha;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Fondo = new javax.swing.JPanel();
        confirmarBtn = new javax.swing.JButton();
        cancelarBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbxClientes = new javax.swing.JComboBox<>();
        panelRound1 = new Control.PanelRound();
        Titulo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        cbxHoras = new javax.swing.JComboBox<>();
        cbxNumPersonas = new javax.swing.JComboBox<>();
        dcFecha = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMesas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Fondo.setBackground(new java.awt.Color(12, 11, 29));
        Fondo.setForeground(new java.awt.Color(51, 51, 51));
        Fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        confirmarBtn.setBackground(new java.awt.Color(201, 60, 32));
        confirmarBtn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        confirmarBtn.setForeground(new java.awt.Color(255, 255, 255));
        confirmarBtn.setText("Confirmar");
        confirmarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarBtnActionPerformed(evt);
            }
        });
        Fondo.add(confirmarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 650, 140, 50));

        cancelarBtn.setBackground(new java.awt.Color(201, 60, 32));
        cancelarBtn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cancelarBtn.setForeground(new java.awt.Color(255, 255, 255));
        cancelarBtn.setText("Cancelar");
        cancelarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarBtnActionPerformed(evt);
            }
        });
        Fondo.add(cancelarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 650, 140, 50));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mesas para la reservacion");
        Fondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 90, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Costo:");
        Fondo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 510, -1, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Cliente:");
        Fondo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, -1, -1));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Numero de personas:");
        Fondo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, -1, -1));

        cbxClientes.setBackground(new java.awt.Color(201, 60, 32));
        cbxClientes.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Fondo.add(cbxClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 420, 300, 40));

        panelRound1.setBackground(new java.awt.Color(201, 60, 32));
        panelRound1.setRoundBottomLeft(50);
        panelRound1.setRoundBottomRight(50);
        panelRound1.setRoundTopLeft(50);
        panelRound1.setRoundTopRight(50);

        Titulo.setBackground(new java.awt.Color(255, 255, 255));
        Titulo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 255, 255));
        Titulo.setText("Reservaciones");

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(Titulo)
                .addGap(73, 73, 73))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Titulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Fondo.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 380, 60));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Hora de reservación:");
        Fondo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Fecha de reservación:");
        Fondo.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        txtCosto.setEditable(false);
        txtCosto.setBackground(new java.awt.Color(12, 11, 29));
        txtCosto.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Fondo.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 510, 300, 40));

        cbxHoras.setBackground(new java.awt.Color(201, 60, 32));
        cbxHoras.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Fondo.add(cbxHoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 300, 40));

        cbxNumPersonas.setBackground(new java.awt.Color(201, 60, 32));
        cbxNumPersonas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxNumPersonas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
        Fondo.add(cbxNumPersonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, 300, 40));

        dcFecha.setBackground(new java.awt.Color(12, 11, 29));
        dcFecha.setForeground(new java.awt.Color(255, 255, 255));
        Fondo.add(dcFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, 300, 40));

        tblMesas.setBackground(new java.awt.Color(12, 11, 29));
        tblMesas.setForeground(new java.awt.Color(255, 255, 255));
        tblMesas.setModel(new javax.swing.table.DefaultTableModel(
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
        tblMesas.setGridColor(new java.awt.Color(201, 60, 32));
        tblMesas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMesasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMesas);

        Fondo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 140, -1, 500));

        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
/**
     * Maneja la acción del botón de confirmar reserva. Este método verifica que
     * los datos ingresados sean válidos y, si es así, recolecta los datos
     * necesarios para realizar una reserva. Luego, utiliza el objeto de fachada
     * {@code reservaFCD} para agregar la reserva.
     *
     * @param evt El evento de acción generado por el botón de confirmar.
     */
    private void confirmarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarBtnActionPerformed
        try {
            if (VerificarDatos()) {
                // Recolectamos los datos
                ClienteDTO cliente = (ClienteDTO) cbxClientes.getSelectedItem();
                // Obtiene la mesa seleccionada en la tabla.
                MesaDTO mesa = formarMesa();
                // Obtenemos la fecha y la hora.
                LocalDateTime fechaHora = formarFechaHora();
                // Obtenemos el número de personas
                String num = (String) cbxNumPersonas.getSelectedItem();
                int numPersonas = Integer.parseInt(num);
                // Obtenemos el costo
                double costo = Double.parseDouble(txtCosto.getText());

                // Guardamos la reserva
                reservaFCD.agregarReserva(cliente, mesa, fechaHora, numPersonas, costo);
                
            }
        } catch (FacadeException fe) {
            JOptionPane.showMessageDialog(this, fe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_confirmarBtnActionPerformed
    /**
     * Maneja la acción del botón de cancelar reserva. Este método cierra la
     * ventana actual y muestra la ventana del administrador.
     *
     * @param evt El evento de acción generado por el botón de cancelar.
     */
    private void cancelarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarBtnActionPerformed
        new Admistrador().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cancelarBtnActionPerformed

    /**
     * Maneja el evento de clic en la tabla de mesas. Este método obtiene la
     * fila seleccionada por el usuario en la tabla, determina el tipo de mesa y
     * calcula su precio, luego muestra el costo calculado en el campo de costo.
     *
     * @param evt El evento de clic del mouse en la tabla de mesas.
     */
    private void tblMesasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMesasMouseClicked
        // Obtener la fila seleccionada por el usuario en la tabla
        int selectedRow = tblMesas.getSelectedRow();

        // Obtener el modelo de la tabla
        TableModel model = tblMesas.getModel();

        // Especificar el índice de la columna que quieres obtener
        // (por ejemplo, columna 1 para "Tipo")
        int column = 1;

        // Obtener el valor de la celda en la fila y columna seleccionada
        Object cellValue = model.getValueAt(selectedRow, column);

        String tipo = cellValue.toString();

        // Calcular el precio de la mesa seleccionada
        String resultado = mesaFCD.calcularPrecio(tipo);

        // Mostrar el costo calculado en el campo de texto
        txtCosto.setText(resultado);
        txtCosto.repaint();
    }//GEN-LAST:event_tblMesasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Fondo;
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton cancelarBtn;
    private javax.swing.JComboBox<String> cbxClientes;
    private javax.swing.JComboBox<String> cbxHoras;
    private javax.swing.JComboBox<String> cbxNumPersonas;
    private javax.swing.JButton confirmarBtn;
    private com.toedter.calendar.JDateChooser dcFecha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private Control.PanelRound panelRound1;
    private javax.swing.JTable tblMesas;
    private javax.swing.JTextField txtCosto;
    // End of variables declaration//GEN-END:variables
}
