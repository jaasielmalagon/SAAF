package views;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
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
 * @author Root
 */
public class Cronograma extends javax.swing.JDialog {

    private final agregarPersona_service servicio;
    private int ID_PERSONA_SELECCIONADA = 0;
    private Persona PERSONA_SELECCIONADA = null;
    private Usuario USUARIO = null;
    private Cliente CLIENTE = null;

    public Cronograma(java.awt.Frame parent, boolean modal, Usuario usuario) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        
        this.servicio = new agregarPersona_service();
//<<<<<<< HEAD
        /*
        this.meses();
=======
        /*this.meses();
>>>>>>> 3f2c4059ccaeffdfc10a76cb8c1e7d4a232c93c8
        this.estadosNacimiento();
        this.USUARIO = usuario;
        System.out.println(this.USUARIO.toString());
        llenarTabla();
        seleccionarPersona();
        opciones(false);
//<<<<<<< HEAD
        */    
//=======
     //   */
//>>>>>>> 3f2c4059ccaeffdfc10a76cb8c1e7d4a232c93c8
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
                            if (nPrestamos >= 10) {
                                tasa = 13.0;
                            } else if (nPrestamos <= 9 && nPrestamos >= 7) {
                                tasa = 13.5;
                            } else if (nPrestamos <= 6 && nPrestamos >= 4) {
                                tasa = 14.0;
                            } else {
                                tasa = 14.5;
                            }
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
                                } else {
                                    Solicitud ultimaSolicitud = this.servicio.ultimaSolicitud(CLIENTE);
                                    Solicitud solicitudNueva = new Solicitud(0, amr.getMONTO(), semanas, CLIENTE.getIdCliente(), this.USUARIO.getIdUsuario(), this.USUARIO.getIdSucursal(), tasa, "", "");
                                    if (this.servicio.compararFechaSolicitud(ultimaSolicitud)) {
                                        JOptionPane.showMessageDialog(this, "Este cliente ya cuenta con una solicitúd expedida durante este día. Intente de nuevo el día de mañana.", "¡AVISO!", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        boolean solIns = this.servicio.guardarSolicitud(solicitudNueva);
                                        if (solIns) {
                                            JOptionPane.showMessageDialog(this, "Solicitud guardada correctamente. Esté pendiente del resultado...");
                                        } else {
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
        /*
        btnReferencias.setEnabled(onOff);
        btnDomicilio.setEnabled(onOff);
        btnFicha.setEnabled(onOff);
        btnCrearCliente.setEnabled(onOff);
        btnCredito.setEnabled(onOff);
        */
    }

    //CORREGIDO
    private void llenarTabla() {
        tablaClientes = this.servicio.tablaPersonas(tablaClientes, this.USUARIO.getIdSucursal());
    }

    //CORREGIDO
    private void guardarDatos() {
//<<<<<<< HEAD
        /*
        Estado estado_nac = (Estado) comboEstadosNacimiento.getSelectedItem();
=======
       /* Estado estado_nac = (Estado) comboEstadosNacimiento.getSelectedItem();
>>>>>>> 3f2c4059ccaeffdfc10a76cb8c1e7d4a232c93c8
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
        */
    }

    //NO NECESITA CORREGIR
    private void limpiarCampos() {
//<<<<<<< HEAD
        /*
        comboMeses.setSelectedIndex(0);
=======
       /* comboMeses.setSelectedIndex(0);
>>>>>>> 3f2c4059ccaeffdfc10a76cb8c1e7d4a232c93c8
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
<<<<<<< HEAD
        txtCel.setText("");
        */
/*=======
        txtCel.setText(""); */
//>>>>>>> 3f2c4059ccaeffdfc10a76cb8c1e7d4a232c93c8
    }

    //NO NECESITA CORREGIR
    private void meses() {
        /*
        Fecha f = new Fecha();
        Mes[] meses = f.meses();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Mes mes : meses) {
            dcbm.addElement(mes);
        }
        comboMeses.setModel(dcbm);
        */
    }

    //CORREGIDO
    private void estadosNacimiento() {
//<<<<<<< HEAD
     /*      comboEstadosNacimiento.setModel(this.servicio.estados()); */
//=======
        //comboEstadosNacimiento.setModel(this.servicio.estados());
//>>>>>>> 3f2c4059ccaeffdfc10a76cb8c1e7d4a232c93c8
    }

    //CORREGIDO
    public void buscar() {
//<<<<<<< HEAD
        /*
        if (!txtBuscar2.getText().isEmpty()) {
            tablaClientes = this.servicio.tablaPersonas(tablaClientes, USUARIO.getIdSucursal(), txtBuscar2.getText());
        } else {
            tablaClientes = this.servicio.tablaPersonas(tablaClientes, this.USUARIO.getIdSucursal());
        }
        */
//=======
      /*  if (!txtBuscar2.getText().isEmpty()) {
            tablaClientes = this.servicio.tablaPersonas(tablaClientes, USUARIO.getIdSucursal(), txtBuscar2.getText());
        } else {
            tablaClientes = this.servicio.tablaPersonas(tablaClientes, this.USUARIO.getIdSucursal());
        }*/
//>>>>>>> 3f2c4059ccaeffdfc10a76cb8c1e7d4a232c93c8
    }

    //CORREGIDO
    public void seleccionarPersona() {
//<<<<<<< HEAD
        /*tablaClientes.addMouseListener(new MouseAdapter() {
=======
       /* tablaClientes.addMouseListener(new MouseAdapter() {
>>>>>>> 3f2c4059ccaeffdfc10a76cb8c1e7d4a232c93c8
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
                            opciones(true);
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println(".mousePressed() : " + ex);
                    }
                }
            }
<<<<<<< HEAD
        });
        */
    }

    public void setSelectedEstado(String value) {
       /* Object item;
=======
        }); */
    }

//    public void setSelectedEstado(String value) {
      /*  Object item;
>>>>>>> 3f2c4059ccaeffdfc10a76cb8c1e7d4a232c93c8
        Estado estado;
        for (int i = 0; i < comboEstadosNacimiento.getItemCount(); i++) {
            item = comboEstadosNacimiento.getItemAt(i);
            estado = (Estado) item;
            if (estado.getEstado().equalsIgnoreCase(value)) {
                comboEstadosNacimiento.setSelectedIndex(i);
                break;
            }
        }
        */
  //  }

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
        txtNombre = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        panelTabla = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        btnGuardar1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

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
        tituloVentana.setText("Cronograma");
        BarraSuperior.add(tituloVentana, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 350, 85));

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
        panelFormulario.setPreferredSize(new java.awt.Dimension(600, 240));
        panelFormulario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        panelFormulario.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(426, 83, 260, -1));

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel16.setText("Descripción");
        panelFormulario.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(467, 55, -1, -1));

        btnCancelar.setBackground(new java.awt.Color(204, 0, 0));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });
        btnCancelar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Cancelar");
        btnCancelar.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 9, -1, 30));

        panelFormulario.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(571, 160, 100, 50));

        btnGuardar.setBackground(new java.awt.Color(244, 0, 100));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });
        btnGuardar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Guardar datos");
        btnGuardar.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 9, 110, 30));

        panelFormulario.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(426, 160, -1, 50));
        panelFormulario.add(jCalendar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 370, 160));

        Contenedor.add(panelFormulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 700, 240));

        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Actividades del día", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelTabla.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        panelTabla.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 80, 1128, 123));

        btnGuardar1.setBackground(new java.awt.Color(244, 0, 100));
        btnGuardar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardar1MouseClicked(evt);
            }
        });
        btnGuardar1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Eliminar");
        btnGuardar1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 0, 80, 30));

        panelTabla.add(btnGuardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(536, 40, 90, -1));

        Contenedor.add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 265, 1160, 260));

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

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        limpiarCampos();
        PERSONA_SELECCIONADA = null;
        ID_PERSONA_SELECCIONADA = 0;
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        guardarDatos();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardar1MouseClicked

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
            java.util.logging.Logger.getLogger(Cronograma.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            Usuario usuario = null;
            Cronograma dialog = new Cronograma(new javax.swing.JFrame(), true, usuario);
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
    private javax.swing.JPanel btnGuardar1;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JLabel tituloVentana;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
