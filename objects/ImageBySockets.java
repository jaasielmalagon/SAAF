package objects;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 *
 * @author JMalagon
 */

class ImageBySockets extends ServerSocket implements Runnable{
    public ImageBySockets(int port, int backlog, InetAddress bindAddr)throws IOException {
        super(port, backlog, bindAddr);
        System.out.println("Server started port "+port);
    }
    public void start(){
        new Thread(this).start();
    }
    @Override
    public void run() {
        System.out.println("Waiting for connections...");
        try {
            Socket socket=accept();
            System.out.println("New connection "+socket.getInetAddress());
            BufferedImage bufferedImage = ImageIO.read(socket.getInputStream());
            ImageIO.write(bufferedImage,"png", new FileOutputStream("/home/leyer/image.png"));
            System.out.println("Image received!");  
            socket.close();
            close();
            System.out.println("Server close.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
