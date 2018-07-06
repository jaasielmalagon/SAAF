package views;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import objects.Persona;
import objects.Usuario;
import services.Personas_service;

/**
 *
 * @author JMalagon
 */
public class Referencias extends javax.swing.JDialog {

    private final Personas_service SERVICIO;
    private final int ID_SUCURSAL;
    private Persona PERSONA;
    private Persona PERSONA_REFERENCIA = null;
    private int ID_REFERENCIA = 0;

    public Referencias(JDialog parent, boolean modal, Usuario usuario, Persona persona, String modulo) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);

        tituloVentana.setText(tituloVentana.getText() + " a: " + persona.toString());
        this.SERVICIO = new Personas_service(modulo);
        this.ID_SUCURSAL = usuario.getIdSucursal();
        this.PERSONA = persona;
        cargarReferencias();
        llenarTabla();
        seleccionarPersona();
    }

    private void llenarTabla() {
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

        PanelPrincipal = new javax.swing.JPanel();
        BarraSuperior = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        tituloVentana = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Contenedor = new javax.swing.JPanel();
        panelFormulario = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblReferencia = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblConyuge = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblAval = new javax.swing.JLabel();
        panelTabla = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar2 = new javax.swing.JTextField();
        btnBusqueda = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        panelOpciones = new javax.swing.JPanel();
        btnReferencia = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnConyu = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btnAval = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
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
        tituloVentana.setText("Asociar referencias ");
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

        PanelPrincipal.add(BarraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 85));

        Contenedor.setBackground(new java.awt.Color(255, 245, 250));
        Contenedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Referencias", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel1.setText("Referencia:");

        lblReferencia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel2.setText("Cónyuge:");

        lblConyuge.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel3.setText("Aval:");

        lblAval.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        javax.swing.GroupLayout panelFormularioLayout = new javax.swing.GroupLayout(panelFormulario);
        panelFormulario.setLayout(panelFormularioLayout);
        panelFormularioLayout.setHorizontalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblReferencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblConyuge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAval, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelFormularioLayout.setVerticalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblConyuge, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblAval, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        Contenedor.add(panelFormulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 800, -1));

        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscador de personas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

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

        javax.swing.GroupLayout panelTablaLayout = new javax.swing.GroupLayout(panelTabla);
        panelTabla.setLayout(panelTablaLayout);
        panelTablaLayout.setHorizontalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTablaLayout.setVerticalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addGroup(panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTablaLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(panelTablaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
        );

        Contenedor.add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 1160, 275));

        panelOpciones.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelOpciones.setMinimumSize(new java.awt.Dimension(270, 487));
        panelOpciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnReferencia.setBackground(new java.awt.Color(255, 78, 0));
        btnReferencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReferenciaMouseClicked(evt);
            }
        });
        btnReferencia.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Referencia");
        btnReferencia.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 35));

        panelOpciones.add(btnReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 18, 190, 35));

        btnConyu.setBackground(new java.awt.Color(255, 78, 0));
        btnConyu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConyuMouseClicked(evt);
            }
        });
        btnConyu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Cónyuge");
        btnConyu.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 35));

        panelOpciones.add(btnConyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 18, 190, 35));

        btnAval.setBackground(new java.awt.Color(255, 78, 0));
        btnAval.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAvalMouseClicked(evt);
            }
        });
        btnAval.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Aval");
        btnAval.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 35));

        panelOpciones.add(btnAval, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 18, 190, 35));

        Contenedor.add(panelOpciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 600, 70));

        PanelPrincipal.add(Contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1200, 530));

        getContentPane().add(PanelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void txtBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar2ActionPerformed
        llenarTabla();
    }//GEN-LAST:event_txtBuscar2ActionPerformed

    private void txtBuscar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyPressed

    }//GEN-LAST:event_txtBuscar2KeyPressed

    private void txtBuscar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyReleased
        char cTeclaPresionada = evt.getKeyChar();
        if (cTeclaPresionada == KeyEvent.VK_DELETE || cTeclaPresionada == KeyEvent.VK_BACK_SPACE) {
            int l = txtBuscar2.getText().length();
            if (l == 0) {
                llenarTabla();
            }
        }
    }//GEN-LAST:event_txtBuscar2KeyReleased

    private void btnBusquedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBusquedaMouseClicked
        llenarTabla();
    }//GEN-LAST:event_btnBusquedaMouseClicked

    private void btnReferenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReferenciaMouseClicked
        if (PERSONA_REFERENCIA != null) {

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

        } else {
            JOptionPane.showMessageDialog(rootPane, "No ha seleccionado una persona como referencia");
        }
    }//GEN-LAST:event_btnReferenciaMouseClicked

    private void btnConyuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConyuMouseClicked
        if (PERSONA.getEstadoCivil() == 1) {
            if (PERSONA_REFERENCIA != null) {
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
            } else {
                JOptionPane.showMessageDialog(rootPane, "No ha seleccionado una persona como conyuge");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No puede agregar un cónyuge a una persona soltera, divorciada o viuda.");
        }
    }//GEN-LAST:event_btnConyuMouseClicked

    private void btnAvalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAvalMouseClicked
        if (PERSONA_REFERENCIA != null) {

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

        } else {
            JOptionPane.showMessageDialog(rootPane, "No ha seleccionado una persona como aval");
        }
    }//GEN-LAST:event_btnAvalMouseClicked

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
            java.util.logging.Logger.getLogger(Referencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            Usuario usuario = null;
            Persona persona = null;
            Referencias dialog = new Referencias(new JDialog(), true, usuario, persona, new String());
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
    private javax.swing.JPanel btnAval;
    private javax.swing.JPanel btnBusqueda;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JPanel btnConyu;
    private javax.swing.JPanel btnReferencia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAval;
    private javax.swing.JLabel lblConyuge;
    private javax.swing.JLabel lblReferencia;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelOpciones;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JTextField txtBuscar2;
    // End of variables declaration//GEN-END:variables
}
