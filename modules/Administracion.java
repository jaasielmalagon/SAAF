package modules;

import avante.Login;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import objects.Usuario;
import views.Clientes;
import views.Domicilios;
import views.Personas;
import views.Solicitudes;

/**
 *
 * @author JMalagon
 */
public class Administracion extends javax.swing.JFrame {

    private final Usuario usuario;
    private final String modulo = Thread.currentThread().getStackTrace()[1].getClassName();
    public Administracion(Usuario usuario) {
        initComponents();
        setLocationRelativeTo(null);
        this.usuario = usuario;
        nombreUsuario.setText("HOLA " + this.usuario.getUsername().toUpperCase());
        if (this.usuario.getFotografia() != null) {
            System.out.println(this.usuario.getFotografia());
            fotografia.setIcon(new ImageIcon(this.usuario.getFotografia()));
        }
//        Toolkit t = Toolkit.getDefaultToolkit();
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        System.out.println("Tu resolución es de " + screenSize.width + "x" + screenSize.height);
    }

    private void openInternet(String URL) {
        try {
            Desktop.getDesktop().browse(URI.create(URL));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e, "¡Error al buscar!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscador = new javax.swing.JTextField();
        btnCerrar = new javax.swing.JButton();
        iconBuscar = new javax.swing.JLabel();
        nombreUsuario = new javax.swing.JLabel();
        lblMensaje2 = new javax.swing.JLabel();
        tituloModulo = new javax.swing.JLabel();
        lblMensaje1 = new javax.swing.JLabel();
        iconPersonas = new javax.swing.JLabel();
        lblPersonas = new javax.swing.JLabel();
        iconDomicilios = new javax.swing.JLabel();
        lblDomicilios = new javax.swing.JLabel();
        iconClientes = new javax.swing.JLabel();
        lblClientes = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        iconSolicitudes = new javax.swing.JLabel();
        lblSolicitudes = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        fotografia = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscador.setFont(new java.awt.Font("Solomon Sans Book", 0, 14)); // NOI18N
        txtBuscador.setForeground(new java.awt.Color(181, 10, 20));
        txtBuscador.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscador.setText("Buscar en internet ...");
        txtBuscador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(181, 10, 20)));
        txtBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscadorActionPerformed(evt);
            }
        });
        getContentPane().add(txtBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 50, 250, 32));

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Multiply_32px.png"))); // NOI18N
        btnCerrar.setBorder(null);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Multiply_white_32px.png"))); // NOI18N
        btnCerrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Multiply_white_32px.png"))); // NOI18N
        btnCerrar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Multiply_white_32px.png"))); // NOI18N
        btnCerrar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Multiply_white_32px.png"))); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 0, 32, 32));

        iconBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Search_32px_2.png"))); // NOI18N
        getContentPane().add(iconBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, -1, -1));

        nombreUsuario.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        nombreUsuario.setForeground(new java.awt.Color(181, 10, 20));
        nombreUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(nombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 300, 20));

        lblMensaje2.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblMensaje2.setForeground(new java.awt.Color(181, 10, 20));
        lblMensaje2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensaje2.setText("EXCELENTE DÍA");
        getContentPane().add(lblMensaje2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 560, 300, 20));

        tituloModulo.setFont(new java.awt.Font("Solomon Sans Book", 1, 24)); // NOI18N
        tituloModulo.setForeground(new java.awt.Color(181, 10, 20));
        tituloModulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloModulo.setText("ADMINISTRACIÓN");
        getContentPane().add(tituloModulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 300, 40));

        lblMensaje1.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblMensaje1.setForeground(new java.awt.Color(181, 10, 20));
        lblMensaje1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensaje1.setText("AVANTE TE DESEA UN ");
        getContentPane().add(lblMensaje1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 300, 20));

        iconPersonas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconPersonas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icono_group.png"))); // NOI18N
        iconPersonas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconPersonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconPersonasMouseClicked(evt);
            }
        });
        getContentPane().add(iconPersonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 150, 150));

        lblPersonas.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblPersonas.setForeground(new java.awt.Color(181, 10, 20));
        lblPersonas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPersonas.setText("PERSONAS");
        lblPersonas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPersonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPersonasMouseClicked(evt);
            }
        });
        getContentPane().add(lblPersonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, 150, 35));

        iconDomicilios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconDomicilios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icono_home.png"))); // NOI18N
        iconDomicilios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconDomicilios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconDomiciliosMouseClicked(evt);
            }
        });
        getContentPane().add(iconDomicilios, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 120, 150, 150));

        lblDomicilios.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblDomicilios.setForeground(new java.awt.Color(181, 10, 20));
        lblDomicilios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDomicilios.setText("DOMICILIOS");
        lblDomicilios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDomicilios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDomiciliosMouseClicked(evt);
            }
        });
        getContentPane().add(lblDomicilios, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 280, 150, 35));

        iconClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icono_cliente.png"))); // NOI18N
        iconClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconClientesMouseClicked(evt);
            }
        });
        getContentPane().add(iconClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 120, 150, 150));

        lblClientes.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblClientes.setForeground(new java.awt.Color(181, 10, 20));
        lblClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClientes.setText("CLIENTES");
        lblClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblClientesMouseClicked(evt);
            }
        });
        getContentPane().add(lblClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 280, 150, 35));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 350, 150, 150));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 150, 150));

        iconSolicitudes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconSolicitudes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/request.png"))); // NOI18N
        iconSolicitudes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconSolicitudes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconSolicitudesMouseClicked(evt);
            }
        });
        getContentPane().add(iconSolicitudes, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 350, 150, 150));

        lblSolicitudes.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblSolicitudes.setForeground(new java.awt.Color(181, 10, 20));
        lblSolicitudes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSolicitudes.setText("SOLICITUDES");
        lblSolicitudes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSolicitudes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSolicitudesMouseClicked(evt);
            }
        });
        getContentPane().add(lblSolicitudes, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 510, 150, 35));

        jLabel12.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(181, 10, 20));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 510, 150, 35));

        jLabel13.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(181, 10, 20));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 510, 150, 35));

        fotografia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(fotografia, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 305, 200, 200));

        lblBackground.setBackground(new java.awt.Color(0, 0, 0));
        lblBackground.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/MENU_900x620.png"))); // NOI18N
        getContentPane().add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iconPersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconPersonasMouseClicked
        (new Personas(this, true, this.usuario, this.modulo)).setVisible(true);
    }//GEN-LAST:event_iconPersonasMouseClicked

    private void lblPersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPersonasMouseClicked
        (new Personas(this, true, this.usuario, this.modulo)).setVisible(true);
    }//GEN-LAST:event_lblPersonasMouseClicked

    private void iconSolicitudesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconSolicitudesMouseClicked
        (new Solicitudes(this, true, this.usuario, this.modulo)).setVisible(true);
    }//GEN-LAST:event_iconSolicitudesMouseClicked

    private void lblSolicitudesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSolicitudesMouseClicked
        (new Solicitudes(this, true, this.usuario, this.modulo)).setVisible(true);
    }//GEN-LAST:event_lblSolicitudesMouseClicked

    private void iconClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconClientesMouseClicked
        (new Clientes(this, true, this.usuario)).setVisible(true);
    }//GEN-LAST:event_iconClientesMouseClicked

    private void lblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClientesMouseClicked
        (new Clientes(this, true, this.usuario)).setVisible(true);
    }//GEN-LAST:event_lblClientesMouseClicked

    private void txtBuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscadorActionPerformed
        openInternet(txtBuscador.getText());
    }//GEN-LAST:event_txtBuscadorActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        int x = JOptionPane.showConfirmDialog(this, "¿Desea cerrar su actividad de este día?", "¡PRECAUCIÓN!", JOptionPane.YES_NO_OPTION);
        if (x == JOptionPane.YES_OPTION) {
            new Login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void iconDomiciliosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconDomiciliosMouseClicked
        (new Domicilios(this, true, this.usuario)).setVisible(true);
    }//GEN-LAST:event_iconDomiciliosMouseClicked

    private void lblDomiciliosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDomiciliosMouseClicked
        (new Domicilios(this, true, this.usuario)).setVisible(true);
    }//GEN-LAST:event_lblDomiciliosMouseClicked

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
            java.util.logging.Logger.getLogger(Administracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JLabel fotografia;
    private javax.swing.JLabel iconBuscar;
    private javax.swing.JLabel iconClientes;
    private javax.swing.JLabel iconDomicilios;
    private javax.swing.JLabel iconPersonas;
    private javax.swing.JLabel iconSolicitudes;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblClientes;
    private javax.swing.JLabel lblDomicilios;
    private javax.swing.JLabel lblMensaje1;
    private javax.swing.JLabel lblMensaje2;
    private javax.swing.JLabel lblPersonas;
    private javax.swing.JLabel lblSolicitudes;
    private javax.swing.JLabel nombreUsuario;
    private javax.swing.JLabel tituloModulo;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
