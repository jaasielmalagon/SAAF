package views;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import objects.Solicitud;
import objects.Usuario;
import services.solicitudes_service;

/**
 *
 * @author Root
 */
public class Solicitudes extends javax.swing.JDialog {

    private final solicitudes_service servicio;
    private Solicitud SOLICITUD_SELECCIONADA = null;
    private Usuario USUARIO = null;

    public Solicitudes(java.awt.Frame parent, boolean modal, Usuario usuario, String modulo) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        this.servicio = new solicitudes_service(modulo);
        this.USUARIO = usuario;
        this.llenarCombos();
        llenarTabla();
        seleccionarDeTabla();
    }

    private void llenarCombos() {
        cmbPlazo.setModel(this.servicio.comboPlazo());
        cmbFecha.setModel(this.servicio.comboFecha());
        cmbMonto.setModel(this.servicio.comboMonto());
        cmbStatus.setModel(this.servicio.comboStatus());
        cmbAdc.setModel(this.servicio.comboAdc(this.USUARIO));
    }

    private void seleccionarDeTabla() {
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                if (Mouse_evt.getClickCount() == 1) {
                    try {
                        int idSolicitud = Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0).toString());

                        SOLICITUD_SELECCIONADA = servicio.solicitud(USUARIO, idSolicitud);
                        if (SOLICITUD_SELECCIONADA != null) {
                            int aprobacion = JOptionPane.showConfirmDialog(rootPane, "Autorizar esta solicitud creará la tabla de pagos \nautomáticamente y generará el préstamo internamente.\n¿Aceptar?", "¿Autorizar solicitud?", JOptionPane.YES_NO_CANCEL_OPTION);
                            String mensaje = "Operación cancelada";
                            if (aprobacion < 2) {
                                if (aprobacion == JOptionPane.YES_OPTION) {
                                    SOLICITUD_SELECCIONADA.setESTADO(2);
                                } else if (aprobacion == JOptionPane.NO_OPTION) {
                                    SOLICITUD_SELECCIONADA.setESTADO(0);
                                }
                                mensaje = servicio.aprobacionSolicitud(SOLICITUD_SELECCIONADA,USUARIO);
                            }
                            JOptionPane.showMessageDialog(rootPane, mensaje);
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println(".mousePressed() : " + ex);
                    }
                }
            }
        });
    }

    private void llenarTabla() {
        tabla = this.servicio.tablaSolicitudes(tabla, this.USUARIO, null);
    }

    private void busquedaFiltrada() {
        Object[] obj = new Object[5];
        obj[0] = cmbAdc.getSelectedItem();
        obj[1] = cmbPlazo.getSelectedItem();
        obj[2] = cmbMonto.getSelectedItem();
        obj[3] = cmbStatus.getSelectedItem();
        obj[4] = cmbFecha.getSelectedItem();
        tabla = this.servicio.tablaSolicitudes(tabla, this.USUARIO, obj);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelPrincipal = new javax.swing.JPanel();
        BarraSuperior = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        tituloVentana = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Contenedor = new javax.swing.JPanel();
        panelFormulario = new javax.swing.JPanel();
        cmbPlazo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbFecha = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cmbMonto = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cmbAdc = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        panelTabla = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelPrincipal.setBackground(new java.awt.Color(244, 0, 100));
        PanelPrincipal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PanelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BarraSuperior.setBackground(new java.awt.Color(189, 0, 53));
        BarraSuperior.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Multiply_white_32px.png"))); // NOI18N
        btnCerrar.setBorder(null);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Multiply_32px.png"))); // NOI18N
        btnCerrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Multiply_32px.png"))); // NOI18N
        btnCerrar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Multiply_32px.png"))); // NOI18N
        btnCerrar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Multiply_32px.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        BarraSuperior.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1167, 0, 32, 32));

        tituloVentana.setFont(new java.awt.Font("Solomon Sans Book", 1, 24)); // NOI18N
        tituloVentana.setForeground(new java.awt.Color(255, 255, 255));
        tituloVentana.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloVentana.setText("Registro de datos personales");
        BarraSuperior.add(tituloVentana, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 85));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cerrar.png"))); // NOI18N
        jLabel11.setToolTipText("Cerrar");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        BarraSuperior.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1312, 0, -1, 47));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/menu-logo.png"))); // NOI18N
        BarraSuperior.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(895, 0, 304, 85));

        PanelPrincipal.add(BarraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 85));

        Contenedor.setBackground(new java.awt.Color(255, 245, 250));
        Contenedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingrese los datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        cmbPlazo.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbPlazo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Seleccione ---", "20", "24" }));
        cmbPlazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPlazoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel3.setText("Filtrar por monto:");

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel4.setText("Filtrar por fecha:");

        cmbFecha.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbFecha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Seleccione ---", "Descendente", "Ascendente" }));
        cmbFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFechaActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel20.setText("Filtrar por plazo:");

        cmbMonto.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbMonto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--", "1000", "1500", "2000", "2500", "3000", "3500", "4000", "4500", "5000", "5500", "6000", "6500", "7000", "7500", "8000", "8500", "9000", "9500", "10000" }));
        cmbMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMontoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel6.setText("Filtrar por ADC:");

        cmbAdc.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbAdc.setEnabled(false);
        cmbAdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAdcActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel5.setText("Filtrar por estatus:");

        cmbStatus.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Seleccione ---", "Aprobado", "Pendiente", "Rechazado" }));
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFormularioLayout = new javax.swing.GroupLayout(panelFormulario);
        panelFormulario.setLayout(panelFormularioLayout);
        panelFormularioLayout.setHorizontalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormularioLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormularioLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbFecha, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormularioLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(67, 67, 67)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbAdc, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(170, Short.MAX_VALUE))
        );

        panelFormularioLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel20, jLabel3, jLabel4, jLabel5, jLabel6});

        panelFormularioLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbAdc, cmbFecha, cmbMonto, cmbPlazo, cmbStatus});

        panelFormularioLayout.setVerticalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cmbPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cmbAdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelFormularioLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbAdc, cmbFecha, cmbMonto, cmbPlazo, cmbStatus, jLabel20, jLabel3, jLabel4, jLabel5, jLabel6});

        Contenedor.add(panelFormulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 920, -1));

        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Solicitudes de préstamo", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabla);

        javax.swing.GroupLayout panelTablaLayout = new javax.swing.GroupLayout(panelTabla);
        panelTabla.setLayout(panelTablaLayout);
        panelTablaLayout.setHorizontalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1128, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTablaLayout.setVerticalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addContainerGap())
        );

        Contenedor.add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 155, 1160, 370));

        PanelPrincipal.add(Contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1200, 530));

        getContentPane().add(PanelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void cmbPlazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPlazoActionPerformed
        this.busquedaFiltrada();
    }//GEN-LAST:event_cmbPlazoActionPerformed

    private void cmbFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFechaActionPerformed
        this.busquedaFiltrada();
    }//GEN-LAST:event_cmbFechaActionPerformed

    private void cmbMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMontoActionPerformed
        this.busquedaFiltrada();
    }//GEN-LAST:event_cmbMontoActionPerformed

    private void cmbAdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAdcActionPerformed
        this.busquedaFiltrada();
    }//GEN-LAST:event_cmbAdcActionPerformed

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        this.busquedaFiltrada();
    }//GEN-LAST:event_cmbStatusActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Solicitudes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            Usuario usuario = null;
            Solicitudes dialog = new Solicitudes(new javax.swing.JFrame(), true, usuario, new String());
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarraSuperior;
    private javax.swing.JPanel Contenedor;
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JComboBox<String> cmbAdc;
    private javax.swing.JComboBox<String> cmbFecha;
    private javax.swing.JComboBox<String> cmbMonto;
    private javax.swing.JComboBox<String> cmbPlazo;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tabla;
    private javax.swing.JLabel tituloVentana;
    // End of variables declaration//GEN-END:variables

}
