package views;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import objects.Empleado;
import objects.Estudio;
import objects.Fecha;
import objects.Lista;
import objects.Mes;
import objects.Persona;
import objects.Usuario;
import services.clientes_service;

/**
 *
 * @author Root
 */
public class Empleados extends javax.swing.JDialog {

    private final clientes_service SERVICIO;
    private Persona PERSONA_SELECCIONADA = null;
    private Usuario USUARIO = null;
    private Empleado EMPLEADO = null;
    private Adc ADC = null;

    public Empleados(java.awt.Frame parent, boolean modal, Usuario usuario, Persona persona) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);

        tituloVentana.setText(tituloVentana.getText() + " " + usuario.getIdSucursal());
        this.SERVICIO = new clientes_service(this.getClass().toString());
        this.USUARIO = usuario;
        this.PERSONA_SELECCIONADA = persona;
        estudios();
        cargos();
        meses();
        departamentos();
        cargarDatosEmpleado();
        llenarTabla("");
        seleccionarPersona();
    }

    public void seleccionarPersona() {
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                if (Mouse_evt.getClickCount() == 1) {
                    int id = Integer.parseInt(tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0).toString());
                    try {
                        PERSONA_SELECCIONADA = SERVICIO.persona(USUARIO.getIdSucursal(), id);
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
            tablaClientes = this.SERVICIO.tablaPersonas(tablaClientes, this.USUARIO.getIdSucursal(), dato);
        } else {
            tablaClientes = this.SERVICIO.tablaEmpleados(tablaClientes, this.USUARIO.getIdSucursal(), dato);
        }
    }

    private void cargarDatosEmpleado() {
        if (PERSONA_SELECCIONADA != null) {
            lblNombrePersona.setText(PERSONA_SELECCIONADA.toString());
            this.EMPLEADO = this.SERVICIO.empleado(PERSONA_SELECCIONADA.getIdPersona());
            if (this.EMPLEADO == null) {
                JOptionPane.showMessageDialog(rootPane, "Agregue los datos laborales para: " + PERSONA_SELECCIONADA.toString());
                limpiarCampos();
            } else {
                int opc = JOptionPane.showConfirmDialog(rootPane, "¿Desea modificar los datos laborales de " + this.EMPLEADO.getPERSONA().toString() + "?", "¡Confirmación!", JOptionPane.YES_NO_OPTION);
                if (opc == JOptionPane.YES_OPTION) {
                    cargarDatos();
                } else {
                    cancelar();
                }
            }
            //txtMontoPortar.setEnabled(chkPortarEfe.isSelected());
        } else {
            desactivarPanelAdc();
        }
    }

    private void limpiarCampos() {
        lblNombrePersona.setText("");
        txtSueldo.setText("");
        cmbNivelEstudios.setSelectedIndex(0);
        cmbDepartamento.setSelectedIndex(0);
        cmbCargo.setSelectedIndex(0);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora;
        try {
            hora = sdf.parse("00:00:00");
            SpinnerDateModel sm = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
            txtEntrada.setModel(sm);
            JSpinner.DateEditor de = new JSpinner.DateEditor(txtEntrada, "HH:mm:ss");
            txtEntrada.setEditor(de);
            SpinnerDateModel sm2 = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
            txtSalida.setModel(sm2);
            JSpinner.DateEditor ded = new JSpinner.DateEditor(txtSalida, "HH:mm:ss");
            txtSalida.setEditor(ded);
        } catch (Exception e) {
        }
        txtDia.setText("");
        comboMeses.setSelectedIndex(0);
        txtAno.setText("");
        txtEmergencia.setText("");
        chkPortarEfe.setSelected(false);
        txtMontoPortar.setText("");
    }

    private void cargarDatos() {
        try {
            this.ADC = this.SERVICIO.adc(this.USUARIO.getIdSucursal(), this.EMPLEADO.getID_STAFF());
            txtSueldo.setText(String.valueOf(this.EMPLEADO.getSALARIO()));
            setSelectedEstudios(this.EMPLEADO.getESTUDIOS());
            setSelectedDepartamento(this.EMPLEADO.getDEPARTAMENTO());
            setSelectedCargo(this.EMPLEADO.getCARGO());
            txtEmergencia.setText(this.EMPLEADO.getCASO_EMERGENCIA());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date hora = sdf.parse(this.EMPLEADO.getENTRADA());
            SpinnerDateModel sm = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
            txtEntrada.setModel(sm);
            JSpinner.DateEditor de = new JSpinner.DateEditor(txtEntrada, "HH:mm:ss");
            txtEntrada.setEditor(de);
            hora = sdf.parse(this.EMPLEADO.getSALIDA());
            SpinnerDateModel sm2 = new SpinnerDateModel(hora, null, null, Calendar.HOUR_OF_DAY);
            txtSalida.setModel(sm2);
            JSpinner.DateEditor ded = new JSpinner.DateEditor(txtSalida, "HH:mm:ss");
            txtSalida.setEditor(ded);
            txtDia.setText(this.EMPLEADO.getFECHA_INCORPORACION().substring(8, 10));
            int mes = Integer.parseInt(EMPLEADO.getFECHA_INCORPORACION().substring(5, 7));
            comboMeses.setSelectedIndex(mes - 1);
            txtAno.setText(this.EMPLEADO.getFECHA_INCORPORACION().substring(0, 4));
            marcarDias();
            if (this.EMPLEADO.getEFECTIVO() > 0) {
                chkPortarEfe.setSelected(true);
                txtMontoPortar.setText(String.valueOf(this.EMPLEADO.getEFECTIVO()));
            }
            if (this.ADC != null) {
                cargaDatosAdc();
            }
        } catch (ParseException ex) {
            System.out.println("views.clientes.cargarDatosCliente() : " + ex);
        }
    }

    private void cancelar() {
        this.PERSONA_SELECCIONADA = null;
        this.EMPLEADO = null;
        this.ADC = null;
        limpiarCampos();
        llenarTabla("");
    }

    private void desactivarPanelAdc() {
//        panelFormulario.setSize(panelFormulario.getWidth(), 225);
        //panelAdc.setVisible(false);
        cmbAgencia.setEnabled(false);
        cmbAgencia.setModel(new DefaultComboBoxModel<>());
        cmbVacante.setModel(new DefaultComboBoxModel<>());
        lblVacante.setVisible(false);
    }

    private void cargaDatosAdc() {
//        panelAdc.setVisible(true);
        cmbAgencia.setEnabled(true);
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
        lblVacante.setVisible(true);
        if (this.ADC.getVACANTE() > 0) {
            lblVacante.setText("Actualmente: Vacante " + this.ADC.getVACANTE());
        } else {
            lblVacante.setText("Actualmente: No asignado");
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

    private Empleado creaEmpleado() {
        Empleado emp = null;
        String dias = this.diasLaborales();
        int salario = Integer.valueOf(txtSueldo.getText());
        int departamento = ((Lista) cmbDepartamento.getSelectedItem()).getID();
        int estudios = ((Estudio) cmbNivelEstudios.getSelectedItem()).getID();
        int cargo = ((Lista) cmbCargo.getSelectedItem()).getID();
        String emergencia = txtEmergencia.getText();
        String entrada = txtEntrada.getValue().toString();
        String[] split = entrada.split(" ");//separa en un array la cadena donde encuentre un espacio
        entrada = split[3];//del formato completo toma sólo la hora
        String salida = txtSalida.getValue().toString();
        split = salida.split(" ");//separa en un array la cadena donde encuentre un espacio
        salida = split[3];//del formato completo toma sólo la hora
        String fecha = txtAno.getText() + "-" + ((Mes) comboMeses.getSelectedItem()).getNumeroMes() + "-" + txtDia.getText();
        int efectivo;//si no marcamos la casilla para portar efectivo se toma como 0
        if ("".equals(txtMontoPortar.getText())) {
            efectivo = 0;
        } else {
            efectivo = Integer.valueOf(txtMontoPortar.getText());
        }
        emp = new Empleado();
        emp.setSUCURSAL(this.USUARIO.getIdSucursal());
        emp.setUSUARIO(this.USUARIO.getIdUsuario());
        emp.setID_PERSONA(PERSONA_SELECCIONADA.getIdPersona());
        emp.setCARGO(cargo);
        emp.setESTUDIOS(estudios);
        emp.setDEPARTAMENTO(departamento);
        emp.setSALARIO(salario);
        emp.setENTRADA(entrada);
        emp.setSALIDA(salida);
        emp.setDIAS_LABORALES(dias);
        emp.setCASO_EMERGENCIA(emergencia);
        emp.setFECHA_INCORPORACION(fecha);
        emp.setEFECTIVO(efectivo);
        emp.setPERSONA(PERSONA_SELECCIONADA);
        return emp;
    }

    private void guardarDatos() {
        try {
            String mensaje = "COMPLETADO";

            int agencia = 0;//número de agencia
            int vacante = 0;//número de vacante de la agencia
            try {
                //convertimos la agencia seleccionada a tipo int en caso de haber una, si no seguirá quedando como 0
                agencia = ((Lista) cmbAgencia.getSelectedItem()).getID();
                //convertimos el numero de vacante a tipo int si existe alguna                
                vacante = ((Lista) cmbVacante.getSelectedItem()).getID();
            } catch (Exception ex) {
//                System.out.println("views.Empleados.guardarDatos() : " + ex);
            }

            int idEmpleado = 0;//id obtenido de la última inserción de un Empleado a la BD
            boolean updated = false;
            if (this.EMPLEADO == null) {//si no tenemos un objeto Empleado entonces...
                this.EMPLEADO = this.creaEmpleado();//creamos un objeto Empleado
                idEmpleado = this.SERVICIO.guardarDatosEmpleado(this.EMPLEADO, agencia, vacante);//lo enviamos para ser guardado
            } else {//si tenemos un objeto Empleado entonces modificamos sus valores
                Empleado nuevoEmpleado = this.creaEmpleado();
                idEmpleado = this.EMPLEADO.getID_STAFF();
                this.EMPLEADO.setSUCURSAL(this.USUARIO.getIdSucursal());
                this.EMPLEADO.setUSUARIO(this.USUARIO.getIdUsuario());
                this.EMPLEADO.setCARGO(nuevoEmpleado.getCARGO());
                this.EMPLEADO.setESTUDIOS(nuevoEmpleado.getESTUDIOS());
                this.EMPLEADO.setDEPARTAMENTO(nuevoEmpleado.getDEPARTAMENTO());
                this.EMPLEADO.setSALARIO(nuevoEmpleado.);
                this.EMPLEADO.setENTRADA(entrada);
                this.EMPLEADO.setSALIDA(salida);
                this.EMPLEADO.setDIAS_LABORALES(dias);
                this.EMPLEADO.setCASO_EMERGENCIA(emergencia);
                this.EMPLEADO.setFECHA_INCORPORACION(fecha);
                this.EMPLEADO.setEFECTIVO(efectivo);
//enviamos el objeto Empleado con los nuevos datos para ser actualizado en la BD
                updated = this.SERVICIO.actualizarDatosEmpleado(this.EMPLEADO, agencia, vacante);
            }

            if (updated == true || idEmpleado > 0) {//verificamos que los datos del empleado se hayan guardado o actualizado
//si tenemos una agencia seleccionada y el cargo es ADC entonces            
                if (cargo == 5 && agencia > 0) {//cargo 5 = ADC |||| agencia = 1,2,3,4, etc... según la BD
                    if (vacante > 0 && this.ADC == null) {//si el Empleado aún no es ADC
//creamos un nuevo ADC con el id de sucursal, idEmpleado, agencia y vacante a la que pertenece
                        mensaje = this.SERVICIO.crearADC(this.USUARIO.getIdSucursal(), idEmpleado, agencia, vacante);
                    } else if (this.ADC != null && vacante > 0) {
//actualizamos el ADC mediante el objeto ADC con los valores nuevos y id de sucursal, idEmpleado, agencia y vacante a la que pertenece
                        mensaje = this.SERVICIO.actualizarADC(this.ADC, this.USUARIO.getIdSucursal(), idEmpleado, agencia, vacante);
                    }
                } else {
                    mensaje = "Datos laborales guardados correctamente";
                }
                cancelar();
            } else {
                mensaje = "Datos del ADC no guardados";
            }
            JOptionPane.showMessageDialog(rootPane, mensaje, "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException | NumberFormatException e) {
            System.out.println("views.Empleados.guardarDatos() : " + e);
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
        String dias = this.EMPLEADO.getDIAS_LABORALES();
        String[] diasSplit = dias.split("-");
        int splits = diasSplit.length;
//        System.out.println(Arrays.toString(diasSplit));
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

    private void meses() {
        Fecha f = new Fecha();
        Mes[] meses = f.meses();
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
        panelTabla = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar2 = new javax.swing.JTextField();
        btnBusqueda = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        panelFormulario = new javax.swing.JPanel();
        txtSueldo = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cmbNivelEstudios = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
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
        jLabel23 = new javax.swing.JLabel();
        cmbAgencia = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        cmbVacante = new javax.swing.JComboBox<>();
        lblVacante = new javax.swing.JLabel();

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
        tituloVentana.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloVentana.setText("Empleados de sucursal: ");
        BarraSuperior.add(tituloVentana, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 85));

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

        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscador de personas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel9.setText("Búsqueda por dato personal:");

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
                .addContainerGap(263, Short.MAX_VALUE))
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1128, Short.MAX_VALUE)
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Contenedor.add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 1160, 200));

        panelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingrese los datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelFormulario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        panelFormulario.add(txtSueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 41, 110, -1));

        btnGuardar.setBackground(new java.awt.Color(244, 0, 100));
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        btnGuardar.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 40));

        panelFormulario.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(824, 28, 120, 40));

        btnCancelar.setBackground(new java.awt.Color(204, 0, 0));
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        btnCancelar.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 40));

        panelFormulario.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(824, 88, 120, 40));

        cmbNivelEstudios.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panelFormulario.add(cmbNivelEstudios, new org.netbeans.lib.awtextra.AbsoluteConstraints(423, 41, 180, -1));

        jLabel19.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel19.setText("Cargo:");
        panelFormulario.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(438, 74, 55, -1));

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel20.setText("Departamento:");
        panelFormulario.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 74, -1, -1));

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel16.setText("Percepción económica:");
        panelFormulario.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 44, -1, -1));

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel21.setText("Nivel de estudios:");
        panelFormulario.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 44, -1, -1));

        cmbDepartamento.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panelFormulario.add(cmbDepartamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 71, 302, -1));

        jLabel25.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel25.setText("Días laborales:");
        panelFormulario.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 101, -1, -1));

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel1.setText("En caso de emergencia llamar a:");
        panelFormulario.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 160, 207, -1));

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
        panelFormulario.add(txtEmergencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 157, 564, -1));

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel2.setText("Entrada:");
        panelFormulario.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 130, 58, -1));

        JSpinner.DateEditor de = new JSpinner.DateEditor(txtEntrada, "HH:mm:ss");
        txtEntrada.setEditor(de);
        txtEntrada.setFont(new java.awt.Font("Solomon Sans Book", 0, 12));
        txtEntrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEntradaKeyPressed(evt);
            }
        });
        panelFormulario.add(txtEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 126, 110, -1));

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel3.setText("Salida:");
        panelFormulario.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 130, 50, -1));

        JSpinner.DateEditor de2 = new JSpinner.DateEditor(txtSalida, "HH:mm:ss");
        txtSalida.setEditor(de2);
        txtSalida.setFont(new java.awt.Font("Solomon Sans Book", 0, 12));
        txtSalida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSalidaKeyTyped(evt);
            }
        });
        panelFormulario.add(txtSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 126, 110, -1));

        lblDatosde.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        lblDatosde.setText("Datos de:");
        panelFormulario.add(lblDatosde, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 17, -1, -1));

        lblNombrePersona.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelFormulario.add(lblNombrePersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 17, 706, 10));

        cmbCargo.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCargoActionPerformed(evt);
            }
        });
        panelFormulario.add(cmbCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(503, 71, 300, -1));

        lunes.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        lunes.setSelected(true);
        lunes.setText("Lunes");
        panelFormulario.add(lunes, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 98, -1, -1));

        martes.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        martes.setSelected(true);
        martes.setText("Martes");
        panelFormulario.add(martes, new org.netbeans.lib.awtextra.AbsoluteConstraints(199, 98, -1, -1));

        miercoles.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        miercoles.setSelected(true);
        miercoles.setText("Miércoles");
        panelFormulario.add(miercoles, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 98, -1, -1));

        jueves.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        jueves.setSelected(true);
        jueves.setText("Jueves");
        panelFormulario.add(jueves, new org.netbeans.lib.awtextra.AbsoluteConstraints(397, 98, -1, -1));

        viernes.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        viernes.setSelected(true);
        viernes.setText("Viernes");
        panelFormulario.add(viernes, new org.netbeans.lib.awtextra.AbsoluteConstraints(484, 98, -1, -1));

        sabado.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        sabado.setSelected(true);
        sabado.setText("Sábado");
        panelFormulario.add(sabado, new org.netbeans.lib.awtextra.AbsoluteConstraints(581, 98, -1, -1));

        domingo.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        domingo.setText("Domingo");
        panelFormulario.add(domingo, new org.netbeans.lib.awtextra.AbsoluteConstraints(676, 98, -1, -1));

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel4.setText("Fecha de incorporación:");
        panelFormulario.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(421, 130, -1, -1));

        txtDia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiaKeyTyped(evt);
            }
        });
        panelFormulario.add(txtDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(579, 126, 50, -1));

        comboMeses.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panelFormulario.add(comboMeses, new org.netbeans.lib.awtextra.AbsoluteConstraints(631, 126, 116, -1));

        txtAno.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAnoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnoKeyTyped(evt);
            }
        });
        panelFormulario.add(txtAno, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 126, 50, -1));

        lblDatosde1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        lblDatosde1.setText("Monto máximo:");
        panelFormulario.add(lblDatosde1, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 190, -1, -1));

        chkPortarEfe.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        chkPortarEfe.setText("Autorizado portar efectivo");
        chkPortarEfe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkPortarEfeItemStateChanged(evt);
            }
        });
        panelFormulario.add(chkPortarEfe, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 187, -1, -1));

        txtMontoPortar.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtMontoPortar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMontoPortar.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtMontoPortar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoPortarKeyTyped(evt);
            }
        });
        panelFormulario.add(txtMontoPortar, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 187, 110, -1));
        panelFormulario.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 217, 948, -1));

        panelAdc.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblDatosde2.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        lblDatosde2.setText("Datos para ADC");

        jLabel23.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel23.setText("Agencia:");

        cmbAgencia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbAgencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAgenciaActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel24.setText("Vacante:");

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
                        .addComponent(lblDatosde2))
                    .addGroup(panelAdcLayout.createSequentialGroup()
                        .addGap(271, 271, 271)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbVacante, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAdcLayout.createSequentialGroup()
                        .addGap(394, 394, 394)
                        .addComponent(lblVacante, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(267, Short.MAX_VALUE))
        );
        panelAdcLayout.setVerticalGroup(
            panelAdcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdcLayout.createSequentialGroup()
                .addComponent(lblDatosde2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAdcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(cmbVacante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblVacante)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        panelFormulario.add(panelAdc, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 225, 928, 70));

        Contenedor.add(panelFormulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, 300));

        PanelPrincipal.add(Contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1200, 530));

        getContentPane().add(PanelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void txtBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscar2ActionPerformed
        llenarTabla(txtBuscar2.getText());
    }//GEN-LAST:event_txtBuscar2ActionPerformed

    private void txtBuscar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyPressed

    }//GEN-LAST:event_txtBuscar2KeyPressed

    private void txtBuscar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyReleased
        char cTeclaPresionada = evt.getKeyChar();
        if (cTeclaPresionada == KeyEvent.VK_DELETE || cTeclaPresionada == KeyEvent.VK_BACK_SPACE) {
            int l = txtBuscar2.getText().length();
            if (l == 0) {
                llenarTabla("");
            }
        }
    }//GEN-LAST:event_txtBuscar2KeyReleased

    private void btnBusquedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBusquedaMouseClicked
        llenarTabla(txtBuscar2.getText());
    }//GEN-LAST:event_btnBusquedaMouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtSueldoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSueldoKeyReleased

    }//GEN-LAST:event_txtSueldoKeyReleased

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

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        guardarDatos();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        cancelar();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void txtEmergenciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmergenciaKeyReleased
        String cadena = txtEmergencia.getText().toUpperCase();
        txtEmergencia.setText(cadena);
    }//GEN-LAST:event_txtEmergenciaKeyReleased

    private void txtEmergenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmergenciaKeyTyped
        int lon = txtEmergencia.getText().length();
        if (lon >= 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEmergenciaKeyTyped

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

    private void cmbCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCargoActionPerformed
        int cargo = ((Lista) cmbCargo.getSelectedItem()).getID();
        if (cargo == 5) {
            panelAdc.setVisible(true);
            cmbAgencia.setModel(this.SERVICIO.agencias(this.USUARIO.getIdSucursal()));
        } else {
            desactivarPanelAdc();
        }
    }//GEN-LAST:event_cmbCargoActionPerformed

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

    private void chkPortarEfeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkPortarEfeItemStateChanged
        portarEfectivo();
    }//GEN-LAST:event_chkPortarEfeItemStateChanged

    private void txtMontoPortarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoPortarKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoPortarKeyTyped

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
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            Usuario usuario = null;
            Persona persona = null;
            Empleados dialog = new Empleados(new javax.swing.JFrame(), true, usuario, persona);
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
    private javax.swing.JCheckBox chkPortarEfe;
    private javax.swing.JComboBox<String> cmbAgencia;
    private javax.swing.JComboBox<String> cmbCargo;
    private javax.swing.JComboBox<String> cmbDepartamento;
    private javax.swing.JComboBox<String> cmbNivelEstudios;
    private javax.swing.JComboBox<String> cmbVacante;
    private javax.swing.JComboBox<String> comboMeses;
    private javax.swing.JCheckBox domingo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
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
    private javax.swing.JPanel panelTabla;
    private javax.swing.JCheckBox sabado;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtBuscar2;
    private javax.swing.JTextField txtDia;
    private javax.swing.JTextField txtEmergencia;
    private javax.swing.JSpinner txtEntrada;
    private javax.swing.JTextField txtMontoPortar;
    private javax.swing.JSpinner txtSalida;
    private javax.swing.JTextField txtSueldo;
    private javax.swing.JCheckBox viernes;
    // End of variables declaration//GEN-END:variables
}
