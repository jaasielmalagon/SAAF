package views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
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
 * @author JMalagon
 */
public class ClienteAdd extends javax.swing.JDialog {

    private final clientes_service SERVICIO;
//    private int ID_PERSONA_SELECCIONADA = 0;
    private Persona PERSONA_SELECCIONADA = null;
    private Usuario USUARIO = null;
    private Cliente CLIENTE = null;

    public ClienteAdd(JDialog parent, boolean modal, Usuario usuario, Persona persona) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        tituloVentana.setText(tituloVentana.getText() + " " + persona.toString());
        
        this.SERVICIO = new clientes_service(this.getClass().toString());
        this.USUARIO = usuario;
        this.PERSONA_SELECCIONADA = persona;
        if (PERSONA_SELECCIONADA != null) {            
            ocupaciones();
            estudios();
            CLIENTE = SERVICIO.cliente(PERSONA_SELECCIONADA);
            if (CLIENTE != null) {
                cargarDatosCliente();
            }
        }
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
                        } else {
                            cancelar();
                        }
                    } else if (insert == 0) {
                        JOptionPane.showMessageDialog(rootPane, "Los datos no se guardaron correctamente.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    } else if (insert > 0) {
                        JOptionPane.showMessageDialog(rootPane, "Datos guardados correctamente.", "¡Extio!", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }
                } else if (CLIENTE != null) {
                    cliente = new Cliente(CLIENTE.getIdCliente(), this.USUARIO.getIdSucursal(), this.USUARIO.getIdUsuario(), CLIENTE.getF_REGISTRO(), adc,
                            CLIENTE.getID_PERSONA(), ingresos, egresos, dependientes, ocupacion, estudios, empresa,
                            direccion, telefono, entrada, salida, CLIENTE.getSCORE(), CLIENTE.getSTATUS(), 1, PERSONA_SELECCIONADA);
                    int update = this.SERVICIO.actualizarDatos(cliente);
                    switch (update) {
                        case 1:
                            JOptionPane.showMessageDialog(rootPane, "Datos guardados correctamente.", "¡Exito!", JOptionPane.INFORMATION_MESSAGE);
                            cancelar();                            
                            this.dispose();
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
        limpiarCampos();
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
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtIngresos = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtEgresos = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDependientes = new javax.swing.JTextField();
        cmbNivelEstudios = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txtSobrante = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
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
        jLabel23 = new javax.swing.JLabel();
        txtAdc = new javax.swing.JTextField();

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
        tituloVentana.setText("Agregar datos del cliente:");
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

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ingrese los datos a continuación", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel17.setText("Egresos:");

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

        btnGuardar.setBackground(new java.awt.Color(244, 0, 100));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Guardar datos");

        javax.swing.GroupLayout btnGuardarLayout = new javax.swing.GroupLayout(btnGuardar);
        btnGuardar.setLayout(btnGuardarLayout);
        btnGuardarLayout.setHorizontalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGuardarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(51, 51, 51))
        );
        btnGuardarLayout.setVerticalGroup(
            btnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGuardarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addContainerGap(19, Short.MAX_VALUE))
        );

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

        jLabel18.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel18.setText("Dependientes económicos:");

        txtDependientes.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDependientes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDependientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDependientesKeyTyped(evt);
            }
        });

        cmbNivelEstudios.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel19.setText("Empresa: ");

        txtSobrante.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtSobrante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSobrante.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtSobrante.setEnabled(false);

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel20.setText("Ocupación:");

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel16.setText("Ingresos: ");

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel21.setText("Nivel de estudios:");

        btnCancelar.setBackground(new java.awt.Color(204, 0, 0));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Cancelar");

        javax.swing.GroupLayout btnCancelarLayout = new javax.swing.GroupLayout(btnCancelar);
        btnCancelar.setLayout(btnCancelarLayout);
        btnCancelarLayout.setHorizontalGroup(
            btnCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCancelarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(36, 36, 36))
        );
        btnCancelarLayout.setVerticalGroup(
            btnCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCancelarLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel8)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel24.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel24.setText("Sobrante:");

        cmbOcupacion.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        txtEmpresa.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtEmpresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmpresa.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmpresaKeyReleased(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel25.setText("Dirección:");

        txtDireccion.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDireccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccion.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel1.setText("Teléfono:");

        txtTel.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtTel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel2.setText("Entrada:");

        JSpinner.DateEditor de = new JSpinner.DateEditor(txtEntrada, "HH:mm:ss");
        txtEntrada.setEditor(de);
        txtEntrada.setFont(new java.awt.Font("Solomon Sans Book", 0, 12));
        txtEntrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEntradaKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel3.setText("Salida:");

        JSpinner.DateEditor de2 = new JSpinner.DateEditor(txtSalida, "HH:mm:ss");
        txtSalida.setEditor(de2);
        txtSalida.setFont(new java.awt.Font("Solomon Sans Book", 0, 12));
        txtSalida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSalidaKeyTyped(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel23.setText("ADC:");

        txtAdc.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAdc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAdc.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel18)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtDependientes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(24, 24, 24)
                            .addComponent(jLabel20)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cmbOcupacion, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel21)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cmbNivelEstudios, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel19)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtEmpresa))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel25)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDireccion))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtAdc, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(jLabel17)
                            .addGap(14, 14, 14)
                            .addComponent(txtEgresos, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50)
                            .addComponent(jLabel24)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSobrante, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(71, 71, 71)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(txtEgresos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSobrante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23)
                            .addComponent(txtAdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtDependientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(cmbOcupacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(cmbNivelEstudios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(txtSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Contenedor.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 1000, 180));

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
            java.util.logging.Logger.getLogger(ClienteAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            Usuario usuario = null;
            Persona persona = null;
            ClienteAdd dialog = new ClienteAdd(new JDialog(), true, usuario, persona);
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
    private javax.swing.JPanel btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JPanel btnGuardar;
    private javax.swing.JComboBox<String> cmbNivelEstudios;
    private javax.swing.JComboBox<String> cmbOcupacion;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JTextField txtAdc;
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
