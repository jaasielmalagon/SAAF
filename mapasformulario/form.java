package mapasformulario;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import maps.java.Geocoding;

/**
 *
 * @author JMalagon
 */
public class form extends javax.swing.JFrame {

    public form() {
        initComponents();
        setLocationRelativeTo(null);

        try (InputStream is = form.class.getResourceAsStream("/image/fa-solid-900.ttf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(Font.PLAIN, 24f);

            JLabel label = new JLabel("holaaaa");
            label.setFont(font);
            
            panel.add(label);
            
        } catch (IOException | FontFormatException exp) {
            System.out.println(exp);
        }
    }

    private void buscarDireccion(String direccion) {
        Geocoding ObjGeocod = new Geocoding();
        try {
            Point2D.Double resultadoCD = ObjGeocod.getCoordinates(direccion);
            JOptionPane.showMessageDialog(this, "Las coordenadas de \"" + direccion + "\", son: "
                    + resultadoCD.x + "," + resultadoCD.y);
            xPos.setText("" + resultadoCD.x);
            yPos.setText("" + resultadoCD.y);

            String titulos[] = {"Dirección postal"};
            DefaultTableModel dtm = new DefaultTableModel(null, titulos);
            ArrayList<String> array = ObjGeocod.getAddress(resultadoCD.x, resultadoCD.y);
            if (array != null) {
                for (String item : array) {
//                    System.out.println(item);
                    Object[] cli = new Object[1];
                    cli[0] = item;
                    dtm.addRow(cli);
                }
                tabla.setModel(dtm);
                seleccionarDeTabla();
            }
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void seleccionarDeTabla() {
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                if (Mouse_evt.getClickCount() == 2) {
                    String direccionSeleccionada = tabla.getValueAt(tabla.getSelectedRow(), 0).toString();
                    try {
                        Geocoding ObjGeocod = new Geocoding();
                        Point2D.Double resultadoCD = ObjGeocod.getCoordinates(direccionSeleccionada);
                        xPos.setText("" + resultadoCD.x);
                        yPos.setText("" + resultadoCD.y);
                    } catch (UnsupportedEncodingException | MalformedURLException ex) {
                        System.out.println(".mousePressed() : " + ex);
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        yPos = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        y = new javax.swing.JLabel();
        x = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        xPos = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 130, 130));

        jLabel1.setText("Dirección:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        yPos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yPosActionPerformed(evt);
            }
        });
        getContentPane().add(yPos, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 260, 20));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, -1, 20));

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
        ));
        jScrollPane1.setViewportView(tabla);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, 90));

        y.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        y.setText("Y: ");
        getContentPane().add(y, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 60, 20));

        x.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        x.setText("X: ");
        getContentPane().add(x, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 44, 60, 20));

        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });
        getContentPane().add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 10, 350, 20));

        xPos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xPosActionPerformed(evt);
            }
        });
        getContentPane().add(xPos, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 44, 260, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String direccion = txtDireccion.getText();
        buscarDireccion(direccion);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void yPosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yPosActionPerformed
        String direccion = txtDireccion.getText();
        buscarDireccion(direccion);
    }//GEN-LAST:event_yPosActionPerformed

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void xPosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xPosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xPosActionPerformed

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
            java.util.logging.Logger.getLogger(form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JLabel x;
    private javax.swing.JTextField xPos;
    private javax.swing.JLabel y;
    private javax.swing.JTextField yPos;
    // End of variables declaration//GEN-END:variables
}
