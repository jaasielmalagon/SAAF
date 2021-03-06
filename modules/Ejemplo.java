package modules;

import avante.Login;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import objects.Usuario;
import views.Clientes;
import views.Personas;

/**
 *
 * @author JMalagon
 */
public class Ejemplo extends javax.swing.JFrame {

    private final Usuario usuario;
    private String modulo = Thread.currentThread().getStackTrace()[1].getClassName();
    
    public Ejemplo(Usuario usuario) {
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

        tituloModulo = new javax.swing.JLabel();
        nombreUsuario = new javax.swing.JLabel();
        lblMensaje2 = new javax.swing.JLabel();
        lblMensaje1 = new javax.swing.JLabel();
        fotografia = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        iconBuscar = new javax.swing.JLabel();
        iconPersonas = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblDomicilios = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblClientes = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        txtBuscador = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        iconDomicilios = new javax.swing.JLabel();
        iconClientes = new javax.swing.JLabel();
        lblPersonas = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tituloModulo.setFont(new java.awt.Font("Solomon Sans Book", 1, 24)); // NOI18N
        tituloModulo.setForeground(new java.awt.Color(181, 10, 20));
        tituloModulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloModulo.setText("ADMINISTRACIÓN");
        getContentPane().add(tituloModulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 300, 40));

        nombreUsuario.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        nombreUsuario.setForeground(new java.awt.Color(181, 10, 20));
        nombreUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(nombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 300, 20));

        lblMensaje2.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblMensaje2.setForeground(new java.awt.Color(181, 10, 20));
        lblMensaje2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensaje2.setText("EXCELENTE DÍA");
        getContentPane().add(lblMensaje2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 560, 300, 20));

        lblMensaje1.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblMensaje1.setForeground(new java.awt.Color(181, 10, 20));
        lblMensaje1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensaje1.setText("AVANTE TE DESEA UN ");
        getContentPane().add(lblMensaje1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 300, 20));

        fotografia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fotografia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user.png"))); // NOI18N
        getContentPane().add(fotografia, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 200, 200));

        jLabel13.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(181, 10, 20));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 520, 150, 35));

        iconBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Search_32px_2.png"))); // NOI18N
        getContentPane().add(iconBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, -1, -1));

        iconPersonas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconPersonas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user-group-icon-80286.png"))); // NOI18N
        iconPersonas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconPersonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconPersonasMouseClicked(evt);
            }
        });
        getContentPane().add(iconPersonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, -1, -1));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 360, 150, 150));

        lblDomicilios.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblDomicilios.setForeground(new java.awt.Color(181, 10, 20));
        lblDomicilios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDomicilios.setText("DOMICILIOS");
        lblDomicilios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(lblDomicilios, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 290, 150, 35));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 360, 150, 150));

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
        getContentPane().add(lblClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 290, 150, 35));

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

        txtBuscador.setFont(new java.awt.Font("Solomon Sans Book", 0, 14)); // NOI18N
        txtBuscador.setForeground(new java.awt.Color(255, 99, 1));
        txtBuscador.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscador.setText("Buscar en internet ...");
        txtBuscador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 99, 1)));
        txtBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscadorActionPerformed(evt);
            }
        });
        getContentPane().add(txtBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 50, 250, 32));

        jLabel12.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(181, 10, 20));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 520, 150, 35));

        iconDomicilios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconDomicilios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        iconDomicilios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(iconDomicilios, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 130, -1, 150));

        iconClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cliente.png"))); // NOI18N
        iconClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconClientesMouseClicked(evt);
            }
        });
        getContentPane().add(iconClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 130, -1, 150));

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
        getContentPane().add(lblPersonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 290, 150, 35));

        jLabel11.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(181, 10, 20));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 520, 150, 35));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 360, 150, 150));

        lblBackground.setBackground(new java.awt.Color(255, 0, 0));
        lblBackground.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/MENU_900x620.png"))); // NOI18N
        getContentPane().add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iconPersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconPersonasMouseClicked
        (new Personas(this, true, this.usuario,this.modulo)).setVisible(true);
    }//GEN-LAST:event_iconPersonasMouseClicked

    private void lblPersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPersonasMouseClicked
        (new Personas(this, true, this.usuario, this.modulo)).setVisible(true);
    }//GEN-LAST:event_lblPersonasMouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        int x = JOptionPane.showConfirmDialog(this, "¿Desea cerrar su actividad de este día?", "¡PRECAUCIÓN!", JOptionPane.YES_NO_OPTION);
        if (x == JOptionPane.YES_OPTION) {
            new Login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtBuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscadorActionPerformed
        openInternet(txtBuscador.getText());
    }//GEN-LAST:event_txtBuscadorActionPerformed

    private void iconClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconClientesMouseClicked
        (new Clientes(this, true, this.usuario)).setVisible(true);
    }//GEN-LAST:event_iconClientesMouseClicked

    private void lblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClientesMouseClicked
        (new Clientes(this, true, this.usuario)).setVisible(true);
    }//GEN-LAST:event_lblClientesMouseClicked

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
            java.util.logging.Logger.getLogger(Ejemplo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel nombreUsuario;
    private javax.swing.JLabel tituloModulo;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
