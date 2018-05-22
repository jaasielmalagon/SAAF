package objects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author JMalagon
 */
public class ErrorController {
    Fecha FECHA;
    public ErrorController() {
        this.FECHA = new Fecha();
    }

    public void escribirErrorLogger(String area, String error) {        
        File archivo = new File("ErrorController_" + area + ".txt");
        FileWriter escribir;
        PrintWriter escritor;
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException ex) {
                System.out.println("objects.ErrorController.escribirErrorLogger() ERROR AL CREAR: " + ex);
            }
        }
        try {            
            escribir = new FileWriter(archivo, true);
            escritor = new PrintWriter(escribir);
            escritor.println(this.FECHA.toString() + ": " +error);
            escritor.close();
            escribir.close();
        } catch (IOException ex) {
            System.out.println("objects.ErrorController.escribirErrorLogger() ERROR AL ESCRIBIR: " + ex);
        }
    }
}

//try {
//    FileWriter w = new FileWriter(f);
//    try (BufferedWriter bw = new BufferedWriter(w);
//            PrintWriter wr = new PrintWriter(bw)) {
////                wr.write("Esta es una linea de codigo");//escribimos en el archivo
//        wr.append(error); //concatenamos en el archivo sin borrar lo existente
//        //ahora cerramos los flujos de canales de datos, al cerrarlos el archivo quedará guardado con información escrita
//        //de no hacerlo no se escribirá nada en el archivo
//        wr.close();
//        bw.close();
//    }
//} catch (IOException e) {
//    System.out.println("objects.ErrorController.escribirErrorLogger() : " + e);
//}
