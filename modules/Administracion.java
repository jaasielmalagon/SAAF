package modules;

import avante.Login;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import objects.Usuario;
import views.Personas;

/**
 *
 * @author JMalagon
 */
public class Administracion extends javax.swing.JFrame {

    private final Usuario usuario;

    public Administracion(Usuario usuario) {
        initComponents();
        setLocationRelativeTo(null);
        this.usuario = usuario;
        nombreUsuario.setText("HOLA " + this.usuario.toString().toUpperCase());
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

        BarraSuperior = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nombreUsuario = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fotografia = new javax.swing.JLabel();
        Escritorio = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        txtBuscador = new javax.swing.JTextField();
        iconBuscar = new javax.swing.JLabel();
        iconPersonas = new javax.swing.JLabel();
        lblPersonas = new javax.swing.JLabel();
        iconDomicilios = new javax.swing.JLabel();
        lblDomicilios = new javax.swing.JLabel();
        iconClientes = new javax.swing.JLabel();
        lblClientes = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BarraSuperior.setBackground(new java.awt.Color(191, 5, 50));
        BarraSuperior.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/menu-logo.png"))); // NOI18N
        BarraSuperior.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1056, 15, 304, 85));

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ADMINISTRACIÓN");
        BarraSuperior.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 15, 300, 40));

        nombreUsuario.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        nombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        nombreUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BarraSuperior.add(nombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 300, 20));

        jLabel15.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("EXCELENTE DÍA");
        BarraSuperior.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 300, 20));

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("AVANTE TE DESEA UN ");
        BarraSuperior.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 300, 20));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/AVANTELOGO_03_150x300.png"))); // NOI18N
        BarraSuperior.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 300, 150));

        fotografia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fotografia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user.png"))); // NOI18N
        BarraSuperior.add(fotografia, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 200, 200));

        getContentPane().add(BarraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 620));

        Escritorio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        Escritorio.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, 32, 32));

        txtBuscador.setBackground(new java.awt.Color(237, 237, 237));
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
        Escritorio.add(txtBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 25, 250, 32));

        iconBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Search_32px_2.png"))); // NOI18N
        Escritorio.add(iconBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 25, 32, 32));

        iconPersonas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconPersonas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user-group-icon-80286.png"))); // NOI18N
        iconPersonas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconPersonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconPersonasMouseClicked(evt);
            }
        });
        Escritorio.add(iconPersonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 150, 150));

        lblPersonas.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblPersonas.setForeground(new java.awt.Color(255, 99, 1));
        lblPersonas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPersonas.setText("PERSONAS");
        lblPersonas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPersonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPersonasMouseClicked(evt);
            }
        });
        Escritorio.add(lblPersonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 245, 150, 35));

        iconDomicilios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconDomicilios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        iconDomicilios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Escritorio.add(iconDomicilios, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 150, 150));

        lblDomicilios.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblDomicilios.setForeground(new java.awt.Color(255, 99, 1));
        lblDomicilios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDomicilios.setText("DOMICILIOS");
        lblDomicilios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Escritorio.add(lblDomicilios, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 245, 150, 35));

        iconClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cliente.png"))); // NOI18N
        iconClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Escritorio.add(iconClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 150, 150));

        lblClientes.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        lblClientes.setForeground(new java.awt.Color(255, 99, 1));
        lblClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClientes.setText("CLIENTES");
        lblClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Escritorio.add(lblClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 245, 150, 35));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Escritorio.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 310, 150, 150));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Escritorio.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 310, 150, 150));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        Escritorio.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 150, 150));

        jLabel11.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 99, 1));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        Escritorio.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 470, 150, 35));

        jLabel12.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 99, 1));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Escritorio.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 470, 150, 35));

        jLabel13.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 99, 1));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Escritorio.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 470, 150, 35));

        lblBackground.setBackground(new java.awt.Color(0, 0, 0));
        lblBackground.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fondo-menu-900x620.png"))); // NOI18N
        Escritorio.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 620));

        getContentPane().add(Escritorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 900, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iconPersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconPersonasMouseClicked
        (new Personas(this, true, this.usuario)).setVisible(true);
    }//GEN-LAST:event_iconPersonasMouseClicked

    private void lblPersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPersonasMouseClicked
        (new Personas(this, true, this.usuario)).setVisible(true);
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
    private javax.swing.JPanel BarraSuperior;
    private javax.swing.JPanel Escritorio;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JLabel fotografia;
    private javax.swing.JLabel iconBuscar;
    private javax.swing.JLabel iconClientes;
    private javax.swing.JLabel iconDomicilios;
    private javax.swing.JLabel iconPersonas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblClientes;
    private javax.swing.JLabel lblDomicilios;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblPersonas;
    private javax.swing.JLabel nombreUsuario;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
