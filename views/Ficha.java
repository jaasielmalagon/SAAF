package views;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import objects.Cliente;
import objects.CloudinaryImages;
import objects.Persona;
import objects.Usuario;
import services.Ficha_service;

/**
 *
 * @author JMalagon
 */
public class Ficha extends javax.swing.JDialog {

    private final Ficha_service SERVICIO;
    private Persona PERSONA = null;
    private Usuario USUARIO = null;

    public Ficha(JDialog parent, boolean modal, Usuario usuario, Persona persona, String modulo) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        this.SERVICIO = new Ficha_service(modulo);
        this.USUARIO = usuario;
        this.PERSONA = persona;
        tituloVentana.setText(tituloVentana.getText() + " " + this.PERSONA.toString());
        ponerDatos();
    }

    private void ponerDatos() {
        lblNombre1.setText(this.PERSONA.toString());
        String sexo = null;
        if ("H".equals(this.PERSONA.getSexo())) {
            sexo = "MASCULINO";
        } else if ("M".equals(this.PERSONA.getSexo())) {
            sexo = "FEMENINO";
        }
        lblSexo1.setText(sexo);
        String estadoCivil = null;
        switch (this.PERSONA.getEstadoCivil()) {
            case 1:
                estadoCivil = "CASADO";
                break;
            case 2:
                estadoCivil = "DIVORCIADO";
                break;
            case 3:
                estadoCivil = "VIUDO";
                break;
            default:
                estadoCivil = "SOLTERO";
                break;
        }
        lblEdoCivil1.setText(estadoCivil);
        lblEntidad1.setText(this.SERVICIO.estado(this.PERSONA.getEntidadNac()));
        lblFecha1.setText(this.PERSONA.getF_nac());
        lblCurp1.setText(this.PERSONA.getCurp());
        lblOcr1.setText(this.PERSONA.getOcr());
        String dom;
        if (this.PERSONA.getDomicilio() > 0) {
            dom = this.SERVICIO.domicilio(this.PERSONA.getDomicilio()).getDIRECCION();
        } else {
            dom = "NO ASIGNADO";
        }
        lblDireccion1.setText(dom);
        lblCelular1.setText(this.PERSONA.getCelular());
        lblTelefono1.setText(this.PERSONA.getTelefono());
        this.setPhoto();
        if (this.PERSONA.getReferencia() == 0 && this.PERSONA.getAval() == 0 && this.PERSONA.getConyuge() == 0) {
            panelReferencias.setVisible(false);
            jPanel3.add(panelDatosEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 750, 170));
        } else {
            datosReferencia();
            datosAval();
            datosConyuge();
        }
        datosCliente();
    }

    private void setPhoto() {
        ImageIcon icon = new CloudinaryImages().getImage("user");
        if (icon == null) {
            try {
                File file = new File("E:\\Documents\\NetBeansProjects\\AvanteGit\\src\\image\\avatar.png");
                BufferedImage img = ImageIO.read(file);
                icon = new ImageIcon(img);
            } catch (IOException ex) {
                System.out.println("views.Ficha.setPhoto() : " + ex);
            }
        }
        lblPhoto.setIcon(icon);
    }

    private void datosReferencia() {
        Persona persona = this.SERVICIO.persona(this.USUARIO.getIdSucursal(), this.PERSONA.getReferencia());
        if (persona == null) {
            persona = new Persona(0, "N/D", "N/D", "N/D", "N/D", 0, "N/D", "N/D", "N/D", 0, "N/D", "N/D", 0, 0, 0, 0);
        }
        lblNombreRef.setText(persona.toString());
        objects.Domicilio domicilio = this.SERVICIO.domicilio(persona.getDomicilio());
        if (domicilio == null) {
            lblDireccionRef.setText("NO ASIGNADO");
        } else {
            lblDireccionRef.setText(domicilio.toString());
        }
        lblCelularRef.setText(persona.getCelular());
        lblTelefonoRef.setText(persona.getTelefono());
    }

    private void datosAval() {
        Persona persona = this.SERVICIO.persona(this.USUARIO.getIdSucursal(), this.PERSONA.getAval());
        if (persona == null) {
            persona = new Persona(0, "N/D", "N/D", "N/D", "N/D", 0, "N/D", "N/D", "N/D", 0, "N/D", "N/D", 0, 0, 0, 0);
        }
        lblNombreAval.setText(persona.toString());
        objects.Domicilio domicilio = this.SERVICIO.domicilio(persona.getDomicilio());
        if (domicilio == null) {
            lblDireccionAval.setText("NO ASIGNADO");
        } else {
            lblDireccionAval.setText(domicilio.toString());
        }
        lblCelularAval.setText(persona.getCelular());
        lblTelefonoAval.setText(persona.getTelefono());
    }

    private void datosConyuge() {
        Persona persona = this.SERVICIO.persona(this.USUARIO.getIdSucursal(), this.PERSONA.getConyuge());
        if (persona == null) {
            persona = new Persona(0, "N/D", "N/D", "N/D", "N/D", 0, "N/D", "N/D", "N/D", 0, "N/D", "N/D", 0, 0, 0, 0);
        }
        lblNombreCon.setText(persona.toString());
        objects.Domicilio domicilio = this.SERVICIO.domicilio(persona.getDomicilio());
        if (domicilio == null) {
            lblDireccionCon.setText("NO ASIGNADO");
        } else {
            lblDireccionCon.setText(domicilio.toString());
        }
        lblCelularCon.setText(persona.getCelular());
        lblTelefonoCon.setText(persona.getTelefono());
    }

    private void datosCliente() {
        Cliente cliente = this.SERVICIO.cliente(this.PERSONA.getIdPersona());
        if (cliente != null) {
            panelDatosCliente.setVisible(true);
            lblAdc1.setText(String.valueOf(cliente.getADC()));
            lblIngresos1.setText(String.valueOf(cliente.getINGRESOS()));
            lblEgresos1.setText(String.valueOf(cliente.getEGRESOS()));
            lblOcupacion1.setText(cliente.getOCUPACION_());
            lblDependientes1.setText(String.valueOf(cliente.getDEPENDIENTES()));
            lblEstudios1.setText(cliente.getESTUDIOS_());
            lblEmpleo1.setText(cliente.getEMPRESA());
            lblEntrada1.setText(cliente.getHORA_ENTRADA());
            lblSalida1.setText(cliente.getHORA_SALIDA());
            lblDireccionEmpleo1.setText(cliente.getDOMICILIO_EMPRESA());
            lblTelEmpleo1.setText(cliente.getTEL_EMPRESA());
            lblTipoVivienda1.setText(String.valueOf(cliente.getTIPO_VIVIENDA()));
            lblVigenciaVivienda1.setText(cliente.getVIGENCIA());
            lblAnosResidencia1.setText(String.valueOf(cliente.getTIEMPO_RESIDENCIA()));
            lblScore1.setText(String.valueOf(cliente.getSCORE()));
            lblStatus1.setText(String.valueOf(cliente.getSTATUS()));
            lblActividad1.setText(String.valueOf(cliente.getACTIVIDAD()));
            lblFechaIngreso.setText(cliente.getF_REGISTRO());
        } else {
            panelDatosCliente.setVisible(false);
            //jPanel3.add(panelDatosCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, 310, 440));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        BarraSuperior = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        tituloVentana = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        lblPhoto = new javax.swing.JLabel();
        panelDatosEmpleado = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblCargo = new javax.swing.JLabel();
        lblFechaIngreso = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblDepartamento = new javax.swing.JLabel();
        lblCurp = new javax.swing.JLabel();
        lblNivelEstudios = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblContacto = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblEntradaTrabajador = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblSalidaTrabajador = new javax.swing.JLabel();
        panelReferencias = new javax.swing.JPanel();
        panelReferencia = new javax.swing.JPanel();
        lblCelularRef = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblTelefonoRef = new javax.swing.JLabel();
        lblNombreRef = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblDireccionRef = new javax.swing.JLabel();
        panelAval = new javax.swing.JPanel();
        lblCelularAval = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        lblTelefonoAval = new javax.swing.JLabel();
        lblNombreAval = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblDireccionAval = new javax.swing.JLabel();
        panelConyuge = new javax.swing.JPanel();
        lblCelularCon = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        lblTelefonoCon = new javax.swing.JLabel();
        lblNombreCon = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblDireccionCon = new javax.swing.JLabel();
        panelDatosCliente = new javax.swing.JPanel();
        lblTelefono1 = new javax.swing.JLabel();
        lblAdc = new javax.swing.JLabel();
        lblEgresos = new javax.swing.JLabel();
        lblIngresos = new javax.swing.JLabel();
        lblDependientes = new javax.swing.JLabel();
        lblOcupacion = new javax.swing.JLabel();
        lblEstudios = new javax.swing.JLabel();
        lblEmpleo = new javax.swing.JLabel();
        lblSalida = new javax.swing.JLabel();
        lblEntrada = new javax.swing.JLabel();
        lblDireccionEmpleo = new javax.swing.JLabel();
        lblTelEmpleo = new javax.swing.JLabel();
        lblTipoVivienda = new javax.swing.JLabel();
        lblVigenciaVivienda = new javax.swing.JLabel();
        lblAnosResidencia = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblScore = new javax.swing.JLabel();
        lblActividad = new javax.swing.JLabel();
        lblRegistro = new javax.swing.JLabel();
        lblAdc1 = new javax.swing.JLabel();
        lblIngresos1 = new javax.swing.JLabel();
        lblOcupacion1 = new javax.swing.JLabel();
        lblEstudios1 = new javax.swing.JLabel();
        lblEmpleo1 = new javax.swing.JLabel();
        lblEntrada1 = new javax.swing.JLabel();
        lblDireccionEmpleo1 = new javax.swing.JLabel();
        lblTelEmpleo1 = new javax.swing.JLabel();
        lblTipoVivienda1 = new javax.swing.JLabel();
        lblVigenciaVivienda1 = new javax.swing.JLabel();
        lblAnosResidencia1 = new javax.swing.JLabel();
        lblScore1 = new javax.swing.JLabel();
        lblActividad1 = new javax.swing.JLabel();
        lblRegistro1 = new javax.swing.JLabel();
        lblEgresos1 = new javax.swing.JLabel();
        lblDependientes1 = new javax.swing.JLabel();
        lblSalida1 = new javax.swing.JLabel();
        lblStatus1 = new javax.swing.JLabel();
        panelDatosPersonales = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lblNombre1 = new javax.swing.JLabel();
        lblFecha1 = new javax.swing.JLabel();
        lblEntidad1 = new javax.swing.JLabel();
        lblSexo1 = new javax.swing.JLabel();
        lblCurp1 = new javax.swing.JLabel();
        lblOcr1 = new javax.swing.JLabel();
        lblEdoCivil1 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lblDireccion1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblTelefono2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblCelular1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setUndecorated(true);
        setResizable(false);

        jPanel4.setBackground(new java.awt.Color(244, 0, 100));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tituloVentana.setText("Ficha general de: ");
        BarraSuperior.add(tituloVentana, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 860, 85));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cerrar.png"))); // NOI18N
        jLabel11.setToolTipText("Cerrar");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        BarraSuperior.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1312, 0, -1, 47));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/menu-logo.png"))); // NOI18N
        BarraSuperior.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(895, 0, 304, 85));

        jPanel4.add(BarraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 85));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPhoto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.add(lblPhoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 100));

        panelDatosEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosEmpleado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Colaborador", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panelDatosEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel18.setText("FECHA INGRESO:");
        panelDatosEmpleado.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 76, 120, -1));

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel3.setText("DEPARTAMENTO:");
        panelDatosEmpleado.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 120, -1));

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel4.setText("NIVEL DE ESTUDIOS:");
        panelDatosEmpleado.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 52, 120, -1));

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel20.setText("CÓDIGO:");
        panelDatosEmpleado.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 76, -1, -1));

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel16.setText("CARGO:");
        panelDatosEmpleado.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 28, 120, -1));

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel21.setText("DÍAS LABORALES:");
        panelDatosEmpleado.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 120, -1));

        lblCargo.setBackground(new java.awt.Color(255, 255, 255));
        lblCargo.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosEmpleado.add(lblCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 28, 580, 13));

        lblFechaIngreso.setBackground(new java.awt.Color(255, 255, 255));
        lblFechaIngreso.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosEmpleado.add(lblFechaIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 76, 320, 13));

        lblCodigo.setBackground(new java.awt.Color(255, 255, 255));
        lblCodigo.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosEmpleado.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(593, 76, 135, 13));

        lblDepartamento.setBackground(new java.awt.Color(255, 255, 255));
        lblDepartamento.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosEmpleado.add(lblDepartamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 52, 210, 13));

        lblCurp.setBackground(new java.awt.Color(255, 255, 255));
        lblCurp.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosEmpleado.add(lblCurp, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 600, 13));

        lblNivelEstudios.setBackground(new java.awt.Color(255, 255, 255));
        lblNivelEstudios.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosEmpleado.add(lblNivelEstudios, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 52, 158, 13));

        jLabel22.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel22.setText("CONTACTO DE EMERGENCIA:");
        panelDatosEmpleado.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 124, 170, -1));

        lblContacto.setBackground(new java.awt.Color(255, 255, 255));
        lblContacto.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosEmpleado.add(lblContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 124, 560, 13));

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel1.setText("ENTRADA:");
        panelDatosEmpleado.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 148, 120, -1));

        lblEntradaTrabajador.setBackground(new java.awt.Color(255, 255, 255));
        lblEntradaTrabajador.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosEmpleado.add(lblEntradaTrabajador, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 148, 210, 13));

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel2.setText("SALIDA:");
        panelDatosEmpleado.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 148, 66, -1));

        lblSalidaTrabajador.setBackground(new java.awt.Color(255, 255, 255));
        lblSalidaTrabajador.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosEmpleado.add(lblSalidaTrabajador, new org.netbeans.lib.awtextra.AbsoluteConstraints(523, 148, 205, 13));

        jPanel3.add(panelDatosEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 750, 170));
        panelDatosEmpleado.getAccessibleContext().setAccessibleName("Datos Colaborador");

        panelReferencias.setBackground(new java.awt.Color(255, 255, 255));
        panelReferencias.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Referencias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(244, 0, 100))); // NOI18N
        panelReferencias.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelReferencia.setBackground(new java.awt.Color(255, 255, 255));
        panelReferencia.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Referencia: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 0, 11))); // NOI18N
        panelReferencia.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCelularRef.setBackground(new java.awt.Color(255, 255, 255));
        lblCelularRef.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelReferencia.add(lblCelularRef, new org.netbeans.lib.awtextra.AbsoluteConstraints(494, 63, 210, 13));

        jLabel24.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel24.setText("DIRECCIÓN:");
        panelReferencia.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 44, -1, -1));

        jLabel17.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel17.setText("NOMBRE: ");
        panelReferencia.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 25, 75, -1));

        lblTelefonoRef.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefonoRef.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelReferencia.add(lblTelefonoRef, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 63, 210, 13));

        lblNombreRef.setBackground(new java.awt.Color(255, 255, 255));
        lblNombreRef.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelReferencia.add(lblNombreRef, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 25, 607, 13));

        jLabel5.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel5.setText("TELÉFONO:");
        panelReferencia.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 63, -1, -1));

        jLabel7.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel7.setText("CELULAR:");
        panelReferencia.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 63, 66, -1));

        lblDireccionRef.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccionRef.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelReferencia.add(lblDireccionRef, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 44, 607, 13));

        panelReferencias.add(panelReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 17, 700, 90));

        panelAval.setBackground(new java.awt.Color(255, 255, 255));
        panelAval.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Aval: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 0, 11))); // NOI18N
        panelAval.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCelularAval.setBackground(new java.awt.Color(255, 255, 255));
        lblCelularAval.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelAval.add(lblCelularAval, new org.netbeans.lib.awtextra.AbsoluteConstraints(496, 63, 210, 13));

        jLabel31.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel31.setText("DIRECCIÓN:");
        panelAval.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 44, -1, -1));

        jLabel32.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel32.setText("NOMBRE: ");
        panelAval.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 25, 75, -1));

        lblTelefonoAval.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefonoAval.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelAval.add(lblTelefonoAval, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 63, 210, 13));

        lblNombreAval.setBackground(new java.awt.Color(255, 255, 255));
        lblNombreAval.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelAval.add(lblNombreAval, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 25, 609, 13));

        jLabel8.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel8.setText("TELÉFONO:");
        panelAval.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 63, -1, -1));

        jLabel9.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel9.setText("CELULAR:");
        panelAval.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(424, 63, 66, -1));

        lblDireccionAval.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccionAval.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelAval.add(lblDireccionAval, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 44, 609, 13));

        panelReferencias.add(panelAval, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 116, 700, 90));

        panelConyuge.setBackground(new java.awt.Color(255, 255, 255));
        panelConyuge.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cónyuge: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 0, 11))); // NOI18N
        panelConyuge.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCelularCon.setBackground(new java.awt.Color(255, 255, 255));
        lblCelularCon.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelConyuge.add(lblCelularCon, new org.netbeans.lib.awtextra.AbsoluteConstraints(496, 63, 210, 13));

        jLabel36.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel36.setText("DIRECCIÓN:");
        panelConyuge.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 44, -1, -1));

        jLabel37.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel37.setText("NOMBRE: ");
        panelConyuge.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 25, 75, -1));

        lblTelefonoCon.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefonoCon.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelConyuge.add(lblTelefonoCon, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 63, 210, 13));

        lblNombreCon.setBackground(new java.awt.Color(255, 255, 255));
        lblNombreCon.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelConyuge.add(lblNombreCon, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 25, 609, 13));

        jLabel10.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel10.setText("TELÉFONO:");
        panelConyuge.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 63, -1, -1));

        jLabel12.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel12.setText("CELULAR:");
        panelConyuge.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(424, 63, 66, -1));

        lblDireccionCon.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccionCon.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelConyuge.add(lblDireccionCon, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 44, 609, 13));

        panelReferencias.add(panelConyuge, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 215, 700, 90));

        jPanel3.add(panelReferencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 750, 320));

        panelDatosCliente.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(255, 78, 0))); // NOI18N
        panelDatosCliente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTelefono1.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefono1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosCliente.add(lblTelefono1, new org.netbeans.lib.awtextra.AbsoluteConstraints(77, 28, 227, 13));

        lblAdc.setText("ADC:");
        panelDatosCliente.add(lblAdc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 15));

        lblEgresos.setText("EGRESOS:");
        panelDatosCliente.add(lblEgresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, -1, 15));

        lblIngresos.setText("INGRESOS:");
        panelDatosCliente.add(lblIngresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 15));

        lblDependientes.setText("DEPENDIENTES:");
        panelDatosCliente.add(lblDependientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, -1, 15));

        lblOcupacion.setText("OCUPACIÓN:");
        panelDatosCliente.add(lblOcupacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 15));

        lblEstudios.setText("ESTUDIOS:");
        panelDatosCliente.add(lblEstudios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, 15));

        lblEmpleo.setText("EMPLEO:");
        panelDatosCliente.add(lblEmpleo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 15));

        lblSalida.setText("SALIDA:");
        panelDatosCliente.add(lblSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, -1, 15));

        lblEntrada.setText("ENTRADA:");
        panelDatosCliente.add(lblEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 15));

        lblDireccionEmpleo.setText("DIR. EMPLEO:");
        panelDatosCliente.add(lblDireccionEmpleo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, 15));

        lblTelEmpleo.setText("TEL. EMPLEO:");
        panelDatosCliente.add(lblTelEmpleo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, 15));

        lblTipoVivienda.setText("TIPO DE VIVIENDA:");
        panelDatosCliente.add(lblTipoVivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, 15));

        lblVigenciaVivienda.setText("VIGENCIA DE VIVIENDA:");
        panelDatosCliente.add(lblVigenciaVivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, 15));

        lblAnosResidencia.setText("AÑOS DE RESIDENCIA:");
        panelDatosCliente.add(lblAnosResidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, 15));

        lblStatus.setText("STATUS:");
        panelDatosCliente.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, -1, 15));

        lblScore.setText("SCORE:");
        panelDatosCliente.add(lblScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, -1, 15));

        lblActividad.setText("ESTADO DE ACTIVIDAD:");
        panelDatosCliente.add(lblActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, 15));

        lblRegistro.setText("FECHA DE REGISTRO:");
        panelDatosCliente.add(lblRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, -1, 15));
        panelDatosCliente.add(lblAdc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 80, 15));
        panelDatosCliente.add(lblIngresos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 90, 15));
        panelDatosCliente.add(lblOcupacion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 90, 15));
        panelDatosCliente.add(lblEstudios1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 200, 15));
        panelDatosCliente.add(lblEmpleo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 240, 15));
        panelDatosCliente.add(lblEntrada1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 90, 15));
        panelDatosCliente.add(lblDireccionEmpleo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 220, 15));
        panelDatosCliente.add(lblTelEmpleo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 160, 15));
        panelDatosCliente.add(lblTipoVivienda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 130, 15));
        panelDatosCliente.add(lblVigenciaVivienda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 170, 15));
        panelDatosCliente.add(lblAnosResidencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 170, 15));
        panelDatosCliente.add(lblScore1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 60, 15));
        panelDatosCliente.add(lblActividad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(138, 380, 120, 15));
        panelDatosCliente.add(lblRegistro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 410, 180, 15));
        panelDatosCliente.add(lblEgresos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 50, 15));
        panelDatosCliente.add(lblDependientes1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, 30, 15));
        panelDatosCliente.add(lblSalida1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, 80, 15));
        panelDatosCliente.add(lblStatus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 80, 15));

        jPanel3.add(panelDatosCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, 310, 440));

        panelDatosPersonales.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        panelDatosPersonales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel25.setText("FECHA NACIMIENTO:");
        panelDatosPersonales.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 76, 120, -1));

        jLabel6.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel6.setText("SEXO:");
        panelDatosPersonales.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 120, -1));

        jLabel13.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel13.setText("ESTADO CIVIL:");
        panelDatosPersonales.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 52, 100, -1));

        jLabel26.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel26.setText("OCR:");
        panelDatosPersonales.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 50, -1));

        jLabel27.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel27.setText("ENTIDAD NACIMIENTO:");
        panelDatosPersonales.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 76, -1, -1));

        jLabel28.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel28.setText("NOMBRE:");
        panelDatosPersonales.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 28, 120, -1));

        jLabel29.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel29.setText("CURP:");
        panelDatosPersonales.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 120, -1));

        lblNombre1.setBackground(new java.awt.Color(255, 255, 255));
        lblNombre1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosPersonales.add(lblNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 28, 580, 13));

        lblFecha1.setBackground(new java.awt.Color(255, 255, 255));
        lblFecha1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosPersonales.add(lblFecha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 76, 320, 13));

        lblEntidad1.setBackground(new java.awt.Color(255, 255, 255));
        lblEntidad1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosPersonales.add(lblEntidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(593, 76, 135, 13));

        lblSexo1.setBackground(new java.awt.Color(255, 255, 255));
        lblSexo1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosPersonales.add(lblSexo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 52, 210, 13));

        lblCurp1.setBackground(new java.awt.Color(255, 255, 255));
        lblCurp1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosPersonales.add(lblCurp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 210, 13));

        lblOcr1.setBackground(new java.awt.Color(255, 255, 255));
        lblOcr1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosPersonales.add(lblOcr1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 208, 13));

        lblEdoCivil1.setBackground(new java.awt.Color(255, 255, 255));
        lblEdoCivil1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosPersonales.add(lblEdoCivil1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 52, 158, 13));

        jLabel30.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel30.setText("DIRECCIÓN:");
        panelDatosPersonales.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 124, 120, -1));

        lblDireccion1.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccion1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosPersonales.add(lblDireccion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 124, 590, 13));

        jLabel14.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel14.setText("TELÉFONO:");
        panelDatosPersonales.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 148, 120, -1));

        lblTelefono2.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefono2.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosPersonales.add(lblTelefono2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 148, 210, 13));

        jLabel15.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel15.setText("CELULAR:");
        panelDatosPersonales.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 148, 66, -1));

        lblCelular1.setBackground(new java.awt.Color(255, 255, 255));
        lblCelular1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        panelDatosPersonales.add(lblCelular1, new org.netbeans.lib.awtextra.AbsoluteConstraints(523, 148, 205, 13));

        jPanel3.add(panelDatosPersonales, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 11, 750, 170));

        jScrollPane1.setViewportView(jPanel3);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 91, 1200, 529));

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

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ficha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            Ficha dialog = new Ficha(new javax.swing.JDialog(), true, null, null, null);
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
    private javax.swing.JButton btnCerrar;
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
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblActividad;
    private javax.swing.JLabel lblActividad1;
    private javax.swing.JLabel lblAdc;
    private javax.swing.JLabel lblAdc1;
    private javax.swing.JLabel lblAnosResidencia;
    private javax.swing.JLabel lblAnosResidencia1;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblCelular1;
    private javax.swing.JLabel lblCelularAval;
    private javax.swing.JLabel lblCelularCon;
    private javax.swing.JLabel lblCelularRef;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblContacto;
    private javax.swing.JLabel lblCurp;
    private javax.swing.JLabel lblCurp1;
    private javax.swing.JLabel lblDepartamento;
    private javax.swing.JLabel lblDependientes;
    private javax.swing.JLabel lblDependientes1;
    private javax.swing.JLabel lblDireccion1;
    private javax.swing.JLabel lblDireccionAval;
    private javax.swing.JLabel lblDireccionCon;
    private javax.swing.JLabel lblDireccionEmpleo;
    private javax.swing.JLabel lblDireccionEmpleo1;
    private javax.swing.JLabel lblDireccionRef;
    private javax.swing.JLabel lblEdoCivil1;
    private javax.swing.JLabel lblEgresos;
    private javax.swing.JLabel lblEgresos1;
    private javax.swing.JLabel lblEmpleo;
    private javax.swing.JLabel lblEmpleo1;
    private javax.swing.JLabel lblEntidad1;
    private javax.swing.JLabel lblEntrada;
    private javax.swing.JLabel lblEntrada1;
    private javax.swing.JLabel lblEntradaTrabajador;
    private javax.swing.JLabel lblEstudios;
    private javax.swing.JLabel lblEstudios1;
    private javax.swing.JLabel lblFecha1;
    private javax.swing.JLabel lblFechaIngreso;
    private javax.swing.JLabel lblIngresos;
    private javax.swing.JLabel lblIngresos1;
    private javax.swing.JLabel lblNivelEstudios;
    private javax.swing.JLabel lblNombre1;
    private javax.swing.JLabel lblNombreAval;
    private javax.swing.JLabel lblNombreCon;
    private javax.swing.JLabel lblNombreRef;
    private javax.swing.JLabel lblOcr1;
    private javax.swing.JLabel lblOcupacion;
    private javax.swing.JLabel lblOcupacion1;
    private javax.swing.JLabel lblPhoto;
    private javax.swing.JLabel lblRegistro;
    private javax.swing.JLabel lblRegistro1;
    private javax.swing.JLabel lblSalida;
    private javax.swing.JLabel lblSalida1;
    private javax.swing.JLabel lblSalidaTrabajador;
    private javax.swing.JLabel lblScore;
    private javax.swing.JLabel lblScore1;
    private javax.swing.JLabel lblSexo1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatus1;
    private javax.swing.JLabel lblTelEmpleo;
    private javax.swing.JLabel lblTelEmpleo1;
    private javax.swing.JLabel lblTelefono1;
    private javax.swing.JLabel lblTelefono2;
    private javax.swing.JLabel lblTelefonoAval;
    private javax.swing.JLabel lblTelefonoCon;
    private javax.swing.JLabel lblTelefonoRef;
    private javax.swing.JLabel lblTipoVivienda;
    private javax.swing.JLabel lblTipoVivienda1;
    private javax.swing.JLabel lblVigenciaVivienda;
    private javax.swing.JLabel lblVigenciaVivienda1;
    private javax.swing.JPanel panelAval;
    private javax.swing.JPanel panelConyuge;
    private javax.swing.JPanel panelDatosCliente;
    private javax.swing.JPanel panelDatosEmpleado;
    private javax.swing.JPanel panelDatosPersonales;
    private javax.swing.JPanel panelReferencia;
    private javax.swing.JPanel panelReferencias;
    private javax.swing.JLabel tituloVentana;
    // End of variables declaration//GEN-END:variables
}
