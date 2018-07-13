package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 *
 * @author JMalagon
 * La clase Filetransfer nos ayuda a tranferir imágenes desde el servidor
 * al sistema SAAF, para su posterior uso dentro del programa.
 */
public class Filetransfer {
    public void transfer(){
        try {
            new ImageBySockets(8081, 1, InetAddress.getByName("127.0.0.1")).start();
            Socket socket=new Socket( InetAddress.getByName("127.0.0.1"),8081);
            BufferedImage bufferedImage = ImageIO.read(new File("/home/leyer/lsm.png"));
            ImageIO.write(bufferedImage, "png", socket.getOutputStream());
            socket.getOutputStream().flush();
        }catch (IOException e) {
        }
    }
}