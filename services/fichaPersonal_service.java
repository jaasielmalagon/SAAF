package services;

import objects.Domicilio;
import objects.Persona;
import resources.fichaPersonal_resource;

/**
 *
 * @author JMalagon
 */
public class fichaPersonal_service {
    
    private final fichaPersonal_resource recurso;    
    public fichaPersonal_service(){
        this.recurso = new fichaPersonal_resource();
    }
    
    public String estado(int id) {
        return recurso.estado(id);
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
    
    public Persona persona(int idSucursal, int idPersona) {
        Persona personas = null;
        String[] array = this.recurso.persona(idSucursal, idPersona);
        if (array != null) {
            personas = new Persona(Integer.parseInt(array[0]), array[1], array[2], array[3], array[4], Integer.parseInt(array[5]), array[6], array[7], array[8], Integer.parseInt(array[9]), array[10], array[11], Integer.parseInt(array[12]), Integer.parseInt(array[13]), Integer.parseInt(array[14]), Integer.parseInt(array[15]));
        }
        return personas;
    }
}
