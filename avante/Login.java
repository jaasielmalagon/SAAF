package avante;

import javax.swing.JOptionPane;
import modules.*;
import objects.Usuario;
import services.login_service;

/**
 *
 * @author Root
 */
public class Login extends javax.swing.JFrame {

    private final login_service SERVICIO;
    
    public Login() {
        initComponents();
        setLocationRelativeTo(null);
        txtUsuario.requestFocus();
        this.SERVICIO = new login_service();
    }

    private void login() {
        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();
        Usuario u = this.SERVICIO.datosUsuario(usuario, contrasena);
//1 Administación
//2 Contabilidad
//3 Recursos Humanos
//4 Gerencia Operativa
//5 Diseño
//6 Metodología
//7 Sistemas informáticos
//8 Administración de cartera
        if (u != null) {
            switch (u.getTipoUsuario()) {
                case 1:
                    JOptionPane.showMessageDialog(rootPane, "Módulo no disponible", "¡Error!", JOptionPane.ERROR_MESSAGE);                    
                    break;
                case 2:
                    JOptionPane.showMessageDialog(rootPane, "Módulo no disponible", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(rootPane, "Módulo no disponible", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    break;
                case 4:
                    JOptionPane.showMessageDialog(rootPane, "Módulo no disponible", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    break;
                case 5:
                    JOptionPane.showMessageDialog(rootPane, "Módulo no disponible", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    break;
                case 6:
                    JOptionPane.showMessageDialog(rootPane, "Módulo no disponible", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    break;
                case 7:
                    JOptionPane.showMessageDialog(rootPane, "Módulo no disponible", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    new Ejemplo(u).setVisible(true);
                    this.dispose();
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Usuario y/o contraseña incorrectos.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnIngresar = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtContrasena = new javax.swing.JPasswordField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SAAF");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, -5, 32, 32));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Key_32px.png"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 220, 32, 32));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_customer_32px_1.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 32, 32));

        btnIngresar.setBackground(new java.awt.Color(214, 9, 9));
        btnIngresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnIngresarMouseClicked(evt);
            }
        });
        btnIngresar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Solomon Sans Book", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Ingresar");
        btnIngresar.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/puntero-icono.png"))); // NOI18N
        btnIngresar.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 20, 30));

        jPanel1.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 305, 110, 30));

        txtUsuario.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        txtUsuario.setBorder(null);
        txtUsuario.setHighlighter(null);
        txtUsuario.setOpaque(false);
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, 280, 25));

        jSeparator1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, 305, 1));

        txtContrasena.setToolTipText("Escriba su contraseña");
        txtContrasena.setBorder(null);
        txtContrasena.setOpaque(false);
        txtContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContrasenaActionPerformed(evt);
            }
        });
        jPanel1.add(txtContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, 280, 25));

        jSeparator3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 250, 305, 1));

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Contraseña:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, -1, -1));

        jLabel5.setFont(new java.awt.Font("Solomon Sans Book", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Usuario:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("LOGIN");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/usuario-icono.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 25, 353, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/LOGIN-900x500.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 500));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        login();
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void btnIngresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIngresarMouseClicked
        login();
    }//GEN-LAST:event_btnIngresarMouseClicked

    private void txtContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContrasenaActionPerformed
        login();
    }//GEN-LAST:event_txtContrasenaActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        int x = JOptionPane.showConfirmDialog(this, "¿Desea cerrar el programa?", "¡PRECAUCIÓN!", JOptionPane.YES_NO_OPTION);
        if (x == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnCerrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JPanel btnIngresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
