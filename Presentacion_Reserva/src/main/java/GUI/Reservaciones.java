package GUI;

import BO.RestauranteBO;
import DTOs.ClienteDTO;
import DTOs.MesaDTO;
import DTOs.RestauranteDTO;
import Excepciones.BOException;
import Excepciones.FacadeException;
import Fachada.ClienteFCD;
import Fachada.HorarioRestauranteFCD;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
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
    // Ejemplo de cómo formatear correctamente

    public Reservaciones() {
        initComponents();
        System.out.println("Inicializando componentes de la ventana...");
        this.clienteFCD = new ClienteFCD();
        this.mesaFCD = new MesaFCD();
        this.reservaFCD = new ReservaFCD();
        System.out.println("Fachadas inicializadas correctamente.");

        cargarDatosIniciales();
        cargarHorasDisponibles();
        poblarHoras();

    }
// Método para poblar el ComboBox con el formato correcto
    private void poblarHoras() {
        cbxHoras.removeAllItems(); // Limpiar items existentes

        // Genera horas desde la apertura hasta el cierre
        LocalTime horaInicio = LocalTime.of(11, 0); // Por ejemplo, desde las 11:00 AM
        LocalTime horaCierre = LocalTime.of(22, 0); // Por ejemplo, hasta las 10:00 PM

        while (horaInicio.isBefore(horaCierre) || horaInicio.equals(horaCierre)) {
            // Formatea la hora en el formato específico
            String horaFormateada = horaInicio.format(DateTimeFormatter.ofPattern("hh:mm a"));
            cbxHoras.addItem(horaFormateada);

            // Incrementa en intervalos de 30 minutos
            horaInicio = horaInicio.plusMinutes(30);
        }
    }

    private void limpiarCampos() {
        System.out.println("Limpiando campos de entrada...");
        cbxClientes.setSelectedIndex(0);
        cbxNumPersonas.setSelectedIndex(0);
        cbxHoras.setSelectedIndex(0);
        dcFecha.setDate(null);
        txtCosto.setText("");
        tblMesas.clearSelection();
        System.out.println("Campos limpiados.");
    }

    private void cargarDatosIniciales() {
        try {
            System.out.println("Cargando datos iniciales de clientes y mesas...");
            clienteFCD.cargarComboBoxClientes(cbxClientes);
            mesaFCD.cargarTablaMesas(tblMesas);
            System.out.println("Datos iniciales cargados correctamente.");
        } catch (FacadeException fe) {
            System.err.println("Error al cargar los datos iniciales:");
            fe.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de inicio: " + fe.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarHorasDisponibles() {
        try {
            System.out.println("Cargando horarios disponibles...");
            RestauranteBO restauranteBO = new RestauranteBO();
            RestauranteDTO restaurante = restauranteBO.consultar();

            HorarioRestauranteFCD horarioFCD = new HorarioRestauranteFCD();
            List<String> horariosDisponibles = horarioFCD.obtenerHorariosDisponibles(restaurante);

            cbxHoras.removeAllItems();
            for (String horario : horariosDisponibles) {
                cbxHoras.addItem(horario);
            }
            System.out.println("Horarios disponibles cargados correctamente.");
        } catch (BOException e) {
            System.err.println("Error al cargar los horarios disponibles:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los horarios: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean VerificarDatos() {
        System.out.println("Verificando datos de entrada...");
        if (dcFecha.getDate() == null) {
            System.out.println("Fecha no seleccionada.");
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fecha.");
            return false;
        } else if (tblMesas.getSelectedRow() == -1) {
            System.out.println("Mesa no seleccionada.");
            JOptionPane.showMessageDialog(this, "Selecciona una mesa antes de continuar.");
            return false;
        }
        System.out.println("Datos de entrada verificados correctamente.");
        return true;
    }

    private MesaDTO formarMesa() {
        try {
            System.out.println("Formando objeto MesaDTO desde la selección de la tabla...");
            int selectedRow = tblMesas.getSelectedRow();
            if (selectedRow == -1) {
                throw new IllegalStateException("No se seleccionó ninguna mesa.");
            }

            String codigoMesa = tblMesas.getValueAt(selectedRow, 0).toString();
            String tipoMesa = tblMesas.getValueAt(selectedRow, 1).toString();
            int capacidadMinima = Integer.parseInt(tblMesas.getValueAt(selectedRow, 2).toString());
            int capacidadMaxima = Integer.parseInt(tblMesas.getValueAt(selectedRow, 3).toString());
            String ubicacion = tblMesas.getValueAt(selectedRow, 4).toString();

            MesaDTO mesa = new MesaDTO(codigoMesa, tipoMesa, capacidadMinima, capacidadMaxima, ubicacion);
            System.out.println("MesaDTO formada: " + mesa);
            return mesa;
        } catch (Exception e) {
            System.err.println("Error al formar el objeto MesaDTO:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al formar la mesa seleccionada: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Forma una fecha y hora a partir de la fecha seleccionada en un componente
     * {@link JDateChooser} y la hora seleccionada en un {@link JComboBox}.
     *
     * Este método valida que la hora haya sido seleccionada correctamente y
     * combina la fecha y la hora en un único objeto {@link LocalDateTime}. Si
     * ocurre algún error, muestra un mensaje de error al usuario y retorna
     * {@code null}.
     *
     * @return Un objeto {@link LocalDateTime} que representa la combinación de
     * la fecha y la hora seleccionadas. Si ocurre un error, retorna
     * {@code null}.
     */
    private LocalDateTime formarFechaHora() {
        try {
            System.out.println("Formando fecha y hora seleccionadas...");

            // Obtiene la hora seleccionada desde el JComboBox
            String horaSeleccionada = (String) cbxHoras.getSelectedItem();

            // Verifica si la hora ha sido seleccionada
            if (horaSeleccionada == null || horaSeleccionada.isEmpty()) {
                throw new IllegalStateException("Hora no seleccionada.");
            }

            // Crea un formateador de fecha y hora específicamente para 12-hour format with AM/PM
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

            // Convierte la hora seleccionada a un objeto LocalTime
            LocalTime time = LocalTime.parse(horaSeleccionada, formatter);

            // Convierte la fecha seleccionada en el JDateChooser a un objeto LocalDate
            Instant instant = dcFecha.getDate().toInstant();
            LocalDate fecha = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            // Combina la fecha y la hora en un objeto LocalDateTime
            LocalDateTime fechaHora = fecha.atTime(time);

            System.out.println("Fecha y hora formadas correctamente: " + fechaHora);

            return fechaHora;

        } catch (DateTimeParseException e) {
            System.err.println("Error al parsear la hora: " + e.getMessage());

            JOptionPane.showMessageDialog(this,
                    "Error: El formato de hora no es válido. Asegúrese de seleccionar una hora.",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);

            return null;

        } catch (Exception e) {
            System.err.println("Error al formar la fecha y hora:");
            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Error al formar la fecha y hora: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Fondo = new javax.swing.JPanel();
        confirmarBtn = new javax.swing.JButton();
        cancelarBtn = new javax.swing.JButton();
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
        panelGlowingGradient1 = new Control.PanelGlowingGradient();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMesas = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

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
        cancelarBtn.setText("Regresar");
        cancelarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarBtnActionPerformed(evt);
            }
        });
        Fondo.add(cancelarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 650, 140, 50));

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
        txtCosto.setForeground(new java.awt.Color(255, 255, 255));
        txtCosto.setBorder(null);
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

        panelGlowingGradient1.setBackground(new java.awt.Color(12, 11, 29));
        panelGlowingGradient1.setBackgroundLight(new java.awt.Color(12, 11, 29));
        panelGlowingGradient1.setGradientColor2(new java.awt.Color(201, 60, 32));

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

        panelGlowingGradient1.add(jScrollPane1);
        jScrollPane1.setBounds(26, 70, 460, 500);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mesas para la reservacion");
        panelGlowingGradient1.add(jLabel3);
        jLabel3.setBounds(120, 30, 281, 32);

        Fondo.add(panelGlowingGradient1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 80, 510, 600));

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
            System.out.println("Iniciando proceso de reservación...");

            if (VerificarDatos()) {
                System.out.println("Datos verificados correctamente.");

                // Recolectamos los datos
                ClienteDTO cliente = (ClienteDTO) cbxClientes.getSelectedItem();
                System.out.println("Cliente seleccionado: " + cliente);

                MesaDTO mesa = formarMesa();
                System.out.println("Mesa formada: " + mesa);

                LocalDateTime fechaHora = formarFechaHora();
                System.out.println("Fecha y hora obtenidas: " + fechaHora);

                if (fechaHora == null) {
                    System.err.println("Error: Fecha y hora no válidas.");
                    JOptionPane.showMessageDialog(this, "Por favor seleccione una fecha y hora válidas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar que la fecha no sea anterior a la actual
                if (fechaHora.isBefore(LocalDateTime.now())) {
                    System.err.println("Error: Fecha anterior a la actual.");
                    JOptionPane.showMessageDialog(this, "No se pueden hacer reservaciones para fechas pasadas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar si el restaurante estará abierto
                RestauranteBO restauranteBO = new RestauranteBO();
                if (!restauranteBO.estaAbierto(fechaHora.toLocalTime())) {
                    System.err.println("Error: Restaurante cerrado en horario seleccionado.");
                    JOptionPane.showMessageDialog(this,
                            "El restaurante estará cerrado en ese horario",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Obtenemos el número de personas
                String num = (String) cbxNumPersonas.getSelectedItem();
                System.out.println("Número de personas seleccionado: " + num);

                int numPersonas = Integer.parseInt(num);
                System.out.println("Número de personas parseado: " + numPersonas);

                // Validar capacidad de la mesa
                if (numPersonas > mesa.getCapacidadMaxima() || numPersonas < mesa.getCapacidadMinima()) {
                    System.err.println("Error: Número de personas no es adecuado para la mesa seleccionada.");
                    JOptionPane.showMessageDialog(this,
                            "El número de personas no es adecuado para esta mesa. "
                            + "Capacidad: " + mesa.getCapacidadMinima() + " - " + mesa.getCapacidadMaxima() + " personas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Obtenemos el costo
                double costo = Double.parseDouble(txtCosto.getText());
                System.out.println("Costo parseado: " + costo);

                // Calcular tiempo hasta cierre
                long tiempoHastaCierre = restauranteBO.tiempoHastaCierre(fechaHora.toLocalTime());
                System.out.println("Tiempo hasta el cierre: " + tiempoHastaCierre + " minutos");

                if (tiempoHastaCierre < 90) { // 90 minutos mínimo para una reservación
                    System.err.println("Error: Hora muy cercana al cierre del restaurante.");
                    JOptionPane.showMessageDialog(this,
                            "La hora seleccionada está muy cerca del cierre del restaurante",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Guardamos la reserva
                reservaFCD.agregarReserva(cliente, mesa, fechaHora, numPersonas, costo);
                System.out.println("Reservación guardada correctamente.");

                JOptionPane.showMessageDialog(this,
                        "Reservación realizada con éxito",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Actualizar la tabla de mesas después de la reservación
                mesaFCD.cargarTablaMesas(tblMesas);
                System.out.println("Tabla de mesas actualizada.");

                // Limpiar campos después de la reservación exitosa
                limpiarCampos();
                System.out.println("Campos limpiados.");
            }
        } catch (BOException be) {
            System.err.println("Error en la lógica de negocio: " + be.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error en la lógica de negocio: " + be.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FacadeException fe) {
            System.err.println("Error al procesar la reservación: " + fe.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error al procesar la reservación: " + fe.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            System.err.println("Error en el formato del costo: " + nfe.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error en el formato del costo",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
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
    private Control.PanelGlowingGradient panelGlowingGradient1;
    private Control.PanelRound panelRound1;
    private javax.swing.JTable tblMesas;
    private javax.swing.JTextField txtCosto;
    // End of variables declaration//GEN-END:variables
}
