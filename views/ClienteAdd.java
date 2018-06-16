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
import objects.Lista;
import objects.Mes;
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
        if (this.PERSONA_SELECCIONADA != null) {
            adcs();
            ocupaciones();
            estudios();
            meses();
            limpiarCampos();
            System.out.println(this.PERSONA_SELECCIONADA.toString());
            this.CLIENTE = SERVICIO.cliente(this.PERSONA_SELECCIONADA);
            if (this.CLIENTE != null) {
                cargarDatosCliente();
            }
        }
    }
    
    private void adcs() {
        cmbAdc.setModel(this.SERVICIO.comboAdc(this.USUARIO));
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
                String direccion_empresa = txtDireccion.getText();
                String telefono = txtTel.getText();
                String entrada = txtEntrada.getValue().toString();
                String[] split = entrada.split(" ");
                entrada = split[3];
                String salida = txtSalida.getValue().toString();
                split = salida.split(" ");
                salida = split[3];
                int adc = ((Lista) cmbAdc.getSelectedItem()).getID();
                int tipo = comboTipoVivienda.getSelectedIndex();
                String vigencia = txtAno.getText() + "-" + ((Mes) comboMeses.getSelectedItem()).getNumeroMes() + "-" + txtDia.getText();
                String propietario = txtPropietario.getText();
                String tiempoResidencia = txtAnosResidencia.getText();

                Cliente cliente = new Cliente();
                if (CLIENTE == null) {
                    cliente.setSUCURSAL(this.USUARIO.getIdSucursal());
                    cliente.setUSUARIO(this.USUARIO.getIdUsuario());
                    cliente.setADC(adc);
                    cliente.setID_PERSONA(PERSONA_SELECCIONADA.getIdPersona());
                    cliente.setINGRESOS(ingresos);
                    cliente.setEGRESOS(egresos);
                    cliente.setDEPENDIENTES(dependientes);
                    cliente.setOCUPACION(ocupacion);
                    cliente.setESTUDIOS(estudios);
                    cliente.setEMPRESA(empresa);
                    cliente.setDOMICILIO_EMPRESA(direccion_empresa);
                    cliente.setTEL_EMPRESA(telefono);
                    cliente.setHORA_ENTRADA(entrada);
                    cliente.setHORA_SALIDA(salida);
                    cliente.setTIPO_VIVIENDA(tipo);
                    cliente.setPROPIETARIO(propietario);
                    cliente.setVIGENCIA(vigencia);
                    cliente.setTIEMPO_RESIDENCIA(tiempoResidencia);
                    cliente.setPERSONA(PERSONA_SELECCIONADA);
                    int insert = this.SERVICIO.guardarDatosCliente(cliente);
                    cargarDatosCliente();
                    if (insert == -1) {
                        int x = JOptionPane.showConfirmDialog(rootPane, "Esta persona ya se encuentra registrada como cliente..", "¡Error!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                        if (x == 1) {
                            CLIENTE = this.SERVICIO.cliente(PERSONA_SELECCIONADA);
                            cargarDatosCliente();
                        } else {
                            limpiarCampos();
                        }
                    } else if (insert == 0) {
                        JOptionPane.showMessageDialog(rootPane, "Los datos no se guardaron correctamente.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    } else if (insert > 0) {
                        JOptionPane.showMessageDialog(rootPane, "Datos guardados correctamente.", "¡Extio!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (CLIENTE != null) {
                    cliente = CLIENTE;
                    cliente.setSUCURSAL(this.USUARIO.getIdSucursal());
                    cliente.setUSUARIO(this.USUARIO.getIdUsuario());
                    cliente.setADC(adc);
                    cliente.setINGRESOS(ingresos);
                    cliente.setEGRESOS(egresos);
                    cliente.setDEPENDIENTES(dependientes);
                    cliente.setOCUPACION(ocupacion);
                    cliente.setESTUDIOS(estudios);
                    cliente.setEMPRESA(empresa);
                    cliente.setDOMICILIO_EMPRESA(direccion_empresa);
                    cliente.setTEL_EMPRESA(telefono);
                    cliente.setHORA_ENTRADA(entrada);
                    cliente.setHORA_SALIDA(salida);
                    cliente.setTIPO_VIVIENDA(tipo);
                    cliente.setPROPIETARIO(propietario);
                    cliente.setVIGENCIA(vigencia);
                    cliente.setTIEMPO_RESIDENCIA(tiempoResidencia);
                    int update = this.SERVICIO.actualizarDatos(cliente);
                    switch (update) {
                        case 1:
                            JOptionPane.showMessageDialog(rootPane, "Datos guardados correctamente.", "¡Extio!", JOptionPane.INFORMATION_MESSAGE);
                            cargarDatosCliente();
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
            setSelectedAdc(CLIENTE.getADC());            
            comboTipoVivienda.setSelectedIndex(CLIENTE.getTIPO_VIVIENDA());
            if (CLIENTE.getTIPO_VIVIENDA() > 1) {
                txtAnosResidencia.setText(String.valueOf(CLIENTE.getTIEMPO_RESIDENCIA()));
                txtPropietario.setText(CLIENTE.getPROPIETARIO());
                String vigencia = CLIENTE.getVIGENCIA();
                txtAno.setText(vigencia.substring(0, 4));
                comboMeses.setSelectedIndex(Integer.valueOf(vigencia.substring(5, 7)) + 1);
                txtDia.setText(vigencia.substring(8, 10));
            }
            switch (CLIENTE.getSTATUS()) {
                case 1:
                    lblEstado.setText("ESTABLE");
                    break;
                case 2:
                    lblEstado.setText("EN MORA");
                    break;
                case 3:
                    lblEstado.setText("NEGACIÓN DE PAGO");
                    break;
                default:
                    lblEstado.setText("N/D");
                    break;
            }
            btnEliminar.setEnabled(true);
            if (CLIENTE.getACTIVIDAD() == 0) {
                lblOrb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/red-orb.png")));
                lblEstado.setText("Deshabilitado");
                lblActividad.setText("Habilitar");
                btnEliminar.setBackground(new java.awt.Color(115, 189, 99));
            } else {
                lblOrb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/green-orb.png")));
                lblEstado.setText("Habilitado");
                lblActividad.setText("Inhabilitar");
                btnEliminar.setBackground(new java.awt.Color(204, 0, 0));
            }
        } catch (ParseException ex) {
            System.out.println("views.clientes.cargarDatosCliente() : " + ex);
        }
    }

    private boolean comprobarCampos() {
        boolean flag = true;
        char[] charArray;
        if (((Lista) cmbAdc.getSelectedItem()).getID() == 0) {
            flag = false;
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
    
    private void setSelectedAdc(int value) {
        Object item;
        Lista objeto;
        for (int i = 0; i < cmbAdc.getItemCount(); i++) {
            item = cmbAdc.getItemAt(i);
            objeto = (Lista) item;
            if (objeto.getID() == value) {
                cmbAdc.setSelectedIndex(i);
                break;
            }
        }
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
        cmbAdc.setSelectedIndex(0);
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
        txtDireccion.setText("");
        txtPropietario.setText("");
        comboTipoVivienda.setSelectedIndex(0);
        btnEliminar.setEnabled(false);
        lblEstado.setText("");
        lblOrb.setIcon(null);
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
        Mes[] meses = this.SERVICIO.meses();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Mes mes : meses) {
            dcbm.addElement(mes);
        }
        comboMeses.setModel(dcbm);
    }

    private void desactivarCliente() {
        int x = JOptionPane.showConfirmDialog(rootPane, "¿Desea cambiar el estado de actividad a este cliente?", "¡ATENCIÓN!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (x == JOptionPane.YES_OPTION) {
            int act = 0;
            if (CLIENTE.getACTIVIDAD() == 0) {
                act = 1;
            }
            boolean dc = this.SERVICIO.desactivarCliente(this.CLIENTE, act);
            if (dc) {
                JOptionPane.showMessageDialog(rootPane, "Estado de actividad cambiado correctamente.", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosCliente();
            }
        }
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
        panelForm = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JPanel();
        lblActividad = new javax.swing.JLabel();
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
        jLabel23 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboTipoVivienda = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        txtDia = new javax.swing.JTextField();
        comboMeses = new javax.swing.JComboBox<>();
        txtAno = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtAnosResidencia = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtPropietario = new javax.swing.JTextField();
        lblEstad = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        lblOrb = new javax.swing.JLabel();
        cmbAdc = new javax.swing.JComboBox<>();

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

        panelForm.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 50, 120, 50));

        btnCancelar.setBackground(new java.awt.Color(255, 78, 0));
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

        panelForm.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 120, 120, 50));

        btnEliminar.setBackground(new java.awt.Color(204, 0, 0));
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });
        btnEliminar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblActividad.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        lblActividad.setForeground(new java.awt.Color(255, 255, 255));
        lblActividad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActividad.setText("Inhabilitar");
        lblActividad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.add(lblActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        panelForm.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 190, 120, 50));

        jLabel17.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Egresos:");
        panelForm.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, 20));

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
        panelForm.add(txtIngresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 110, 20));

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
        panelForm.add(txtEgresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 110, 20));

        jLabel18.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Dependientes económicos:");
        panelForm.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 20));

        txtDependientes.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDependientes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDependientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDependientesKeyTyped(evt);
            }
        });
        panelForm.add(txtDependientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 50, 20));

        cmbNivelEstudios.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panelForm.add(cmbNivelEstudios, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 80, 180, 20));

        jLabel19.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Empresa: ");
        panelForm.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 20));

        txtSobrante.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtSobrante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSobrante.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtSobrante.setEnabled(false);
        panelForm.add(txtSobrante, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 110, 20));

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Ocupación:");
        panelForm.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, -1, 20));

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Ingresos: ");
        panelForm.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, 20));

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Nivel de estudios:");
        panelForm.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, -1, 20));

        jLabel24.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Sobrante:");
        panelForm.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, -1, 20));

        cmbOcupacion.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panelForm.add(cmbOcupacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 180, 20));

        txtEmpresa.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtEmpresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmpresa.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmpresaKeyReleased(evt);
            }
        });
        panelForm.add(txtEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 760, 20));

        jLabel25.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Dirección:");
        panelForm.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 20));

        txtDireccion.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDireccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccion.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionKeyReleased(evt);
            }
        });
        panelForm.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 760, 20));

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Teléfono:");
        panelForm.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 60, 20));

        txtTel.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtTel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelKeyTyped(evt);
            }
        });
        panelForm.add(txtTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 110, 20));

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Entrada:");
        panelForm.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 60, 20));

        JSpinner.DateEditor de = new JSpinner.DateEditor(txtEntrada, "HH:mm:ss");
        txtEntrada.setEditor(de);
        txtEntrada.setFont(new java.awt.Font("Solomon Sans Book", 0, 12));
        txtEntrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEntradaKeyPressed(evt);
            }
        });
        panelForm.add(txtEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, 110, 20));

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Salida:");
        panelForm.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, 60, 20));

        JSpinner.DateEditor de2 = new JSpinner.DateEditor(txtSalida, "HH:mm:ss");
        txtSalida.setEditor(de2);
        txtSalida.setFont(new java.awt.Font("Solomon Sans Book", 0, 12));
        txtSalida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSalidaKeyTyped(evt);
            }
        });
        panelForm.add(txtSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 170, 110, 20));

        jLabel23.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("ADC:");
        panelForm.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 20));

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel4.setText("Tipo de vivienda:");
        panelForm.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 110, 20));

        comboTipoVivienda.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboTipoVivienda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-----", "Propia", "Rentada", "Prestada" }));
        comboTipoVivienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoViviendaActionPerformed(evt);
            }
        });
        panelForm.add(comboTipoVivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 100, 20));

        jLabel26.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel26.setText("Vigencia de renta");
        panelForm.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, -1, 20));

        txtDia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiaKeyTyped(evt);
            }
        });
        panelForm.add(txtDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 80, 20));

        comboMeses.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panelForm.add(comboMeses, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, 120, 20));

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
        panelForm.add(txtAno, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 200, 80, 20));

        jLabel28.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel28.setText("Años de residencia:");
        panelForm.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 200, -1, 20));

        txtAnosResidencia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidenciaKeyTyped(evt);
            }
        });
        panelForm.add(txtAnosResidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 200, 60, 20));

        jLabel27.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel27.setText("Propietario:");
        panelForm.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, 20));

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
        panelForm.add(txtPropietario, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 580, 20));

        lblEstad.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        lblEstad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEstad.setText("Estado");
        panelForm.add(lblEstad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 40, 20));

        lblEstado.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelForm.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 140, 20));
        panelForm.add(lblOrb, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 40, 20));

        cmbAdc.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbAdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAdcActionPerformed(evt);
            }
        });
        panelForm.add(cmbAdc, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 100, 20));

        Contenedor.add(panelForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 25, 1000, 260));

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

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        guardarDatos();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        limpiarCampos();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        if (CLIENTE != null) {
            desactivarCliente();
        } else {
            JOptionPane.showMessageDialog(rootPane, "No ha seleccionado una persona existente.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarMouseClicked

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

    private void cmbAdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAdcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAdcActionPerformed

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
    private javax.swing.JPanel btnEliminar;
    private javax.swing.JPanel btnGuardar;
    private javax.swing.JComboBox<String> cmbAdc;
    private javax.swing.JComboBox<String> cmbNivelEstudios;
    private javax.swing.JComboBox<String> cmbOcupacion;
    private javax.swing.JComboBox<String> comboMeses;
    private javax.swing.JComboBox<String> comboTipoVivienda;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblActividad;
    private javax.swing.JLabel lblEstad;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblOrb;
    private javax.swing.JPanel panelForm;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtAnosResidencia;
    private javax.swing.JTextField txtDependientes;
    private javax.swing.JTextField txtDia;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEgresos;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JSpinner txtEntrada;
    private javax.swing.JTextField txtIngresos;
    private javax.swing.JTextField txtPropietario;
    private javax.swing.JSpinner txtSalida;
    private javax.swing.JTextField txtSobrante;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
