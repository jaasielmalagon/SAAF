package views;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import javax.swing.JOptionPane;
import maps.java.Geocoding;
import objects.Usuario;
import services.Domicilios_service;

/**
 *
 * @author Root
 */
public class Domicilios extends javax.swing.JDialog {

    private String DIRECCION_SELECCIONADA = null;
    private Usuario USUARIO = null;
    private final Domicilios_service servicio;

    public Domicilios(java.awt.Frame parent, boolean modal, Usuario usuario) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);

        this.USUARIO = usuario;
        this.servicio = new Domicilios_service(this.getClass().toString());
        buscarDireccionesGuardadas("");
        seleccionarDeTabla();
    }

    private void guardarDomicilio() {
        String direccion = txtDireccion.getText().toUpperCase();
        String latitud = xPos.getText();
        String longitud = yPos.getText();

        if (!"".equals(direccion) && !"".equals(latitud) && !"".equals(longitud) && !"0.0".equals(latitud) && !"0.0".equals(longitud)) {
            objects.Domicilio dom = this.servicio.buscarDomicilioGuardado(direccion, latitud, longitud);

            boolean guardado = false;
            if (dom == null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Desea guardar esta dirección y sus coordenadas?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    dom = new objects.Domicilio(0, direccion, latitud, longitud);
                    guardado = this.servicio.guardarDomicilio(dom);
                }
            } else if (!dom.getDIRECCION().equals(direccion)) {
                int confirm = JOptionPane.showConfirmDialog(this, "La dirección guardada no es igual a la actualmente ingresada\n¿Desea actualizar la información?", "Aviso", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (this.servicio.actualizarDomicilio(dom, direccion, dom.getID())) {
                        JOptionPane.showMessageDialog(this, "Los datos del domicilio se actualizaron correctamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    } 
                }
            }
            
            if (guardado) {
                JOptionPane.showMessageDialog(this, "Datos del domicilio guardados correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);                
                cancelar();                
            } else {
                JOptionPane.showMessageDialog(this, "Falla al guardar la dirección indicada", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarCoordenadas(String direccion) {
        tabla = this.servicio.buscarCoordenadas(tabla, direccion);
    }

    private void buscarDireccionesGuardadas(String direccion) {
        tabla = this.servicio.buscarDomicilios(tabla, direccion);
    }

    private void mostrarDatosDireccionSeleccionada() {
        if (DIRECCION_SELECCIONADA != null) {
            try {
                Geocoding ObjGeocod = new Geocoding();
                Point2D.Double resultadoCD = ObjGeocod.getCoordinates(DIRECCION_SELECCIONADA);
                tabla.setEnabled(false);
                JOptionPane.showMessageDialog(rootPane, "Se mostrarán las coordenadas de la dirección \"" + DIRECCION_SELECCIONADA + "\".", "Información", JOptionPane.INFORMATION_MESSAGE);
                txtDireccion.setText(DIRECCION_SELECCIONADA);
                txtDireccionSeleccionada.setText(DIRECCION_SELECCIONADA);
                xPos.setText("" + resultadoCD.x);
                yPos.setText("" + resultadoCD.y);
                tabla.setEnabled(true);
            } catch (UnsupportedEncodingException | MalformedURLException ex) {
                System.out.println("mostrarDatosDireccionSeleccionada() : " + ex);
            }
        }
    }

    private void seleccionarDeTabla() {
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                if (Mouse_evt.getClickCount() == 1) {
                    DIRECCION_SELECCIONADA = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
                    mostrarDatosDireccionSeleccionada();
                }
            }
        });
    }

    private void cancelar() {
        txtDireccion.setText("");
        xPos.setText("");
        yPos.setText("");
        buscarDireccionesGuardadas("");
        DIRECCION_SELECCIONADA = null;
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
        yPos = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        xPos = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtDireccionSeleccionada = new javax.swing.JTextField();
        btnBuscarGPS = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
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
        tituloVentana.setText("Domicilios");
        BarraSuperior.add(tituloVentana, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 870, 85));

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
        panelFormulario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        yPos.setEditable(false);
        yPos.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        yPos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                yPosKeyTyped(evt);
            }
        });
        panelFormulario.add(yPos, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, 150, 20));

        txtDireccion.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDireccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panelFormulario.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 580, 20));

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel1.setText("Latitúd:");
        panelFormulario.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 58, 20));

        xPos.setEditable(false);
        xPos.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        xPos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xPosActionPerformed(evt);
            }
        });
        xPos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                xPosKeyTyped(evt);
            }
        });
        panelFormulario.add(xPos, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 150, 20));

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel2.setText("Longitúd:");
        panelFormulario.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, -1, 20));

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel16.setText("Seleccionada:");
        panelFormulario.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 110, 20));

        btnCancelar.setBackground(new java.awt.Color(255, 78, 0));
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setEnabled(false);
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });
        btnCancelar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Cancelar");
        btnCancelar.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 40));

        panelFormulario.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 130, 110, -1));

        btnEliminar.setBackground(new java.awt.Color(204, 0, 0));
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setEnabled(false);
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });
        btnEliminar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Eliminar");
        btnEliminar.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 40));

        panelFormulario.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 110, -1));

        btnGuardar.setBackground(new java.awt.Color(244, 0, 100));
        btnGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setEnabled(false);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });
        btnGuardar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Guardar");
        btnGuardar.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 40));

        panelFormulario.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, 110, 40));

        jLabel17.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel17.setText("Ingresar dirección:");
        panelFormulario.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 20));

        txtDireccionSeleccionada.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        txtDireccionSeleccionada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccionSeleccionada.setEnabled(false);
        txtDireccionSeleccionada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionSeleccionadaActionPerformed(evt);
            }
        });
        txtDireccionSeleccionada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionSeleccionadaKeyReleased(evt);
            }
        });
        panelFormulario.add(txtDireccionSeleccionada, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 580, 20));

        btnBuscarGPS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/gps30x30.png"))); // NOI18N
        btnBuscarGPS.setText("GPS");
        btnBuscarGPS.setToolTipText("Buscar coordenadas GPS");
        btnBuscarGPS.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscarGPS.setContentAreaFilled(false);
        btnBuscarGPS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarGPS.setFocusPainted(false);
        btnBuscarGPS.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/image/gps30x30.png"))); // NOI18N
        btnBuscarGPS.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/gps30x30.png"))); // NOI18N
        btnBuscarGPS.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/gps30x30.png"))); // NOI18N
        btnBuscarGPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarGPSActionPerformed(evt);
            }
        });
        panelFormulario.add(btnBuscarGPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 60, 80, 35));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Search_32px.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setToolTipText("Buscar coordenadas GPS");
        btnBuscar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Search_32px.png"))); // NOI18N
        btnBuscar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Search_32px.png"))); // NOI18N
        btnBuscar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Search_32px.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        panelFormulario.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, 80, 35));

        Contenedor.add(panelFormulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 820, 180));

        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Domicilios registrados", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
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
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );

        Contenedor.add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 205, 1160, 320));

        PanelPrincipal.add(Contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1200, 530));

        getContentPane().add(PanelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void yPosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_yPosKeyTyped
        int lon = yPos.getText().length();
        if (lon >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_yPosKeyTyped

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        guardarDomicilio();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void xPosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_xPosKeyTyped
        int lon = xPos.getText().length();
        if (lon >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_xPosKeyTyped

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        cancelar();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtDireccionSeleccionadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionSeleccionadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionSeleccionadaActionPerformed

    private void txtDireccionSeleccionadaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionSeleccionadaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionSeleccionadaKeyReleased

    private void btnBuscarGPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarGPSActionPerformed
        buscarCoordenadas(txtDireccion.getText());
    }//GEN-LAST:event_btnBuscarGPSActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarDireccionesGuardadas(txtDireccion.getText());
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void xPosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xPosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xPosActionPerformed

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
            java.util.logging.Logger.getLogger(Domicilios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            Usuario usuario = null;
            Domicilios dialog = new Domicilios(new javax.swing.JFrame(), true, usuario);
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
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarGPS;
    private javax.swing.JPanel btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JPanel btnEliminar;
    private javax.swing.JPanel btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tabla;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDireccionSeleccionada;
    private javax.swing.JTextField xPos;
    private javax.swing.JTextField yPos;
    // End of variables declaration//GEN-END:variables
}
