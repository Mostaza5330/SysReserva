package GUI;

import BO.ReservaBO;
import DTOs.ReservaDTO;
import Fachada.ClienteFCD;
import Fachada.FiltrosFCD;
import Fachada.PdfGenerator;
import Interfaces.IReservaBO;
import interfacesFachada.IClienteFCD;
import interfacesFachada.IFiltrosFCD;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Esta clase representa una ventana GUI para generar reportes basados en
 * reservas. Extiende JFrame y proporciona funcionalidades para mostrar, filtrar
 * y generar reportes en formato PDF.
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class Reportes extends javax.swing.JFrame {

    private IFiltrosFCD filtros;
    private IClienteFCD clienteFCD;
    private List<ReservaDTO> reservas;
    private IReservaBO reservaBO;

    /**
     * Crea una nueva instancia de la clase Reportes, inicializando componentes
     * y obteniendo datos de reservas.
     */
    public Reportes() {
        initComponents();
        filtros = new FiltrosFCD(); // Inicializa la fachada de filtros
        clienteFCD = new ClienteFCD(); // Inicializa la fachada de cliente
        this.reservaBO = new ReservaBO(); // Inicializa el objeto de negocio de reservas

        // Obtiene e inicializa las reservas antes de actualizar la tabla
        obtenerReservas(); // Este método obtiene las reservas
        actualizarTablaReservas(); // Ahora puedes actualizar la tabla con los datos obtenidos
    }

    /**
     * Muestra un cuadro de diálogo de error con el mensaje dado.
     *
     * @param mensaje El mensaje de error a mostrar.
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo de éxito con el mensaje dado.
     *
     * @param mensaje El mensaje de éxito a mostrar.
     */
    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Valida las fechas de inicio y fin para el reporte de reservas.
     *
     * @return true si las fechas son válidas, false de lo contrario.
     */
    private boolean validarFechas() {
        if (fechaInicioDC.getDate().after(fechaFinDC.getDate())) {
            mostrarError("La fecha de inicio no puede ser posterior a la fecha de fin.");
            return false;
        }
        return true;
    }

    /**
     * Obtiene la lista de reservas de la base de datos usando el objeto de
     * negocio.
     *
     * @return Una lista de Objetos de Transferencia de Datos de Reserva
     * (ReservaDTO).
     */
    private List<ReservaDTO> obtenerReservas() {
        try {
            // Calls reservaBO to obtain the reservations from the database.
            this.reservas = reservaBO.obtenerReservas();
        } catch (Exception e) {
            mostrarError("Error al obtener las reservas: " + e.getMessage());
        }
        return reservas;
    }

    /**
     * Actualiza la tabla que muestra las reservas con los datos actuales.
     */
    private void actualizarTablaReservas() {
        String[] columnas = {"No.Mesa", "Fecha y hora", "Tamaño de mesa", "Lugar", "Cliente"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (ReservaDTO reserva : reservas) {
            model.addRow(new Object[]{
                reserva.getMesa().getCodigoMesa(),
                reserva.getFechaHoraReserva(),
                reserva.getMesa().getTipoMesa(),
                reserva.getMesa().getUbicacion(),
                reserva.getCliente().getNombre()
            });
        }
        tblMesas.setModel(model);
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
        generarPDFBtn = new javax.swing.JButton();
        atrasBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        ubicacionTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        fechaInicioDC = new com.toedter.calendar.JDateChooser();
        fechaFinDC = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tipoMesaTxt = new javax.swing.JTextField();
        buscarBtn = new javax.swing.JButton();
        panelGlowingGradient1 = new Control.PanelGlowingGradient();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMesas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(25, 25, 25));
        setMaximumSize(new java.awt.Dimension(1290, 730));
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 720));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tituloPanel.setBackground(new java.awt.Color(201, 60, 32));
        tituloPanel.setRoundBottomLeft(50);
        tituloPanel.setRoundBottomRight(50);
        tituloPanel.setRoundTopLeft(50);
        tituloPanel.setRoundTopRight(50);

        Titulo.setBackground(new java.awt.Color(255, 255, 255));
        Titulo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 255, 255));
        Titulo.setText("Generacion de Reportes");

        javax.swing.GroupLayout tituloPanelLayout = new javax.swing.GroupLayout(tituloPanel);
        tituloPanel.setLayout(tituloPanelLayout);
        tituloPanelLayout.setHorizontalGroup(
            tituloPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tituloPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(Titulo)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        tituloPanelLayout.setVerticalGroup(
            tituloPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tituloPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(Titulo)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        getContentPane().add(tituloPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        generarPDFBtn.setBackground(new java.awt.Color(201, 60, 32));
        generarPDFBtn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        generarPDFBtn.setForeground(new java.awt.Color(255, 255, 255));
        generarPDFBtn.setText("Generar PDF");
        generarPDFBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarPDFBtnActionPerformed(evt);
            }
        });
        getContentPane().add(generarPDFBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 591, 180, 60));

        atrasBtn.setBackground(new java.awt.Color(201, 60, 32));
        atrasBtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        atrasBtn.setForeground(new java.awt.Color(255, 255, 255));
        atrasBtn.setText("Regresar");
        atrasBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atrasBtnActionPerformed(evt);
            }
        });
        getContentPane().add(atrasBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 523, 134, 50));

        jPanel1.setBackground(new java.awt.Color(12, 11, 29));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Hasta el");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, -1, -1));

        ubicacionTxt.setBackground(new java.awt.Color(201, 60, 32));
        ubicacionTxt.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(ubicacionTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 320, 220, 40));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Reporte del");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        fechaInicioDC.setBackground(new java.awt.Color(12, 11, 29));
        fechaInicioDC.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jPanel1.add(fechaInicioDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 130, 40));

        fechaFinDC.setBackground(new java.awt.Color(12, 11, 29));
        fechaFinDC.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jPanel1.add(fechaFinDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 190, 130, 40));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tipo de mesa");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Ubicacion");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, 30));

        tipoMesaTxt.setBackground(new java.awt.Color(201, 60, 32));
        tipoMesaTxt.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(tipoMesaTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 220, 40));

        buscarBtn.setBackground(new java.awt.Color(201, 60, 32));
        buscarBtn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        buscarBtn.setForeground(new java.awt.Color(255, 255, 255));
        buscarBtn.setText("Buscar");
        buscarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarBtnActionPerformed(evt);
            }
        });
        jPanel1.add(buscarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 140, 50));

        panelGlowingGradient1.setBackground(new java.awt.Color(12, 11, 29));
        panelGlowingGradient1.setBackgroundLight(new java.awt.Color(12, 11, 29));
        panelGlowingGradient1.setGradientColor2(new java.awt.Color(201, 60, 32));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Reservaciones");
        panelGlowingGradient1.add(jLabel5);
        jLabel5.setBounds(280, 30, 160, 32);

        tblMesas.setBackground(new java.awt.Color(12, 11, 29));
        tblMesas.setForeground(new java.awt.Color(255, 255, 255));
        tblMesas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No.Mesa", "Tamaño de mesa", "Cliente", "Lugar", "Fehca de reservacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblMesas);

        panelGlowingGradient1.add(jScrollPane1);
        jScrollPane1.setBounds(30, 70, 640, 550);

        jPanel1.add(panelGlowingGradient1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 700, 650));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void atrasBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atrasBtnActionPerformed
        new Admistrador().setVisible(true);
        dispose();

    }//GEN-LAST:event_atrasBtnActionPerformed
    /**
     * Maneja la acción de generar el informe en PDF cuando se hace clic en el
     * botón. Valida las fechas y genera el informe si son válidas.
     *
     * @param evt El evento de acción desencadenado al hacer clic en el botón.
     */
    private void generarPDFBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarPDFBtnActionPerformed
        try {
            // Validar fechas y campos obligatorios
            if (!validarFechas()) {
                return;
            }

            // Formatear fechas
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaInicio = dateFormat.format(fechaInicioDC.getDate());
            String fechaFin = dateFormat.format(fechaFinDC.getDate());

            // Obtener valores de campos de texto
            String tipoMesa = tipoMesaTxt.getText().trim();
            String ubicacion = ubicacionTxt.getText().trim();

            // Obtener reservas filtradas
            List<ReservaDTO> reservasFiltradas = obtenerReservas();

            // Llamar a la clase PdfGenerator para crear el PDF
            PdfGenerator pdfGenerator = new PdfGenerator();
            boolean exito = pdfGenerator.generarPDFDesdeFormulario(fechaInicio, fechaFin, tipoMesa, ubicacion, reservasFiltradas);

            // Mostrar mensaje según el resultado
            if (exito) {
                mostrarExito("Reporte generado exitosamente.");
            } else {
                mostrarError("Error al generar el reporte.");
            }
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }

    }//GEN-LAST:event_generarPDFBtnActionPerformed
    /**
     * Maneja la acción de buscar reservas filtradas según los criterios
     * especificados en el formulario. Obtiene los filtros y actualiza la tabla
     * con las reservas que coinciden.
     *
     * @param evt El evento de acción desencadenado al hacer clic en el botón de
     * búsqueda.
     */
    private void buscarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarBtnActionPerformed
        try {
            // Obtener los filtros del formulario
            String tipoMesa = tipoMesaTxt.getText().trim();
            String ubicacion = ubicacionTxt.getText().trim();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaInicio = dateFormat.format(fechaInicioDC.getDate());
            String fechaFin = dateFormat.format(fechaFinDC.getDate());

            // Filtrar las reservas usando la clase FiltrosFCD y el nuevo método
            FiltrosFCD filtros = new FiltrosFCD();
            List<ReservaDTO> reservasFiltradas = filtros.filtrarReservasPorMesaUbicacionFecha(tipoMesa, ubicacion, fechaInicio, fechaFin);

            // Actualizar la tabla con las reservas filtradas
            reservas = reservasFiltradas;
            actualizarTablaReservas();
        } catch (Exception e) {
            mostrarError("Error al filtrar las reservas: " + e.getMessage());
        }

    }//GEN-LAST:event_buscarBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton atrasBtn;
    private javax.swing.JButton buscarBtn;
    private com.toedter.calendar.JDateChooser fechaFinDC;
    private com.toedter.calendar.JDateChooser fechaInicioDC;
    private javax.swing.JButton generarPDFBtn;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private Control.PanelGlowingGradient panelGlowingGradient1;
    private javax.swing.JTable tblMesas;
    private javax.swing.JTextField tipoMesaTxt;
    private Control.PanelRound tituloPanel;
    private javax.swing.JTextField ubicacionTxt;
    // End of variables declaration//GEN-END:variables
}
