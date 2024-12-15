package GUI;

/**
 * Clase que representa la interfaz gráfica del administrador del restaurante.
 * Extiende de JFrame y proporciona opciones para la gestión de reservas,
 * reportes, consultas, cancelaciones y la adición de mesas.
 *
 * <p>
 * Esta clase utiliza componentes gráficos personalizados, como PanelRound, para
 * crear un diseño atractivo y funcional. Incluye paneles que permiten al
 * administrador interactuar con diferentes secciones de la aplicación. </p>
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class Admistrador extends javax.swing.JFrame {

    /**
     * Crea una nueva instancia de la clase Administrador e inicializa la
     * interfaz gráfica.
     */
    public Admistrador() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new Control.PanelRound();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        reservacionPn = new Control.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        reportePn = new Control.PanelRound();
        jLabel3 = new javax.swing.JLabel();
        consultasPn = new Control.PanelRound();
        jLabel4 = new javax.swing.JLabel();
        cancelarReserva = new Control.PanelRound();
        jLabel5 = new javax.swing.JLabel();
        agregarMesaPn = new Control.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        salirPn = new Control.PanelRound();
        jLabel6 = new javax.swing.JLabel();
        panelRound2 = new Control.PanelRound();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound1.setBackground(new java.awt.Color(51, 51, 51));
        panelRound1.setRoundBottomLeft(50);
        panelRound1.setRoundBottomRight(50);
        panelRound1.setRoundTopLeft(50);
        panelRound1.setRoundTopRight(50);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Administrador del Restaurante");

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addContainerGap(148, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(129, 129, 129))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 82, 760, -1));

        jPanel1.setBackground(new java.awt.Color(29, 29, 29));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reservacionPn.setBackground(new java.awt.Color(51, 51, 51));
        reservacionPn.setCursorHandEnabled(true);
        reservacionPn.setRoundBottomLeft(50);
        reservacionPn.setRoundBottomRight(50);
        reservacionPn.setRoundTopLeft(50);
        reservacionPn.setRoundTopRight(50);
        reservacionPn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reservacionPnMouseClicked(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Reservacion");

        javax.swing.GroupLayout reservacionPnLayout = new javax.swing.GroupLayout(reservacionPn);
        reservacionPn.setLayout(reservacionPnLayout);
        reservacionPnLayout.setHorizontalGroup(
            reservacionPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservacionPnLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel2)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        reservacionPnLayout.setVerticalGroup(
            reservacionPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservacionPnLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel1.add(reservacionPn, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 370, 320, 90));

        reportePn.setBackground(new java.awt.Color(51, 51, 51));
        reportePn.setCursorHandEnabled(true);
        reportePn.setPreferredSize(new java.awt.Dimension(320, 90));
        reportePn.setRoundBottomLeft(50);
        reportePn.setRoundBottomRight(50);
        reportePn.setRoundTopLeft(50);
        reportePn.setRoundTopRight(50);
        reportePn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportePnMouseClicked(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Reporte");

        javax.swing.GroupLayout reportePnLayout = new javax.swing.GroupLayout(reportePn);
        reportePn.setLayout(reportePnLayout);
        reportePnLayout.setHorizontalGroup(
            reportePnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportePnLayout.createSequentialGroup()
                .addContainerGap(99, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(94, 94, 94))
        );
        reportePnLayout.setVerticalGroup(
            reportePnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportePnLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel1.add(reportePn, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 370, -1, -1));

        consultasPn.setBackground(new java.awt.Color(51, 51, 51));
        consultasPn.setCursorHandEnabled(true);
        consultasPn.setPreferredSize(new java.awt.Dimension(320, 90));
        consultasPn.setRoundBottomLeft(50);
        consultasPn.setRoundBottomRight(50);
        consultasPn.setRoundTopLeft(50);
        consultasPn.setRoundTopRight(50);
        consultasPn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                consultasPnMouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Consultas");

        javax.swing.GroupLayout consultasPnLayout = new javax.swing.GroupLayout(consultasPn);
        consultasPn.setLayout(consultasPnLayout);
        consultasPnLayout.setHorizontalGroup(
            consultasPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consultasPnLayout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jLabel4)
                .addContainerGap(84, Short.MAX_VALUE))
        );
        consultasPnLayout.setVerticalGroup(
            consultasPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consultasPnLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.add(consultasPn, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 480, -1, -1));

        cancelarReserva.setBackground(new java.awt.Color(51, 51, 51));
        cancelarReserva.setCursorHandEnabled(true);
        cancelarReserva.setPreferredSize(new java.awt.Dimension(320, 90));
        cancelarReserva.setRoundBottomLeft(50);
        cancelarReserva.setRoundBottomRight(50);
        cancelarReserva.setRoundTopLeft(50);
        cancelarReserva.setRoundTopRight(50);
        cancelarReserva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelarReservaMouseClicked(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Cancelar Reserva");

        javax.swing.GroupLayout cancelarReservaLayout = new javax.swing.GroupLayout(cancelarReserva);
        cancelarReserva.setLayout(cancelarReservaLayout);
        cancelarReservaLayout.setHorizontalGroup(
            cancelarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelarReservaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        cancelarReservaLayout.setVerticalGroup(
            cancelarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelarReservaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel1.add(cancelarReserva, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 470, -1, -1));

        agregarMesaPn.setBackground(new java.awt.Color(51, 51, 51));
        agregarMesaPn.setCursorHandEnabled(true);
        agregarMesaPn.setMinimumSize(new java.awt.Dimension(320, 90));
        agregarMesaPn.setRoundBottomLeft(50);
        agregarMesaPn.setRoundBottomRight(50);
        agregarMesaPn.setRoundTopLeft(50);
        agregarMesaPn.setRoundTopRight(50);
        agregarMesaPn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agregarMesaPnMouseClicked(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Agregar Mesas");

        javax.swing.GroupLayout agregarMesaPnLayout = new javax.swing.GroupLayout(agregarMesaPn);
        agregarMesaPn.setLayout(agregarMesaPnLayout);
        agregarMesaPnLayout.setHorizontalGroup(
            agregarMesaPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, agregarMesaPnLayout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(75, 75, 75))
        );
        agregarMesaPnLayout.setVerticalGroup(
            agregarMesaPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(agregarMesaPnLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel1.add(agregarMesaPn, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, 400, -1));

        salirPn.setBackground(new java.awt.Color(51, 51, 51));
        salirPn.setCursorHandEnabled(true);
        salirPn.setPreferredSize(new java.awt.Dimension(320, 90));
        salirPn.setRoundBottomLeft(50);
        salirPn.setRoundBottomRight(50);
        salirPn.setRoundTopLeft(50);
        salirPn.setRoundTopRight(50);
        salirPn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salirPnMouseClicked(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Salir");

        javax.swing.GroupLayout salirPnLayout = new javax.swing.GroupLayout(salirPn);
        salirPn.setLayout(salirPnLayout);
        salirPnLayout.setHorizontalGroup(
            salirPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salirPnLayout.createSequentialGroup()
                .addContainerGap(142, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(130, 130, 130))
        );
        salirPnLayout.setVerticalGroup(
            salirPnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salirPnLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel1.add(salirPn, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 580, 340, -1));

        panelRound2.setBackground(new java.awt.Color(51, 51, 51));
        panelRound2.setToolTipText("");
        panelRound2.setCursorHandEnabled(true);
        panelRound2.setRoundBottomLeft(100);
        panelRound2.setRoundBottomRight(100);
        panelRound2.setRoundTopLeft(100);
        panelRound2.setRoundTopRight(100);
        panelRound2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelRound2MouseClicked(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/M/ajustes2.gif"))); // NOI18N

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel9)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel9)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 20, 100, 100));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Maneja el evento de clic del mouse en el panel de reservaciones. Este
     * método abre la ventana de reservaciones y cierra la ventana actual.
     *
     * @param evt el evento del mouse que activó este método
     */
    private void reservacionPnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reservacionPnMouseClicked
        new Reservaciones().setVisible(true);
        dispose();
    }//GEN-LAST:event_reservacionPnMouseClicked

    /**
     * Maneja el evento de clic del mouse en el panel de agregar mesa. Este
     * método abre la ventana para agregar mesas y cierra la ventana actual.
     *
     * @param evt el evento del mouse que activó este método
     */
    private void agregarMesaPnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agregarMesaPnMouseClicked
        new AgregarMesas().setVisible(true);
        dispose();
    }//GEN-LAST:event_agregarMesaPnMouseClicked

    /**
     * Maneja el evento de clic del mouse en el panel de reportes. Este método
     * abre la ventana de reportes y cierra la ventana actual.
     *
     * @param evt el evento del mouse que activó este método
     */
    private void reportePnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportePnMouseClicked
        new Reportes().setVisible(true);
        dispose();
    }//GEN-LAST:event_reportePnMouseClicked

    /**
     * Maneja el evento de clic del mouse en el panel de cancelar reserva. Este
     * método abre la ventana para cancelar reservaciones y cierra la ventana
     * actual.
     *
     * @param evt el evento del mouse que activó este método
     */
    private void cancelarReservaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelarReservaMouseClicked
        new CancelarReservacion().setVisible(true);
        dispose();
    }//GEN-LAST:event_cancelarReservaMouseClicked

    /**
     * Maneja el evento de clic del mouse en el panel de consultas. Este método
     * abre la ventana de consulta de reservaciones y cierra la ventana actual.
     *
     * @param evt el evento del mouse que activó este método
     */
    private void consultasPnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_consultasPnMouseClicked
        new ConsultaReservacion().setVisible(true);
        dispose();
    }//GEN-LAST:event_consultasPnMouseClicked

    /**
     * Maneja el evento de clic del mouse en el panel de salir. Este método
     * cierra la ventana actual, finalizando así la aplicación.
     *
     * @param evt el evento del mouse que activó este método
     */
    private void salirPnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salirPnMouseClicked
        dispose();
    }//GEN-LAST:event_salirPnMouseClicked

    private void panelRound2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseClicked
        new Ajustes().setVisible(true);
        dispose();
    }//GEN-LAST:event_panelRound2MouseClicked

    /**
     * El método principal que sirve como punto de entrada para la aplicación.
     * Establece la apariencia de la aplicación y muestra la ventana principal
     * del administrador.
     *
     * @param args los argumentos de línea de comandos
     */
    public static void main(String args[]) {
        /* Establecer la apariencia Nimbus */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Admistrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admistrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admistrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admistrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Crear y mostrar la ventana */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admistrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Control.PanelRound agregarMesaPn;
    private Control.PanelRound cancelarReserva;
    private Control.PanelRound consultasPn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private Control.PanelRound panelRound1;
    private Control.PanelRound panelRound2;
    private Control.PanelRound reportePn;
    private Control.PanelRound reservacionPn;
    private Control.PanelRound salirPn;
    // End of variables declaration//GEN-END:variables
}
