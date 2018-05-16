package views;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import static modules.administración.escritorio;
import objects.Amortizacion;
import objects.Cliente;
import objects.Estado;
import objects.Fecha;
import objects.Mes;
import objects.Persona;
import objects.Solicitud;
import objects.Usuario;
import services.agregarPersona_service;

/**
 *
 * @author JMalagon
 */
public final class agregar_personas extends javax.swing.JInternalFrame {

    private final agregarPersona_service servicio;
    private int ID_PERSONA_SELECCIONADA = 0;
    private Persona PERSONA_SELECCIONADA = null;
    private Usuario USUARIO = null;
    private Cliente CLIENTE = null;

    public agregar_personas(Usuario usuario) {
        initComponents();
        this.servicio = new agregarPersona_service();
        this.meses();
        this.estadosNacimiento();
        this.USUARIO = usuario;
        llenarTabla();
        seleccionarPersona();
        opciones(false);
    }

    private void solicitarPrestamo() {
        if (PERSONA_SELECCIONADA != null) {
            try {
                if (PERSONA_SELECCIONADA.getReferencia() > 0) {
                    Amortizacion amr = new Amortizacion();
                    String[] montos = amr.montos();
                    Object cantidades = JOptionPane.showInputDialog(this, "Ingrese el monto a solicitar", "Solicitar préstamo", JOptionPane.QUESTION_MESSAGE, null, montos, "0");
                    if (cantidades != null) {
                        CLIENTE = this.servicio.cliente(PERSONA_SELECCIONADA);
                        if (CLIENTE != null) {
                            int nPrestamos = 100;//CONSULTAR A LA BASE DE DATOS CUANTOS PRESTAMOS LLEVA EL CLIENTE
                            double tasa;
                            //GENERAR LA TASA SEGUN EL NUMERO DE PRESTAMOS QUE TIENE EL CLIENTE
                            if (nPrestamos >= 10) {tasa = 13.0;} else if (nPrestamos <= 9 && nPrestamos >= 7) {tasa = 13.5;} else if (nPrestamos <= 6 && nPrestamos >= 4) {tasa = 14.0;}else {tasa = 14.5;}
                            double pagoMax = CLIENTE.getINGRESOS() - CLIENTE.getEGRESOS();//LO MÁS QUE PUEDE PAGAR
                            String[] nSemanas = {"20", "24"};//PLAZO EN SEMANAS
                            Object sems = JOptionPane.showInputDialog(this, "Seleccione a cuántas semanas desea el préstamo", "Seleccionar plazo", JOptionPane.QUESTION_MESSAGE, null, nSemanas, "0");
                            if (sems != null) {
                                int semanas = Integer.parseInt(sems.toString());//SEMANAS CONVERTIDAS A int (20 O 24)
                                int montoSolicitado = Integer.parseInt(cantidades.toString());//MONTO SOLICITADO (DE 1000 A 10000)                                
                                String tasaString = String.valueOf(tasa);//TASA EN STRING PARA UTILIZAR EL SWITCH NADA MAS
                                //SEGUN EL PLAZO SOLICITADO SE BUSCA Y GENERA LA TABLA DE AMORTIZACIÓN CORRESPONDIENTE A 20 O 24 SEMANAS
                                if (semanas == 20) {//TABLAS A 20 SEMANAS                                    
                                    switch (tasaString) {
                                        case "14.5":
                                            amr.tabla145_20(montoSolicitado, semanas);//14.5% A 20 SEMANAS
                                            break;
                                        case "14.0":
                                            amr.tabla140_20(montoSolicitado, semanas);//14.0% A 20 SEMANAS
                                            break;
                                        case "13.5":
                                            amr.tabla135_20(montoSolicitado, semanas);//13.5% A 20 SEMANAS
                                            break;
                                        case "13.0":
                                            amr.tabla130_20(montoSolicitado, semanas);//13.0% A 20 SEMANAS
                                            break;
                                        default:
                                            JOptionPane.showMessageDialog(this, "No se encuentra la tabla de amortización para los parámetros ingresados.\nLa operación ha sido cancelada", "¡AVISO!", JOptionPane.WARNING_MESSAGE);
                                            break;
                                    }
                                } else if (semanas == 24) {//TABLAS A 24 SEMANAS
                                    switch (tasaString) {
                                        case "14.5":
                                            amr.tabla145_24(montoSolicitado, semanas);//14.5% A 24 SEMANAS
                                            break;
                                        case "14.0":
                                            amr.tabla140_24(montoSolicitado, semanas);//14.0% A 24 SEMANAS
                                            break;
                                        case "13.5":
                                            amr.tabla135_24(montoSolicitado, semanas);//13.5% A 24 SEMANAS
                                            break;
                                        case "13.0":
                                            amr.tabla130_24(montoSolicitado, semanas);//13.0% A 24 SEMANAS
                                            break;
                                        default:
                                            JOptionPane.showMessageDialog(this, "No se encuentra la tabla de amortización para los parámetros ingresados.\nLa operación ha sido cancelada", "¡AVISO!", JOptionPane.WARNING_MESSAGE);
                                            break;
                                    }
                                }
                                //TENIENDO LA AMORTIZACIÓN BUSCAMOS SEGUN EL MONTO SOLICITADO, POR POLITICA A PARTIR DE $5000 SE SOLICITA EL RESPALDO DE UN AVAL
                                if (amr.getMONTO() >= 5000 && PERSONA_SELECCIONADA.getAval() <= 0) {
                                    JOptionPane.showMessageDialog(this, "La operación ha sido cancelada porque la persona seleccionada no cuenta con un aval asignado\ny el monto seleccionado es mayor o igual a $5000.00", "¡AVISO!", JOptionPane.WARNING_MESSAGE);
                                }else{
                                    Solicitud ultimaSolicitud = this.servicio.ultimaSolicitud(CLIENTE);                                    
                                    Solicitud solicitudNueva = new Solicitud(0, amr.getMONTO(), semanas, CLIENTE.getIdCliente(), this.USUARIO.getIdUsuario(), this.USUARIO.getIdSucursal(), tasa, "","");
                                    if(this.servicio.compararFechaSolicitud(ultimaSolicitud)){
                                        JOptionPane.showMessageDialog(this, "Este cliente ya cuenta con una solicitúd expedida durante este día. Intente de nuevo el día de mañana.", "¡AVISO!", JOptionPane.INFORMATION_MESSAGE);
                                    }else{
                                        boolean solIns = this.servicio.guardarSolicitud(solicitudNueva);
                                        if (solIns) {
                                            JOptionPane.showMessageDialog(this, "Solicitud guardada correctamente. Esté pendiente del resultado...");
                                        }else{
                                            JOptionPane.showMessageDialog(this, "No se guardó la solicitud");
                                        }
                                    }
                                    /**/
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "La operación ha sido cancelada", "¡AVISO!", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "La persona seleccionada todavía no cuenta con datos de cliente", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "La operación ha sido cancelada", "¡AVISO!", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La persona seleccionada no cuenta con una referencia válida asociada", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un monto válido", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No ha seleccionado a una persona", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //NO NECESITA CORREGIR
    private void opciones(boolean onOff) {
        btnReferencias.setEnabled(onOff);
        btnDomicilio.setEnabled(onOff);
        btnFicha.setEnabled(onOff);
        btnCrearCliente.setEnabled(onOff);
        opcion4.setEnabled(onOff);
        opcion5.setEnabled(onOff);
        opcion6.setEnabled(onOff);
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
        if (radioH.isSelected()) {
            sexo = "H";
        } else if (radioM.isSelected()) {
            sexo = "M";
        }
        int estadoCivil = cmbEstadoCivil.getSelectedIndex();
        String tel = txtTel.getText();
        String cel = txtCel.getText();
        if ((tel.length() > 0 && tel.length() < 10) || tel.length() == 0) {
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
    public void seleccionarPersona() {
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
                                buttonGroup1.setSelected(radioH.getModel(), true);
                            } else if ("M".equals(PERSONA_SELECCIONADA.getSexo())) {
                                buttonGroup1.setSelected(radioM.getModel(), true);
                            }
                            cmbEstadoCivil.setSelectedIndex(PERSONA_SELECCIONADA.getEstadoCivil());
                            txtTel.setText(PERSONA_SELECCIONADA.getTelefono());
                            txtCel.setText(PERSONA_SELECCIONADA.getCelular());
                            opciones(true);
                        }
                    } catch (Exception ex) {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        BarraSuperior = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        PanelBlanco = new javax.swing.JPanel();
        FormDatos = new javax.swing.JPanel();
        comboMeses = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtCel = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        radioM = new javax.swing.JRadioButton();
        txtAno = new javax.swing.JTextField();
        txtAp1 = new javax.swing.JTextField();
        comboEstadosNacimiento = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCurp = new javax.swing.JTextField();
        radioH = new javax.swing.JRadioButton();
        txtDia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbEstadoCivil = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txtTel = new javax.swing.JTextField();
        txtAp2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOcr = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        TablaContenedor = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtBuscar2 = new javax.swing.JTextField();
        btnBusqueda = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        panelOpciones = new javax.swing.JPanel();
        btnReferencias = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btnDomicilio = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnFicha = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btnCrearCliente = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        opcion4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        opcion5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        opcion6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setFont(new java.awt.Font("Solomon Sans Book", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(1360, 582));

        jPanel4.setBackground(new java.awt.Color(244, 0, 100));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BarraSuperior.setBackground(new java.awt.Color(189, 0, 53));
        BarraSuperior.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Solomon Sans Book", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Datos personales");
        BarraSuperior.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 0, -1, 47));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cerrar.png"))); // NOI18N
        jLabel11.setToolTipText("Cerrar");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        BarraSuperior.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1312, 0, -1, 47));

        jPanel4.add(BarraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, -1));

        PanelBlanco.setBackground(new java.awt.Color(255, 255, 255));
        PanelBlanco.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FormDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingrese los datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        comboMeses.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel17.setText("Apellidos:");

        txtCel.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelKeyTyped(evt);
            }
        });

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

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel1.setText("Teléfono:");

        jPanel2.setBackground(new java.awt.Color(244, 0, 100));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Guardar datos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup1.add(radioM);
        radioM.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        radioM.setText("Mujer");

        txtAno.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAnoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnoKeyTyped(evt);
            }
        });

        txtAp1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp1KeyTyped(evt);
            }
        });

        comboEstadosNacimiento.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel18.setText("Fecha nacimiento:");

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel3.setText("Sexo:");

        txtCurp.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtCurp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurpKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurpKeyTyped(evt);
            }
        });

        buttonGroup1.add(radioH);
        radioH.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        radioH.setText("Hombre");

        txtDia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiaKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel4.setText("Estado civíl:");

        cmbEstadoCivil.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        cmbEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Soltero", "Casado", "Divorciado", "Viudo" }));

        jLabel19.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel19.setText("OCR:");

        txtTel.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelKeyTyped(evt);
            }
        });

        txtAp2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAp2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAp2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAp2KeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel20.setText("Entidad de nacimiento:");

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel2.setText("Celular:");

        txtOcr.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtOcr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOcrKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOcrKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel16.setText("Nombre(s):");

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel21.setText("CURP:");

        jPanel7.setBackground(new java.awt.Color(204, 0, 0));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Cancelar");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(36, 36, 36))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout FormDatosLayout = new javax.swing.GroupLayout(FormDatos);
        FormDatos.setLayout(FormDatosLayout);
        FormDatosLayout.setHorizontalGroup(
            FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FormDatosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(FormDatosLayout.createSequentialGroup()
                        .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FormDatosLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(FormDatosLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(13, 13, 13)
                                .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(comboMeses, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FormDatosLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(14, 14, 14)
                                .addComponent(txtAp1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtAp2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FormDatosLayout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(comboEstadosNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(FormDatosLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbEstadoCivil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(26, 26, 26)
                        .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(FormDatosLayout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtOcr, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FormDatosLayout.createSequentialGroup()
                                .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FormDatosLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radioH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioM))))
                    .addGroup(FormDatosLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCurp, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(528, 528, 528)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        FormDatosLayout.setVerticalGroup(
            FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FormDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FormDatosLayout.createSequentialGroup()
                        .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(txtAp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboMeses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(comboEstadosNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FormDatosLayout.createSequentialGroup()
                        .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCurp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(txtOcr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(radioH)
                            .addComponent(radioM)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(cmbEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(FormDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        PanelBlanco.add(FormDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        TablaContenedor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscador de personas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

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

        javax.swing.GroupLayout TablaContenedorLayout = new javax.swing.GroupLayout(TablaContenedor);
        TablaContenedor.setLayout(TablaContenedorLayout);
        TablaContenedorLayout.setHorizontalGroup(
            TablaContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TablaContenedorLayout.createSequentialGroup()
                .addGroup(TablaContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TablaContenedorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3))
                    .addGroup(TablaContenedorLayout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        TablaContenedorLayout.setVerticalGroup(
            TablaContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TablaContenedorLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(TablaContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
        );

        PanelBlanco.add(TablaContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 168, 1133, -1));

        panelOpciones.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelOpciones.setMinimumSize(new java.awt.Dimension(270, 487));

        btnReferencias.setBackground(new java.awt.Color(255, 78, 0));
        btnReferencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReferenciasMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Agregar Referencias");

        javax.swing.GroupLayout btnReferenciasLayout = new javax.swing.GroupLayout(btnReferencias);
        btnReferencias.setLayout(btnReferenciasLayout);
        btnReferenciasLayout.setHorizontalGroup(
            btnReferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnReferenciasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );
        btnReferenciasLayout.setVerticalGroup(
            btnReferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnReferenciasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnDomicilio.setBackground(new java.awt.Color(255, 78, 0));
        btnDomicilio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDomicilioMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Agregar domicilio");

        javax.swing.GroupLayout btnDomicilioLayout = new javax.swing.GroupLayout(btnDomicilio);
        btnDomicilio.setLayout(btnDomicilioLayout);
        btnDomicilioLayout.setHorizontalGroup(
            btnDomicilioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDomicilioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        btnDomicilioLayout.setVerticalGroup(
            btnDomicilioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnDomicilioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addContainerGap())
        );

        btnFicha.setBackground(new java.awt.Color(255, 78, 0));
        btnFicha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFichaMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Ficha general");

        javax.swing.GroupLayout btnFichaLayout = new javax.swing.GroupLayout(btnFicha);
        btnFicha.setLayout(btnFichaLayout);
        btnFichaLayout.setHorizontalGroup(
            btnFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnFichaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(21, 21, 21))
        );
        btnFichaLayout.setVerticalGroup(
            btnFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnFichaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCrearCliente.setBackground(new java.awt.Color(255, 78, 0));
        btnCrearCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearCliente3MouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Crear cliente");

        javax.swing.GroupLayout btnCrearClienteLayout = new javax.swing.GroupLayout(btnCrearCliente);
        btnCrearCliente.setLayout(btnCrearClienteLayout);
        btnCrearClienteLayout.setHorizontalGroup(
            btnCrearClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCrearClienteLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnCrearClienteLayout.setVerticalGroup(
            btnCrearClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCrearClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        opcion4.setBackground(new java.awt.Color(255, 78, 0));
        opcion4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opcion4MouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Solicitar crédito");

        javax.swing.GroupLayout opcion4Layout = new javax.swing.GroupLayout(opcion4);
        opcion4.setLayout(opcion4Layout);
        opcion4Layout.setHorizontalGroup(
            opcion4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, opcion4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(23, 23, 23))
        );
        opcion4Layout.setVerticalGroup(
            opcion4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, opcion4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addContainerGap())
        );

        opcion5.setBackground(new java.awt.Color(255, 78, 0));
        opcion5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opcion5MouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Opción");

        javax.swing.GroupLayout opcion5Layout = new javax.swing.GroupLayout(opcion5);
        opcion5.setLayout(opcion5Layout);
        opcion5Layout.setHorizontalGroup(
            opcion5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, opcion5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(49, 49, 49))
        );
        opcion5Layout.setVerticalGroup(
            opcion5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opcion5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        opcion6.setBackground(new java.awt.Color(255, 78, 0));
        opcion6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opcion6MouseClicked(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Opción");

        javax.swing.GroupLayout opcion6Layout = new javax.swing.GroupLayout(opcion6);
        opcion6.setLayout(opcion6Layout);
        opcion6Layout.setHorizontalGroup(
            opcion6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, opcion6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addGap(50, 50, 50))
        );
        opcion6Layout.setVerticalGroup(
            opcion6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opcion6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelOpcionesLayout = new javax.swing.GroupLayout(panelOpciones);
        panelOpciones.setLayout(panelOpcionesLayout);
        panelOpcionesLayout.setHorizontalGroup(
            panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(opcion4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(opcion5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(opcion6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOpcionesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnFicha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDomicilio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCrearCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReferencias, javax.swing.GroupLayout.PREFERRED_SIZE, 154, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelOpcionesLayout.setVerticalGroup(
            panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcionesLayout.createSequentialGroup()
                .addComponent(btnCrearCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(btnReferencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFicha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(opcion4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(opcion5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(opcion6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelBlanco.add(panelOpciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(1149, 11, -1, -1));

        jPanel4.add(PanelBlanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 53, -1, -1));

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

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        guardarDatos();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        String cadena = txtNombre.getText().toUpperCase();
        txtNombre.setText(cadena);
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtAp1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp1KeyReleased
        String cadena = txtAp1.getText().toUpperCase();
        txtAp1.setText(cadena);
    }//GEN-LAST:event_txtAp1KeyReleased

    private void txtAp2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp2KeyReleased
        String cadena = txtAp2.getText().toUpperCase();
        txtAp2.setText(cadena);
    }//GEN-LAST:event_txtAp2KeyReleased

    private void txtDiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiaKeyTyped
        char c = evt.getKeyChar();
        int lon = txtDia.getText().length();
        if (!Character.isDigit(c) || lon >= 2) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDiaKeyTyped

    private void txtAnoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnoKeyTyped
        int lon = txtAno.getText().length();
        char c = evt.getKeyChar();
        if (lon >= 4 || !Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAnoKeyTyped

    private void txtAnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnoKeyReleased
    }//GEN-LAST:event_txtAnoKeyReleased

    private void txtOcrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcrKeyTyped
        int lon = txtOcr.getText().length();
        if (lon >= 13) {
            evt.consume();
//            JOptionPane.showMessageDialog(rootPane, "El código OCR contiene únicamente 13 caracteres");
        }
    }//GEN-LAST:event_txtOcrKeyTyped

    private void txtCurpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurpKeyTyped
        int lon = txtCurp.getText().length();
        if (lon >= 18) {
            evt.consume();
//            JOptionPane.showMessageDialog(rootPane, "El código CURP contiene únicamente 18 caracteres");
        }
    }//GEN-LAST:event_txtCurpKeyTyped

    private void txtCurpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurpKeyReleased
        String cadena = txtCurp.getText().toUpperCase();
        txtCurp.setText(cadena);
    }//GEN-LAST:event_txtCurpKeyReleased

    private void txtOcrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcrKeyReleased
        String cadena = txtOcr.getText().toUpperCase();
        txtOcr.setText(cadena);
    }//GEN-LAST:event_txtOcrKeyReleased

    private void txtCelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelKeyTyped
        int lon = txtCel.getText().length();
        if (lon >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCelKeyTyped

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

    private void btnReferenciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReferenciasMouseClicked
        if (PERSONA_SELECCIONADA != null) {
            try {
                asociar_referencias personas = new asociar_referencias(USUARIO.getIdSucursal(), "Asociar referencias de " + PERSONA_SELECCIONADA.toString(), PERSONA_SELECCIONADA);
                escritorio.add(personas);
                Dimension FrameSize = escritorio.getSize();
//                System.out.println(FrameSize.width +" " + FrameSize.height);
                personas.setSize(FrameSize);
                personas.toFront();
                personas.show();
            } catch (SQLException ex) {
                Logger.getLogger(agregar_personas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No hay ninguna persona seleccionada actualmente.");
        }
    }//GEN-LAST:event_btnReferenciasMouseClicked

    private void btnDomicilioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDomicilioMouseClicked
        if (PERSONA_SELECCIONADA != null) {
            asociar_direcciones direcciones = new asociar_direcciones(USUARIO, "Asociar domicilio a " + PERSONA_SELECCIONADA.toString(), PERSONA_SELECCIONADA);
            escritorio.add(direcciones);
            Dimension FrameSize = escritorio.getSize();
            direcciones.setSize(FrameSize);
            direcciones.toFront();
            direcciones.show();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(rootPane, "No hay ninguna persona seleccionada actualmente.");
        }
    }//GEN-LAST:event_btnDomicilioMouseClicked

    private void btnFichaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFichaMouseClicked
        if (PERSONA_SELECCIONADA != null) {
            ficha_personal fp = new ficha_personal(this, USUARIO, PERSONA_SELECCIONADA);
            escritorio.add(fp);
            Dimension FrameSize = escritorio.getSize();
            fp.setSize(FrameSize);
            fp.toFront();
            fp.show();
            this.toBack();
            this.hide();
        } else {
            JOptionPane.showMessageDialog(rootPane, "No hay ninguna persona seleccionada actualmente.");
        }
    }//GEN-LAST:event_btnFichaMouseClicked

    private void btnCrearCliente3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearCliente3MouseClicked
        if (PERSONA_SELECCIONADA != null) {
            clientes2 clientes = new clientes2(this, USUARIO, PERSONA_SELECCIONADA);
            escritorio.add(clientes);
            Dimension FrameSize = escritorio.getSize();
//            System.out.println(FrameSize.height/4 + " x " + FrameSize.width /8);
            clientes.setLocation(FrameSize.height / 4, FrameSize.width / 8);
            clientes.toFront();
            clientes.show();
            this.toBack();
            this.hide();
        } else {
            JOptionPane.showMessageDialog(rootPane, "No hay ninguna persona seleccionada actualmente.");
        }
    }//GEN-LAST:event_btnCrearCliente3MouseClicked

    private void opcion4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcion4MouseClicked
        solicitarPrestamo();
    }//GEN-LAST:event_opcion4MouseClicked

    private void opcion5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcion5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_opcion5MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        limpiarCampos();
        PERSONA_SELECCIONADA = null;
        ID_PERSONA_SELECCIONADA = 0;
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void txtAp1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp1KeyTyped
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAp1KeyTyped

    private void txtAp2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAp2KeyTyped
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAp2KeyTyped

    private void txtTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelKeyTyped
        int lon = txtTel.getText().length();
        if (lon >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTelKeyTyped

    private void opcion6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcion6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_opcion6MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarraSuperior;
    private javax.swing.JPanel FormDatos;
    private javax.swing.JPanel PanelBlanco;
    private javax.swing.JPanel TablaContenedor;
    private javax.swing.JPanel btnBusqueda;
    private javax.swing.JPanel btnCrearCliente;
    private javax.swing.JPanel btnDomicilio;
    private javax.swing.JPanel btnFicha;
    private javax.swing.JPanel btnReferencias;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbEstadoCivil;
    private javax.swing.JComboBox<String> comboEstadosNacimiento;
    private javax.swing.JComboBox<String> comboMeses;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel opcion4;
    private javax.swing.JPanel opcion5;
    private javax.swing.JPanel opcion6;
    private javax.swing.JPanel panelOpciones;
    private javax.swing.JRadioButton radioH;
    private javax.swing.JRadioButton radioM;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtAp1;
    private javax.swing.JTextField txtAp2;
    private javax.swing.JTextField txtBuscar2;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtCurp;
    private javax.swing.JTextField txtDia;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtOcr;
    private javax.swing.JTextField txtTel;
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
