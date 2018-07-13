package views;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
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
        lblNombre.setText(this.PERSONA.toString());
        String sexo = null;
        if ("H".equals(this.PERSONA.getSexo())) {
            sexo = "MASCULINO";
        } else if ("M".equals(this.PERSONA.getSexo())) {
            sexo = "FEMENINO";
        }
        lblSexo.setText(sexo);
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
        lblEdoCivil.setText(estadoCivil);
        lblEntidad.setText(this.SERVICIO.estado(this.PERSONA.getEntidadNac()));
        lblFecha.setText(this.PERSONA.getF_nac());
        lblCurp.setText(this.PERSONA.getCurp());
        lblOcr.setText(this.PERSONA.getOcr());
        String dom;
        if (this.PERSONA.getDomicilio() > 0) {
            dom = this.SERVICIO.domicilio(this.PERSONA.getDomicilio()).getDIRECCION();
        } else {
            dom = "NO ASIGNADO";
        }
        lblDireccion.setText(dom);
        lblCelular.setText(this.PERSONA.getCelular());
        lblTelefono.setText(this.PERSONA.getTelefono());
        this.setPhoto();
        datosReferencia();
        datosAval();
        datosConyuge();
    }

    private void setPhoto() {
        try {
            //String url = new CloudinaryImages().getImageUrl("mifoto");
            //String url = "https://res.cloudinary.com/grupoavante/image/upload/v1531512157/personas_avante/100x100.jpg";
            String url = "https://res.cloudinary.com/grupoavante/image/upload/v1531512157/personas_avante/100x100.jpg";
            if (url != null) {
                URLConnection connection = new URL(url).openConnection();
                connection.setDoInput(true);
                connection.setRequestProperty("User-Agent", "xxxxxx");
                BufferedImage img = ImageIO.read(connection.getInputStream());
                System.out.println(connection.getInputStream());
                ImageIcon icon = new ImageIcon(img);
                System.out.println(img);
                lblPhoto.setIcon(icon);
            }
        } catch (IOException ex) {
            Logger.getLogger(Ficha.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        jPanel5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblEntidad = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        lblCurp = new javax.swing.JLabel();
        lblOcr = new javax.swing.JLabel();
        lblEdoCivil = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
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
        jPanel2 = new javax.swing.JPanel();
        lblTelefono1 = new javax.swing.JLabel();
        lblPhoto = new javax.swing.JLabel();

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

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel18.setText("FECHA NACIMIENTO:");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 76, 120, -1));

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel3.setText("SEXO:");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 120, -1));

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel4.setText("ESTADO CIVIL:");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 52, 100, -1));

        jLabel19.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel19.setText("OCR:");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 50, -1));

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel20.setText("ENTIDAD NACIMIENTO:");
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 76, -1, -1));

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel16.setText("NOMBRE:");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 28, 120, -1));

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel21.setText("CURP:");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 120, -1));

        lblNombre.setBackground(new java.awt.Color(255, 255, 255));
        lblNombre.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel5.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 28, 580, 13));

        lblFecha.setBackground(new java.awt.Color(255, 255, 255));
        lblFecha.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel5.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 76, 320, 13));

        lblEntidad.setBackground(new java.awt.Color(255, 255, 255));
        lblEntidad.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel5.add(lblEntidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(593, 76, 135, 13));

        lblSexo.setBackground(new java.awt.Color(255, 255, 255));
        lblSexo.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel5.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 52, 210, 13));

        lblCurp.setBackground(new java.awt.Color(255, 255, 255));
        lblCurp.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel5.add(lblCurp, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 210, 13));

        lblOcr.setBackground(new java.awt.Color(255, 255, 255));
        lblOcr.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel5.add(lblOcr, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 208, 13));

        lblEdoCivil.setBackground(new java.awt.Color(255, 255, 255));
        lblEdoCivil.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel5.add(lblEdoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 52, 158, 13));

        jLabel22.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel22.setText("DIRECCIÓN:");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 124, 120, -1));

        lblDireccion.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccion.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel5.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 124, 590, 13));

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel1.setText("TELÉFONO:");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 148, 120, -1));

        lblTelefono.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefono.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel5.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 148, 210, 13));

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel2.setText("CELULAR:");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 148, 66, -1));

        lblCelular.setBackground(new java.awt.Color(255, 255, 255));
        lblCelular.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel5.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(523, 148, 205, 13));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 11, 750, 170));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Referencias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(244, 0, 100))); // NOI18N
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel7.add(panelReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 17, 700, 90));

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

        jPanel7.add(panelAval, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 116, 700, 90));

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

        jPanel7.add(panelConyuge, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 215, 700, 90));

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 750, 320));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos adicionales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(255, 78, 0))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTelefono1.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefono1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel2.add(lblTelefono1, new org.netbeans.lib.awtextra.AbsoluteConstraints(77, 28, 227, 13));

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, 320, 510));

        lblPhoto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.add(lblPhoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, 100));

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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblCelularAval;
    private javax.swing.JLabel lblCelularCon;
    private javax.swing.JLabel lblCelularRef;
    private javax.swing.JLabel lblCurp;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDireccionAval;
    private javax.swing.JLabel lblDireccionCon;
    private javax.swing.JLabel lblDireccionRef;
    private javax.swing.JLabel lblEdoCivil;
    private javax.swing.JLabel lblEntidad;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreAval;
    private javax.swing.JLabel lblNombreCon;
    private javax.swing.JLabel lblNombreRef;
    private javax.swing.JLabel lblOcr;
    private javax.swing.JLabel lblPhoto;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTelefono1;
    private javax.swing.JLabel lblTelefonoAval;
    private javax.swing.JLabel lblTelefonoCon;
    private javax.swing.JLabel lblTelefonoRef;
    private javax.swing.JPanel panelAval;
    private javax.swing.JPanel panelConyuge;
    private javax.swing.JPanel panelReferencia;
    private javax.swing.JLabel tituloVentana;
    // End of variables declaration//GEN-END:variables
}
