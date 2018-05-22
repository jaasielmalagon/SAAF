package views;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import objects.Calle;
import objects.Colonia;
import objects.Domicilio;
import objects.Mes;
import objects.Municipio;
import objects.Numerosdomiciliares;
import objects.Usuario;
import services.agregarDirecciones_service;

/**
 *
 * @author JMalagon
 */
public final class agregar_direcciones extends javax.swing.JInternalFrame {

    private final agregarDirecciones_service SERVICIO;
    private int DOMICILIO_SELECCIONADO = 0;
    private Domicilio DOMICILIO = null;
    private Usuario USUARIO = null;

    public agregar_direcciones(Usuario usuario) {
        initComponents();
        this.setTitle("Domicilios");
        lblTitulo.setText(title);
        this.SERVICIO = new agregarDirecciones_service();
        this.USUARIO = usuario;
//        this.SUCURSAL = usuario.getIdSucursal();
        municipios();
        meses();
        llenarTabla();
        seleccionarDeTabla();
        frmArrendamientoOnOff(false);
        buscadorOnOff(false);
    }

    private void llenarTabla() {
        tabla.setModel(this.SERVICIO.tablaDirecciones(this.USUARIO.getIdSucursal()));
        TableColumnModel columnModel = tabla.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(900);
        tabla.setColumnModel(columnModel);
    }

    private void guardarDatos() {
        if (comboCalle.getSelectedItem() == null || comboCalle1.getSelectedItem() == null || comboCalle2.getSelectedItem() == null
                || comboNumero.getSelectedItem() == null || comboColonias.getSelectedItem() == null || comboTipoVivienda.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "No ha seleccionado todos los campos requeridos. \nPor favor verifique la información ingresada.", "¡Aviso!", JOptionPane.ERROR_MESSAGE);
        } else {
            int tipo = comboTipoVivienda.getSelectedIndex();
            String vigencia = txtAno.getText() + "-" + ((Mes) comboMeses.getSelectedItem()).getNumeroMes() + "-" + txtDia.getText();
            String propietario = txtPropietario.getText();
            int tiempoResidencia = Integer.parseInt(txtAnosResidencia.getText());
            int numero = ((Numerosdomiciliares) comboNumero.getSelectedItem()).getIdNumero();
            int calle = ((Calle) comboCalle.getSelectedItem()).getIdCalle();
            int calle1 = ((Calle) comboCalle1.getSelectedItem()).getIdCalle();
            int calle2 = ((Calle) comboCalle2.getSelectedItem()).getIdCalle();
            int colonia = ((Colonia) comboColonias.getSelectedItem()).getIdColonia();
            int encontrado = this.SERVICIO.buscarDireccion(numero);
            if (DOMICILIO == null) {
                DOMICILIO = new Domicilio(0, 0, 0, 0, 0, 0, 0, "", "", 0, null);
            }
            System.err.println("Encontrado: " + encontrado + " / Seleccionado: " + DOMICILIO.getId());
            if (encontrado > 0 && encontrado == DOMICILIO.getId()) {
                int resp = JOptionPane.showConfirmDialog(rootPane, "¿Desea actualizar los datos de este domicilio?", "¡Atención!", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(rootPane, "Datos actualizados correctamente", "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    llenarTabla();
                }
            } else if (encontrado > 0 && encontrado != DOMICILIO.getId()) {
                JOptionPane.showMessageDialog(rootPane, "El domicilio que intenta agregar ya se encuentra registrado.", "¡Aviso!", JOptionPane.ERROR_MESSAGE);
            } else {
                int gd = this.SERVICIO.guardarDatos(this.USUARIO.getIdSucursal(), calle, numero, calle1, calle2, colonia, tipo, propietario, vigencia, tiempoResidencia);
                switch (gd) {
                    case 0:
                        JOptionPane.showMessageDialog(rootPane, "Domicilio registrado correctamente", "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                        limpiarCampos();
                        llenarTabla();
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(rootPane, "La fecha es menor o igual a la actual", "¡Error!", JOptionPane.ERROR_MESSAGE);
                        txtDia.setRequestFocusEnabled(true);
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(rootPane, "El nombre del propietario está vacío.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                        txtPropietario.setRequestFocusEnabled(true);
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(rootPane, "El tiempo de residencia es inválido.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                        txtAnosResidencia.setRequestFocusEnabled(true);
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(rootPane, "No se pudieron guardar los datos correctamente.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                        break;
                    default:
                        break;
                }
            }
        }

    }

    private void limpiarCampos() {
        this.DOMICILIO = null;
        comboMunicipios.setSelectedIndex(0);
        comboColonias.removeAllItems();
        comboCalle.removeAllItems();
        comboCalle1.removeAllItems();
        comboCalle2.removeAllItems();
        comboMeses.setSelectedIndex(0);
        comboTipoVivienda.setSelectedIndex(0);
        frmArrendamientoOnOff(false);
    }

    private void meses() {
        Mes[] meses = this.SERVICIO.meses();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Mes mes : meses) {
            dcbm.addElement(mes);
        }
        comboMeses.setModel(dcbm);
    }

    public void buscar() {
        Colonia colonia = (Colonia) comboColonias1.getSelectedItem();
        Calle calle = (Calle) comboCalleBuscador.getSelectedItem();
        Numerosdomiciliares numero = (Numerosdomiciliares) comboNumero1.getSelectedItem();
        DefaultTableModel modeloTabla;
        if (colonia != null && calle == null && numero == null) {
            modeloTabla = this.SERVICIO.tablaDireccionesBuscar(this.USUARIO.getIdSucursal(), colonia.getIdColonia(), 0, 0);
        } else if (colonia != null && calle != null && numero == null) {
            modeloTabla = this.SERVICIO.tablaDireccionesBuscar(this.USUARIO.getIdSucursal(), colonia.getIdColonia(), calle.getIdCalle(), 0);
        } else if (colonia != null && calle != null && numero != null) {
            modeloTabla = this.SERVICIO.tablaDireccionesBuscar(this.USUARIO.getIdSucursal(), colonia.getIdColonia(), calle.getIdCalle(), numero.getIdNumero());
        } else {
            modeloTabla = this.SERVICIO.tablaDirecciones(this.USUARIO.getIdSucursal());
        }
        tabla.setModel(modeloTabla);
        TableColumnModel columnModel = tabla.getColumnModel();
        if (columnModel.getColumnCount() > 0) {
            columnModel.getColumn(0).setPreferredWidth(30);
            columnModel.getColumn(1).setPreferredWidth(900);
        }
        tabla.setColumnModel(columnModel);
    }

    public void seleccionarDeTabla() {
        tabla.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                if (Mouse_evt.getClickCount() == 1) {
                    DOMICILIO_SELECCIONADO = Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0).toString());
                    DOMICILIO = SERVICIO.domicilio(DOMICILIO_SELECCIONADO);
                    //System.out.println(Arrays.toString(DOMICILIO.getDireccionArray()));
                    if (DOMICILIO != null) {
                        setSelectedMunicipio(DOMICILIO.getDireccionArray()[5]);
                        setSelectedColonia(DOMICILIO.getColonia());
                        setSelectedCalle(DOMICILIO.getCalle());
                        setSelectedCalle1(DOMICILIO.getCalle1());
                        setSelectedCalle2(DOMICILIO.getCalle2());
                        setSelectedNumero(DOMICILIO.getNumero());
                        comboTipoVivienda.setSelectedIndex(DOMICILIO.getTipo());
                        txtAnosResidencia.setText(String.valueOf(DOMICILIO.getTiempoResidencia()));
                        txtPropietario.setText(DOMICILIO.getPropietario());
                    }
                }
            }
        });
    }

    public void setSelectedNumero(int value) {
        Object item;
        Numerosdomiciliares nd;
        for (int i = 0; i < comboNumero.getItemCount(); i++) {
            item = comboNumero.getItemAt(i);
            nd = (Numerosdomiciliares) item;
            if (nd.getIdNumero() == value) {
                comboNumero.setSelectedIndex(i);
                break;
            }
        }
    }

    public void setSelectedCalle2(int value) {
        Object item;
        Calle calle;
        for (int i = 0; i < comboCalle2.getItemCount(); i++) {
            item = comboCalle2.getItemAt(i);
            calle = (Calle) item;
            if (calle.getIdCalle() == value) {
                comboCalle2.setSelectedIndex(i);
                break;
            }
        }
    }

    public void setSelectedCalle1(int value) {
        Object item;
        Calle calle;
        for (int i = 0; i < comboCalle1.getItemCount(); i++) {
            item = comboCalle1.getItemAt(i);
            calle = (Calle) item;
            if (calle.getIdCalle() == value) {
                comboCalle1.setSelectedIndex(i);
                break;
            }
        }
    }

    public void setSelectedCalle(int value) {
        Object item;
        Calle calle;
        for (int i = 0; i < comboCalle.getItemCount(); i++) {
            item = comboCalle.getItemAt(i);
            calle = (Calle) item;
            if (calle.getIdCalle() == value) {
                comboCalle.setSelectedIndex(i);
                break;
            }
        }
    }

    public void setSelectedColonia(int value) {
        Object item;
        Colonia colonia;
        for (int i = 0; i < comboColonias.getItemCount(); i++) {
            item = comboColonias.getItemAt(i);
            colonia = (Colonia) item;
            if (colonia.getIdColonia() == value) {
                comboColonias.setSelectedIndex(i);
                break;
            }
        }
    }

    public void setSelectedMunicipio(String value) {
        Object item;
        Municipio municipio;
        for (int i = 0; i < comboMunicipios.getItemCount(); i++) {
            item = comboMunicipios.getItemAt(i);
            municipio = (Municipio) item;
            if (municipio.getNombreMunicipio().equalsIgnoreCase(value)) {
                comboMunicipios.setSelectedIndex(i);
                break;
            }
        }
    }

    private void municipios() {
        comboMunicipios.removeAllItems();
        DefaultComboBoxModel dcbm = this.SERVICIO.municipiosDeSucursal(this.USUARIO.getIdSucursal());
        DefaultComboBoxModel dcbm1 = this.SERVICIO.municipiosDeSucursal(this.USUARIO.getIdSucursal());
        comboMunicipios.setModel(dcbm);
        comboMunicipios1.setModel(dcbm1);
    }

    private void colonias() {
        comboColonias.removeAllItems();
        Municipio municipio = (Municipio) comboMunicipios.getSelectedItem();
        int idMunicipio = municipio.getIdMunicipio();
        DefaultComboBoxModel dcbm = this.SERVICIO.cmbColonias(idMunicipio);
        comboColonias.setModel(dcbm);
    }

    private void colonias1() {
        comboColonias1.removeAllItems();
        Municipio municipio = (Municipio) comboMunicipios1.getSelectedItem();
        int idMunicipio = municipio.getIdMunicipio();
        DefaultComboBoxModel dcbm = this.SERVICIO.cmbColonias(idMunicipio);
        comboColonias1.setModel(dcbm);
    }

    private void calles() {
        Colonia colonia = (Colonia) comboColonias.getSelectedItem();
        if (colonia != null) {
            int idColonia = colonia.getIdColonia();
            Calle[] calles = this.SERVICIO.calles(idColonia);
            comboCalle.removeAllItems();
            comboCalle1.removeAllItems();
            comboCalle2.removeAllItems();
            if (calles != null) {
                DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
                DefaultComboBoxModel dcbm1 = new DefaultComboBoxModel();
                DefaultComboBoxModel dcbm2 = new DefaultComboBoxModel();
                for (Calle calle : calles) {
                    dcbm.addElement(calle);
                    dcbm1.addElement(calle);
                    dcbm2.addElement(calle);
                }
                comboCalle.setModel(dcbm);
                comboCalle1.setModel(dcbm1);
                comboCalle2.setModel(dcbm2);
            }
        }
    }

    private void calles1() {
        Colonia colonia = (Colonia) comboColonias1.getSelectedItem();
        if (colonia != null) {
            int idColonia = colonia.getIdColonia();
            Calle[] calles = this.SERVICIO.calles(idColonia);
            comboCalleBuscador.removeAllItems();
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            for (Calle calle : calles) {
                dcbm.addElement(calle);
            }
            comboCalleBuscador.setModel(dcbm);
        }
    }

    private void numeros() {
        comboNumero.removeAllItems();
        Calle calle = (Calle) comboCalle.getSelectedItem();
        if (calle != null) {
            int idCalle = calle.getIdCalle();
            Numerosdomiciliares[] nds = this.SERVICIO.numerosDeCalle(idCalle);
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            if (nds != null) {
                for (Numerosdomiciliares nd : nds) {
                    dcbm.addElement(nd);
                }
            }
            comboNumero.setModel(dcbm);
        }
    }

    private void numeros1() {
        comboNumero1.removeAllItems();
        Calle calle = (Calle) comboCalleBuscador.getSelectedItem();
        if (calle != null) {
            int idCalle = calle.getIdCalle();
            Numerosdomiciliares[] nds = this.SERVICIO.numerosDeCalle(idCalle);
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            if (nds != null) {
                for (Numerosdomiciliares nd : nds) {
                    dcbm.addElement(nd);
                }
            }
            comboNumero1.setModel(dcbm);
        }
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

    private void buscadorOnOff(boolean estado) {
        lblMun.setEnabled(estado);
        lblCol.setEnabled(estado);
        lblCal.setEnabled(estado);
        lblNum.setEnabled(estado);
        comboMunicipios1.setEnabled(estado);
        comboColonias1.setEnabled(estado);
        comboCalleBuscador.setEnabled(estado);
        comboNumero1.setEnabled(estado);
        comboMunicipios1.setSelectedIndex(0);
        comboColonias1.removeAllItems();
        comboCalleBuscador.removeAllItems();
        comboNumero1.removeAllItems();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        comboMeses = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtAno = new javax.swing.JTextField();
        comboColonias = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        txtPropietario = new javax.swing.JTextField();
        txtDia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        comboTipoVivienda = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        comboCalle = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        comboCalle1 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        comboCalle2 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        comboNumero = new javax.swing.JComboBox<>();
        txtAnosResidencia = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        comboMunicipios = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        panelOpciones2 = new javax.swing.JPanel();
        lblMun = new javax.swing.JLabel();
        comboMunicipios1 = new javax.swing.JComboBox<>();
        lblCol = new javax.swing.JLabel();
        comboColonias1 = new javax.swing.JComboBox<>();
        lblCal = new javax.swing.JLabel();
        comboCalleBuscador = new javax.swing.JComboBox<>();
        lblNum = new javax.swing.JLabel();
        comboNumero1 = new javax.swing.JComboBox<>();
        toggleBuscador = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setFont(new java.awt.Font("Solomon Sans Book", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(1360, 582));

        jPanel4.setBackground(new java.awt.Color(244, 0, 100));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingrese los datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        comboMeses.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

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
                .addGap(51, 51, 51))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtAno.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAnoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnoKeyTyped(evt);
            }
        });

        comboColonias.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboColonias.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboColoniasItemStateChanged(evt);
            }
        });
        comboColonias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboColoniasActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel18.setText("Vigencia de renta");

        txtPropietario.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtPropietario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPropietarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPropietarioKeyTyped(evt);
            }
        });

        txtDia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiaKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel4.setText("Tipo de vivienda:");

        comboTipoVivienda.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboTipoVivienda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-----", "Propia", "Rentada", "Prestada" }));
        comboTipoVivienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoViviendaActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel20.setText("Colonia:");

        jLabel21.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel21.setText("Propietario");

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
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel24.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel24.setText("Calle:");

        comboCalle.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboCalle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboCalleItemStateChanged(evt);
            }
        });
        comboCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCalleActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel25.setText("Entre calle:");

        comboCalle1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel26.setText("y calle:");

        comboCalle2.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel27.setText("Número oficial:");

        comboNumero.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNumeroActionPerformed(evt);
            }
        });

        txtAnosResidencia.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        txtAnosResidencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnosResidenciaKeyTyped(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel28.setText("Tiempo de residencia (años):");

        jLabel22.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        jLabel22.setText("Municipios:");

        comboMunicipios.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboMunicipios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMunicipiosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel21)
                            .addGap(0, 0, 0)
                            .addComponent(txtPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 741, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel27)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel25)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboCalle1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboCalle2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel22)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboMunicipios, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel20)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboColonias, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel24)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboTipoVivienda, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(comboMeses, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(txtAnosResidencia)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel27, jLabel4});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel22)
                            .addComponent(comboMunicipios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(comboColonias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel27)
                            .addComponent(comboNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(comboCalle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(comboCalle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel4)
                            .addComponent(comboTipoVivienda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboMeses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(txtAnosResidencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel21)
                            .addComponent(txtPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {comboCalle, comboCalle1, comboCalle2, comboColonias, comboMeses, comboMunicipios, comboNumero, comboTipoVivienda, jLabel18, jLabel20, jLabel21, jLabel22, jLabel24, jLabel25, jLabel26, jLabel27, jLabel28, jLabel4, txtAno, txtAnosResidencia, txtDia, txtPropietario});

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Domicilios disponibles", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N

        tabla.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tabla);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelOpciones2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscador de domicilios", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Solomon Sans Book", 1, 14))); // NOI18N
        panelOpciones2.setMinimumSize(new java.awt.Dimension(270, 487));

        lblMun.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        lblMun.setText("Municipio:");

        comboMunicipios1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboMunicipios1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMunicipios1ActionPerformed(evt);
            }
        });

        lblCol.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        lblCol.setText("Colonia:");

        comboColonias1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboColonias1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboColonias1ItemStateChanged(evt);
            }
        });
        comboColonias1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboColonias1ActionPerformed(evt);
            }
        });

        lblCal.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        lblCal.setText("Calle:");

        comboCalleBuscador.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboCalleBuscador.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboCalleBuscadorItemStateChanged(evt);
            }
        });
        comboCalleBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCalleBuscadorActionPerformed(evt);
            }
        });

        lblNum.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        lblNum.setText("Número oficial:");

        comboNumero1.setFont(new java.awt.Font("Solomon Sans Book", 0, 12)); // NOI18N
        comboNumero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNumero1ActionPerformed(evt);
            }
        });

        toggleBuscador.setBackground(new java.awt.Color(189, 0, 53));
        toggleBuscador.setFont(new java.awt.Font("Solomon Sans Book", 1, 14)); // NOI18N
        toggleBuscador.setText("Activar buscador");
        toggleBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleBuscadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOpciones2Layout = new javax.swing.GroupLayout(panelOpciones2);
        panelOpciones2.setLayout(panelOpciones2Layout);
        panelOpciones2Layout.setHorizontalGroup(
            panelOpciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOpciones2Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(panelOpciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOpciones2Layout.createSequentialGroup()
                        .addGroup(panelOpciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelOpciones2Layout.createSequentialGroup()
                                .addComponent(lblCol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboColonias1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelOpciones2Layout.createSequentialGroup()
                                .addComponent(lblMun)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboMunicipios1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOpciones2Layout.createSequentialGroup()
                        .addComponent(lblCal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboCalleBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelOpciones2Layout.createSequentialGroup()
                        .addComponent(lblNum)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboNumero1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelOpciones2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(toggleBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelOpciones2Layout.setVerticalGroup(
            panelOpciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpciones2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggleBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelOpciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblMun)
                    .addComponent(comboMunicipios1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(panelOpciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblCol)
                    .addComponent(comboColonias1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(panelOpciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(comboCalleBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCal))
                .addGap(21, 21, 21)
                .addGroup(panelOpciones2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNum)
                    .addComponent(comboNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOpciones2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panelOpciones2, javax.swing.GroupLayout.PREFERRED_SIZE, 468, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(189, 0, 53));

        lblTitulo.setFont(new java.awt.Font("Solomon Sans Book", 0, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Domicilio");

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cerrar.png"))); // NOI18N
        btnCerrar.setToolTipText("Cerrar");
        btnCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCerrarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)))
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

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        guardarDatos();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void txtDiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiaKeyTyped
        char c = evt.getKeyChar();
        int lon = txtDia.getText().length();
        if (!Character.isDigit(c) || lon >= 2) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDiaKeyTyped

    private void txtAnoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnoKeyTyped
        int lon = txtAno.getText().length();
        if (lon >= 4) {
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "El año no puede tener más de 4 caracteres");
        }

    }//GEN-LAST:event_txtAnoKeyTyped

    private void txtAnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnoKeyReleased
    }//GEN-LAST:event_txtAnoKeyReleased

    private void txtPropietarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPropietarioKeyTyped
//        int lon = txtPropietario.getText().length();
//        if (lon >= 18) {
//            evt.consume();
//            JOptionPane.showMessageDialog(rootPane, "El código CURP contiene únicamente 18 caracteres");
//        }
    }//GEN-LAST:event_txtPropietarioKeyTyped

    private void txtPropietarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPropietarioKeyReleased
        String cadena = txtPropietario.getText().toUpperCase();
        txtPropietario.setText(cadena);
    }//GEN-LAST:event_txtPropietarioKeyReleased

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        limpiarCampos();
    }//GEN-LAST:event_jPanel7MouseClicked

    private void btnCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarMouseClicked
        this.dispose();
    }//GEN-LAST:event_btnCerrarMouseClicked

    private void txtAnosResidenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnosResidenciaKeyTyped
        char c = evt.getKeyChar();
        int lon = txtAnosResidencia.getText().length();
        if (!Character.isDigit(c) || lon >= 2) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAnosResidenciaKeyTyped

    private void comboColoniasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboColoniasActionPerformed

    }//GEN-LAST:event_comboColoniasActionPerformed

    private void comboMunicipiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMunicipiosActionPerformed
        colonias();
    }//GEN-LAST:event_comboMunicipiosActionPerformed

    private void comboCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCalleActionPerformed

    }//GEN-LAST:event_comboCalleActionPerformed

    private void comboTipoViviendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoViviendaActionPerformed
        int i = comboTipoVivienda.getSelectedIndex();
        if (i > 1) {
            frmArrendamientoOnOff(true);
        } else {
            frmArrendamientoOnOff(false);
        }
    }//GEN-LAST:event_comboTipoViviendaActionPerformed

    private void comboNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboNumeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboNumeroActionPerformed

    private void comboColoniasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboColoniasItemStateChanged
        calles();
    }//GEN-LAST:event_comboColoniasItemStateChanged

    private void comboCalleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboCalleItemStateChanged
        numeros();
    }//GEN-LAST:event_comboCalleItemStateChanged

    private void comboMunicipios1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMunicipios1ActionPerformed
        colonias1();
    }//GEN-LAST:event_comboMunicipios1ActionPerformed

    private void comboColonias1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboColonias1ItemStateChanged

    }//GEN-LAST:event_comboColonias1ItemStateChanged

    private void comboColonias1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboColonias1ActionPerformed
        buscar();
        calles1();
    }//GEN-LAST:event_comboColonias1ActionPerformed

    private void comboCalleBuscadorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboCalleBuscadorItemStateChanged
        buscar();
        numeros1();
    }//GEN-LAST:event_comboCalleBuscadorItemStateChanged

    private void comboCalleBuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCalleBuscadorActionPerformed

    }//GEN-LAST:event_comboCalleBuscadorActionPerformed

    private void comboNumero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboNumero1ActionPerformed
        buscar();
    }//GEN-LAST:event_comboNumero1ActionPerformed

    private void toggleBuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleBuscadorActionPerformed
        if (comboMunicipios1.isEnabled() == true) {
            buscadorOnOff(false);
            toggleBuscador.setText("Activar buscador");
            llenarTabla();
        } else {
            buscadorOnOff(true);
            toggleBuscador.setText("Desactivar buscador");
        }
    }//GEN-LAST:event_toggleBuscadorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnCerrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboCalle;
    private javax.swing.JComboBox<String> comboCalle1;
    private javax.swing.JComboBox<String> comboCalle2;
    private javax.swing.JComboBox<String> comboCalleBuscador;
    private javax.swing.JComboBox<String> comboColonias;
    private javax.swing.JComboBox<String> comboColonias1;
    private javax.swing.JComboBox<String> comboMeses;
    private javax.swing.JComboBox<String> comboMunicipios;
    private javax.swing.JComboBox<String> comboMunicipios1;
    private javax.swing.JComboBox<String> comboNumero;
    private javax.swing.JComboBox<String> comboNumero1;
    private javax.swing.JComboBox<String> comboTipoVivienda;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCal;
    private javax.swing.JLabel lblCol;
    private javax.swing.JLabel lblMun;
    private javax.swing.JLabel lblNum;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelOpciones2;
    private javax.swing.JTable tabla;
    private javax.swing.JToggleButton toggleBuscador;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtAnosResidencia;
    private javax.swing.JTextField txtDia;
    private javax.swing.JTextField txtPropietario;
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
