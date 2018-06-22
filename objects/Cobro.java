/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author 76053
 */
public class Cobro {
    private int ID = 0;   
    private String FOLIO, CAMPO1, CAMPO2;
    
    public Cobro(int ID, String FOLIO, String CAMPO1, String CAMPO2) {
        this.ID = ID;        
        this.FOLIO = FOLIO;
        this.CAMPO1 = CAMPO1;
        this.CAMPO2 = CAMPO2;        
    }
    
    public Cobro() {
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFOLIO(String FOLIO) {
        this.FOLIO = FOLIO;
    }

    public void setCAMPO1(String CAMPO1) {
        this.CAMPO1 = CAMPO1;
    }

    public void setCAMPO2(String CAMPO2) {
        this.CAMPO2 = CAMPO2;
    }

    public int getID() {
        return ID;
    }

    public String getFOLIO() {
        return FOLIO;
    }

    public String getCAMPO1() {
        return CAMPO1;
    }

    public String getCAMPO2() {
        return CAMPO2;
    }
    
    @Override
    public String toString() {
        return "Cobro{" + "ID=" + ID + ", FOLIO=" + FOLIO + ", CAMPO1=" + CAMPO1 + ", CAMPO2=" + CAMPO2 + '}';
    }   
}
