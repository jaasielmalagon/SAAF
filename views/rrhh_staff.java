package views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import objects.Adc;
import objects.Estudio;
import objects.Fecha;
import objects.Lista;
import objects.Mes;
import objects.Persona;
import objects.Empleado;
import objects.Usuario;
import services.clientes_service;

/**
 *
 * @author JMalagon
 */
public final class rrhh_staff extends javax.swing.JInternalFrame {

    private final clientes_service SERVICIO;
    private Persona PERSONA_SELECCIONADA = null;
    private Usuario USUARIO = null;
    private Empleado STAFF = null;
    private Adc ADC = null;
    private final rrhh_personas parentView;

    public rrhh_staff(rrhh_personas parentView, Usuario usuario, Persona persona) {
        initComponents();
        this.SERVICIO = new clientes_service();
        this.parentView = parentView;
        this.USUARIO = usuario;
        this.PERSONA_SELECCIONADA = persona;
        if (PERSONA_SELECCIONADA != null) {
            departamentos();
            estudios();
            cargos();
            meses();
            this.STAFF = this.SERVICIO.staff(PERSONA_SELECCIONADA.getIdPersona());
            if (this.STAFF == null) {
                JOptionPane.showMessageDialog(rootPane, "Agregue los datos laborales para: " + PERSONA_SELECCIONADA.toString());
                panelAdc.setVisible(false);
            } else {
                int opc = JOptionPane.showConfirmDialog(rootPane, "¿Desea modificar los datos laborales de " + this.STAFF.getPERSONA().toString() + "?", "¡Confirmación!", JOptionPane.YES_NO_OPTION);
                if (opc == JOptionPane.YES_OPTION) {
                    cargarDatos();
                } else {
                    cerrarVentana();
                }
            }
            lblNombrePersona.setText(PERSONA_SELECCIONADA.toString());
            txtMontoPortar.setEnabled(chkPortarEfe.isSelected());
        } else {
            this.parentView.show();
            this.parentView.toFront();
            this.dispose();
        }
    }

    private void portarEfectivo() {
        txtMontoPortar.setEnabled(chkPortarEfe.isSelected());
        if (chkPortarEfe.isSelected()) {
            txtMontoPortar.requestFocus();
        }
    }

    private String diasLaborales() {
        String cad = "";
        if (lunes.isSelected() == true) {
            cad = cad + "Lunes-";
        }
        if (martes.isSelected() == true) {
            cad = cad + "Martes-";
        }
        if (miercoles.isSelected() == true) {
            cad = cad + "Miércoles-";
        }
        if (jueves.isSelected() == true) {
            cad = cad + "Jueves-";
        }
        if (viernes.isSelected() == true) {
            cad = cad + "Viernes-";
        }
        if (sabado.isSelected() == true) {
            cad = cad + "Sábado-";
        }
        if (domingo.isSelected() == true) {
            cad = cad + "Domingo-";
        }
        return cad;
    }

    private void guardarDatos() {
        String dias = this.diasLaborales();
        int salario = Integer.valueOf(txtSueldo.getText());
        int departamento = ((Lista) cmbDepartamento.getSelectedItem()).getID();
        int estudios = ((Estudio) cmbNivelEstudios.getSelectedItem()).getID();
        int cargo = ((Lista) cmbCargo.getSelectedItem()).getID();
        String emergencia = txtEmergencia.getText();
        String entrada = txtEntrada.getValue().toString();
        String[] split = entrada.split(" ");
        entrada = split[3];
        String salida = txtSalida.getValue().toString();
        split = salida.split(" ");
        salida = split[3];
        String fecha = txtAno.getText() + "-" + ((Mes) comboMeses.getSelectedItem()).getNumeroMes() + "-" + txtDia.getText();
        int efectivo;
        int agencia = ((Lista) cmbAgencia.getSelectedItem()).getID();
        if ("".equals(txtMontoPortar.getText())) {
            efectivo = 0;
        } else {
            efectivo = Integer.valueOf(txtMontoPortar.getText());
        }
        int idInsert = 0;
        boolean updated = false;
        if (this.STAFF == null) {
            this.STAFF = new Empleado(0, this.USUARIO.getIdSucursal(), this.USUARIO.getIdUsuario(), "", PERSONA_SELECCIONADA.getIdPersona(),
                    cargo, estudios, departamento, salario, entrada, salida, dias, emergencia, fecha, efectivo, "", PERSONA_SELECCIONADA);
            idInsert = this.SERVICIO.guardarDatosStaff(this.STAFF);
        } else {
            idInsert = this.STAFF.getID_STAFF();
            this.STAFF.setSUCURSAL(this.USUARIO.getIdSucursal());
            this.STAFF.setUSUARIO(this.USUARIO.getIdUsuario());
            this.STAFF.setCARGO(cargo);
            this.STAFF.setESTUDIOS(estudios);
            this.STAFF.setDEPARTAMENTO(departamento);
            this.STAFF.setSALARIO(salario);
            this.STAFF.setENTRADA(entrada);
            this.STAFF.setSALIDA(salida);
            this.STAFF.setDIAS_LABORALES(dias);
            this.STAFF.setCASO_EMERGENCIA(emergencia);
            this.STAFF.setFECHA_INCORPORACION(fecha);
            this.STAFF.setEFECTIVO(efectivo);
            updated = this.SERVICIO.actualizarDatosStaff(this.STAFF);
//            System.out.println(this.STAFF.toString());
        }

        if (updated == true || idInsert > 0) {
            if (cargo == 5 && agencia > 0) {
                int vacante = ((Lista) cmbVacante.getSelectedItem()).getID();
                String mensaje = "NO SE REALIZÓ NINGUNA OPERACIÓN";
                if (vacante > 0 && this.ADC == null) {
                    mensaje = this.SERVICIO.crearADC(this.USUARIO.getIdSucursal(), idInsert, agencia, vacante);                    
                }else if(this.ADC != null && vacante > 0){   
                    mensaje = this.SERVICIO.actualizarADC(this.ADC,this.USUARIO.getIdSucursal(),idInsert,agencia,vacante);    
                }
                JOptionPane.showMessageDialog(rootPane, mensaje);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Datos laborales guardados correctamente.", "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
            }
            cerrarVentana();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Datos laborales no guardados.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatos() {
        try {
            this.ADC = this.SERVICIO.adc(this.USUARIO.getIdSucursal(), this.STAFF.getID_STAFF());
            txtSueldo.setText(String.valueOf(this.STAFF.getSALARIO()));
            setSelectedEstudios(this.STAFF.getESTUDIOS());
            setSelectedDepartamento(this.STAFF.getDEPARTAMENTO());
            setSelectedCargo(this.STAFF.getCARGO());
            txtEmergencia.setText(this.STAFF.getCASO_EMERGENCIA());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date hora = sdf.parse(this.STAFF.getENTRADA());
            SpinnerDateModel sm = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
            txtEntrada.setModel(sm);
            JSpinner.DateEditor de = new JSpinner.DateEditor(txtEntrada, "HH:mm:ss");
            txtEntrada.setEditor(de);
            hora = sdf.parse(this.STAFF.getSALIDA());
            SpinnerDateModel sm2 = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
            txtSalida.setModel(sm2);
            JSpinner.DateEditor ded = new JSpinner.DateEditor(txtSalida, "HH:mm:ss");
            txtSalida.setEditor(ded);
            txtDia.setText(this.STAFF.getFECHA_INCORPORACION().substring(8, 10));
            int mes = Integer.parseInt(PERSONA_SELECCIONADA.getF_nac().substring(5, 7));
            comboMeses.setSelectedIndex(mes - 1);
            txtAno.setText(this.STAFF.getFECHA_INCORPORACION().substring(0, 4));
            marcarDias();
            if (this.STAFF.getEFECTIVO() > 0) {
                chkPortarEfe.setSelected(true);
                txtMontoPortar.setText(String.valueOf(this.STAFF.getEFECTIVO()));
            }
            if (this.ADC != null) {
                cargaDatosAdc();
            }
        } catch (ParseException ex) {
            System.out.println("views.clientes.cargarDatosCliente() : " + ex);
        }
    }

    private void marcarDias() {
        lunes.setSelected(false);
        martes.setSelected(false);
        miercoles.setSelected(false);
        jueves.setSelected(false);
        viernes.setSelected(false);
        sabado.setSelected(false);
        domingo.setSelected(false);
        String dias = this.STAFF.getDIAS_LABORALES();
        String[] diasSplit = dias.split("-");
        int splits = diasSplit.length;
        System.out.println(Arrays.toString(diasSplit));
        for (int i = 0; i < splits; i++) {
            if ("Lunes".equals(diasSplit[i])) {
                lunes.setSelected(true);
            }
            if ("Martes".equals(diasSplit[i])) {
                martes.setSelected(true);
            }
            if ("Miércoles".equals(diasSplit[i])) {
                miercoles.setSelected(true);
            }
            if ("Jueves".equals(diasSplit[i])) {
                jueves.setSelected(true);
            }
            if ("Viernes".equals(diasSplit[i])) {
                viernes.setSelected(true);
            }
            if ("Sábado".equals(diasSplit[i])) {
                sabado.setSelected(true);
            }
            if ("Domingo".equals(diasSplit[i])) {
                domingo.setSelected(true);
            }
        }
    }

    private void setSelectedEstudios(int value) {
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

    private void setSelectedCargo(int value) {
        Object item;
        Lista objeto;
        for (int i = 0; i < cmbCargo.getItemCount(); i++) {
            item = cmbCargo.getItemAt(i);
            objeto = (Lista) item;
            if (objeto.getID() == value) {
                cmbCargo.setSelectedIndex(i);
                break;
            }
        }
    }

    private void cargos() {
        DefaultComboBoxModel dcbm = this.SERVICIO.cargos();
        cmbCargo.setModel(dcbm);
    }

    private void estudios() {
        DefaultComboBoxModel dcbm = this.SERVICIO.estudios();
        cmbNivelEstudios.setModel(dcbm);
    }

    private void departamentos() {
        DefaultComboBoxModel dcbm = this.SERVICIO.departamentos();
        cmbDepartamento.setModel(dcbm);
    }

    private void limpiarCampos() {
        lblNombrePersona.setText("");
        txtSueldo.setText("");
        cmbNivelEstudios.setSelectedIndex(0);
        cmbDepartamento.setSelectedIndex(0);
        txtEmergencia.setText("");
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
        this.STAFF = null;
        limpiarCampos();
    }

    private void meses() {
        Fecha f = new Fecha();
        Mes[] meses = f.meses();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Mes mes : meses) {
            dcbm.addElement(mes);
        }
        comboMeses.setModel(dcbm);
    }

    private void cerrarVentana() {
        cancelar();
        this.parentView.show();
        this.parentView.toFront();
        this.dispose();
    }

    private void cargaDatosAdc() {
        panelAdc.setVisible(true);
        cmbAgencia.setModel(this.SERVICIO.agencias(this.USUARIO.getIdSucursal()));
        Object item;
        Lista objeto;
        for (int i = 0; i < cmbAgencia.getItemCount(); i++) {
            item = cmbAgencia.getItemAt(i);
            objeto = (Lista) item;
            if (objeto.getID() == this.ADC.getAGENCIA()) {
                cmbAgencia.setSelectedIndex(i);
                break;
            }
        }
        lblVacante.setText("Actualmente: Vacante " + this.ADC.getVACANTE());
    }

    public void desactivarPanelAdc() {
        panelAdc.setVisible(false);
        cmbAgencia.removeAllItems();
        lblVacante.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelFormulario = new javax.swing.JPanel();
        txtSueldo = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbNivelEstudios = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cmbDepartamento = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtEmergencia = new javax.swing.JTextField();
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
        cmbCargo = new javax.swing.JComboBox<>();
        lunes = new javax.swing.JCheckBox();
        martes = new javax.swing.JCheckBox();
        miercoles = new javax.swing.JCheckBox();
        jueves = new javax.swing.JCheckBox();
        viernes = new javax.swing.JCheckBox();
        sabado = new javax.swing.JCheckBox();
        domingo = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        txtDia = new javax.swing.JTextField();
        comboMeses = new javax.swing.JComboBox<>();
        txtAno = new javax.swing.JTextField();
        lblDatosde1 = new javax.swing.JLabel();
        chkPortarEfe = new javax.swing.JCheckBox();
        txtMontoPortar = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        panelAdc = new javax.swing.JPanel();
        lblDatosde2 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cmbAgencia = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cmbVacante = new javax.swing.JComboBox<>();
        lblVacante = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setFont(new java.awt.Font("Solomon Sans Book", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(1360, 582));

        jPanel4.setBackground(new java.awt.Color(244, 0, 100));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        panelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingrese los datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        txtSueldo.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtSueldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSueldo.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtSueldo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSueldoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSueldoKeyTyped(evt);
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

        cmbNivelEstudios.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel19.setText("Cargo:");

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel20.setText("Departamento:");

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel16.setText("Percepción económica:");

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

        cmbDepartamento.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel25.setText("Días laborales:");

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel1.setText("En caso de emergencia llamar a:");

        txtEmergencia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtEmergencia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmergencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmergenciaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmergenciaKeyTyped(evt);
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

        lblDatosde.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        lblDatosde.setText("Datos de:");

        lblNombrePersona.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N

        cmbCargo.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCargoActionPerformed(evt);
            }
        });

        lunes.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        lunes.setSelected(true);
        lunes.setText("Lunes");

        martes.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        martes.setSelected(true);
        martes.setText("Martes");

        miercoles.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        miercoles.setSelected(true);
        miercoles.setText("Miércoles");

        jueves.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        jueves.setSelected(true);
        jueves.setText("Jueves");

        viernes.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        viernes.setSelected(true);
        viernes.setText("Viernes");

        sabado.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        sabado.setSelected(true);
        sabado.setText("Sábado");

        domingo.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        domingo.setText("Domingo");

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel4.setText("Fecha de incorporación:");

        txtDia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiaKeyTyped(evt);
            }
        });

        comboMeses.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        txtAno.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAnoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnoKeyTyped(evt);
            }
        });

        lblDatosde1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        lblDatosde1.setText("Monto máximo:");

        chkPortarEfe.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        chkPortarEfe.setText("Autorizado portar efectivo");
        chkPortarEfe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkPortarEfeItemStateChanged(evt);
            }
        });

        txtMontoPortar.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtMontoPortar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMontoPortar.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtMontoPortar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoPortarKeyTyped(evt);
            }
        });

        panelAdc.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblDatosde2.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        lblDatosde2.setText("Datos para ADC");

        jLabel22.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel22.setText("Agencia:");

        cmbAgencia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbAgencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAgenciaActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel23.setText("Vacante:");

        cmbVacante.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        lblVacante.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        lblVacante.setText("Vacante:");

        javax.swing.GroupLayout panelAdcLayout = new javax.swing.GroupLayout(panelAdc);
        panelAdc.setLayout(panelAdcLayout);
        panelAdcLayout.setHorizontalGroup(
            panelAdcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdcLayout.createSequentialGroup()
                .addGroup(panelAdcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAdcLayout.createSequentialGroup()
                        .addGap(427, 427, 427)
                        .addComponent(lblDatosde2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelAdcLayout.createSequentialGroup()
                        .addGap(271, 271, 271)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbVacante, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblVacante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelAdcLayout.setVerticalGroup(
            panelAdcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdcLayout.createSequentialGroup()
                .addComponent(lblDatosde2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAdcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(cmbVacante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(lblVacante))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelFormularioLayout = new javax.swing.GroupLayout(panelFormulario);
        panelFormulario.setLayout(panelFormularioLayout);
        panelFormularioLayout.setHorizontalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFormularioLayout.createSequentialGroup()
                                .addComponent(chkPortarEfe)
                                .addGap(18, 18, 18)
                                .addComponent(lblDatosde1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMontoPortar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelFormularioLayout.createSequentialGroup()
                                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelFormularioLayout.createSequentialGroup()
                                        .addComponent(jLabel25)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lunes)
                                        .addGap(30, 30, 30)
                                        .addComponent(martes)
                                        .addGap(30, 30, 30)
                                        .addComponent(miercoles)
                                        .addGap(30, 30, 30)
                                        .addComponent(jueves)
                                        .addGap(24, 24, 24)
                                        .addComponent(viernes)
                                        .addGap(30, 30, 30)
                                        .addComponent(sabado)
                                        .addGap(30, 30, 30)
                                        .addComponent(domingo))
                                    .addGroup(panelFormularioLayout.createSequentialGroup()
                                        .addComponent(lblDatosde)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblNombrePersona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panelFormularioLayout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbNivelEstudios, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelFormularioLayout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelFormularioLayout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtEmergencia))
                                    .addGroup(panelFormularioLayout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(comboMeses, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 21, Short.MAX_VALUE)
                                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelAdc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        panelFormularioLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtEntrada, txtSalida});

        panelFormularioLayout.setVerticalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombrePersona)
                            .addComponent(lblDatosde))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbNivelEstudios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(cmbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(cmbCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(lunes)
                            .addComponent(martes)
                            .addComponent(miercoles)
                            .addComponent(jueves)
                            .addComponent(viernes)
                            .addComponent(sabado)
                            .addComponent(domingo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboMeses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(txtSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtEmergencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkPortarEfe)
                    .addComponent(lblDatosde1)
                    .addComponent(txtMontoPortar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        panelFormularioLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblDatosde, lblNombrePersona, txtSueldo});

        panelFormularioLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtEmergencia, txtEntrada, txtSalida});

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(189, 0, 53));

        jLabel6.setFont(new java.awt.Font("Solomon Sans Book", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Datos del empleado");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cerrar.png"))); // NOI18N
        jLabel11.setToolTipText("Cerrar");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        guardarDatos();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void txtSueldoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSueldoKeyTyped
        char c = evt.getKeyChar();
        int lon = txtSueldo.getText().length();
//        if (c == '.') {
//            int punto = txtSueldo.getText().indexOf(".");
//            if (punto > -1) {
//                evt.consume();
//            }
//        }
        if (Character.isWhitespace(c) || Character.isLetter(c) || lon > 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSueldoKeyTyped

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        cancelar();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.parentView.show();
        this.parentView.toFront();
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void txtEmergenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmergenciaKeyTyped
        int lon = txtEmergencia.getText().length();
        if (lon >= 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEmergenciaKeyTyped

    private void txtSalidaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalidaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSalidaKeyTyped

    private void txtEntradaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntradaKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEntradaKeyPressed

    private void txtSueldoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSueldoKeyReleased

    }//GEN-LAST:event_txtSueldoKeyReleased

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
        char c = evt.getKeyChar();
        if (lon >= 4 || !Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAnoKeyTyped

    private void txtMontoPortarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoPortarKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoPortarKeyTyped

    private void chkPortarEfeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkPortarEfeItemStateChanged
        portarEfectivo();
    }//GEN-LAST:event_chkPortarEfeItemStateChanged

    private void txtEmergenciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmergenciaKeyReleased
        String cadena = txtEmergencia.getText().toUpperCase();
        txtEmergencia.setText(cadena);
    }//GEN-LAST:event_txtEmergenciaKeyReleased

    private void cmbCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCargoActionPerformed
        int cargo = ((Lista) cmbCargo.getSelectedItem()).getID();
        if (cargo == 5) {
            panelAdc.setVisible(true);
            cmbAgencia.setModel(this.SERVICIO.agencias(this.USUARIO.getIdSucursal()));
        } else {
            desactivarPanelAdc();
        }
    }//GEN-LAST:event_cmbCargoActionPerformed

    private void cmbAgenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAgenciaActionPerformed
        try {
            int zona = ((Lista) cmbAgencia.getSelectedItem()).getID();
            if (zona > 0) {
                cmbVacante.setModel(this.SERVICIO.vacantes(this.USUARIO.getIdSucursal(), zona));                
            } else {
                cmbVacante.removeAllItems();
            }
        } catch (Exception e) {
            System.out.println("views.rrhh_staff.cmbAgenciaActionPerformed() " + e);
        }
    }//GEN-LAST:event_cmbAgenciaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnCancelar;
    private javax.swing.JPanel btnGuardar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkPortarEfe;
    private javax.swing.JComboBox<String> cmbAgencia;
    private javax.swing.JComboBox<String> cmbCargo;
    private javax.swing.JComboBox<String> cmbDepartamento;
    private javax.swing.JComboBox<String> cmbNivelEstudios;
    private javax.swing.JComboBox<String> cmbVacante;
    private javax.swing.JComboBox<String> comboMeses;
    private javax.swing.JCheckBox domingo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox jueves;
    private javax.swing.JLabel lblDatosde;
    private javax.swing.JLabel lblDatosde1;
    private javax.swing.JLabel lblDatosde2;
    private javax.swing.JLabel lblNombrePersona;
    private javax.swing.JLabel lblVacante;
    private javax.swing.JCheckBox lunes;
    private javax.swing.JCheckBox martes;
    private javax.swing.JCheckBox miercoles;
    private javax.swing.JPanel panelAdc;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JCheckBox sabado;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtDia;
    private javax.swing.JTextField txtEmergencia;
    private javax.swing.JSpinner txtEntrada;
    private javax.swing.JTextField txtMontoPortar;
    private javax.swing.JSpinner txtSalida;
    private javax.swing.JTextField txtSueldo;
    private javax.swing.JCheckBox viernes;
    // End of variables declaration//GEN-END:variables

}
//    private void cargarImagen() {
//        try {
//            BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/image/fondo-menu.png"));
//            Graficos graf = new Graficos(img);
//            contenedor.setBorder(graf);
//        } catch (IOException ex) {
//            System.err.println("Error al cargar el fondo de menú: " + ex);
//        }
//    }
