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
import objects.Cliente;
import objects.Estudio;
import objects.Ocupacion;
import objects.Persona;
import objects.Usuario;
import services.clientes_service;

/**
 *
 * @author Root
 */
public class Clientes extends javax.swing.JDialog {

    private final clientes_service SERVICIO;
    private Persona PERSONA_SELECCIONADA = null;
    private Usuario USUARIO = null;
    private Cliente CLIENTE = null;

    public Clientes(java.awt.Frame parent, boolean modal, Usuario usuario) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        
        this.SERVICIO = new clientes_service(this.getClass().toString());
        this.USUARIO = usuario;
        ocupaciones();
        estudios();
        llenarTabla();
        seleccionarPersona();
        if (PERSONA_SELECCIONADA != null) {
            lblNombrePersona.setText(PERSONA_SELECCIONADA.toString());
            etiquetasOnOff(true);
        }
    }
    
    private void llenarTabla() {
        tablaClientes = this.SERVICIO.tablaPersonas(tablaClientes, this.USUARIO.getIdSucursal(), "");
    }

    public void buscar() {
        if (!txtBuscar2.getText().isEmpty()) {
            tablaClientes = this.SERVICIO.tablaPersonas(tablaClientes, USUARIO.getIdSucursal(), txtBuscar2.getText());
        } else {
            llenarTabla();
        }
    }

    public final void seleccionarPersona() {
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                if (Mouse_evt.getClickCount() == 1) {
                    int id = Integer.parseInt(tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0).toString());
                    try {
                        PERSONA_SELECCIONADA = SERVICIO.persona(USUARIO.getIdSucursal(), id);
                        if (PERSONA_SELECCIONADA != null) {
                            CLIENTE = SERVICIO.cliente(PERSONA_SELECCIONADA);
                        }
                        if (CLIENTE != null) {
                            int confirm = JOptionPane.showConfirmDialog(rootPane, "¿Desea modificar los datos de " + CLIENTE.getPersona().toString() + "?", "¡Atención!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (confirm == JOptionPane.YES_OPTION) {
                                cargarDatosCliente();
                            }
                        } else {
                            limpiarCampos();
                            lblNombrePersona.setText(PERSONA_SELECCIONADA.toString());
                            etiquetasOnOff(true);
                            JOptionPane.showMessageDialog(rootPane, "La persona seleccionada todavía no es cliente.\nIngrese los datos solicitados en el formulario y presione 'Guardar'.", "¡Aviso!", JOptionPane.WARNING_MESSAGE);                                                        
                        }
                    } catch (HeadlessException | NumberFormatException ex) {
                        System.out.println(".mousePressed() : " + ex);
                    }
                }
            }
        });
    }

    private void guardarDatos() {
        if (PERSONA_SELECCIONADA != null) {
            if (comprobarCampos() == true) {
                double ingresos = Double.valueOf(txtIngresos.getText());
                double egresos = Double.valueOf(txtEgresos.getText());
                int dependientes = Integer.valueOf(txtDependientes.getText());
                int ocupacion = ((Ocupacion) cmbOcupacion.getSelectedItem()).getId();
                int estudios = ((Estudio) cmbNivelEstudios.getSelectedItem()).getID();
                String empresa = txtEmpresa.getText();
                String direccion = txtDireccion.getText();
                String telefono = txtTel.getText();
                String entrada = txtEntrada.getValue().toString();
                String[] split = entrada.split(" ");
                entrada = split[3];
                String salida = txtSalida.getValue().toString();
                split = salida.split(" ");
                salida = split[3];
                String adc = txtAdc.getText();
                Cliente cliente;
                if (CLIENTE == null) {
                    cliente = new Cliente(0, this.USUARIO.getIdSucursal(), this.USUARIO.getIdUsuario(), "", adc,
                            PERSONA_SELECCIONADA.getIdPersona(), ingresos, egresos, dependientes, ocupacion, estudios,
                            empresa, direccion, telefono, entrada, salida, 0, 0, 1, PERSONA_SELECCIONADA);
                    int insert = this.SERVICIO.guardarDatos(cliente);
                    cancelar();
                    if (insert == -1) {
                        int x = JOptionPane.showConfirmDialog(rootPane, "Esta persona ya se encuentra registrada como cliente..", "¡Error!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                        if (x == 1) {
                            CLIENTE = this.SERVICIO.cliente(PERSONA_SELECCIONADA);
                            cargarDatosCliente();
                        }else{
                            cancelar();
                        }
                    } else if (insert == 0) {
                        JOptionPane.showMessageDialog(rootPane, "Los datos no se guardaron correctamente.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    } else if (insert > 0) {
                        JOptionPane.showMessageDialog(rootPane, "Datos guardados correctamente.", "¡Extio!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (CLIENTE != null) {
                    cliente = new Cliente(CLIENTE.getIdCliente(), this.USUARIO.getIdSucursal(), this.USUARIO.getIdUsuario(), CLIENTE.getF_REGISTRO(), adc,
                            CLIENTE.getID_PERSONA(), ingresos, egresos, dependientes, ocupacion, estudios, empresa,
                            direccion, telefono, entrada, salida, CLIENTE.getSCORE(), CLIENTE.getSTATUS(), 1, PERSONA_SELECCIONADA);
                    int update = this.SERVICIO.actualizarDatos(cliente);                    
                    switch (update) {
                        case 1:
                            JOptionPane.showMessageDialog(rootPane, "Datos guardados correctamente.", "¡Extio!", JOptionPane.INFORMATION_MESSAGE);
                            cancelar();
                            break;
                        case 0:
                            JOptionPane.showMessageDialog(rootPane, "Los datos no se guardaron correctamente.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                            break;
                        case -1:
                            JOptionPane.showMessageDialog(rootPane, "Esta persona no es un cliente registrado, presione cancelar y genere el registro nuevamente.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                            break;
                        default:
                            break;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No ha seleccionado una persona existente.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosCliente() {
        try {
            lblNombrePersona.setText(CLIENTE.getPersona().toString());
            etiquetasOnOff(true);
            txtIngresos.setText(String.valueOf(CLIENTE.getINGRESOS()));
            txtEgresos.setText(String.valueOf(CLIENTE.getEGRESOS()));
            restarIngresosyEgresos();
            txtDependientes.setText(String.valueOf(CLIENTE.getDEPENDIENTES()));
            setSelectedOcupacion(CLIENTE.getOCUPACION());
            setSelectedEstudios(CLIENTE.getESTUDIOS());
            txtEmpresa.setText(CLIENTE.getEMPRESA());
            txtDireccion.setText(CLIENTE.getDOMICILIO_EMPRESA());
            txtTel.setText(CLIENTE.getTEL_EMPRESA());
            
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date hora = sdf.parse(CLIENTE.getHORA_ENTRADA());
            SpinnerDateModel sm = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
            txtEntrada.setModel(sm);
            JSpinner.DateEditor de = new JSpinner.DateEditor(txtEntrada, "HH:mm:ss");
            txtEntrada.setEditor(de);
            
            hora = sdf.parse(CLIENTE.getHORA_SALIDA());
            SpinnerDateModel sm2 = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
            txtSalida.setModel(sm2);
            JSpinner.DateEditor ded = new JSpinner.DateEditor(txtSalida, "HH:mm:ss");
            txtSalida.setEditor(ded);
            txtAdc.setText(CLIENTE.getADC());
            txtAdc.requestFocus();
        } catch (ParseException ex) {
            System.out.println("views.clientes.cargarDatosCliente() : " + ex);
        }
    }

    private boolean comprobarCampos() {
        boolean flag = true;
        char[] charArray;
        if (txtAdc.getText().length() == 0 || txtAdc.getText().isEmpty()) {
            flag = false;
            txtAdc.requestFocus();
            JOptionPane.showMessageDialog(rootPane, "El cliente debe pertenecer a un ADC.", "¡Hay un error en los datos!", JOptionPane.ERROR_MESSAGE);
        }
        if (flag == true) {
            try {
                Double.parseDouble(txtIngresos.getText());
            } catch (NumberFormatException e) {
                flag = false;
                JOptionPane.showMessageDialog(rootPane, "Debe indicar la cantidad de ingresos del cliente.", "¡Hay un error en los datos!", JOptionPane.ERROR_MESSAGE);
                txtIngresos.requestFocus();
            }
        }
        if (flag == true) {
            try {
                Double.parseDouble(txtEgresos.getText());
            } catch (NumberFormatException e) {
                flag = false;
                JOptionPane.showMessageDialog(rootPane, "Debe indicar la cantidad de egresos del cliente.", "¡Hay un error en los datos!", JOptionPane.ERROR_MESSAGE);
                txtEgresos.requestFocus();
            }
        }

        if (flag == true) {
            charArray = txtDependientes.getText().toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                if (!Character.isDigit(charArray[i])) {
                    flag = false;
                    JOptionPane.showMessageDialog(rootPane, "Debe indicar el número de personas que dependen del ingreso del cliente.", "¡Hay un error en los datos!", JOptionPane.ERROR_MESSAGE);
                    txtDependientes.requestFocus();
                    break;
                }
            }
        }

        if (flag == true) {
            int ocupacion = ((Ocupacion) cmbOcupacion.getSelectedItem()).getId();
            if (ocupacion == 2 || ocupacion == 3) {
                if (txtEmpresa.getText().isEmpty()) {
                    flag = false;
                    JOptionPane.showMessageDialog(rootPane, "Debe indicar la empresa donde trabaja el cliente.", "¡Hay un error en los datos!", JOptionPane.ERROR_MESSAGE);
                    txtEmpresa.requestFocus();
                }
                if (txtDireccion.getText().isEmpty()) {
                    flag = false;
                    JOptionPane.showMessageDialog(rootPane, "Debe indicar la dirección de la empresa donde trabaja el cliente.", "¡Hay un error en los datos!", JOptionPane.ERROR_MESSAGE);
                    txtDireccion.requestFocus();
                }
                if (txtTel.getText().isEmpty() || txtTel.getText().length() < 10) {
                    flag = false;
                    JOptionPane.showMessageDialog(rootPane, "Debe indicar el número telefónico de la empresa donde trabaja el cliente.", "¡Hay un error en los datos!", JOptionPane.ERROR_MESSAGE);
                    txtTel.requestFocus();
                }
                if (txtEntrada.getValue().toString().isEmpty() || "00:00:00".equals(txtEntrada.getValue().toString())) {
                    flag = false;
                    JOptionPane.showMessageDialog(rootPane, "Debe indicar la hora de entrada a trabajar del cliente.", "¡Hay un error en los datos!", JOptionPane.ERROR_MESSAGE);
                    txtEntrada.requestFocus();
                }
                if (txtSalida.getValue().toString().isEmpty() || "00:00:00".equals(txtSalida.getValue().toString())) {
                    flag = false;
                    JOptionPane.showMessageDialog(rootPane, "Debe indicar la hora de salida de trabajar del cliente.", "¡Hay un error en los datos!", JOptionPane.ERROR_MESSAGE);
                    txtSalida.requestFocus();
                }
            }
        }

        return flag;
    }

    public void setSelectedEstudios(int value) {
        Object item;
        Estudio objeto;
        for (int i = 0; i < cmbNivelEstudios.getItemCount(); i++) {
            item = cmbNivelEstudios.getItemAt(i);
            objeto = (Estudio) item;
            if (objeto.getID() == value) {
                cmbNivelEstudios.setSelectedIndex(i);
                break;
            }
        }
    }

    public void setSelectedOcupacion(int value) {
        Object item;
        Ocupacion objeto;
        for (int i = 0; i < cmbOcupacion.getItemCount(); i++) {
            item = cmbOcupacion.getItemAt(i);
            objeto = (Ocupacion) item;
            if (objeto.getId() == value) {
                cmbOcupacion.setSelectedIndex(i);
                break;
            }
        }
    }

    private void estudios() {
        DefaultComboBoxModel dcbm = this.SERVICIO.estudios();
        cmbNivelEstudios.setModel(dcbm);
    }

    private void ocupaciones() {
        DefaultComboBoxModel dcbm = this.SERVICIO.ocupaciones();
        cmbOcupacion.setModel(dcbm);
    }

    private void etiquetasOnOff(boolean status) {
        lblDatosde.setVisible(status);
        lblNombrePersona.setVisible(status);
    }

    private void restarIngresosyEgresos() {
        if (!txtIngresos.getText().isEmpty() && !txtEgresos.getText().isEmpty()) {
            try {
                double ingresos = Double.valueOf(txtIngresos.getText());
                double egresos = Double.valueOf(txtEgresos.getText());
                double total = ingresos - egresos;
                txtSobrante.setText(String.valueOf(total));
            } catch (NumberFormatException e) {
            }
        }
    }

    private void limpiarCampos() {
        lblNombrePersona.setText("");
        etiquetasOnOff(false);
        txtAdc.setText("");
        txtIngresos.setText("");
        txtEgresos.setText("");
        txtSobrante.setText("");
        txtDependientes.setText("");
        cmbNivelEstudios.setSelectedIndex(0);
        cmbOcupacion.setSelectedIndex(0);
        txtEmpresa.setText("");
        txtDireccion.setText("");
        txtTel.setText("");
        Date hora = new Date();
        SpinnerDateModel sm = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
        txtEntrada.setModel(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(txtEntrada, "HH:mm:ss");
        txtEntrada.setEditor(de);
        SpinnerDateModel sm2 = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
        txtSalida.setModel(sm2);
        JSpinner.DateEditor ded = new JSpinner.DateEditor(txtSalida, "HH:mm:ss");
        txtSalida.setEditor(ded);
    }

    private void cancelar() {
        this.PERSONA_SELECCIONADA = null;
        this.CLIENTE = null;
        etiquetasOnOff(false);
        limpiarCampos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelPrincipal = new javax.swing.JPanel();
        BarraSuperior = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Contenedor = new javax.swing.JPanel();
        panelForm = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtIngresos = new javax.swing.JTextField();
        txtEgresos = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDependientes = new javax.swing.JTextField();
        cmbNivelEstudios = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txtSobrante = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cmbOcupacion = new javax.swing.JComboBox<>();
        txtEmpresa = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtTel = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = null;
        try{
            hora = sdf.parse("00:00:00");
        }catch(Exception e){
        }
        SpinnerDateModel sm = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
        txtEntrada = new javax.swing.JSpinner(sm);
        jLabel3 = new javax.swing.JLabel();
        SpinnerDateModel sm2 = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
        txtSalida = new javax.swing.JSpinner(sm2);
        lblDatosde = new javax.swing.JLabel();
        lblNombrePersona = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtAdc = new javax.swing.JTextField();
        panelTabla = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar2 = new javax.swing.JTextField();
        btnBusqueda = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();

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

        jLabel6.setFont(new java.awt.Font("Solomon Sans Book", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Clientes registrados");
        BarraSuperior.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 85));

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

        panelForm.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Ingrese los datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelForm.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardar.setBackground(new java.awt.Color(244, 0, 100));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });
        btnGuardar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Guardar datos");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        panelForm.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(867, 28, 120, 50));

        btnCancelar.setBackground(new java.awt.Color(204, 0, 0));
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
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        panelForm.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(867, 95, 120, 50));

        jLabel17.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Egresos:");
        panelForm.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(379, 40, -1, 20));

        txtIngresos.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresosKeyTyped(evt);
            }
        });
        panelForm.add(txtIngresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(241, 40, 110, 20));

        txtEgresos.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtEgresos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtEgresos.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtEgresos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEgresosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEgresosKeyTyped(evt);
            }
        });
        panelForm.add(txtEgresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 40, 110, 20));

        jLabel18.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Dependientes económicos:");
        panelForm.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 65, -1, 20));

        txtDependientes.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDependientes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDependientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDependientesKeyTyped(evt);
            }
        });
        panelForm.add(txtDependientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 65, 50, 20));

        cmbNivelEstudios.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panelForm.add(cmbNivelEstudios, new org.netbeans.lib.awtextra.AbsoluteConstraints(669, 65, 180, 20));

        jLabel19.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Empresa: ");
        panelForm.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 20));

        txtSobrante.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtSobrante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSobrante.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtSobrante.setEnabled(false);
        panelForm.add(txtSobrante, new org.netbeans.lib.awtextra.AbsoluteConstraints(668, 40, 110, 20));

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Ocupación:");
        panelForm.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(272, 65, -1, 20));

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Ingresos: ");
        panelForm.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(176, 40, -1, 20));

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Nivel de estudios:");
        panelForm.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(549, 65, -1, 20));

        jLabel24.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Sobrante:");
        panelForm.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(605, 40, -1, 20));

        cmbOcupacion.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panelForm.add(cmbOcupacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(351, 65, 180, 20));

        txtEmpresa.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtEmpresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmpresa.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmpresaKeyReleased(evt);
            }
        });
        panelForm.add(txtEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 750, 20));

        jLabel25.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Dirección:");
        panelForm.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 115, -1, 20));

        txtDireccion.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDireccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccion.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionKeyReleased(evt);
            }
        });
        panelForm.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 115, 750, 20));

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Teléfono:");
        panelForm.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 60, 20));

        txtTel.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtTel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelKeyTyped(evt);
            }
        });
        panelForm.add(txtTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 110, 20));

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Entrada:");
        panelForm.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 60, 20));

        JSpinner.DateEditor de = new JSpinner.DateEditor(txtEntrada, "HH:mm:ss");
        txtEntrada.setEditor(de);
        txtEntrada.setFont(new java.awt.Font("Solomon Sans Book", 0, 12));
        txtEntrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEntradaKeyPressed(evt);
            }
        });
        panelForm.add(txtEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 140, 110, 20));

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Salida:");
        panelForm.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 140, 60, 20));

        JSpinner.DateEditor de2 = new JSpinner.DateEditor(txtSalida, "HH:mm:ss");
        txtSalida.setEditor(de2);
        txtSalida.setFont(new java.awt.Font("Solomon Sans Book", 0, 12));
        txtSalida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSalidaKeyTyped(evt);
            }
        });
        panelForm.add(txtSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 110, 20));

        lblDatosde.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        lblDatosde.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDatosde.setText("Datos de:");
        panelForm.add(lblDatosde, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 68, 20));

        lblNombrePersona.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        lblNombrePersona.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelForm.add(lblNombrePersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 15, 375, 20));

        jLabel23.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("ADC:");
        panelForm.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));

        txtAdc.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAdc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAdc.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        panelForm.add(txtAdc, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 40, 60, 20));

        Contenedor.add(panelForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 25, 1000, 190));

        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Ingrese los datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

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
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addGap(182, 182, 182)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 228, Short.MAX_VALUE))
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        panelTablaLayout.setVerticalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addGroup(panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addContainerGap())
        );

        Contenedor.add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 1000, -1));

        PanelPrincipal.add(Contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1200, 530));

        getContentPane().add(PanelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtIngresosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresosKeyReleased
        restarIngresosyEgresos();
    }//GEN-LAST:event_txtIngresosKeyReleased

    private void txtIngresosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresosKeyTyped
        char c = evt.getKeyChar();
        int lon = txtIngresos.getText().length();
        if (c == '.') {
            int punto = txtIngresos.getText().indexOf(".");
            if (punto > -1) {
                evt.consume();
            }
        }
        if (Character.isWhitespace(c) || Character.isLetter(c) || lon > 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIngresosKeyTyped

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        guardarDatos();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void txtEgresosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEgresosKeyReleased
        restarIngresosyEgresos();
    }//GEN-LAST:event_txtEgresosKeyReleased

    private void txtEgresosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEgresosKeyTyped
        char c = evt.getKeyChar();
        int lon = txtEgresos.getText().length();
        if (c == '.') {
            int punto = txtEgresos.getText().indexOf(".");
            if (punto > -1) {
                evt.consume();
            }
        }
        if (Character.isWhitespace(c) || Character.isLetter(c) || lon > 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEgresosKeyTyped

    private void txtDependientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDependientesKeyTyped
        char c = evt.getKeyChar();
        int lon = txtDependientes.getText().length();
        if (!Character.isDigit(c) || lon >= 2) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDependientesKeyTyped

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        cancelar();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void txtEmpresaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpresaKeyReleased
        String txt = txtEmpresa.getText();
        txtEmpresa.setText(txt.toUpperCase());
    }//GEN-LAST:event_txtEmpresaKeyReleased

    private void txtDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyReleased
        String txt = txtDireccion.getText();
        txtDireccion.setText(txt.toUpperCase());
    }//GEN-LAST:event_txtDireccionKeyReleased

    private void txtTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelKeyTyped
        int lon = txtTel.getText().length();
        if (lon >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTelKeyTyped

    private void txtEntradaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntradaKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEntradaKeyPressed

    private void txtSalidaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalidaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSalidaKeyTyped

    private void txtBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar2ActionPerformed
        buscar();
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
        buscar();
    }//GEN-LAST:event_btnBusquedaMouseClicked

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
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            Usuario usuario = null;
            Clientes dialog = new Clientes(new javax.swing.JFrame(), true, usuario);
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
    private javax.swing.JPanel btnBusqueda;
    private javax.swing.JPanel btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JPanel btnGuardar;
    private javax.swing.JComboBox<String> cmbNivelEstudios;
    private javax.swing.JComboBox<String> cmbOcupacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblDatosde;
    private javax.swing.JLabel lblNombrePersona;
    private javax.swing.JPanel panelForm;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTextField txtAdc;
    private javax.swing.JTextField txtBuscar2;
    private javax.swing.JTextField txtDependientes;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEgresos;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JSpinner txtEntrada;
    private javax.swing.JTextField txtIngresos;
    private javax.swing.JSpinner txtSalida;
    private javax.swing.JTextField txtSobrante;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
