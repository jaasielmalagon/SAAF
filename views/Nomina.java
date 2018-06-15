/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import objects.Amortizacion;
import objects.Cliente;
import objects.Estado;
import objects.Fecha;
import objects.Mes;
import objects.Persona;
import objects.Solicitud;
import objects.Usuario;
import services.agregarPersona_service;

/**
 *
 * @author mield
 */
public class Nomina extends javax.swing.JDialog {
    private final agregarPersona_service servicio;
    private int ID_PERSONA_SELECCIONADA = 0;
    private Persona PERSONA_SELECCIONADA = null;
    private Usuario USUARIO = null;
    private Cliente CLIENTE = null;
    /**
     * Creates new form Nomina
     */
    public Nomina(java.awt.Frame parent, boolean modal, Usuario usuario) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        
        this.servicio= new agregarPersona_service();
        this.USUARIO=usuario;
        System.out.print(this.USUARIO.toString());
        llenarTabla();
        
    }

private void llenarTabla() {
        tablaEmpleados = this.servicio.tablaPersonas(tablaEmpleados, this.USUARIO.getIdSucursal());
}
    @SuppressWarnings("unchecked")


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BarraSuperior = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        tituloVentana = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Contenedor = new javax.swing.JPanel();
        panelTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();
        txtNumeroEmpleado = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        panelTabla1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();

        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tituloVentana.setText("Nomina");
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

        getContentPane().add(BarraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 85));

        Contenedor.setBackground(new java.awt.Color(255, 245, 250));
        Contenedor.setMinimumSize(new java.awt.Dimension(1200, 620));
        Contenedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empleados", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelTabla.setPreferredSize(new java.awt.Dimension(1200, 620));
        panelTabla.setLayout(null);

        tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaEmpleados);

        panelTabla.add(jScrollPane1);
        jScrollPane1.setBounds(10, 80, 1010, 240);

        txtNumeroEmpleado.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNumeroEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroEmpleadoActionPerformed(evt);
            }
        });
        txtNumeroEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroEmpleadoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumeroEmpleadoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroEmpleadoKeyTyped(evt);
            }
        });
        panelTabla.add(txtNumeroEmpleado);
        txtNumeroEmpleado.setBounds(390, 30, 260, 30);

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel20.setText("Número:");
        panelTabla.add(jLabel20);
        jLabel20.setBounds(330, 30, 55, 30);

        Contenedor.add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 1040, 330));

        panelTabla1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscador de nomina", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelTabla1.setPreferredSize(new java.awt.Dimension(1200, 620));
        panelTabla1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel1.setText("Departamento:");
        panelTabla1.add(jLabel1);
        jLabel1.setBounds(20, 40, 100, 20);

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel2.setText("Cargo:");
        panelTabla1.add(jLabel2);
        jLabel2.setBounds(350, 40, 40, 20);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        panelTabla1.add(jComboBox1);
        jComboBox1.setBounds(120, 40, 160, 20);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gerente", "Empleado", "Administrador", " " }));
        panelTabla1.add(jComboBox2);
        jComboBox2.setBounds(390, 40, 150, 20);

        Contenedor.add(panelTabla1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 600, 110));

        getContentPane().add(Contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1200, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void txtNumeroEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroEmpleadoActionPerformed

    private void txtNumeroEmpleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroEmpleadoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroEmpleadoKeyPressed

    private void txtNumeroEmpleadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroEmpleadoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroEmpleadoKeyReleased

    private void txtNumeroEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroEmpleadoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroEmpleadoKeyTyped

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Nomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Nomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Nomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Nomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                
                try {
                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if ("Windows".equals(info.getName())) {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
                    java.util.logging.Logger.getLogger(Personas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }

                java.awt.EventQueue.invokeLater(() -> {
                    Usuario usuario = null;
                    Nomina dialog = new Nomina(new javax.swing.JFrame(), true, usuario);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                });
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarraSuperior;
    private javax.swing.JPanel Contenedor;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JPanel panelTabla1;
    private javax.swing.JTable tablaEmpleados;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JTextField txtNumeroEmpleado;
    // End of variables declaration//GEN-END:variables
}
