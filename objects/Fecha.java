package objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author JMalagon
 */
public class Fecha {

    Calendar FECHA = new GregorianCalendar();

    public Fecha() {
        
    }

    public int dayOfMonth() {
        return FECHA.get(Calendar.DAY_OF_MONTH);
    }

    public int monthOfYear() {
        return FECHA.get(Calendar.MONTH) + 1;
    }

    public int currentYear() {
        return FECHA.get(Calendar.YEAR);
    }
    
    /**
     * Convierte la fecha ingresada al formato dd-MM-yyy.
     * El FORMATO DE FECHA SIEMPRE DEBE SER yyyy-MM-dd
     * @param   fecha   la fecha para ser comparada
     * @return  si la fecha ingresada es igual a la fecha actual retorna verdadero, de lo contrario retorna falso
     * @since   1.0     
     */
    public String formatoFecha(String fecha) {
        String fechas = null;
        try {
            SimpleDateFormat fe = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat myFecha = new SimpleDateFormat("20" + "yy-MM-dd");
 
            fechas = myFecha.format(fe.parse(fecha));
        } catch (ParseException e) {
            System.out.println("No se pudo convertir");
        }
        return fechas;
    }
    
    /**
     * Compara la fecha ingresada con la fecha actual en formato yyyy-MM-dd.
     * El FORMATO DE FECHA SIEMPRE DEBE SER yyyy-MM-dd
     * @param   date   la fecha para ser comparada
     * @return  si la fecha ingresada es igual a la fecha actual retorna verdadero, de lo contrario retorna falso
     * @since   1.0     
     */    
    public boolean compareEqualDate(String date) {        
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date formatedDate = dateFormat.parse(date);
            String currentDate = String.valueOf(currentYear()) + "-" + String.valueOf(monthOfYear()) + "-" + String.valueOf(dayOfMonth());
            Date formatedCurrentDate = dateFormat.parse(currentDate);
            int comparison = formatedDate.compareTo(formatedCurrentDate);
            return comparison == 0;
        } catch (ParseException ex) {
            return false;
        }
    }
    /**
     * Compara la fecha ingresada con la fecha actual en formato yyyy-MM-dd.
     * El FORMATO DE FECHA SIEMPRE DEBE SER yyyy-MM-dd
     * @param   date   la fecha para ser comparada
     * @return  si la fecha ingresada es menor a la fecha actual retorna verdadero, de lo contrario retorna falso
     * @since   1.0     
     */
    public boolean compareMinorDate(String date) {       
        boolean flag = true;
        try {
            if (!"".equals(date) && date != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date formatedDate = dateFormat.parse(date);
//                System.out.println(formatedDate);
//                System.err.println(date);
                int ano = Integer.parseInt(date.substring(0, 4));
                int mes = Integer.parseInt(date.substring(5, 7));
                int dia = Integer.parseInt(date.substring(8, 10));
                if (ano < currentYear()) {
//                    System.out.println("if "+ano+" < " +currentYear());
                    flag = true;                    
                } else {                    
                    if (ano == currentYear() && mes < monthOfYear()) {
//                        System.out.println("if "+ano+" == "+currentYear()+" && "+mes+" < "+monthOfYear());
                        flag = true;                        
                    }else{                        
                        if (ano == currentYear() && mes == monthOfYear() && dia < dayOfMonth()) {
//                            System.out.println("if ("+ano+" == "+currentYear()+" && "+mes+" == "+monthOfYear()+" && "+dia+" < "+dayOfMonth()+")");
                            flag = true;
                        }else{
                            flag = false;
                        }
                    }
                }
            } else {                
                flag = false;
            }
        } catch (ParseException ex) {
            System.out.println("Fecha inválida");
            flag = true;
        }
        return flag;
    }

    /**
     * Comprueba que el parámetro inputDate pueda ser convertido formato yyyy-MM-dd
     * El FORMATO DE FECHA SIEMPRE DEBE SER yyyy-MM-dd
     * @param   inputDate   la fecha para ser comparada
     * @return  si la fecha ingresada no puede convertirse a formato de fecha tipo Date retorna falso y el mensaje "Fecha no válida"
     * @since   1.0     
     */
    public boolean isValid(String inputDate) {//si la fecha es válida retorna verdadero, de lo contrario retorna falso
        try {            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date formatedDate = dateFormat.parse(inputDate);
            return true;            
        } catch (ParseException ex) {
            System.out.println("Fecha no válida");
            return false;
        }
    }

    /**
     * Retorna la fecha actual en un arreglo de cadenas:
     * String[0] = dia de la semana (Lunes - Domingo)
     * String[1] = dia del mes (01 - 31)
     * String[2] = mes del año (01 - 12)
     * String[3] = año actual
     * String[4] = hora del día actual (00 - 23)
     * String[5] = minuto de la hora actual (00 - 59)
     * String[6] = segundo del minuto actual (00 - 59)
     * String[7] = meridiano (a.m. - p.m.)     
     * @return  String[7]
     * @since   1.0     
     */
    public String[] fechaSegmentada() {
        int dia = FECHA.get(Calendar.DAY_OF_MONTH);
        int mes = FECHA.get(Calendar.MONTH) + 1;
        int anio = FECHA.get(Calendar.YEAR);
        int ds = FECHA.get(Calendar.DAY_OF_WEEK);
        int hora = FECHA.get(Calendar.HOUR);
        int minuto = FECHA.get(Calendar.MINUTE);
        int segundo = FECHA.get(Calendar.SECOND);
        int meridiano = FECHA.get(Calendar.AM_PM);
        String diaSem = "";
        String[] date = new String[8];
        switch (ds) {
            case 2:
                diaSem = "Lunes";
                break;
            case 3:
                diaSem = "Martes";
                break;
            case 4:
                diaSem = "Miercoles";
                break;
            case 5:
                diaSem = "Jueves";
                break;
            case 6:
                diaSem = "Viernes";
                break;
            case 7:
                diaSem = "Sabado";
                break;
            default:
                diaSem = "Domingo";
                break;
        }
        date[0] = diaSem;
        if (dia <= 9) {
            date[1] = "0" + String.valueOf(dia);
        } else {
            date[1] = String.valueOf(dia);
        }
        if (mes <= 9) {
            date[2] = "0" + String.valueOf(mes);
        } else {
            date[2] = String.valueOf(mes);
        }
        date[3] = String.valueOf(anio);
        if (hora <= 9) {
            date[4] = "0" + String.valueOf(hora);
        } else {
            date[4] = String.valueOf(hora);
        }
        if (minuto <= 9) {
            date[5] = "0" + String.valueOf(minuto);
        } else {
            date[5] = String.valueOf(minuto);
        }
        if (segundo <= 9) {
            date[6] = "0" + String.valueOf(segundo);
        } else {
            date[6] = String.valueOf(segundo);
        }
        if (meridiano > 0) {
            date[7] = "p.m.";
        } else {
            date[7] = "a.m.";
        }
        return date;
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
    
    @Override
    public String toString(){
        return Arrays.toString(this.fechaSegmentada());        
    }
}
