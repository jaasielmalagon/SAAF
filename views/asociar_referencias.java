package views;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objects.Persona;
import services.agregarPersona_service;

/**
 *
 * @author JMalagon
 */
public class asociar_referencias extends javax.swing.JInternalFrame {

    private final agregarPersona_service SERVICIO;
    private final int ID_SUCURSAL;
    private Persona PERSONA;
    private Persona PERSONA_REFERENCIA = null;
    private int ID_REFERENCIA = 0;

    public asociar_referencias(int idSucursal, String titulo, Persona cliente) throws SQLException {
        initComponents();
        this.setTitle(titulo);
        lblReferencias.setText(title);
        this.SERVICIO = new agregarPersona_service();
        this.ID_SUCURSAL = idSucursal;
        this.PERSONA = cliente;
        cargarReferencias();
        llenarTabla();
        seleccionarPersona();
    }

    private void llenarTabla() throws SQLException {
        if (!txtBuscar2.getText().isEmpty()) {
            tablaClientes.setModel(this.SERVICIO.tablaPersonas2(ID_SUCURSAL, PERSONA.getIdPersona(), txtBuscar2.getText()));
        } else {
            tablaClientes.setModel(this.SERVICIO.tablaPersonas2(ID_SUCURSAL, PERSONA.getIdPersona()));
        }
    }

    private void seleccionarPersona() {
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                if (Mouse_evt.getClickCount() == 1) {
                    ID_REFERENCIA = Integer.parseInt(tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0).toString());
                    PERSONA_REFERENCIA = SERVICIO.persona(ID_SUCURSAL, ID_REFERENCIA);
                    System.out.println(PERSONA_REFERENCIA.toString());
                }
            }
        });
    }

    private void cargarReferencias() {
        if (PERSONA.getReferencia() > 0) {
            lblReferencia.setText(SERVICIO.persona(ID_SUCURSAL, PERSONA.getReferencia()).toString());
        } else {
            lblReferencia.setText("NO ASIGNADO");
        }
        if (PERSONA.getConyuge() > 0) {
            lblConyuge.setText(SERVICIO.persona(ID_SUCURSAL, PERSONA.getConyuge()).toString());
        } else {
            lblConyuge.setText("NO ASIGNADO");
        }
        if (PERSONA.getAval() > 0) {
            lblAval.setText(SERVICIO.persona(ID_SUCURSAL, PERSONA.getAval()).toString());
        } else {
            lblAval.setText("NO ASIGNADO");
        }
    }

    private void recargarCliente() {
        PERSONA = SERVICIO.persona(ID_SUCURSAL, PERSONA.getIdPersona());
        /*int ref = PERSONA.getReferencia();
        int con = PERSONA.getConyuge();
        int ava = PERSONA.getAval();
        System.out.println("Ref: " + ref + " Cony: " + con + " Aval: " + ava);*/
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblReferencias = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar2 = new javax.swing.JTextField();
        btnBusqueda = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        panelOpciones2 = new javax.swing.JPanel();
        btnReferencia = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnConyu = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnAval = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblReferencia = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblConyuge = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblAval = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(244, 0, 100));

        jPanel2.setBackground(new java.awt.Color(189, 0, 53));

        lblReferencias.setFont(new java.awt.Font("Solomon Sans Book", 0, 18)); // NOI18N
        lblReferencias.setForeground(new java.awt.Color(255, 255, 255));
        lblReferencias.setText("Referencias de");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cerrar.png"))); // NOI18N
        jLabel14.setToolTipText("Cerrar");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblReferencias)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblReferencias, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscador de personas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel9.setText("Dato:");

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

        btnBusqueda.setBackground(new java.awt.Color(244, 0, 100));
        btnBusqueda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBusquedaMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Buscar");

        javax.swing.GroupLayout btnBusquedaLayout = new javax.swing.GroupLayout(btnBusqueda);
        btnBusqueda.setLayout(btnBusquedaLayout);
        btnBusquedaLayout.setHorizontalGroup(
            btnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnBusquedaLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18))
        );
        btnBusquedaLayout.setVerticalGroup(
            btnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tablaClientes);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        panelOpciones2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelOpciones2.setMinimumSize(new java.awt.Dimension(270, 487));

        btnReferencia.setBackground(new java.awt.Color(255, 78, 0));
        btnReferencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReferenciaMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Agregar Referencia");

        javax.swing.GroupLayout btnReferenciaLayout = new javax.swing.GroupLayout(btnReferencia);
        btnReferencia.setLayout(btnReferenciaLayout);
        btnReferenciaLayout.setHorizontalGroup(
            btnReferenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnReferenciaLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnReferenciaLayout.setVerticalGroup(
            btnReferenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnReferenciaLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        btnConyu.setBackground(new java.awt.Color(255, 78, 0));
        btnConyu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConyuMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Agregar Cónyuge");

        javax.swing.GroupLayout btnConyuLayout = new javax.swing.GroupLayout(btnConyu);
        btnConyu.setLayout(btnConyuLayout);
        btnConyuLayout.setHorizontalGroup(
            btnConyuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnConyuLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnConyuLayout.setVerticalGroup(
            btnConyuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnConyuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        btnAval.setBackground(new java.awt.Color(255, 78, 0));
        btnAval.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAvalMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Agregar Aval");

        javax.swing.GroupLayout btnAvalLayout = new javax.swing.GroupLayout(btnAval);
        btnAval.setLayout(btnAvalLayout);
        btnAvalLayout.setHorizontalGroup(
            btnAvalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAvalLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnAvalLayout.setVerticalGroup(
            btnAvalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAvalLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelOpciones2Layout = new javax.swing.GroupLayout(panelOpciones2);
        panelOpciones2.setLayout(panelOpciones2Layout);
        panelOpciones2Layout.setHorizontalGroup(
            panelOpciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpciones2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOpciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReferencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConyu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAval, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelOpciones2Layout.setVerticalGroup(
            panelOpciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpciones2Layout.createSequentialGroup()
                .addComponent(btnReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConyu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Referencias", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel1.setText("Referencia:");

        lblReferencia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel2.setText("Cónyuge:");

        lblConyuge.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel3.setText("Aval:");

        lblAval.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblReferencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblConyuge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAval, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblConyuge, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblAval, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOpciones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(panelOpciones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(288, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar2ActionPerformed
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(agregar_personas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtBuscar2ActionPerformed

    private void txtBuscar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyPressed

    }//GEN-LAST:event_txtBuscar2KeyPressed

    private void txtBuscar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyReleased
        char cTeclaPresionada = evt.getKeyChar();
        if (cTeclaPresionada == KeyEvent.VK_DELETE || cTeclaPresionada == KeyEvent.VK_BACK_SPACE) {
            int l = txtBuscar2.getText().length();
            if (l == 0) {
                try {
                    llenarTabla();
                } catch (SQLException ex) {
                    Logger.getLogger(asociar_referencias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtBuscar2KeyReleased

    private void btnBusquedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBusquedaMouseClicked
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(agregar_personas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBusquedaMouseClicked

    private void btnReferenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReferenciaMouseClicked
        if (PERSONA_REFERENCIA != null) {
            try {
//                boolean existe = this.SERVICIO.buscarReferencia("referencia = " + PERSONA_REFERENCIA.getIdPersona() + " OR conyuge = " + PERSONA_REFERENCIA.getIdPersona() + " OR aval = " + PERSONA_REFERENCIA.getIdPersona() + " LIMIT 1");
//                if (existe) {
//                    JOptionPane.showMessageDialog(rootPane, "La persona que desea agregar como referencia ya se encuentra asociada a otra.", "Error", JOptionPane.ERROR_MESSAGE);
//                } else {
                int bpsd = this.SERVICIO.buscarPersonaSinDomicilio(PERSONA_REFERENCIA.getIdPersona());
                if (bpsd <= 0) {
                    JOptionPane.showMessageDialog(rootPane, "La persona que desea agregar como referencia tiene un domicilio inválido o no ha sido asignado.", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean agregado = this.SERVICIO.agregarReferencia(PERSONA.getIdPersona(), "referencia = " + PERSONA_REFERENCIA.getIdPersona());
                    if (agregado) {
                        JOptionPane.showMessageDialog(rootPane, "Referencia agregada al cliente " + PERSONA.toString());
                        recargarCliente();
                        cargarReferencias();
                    }
                }
//                }
            } catch (SQLException ex) {
                Logger.getLogger(asociar_referencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No ha seleccionado una persona como referencia");
        }
    }//GEN-LAST:event_btnReferenciaMouseClicked

    private void btnConyuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConyuMouseClicked
        if (PERSONA.getEstadoCivil() == 1) {
            if (PERSONA_REFERENCIA != null) {
                try {
                    boolean existe = this.SERVICIO.buscarReferencia("conyuge = " + PERSONA_REFERENCIA.getIdPersona() + " LIMIT 1");
                    if (existe) {
                        JOptionPane.showMessageDialog(rootPane, "La persona que desea agregar como conyuge ya se encuentra asociada a otro cliente", "¡Atención!", JOptionPane.WARNING_MESSAGE);
                    } else {
                        int opc = JOptionPane.showConfirmDialog(rootPane, "¿Desea asociar a ambas personas al mismo domicilio?", "¡Pregunta!", JOptionPane.YES_NO_OPTION);
                        int act = 0;
                        if (opc == JOptionPane.YES_OPTION) {
                            if (PERSONA.getDomicilio() > 0) {
                                act = this.SERVICIO.actualizarPersona(PERSONA_REFERENCIA.getIdPersona(), PERSONA.getDomicilio());
                            } else if (PERSONA_REFERENCIA.getDomicilio() > 0) {
                                act = this.SERVICIO.actualizarPersona(PERSONA.getIdPersona(), PERSONA_REFERENCIA.getDomicilio());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "No se han podido asociar los domicilios de los cónyuges porque ninguno cuenta con domicilio asignado.", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
//                        if (act >= 1) {
//                            JOptionPane.showMessageDialog(rootPane, "Sólo pueden asociarse 3 personas al mismo domicilio.\nRevise la asignación de domicilios.", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
//                        } else {
                            int bpsd = this.SERVICIO.buscarPersonaSinDomicilio(PERSONA_REFERENCIA.getIdPersona());
                            if (bpsd > 0) {
                                boolean agregado = this.SERVICIO.agregarReferencia(PERSONA.getIdPersona(), "conyuge = " + PERSONA_REFERENCIA.getIdPersona());
                                System.out.println(agregado);
                                agregado = this.SERVICIO.agregarReferencia(PERSONA_REFERENCIA.getIdPersona(), "conyuge = " + PERSONA.getIdPersona());
                                System.err.println(agregado);
                                if (agregado) {
                                    JOptionPane.showMessageDialog(rootPane, "Conyuge agregado al cliente " + PERSONA.toString());
                                    recargarCliente();
                                    cargarReferencias();
                                }
                            }
//                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("views.asociar_referencias.btnConyuMouseClicked()");
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "No ha seleccionado una persona como conyuge");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No puede agregar un cónyuge a una persona soltera, divorciada o viuda.");
        }

    }//GEN-LAST:event_btnConyuMouseClicked

    private void btnAvalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAvalMouseClicked
        if (PERSONA_REFERENCIA != null) {
            try {
                boolean existe = this.SERVICIO.buscarReferencia("aval = " + PERSONA_REFERENCIA.getIdPersona() + " LIMIT 1");
                if (existe) {
                    JOptionPane.showMessageDialog(rootPane, "La persona que desea agregar como aval ya se encuentra asociada a otro cliente");
                } else {
                    int bpsd = this.SERVICIO.buscarPersonaSinDomicilio(PERSONA_REFERENCIA.getIdPersona());
                    if (bpsd <= 0) {
                        JOptionPane.showMessageDialog(rootPane, "La persona que desea agregar como aval tiene un domicilio inválido o no ha sido asignado.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        boolean agregado = this.SERVICIO.agregarReferencia(PERSONA.getIdPersona(), "aval = " + PERSONA_REFERENCIA.getIdPersona());
                        if (agregado) {
                            JOptionPane.showMessageDialog(rootPane, "Aval agregado al cliente " + PERSONA.toString());
                            recargarCliente();
                            cargarReferencias();
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(asociar_referencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No ha seleccionado una persona como aval");
        }
    }//GEN-LAST:event_btnAvalMouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel14MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAval;
    private javax.swing.JPanel btnBusqueda;
    private javax.swing.JPanel btnConyu;
    private javax.swing.JPanel btnReferencia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAval;
    private javax.swing.JLabel lblConyuge;
    private javax.swing.JLabel lblReferencia;
    private javax.swing.JLabel lblReferencias;
    private javax.swing.JPanel panelOpciones2;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTextField txtBuscar2;
    // End of variables declaration//GEN-END:variables
}
