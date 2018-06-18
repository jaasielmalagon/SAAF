package views;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import objects.Adc;
import objects.Empleado;
import objects.Estudio;
import objects.Fecha;
import objects.Lista;
import objects.Mes;
import objects.Persona;
import objects.Usuario;
import services.clientes_service;


/**
 * @author mield
 */
public class Nomina extends javax.swing.JDialog {
    private final clientes_service SERVICIO;
    private Persona Empleado_seleccionado = null;
    private Usuario USUARIO = null;
     private Empleado EMPLEADO = null;
    /**
     * Creates new form Nomina
     */
    public Nomina(java.awt.Frame parent, boolean modal, Usuario Empleado, Persona persona) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        
        tituloVentana.setText(tituloVentana.getText() + " " + Empleado.getIdSucursal());
        this.USUARIO = Empleado;
        this.Empleado_seleccionado = persona;                
        this.SERVICIO = new clientes_service(this.getClass().toString());
        cargarDatosEmpleado();
        llenarTabla("");
        seleccionarEmpleado();
    }
    private Nomina() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    private void seleccionarEmpleado() {
        tablaEmpleados.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                if (Mouse_evt.getClickCount() == 1) {
                    int id = Integer.parseInt(tablaEmpleados.getValueAt(tablaEmpleados.getSelectedRow(), 0).toString());
                    try {
                        Empleado_seleccionado = SERVICIO.persona(USUARIO.getIdSucursal(), id);
                        cargarDatosEmpleado();
                    } catch (NumberFormatException ex) {
                        System.out.println(".mousePressed() : " + ex);
                    }
                }
            }
            });
    }
        private void llenarTabla(String dato) {
        if (!dato.isEmpty()) {
            tablaEmpleados = this.SERVICIO.tablaPersonas(tablaEmpleados, this.USUARIO.getIdSucursal(), dato);
        } else {
            tablaEmpleados = this.SERVICIO.tablaEmpleados(tablaEmpleados, this.USUARIO.getIdSucursal(), dato);
        }
    }
            private void cargarDatosEmpleado() {
        if (Empleado_seleccionado!= null) {
            idEmpleado.setText(Empleado_seleccionado.toString());
            this.EMPLEADO = this.SERVICIO.getEmpleado(Empleado_seleccionado.getIdPersona());            
            if (this.EMPLEADO == null) {
                JOptionPane.showMessageDialog(rootPane, "Agregue los datos laborales para: " + Empleado_seleccionado.toString());
                limpiarCampos();
            }
        } 
    }
             private void limpiarCampos() {        
                 cmbDepartamento.setSelectedIndex(0);
                 cmbCargo.setSelectedIndex(0);
    }
                 private void cancelar() {
        this.Empleado_seleccionado = null;
        this.EMPLEADO = null;
        limpiarCampos();
        llenarTabla("");
    }
    private void setSelectedDepartamento(int value) {
        Object item;
        Lista objeto;
        for (int i = 0; i < cmbDepartamento.getItemCount(); i++) {
            item = cmbDepartamento.getItemAt(i);
            objeto = (Lista) item;
            if (objeto.getID() == value) {
                cmbDepartamento.setSelectedIndex(i);
                break;
            }
        }
    }
        private void cargos() {
        DefaultComboBoxModel dcbm = this.SERVICIO.cargos();
        cmbCargo.setModel(dcbm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BarraSuperior = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        tituloVentana = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Contenedor = new javax.swing.JPanel();
        panelForm = new javax.swing.JPanel();
        lblOrb = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbDepartamento = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cmbCargo = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        idEmpleado = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();
        btnBusqueda = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JLabel();

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
        BarraSuperior.add(tituloVentana, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 866, 85));

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

        Contenedor.setBackground(new java.awt.Color(255, 245, 250));
        Contenedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelForm.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Buscador de Nomina", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelForm.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelForm.add(lblOrb, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 40, 20));

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel1.setText("Departamento:");
        panelForm.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, 20));

        cmbDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelForm.add(cmbDepartamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 120, -1));

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel2.setText("Cargo:");
        panelForm.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, -1, 20));

        cmbCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelForm.add(cmbCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 140, -1));

        Contenedor.add(panelForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 730, 120));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Empleados", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        jPanel1.setAlignmentX(0.0F);
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel3.setText("Número:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, -1, 30));

        idEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idEmpleadoActionPerformed(evt);
            }
        });
        jPanel1.add(idEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 230, 30));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 1140, 200));

        btnBusqueda.setBackground(new java.awt.Color(244, 0, 100));
        btnBusqueda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBusquedaMouseClicked(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscar.setText("Buscar");

        javax.swing.GroupLayout btnBusquedaLayout = new javax.swing.GroupLayout(btnBusqueda);
        btnBusqueda.setLayout(btnBusquedaLayout);
        btnBusquedaLayout.setHorizontalGroup(
            btnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnBusquedaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtBuscar)
                .addGap(18, 18, 18))
        );
        btnBusquedaLayout.setVerticalGroup(
            btnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnBusquedaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(btnBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 30, -1, -1));

        Contenedor.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 1160, 290));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(BarraSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(BarraSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(5, 5, 5)
                    .addComponent(Contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void idEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idEmpleadoActionPerformed
        llenarTabla(idEmpleado.getText());
    }//GEN-LAST:event_idEmpleadoActionPerformed

    private void btnBusquedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBusquedaMouseClicked
        llenarTabla(idEmpleado.getText());
    }//GEN-LAST:event_btnBusquedaMouseClicked

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
                new Nomina().setVisible(true);
            }
        });
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarraSuperior;
    private javax.swing.JPanel Contenedor;
    private javax.swing.JPanel btnBusqueda;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JComboBox<String> cmbCargo;
    private javax.swing.JComboBox<String> cmbDepartamento;
    private javax.swing.JTextField idEmpleado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblOrb;
    private javax.swing.JPanel panelForm;
    private javax.swing.JTable tablaEmpleados;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JLabel txtBuscar;
    // End of variables declaration//GEN-END:variables

}

