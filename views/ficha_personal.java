package views;

import objects.Domicilio;
import objects.Persona;
import objects.Usuario;
import services.fichaPersonal_service;

/**
 *
 * @author JMalagon
 */
public final class ficha_personal extends javax.swing.JInternalFrame {

    private final fichaPersonal_service SERVICIO;
//    private int ID_PERSONA_SELECCIONADA = 0;
    private Persona PERSONA = null;
    private Usuario USUARIO = null;
    private agregar_personas AP = null;

    public ficha_personal(agregar_personas ap, Usuario usuario, Persona persona) {
        initComponents();
        this.SERVICIO = new fichaPersonal_service();
        this.USUARIO = usuario;
        this.PERSONA = persona;
        this.AP = ap;
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
            dom = this.SERVICIO.domicilio(this.PERSONA.getDomicilio()).toString();
        } else {
            dom = "NO ASIGNADO";
        }
        lblDireccion.setText(dom);
        lblCelular.setText(this.PERSONA.getCelular());
        lblTelefono.setText(this.PERSONA.getTelefono());
        datosReferencia();
        datosAval();
        datosConyuge();
    }

    private void datosReferencia() {
        System.err.println("Ref: " + this.PERSONA.getReferencia());
        Persona persona = SERVICIO.persona(USUARIO.getIdSucursal(), this.PERSONA.getReferencia());
        System.err.println(persona);
        if (persona == null) {
            persona = new Persona(0, "N/D", "N/D", "N/D", "N/D", 0, "N/D", "N/D", "N/D", 0, "N/D", "N/D", 0, 0, 0, 0);
        }
        lblNombreRef.setText(persona.toString()); 
        System.out.println("Dom: " + persona.getDomicilio());
        Domicilio domicilio = SERVICIO.domicilio(persona.getDomicilio());        
        if (domicilio == null) {
            lblDireccionRef.setText("NO ASIGNADO");
        }else{
            lblDireccionRef.setText(domicilio.toString());
        }        
        lblCelularRef.setText(persona.getCelular());
        lblTelefonoRef.setText(persona.getTelefono());
    }

    private void datosAval() {
        Persona persona = SERVICIO.persona(USUARIO.getIdSucursal(), this.PERSONA.getAval());
        if (persona == null) {
            persona = new Persona(0, "N/D", "N/D", "N/D", "N/D", 0, "N/D", "N/D", "N/D", 0, "N/D", "N/D", 0, 0, 0, 0);
        }
        lblNombreAval.setText(persona.toString());
        Domicilio domicilio = SERVICIO.domicilio(persona.getDomicilio());        
        if (domicilio == null) {
            lblDireccionAval.setText("NO ASIGNADO");
        }else{
            lblDireccionAval.setText(domicilio.toString());
        }
        lblCelularAval.setText(persona.getCelular());
        lblTelefonoAval.setText(persona.getTelefono());
    }

    private void datosConyuge() {
        Persona persona = SERVICIO.persona(USUARIO.getIdSucursal(), this.PERSONA.getConyuge());
        if (persona == null) {
            persona = new Persona(0, "N/D", "N/D", "N/D", "N/D", 0, "N/D", "N/D", "N/D", 0, "N/D", "N/D", 0, 0, 0, 0);
        }
        lblNombreCon.setText(persona.toString());
        Domicilio domicilio = SERVICIO.domicilio(persona.getDomicilio());        
        if (domicilio == null) {
            lblDireccionCon.setText("NO ASIGNADO");
        }else{
            lblDireccionCon.setText(domicilio.toString());
        }
        lblCelularCon.setText(persona.getCelular());
        lblTelefonoCon.setText(persona.getTelefono());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
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
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        lblTelefono1 = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setFont(new java.awt.Font("Solomon Sans Book", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(1360, 582));

        jPanel4.setBackground(new java.awt.Color(244, 0, 100));

        jPanel1.setBackground(new java.awt.Color(189, 0, 53));

        jLabel6.setFont(new java.awt.Font("Solomon Sans Book", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ficha general");

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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(189, 0, 53))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel18.setText("FECHA NACIMIENTO:");

        jLabel3.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel3.setText("SEXO:");

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel4.setText("ESTADO CIVIL:");

        jLabel19.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel19.setText("OCR:");

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel20.setText("ENTIDAD NACIMIENTO:");

        jLabel16.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel16.setText("NOMBRE:");

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel21.setText("CURP:");

        lblNombre.setBackground(new java.awt.Color(255, 255, 255));
        lblNombre.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N

        lblFecha.setBackground(new java.awt.Color(255, 255, 255));
        lblFecha.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N

        lblEntidad.setBackground(new java.awt.Color(255, 255, 255));
        lblEntidad.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N

        lblSexo.setBackground(new java.awt.Color(255, 255, 255));
        lblSexo.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N

        lblCurp.setBackground(new java.awt.Color(255, 255, 255));
        lblCurp.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N

        lblOcr.setBackground(new java.awt.Color(255, 255, 255));
        lblOcr.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N

        lblEdoCivil.setBackground(new java.awt.Color(255, 255, 255));
        lblEdoCivil.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel22.setText("DIRECCIÓN:");

        lblDireccion.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccion.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel1.setText("TELÉFONO:");

        lblTelefono.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefono.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel2.setText("CELULAR:");

        lblCelular.setBackground(new java.awt.Color(255, 255, 255));
        lblCelular.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCelular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCurp, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(91, 91, 91))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(168, 168, 168)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblEdoCivil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblOcr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel22});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblCurp, lblFecha, lblSexo});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel18, jLabel20});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)
                        .addComponent(lblEdoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3)
                    .addComponent(lblSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addComponent(lblEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(lblCurp, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel19)
                        .addComponent(lblOcr, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(lblCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblCelular, lblDireccion, lblTelefono});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblCurp, lblEdoCivil, lblEntidad, lblFecha, lblNombre, lblOcr, lblSexo});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel16, jLabel18, jLabel19, jLabel20, jLabel21, jLabel3, jLabel4});

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 11, -1, -1));

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

        jPanel7.add(panelReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 17, 720, 90));

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

        jPanel7.add(panelAval, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 116, 720, 90));

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

        jPanel7.add(panelConyuge, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 215, 720, 90));

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 195, 754, 325));

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setMaximumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos adicionales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14), new java.awt.Color(255, 78, 0))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel14.setText("TELÉFONO:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 28, -1, -1));

        lblTelefono1.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefono1.setFont(new java.awt.Font("Solomon Sans Book", 1, 12)); // NOI18N
        jPanel2.add(lblTelefono1, new org.netbeans.lib.awtextra.AbsoluteConstraints(77, 28, 227, 13));

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, 320, 510));

        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1226, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
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

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this.AP.show();
        this.AP.toFront();
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
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
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTelefono1;
    private javax.swing.JLabel lblTelefonoAval;
    private javax.swing.JLabel lblTelefonoCon;
    private javax.swing.JLabel lblTelefonoRef;
    private javax.swing.JPanel panelAval;
    private javax.swing.JPanel panelConyuge;
    private javax.swing.JPanel panelReferencia;
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
