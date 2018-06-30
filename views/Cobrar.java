package views;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JOptionPane;
import objects.Lista;
import objects.Prestamo;
import objects.Usuario;
import services.Cobrar_service;

/**
 *
 * @author 76053
 */
public class Cobrar extends javax.swing.JDialog {

    private final Cobrar_service SERVICIO;
    private Prestamo PRESTAMO_SELECCIONADO = null;
    private Usuario USUARIO = null;

    public Cobrar(java.awt.Frame parent, boolean modal, Usuario usuario, String modulo) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        this.SERVICIO = new Cobrar_service(modulo);
        this.USUARIO = usuario;
        this.comboZonas();
    }

    private void seleccionarDeTabla() {
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                if (Mouse_evt.getClickCount() == 2) {
                    int confirm = JOptionPane.showConfirmDialog(rootPane, "¿Desea iniciar la cobranza a partir del préstamo seleccionado hasta el último mostrado en la tabla?", "CONFIRMACIÓN", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            int i = tabla.getSelectedRow();
                            int rows = tabla.getRowCount();
                            int x = 0;
                            String monto = null;
                            String[][] pagos = new String[rows - i][3];
                            do {
                                String id = tabla.getValueAt(i, 0).toString();
                                String fecha = null;
                                monto = JOptionPane.showInputDialog(rootPane, "Préstamo: " + id, "0");
                                if (monto != null) {
                                    Date date = new Date();
                                    Object[] params = {"Seleccione fecha de pago:\n", new JDateChooser(date)};
                                    JOptionPane.showConfirmDialog(null, params, "Préstamo: " + id, JOptionPane.PLAIN_MESSAGE);
                                    Date jdc = ((JDateChooser) params[1]).getDate();
                                    if (jdc != null) {
                                        fecha = new SimpleDateFormat("yyyy/MM/dd").format(((JDateChooser) params[1]).getDate());
                                    }
                                }
                                pagos[x][0] = id;
                                pagos[x][1] = monto;
                                pagos[x][2] = fecha;
                                i++;
                                x++;
                            } while (i < rows && monto != null);
                            
                            confirm = JOptionPane.showConfirmDialog(rootPane, "¿Desea guardar los pagos capturados hasta este momento?", "CONFIRMACIÓN", JOptionPane.YES_NO_OPTION);
                            String mensaje;
                            if (confirm == JOptionPane.YES_OPTION) {
                                mensaje = SERVICIO.guardarPagos(USUARIO, pagos);
                            } else {
                                mensaje = "Operación cancelada";
                            }
                            JOptionPane.showMessageDialog(rootPane, mensaje);
                        } catch (NumberFormatException ex) {
                            System.out.println(".mousePressed() : " + ex);
                        }
                    }
                }
            }
        });
    }

    private void cobrar() {

    }

    private void buscarPorFolio() {
    }

    private void comboZonas() {
        cmbZona.setModel(this.SERVICIO.agencias(this.USUARIO.getIdSucursal()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        BarraSuperior = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        tituloVentana = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        panelTabla1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        cmbAdc = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        cmbZona = new javax.swing.JComboBox<>();
        panelTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar2 = new javax.swing.JTextField();
        btnBusqueda = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(1200, 620));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 620));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tituloVentana.setText("Ventana de cobro");
        BarraSuperior.add(tituloVentana, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 350, 85));

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

        jPanel1.add(BarraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 85));

        panelTabla1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingrese los datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelTabla1.setPreferredSize(new java.awt.Dimension(1200, 620));
        panelTabla1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel19.setText("ADC:");
        panelTabla1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 35, 40, 20));

        cmbAdc.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbAdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAdcActionPerformed(evt);
            }
        });
        panelTabla1.add(cmbAdc, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 35, 260, 20));

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel21.setText("Zona:");
        panelTabla1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 40, 20));

        cmbZona.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbZonaActionPerformed(evt);
            }
        });
        panelTabla1.add(cmbZona, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 35, 260, 20));

        jPanel1.add(panelTabla1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 660, 80));

        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clientes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelTabla.setPreferredSize(new java.awt.Dimension(1200, 620));
        panelTabla.setLayout(null);

        tabla.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabla);

        panelTabla.add(jScrollPane1);
        jScrollPane1.setBounds(10, 60, 1160, 340);

        jLabel9.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Folio de préstamo:");
        panelTabla.add(jLabel9);
        jLabel9.setBounds(230, 30, 168, 20);

        txtBuscar2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscar2.setToolTipText("Ingrese nombre, apellidos, CURP, OCR, teléfono o celular para realizar la búsqueda");
        txtBuscar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscar2ActionPerformed(evt);
            }
        });
        txtBuscar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscar2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscar2KeyReleased(evt);
            }
        });
        panelTabla.add(txtBuscar2);
        txtBuscar2.setBounds(400, 30, 380, 20);

        btnBusqueda.setBackground(new java.awt.Color(244, 0, 100));
        btnBusqueda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBusquedaMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Buscar");

        javax.swing.GroupLayout btnBusquedaLayout = new javax.swing.GroupLayout(btnBusqueda);
        btnBusqueda.setLayout(btnBusquedaLayout);
        btnBusquedaLayout.setHorizontalGroup(
            btnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );
        btnBusquedaLayout.setVerticalGroup(
            btnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnBusquedaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelTabla.add(btnBusqueda);
        btnBusqueda.setBounds(790, 30, 75, 20);

        jPanel1.add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 1180, 410));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void txtBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar2ActionPerformed
        buscarPorFolio();
    }//GEN-LAST:event_txtBuscar2ActionPerformed

    private void txtBuscar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyPressed

    }//GEN-LAST:event_txtBuscar2KeyPressed

    private void txtBuscar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyReleased
        char cTeclaPresionada = evt.getKeyChar();
        if (cTeclaPresionada == KeyEvent.VK_DELETE || cTeclaPresionada == KeyEvent.VK_BACK_SPACE) {
            int l = txtBuscar2.getText().length();
            if (l == 0) {

            }
        }
    }//GEN-LAST:event_txtBuscar2KeyReleased

    private void btnBusquedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBusquedaMouseClicked
        buscarPorFolio();
    }//GEN-LAST:event_btnBusquedaMouseClicked

    private void cmbZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbZonaActionPerformed
        try {
            int zona = ((Lista) cmbZona.getSelectedItem()).getID();
            if (zona > 0) {
                cmbAdc.setModel(this.SERVICIO.vacantes(this.USUARIO.getIdSucursal(), zona));
                this.SERVICIO.tablaPrestamosDe(tabla, this.USUARIO.getIdSucursal(), zona, 0);
                this.seleccionarDeTabla();
            } else {
                cmbAdc.removeAllItems();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cmbZonaActionPerformed

    private void cmbAdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAdcActionPerformed
        try {
            int zona = ((Lista) cmbZona.getSelectedItem()).getID();
            int adc = ((Lista) cmbAdc.getSelectedItem()).getID();
            if (zona > 0 && adc > 0) {
                this.SERVICIO.tablaPrestamosDe(tabla, this.USUARIO.getIdSucursal(), zona, adc);
                this.seleccionarDeTabla();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cmbAdcActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cobrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            Cobrar dialog = new Cobrar(new javax.swing.JFrame(), true, null, null);
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
    private javax.swing.JPanel btnBusqueda;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JComboBox<String> cmbAdc;
    private javax.swing.JComboBox<String> cmbZona;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JPanel panelTabla1;
    private javax.swing.JTable tabla;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JTextField txtBuscar2;
    // End of variables declaration//GEN-END:variables

}
