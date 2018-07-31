package views;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1200, 620));
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
        Contenedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscador de personas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
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

        panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ACERCA DEL PRÉSTAMO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
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

        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATOS DE IDENTIFICACIÓN DEL SOLICITANTE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
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

        txtCurp.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurpKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurpKeyTyped(evt);
            }
        });
        panel2.add(txtCurp, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 70, 160, 20));

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

        Contenedor.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 1180, 100));

        panel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ESTADO CIVIL", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
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

        txtCurp1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurp1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurp1KeyTyped(evt);
            }
        });
        panel3.add(txtCurp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 90, 160, 20));

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

        Contenedor.add(panel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 1180, 170));

        panel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DOMICILIO PARTICULAR", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
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

        panel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "REFERENCIA 1", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
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
        panel5.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, -1, 20));

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
        panel5.add(txtOcr2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 140, 20));

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

        Contenedor.add(panel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 710, 1180, 280));

        panel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "REFERENCIA 2", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
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
        panel6.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, -1, 20));

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
        panel6.add(txtOcr3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 140, 20));

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

        Contenedor.add(panel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1010, 1180, 280));

        panel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "AVAL", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
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
        panel7.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, -1, 20));

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
        panel7.add(txtOcr4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 140, 20));

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
        panel7.add(txtNombre9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 400, 260, 20));

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

        Contenedor.add(panel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1310, 1180, 570));

        jScrollPane1.setViewportView(Contenedor);

        PanelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1220, 530));

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panel4;
    private javax.swing.JPanel panel5;
    private javax.swing.JPanel panel6;
    private javax.swing.JPanel panel7;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtAno1;
    private javax.swing.JTextField txtAno2;
    private javax.swing.JTextField txtAno3;
    private javax.swing.JTextField txtAno4;
    private javax.swing.JTextField txtAno5;
    private javax.swing.JTextField txtAnosResidencia;
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
    private javax.swing.JTextField txtDia;
    private javax.swing.JTextField txtDia1;
    private javax.swing.JTextField txtDia2;
    private javax.swing.JTextField txtDia3;
    private javax.swing.JTextField txtDia4;
    private javax.swing.JTextField txtDia5;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtNombre10;
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
    // End of variables declaration//GEN-END:variables
}
