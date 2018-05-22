package views;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import objects.Asentamiento;
import objects.Calle;
import objects.Colonia;
import objects.Municipio;

/**
 *
 * @author JMalagon
 */
public class zonificacion extends javax.swing.JInternalFrame {

    private services.Direcciones_service servicio;
    private int idMun;

    public zonificacion() throws SQLException {
        initComponents();
        this.servicio = new services.Direcciones_service();
        this.idMun = 0;
//        this.municipios();
//        this.tiposAsentamiento();
    }
/*
    private void municipios() throws SQLException {
        comboMunicipio.removeAllItems();
        Municipio[] municipios = this.servicio.municipios();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Municipio municipio : municipios) {
            dcbm.addElement(municipio);
        }
        comboMunicipio.setModel(dcbm);
    }

    private void calles(int idColonia) throws SQLException {
        comboCalles.removeAllItems();
        Calle[] calles = this.servicio.calles(idColonia);
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Calle calle : calles) {            
            dcbm.addElement(calle);
        }
        comboCalles.setModel(dcbm);
    }

    private void tiposAsentamiento() throws SQLException {
        comboTipoAsentamiento.removeAllItems();
        Asentamiento[] asentamientos = this.servicio.asentamientos();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Asentamiento asentamiento : asentamientos) {
            dcbm.addElement(asentamiento);
        }
        comboTipoAsentamiento.setModel(dcbm);
    }

    private void colonias() throws SQLException {
        comboColonias.removeAllItems();
        Colonia[] colonias = this.servicio.colonias(this.idMun);
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Colonia colonia : colonias) {
            dcbm.addElement(colonia);
        }
        comboColonias.setModel(dcbm);
    }

    private void colonias1() throws SQLException {
        comboColonias1.removeAllItems();
        Colonia[] colonias = this.servicio.colonias(this.idMun);
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Colonia colonia : colonias) {
            dcbm.addElement(colonia);
        }
        comboColonias1.setModel(dcbm);
    }

    private void guardarCalle() throws SQLException {
        String calle = txtCalle.getText();
        Colonia colonia = (Colonia) comboColonias.getSelectedItem();
        if (!"".equals(calle) && colonia != null) {
            int idColonia = colonia.getIdColonia();
            boolean existe = this.servicio.buscarCalle(calle, idColonia);
            if (existe == true) {
                JOptionPane.showMessageDialog(this, "La dirección ingresada ya existe");
            } else {
                boolean guardaCalle = this.servicio.guardarCalle(calle, idColonia);
                if (guardaCalle == false) {
                    JOptionPane.showMessageDialog(this, "Calle guardada correctamente");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar calle");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Favor de ingresar una dirección");
        }
    }

    private void guardarColonia() throws SQLException {
        Asentamiento tipoAsentamiento = (Asentamiento) comboTipoAsentamiento.getSelectedItem();
        int cp = 0;
        if (!"".equals(txtCp.getText())) {
            cp = Integer.parseInt(txtCp.getText());
        }
        String colonia = txtColonia.getText();
        if (!"".equals(colonia) && colonia != null) {
            int tipo = tipoAsentamiento.getIdAsentamiento();
            boolean existe = this.servicio.buscarColonia(colonia, this.idMun, tipo);
            if (existe == true) {
                JOptionPane.showMessageDialog(this, "La colonia ingresada ya existe");
            } else {
                boolean guardaColonia = this.servicio.guardarColonia(colonia, this.idMun, tipo, cp);
                if (guardaColonia == false) {
                    JOptionPane.showMessageDialog(this, "Colonia guardada correctamente");
                    this.colonias();
                    this.colonias1();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar colonia");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Favor de ingresar una dirección");
        }
    }

    private void guardarNumero() throws SQLException {
        Calle c = (Calle) comboCalles.getSelectedItem();
        int idCalle = c.getIdCalle();
        String numero = txtNumero.getText();
        if (!"".equals(numero)) {
            boolean existe = this.servicio.buscarNumero(numero, idCalle);
            if (existe == true) {
                JOptionPane.showMessageDialog(this, "El número ingresado ya existe");
            } else {
                boolean guardaCalle = this.servicio.guardarNumero(numero, idCalle);
                if (guardaCalle == false) {
                    JOptionPane.showMessageDialog(this, "Número guardado correctamente");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar número");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Favor de ingresar un número");
        }
    }
*/
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        txtColonia = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        comboTipoAsentamiento = new javax.swing.JComboBox<>();
        txtCp = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtCalle = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        comboColonias = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblCp = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        comboCalles = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        comboColonias1 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        lblTipo1 = new javax.swing.JLabel();
        lblCp1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        nombreMunicipio = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        idMunicipio = new javax.swing.JLabel();
        comboMunicipio = new javax.swing.JComboBox<>();

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Colonia"));

        jLabel5.setText("Colonia:");

        jButton3.setText("Guardar asentamiento");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtColonia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtColoniaActionPerformed(evt);
            }
        });
        txtColonia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtColoniaKeyReleased(evt);
            }
        });

        jLabel7.setText("Tipo:");

        txtCp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCpActionPerformed(evt);
            }
        });
        txtCp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCpKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCpKeyTyped(evt);
            }
        });

        jLabel8.setText("C. P.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboTipoAsentamiento, 0, 475, Short.MAX_VALUE)
                    .addComponent(txtColonia)
                    .addComponent(txtCp))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(jButton3)
                .addContainerGap(253, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboTipoAsentamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(26, 26, 26))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Calle"));

        jLabel1.setText("Calle:");

        jButton1.setText("Guardar calle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCalleActionPerformed(evt);
            }
        });
        txtCalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCalleKeyReleased(evt);
            }
        });

        jLabel4.setText("Asentamiento:");

        comboColonias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboColoniasActionPerformed(evt);
            }
        });

        jLabel9.setText("Tipo:");

        lblTipo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel12.setText("Código Postal:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCp, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtCalle)
                                .addComponent(comboColonias, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(comboColonias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(7, 7, 7))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar número"));

        jLabel2.setText("Calle:");

        jButton2.setText("Guardar número");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        comboCalles.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboCallesItemStateChanged(evt);
            }
        });
        comboCalles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboCallesMouseClicked(evt);
            }
        });
        comboCalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCallesActionPerformed(evt);
            }
        });

        jLabel3.setText("Número:");

        txtNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroActionPerformed(evt);
            }
        });
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroKeyPressed(evt);
            }
        });

        jLabel10.setText("Colonia:");

        comboColonias1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboColonias1ActionPerformed(evt);
            }
        });

        jLabel13.setText("Tipo:");

        lblTipo1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel14.setText("Código Postal:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(jButton2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblTipo1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCp1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboColonias1, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNumero)
                                .addComponent(comboCalles, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboColonias1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTipo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCp1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboCalles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccionar Municipio"));

        jLabel6.setText("Seleccione municipio:");

        nombreMunicipio.setText("nombreMunicipio");

        jLabel11.setText("Municipio en uso:");

        idMunicipio.setText("idMunicipio");

        comboMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMunicipioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(10, 10, 10)
                        .addComponent(nombreMunicipio)
                        .addGap(18, 18, 18)
                        .addComponent(idMunicipio))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(idMunicipio)
                    .addComponent(nombreMunicipio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(comboMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//        try {
//            guardarColonia();
//        } catch (SQLException ex) {
//            Logger.getLogger(zonificacion.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtColoniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtColoniaActionPerformed

    }//GEN-LAST:event_txtColoniaActionPerformed

    private void txtCpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCpActionPerformed

    }//GEN-LAST:event_txtCpActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        try {
//            guardarCalle();
//        } catch (SQLException ex) {
//            Logger.getLogger(zonificacion.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCalleActionPerformed
    }//GEN-LAST:event_txtCalleActionPerformed

    private void comboColoniasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboColoniasActionPerformed
//        Colonia colonia = (Colonia) comboColonias.getSelectedItem();
//        if (colonia != null) {
//            try {
//                int idTipo = colonia.getTipo();
//                String tipoAsentamiento = this.servicio.tipoAsentamiento(idTipo);
//                lblTipo.setText(tipoAsentamiento);
//                lblCp.setText(String.valueOf(colonia.getCP()));
//            } catch (SQLException ex) {
//                Logger.getLogger(zonificacion.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }//GEN-LAST:event_comboColoniasActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//        try {
//            guardarNumero();
//        } catch (SQLException ex) {
//            Logger.getLogger(zonificacion.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void comboCallesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboCallesItemStateChanged

    }//GEN-LAST:event_comboCallesItemStateChanged

    private void comboCallesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboCallesMouseClicked

    }//GEN-LAST:event_comboCallesMouseClicked

    private void comboCallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCallesActionPerformed
//        Calle c = (Calle) comboCalles.getSelectedItem();
//        System.out.println(c);
//        if (c != null) {
//            String colonia = null;
//            try {
//                colonia = this.servicio.colonia(c.getIdColonia());
//            } catch (SQLException ex) {
//                Logger.getLogger(zonificacion.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            lblCol.setText(colonia);
//        }
    }//GEN-LAST:event_comboCallesActionPerformed

    private void txtNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroActionPerformed
        String cadena = txtNumero.getText().toUpperCase();
        txtNumero.setText(cadena);
    }//GEN-LAST:event_txtNumeroActionPerformed

    private void txtNumeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyPressed
    }//GEN-LAST:event_txtNumeroKeyPressed

    private void comboMunicipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMunicipioActionPerformed
//        try {
//            Municipio municipio = (Municipio) comboMunicipio.getSelectedItem();
//            this.idMun = municipio.getIdMunicipio();
//            nombreMunicipio.setText(municipio.getNombreMunicipio());
//            idMunicipio.setText(String.valueOf(this.idMun));
//            this.colonias();
//            this.colonias1();
//        } catch (SQLException ex) {
//            Logger.getLogger(zonificacion.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_comboMunicipioActionPerformed

    private void comboColonias1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboColonias1ActionPerformed
//        Colonia col = (Colonia) comboColonias1.getSelectedItem();
//        if (col != null) {
//            try {
//                int idTipo = col.getTipo();
//                String tipoAsentamiento = this.servicio.tipoAsentamiento(idTipo);
//                lblTipo1.setText(tipoAsentamiento);
//                lblCp1.setText(String.valueOf(col.getCP()));
//                this.calles(col.getIdColonia());                
//            } catch (SQLException ex) {
//                Logger.getLogger(zonificacion.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }//GEN-LAST:event_comboColonias1ActionPerformed

    private void txtColoniaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaKeyReleased
        String cadena = txtColonia.getText().toUpperCase();
        txtColonia.setText(cadena);
    }//GEN-LAST:event_txtColoniaKeyReleased

    private void txtCalleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleKeyReleased
        String cadena = txtCalle.getText().toUpperCase();
        txtCalle.setText(cadena);
    }//GEN-LAST:event_txtCalleKeyReleased

    private void txtCpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpKeyReleased

    }//GEN-LAST:event_txtCpKeyReleased

    private void txtCpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCpKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboCalles;
    private javax.swing.JComboBox<String> comboColonias;
    private javax.swing.JComboBox<String> comboColonias1;
    private javax.swing.JComboBox<String> comboMunicipio;
    private javax.swing.JComboBox<String> comboTipoAsentamiento;
    private javax.swing.JLabel idMunicipio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblCp;
    private javax.swing.JLabel lblCp1;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTipo1;
    private javax.swing.JLabel nombreMunicipio;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtColonia;
    private javax.swing.JTextField txtCp;
    private javax.swing.JTextField txtNumero;
    // End of variables declaration//GEN-END:variables
}
