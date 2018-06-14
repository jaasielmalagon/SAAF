package services;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import objects.Lista;
import objects.Usuario;
import resources.solicitudCredito_resource;

/**
 *
 * @author JMalagon
 */
public class solicitudes_service {

    private final solicitudCredito_resource RECURSO;

    public solicitudes_service(String modulo) {
        this.RECURSO = new solicitudCredito_resource(modulo);
    }

    public DefaultComboBoxModel comboAdc(Usuario USUARIO) {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[][] array = this.RECURSO.getAdcFromSucursal(USUARIO.getIdSucursal());
        if (array != null) {
            Object[] obj = new Object[2];
            obj[0] = 0;
            obj[1] = "--- Seleccione ---";
            dcbm.addElement(obj);
            for (String[] val : array) {
                obj[0] = val[0];
                if (Integer.parseInt(val[2]) < 10) {
                    val[2] += "0" + val[2];
                }
                obj[1] = "Z" + val[1] + "-" + val[2];
                dcbm.addElement(obj);
            }
        }else{
            Object[] obj = new Object[2];
            obj[0] = 0;
            obj[1] = "Sin resultados";
            dcbm.addElement(obj);
        }
        return dcbm;
    }

}
