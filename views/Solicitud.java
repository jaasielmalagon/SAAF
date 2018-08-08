package views;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import objects.Amortizacion;
import objects.Estado;
import objects.Fecha;
import objects.Mes;
import objects.Persona;
import objects.Usuario;
import services.Personas_service;

/**
 *
 * @author Root
 */
public class Solicitud extends javax.swing.JDialog {

    private final Personas_service servicio;
    private int ID_PERSONA_SELECCIONADA = 0;
    private Persona PERSONA_SELECCIONADA = null;
    private Usuario USUARIO = null;    
    private final String modulo;

    public Solicitud(java.awt.Frame parent, boolean modal, Usuario usuario, String modulo) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);

        this.servicio = new Personas_service(modulo);
        this.meses();
        this.estadosNacimiento();
        this.USUARIO = usuario;
        this.modulo = modulo;
        
        llenarTabla();
        seleccionarPersona();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        this.agruparRadiosLimpieza();
        this.agruparRadiosCuentaCon();
    }
    
    private void agruparRadiosLimpieza(){
        ButtonGroup bg = new ButtonGroup();
        bg.add(jRadioButton11);
        bg.add(jRadioButton12);
        bg.add(jRadioButton13);
        bg.add(jRadioButton14);
    }
    
    private void agruparRadiosCuentaCon(){
        ButtonGroup bg = new ButtonGroup();
        bg.add(jRadioButton8);
        bg.add(jRadioButton9);
        bg.add(jRadioButton10);       
    }

    private void solicitarPrestamo() {
        if (PERSONA_SELECCIONADA != null) {
            Amortizacion amr = new Amortizacion();
            String[] montos = amr.montos();
            Object cantidades = JOptionPane.showInputDialog(this, "Seleccione el monto a solicitar", "Solicitar préstamo", JOptionPane.QUESTION_MESSAGE, null, montos, "0");
            
            String[] nSemanas = {"20", "24"};//PLAZO EN SEMANAS
            Object sems = JOptionPane.showInputDialog(this, "Seleccione a cuántas semanas desea el préstamo", "Seleccionar plazo", JOptionPane.QUESTION_MESSAGE, null, nSemanas, "0");
            
            String response = this.servicio.solicitarPrestamo(USUARIO, PERSONA_SELECCIONADA, cantidades, sems);
            JOptionPane.showMessageDialog(this, response, "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No ha seleccionado a una persona", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }


    //CORREGIDO
    private void llenarTabla() {
        tablaClientes = this.servicio.tablaPersonas(tablaClientes, this.USUARIO.getIdSucursal());
    }

    //CORREGIDO
    private void guardarDatos() {
        Estado estado_nac = (Estado) comboEstadosNacimiento.getSelectedItem();
        Mes mesNac = (Mes) comboMeses.getSelectedItem();
        String nombre = txtNombre.getText();
        String apaterno = txtAp1.getText();
        String amaterno = txtAp2.getText();
        int anio = 0;
        if (!txtAno.getText().isEmpty()) {
            anio = Integer.parseInt(txtAno.getText());
        }
        String f_nac = txtAno.getText() + "-" + mesNac.getNumeroMes() + "-" + txtDia.getText();
        int idEstadoNac = estado_nac.getId();
        String curp = txtCurp.getText();
        String ocr = txtOcr.getText();
        String sexo = "";
        if (comboSexo.getSelectedIndex() == 1) {
            sexo = "H";
        } else if (comboSexo.getSelectedIndex() == 2) {
            sexo = "M";
        }
//        System.out.println(sexo);
        int estadoCivil = cmbEstadoCivil.getSelectedIndex();
        String tel = txtTel.getText();
        String cel = txtCel.getText();
        if (tel.length() < 10) {
            tel = "----------";
        }
        int encontrado = this.servicio.buscarCliente(curp, ocr, cel, tel);
        if (encontrado > 0 && this.ID_PERSONA_SELECCIONADA == 0) {
            JOptionPane.showMessageDialog(rootPane, "Los datos únicos de esta persona ya se encuentran previamente registrados.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        } else if ("".equals(nombre) || "".equals(apaterno) || "".equals(amaterno) || "".equals(f_nac) || "".equals(idEstadoNac)
                || "".equals(curp) || "".equals(ocr) || "".equals(sexo) || "".equals(cel)) {
            JOptionPane.showMessageDialog(rootPane, "No puede dejar ningún campo vacío, todos son obligatorios.");
        } else if (tel.length() == 10 || cel.length() == 10) {
            int guardado = 0;
            if (encontrado > 0 && ID_PERSONA_SELECCIONADA > 0 && encontrado == this.ID_PERSONA_SELECCIONADA) {
                int confirmacion = JOptionPane.showConfirmDialog(rootPane, "¿Desea modificar los datos de " + PERSONA_SELECCIONADA.toString() + "?");
                if (confirmacion == 0) {
                    guardado = this.servicio.actualizarDatosCliente(USUARIO.getIdUsuario(), this.ID_PERSONA_SELECCIONADA, USUARIO.getIdSucursal(), nombre, apaterno, amaterno, f_nac, idEstadoNac, curp, ocr, sexo, estadoCivil, tel, cel);
                }
            } else {
                guardado = this.servicio.guardarDatosCliente(USUARIO.getIdUsuario(), USUARIO.getIdSucursal(), nombre, apaterno, amaterno, f_nac, idEstadoNac, curp, ocr, sexo, estadoCivil, tel, cel);
            }
            switch (guardado) {
                case 1:
                    JOptionPane.showMessageDialog(rootPane, "La fecha ingresada no es válida.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(rootPane, "La persona es menor de 18 años.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(rootPane, "Los datos no fueron guardados correctamente.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(rootPane, "Datos personales guardados correctamente");
                    limpiarCampos();
                    llenarTabla();
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "El número telefónico y celular deben contener 10 dígitos", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }

    }

    //NO NECESITA CORREGIR
    private void limpiarCampos() {
        comboMeses.setSelectedIndex(0);
        comboEstadosNacimiento.setSelectedIndex(0);
        cmbEstadoCivil.setSelectedIndex(0);
        txtNombre.setText("");
        txtAp1.setText("");
        txtAp2.setText("");
        txtDia.setText("");
        txtAno.setText("");
        txtCurp.setText("");
        txtOcr.setText("");
        txtTel.setText("");
        txtCel.setText("");
    }

    //NO NECESITA CORREGIR
    private void meses() {
        Fecha f = new Fecha();
        Mes[] meses = f.meses();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Mes mes : meses) {
            dcbm.addElement(mes);
        }
        comboMeses.setModel(dcbm);
    }

    //CORREGIDO
    private void estadosNacimiento() {
        comboEstadosNacimiento.setModel(this.servicio.estados());
    }

    //CORREGIDO
    public void buscar() {
        if (!txtBuscar2.getText().isEmpty()) {
            tablaClientes = this.servicio.tablaPersonas(tablaClientes, USUARIO.getIdSucursal(), txtBuscar2.getText());
        } else {
            tablaClientes = this.servicio.tablaPersonas(tablaClientes, this.USUARIO.getIdSucursal());
        }
    }

    //CORREGIDO
    private void seleccionarPersona() {
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                if (Mouse_evt.getClickCount() == 1) {
                    ID_PERSONA_SELECCIONADA = Integer.parseInt(tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0).toString());
                    try {
                        PERSONA_SELECCIONADA = servicio.persona(USUARIO.getIdSucursal(), ID_PERSONA_SELECCIONADA);
                        if (PERSONA_SELECCIONADA != null) {
                            txtNombre.setText(PERSONA_SELECCIONADA.getNombre());
                            txtAp1.setText(PERSONA_SELECCIONADA.getApaterno());
                            txtAp2.setText(PERSONA_SELECCIONADA.getAmaterno());
                            txtDia.setText(PERSONA_SELECCIONADA.getF_nac().substring(8, 10));
                            int mes = Integer.parseInt(PERSONA_SELECCIONADA.getF_nac().substring(5, 7));
                            comboMeses.setSelectedIndex(mes - 1);
                            txtAno.setText(PERSONA_SELECCIONADA.getF_nac().substring(0, 4));
                            setSelectedEstado(servicio.estado(PERSONA_SELECCIONADA.getEntidadNac()));
                            txtCurp.setText(PERSONA_SELECCIONADA.getCurp());
                            txtOcr.setText(PERSONA_SELECCIONADA.getOcr());
                            if ("H".equals(PERSONA_SELECCIONADA.getSexo())) {
                                comboSexo.setSelectedIndex(1);
                            } else if ("M".equals(PERSONA_SELECCIONADA.getSexo())) {
                                comboSexo.setSelectedIndex(2);
                            }
                            cmbEstadoCivil.setSelectedIndex(PERSONA_SELECCIONADA.getEstadoCivil());
                            txtTel.setText(PERSONA_SELECCIONADA.getTelefono());
                            txtCel.setText(PERSONA_SELECCIONADA.getCelular());                         
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println(".mousePressed() : " + ex);
                    }
                }
            }
        });
    }

    public void setSelectedEstado(String value) {
        Object item;
        Estado estado;
        for (int i = 0; i < comboEstadosNacimiento.getItemCount(); i++) {
            item = comboEstadosNacimiento.getItemAt(i);
            estado = (Estado) item;
            if (estado.getEstado().equalsIgnoreCase(value)) {
                comboEstadosNacimiento.setSelectedIndex(i);
                break;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        Contenedor = new javax.swing.JPanel();
        panelTabla = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar2 = new javax.swing.JTextField();
        btnBusqueda = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        panel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cmbEstadoCivil2 = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        txtNombre2 = new javax.swing.JTextField();
        panel2 = new javax.swing.JPanel();
        comboMeses = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtAno = new javax.swing.JTextField();
        txtAp1 = new javax.swing.JTextField();
        comboEstadosNacimiento = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCurp = new javax.swing.JTextField();
        txtDia = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtAp2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtOcr = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        comboSexo = new javax.swing.JComboBox<>();
        txtCurp5 = new javax.swing.JTextField();
        panel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmbEstadoCivil = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbEstadoCivil1 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        txtNombre1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtAp3 = new javax.swing.JTextField();
        txtAp4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        comboSexo1 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        txtDia1 = new javax.swing.JTextField();
        comboMeses1 = new javax.swing.JComboBox<>();
        txtAno1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        comboEstadosNacimiento1 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        txtCurp1 = new javax.swing.JTextField();
        txtOcr1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        txtNombre6 = new javax.swing.JTextField();
        txtNombre7 = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        txtCurp6 = new javax.swing.JTextField();
        panel4 = new javax.swing.JPanel();
        txtCalle = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtCalle1 = new javax.swing.JTextField();
        txtCalle2 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtCalle3 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtCalle4 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtCalle5 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtCalle6 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtCalle7 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtCalle8 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtTel = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCel = new javax.swing.JTextField();
        panel5 = new javax.swing.JPanel();
        comboMeses2 = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        txtNombre3 = new javax.swing.JTextField();
        txtAno2 = new javax.swing.JTextField();
        txtAp5 = new javax.swing.JTextField();
        comboEstadosNacimiento2 = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCurp2 = new javax.swing.JTextField();
        txtDia2 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtAp6 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtOcr2 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        comboSexo2 = new javax.swing.JComboBox<>();
        jLabel45 = new javax.swing.JLabel();
        txtAp7 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtCalle9 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txtCalle10 = new javax.swing.JTextField();
        txtCalle11 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txtCalle12 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtCalle13 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtCalle14 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        txtCalle15 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtCalle16 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        txtCalle17 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTel1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCel1 = new javax.swing.JTextField();
        txtCurp7 = new javax.swing.JTextField();
        panel6 = new javax.swing.JPanel();
        comboMeses3 = new javax.swing.JComboBox<>();
        jLabel55 = new javax.swing.JLabel();
        txtNombre4 = new javax.swing.JTextField();
        txtAno3 = new javax.swing.JTextField();
        txtAp8 = new javax.swing.JTextField();
        comboEstadosNacimiento3 = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtCurp3 = new javax.swing.JTextField();
        txtDia3 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        txtAp9 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        txtOcr3 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        comboSexo3 = new javax.swing.JComboBox<>();
        jLabel61 = new javax.swing.JLabel();
        txtAp10 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        txtCalle18 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        txtCalle19 = new javax.swing.JTextField();
        txtCalle20 = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        txtCalle21 = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        txtCalle22 = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        txtCalle23 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        txtCalle24 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        txtCalle25 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        txtCalle26 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtTel2 = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        txtCel2 = new javax.swing.JTextField();
        txtCurp8 = new javax.swing.JTextField();
        panel7 = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        txtAnosResidencia = new javax.swing.JTextField();
        comboMeses4 = new javax.swing.JComboBox<>();
        jLabel72 = new javax.swing.JLabel();
        txtNombre5 = new javax.swing.JTextField();
        txtAno4 = new javax.swing.JTextField();
        txtAp11 = new javax.swing.JTextField();
        comboEstadosNacimiento4 = new javax.swing.JComboBox<>();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        txtCurp4 = new javax.swing.JTextField();
        txtDia4 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        txtAp12 = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        txtOcr4 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        comboSexo4 = new javax.swing.JComboBox<>();
        jLabel79 = new javax.swing.JLabel();
        txtAp13 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txtCalle27 = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        txtCalle28 = new javax.swing.JTextField();
        txtCalle29 = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        txtCalle30 = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        txtCalle31 = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        txtCalle32 = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        txtCalle33 = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        txtCalle34 = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        txtCalle35 = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        txtTel3 = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        txtCel3 = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel93 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel94 = new javax.swing.JLabel();
        txtNombre8 = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        txtDia5 = new javax.swing.JTextField();
        comboMeses5 = new javax.swing.JComboBox<>();
        txtAno5 = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        cmbEstadoCivil3 = new javax.swing.JComboBox<>();
        jLabel98 = new javax.swing.JLabel();
        txtNombre9 = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        txtNombre10 = new javax.swing.JTextField();
        txtCurp9 = new javax.swing.JTextField();
        panel8 = new javax.swing.JPanel();
        jLabel126 = new javax.swing.JLabel();
        cmbEstadoCivil5 = new javax.swing.JComboBox<>();
        jLabel127 = new javax.swing.JLabel();
        txtDependientes = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        txtNombre11 = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        txtNombre12 = new javax.swing.JTextField();
        panelIngresos2 = new javax.swing.JPanel();
        jLabel116 = new javax.swing.JLabel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jLabel117 = new javax.swing.JLabel();
        lunes = new javax.swing.JCheckBox();
        martes = new javax.swing.JCheckBox();
        miercoles = new javax.swing.JCheckBox();
        jueves = new javax.swing.JCheckBox();
        viernes = new javax.swing.JCheckBox();
        sabado = new javax.swing.JCheckBox();
        domingo = new javax.swing.JCheckBox();
        jLabel118 = new javax.swing.JLabel();
        txtNombre13 = new javax.swing.JTextField();
        jLabel119 = new javax.swing.JLabel();
        panelIngresos1 = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        txtIngresos6 = new javax.swing.JTextField();
        jLabel110 = new javax.swing.JLabel();
        txtIngresos7 = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        txtIngresos8 = new javax.swing.JTextField();
        jLabel112 = new javax.swing.JLabel();
        txtIngresos9 = new javax.swing.JTextField();
        jLabel113 = new javax.swing.JLabel();
        txtIngresos10 = new javax.swing.JTextField();
        jLabel114 = new javax.swing.JLabel();
        txtIngresos11 = new javax.swing.JTextField();
        jLabel115 = new javax.swing.JLabel();
        txtTotalIngresos1 = new javax.swing.JTextField();
        jLabel123 = new javax.swing.JLabel();
        txtIngresos18 = new javax.swing.JTextField();
        jLabel124 = new javax.swing.JLabel();
        txtIngresos19 = new javax.swing.JTextField();
        jLabel125 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        txtIngresos20 = new javax.swing.JTextField();
        txtIngresos21 = new javax.swing.JTextField();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        txtIngresos22 = new javax.swing.JTextField();
        txtIngresos23 = new javax.swing.JTextField();
        jLabel131 = new javax.swing.JLabel();
        txtIngresos24 = new javax.swing.JTextField();
        panelIngresos = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        txtIngresos = new javax.swing.JTextField();
        jLabel104 = new javax.swing.JLabel();
        txtIngresos1 = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        txtIngresos2 = new javax.swing.JTextField();
        jLabel106 = new javax.swing.JLabel();
        txtIngresos3 = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        txtIngresos4 = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        txtIngresos5 = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        txtTotalIngresos = new javax.swing.JTextField();
        panel9 = new javax.swing.JPanel();
        jLabel120 = new javax.swing.JLabel();
        comboTipoVivienda = new javax.swing.JComboBox<>();
        jLabel158 = new javax.swing.JLabel();
        txtAnosResidencia1 = new javax.swing.JTextField();
        cmbEstadoCivil6 = new javax.swing.JComboBox<>();
        jLabel121 = new javax.swing.JLabel();
        comboTipoVivienda1 = new javax.swing.JComboBox<>();
        jLabel122 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel132 = new javax.swing.JLabel();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jLabel133 = new javax.swing.JLabel();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jRadioButton13 = new javax.swing.JRadioButton();
        jRadioButton14 = new javax.swing.JRadioButton();
        jLabel161 = new javax.swing.JLabel();
        txtAnosResidencia2 = new javax.swing.JTextField();
        jLabel162 = new javax.swing.JLabel();
        txtAnosResidencia3 = new javax.swing.JTextField();
        jLabel163 = new javax.swing.JLabel();
        txtAnosResidencia4 = new javax.swing.JTextField();
        jLabel164 = new javax.swing.JLabel();
        txtAnosResidencia5 = new javax.swing.JTextField();
        jLabel165 = new javax.swing.JLabel();
        txtAnosResidencia6 = new javax.swing.JTextField();
        jLabel166 = new javax.swing.JLabel();
        txtAnosResidencia7 = new javax.swing.JTextField();
        jLabel167 = new javax.swing.JLabel();
        txtAnosResidencia8 = new javax.swing.JTextField();
        jLabel168 = new javax.swing.JLabel();
        txtAnosResidencia9 = new javax.swing.JTextField();
        jLabel134 = new javax.swing.JLabel();
        jRadioButton15 = new javax.swing.JRadioButton();
        jRadioButton16 = new javax.swing.JRadioButton();
        jLabel135 = new javax.swing.JLabel();
        jRadioButton17 = new javax.swing.JRadioButton();
        jRadioButton18 = new javax.swing.JRadioButton();
        jLabel136 = new javax.swing.JLabel();
        jRadioButton19 = new javax.swing.JRadioButton();
        jRadioButton20 = new javax.swing.JRadioButton();
        jLabel169 = new javax.swing.JLabel();
        txtAnosResidencia10 = new javax.swing.JTextField();
        jLabel170 = new javax.swing.JLabel();
        txtAnosResidencia11 = new javax.swing.JTextField();
        jLabel171 = new javax.swing.JLabel();
        txtAnosResidencia12 = new javax.swing.JTextField();
        jLabel172 = new javax.swing.JLabel();
        txtAnosResidencia13 = new javax.swing.JTextField();
        jLabel173 = new javax.swing.JLabel();
        txtAnosResidencia14 = new javax.swing.JTextField();
        jLabel174 = new javax.swing.JLabel();
        txtAnosResidencia15 = new javax.swing.JTextField();

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
        BarraSuperior.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 0, 32, 32));

        tituloVentana.setFont(new java.awt.Font("Solomon Sans Book", 1, 24)); // NOI18N
        tituloVentana.setForeground(new java.awt.Color(255, 255, 255));
        tituloVentana.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloVentana.setText("Solicitud de crédito");
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
        BarraSuperior.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 304, 85));

        PanelPrincipal.add(BarraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 85));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setAutoscrolls(true);

        Contenedor.setBackground(new java.awt.Color(255, 245, 250));
        Contenedor.setPreferredSize(new java.awt.Dimension(1190, 3000));
        Contenedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscador de personas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panelTabla.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel9.setText("Búsqueda por dato personal:");
        panelTabla.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 23, -1, -1));

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
        panelTabla.add(txtBuscar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 19, 450, -1));

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

        panelTabla.add(btnBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(816, 19, -1, -1));

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

        panelTabla.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 45, 1148, 90));

        Contenedor.add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1180, 150));

        panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ACERCA DEL PRÉSTAMO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel7.setText("Monto solicitado:");
        panel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        cmbEstadoCivil2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbEstadoCivil2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1000", "1500", "2000", "2500", "3000", "3500", "4000", "4500", "5000", "6000", "6500", "7000", "8000", "8500", "9000", "9500", "10000" }));
        panel1.add(cmbEstadoCivil2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 130, 20));

        jLabel29.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel29.setText("¿En qué ocupará el préstamo?");
        panel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, 20));

        txtNombre2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre2ActionPerformed(evt);
            }
        });
        txtNombre2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre2KeyTyped(evt);
            }
        });
        panel1.add(txtNombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 690, 20));

        Contenedor.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 1180, 50));

        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATOS DE IDENTIFICACIÓN DEL SOLICITANTE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        comboMeses.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panel2.add(comboMeses, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 70, 116, 20));

        jLabel17.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel17.setText("Apellidos:");
        panel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, -1, 20));

        txtNombre.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        panel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 30, 269, 20));

        txtAno.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAnoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnoKeyTyped(evt);
            }
        });
        panel2.add(txtAno, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 50, 20));

        txtAp1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp1KeyTyped(evt);
            }
        });
        panel2.add(txtAp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 200, 20));

        comboEstadosNacimiento.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panel2.add(comboEstadosNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 206, 20));

        jLabel18.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel18.setText("Fecha nacimiento:");
        panel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 20));

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel3.setText("Sexo:");
        panel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 30, -1, 20));

        txtCurp.setEditable(false);
        txtCurp.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurpKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurpKeyTyped(evt);
            }
        });
        panel2.add(txtCurp, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 70, 130, 20));

        txtDia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiaKeyTyped(evt);
            }
        });
        panel2.add(txtDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 50, 20));

        jLabel19.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel19.setText("OCR:");
        panel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 70, -1, 20));

        txtAp2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp2KeyTyped(evt);
            }
        });
        panel2.add(txtAp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, 200, 20));

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel20.setText("Entidad de nacimiento:");
        panel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, -1, 20));

        txtOcr.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtOcr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOcrKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOcrKeyTyped(evt);
            }
        });
        panel2.add(txtOcr, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 70, 100, 20));

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel16.setText("Nombre(s):");
        panel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 30, -1, 20));

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel21.setText("CURP:");
        panel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 70, 52, 20));

        comboSexo.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--", "Hombre", "Mujer" }));
        comboSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSexoActionPerformed(evt);
            }
        });
        panel2.add(comboSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(983, 30, 150, 20));

        txtCurp5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp5.setToolTipText("Homoclave (2 dígitos)");
        txtCurp5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurp5KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurp5KeyTyped(evt);
            }
        });
        panel2.add(txtCurp5, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 70, 30, 20));

        Contenedor.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 1180, 100));

        panel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ESTADO CIVIL", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel4.setText("Estado civíl:");
        panel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        cmbEstadoCivil.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Soltero", "Casado" }));
        panel3.add(cmbEstadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 100, 20));

        jLabel5.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel5.setText("Ocupación del cónyuge:");
        panel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, 20));

        cmbEstadoCivil1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbEstadoCivil1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Comerciante", "Empleado(a)", "Estudiante", "Labores del hogar", "Ninguno" }));
        panel3.add(cmbEstadoCivil1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 150, 20));

        jLabel23.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel23.setText("Nombre(s):");
        panel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 20));

        txtNombre1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre1ActionPerformed(evt);
            }
        });
        txtNombre1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre1KeyTyped(evt);
            }
        });
        panel3.add(txtNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 50, 270, 20));

        jLabel24.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel24.setText("Apellidos:");
        panel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, 20));

        txtAp3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp3KeyTyped(evt);
            }
        });
        panel3.add(txtAp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 200, 20));

        txtAp4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp4KeyTyped(evt);
            }
        });
        panel3.add(txtAp4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 200, 20));

        jLabel6.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel6.setText("Sexo:");
        panel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 50, -1, 20));

        comboSexo1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboSexo1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--", "Hombre", "Mujer" }));
        comboSexo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSexo1ActionPerformed(evt);
            }
        });
        panel3.add(comboSexo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, 150, 20));

        jLabel25.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel25.setText("Fecha nacimiento:");
        panel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 20));

        txtDia1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDia1KeyTyped(evt);
            }
        });
        panel3.add(txtDia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 50, 20));

        comboMeses1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panel3.add(comboMeses1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 116, 20));

        txtAno1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAno1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAno1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAno1KeyTyped(evt);
            }
        });
        panel3.add(txtAno1, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 90, 50, 20));

        jLabel26.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel26.setText("Entidad de nacimiento:");
        panel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, -1, 20));

        comboEstadosNacimiento1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panel3.add(comboEstadosNacimiento1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 206, 20));

        jLabel27.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel27.setText("CURP:");
        panel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 52, 20));

        txtCurp1.setEditable(false);
        txtCurp1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurp1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurp1KeyTyped(evt);
            }
        });
        panel3.add(txtCurp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 90, 120, 20));

        txtOcr1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtOcr1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOcr1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOcr1KeyTyped(evt);
            }
        });
        panel3.add(txtOcr1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 90, 100, 20));

        jLabel28.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel28.setText("OCR:");
        panel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 90, -1, 20));

        jLabel91.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel91.setText("Empresa donde labora el cónyuge:");
        panel3.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 20));

        txtNombre6.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre6ActionPerformed(evt);
            }
        });
        txtNombre6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre6KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre6KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre6KeyTyped(evt);
            }
        });
        panel3.add(txtNombre6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, 270, 20));

        txtNombre7.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre7ActionPerformed(evt);
            }
        });
        txtNombre7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre7KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre7KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre7KeyTyped(evt);
            }
        });
        panel3.add(txtNombre7, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 130, 450, 20));

        jLabel92.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel92.setText("Domicilio de la empresa:");
        panel3.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, -1, 20));

        txtCurp6.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp6.setToolTipText("Homoclave (2 dígitos)");
        txtCurp6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurp6KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurp6KeyTyped(evt);
            }
        });
        panel3.add(txtCurp6, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 90, 30, 20));

        Contenedor.add(panel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 1180, 170));

        panel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DOMICILIO PARTICULAR", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCalle.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel4.add(txtCalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 300, 20));

        jLabel30.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel30.setText("Calle:");
        panel4.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel31.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel31.setText("Número exterior:");
        panel4.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, 20));

        txtCalle1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel4.add(txtCalle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 110, 20));

        txtCalle2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel4.add(txtCalle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, 110, 20));

        jLabel32.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel32.setText("Número interior:");
        panel4.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, -1, 20));

        txtCalle3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel4.add(txtCalle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, 250, 20));

        jLabel33.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel33.setText("Colonia:");
        panel4.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 20, -1, 20));

        txtCalle4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel4.add(txtCalle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 370, 20));

        jLabel34.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel34.setText("Entre calle:");
        panel4.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        txtCalle5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel4.add(txtCalle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 370, 20));

        jLabel35.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel35.setText("y calle:");
        panel4.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, -1, 20));

        jLabel36.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel36.setText("C.P.");
        panel4.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 60, -1, 20));

        txtCalle6.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel4.add(txtCalle6, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 60, 110, 20));

        jLabel37.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel37.setText("Ciudad:");
        panel4.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

        txtCalle7.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel4.add(txtCalle7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 160, 20));

        jLabel38.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel38.setText("Estado:");
        panel4.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, 20));

        txtCalle8.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel4.add(txtCalle8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 160, 20));

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel1.setText("Teléfono:");
        panel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, -1, 20));

        txtTel.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelKeyTyped(evt);
            }
        });
        panel4.add(txtTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, 107, 20));

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel2.setText("Celular:");
        panel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 100, -1, 20));

        txtCel.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelKeyTyped(evt);
            }
        });
        panel4.add(txtCel, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 100, 100, 20));

        Contenedor.add(panel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 560, 1180, 130));

        panel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "REFERENCIA 1", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        comboMeses2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panel5.add(comboMeses2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 116, 20));

        jLabel39.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel39.setText("Apellidos:");
        panel5.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, -1, 20));

        txtNombre3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre3ActionPerformed(evt);
            }
        });
        txtNombre3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre3KeyTyped(evt);
            }
        });
        panel5.add(txtNombre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 30, 269, 20));

        txtAno2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAno2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAno2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAno2KeyTyped(evt);
            }
        });
        panel5.add(txtAno2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, 50, 20));

        txtAp5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp5KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp5KeyTyped(evt);
            }
        });
        panel5.add(txtAp5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 200, 20));

        comboEstadosNacimiento2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panel5.add(comboEstadosNacimiento2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 70, 206, 20));

        jLabel40.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel40.setText("Fecha nacimiento:");
        panel5.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, 20));

        jLabel8.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel8.setText("Sexo:");
        panel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 20));

        txtCurp2.setEditable(false);
        txtCurp2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurp2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurp2KeyTyped(evt);
            }
        });
        panel5.add(txtCurp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 160, 20));

        txtDia2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDia2KeyTyped(evt);
            }
        });
        panel5.add(txtDia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 50, 20));

        jLabel41.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel41.setText("OCR:");
        panel5.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, -1, 20));

        txtAp6.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp6KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp6KeyTyped(evt);
            }
        });
        panel5.add(txtAp6, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, 200, 20));

        jLabel42.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel42.setText("Entidad de nacimiento:");
        panel5.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, -1, 20));

        txtOcr2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtOcr2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOcr2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOcr2KeyTyped(evt);
            }
        });
        panel5.add(txtOcr2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 140, 20));

        jLabel43.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel43.setText("Nombre(s):");
        panel5.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        jLabel44.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel44.setText("CURP:");
        panel5.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 52, 20));

        comboSexo2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboSexo2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--", "Hombre", "Mujer" }));
        comboSexo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSexo2ActionPerformed(evt);
            }
        });
        panel5.add(comboSexo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 150, 20));

        jLabel45.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel45.setText("Parentesco:");
        panel5.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, -1, 20));

        txtAp7.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp7KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp7KeyTyped(evt);
            }
        });
        panel5.add(txtAp7, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 30, 190, 20));
        panel5.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 1160, 5));

        txtCalle9.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel5.add(txtCalle9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 300, 20));

        jLabel46.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel46.setText("Calle:");
        panel5.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        jLabel47.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel47.setText("Número exterior:");
        panel5.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, 20));

        txtCalle10.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel5.add(txtCalle10, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 110, 20));

        txtCalle11.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel5.add(txtCalle11, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 160, 110, 20));

        jLabel48.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel48.setText("Número interior:");
        panel5.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, -1, 20));

        txtCalle12.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel5.add(txtCalle12, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 160, 230, 20));

        jLabel49.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel49.setText("Colonia:");
        panel5.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 160, -1, 20));

        txtCalle13.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel5.add(txtCalle13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 370, 20));

        jLabel50.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel50.setText("Entre calle:");
        panel5.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 20));

        txtCalle14.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel5.add(txtCalle14, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200, 370, 20));

        jLabel51.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel51.setText("y calle:");
        panel5.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 200, -1, 20));

        jLabel52.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel52.setText("C.P.");
        panel5.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 200, -1, 20));

        txtCalle15.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel5.add(txtCalle15, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 200, 110, 20));

        jLabel53.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel53.setText("Ciudad:");
        panel5.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 20));

        txtCalle16.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel5.add(txtCalle16, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 160, 20));

        jLabel54.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel54.setText("Estado:");
        panel5.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, -1, 20));

        txtCalle17.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel5.add(txtCalle17, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 160, 20));

        jLabel12.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel12.setText("Teléfono:");
        panel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 240, -1, 20));

        txtTel1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtTel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTel1KeyTyped(evt);
            }
        });
        panel5.add(txtTel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, 107, 20));

        jLabel13.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel13.setText("Celular:");
        panel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 240, -1, 20));

        txtCel1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCel1KeyTyped(evt);
            }
        });
        panel5.add(txtCel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 240, 100, 20));

        txtCurp7.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp7.setToolTipText("Homoclave (2 dígitos)");
        txtCurp7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurp7KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurp7KeyTyped(evt);
            }
        });
        panel5.add(txtCurp7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 30, 20));

        Contenedor.add(panel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 710, 1180, 280));

        panel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "REFERENCIA 2", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        comboMeses3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panel6.add(comboMeses3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 116, 20));

        jLabel55.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel55.setText("Apellidos:");
        panel6.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, -1, 20));

        txtNombre4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre4ActionPerformed(evt);
            }
        });
        txtNombre4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre4KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre4KeyTyped(evt);
            }
        });
        panel6.add(txtNombre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 30, 269, 20));

        txtAno3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAno3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAno3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAno3KeyTyped(evt);
            }
        });
        panel6.add(txtAno3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, 50, 20));

        txtAp8.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp8KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp8KeyTyped(evt);
            }
        });
        panel6.add(txtAp8, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 200, 20));

        comboEstadosNacimiento3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panel6.add(comboEstadosNacimiento3, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 70, 206, 20));

        jLabel56.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel56.setText("Fecha nacimiento:");
        panel6.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, 20));

        jLabel14.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel14.setText("Sexo:");
        panel6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 20));

        txtCurp3.setEditable(false);
        txtCurp3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurp3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurp3KeyTyped(evt);
            }
        });
        panel6.add(txtCurp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 160, 20));

        txtDia3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDia3KeyTyped(evt);
            }
        });
        panel6.add(txtDia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 50, 20));

        jLabel57.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel57.setText("OCR:");
        panel6.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, -1, 20));

        txtAp9.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp9KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp9KeyTyped(evt);
            }
        });
        panel6.add(txtAp9, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, 200, 20));

        jLabel58.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel58.setText("Entidad de nacimiento:");
        panel6.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, -1, 20));

        txtOcr3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtOcr3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOcr3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOcr3KeyTyped(evt);
            }
        });
        panel6.add(txtOcr3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 140, 20));

        jLabel59.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel59.setText("Nombre(s):");
        panel6.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        jLabel60.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel60.setText("CURP:");
        panel6.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 52, 20));

        comboSexo3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboSexo3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--", "Hombre", "Mujer" }));
        comboSexo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSexo3ActionPerformed(evt);
            }
        });
        panel6.add(comboSexo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 150, 20));

        jLabel61.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel61.setText("Parentesco:");
        panel6.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, -1, 20));

        txtAp10.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp10KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp10KeyTyped(evt);
            }
        });
        panel6.add(txtAp10, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 30, 190, 20));
        panel6.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 1160, 5));

        txtCalle18.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel6.add(txtCalle18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 300, 20));

        jLabel62.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel62.setText("Calle:");
        panel6.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        jLabel63.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel63.setText("Número exterior:");
        panel6.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, 20));

        txtCalle19.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel6.add(txtCalle19, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 110, 20));

        txtCalle20.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel6.add(txtCalle20, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 160, 110, 20));

        jLabel64.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel64.setText("Número interior:");
        panel6.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, -1, 20));

        txtCalle21.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel6.add(txtCalle21, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 160, 230, 20));

        jLabel65.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel65.setText("Colonia:");
        panel6.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 160, -1, 20));

        txtCalle22.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel6.add(txtCalle22, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 370, 20));

        jLabel66.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel66.setText("Entre calle:");
        panel6.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 20));

        txtCalle23.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel6.add(txtCalle23, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200, 370, 20));

        jLabel67.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel67.setText("y calle:");
        panel6.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 200, -1, 20));

        jLabel68.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel68.setText("C.P.");
        panel6.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 200, -1, 20));

        txtCalle24.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel6.add(txtCalle24, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 200, 110, 20));

        jLabel69.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel69.setText("Ciudad:");
        panel6.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 20));

        txtCalle25.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle25.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel6.add(txtCalle25, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 160, 20));

        jLabel70.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel70.setText("Estado:");
        panel6.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, -1, 20));

        txtCalle26.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle26.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel6.add(txtCalle26, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 160, 20));

        jLabel15.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel15.setText("Teléfono:");
        panel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 240, -1, 20));

        txtTel2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtTel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTel2KeyTyped(evt);
            }
        });
        panel6.add(txtTel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, 107, 20));

        jLabel71.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel71.setText("Celular:");
        panel6.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 240, -1, 20));

        txtCel2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCel2KeyTyped(evt);
            }
        });
        panel6.add(txtCel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 240, 100, 20));

        txtCurp8.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp8.setToolTipText("Homoclave (2 dígitos)");
        txtCurp8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurp8KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurp8KeyTyped(evt);
            }
        });
        panel6.add(txtCurp8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 30, 20));

        Contenedor.add(panel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1010, 1180, 280));

        panel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "AVAL", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel96.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel96.setText("Años de residir en el domicilio:");
        panel7.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 360, 200, 20));

        txtAnosResidencia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidenciaKeyTyped(evt);
            }
        });
        panel7.add(txtAnosResidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 360, 60, 20));

        comboMeses4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panel7.add(comboMeses4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 116, 20));

        jLabel72.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel72.setText("Apellidos:");
        panel7.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, -1, 20));

        txtNombre5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre5ActionPerformed(evt);
            }
        });
        txtNombre5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre5KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre5KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre5KeyTyped(evt);
            }
        });
        panel7.add(txtNombre5, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 30, 269, 20));

        txtAno4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAno4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAno4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAno4KeyTyped(evt);
            }
        });
        panel7.add(txtAno4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, 50, 20));

        txtAp11.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp11KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp11KeyTyped(evt);
            }
        });
        panel7.add(txtAp11, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 200, 20));

        comboEstadosNacimiento4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panel7.add(comboEstadosNacimiento4, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 70, 206, 20));

        jLabel73.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel73.setText("Fecha nacimiento:");
        panel7.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, 20));

        jLabel74.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel74.setText("Sexo:");
        panel7.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 20));

        txtCurp4.setEditable(false);
        txtCurp4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurp4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurp4KeyTyped(evt);
            }
        });
        panel7.add(txtCurp4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 160, 20));

        txtDia4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDia4KeyTyped(evt);
            }
        });
        panel7.add(txtDia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 50, 20));

        jLabel75.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel75.setText("OCR:");
        panel7.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, -1, 20));

        txtAp12.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp12KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp12KeyTyped(evt);
            }
        });
        panel7.add(txtAp12, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, 200, 20));

        jLabel76.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel76.setText("Entidad de nacimiento:");
        panel7.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, -1, 20));

        txtOcr4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtOcr4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOcr4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOcr4KeyTyped(evt);
            }
        });
        panel7.add(txtOcr4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 140, 20));

        jLabel77.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel77.setText("Nombre(s):");
        panel7.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        jLabel78.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel78.setText("CURP:");
        panel7.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 52, 20));

        comboSexo4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboSexo4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--", "Hombre", "Mujer" }));
        comboSexo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSexo4ActionPerformed(evt);
            }
        });
        panel7.add(comboSexo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 150, 20));

        jLabel79.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel79.setText("Parentesco:");
        panel7.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, -1, 20));

        txtAp13.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp13KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp13KeyTyped(evt);
            }
        });
        panel7.add(txtAp13, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 30, 190, 20));
        panel7.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 1160, 5));

        txtCalle27.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle27.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel7.add(txtCalle27, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 300, 20));

        jLabel80.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel80.setText("Calle:");
        panel7.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        jLabel81.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel81.setText("Número exterior:");
        panel7.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, 20));

        txtCalle28.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle28.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel7.add(txtCalle28, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 110, 20));

        txtCalle29.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle29.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel7.add(txtCalle29, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 160, 110, 20));

        jLabel82.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel82.setText("Número interior:");
        panel7.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, -1, 20));

        txtCalle30.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle30.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel7.add(txtCalle30, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 160, 230, 20));

        jLabel83.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel83.setText("Colonia:");
        panel7.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 160, -1, 20));

        txtCalle31.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle31.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel7.add(txtCalle31, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 370, 20));

        jLabel84.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel84.setText("Entre calle:");
        panel7.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 20));

        txtCalle32.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle32.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel7.add(txtCalle32, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200, 370, 20));

        jLabel85.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel85.setText("y calle:");
        panel7.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 200, -1, 20));

        jLabel86.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel86.setText("C.P.");
        panel7.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 200, -1, 20));

        txtCalle33.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle33.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel7.add(txtCalle33, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 200, 110, 20));

        jLabel87.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel87.setText("La casa que habita es:");
        panel7.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 130, 20));

        txtCalle34.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle34.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel7.add(txtCalle34, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 160, 20));

        jLabel88.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel88.setText("Estado:");
        panel7.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, -1, 20));

        txtCalle35.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCalle35.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel7.add(txtCalle35, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 160, 20));

        jLabel89.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel89.setText("Teléfono:");
        panel7.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 240, -1, 20));

        txtTel3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtTel3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTel3KeyTyped(evt);
            }
        });
        panel7.add(txtTel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, 107, 20));

        jLabel90.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel90.setText("Celular:");
        panel7.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 240, -1, 20));

        txtCel3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCel3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCel3KeyTyped(evt);
            }
        });
        panel7.add(txtCel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 240, 100, 20));
        panel7.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 1160, 5));

        jLabel93.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel93.setText("Ciudad:");
        panel7.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 20));

        jRadioButton1.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton1.setText("Prestada");
        panel7.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, -1, -1));

        jRadioButton2.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton2.setText("Propia");
        panel7.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));

        jRadioButton3.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton3.setText("Rentada");
        panel7.add(jRadioButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, -1, -1));

        jLabel94.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel94.setText("Nombre del propietario:");
        panel7.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, -1, 20));

        txtNombre8.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre8ActionPerformed(evt);
            }
        });
        txtNombre8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre8KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre8KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre8KeyTyped(evt);
            }
        });
        panel7.add(txtNombre8, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 320, 700, 20));

        jLabel95.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel95.setText("Vigencia del contrato:");
        panel7.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, 20));

        txtDia5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDia5ActionPerformed(evt);
            }
        });
        txtDia5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDia5KeyTyped(evt);
            }
        });
        panel7.add(txtDia5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 50, 20));

        comboMeses5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        panel7.add(comboMeses5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 360, 116, 20));

        txtAno5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAno5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAno5KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAno5KeyTyped(evt);
            }
        });
        panel7.add(txtAno5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 360, 50, 20));

        jLabel97.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel97.setText("Ocupación:");
        panel7.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 360, -1, 20));

        cmbEstadoCivil3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbEstadoCivil3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Comerciante", "Empleado(a)", "Estudiante", "Labores del hogar", "Ninguno" }));
        panel7.add(cmbEstadoCivil3, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 360, 150, 20));

        jLabel98.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel98.setText("Empresa donde labora el cónyuge:");
        panel7.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, 20));

        txtNombre9.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre9ActionPerformed(evt);
            }
        });
        txtNombre9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre9KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre9KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre9KeyTyped(evt);
            }
        });
        panel7.add(txtNombre9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 400, 290, 20));

        jLabel99.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel99.setText("Domicilio de la empresa:");
        panel7.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 400, -1, 20));

        txtNombre10.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre10ActionPerformed(evt);
            }
        });
        txtNombre10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre10KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre10KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre10KeyTyped(evt);
            }
        });
        panel7.add(txtNombre10, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 400, 450, 20));

        txtCurp9.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp9.setToolTipText("Homoclave (2 dígitos)");
        txtCurp9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurp9KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurp9KeyTyped(evt);
            }
        });
        panel7.add(txtCurp9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 30, 20));

        Contenedor.add(panel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1310, 1180, 440));

        panel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ACTIVIDAD ECONÓMICA DEL CLIENTE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel126.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel126.setText("Ocupación:");
        panel8.add(jLabel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, 20));

        cmbEstadoCivil5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbEstadoCivil5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Comerciante", "Empleado(a)", "Estudiante", "Labores del hogar", "Ninguno" }));
        panel8.add(cmbEstadoCivil5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 250, 20));

        jLabel127.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel127.setText("Cantidad de dependientes económicos:");
        panel8.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 30, 260, 20));

        txtDependientes.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDependientes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDependientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDependientesKeyTyped(evt);
            }
        });
        panel8.add(txtDependientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 30, 100, 20));

        jLabel100.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel100.setText("Empresa donde labora:");
        panel8.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 20));

        txtNombre11.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre11ActionPerformed(evt);
            }
        });
        txtNombre11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre11KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre11KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre11KeyTyped(evt);
            }
        });
        panel8.add(txtNombre11, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 970, 20));

        jLabel101.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel101.setText("Domicilio de la empresa:");
        panel8.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 20));

        txtNombre12.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre12ActionPerformed(evt);
            }
        });
        txtNombre12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre12KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre12KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre12KeyTyped(evt);
            }
        });
        panel8.add(txtNombre12, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 970, 20));

        panelIngresos2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DÍA DE SUELDO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 12))); // NOI18N
        panelIngresos2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel116.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel116.setText("de cada mes.");
        jLabel116.setEnabled(false);
        panelIngresos2.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 90, 20));

        jRadioButton4.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton4.setText("Eventual");
        panelIngresos2.add(jRadioButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, -1, -1));

        jRadioButton5.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton5.setText("Semanal");
        panelIngresos2.add(jRadioButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 50, 80, -1));

        jRadioButton6.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton6.setText("Quincenal");
        panelIngresos2.add(jRadioButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));

        jRadioButton7.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton7.setText("Mensual");
        panelIngresos2.add(jRadioButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, -1, -1));

        jLabel117.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel117.setText("Periodo:");
        panelIngresos2.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 120, 20));

        lunes.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        lunes.setText("Lunes");
        lunes.setEnabled(false);
        panelIngresos2.add(lunes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 80, 20));

        martes.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        martes.setText("Martes");
        martes.setEnabled(false);
        panelIngresos2.add(martes, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 80, 20));

        miercoles.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        miercoles.setText("Miércoles");
        miercoles.setEnabled(false);
        panelIngresos2.add(miercoles, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 80, 20));

        jueves.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        jueves.setText("Jueves");
        jueves.setEnabled(false);
        panelIngresos2.add(jueves, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 80, 20));

        viernes.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        viernes.setText("Viernes");
        viernes.setEnabled(false);
        panelIngresos2.add(viernes, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 80, 20));

        sabado.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        sabado.setText("Sábado");
        sabado.setEnabled(false);
        panelIngresos2.add(sabado, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 80, 20));

        domingo.setFont(new java.awt.Font("Solomon Sans Book", 0, 11)); // NOI18N
        domingo.setText("Domingo");
        domingo.setEnabled(false);
        panelIngresos2.add(domingo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 80, 20));

        jLabel118.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel118.setText("Días de pago:");
        panelIngresos2.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 120, 20));

        txtNombre13.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtNombre13.setEnabled(false);
        txtNombre13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre13ActionPerformed(evt);
            }
        });
        txtNombre13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre13KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombre13KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre13KeyTyped(evt);
            }
        });
        panelIngresos2.add(txtNombre13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 70, 20));

        jLabel119.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel119.setText("Día (s)");
        jLabel119.setEnabled(false);
        panelIngresos2.add(jLabel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 50, 20));

        panel8.add(panelIngresos2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 130, 310, 240));

        panelIngresos1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "EGRESOS MENSUALES", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 12))); // NOI18N
        panelIngresos1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel103.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel103.setText("Alimentación:");
        panelIngresos1.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 120, 20));

        txtIngresos6.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos6.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos6KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos6KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 110, 20));

        jLabel110.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setText("Renta o hipoteca:");
        panelIngresos1.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, 20));

        txtIngresos7.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos7.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos7.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos7KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos7KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 110, 20));

        jLabel111.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setText("Electricidad:");
        panelIngresos1.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 120, 20));

        txtIngresos8.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos8.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos8.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos8KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos8KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos8, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 110, 20));

        jLabel112.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel112.setText("Agua:");
        panelIngresos1.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 120, 20));

        txtIngresos9.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos9.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos9.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos9KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos9KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos9, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 110, 20));

        jLabel113.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel113.setText("Gas:");
        panelIngresos1.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 120, 20));

        txtIngresos10.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos10.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos10KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos10KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 110, 20));

        jLabel114.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel114.setText("Teléfono:");
        panelIngresos1.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 120, 20));

        txtIngresos11.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos11.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos11.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos11KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos11KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos11, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 110, 20));

        jLabel115.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel115.setText("Total:");
        panelIngresos1.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 210, 150, 20));

        txtTotalIngresos1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtTotalIngresos1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalIngresos1.setText("0.0");
        txtTotalIngresos1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtTotalIngresos1.setEnabled(false);
        txtTotalIngresos1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalIngresos1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalIngresos1KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtTotalIngresos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 210, 110, 20));

        jLabel123.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel123.setText("Cable o internet:");
        panelIngresos1.add(jLabel123, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 150, 20));

        txtIngresos18.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos18.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos18.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos18KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos18KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos18, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 110, 20));

        jLabel124.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setText("Predial:");
        panelIngresos1.add(jLabel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 150, 20));

        txtIngresos19.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos19.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos19.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos19KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos19KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos19, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 110, 20));

        jLabel125.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setText("Mtto. del hogar:");
        panelIngresos1.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 150, 20));

        jLabel128.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel128.setText("Escuela y material:");
        panelIngresos1.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 150, 20));

        txtIngresos20.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos20.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos20.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos20KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos20KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos20, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, 110, 20));

        txtIngresos21.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos21.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos21.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos21KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos21KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos21, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 110, 20));

        jLabel129.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel129.setText("Créditos o préstamos:");
        panelIngresos1.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, 150, 20));

        jLabel130.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel130.setText("Transporte:");
        panelIngresos1.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 150, 20));

        txtIngresos22.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos22.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos22.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos22KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos22KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos22, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 110, 20));

        txtIngresos23.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos23.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos23.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos23KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos23KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos23, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, 110, 20));

        jLabel131.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel131.setText("Otros:");
        panelIngresos1.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 150, 20));

        txtIngresos24.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos24.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos24.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos24KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos24KeyTyped(evt);
            }
        });
        panelIngresos1.add(txtIngresos24, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, 110, 20));

        panel8.add(panelIngresos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 550, 240));

        panelIngresos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INGRESOS MENSUALES", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 12))); // NOI18N
        panelIngresos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel102.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel102.setText("Ingreso actual:");
        panelIngresos.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 120, 20));

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
        panelIngresos.add(txtIngresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 110, 20));

        jLabel104.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel104.setText("Ingreso conyuge:");
        panelIngresos.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, 20));

        txtIngresos1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos1KeyTyped(evt);
            }
        });
        panelIngresos.add(txtIngresos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 110, 20));

        jLabel105.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel105.setText("Ingreso padre:");
        panelIngresos.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 120, 20));

        txtIngresos2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos2KeyTyped(evt);
            }
        });
        panelIngresos.add(txtIngresos2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 110, 20));

        jLabel106.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel106.setText("Ingreso madre:");
        panelIngresos.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 120, 20));

        txtIngresos3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos3KeyTyped(evt);
            }
        });
        panelIngresos.add(txtIngresos3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 110, 20));

        jLabel107.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel107.setText("Ingreso hermanos:");
        panelIngresos.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 120, 20));

        txtIngresos4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos4.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos4KeyTyped(evt);
            }
        });
        panelIngresos.add(txtIngresos4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 110, 20));

        jLabel108.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel108.setText("Otros ingresos:");
        panelIngresos.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 120, 20));

        txtIngresos5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtIngresos5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngresos5.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIngresos5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIngresos5KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngresos5KeyTyped(evt);
            }
        });
        panelIngresos.add(txtIngresos5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 110, 20));

        jLabel109.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel109.setText("Total:");
        panelIngresos.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 120, 20));

        txtTotalIngresos.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtTotalIngresos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalIngresos.setText("0.0");
        txtTotalIngresos.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtTotalIngresos.setEnabled(false);
        txtTotalIngresos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalIngresosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalIngresosKeyTyped(evt);
            }
        });
        panelIngresos.add(txtTotalIngresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 110, 20));

        panel8.add(panelIngresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 280, 240));

        Contenedor.add(panel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1770, 1180, 440));

        panel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CARACTERÍSTICAS DE VIVIENDA", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel120.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel120.setText("Régimen:");
        panel9.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 70, 20));

        comboTipoVivienda.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboTipoVivienda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--", "Hipotecario", "Prestado", "Propio", "Rentado" }));
        comboTipoVivienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoViviendaActionPerformed(evt);
            }
        });
        panel9.add(comboTipoVivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 30, 120, 20));

        jLabel158.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel158.setText("Tipo de vivienda:");
        panel9.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 110, 20));

        txtAnosResidencia1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia1KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 60, 20));

        cmbEstadoCivil6.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbEstadoCivil6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--", "Casa", "Condominio", "Departamento", "Duplex", "Vecindad" }));
        panel9.add(cmbEstadoCivil6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 190, 20));

        jLabel121.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel121.setText("años.");
        panel9.add(jLabel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 40, 20));

        comboTipoVivienda1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboTipoVivienda1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--", "Rural", "Suburbana", "Urbana" }));
        comboTipoVivienda1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoVivienda1ActionPerformed(evt);
            }
        });
        panel9.add(comboTipoVivienda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 30, 130, 20));

        jLabel122.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel122.setText("Zona:");
        panel9.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 30, 50, 20));

        jLabel160.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel160.setText("Tiempo de vivir en el domicilio actual:");
        panel9.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 240, 20));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jTextArea1.setRows(3);
        jTextArea1.setToolTipText("Comentarios");
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mobiliario (observaciones):", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 12))); // NOI18N
        jScrollPane2.setViewportView(jTextArea1);

        panel9.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 70, 390, 70));

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jTextArea2.setRows(3);
        jTextArea2.setToolTipText("Comentarios");
        jTextArea2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo de construcción (observaciones):", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 12))); // NOI18N
        jScrollPane4.setViewportView(jTextArea2);

        panel9.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 390, 70));

        jLabel132.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel132.setText("Cuenta con:");
        panel9.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 120, 20));

        jRadioButton8.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton8.setText("Más de lo necesario");
        panel9.add(jRadioButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 170, -1));

        jRadioButton9.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton9.setText("Lo necesario");
        panel9.add(jRadioButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 160, 140, -1));

        jRadioButton10.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton10.setText("Menos de lo necesario");
        panel9.add(jRadioButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, 210, -1));

        jLabel133.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel133.setText("La limpieza de su hogar es:");
        panel9.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 190, 20));

        jRadioButton11.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton11.setText("Excelente");
        panel9.add(jRadioButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, 100, -1));

        jRadioButton12.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton12.setText("Buena");
        panel9.add(jRadioButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 200, 80, -1));

        jRadioButton13.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton13.setText("Mala");
        panel9.add(jRadioButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 200, 80, -1));

        jRadioButton14.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton14.setText("Regular");
        panel9.add(jRadioButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 200, 80, -1));

        jLabel161.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel161.setText("Niveles o plantas:");
        panel9.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 120, 20));

        txtAnosResidencia2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia2KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 60, 20));

        jLabel162.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel162.setText("Recámaras:");
        panel9.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 240, 80, 20));

        txtAnosResidencia3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia3KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 240, 60, 20));

        jLabel163.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel163.setText("Baños:");
        panel9.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 240, 50, 20));

        txtAnosResidencia4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia4KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, 60, 20));

        jLabel164.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel164.setText("Cocinas:");
        panel9.add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 240, 60, 20));

        txtAnosResidencia5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia5KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia5, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 240, 60, 20));

        jLabel165.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel165.setText("Salas:");
        panel9.add(jLabel165, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 240, 50, 20));

        txtAnosResidencia6.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia6KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia6, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 240, 60, 20));

        jLabel166.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel166.setText("Comedores:");
        panel9.add(jLabel166, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 240, 90, 20));

        txtAnosResidencia7.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia7KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 240, 60, 20));

        jLabel167.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel167.setText("Garajes:");
        panel9.add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, 80, 20));

        txtAnosResidencia8.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia8KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 60, 20));

        jLabel168.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel168.setText("Cuarto servicio:");
        panel9.add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 120, 20));

        txtAnosResidencia9.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia9KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia9, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 270, 60, 20));

        jLabel134.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel134.setText("Existe orden:");
        panel9.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 90, 20));

        jRadioButton15.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton15.setText("Si");
        panel9.add(jRadioButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 40, -1));

        jRadioButton16.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton16.setText("No");
        panel9.add(jRadioButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 300, 50, -1));

        jLabel135.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel135.setText("Número visible:");
        panel9.add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 300, 110, 20));

        jRadioButton17.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton17.setText("Si");
        panel9.add(jRadioButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, 40, -1));

        jRadioButton18.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton18.setText("No");
        panel9.add(jRadioButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 50, -1));

        jLabel136.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel136.setText("¿Es adecuada para casa habitación?");
        panel9.add(jLabel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 300, 220, 20));

        jRadioButton19.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton19.setText("Si");
        panel9.add(jRadioButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 300, 40, -1));

        jRadioButton20.setFont(new java.awt.Font("Solomon Sans Book", 1, 11)); // NOI18N
        jRadioButton20.setText("No");
        panel9.add(jRadioButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 300, 50, -1));

        jLabel169.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel169.setText("Radio:");
        panel9.add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 60, 20));

        txtAnosResidencia10.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia10KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia10, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 330, 60, 20));

        jLabel170.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel170.setText("Refrigerador:");
        panel9.add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 330, 90, 20));

        txtAnosResidencia11.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia11KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia11, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 330, 60, 20));

        jLabel171.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel171.setText("Televisión:");
        panel9.add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 330, 70, 20));

        txtAnosResidencia12.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia12KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia12, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 330, 60, 20));

        jLabel172.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel172.setText("Lavadora:");
        panel9.add(jLabel172, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 330, 60, 20));

        txtAnosResidencia13.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia13KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia13, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 330, 60, 20));

        jLabel173.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel173.setText("Computadora:");
        panel9.add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 330, 90, 20));

        txtAnosResidencia14.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia14KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia14, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 330, 50, 20));

        jLabel174.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jLabel174.setText("Internet:");
        panel9.add(jLabel174, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 330, 60, 20));

        txtAnosResidencia15.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidencia15KeyTyped(evt);
            }
        });
        panel9.add(txtAnosResidencia15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 330, 60, 20));

        Contenedor.add(panel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2230, 1180, 450));

        jScrollPane1.setViewportView(Contenedor);

        PanelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1220, 3535));

        getContentPane().add(PanelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void txtCelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelKeyTyped
        int lon = txtCel.getText().length();
        if (lon >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCelKeyTyped

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed

    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed

    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        String cadena = txtNombre.getText().toUpperCase();
        txtNombre.setText(cadena);
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtAnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnoKeyReleased

    }//GEN-LAST:event_txtAnoKeyReleased

    private void txtAnoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnoKeyTyped
        int lon = txtAno.getText().length();
        char c = evt.getKeyChar();
        if (lon >= 4 || !Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAnoKeyTyped

    private void txtAp1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp1KeyReleased
        String cadena = txtAp1.getText().toUpperCase();
        txtAp1.setText(cadena);
    }//GEN-LAST:event_txtAp1KeyReleased

    private void txtAp1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp1KeyTyped
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAp1KeyTyped

    private void txtCurpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurpKeyReleased
        String cadena = txtCurp.getText().toUpperCase();
        txtCurp.setText(cadena);
    }//GEN-LAST:event_txtCurpKeyReleased

    private void txtCurpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurpKeyTyped
        int lon = txtCurp.getText().length();
        if (lon >= 18) {
            evt.consume();
            //            JOptionPane.showMessageDialog(rootPane, "El código CURP contiene únicamente 18 caracteres");
        }
    }//GEN-LAST:event_txtCurpKeyTyped

    private void txtDiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiaKeyTyped
        char c = evt.getKeyChar();
        int lon = txtDia.getText().length();
        if (!Character.isDigit(c) || lon >= 2) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDiaKeyTyped

    private void txtTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelKeyTyped
        int lon = txtTel.getText().length();
        if (lon >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTelKeyTyped

    private void txtAp2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp2KeyReleased
        String cadena = txtAp2.getText().toUpperCase();
        txtAp2.setText(cadena);
    }//GEN-LAST:event_txtAp2KeyReleased

    private void txtAp2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp2KeyTyped
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAp2KeyTyped

    private void txtOcrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcrKeyReleased
        String cadena = txtOcr.getText().toUpperCase();
        txtOcr.setText(cadena);
    }//GEN-LAST:event_txtOcrKeyReleased

    private void txtOcrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcrKeyTyped
        int lon = txtOcr.getText().length();
        if (lon >= 13) {
            evt.consume();
            //            JOptionPane.showMessageDialog(rootPane, "El código OCR contiene únicamente 13 caracteres");
        }
    }//GEN-LAST:event_txtOcrKeyTyped

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

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void comboSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSexoActionPerformed

    private void txtNombre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre1ActionPerformed

    private void txtNombre1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre1KeyPressed

    private void txtNombre1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre1KeyReleased

    private void txtNombre1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre1KeyTyped

    private void txtAp3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp3KeyReleased

    private void txtAp3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp3KeyTyped

    private void txtAp4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp4KeyReleased

    private void txtAp4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp4KeyTyped

    private void comboSexo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSexo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSexo1ActionPerformed

    private void txtDia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDia1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDia1KeyTyped

    private void txtAno1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAno1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAno1KeyReleased

    private void txtAno1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAno1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAno1KeyTyped

    private void txtCurp1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp1KeyReleased

    private void txtCurp1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp1KeyTyped

    private void txtOcr1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcr1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOcr1KeyReleased

    private void txtOcr1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcr1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOcr1KeyTyped

    private void txtNombre2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre2ActionPerformed

    private void txtNombre2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre2KeyPressed

    private void txtNombre2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre2KeyReleased

    private void txtNombre2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre2KeyTyped

    private void txtNombre3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre3ActionPerformed

    private void txtNombre3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre3KeyPressed

    private void txtNombre3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre3KeyReleased

    private void txtNombre3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre3KeyTyped

    private void txtAno2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAno2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAno2KeyReleased

    private void txtAno2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAno2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAno2KeyTyped

    private void txtAp5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp5KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp5KeyReleased

    private void txtAp5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp5KeyTyped

    private void txtCurp2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp2KeyReleased

    private void txtCurp2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp2KeyTyped

    private void txtDia2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDia2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDia2KeyTyped

    private void txtAp6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp6KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp6KeyReleased

    private void txtAp6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp6KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp6KeyTyped

    private void txtOcr2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcr2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOcr2KeyReleased

    private void txtOcr2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcr2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOcr2KeyTyped

    private void comboSexo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSexo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSexo2ActionPerformed

    private void txtAp7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp7KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp7KeyReleased

    private void txtAp7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp7KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp7KeyTyped

    private void txtTel1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTel1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTel1KeyTyped

    private void txtCel1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCel1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCel1KeyTyped

    private void txtNombre4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre4ActionPerformed

    private void txtNombre4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre4KeyPressed

    private void txtNombre4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre4KeyReleased

    private void txtNombre4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre4KeyTyped

    private void txtAno3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAno3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAno3KeyReleased

    private void txtAno3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAno3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAno3KeyTyped

    private void txtAp8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp8KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp8KeyReleased

    private void txtAp8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp8KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp8KeyTyped

    private void txtCurp3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp3KeyReleased

    private void txtCurp3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp3KeyTyped

    private void txtDia3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDia3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDia3KeyTyped

    private void txtAp9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp9KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp9KeyReleased

    private void txtAp9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp9KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp9KeyTyped

    private void txtOcr3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcr3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOcr3KeyReleased

    private void txtOcr3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcr3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOcr3KeyTyped

    private void comboSexo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSexo3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSexo3ActionPerformed

    private void txtAp10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp10KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp10KeyReleased

    private void txtAp10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp10KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp10KeyTyped

    private void txtTel2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTel2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTel2KeyTyped

    private void txtCel2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCel2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCel2KeyTyped

    private void txtNombre5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre5ActionPerformed

    private void txtNombre5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre5KeyPressed

    private void txtNombre5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre5KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre5KeyReleased

    private void txtNombre5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre5KeyTyped

    private void txtAno4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAno4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAno4KeyReleased

    private void txtAno4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAno4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAno4KeyTyped

    private void txtAp11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp11KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp11KeyReleased

    private void txtAp11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp11KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp11KeyTyped

    private void txtCurp4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp4KeyReleased

    private void txtCurp4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp4KeyTyped

    private void txtDia4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDia4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDia4KeyTyped

    private void txtAp12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp12KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp12KeyReleased

    private void txtAp12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp12KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp12KeyTyped

    private void txtOcr4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcr4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOcr4KeyReleased

    private void txtOcr4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcr4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOcr4KeyTyped

    private void comboSexo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSexo4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSexo4ActionPerformed

    private void txtAp13KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp13KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp13KeyReleased

    private void txtAp13KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp13KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAp13KeyTyped

    private void txtTel3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTel3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTel3KeyTyped

    private void txtCel3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCel3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCel3KeyTyped

    private void txtNombre6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre6ActionPerformed

    private void txtNombre6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre6KeyPressed

    private void txtNombre6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre6KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre6KeyReleased

    private void txtNombre6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre6KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre6KeyTyped

    private void txtNombre7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre7ActionPerformed

    private void txtNombre7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre7KeyPressed

    private void txtNombre7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre7KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre7KeyReleased

    private void txtNombre7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre7KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre7KeyTyped

    private void txtNombre8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre8ActionPerformed

    private void txtNombre8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre8KeyPressed

    private void txtNombre8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre8KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre8KeyReleased

    private void txtNombre8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre8KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre8KeyTyped

    private void txtDia5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDia5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDia5KeyTyped

    private void txtAno5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAno5KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAno5KeyReleased

    private void txtAno5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAno5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAno5KeyTyped

    private void txtDia5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDia5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDia5ActionPerformed

    private void txtAnosResidenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidenciaKeyTyped
        char c = evt.getKeyChar();
        int lon = txtAnosResidencia.getText().length();
        if (!Character.isDigit(c) || lon >= 2) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAnosResidenciaKeyTyped

    private void txtNombre9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre9ActionPerformed

    private void txtNombre9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre9KeyPressed

    private void txtNombre9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre9KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre9KeyReleased

    private void txtNombre9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre9KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre9KeyTyped

    private void txtNombre10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre10ActionPerformed

    private void txtNombre10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre10KeyPressed

    private void txtNombre10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre10KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre10KeyReleased

    private void txtNombre10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre10KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre10KeyTyped

    private void txtDependientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDependientesKeyTyped
        char c = evt.getKeyChar();
        int lon = txtDependientes.getText().length();
        if (!Character.isDigit(c) || lon >= 2) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDependientesKeyTyped

    private void txtNombre11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre11ActionPerformed

    private void txtNombre11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre11KeyPressed

    private void txtNombre11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre11KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre11KeyReleased

    private void txtNombre11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre11KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre11KeyTyped

    private void txtNombre12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre12ActionPerformed

    private void txtNombre12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre12KeyPressed

    private void txtNombre12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre12KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre12KeyReleased

    private void txtNombre12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre12KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre12KeyTyped

    private void txtIngresosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresosKeyReleased
//        restarIngresosyEgresos();
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

    private void txtIngresos1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos1KeyReleased

    private void txtIngresos1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos1KeyTyped

    private void txtIngresos2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos2KeyReleased

    private void txtIngresos2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos2KeyTyped

    private void txtIngresos3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos3KeyReleased

    private void txtIngresos3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos3KeyTyped

    private void txtIngresos4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos4KeyReleased

    private void txtIngresos4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos4KeyTyped

    private void txtIngresos5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos5KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos5KeyReleased

    private void txtIngresos5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos5KeyTyped

    private void txtTotalIngresosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalIngresosKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalIngresosKeyReleased

    private void txtTotalIngresosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalIngresosKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalIngresosKeyTyped

    private void txtIngresos6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos6KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos6KeyReleased

    private void txtIngresos6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos6KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos6KeyTyped

    private void txtIngresos7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos7KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos7KeyReleased

    private void txtIngresos7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos7KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos7KeyTyped

    private void txtIngresos8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos8KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos8KeyReleased

    private void txtIngresos8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos8KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos8KeyTyped

    private void txtIngresos9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos9KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos9KeyReleased

    private void txtIngresos9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos9KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos9KeyTyped

    private void txtIngresos10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos10KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos10KeyReleased

    private void txtIngresos10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos10KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos10KeyTyped

    private void txtIngresos11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos11KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos11KeyReleased

    private void txtIngresos11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos11KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos11KeyTyped

    private void txtTotalIngresos1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalIngresos1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalIngresos1KeyReleased

    private void txtTotalIngresos1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalIngresos1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalIngresos1KeyTyped

    private void txtIngresos18KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos18KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos18KeyReleased

    private void txtIngresos18KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos18KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos18KeyTyped

    private void txtIngresos19KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos19KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos19KeyReleased

    private void txtIngresos19KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos19KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos19KeyTyped

    private void txtIngresos20KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos20KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos20KeyReleased

    private void txtIngresos20KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos20KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos20KeyTyped

    private void txtIngresos21KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos21KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos21KeyReleased

    private void txtIngresos21KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos21KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos21KeyTyped

    private void txtIngresos22KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos22KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos22KeyReleased

    private void txtIngresos22KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos22KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos22KeyTyped

    private void txtIngresos23KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos23KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos23KeyReleased

    private void txtIngresos23KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos23KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos23KeyTyped

    private void txtIngresos24KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos24KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos24KeyReleased

    private void txtIngresos24KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngresos24KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngresos24KeyTyped

    private void txtCurp5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp5KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp5KeyReleased

    private void txtCurp5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp5KeyTyped

    private void txtNombre13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre13ActionPerformed

    private void txtNombre13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre13KeyPressed

    private void txtNombre13KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre13KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre13KeyReleased

    private void txtNombre13KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre13KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre13KeyTyped

    private void txtAnosResidencia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia1KeyTyped
        char c = evt.getKeyChar();
        int lon = txtAnosResidencia.getText().length();
        if (!Character.isDigit(c) || lon >= 2) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAnosResidencia1KeyTyped

    private void comboTipoViviendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoViviendaActionPerformed
        int i = comboTipoVivienda.getSelectedIndex();
        if (i > 1) {
//            frmArrendamientoOnOff(true);
        } else {
//            frmArrendamientoOnOff(false);
        }
    }//GEN-LAST:event_comboTipoViviendaActionPerformed

    private void comboTipoVivienda1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoVivienda1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoVivienda1ActionPerformed

    private void txtCurp6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp6KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp6KeyReleased

    private void txtCurp6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp6KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp6KeyTyped

    private void txtCurp7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp7KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp7KeyReleased

    private void txtCurp7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp7KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp7KeyTyped

    private void txtCurp8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp8KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp8KeyReleased

    private void txtCurp8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp8KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp8KeyTyped

    private void txtCurp9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp9KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp9KeyReleased

    private void txtCurp9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurp9KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurp9KeyTyped

    private void txtAnosResidencia2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia2KeyTyped

    private void txtAnosResidencia3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia3KeyTyped

    private void txtAnosResidencia4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia4KeyTyped

    private void txtAnosResidencia5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia5KeyTyped

    private void txtAnosResidencia6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia6KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia6KeyTyped

    private void txtAnosResidencia7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia7KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia7KeyTyped

    private void txtAnosResidencia8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia8KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia8KeyTyped

    private void txtAnosResidencia9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia9KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia9KeyTyped

    private void txtAnosResidencia10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia10KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia10KeyTyped

    private void txtAnosResidencia11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia11KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia11KeyTyped

    private void txtAnosResidencia12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia12KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia12KeyTyped

    private void txtAnosResidencia13KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia13KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia13KeyTyped

    private void txtAnosResidencia14KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia14KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia14KeyTyped

    private void txtAnosResidencia15KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidencia15KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnosResidencia15KeyTyped

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
            java.util.logging.Logger.getLogger(Solicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            Usuario usuario = null;
            Solicitud dialog = new Solicitud(new javax.swing.JFrame(), true, usuario, new String());
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
    private javax.swing.JButton btnCerrar;
    private javax.swing.JComboBox<String> cmbEstadoCivil;
    private javax.swing.JComboBox<String> cmbEstadoCivil1;
    private javax.swing.JComboBox<String> cmbEstadoCivil2;
    private javax.swing.JComboBox<String> cmbEstadoCivil3;
    private javax.swing.JComboBox<String> cmbEstadoCivil5;
    private javax.swing.JComboBox<String> cmbEstadoCivil6;
    private javax.swing.JComboBox<String> comboEstadosNacimiento;
    private javax.swing.JComboBox<String> comboEstadosNacimiento1;
    private javax.swing.JComboBox<String> comboEstadosNacimiento2;
    private javax.swing.JComboBox<String> comboEstadosNacimiento3;
    private javax.swing.JComboBox<String> comboEstadosNacimiento4;
    private javax.swing.JComboBox<String> comboMeses;
    private javax.swing.JComboBox<String> comboMeses1;
    private javax.swing.JComboBox<String> comboMeses2;
    private javax.swing.JComboBox<String> comboMeses3;
    private javax.swing.JComboBox<String> comboMeses4;
    private javax.swing.JComboBox<String> comboMeses5;
    private javax.swing.JComboBox<String> comboSexo;
    private javax.swing.JComboBox<String> comboSexo1;
    private javax.swing.JComboBox<String> comboSexo2;
    private javax.swing.JComboBox<String> comboSexo3;
    private javax.swing.JComboBox<String> comboSexo4;
    private javax.swing.JComboBox<String> comboTipoVivienda;
    private javax.swing.JComboBox<String> comboTipoVivienda1;
    private javax.swing.JCheckBox domingo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
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
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton13;
    private javax.swing.JRadioButton jRadioButton14;
    private javax.swing.JRadioButton jRadioButton15;
    private javax.swing.JRadioButton jRadioButton16;
    private javax.swing.JRadioButton jRadioButton17;
    private javax.swing.JRadioButton jRadioButton18;
    private javax.swing.JRadioButton jRadioButton19;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton20;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JCheckBox jueves;
    private javax.swing.JCheckBox lunes;
    private javax.swing.JCheckBox martes;
    private javax.swing.JCheckBox miercoles;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panel4;
    private javax.swing.JPanel panel5;
    private javax.swing.JPanel panel6;
    private javax.swing.JPanel panel7;
    private javax.swing.JPanel panel8;
    private javax.swing.JPanel panel9;
    private javax.swing.JPanel panelIngresos;
    private javax.swing.JPanel panelIngresos1;
    private javax.swing.JPanel panelIngresos2;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JCheckBox sabado;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtAno1;
    private javax.swing.JTextField txtAno2;
    private javax.swing.JTextField txtAno3;
    private javax.swing.JTextField txtAno4;
    private javax.swing.JTextField txtAno5;
    private javax.swing.JTextField txtAnosResidencia;
    private javax.swing.JTextField txtAnosResidencia1;
    private javax.swing.JTextField txtAnosResidencia10;
    private javax.swing.JTextField txtAnosResidencia11;
    private javax.swing.JTextField txtAnosResidencia12;
    private javax.swing.JTextField txtAnosResidencia13;
    private javax.swing.JTextField txtAnosResidencia14;
    private javax.swing.JTextField txtAnosResidencia15;
    private javax.swing.JTextField txtAnosResidencia2;
    private javax.swing.JTextField txtAnosResidencia3;
    private javax.swing.JTextField txtAnosResidencia4;
    private javax.swing.JTextField txtAnosResidencia5;
    private javax.swing.JTextField txtAnosResidencia6;
    private javax.swing.JTextField txtAnosResidencia7;
    private javax.swing.JTextField txtAnosResidencia8;
    private javax.swing.JTextField txtAnosResidencia9;
    private javax.swing.JTextField txtAp1;
    private javax.swing.JTextField txtAp10;
    private javax.swing.JTextField txtAp11;
    private javax.swing.JTextField txtAp12;
    private javax.swing.JTextField txtAp13;
    private javax.swing.JTextField txtAp2;
    private javax.swing.JTextField txtAp3;
    private javax.swing.JTextField txtAp4;
    private javax.swing.JTextField txtAp5;
    private javax.swing.JTextField txtAp6;
    private javax.swing.JTextField txtAp7;
    private javax.swing.JTextField txtAp8;
    private javax.swing.JTextField txtAp9;
    private javax.swing.JTextField txtBuscar2;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCalle1;
    private javax.swing.JTextField txtCalle10;
    private javax.swing.JTextField txtCalle11;
    private javax.swing.JTextField txtCalle12;
    private javax.swing.JTextField txtCalle13;
    private javax.swing.JTextField txtCalle14;
    private javax.swing.JTextField txtCalle15;
    private javax.swing.JTextField txtCalle16;
    private javax.swing.JTextField txtCalle17;
    private javax.swing.JTextField txtCalle18;
    private javax.swing.JTextField txtCalle19;
    private javax.swing.JTextField txtCalle2;
    private javax.swing.JTextField txtCalle20;
    private javax.swing.JTextField txtCalle21;
    private javax.swing.JTextField txtCalle22;
    private javax.swing.JTextField txtCalle23;
    private javax.swing.JTextField txtCalle24;
    private javax.swing.JTextField txtCalle25;
    private javax.swing.JTextField txtCalle26;
    private javax.swing.JTextField txtCalle27;
    private javax.swing.JTextField txtCalle28;
    private javax.swing.JTextField txtCalle29;
    private javax.swing.JTextField txtCalle3;
    private javax.swing.JTextField txtCalle30;
    private javax.swing.JTextField txtCalle31;
    private javax.swing.JTextField txtCalle32;
    private javax.swing.JTextField txtCalle33;
    private javax.swing.JTextField txtCalle34;
    private javax.swing.JTextField txtCalle35;
    private javax.swing.JTextField txtCalle4;
    private javax.swing.JTextField txtCalle5;
    private javax.swing.JTextField txtCalle6;
    private javax.swing.JTextField txtCalle7;
    private javax.swing.JTextField txtCalle8;
    private javax.swing.JTextField txtCalle9;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtCel1;
    private javax.swing.JTextField txtCel2;
    private javax.swing.JTextField txtCel3;
    private javax.swing.JTextField txtCurp;
    private javax.swing.JTextField txtCurp1;
    private javax.swing.JTextField txtCurp2;
    private javax.swing.JTextField txtCurp3;
    private javax.swing.JTextField txtCurp4;
    private javax.swing.JTextField txtCurp5;
    private javax.swing.JTextField txtCurp6;
    private javax.swing.JTextField txtCurp7;
    private javax.swing.JTextField txtCurp8;
    private javax.swing.JTextField txtCurp9;
    private javax.swing.JTextField txtDependientes;
    private javax.swing.JTextField txtDia;
    private javax.swing.JTextField txtDia1;
    private javax.swing.JTextField txtDia2;
    private javax.swing.JTextField txtDia3;
    private javax.swing.JTextField txtDia4;
    private javax.swing.JTextField txtDia5;
    private javax.swing.JTextField txtIngresos;
    private javax.swing.JTextField txtIngresos1;
    private javax.swing.JTextField txtIngresos10;
    private javax.swing.JTextField txtIngresos11;
    private javax.swing.JTextField txtIngresos18;
    private javax.swing.JTextField txtIngresos19;
    private javax.swing.JTextField txtIngresos2;
    private javax.swing.JTextField txtIngresos20;
    private javax.swing.JTextField txtIngresos21;
    private javax.swing.JTextField txtIngresos22;
    private javax.swing.JTextField txtIngresos23;
    private javax.swing.JTextField txtIngresos24;
    private javax.swing.JTextField txtIngresos3;
    private javax.swing.JTextField txtIngresos4;
    private javax.swing.JTextField txtIngresos5;
    private javax.swing.JTextField txtIngresos6;
    private javax.swing.JTextField txtIngresos7;
    private javax.swing.JTextField txtIngresos8;
    private javax.swing.JTextField txtIngresos9;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtNombre10;
    private javax.swing.JTextField txtNombre11;
    private javax.swing.JTextField txtNombre12;
    private javax.swing.JTextField txtNombre13;
    private javax.swing.JTextField txtNombre2;
    private javax.swing.JTextField txtNombre3;
    private javax.swing.JTextField txtNombre4;
    private javax.swing.JTextField txtNombre5;
    private javax.swing.JTextField txtNombre6;
    private javax.swing.JTextField txtNombre7;
    private javax.swing.JTextField txtNombre8;
    private javax.swing.JTextField txtNombre9;
    private javax.swing.JTextField txtOcr;
    private javax.swing.JTextField txtOcr1;
    private javax.swing.JTextField txtOcr2;
    private javax.swing.JTextField txtOcr3;
    private javax.swing.JTextField txtOcr4;
    private javax.swing.JTextField txtTel;
    private javax.swing.JTextField txtTel1;
    private javax.swing.JTextField txtTel2;
    private javax.swing.JTextField txtTel3;
    private javax.swing.JTextField txtTotalIngresos;
    private javax.swing.JTextField txtTotalIngresos1;
    private javax.swing.JCheckBox viernes;
    // End of variables declaration//GEN-END:variables
}
