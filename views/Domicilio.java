package views;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import maps.java.Geocoding;
import objects.Mes;
import objects.Persona;
import objects.Usuario;
import services.Domicilios_service;

/**
 *
 * @author Root
 */
public class Domicilio extends javax.swing.JDialog {

    private String DIRECCION_SELECCIONADA = null;
    private Persona PERSONA_SELECCIONADA = null;
    private Usuario USUARIO = null;
    private final Domicilios_service servicio;

    public Domicilio(JDialog parent, boolean modal, Usuario usuario, Persona persona) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);

        this.PERSONA_SELECCIONADA = persona;
        this.USUARIO = usuario;
        this.servicio = new Domicilios_service(this.getClass().toString());
        tituloVentana.setText(tituloVentana.getText() + " " + persona.toString());
        mostrarDomicilioActual();
        frmArrendamientoOnOff(false);
        meses();
        seleccionarDeTabla();
    }

    private void mostrarDomicilioActual() {
        if (this.PERSONA_SELECCIONADA.getDomicilio() > 0) {
            objects.Domicilio d = this.servicio.buscarDomicilioGuardado(this.PERSONA_SELECCIONADA.getDomicilio());
            if (d != null) {
                txtDireccionActual.setText(d.getDIRECCION());
            } else {
                txtDireccionActual.setText("ASIGNADO PERO NO ENCONTRADO, REVISE EL REGISTRO DE DOMICILIOS");
            }
        } else {
            txtDireccionActual.setText("NO ASIGNADA");
        }
    }

    private void guardarDomicilio() {
        String direccion = txtDireccion.getText().toUpperCase();
        String latitud = xPos.getText();
        String longitud = yPos.getText();
        int tipo = comboTipoVivienda.getSelectedIndex();
        String vigencia = txtAno.getText() + "-" + ((Mes) comboMeses.getSelectedItem()).getNumeroMes() + "-" + txtDia.getText();
        String propietario = txtPropietario.getText();
        String tiempoResidencia = txtAnosResidencia.getText();
        if (!"".equals(direccion) && !"".equals(latitud) && !"".equals(longitud) && tipo > 0 && !"0.0".equals(latitud) && !"0.0".equals(longitud) ) {
            objects.Domicilio dom = this.servicio.buscarDomicilioGuardado(direccion, latitud, longitud);
            boolean guardado = false;
            if (dom == null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Desea guardar esta dirección y asociarla con la persona " + this.PERSONA_SELECCIONADA.toString() + "?", "Pregunta", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dom = new objects.Domicilio(0, tipo, direccion, latitud, longitud, propietario, vigencia, tiempoResidencia);
                    guardado = this.servicio.guardarAsociarDomicilio(dom, this.PERSONA_SELECCIONADA, this.USUARIO);
                }
            } else {
                int confirm = JOptionPane.showConfirmDialog(this, "El domicilio ya se encuentra registrado, ¿desea asociarlo a la persona " + this.PERSONA_SELECCIONADA.toString() + "?", "Pregunta", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    guardado = this.servicio.asociarDomicilioPersona(dom.getID(), this.PERSONA_SELECCIONADA, this.USUARIO);
                }
            }

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Domicilio guardado y asociado correctamente a " + this.PERSONA_SELECCIONADA.toString(), "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Falla al guardar y asociar el domicilio indicado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {//SI EL FORMULARIO ESTÁ VACÍO
            JOptionPane.showMessageDialog(this, "No ha completado todos los campos necesarios", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCoordenadas(String direccion) {
        tabla = this.servicio.buscarCoordenadas(tabla, direccion);
    }

    private void buscarDireccionesGuardadas() {
        String direccion = txtBuscar2.getText();
        tabla = this.servicio.buscarDomicilios(tabla, direccion);
    }

    private void mostrarDatosDireccionSeleccionada() {
        if (DIRECCION_SELECCIONADA != null) {
            try {
                Geocoding ObjGeocod = new Geocoding();
                Point2D.Double resultadoCD = ObjGeocod.getCoordinates(DIRECCION_SELECCIONADA);
                tabla.setEnabled(false);
                JOptionPane.showMessageDialog(rootPane, "Se mostrarán las coordenadas de la dirección \"" + DIRECCION_SELECCIONADA + "\".", "Información", JOptionPane.INFORMATION_MESSAGE);
                txtDireccion.setText(DIRECCION_SELECCIONADA);
                xPos.setText("" + resultadoCD.x);
                yPos.setText("" + resultadoCD.y);
                tabla.setEnabled(true);
            } catch (UnsupportedEncodingException | MalformedURLException ex) {
                System.out.println("mostrarDatosDireccionSeleccionada() : " + ex);
            }
        }
    }

    private void seleccionarDeTabla() {
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                if (Mouse_evt.getClickCount() == 1) {
                    DIRECCION_SELECCIONADA = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
                    mostrarDatosDireccionSeleccionada();
                }
            }
        });
    }

    private void frmArrendamientoOnOff(boolean estado) {
        txtAno.setEnabled(estado);
        txtDia.setEnabled(estado);
        comboMeses.setEnabled(estado);
        txtAnosResidencia.setEnabled(estado);
        txtPropietario.setEnabled(estado);
        jLabel18.setEnabled(estado);
        jLabel28.setEnabled(estado);
        jLabel21.setEnabled(estado);
        txtAno.setText("0000");
        txtDia.setText("00");
        txtAnosResidencia.setText("0");
        txtPropietario.setText("");
    }

    private void meses() {
        Mes[] meses = this.servicio.meses();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Mes mes : meses) {
            dcbm.addElement(mes);
        }
        comboMeses.setModel(dcbm);
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
        yPos = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        xPos = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        comboTipoVivienda = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        txtDia = new javax.swing.JTextField();
        comboMeses = new javax.swing.JComboBox<>();
        txtAno = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtAnosResidencia = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtPropietario = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDireccionActual = new javax.swing.JTextField();
        panelTabla = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar2 = new javax.swing.JTextField();
        btnBusqueda = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
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
        tituloVentana.setText("Asignar domicilio a:");
        BarraSuperior.add(tituloVentana, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 870, 85));

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

        panelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingrese los datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelFormulario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        yPos.setEditable(false);
        yPos.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        yPos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                yPosKeyTyped(evt);
            }
        });
        panelFormulario.add(yPos, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, 150, 20));

        txtDireccion.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDireccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionKeyReleased(evt);
            }
        });
        panelFormulario.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 580, 20));

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel1.setText("Latitúd:");
        panelFormulario.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 58, 20));

        xPos.setEditable(false);
        xPos.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        xPos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                xPosKeyTyped(evt);
            }
        });
        panelFormulario.add(xPos, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 150, 20));

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel2.setText("Longitúd:");
        panelFormulario.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 110, -1, 20));

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel16.setText("Dirección actual:");
        panelFormulario.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        btnCancelar.setBackground(new java.awt.Color(204, 0, 0));
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setEnabled(false);
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });
        btnCancelar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Cancelar");
        btnCancelar.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 40));

        panelFormulario.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 220, 110, -1));

        btnGuardar.setBackground(new java.awt.Color(244, 0, 100));
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setEnabled(false);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });
        btnGuardar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Guardar");
        btnGuardar.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 40));

        panelFormulario.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 220, 110, 40));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Search_32px_2.png"))); // NOI18N
        btnBuscar.setBorder(null);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Search_32px.png"))); // NOI18N
        btnBuscar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Search_32px.png"))); // NOI18N
        btnBuscar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons/icons8_Search_32px.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        panelFormulario.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 70, 32, 32));

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel4.setText("Tipo de vivienda:");
        panelFormulario.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 110, 20));

        comboTipoVivienda.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboTipoVivienda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-----", "Propia", "Rentada", "Prestada" }));
        comboTipoVivienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoViviendaActionPerformed(evt);
            }
        });
        panelFormulario.add(comboTipoVivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 100, 20));

        jLabel18.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel18.setText("Vigencia de renta");
        panelFormulario.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, -1, 20));

        txtDia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiaKeyTyped(evt);
            }
        });
        panelFormulario.add(txtDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, 80, 20));

        comboMeses.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panelFormulario.add(comboMeses, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 160, 120, 20));

        txtAno.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAnoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnoKeyTyped(evt);
            }
        });
        panelFormulario.add(txtAno, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 80, 20));

        jLabel28.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel28.setText("Tiempo de residencia (años):");
        panelFormulario.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 160, -1, 20));

        txtAnosResidencia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidenciaKeyTyped(evt);
            }
        });
        panelFormulario.add(txtAnosResidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 160, 60, 20));

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel21.setText("Propietario:");
        panelFormulario.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, -1, 20));

        txtPropietario.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtPropietario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPropietario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPropietarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPropietarioKeyTyped(evt);
            }
        });
        panelFormulario.add(txtPropietario, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 580, 20));

        jLabel17.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel17.setText("Ingresar dirección:");
        panelFormulario.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 20));

        txtDireccionActual.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        txtDireccionActual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccionActual.setEnabled(false);
        txtDireccionActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActualActionPerformed(evt);
            }
        });
        txtDireccionActual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionActualKeyReleased(evt);
            }
        });
        panelFormulario.add(txtDireccionActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 580, 20));

        Contenedor.add(panelFormulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 920, 280));

        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Domicilios", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel9.setText("Ingresar dirección:");

        txtBuscar2.setToolTipText("Ingrese nombre, apellidos, CURP, OCR, teléfono o celular para realizar la búsqueda");
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18))
        );
        btnBusquedaLayout.setVerticalGroup(
            btnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tabla);

        javax.swing.GroupLayout panelTablaLayout = new javax.swing.GroupLayout(panelTabla);
        panelTabla.setLayout(panelTablaLayout);
        panelTablaLayout.setHorizontalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addGroup(panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTablaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3))
                    .addGroup(panelTablaLayout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTablaLayout.setVerticalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );

        Contenedor.add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 295, 1160, 230));

        PanelPrincipal.add(Contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1200, 530));

        getContentPane().add(PanelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void yPosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_yPosKeyTyped
        int lon = yPos.getText().length();
        if (lon >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_yPosKeyTyped

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        buscarCoordenadas(txtDireccion.getText());
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void txtDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyReleased
//        String cadena = txtDireccion.getText().toUpperCase();
//        txtDireccion.setText(cadena);
    }//GEN-LAST:event_txtDireccionKeyReleased

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        guardarDomicilio();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void xPosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_xPosKeyTyped
        int lon = xPos.getText().length();
        if (lon >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_xPosKeyTyped

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        txtDireccion.setText("");
        xPos.setText("");
        yPos.setText("");
        tabla.setModel(new DefaultTableModel());
        txtPropietario.setText("");
        comboTipoVivienda.setSelectedIndex(0);
        DIRECCION_SELECCIONADA = null;
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void txtBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar2ActionPerformed
        buscarDireccionesGuardadas();
    }//GEN-LAST:event_txtBuscar2ActionPerformed

    private void txtBuscar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyPressed
//        buscarDireccionesGuardadas();
    }//GEN-LAST:event_txtBuscar2KeyPressed

    private void txtBuscar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyReleased
        char cTeclaPresionada = evt.getKeyChar();
        if (cTeclaPresionada == KeyEvent.VK_DELETE || cTeclaPresionada == KeyEvent.VK_BACK_SPACE) {
            int l = txtBuscar2.getText().length();
            if (l == 0) {
                tabla.setModel(new DefaultTableModel());
            }
        }
    }//GEN-LAST:event_txtBuscar2KeyReleased

    private void btnBusquedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBusquedaMouseClicked
        buscarDireccionesGuardadas();
    }//GEN-LAST:event_btnBusquedaMouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarCoordenadas(txtDireccion.getText());
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void comboTipoViviendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoViviendaActionPerformed
        int i = comboTipoVivienda.getSelectedIndex();
        if (i > 1) {
            frmArrendamientoOnOff(true);
        } else {
            frmArrendamientoOnOff(false);
        }
    }//GEN-LAST:event_comboTipoViviendaActionPerformed

    private void txtDiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiaKeyTyped
        char c = evt.getKeyChar();
        int lon = txtDia.getText().length();
        if (!Character.isDigit(c) || lon >= 2) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDiaKeyTyped

    private void txtAnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnoKeyReleased

    }//GEN-LAST:event_txtAnoKeyReleased

    private void txtAnoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnoKeyTyped
        int lon = txtAno.getText().length();
        if (lon >= 4) {
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "El año no puede tener más de 4 caracteres");
        }
    }//GEN-LAST:event_txtAnoKeyTyped

    private void txtAnosResidenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidenciaKeyTyped
        char c = evt.getKeyChar();
        int lon = txtAnosResidencia.getText().length();
        if (!Character.isDigit(c) || lon >= 2) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAnosResidenciaKeyTyped

    private void txtPropietarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPropietarioKeyReleased
        String cadena = txtPropietario.getText().toUpperCase();
        txtPropietario.setText(cadena);
    }//GEN-LAST:event_txtPropietarioKeyReleased

    private void txtPropietarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPropietarioKeyTyped
        //        int lon = txtPropietario.getText().length();
        //        if (lon >= 18) {
        //            evt.consume();
        //            JOptionPane.showMessageDialog(rootPane, "El código CURP contiene únicamente 18 caracteres");
        //        }
    }//GEN-LAST:event_txtPropietarioKeyTyped

    private void txtDireccionActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionActualActionPerformed

    private void txtDireccionActualKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionActualKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionActualKeyReleased

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
            java.util.logging.Logger.getLogger(Domicilio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            Usuario usuario = null;
            Persona persona = null;
            Domicilio dialog = new Domicilio(new JDialog(), true, usuario, persona);
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
    private javax.swing.JButton btnBuscar;
    private javax.swing.JPanel btnBusqueda;
    private javax.swing.JPanel btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JPanel btnGuardar;
    private javax.swing.JComboBox<String> comboMeses;
    private javax.swing.JComboBox<String> comboTipoVivienda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tabla;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtAnosResidencia;
    private javax.swing.JTextField txtBuscar2;
    private javax.swing.JTextField txtDia;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDireccionActual;
    private javax.swing.JTextField txtPropietario;
    private javax.swing.JTextField xPos;
    private javax.swing.JTextField yPos;
    // End of variables declaration//GEN-END:variables
}
