package services;

import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import objects.Calle;
import objects.Colonia;
import objects.Domicilio;
import objects.Fecha;
import objects.Mes;
import objects.Municipio;
import objects.Numerosdomiciliares;
import resources.agregarDirecciones_resource;

/**
 *
 * @author JMalagon
 */
public class agregarDirecciones_service {

    private final agregarDirecciones_resource recurso;
    private ResultSet rs;

    public agregarDirecciones_service() {
        this.recurso = new agregarDirecciones_resource();
    }

    //FUNCIONALES EN POO
    public int guardarDatos(int sucursal, int calle, int numero, int calle1, int calle2, int colonia, int tipo, String propietario, String vigencia, int tiempoResidencia) {
        Fecha fecha = new Fecha();
        int error = 0;
        if (tipo > 1) {
            if (fecha.compareMinorDate(vigencia) == true || fecha.compareEqualDate(vigencia) == true) {
                System.err.println("La fecha es menor o igual a la actual");
                error = 1;
            } else if ("".equals(propietario)) {
                System.err.println("El nombre del propietario está vacío");
                error = 2;
            } else if (tiempoResidencia < 1) {
                System.err.println("El tiempo de residencia es inválido.");
                error = 3;
            }
        }
        if (error == 0) {
            boolean g = this.recurso.guardarDomicilio(sucursal, calle, numero, calle1, calle2, colonia, tipo, propietario, vigencia, tiempoResidencia);
            if (g != true) {
                error = 4;
            }
        }
        return error;
    }

    public int buscarDireccion(int numero) {
        int idDomicilio = 0;
        try {
            idDomicilio = this.recurso.idDomicilio(numero);
        } catch (Exception e) {
            System.out.println("services.agregarDirecciones_service.buscarDireccion() : " + e);
        }
        return idDomicilio;
    }

    public Numerosdomiciliares[] numerosDeCalle(int idCalle) {
        Numerosdomiciliares[] array = null;
        try {
            String[][] nd = this.recurso.numeros(idCalle);
            if (nd != null) {
                array = new Numerosdomiciliares[nd.length];
                for (int i = 0; i < nd.length; i++) {
                    array[i] = new Numerosdomiciliares(Integer.valueOf(nd[i][0]), nd[i][1], Integer.valueOf(nd[i][2]));
                }
            }
        } catch (Exception e) {
            System.out.println("services.agregarDirecciones_service.numerosDeCalle() " + e);
        }
        return array;
    }

    public Calle[] calles(int idColonia) {
        Calle[] calles = null;
        String[][] array = this.recurso.traerCalles(idColonia);
        if (array != null) {
            calles = new Calle[array.length];
            for (int i = 0; i < array.length; i++) {
                calles[i] = new Calle(Integer.parseInt(array[i][0]), array[i][1], Integer.parseInt(array[i][2]));
            }
        }
        return calles;
    }

    public DefaultComboBoxModel cmbColonias(int idMunicipio) {
        String[][] array = this.recurso.traerColonias(idMunicipio);
        DefaultComboBoxModel dcbm = null;
        if (array != null) {
            dcbm = new DefaultComboBoxModel();
            for (String[] array1 : array) {
                dcbm.addElement(new Colonia(Integer.valueOf(array1[0]), array1[1], Integer.valueOf(array1[2]), Integer.valueOf(array1[3]), Integer.valueOf(array1[4])));
            }
        }
        return dcbm;
    }

    public DefaultComboBoxModel municipiosDeSucursal(int idSucursal) {
        DefaultComboBoxModel dcbm = null;
        String[][] array = this.recurso.municipios(idSucursal);
        if (array != null) {
            dcbm = new DefaultComboBoxModel();
            for (String[] array1 : array) {                
                dcbm.addElement(new Municipio(Integer.valueOf(array1[0]), array1[1], Integer.valueOf(array1[2])));
            }
        }
        return dcbm;
    }

    public Mes[] meses() {
        Mes[] meses = new Mes[12];
        meses[0] = new Mes("01", "Enero");
        meses[1] = new Mes("02", "Febrero");
        meses[2] = new Mes("03", "Marzo");
        meses[3] = new Mes("04", "Abril");
        meses[4] = new Mes("05", "Mayo");
        meses[5] = new Mes("06", "Junio");
        meses[6] = new Mes("07", "Julio");
        meses[7] = new Mes("08", "Agosto");
        meses[8] = new Mes("09", "Septiembre");
        meses[9] = new Mes("10", "Octubre");
        meses[10] = new Mes("11", "Noviembre");
        meses[11] = new Mes("12", "Diciembre");
        return meses;
    }

    public DefaultTableModel tablaDirecciones(int sucursal) {
        String titulos[] = {"Folio", "Dirección"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String[][] array = this.recurso.domicilios(sucursal);
        if (array != null) {
            for (String[] var : array) {
                Object[] cli = new Object[2];
                cli[0] = var[0];
                cli[1] = var[1] + " " + var[2] + " " + var[3] + " " + var[4] + "," + var[6] + "," + var[7] + " C.P. " + var[5];
                dtm.addRow(cli);
            }
        }
        return dtm;
    }

    public DefaultTableModel tablaDireccionesFiltrado(int sucursal, int idDomicilio) {
        String titulos[] = {"Folio", "Dirección"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String[][] array = this.recurso.domiciliosFiltrado(sucursal, idDomicilio);
        Object[] cli = new Object[2];
        if (array != null) {
            for (String[] var : array) {
                cli[0] = var[0];
                cli[1] = var[1] + " " + var[2] + " " + var[3] + " " + var[4] + "," + var[6] + "," + var[7] + " C.P. " + var[5];
                dtm.addRow(cli);
            }
        }else {
            cli[0] = "0";
            cli[1] = "SIN RESULTADOS ENCONTRADOS";
            dtm.addRow(cli);
        }
        return dtm;
    }

    public Domicilio domicilio(int idDomicilio) {
        Domicilio domicilio = null;
        if (idDomicilio > 0) {
            String[] array = this.recurso.domicilio(idDomicilio);
            if (array != null) {
                String[] direccion = {array[10], array[11], array[12], array[13], array[14], array[15], array[16]};
                domicilio = new Domicilio(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]), Integer.parseInt(array[3]), Integer.parseInt(array[4]), Integer.parseInt(array[5]), Integer.parseInt(array[6]), array[7], array[8], Integer.parseInt(array[9]), direccion);
            } else {
                System.out.println("services.agregarDirecciones_service.domicilio() : ");
            }
        }
        return domicilio;
    }

    public int actualizarPersona(int idPersona, int idDomicilio) {
        int error = 0;
        if (idPersona > 0 && idDomicilio > 0) {
            int asociados = this.recurso.contarAsociaciones(idDomicilio);
            if (asociados < 3) {
                boolean asociar = this.recurso.asociarDomicilio(idPersona, idDomicilio);
                if (asociar != true) {
                    error = 2;//ALGO FALLÓ EN LA ACTUALIZACIÓN
                }
            } else {
                error = 1;//EL DOMICILIO YA TIENE 3 ASOCIADOS
            }
        }
        return error;
    }

    public DefaultTableModel tablaDireccionesBuscar(int sucursal, int colonia, int calle, int numero) {
        String titulos[] = {"Folio", "Dirección"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String condicional = "WHERE domicilios.sucursal = " + sucursal + " AND ";
        if (colonia > 0 && calle == 0 && numero == 0) {
            condicional = condicional + "domicilios.colonia = " + colonia;
        } else if (colonia > 0 && calle > 0 && numero == 0) {
            condicional = condicional + "(domicilios.colonia = " + colonia + " AND domicilios.calle = " + calle + ")";
        } else if (colonia > 0 && calle > 0 && numero > 0) {
            condicional = condicional + "(domicilios.colonia = " + colonia + " AND domicilios.calle = " + calle + " AND domicilios.numero = " + numero + ")";
        }
        String[][] array = this.recurso.buscarDomicilios(condicional);
        Object[] cli = new Object[2];
        if (array != null) {
            for (String[] var : array) {
                cli[0] = var[0];
                cli[1] = var[1] + " " + var[2] + " " + var[3] + " " + var[4] + "," + var[6] + "," + var[7] + " C.P. " + var[5];
                dtm.addRow(cli);
            }
        } else {
            cli[0] = "0";
            cli[1] = "SIN RESULTADOS ENCONTRADOS";
            dtm.addRow(cli);
        }
        return dtm;
    }

    public DefaultTableModel tablaDireccionesBuscarFiltrado(int sucursal, int colonia, int calle, int numero, int idDomicilio) {
        String titulos[] = {"Folio", "Dirección"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String condicional = "WHERE domicilios.sucursal = " + sucursal + " AND domicilios.idDomicilio != " + idDomicilio + " AND ";
        if (colonia > 0 && calle == 0 && numero == 0) {
            condicional = condicional + "domicilios.colonia = " + colonia;
        } else if (colonia > 0 && calle > 0 && numero == 0) {
            condicional = condicional + "(domicilios.colonia = " + colonia + " AND domicilios.calle = " + calle + ")";
        } else if (colonia > 0 && calle > 0 && numero > 0) {
            condicional = condicional + "(domicilios.colonia = " + colonia + " AND domicilios.calle = " + calle + " AND domicilios.numero = " + numero + ")";
        }
        String[][] array = this.recurso.buscarDomiciliosFiltrado(condicional);
        Object[] cli = new Object[2];
        if (array != null) {
            for (String[] var : array) {
                cli[0] = var[0];
                cli[1] = var[1] + " " + var[2] + " " + var[3] + " " + var[4] + "," + var[6] + "," + var[7] + " C.P. " + var[5];
                dtm.addRow(cli);
            }
        } else {
            cli[0] = "0";
            cli[1] = "SIN RESULTADOS ENCONTRADOS";
            dtm.addRow(cli);
        }
        return dtm;
    }

    //TERMINAN FUNCIONALES EN POO
}
