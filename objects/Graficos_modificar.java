package objects;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.border.Border;

/**
 *
 * @author JMalagon
 */
public class Graficos_modificar implements Border{    
    private BufferedImage image;
    
    public Graficos_modificar(){
    }
    
    public void setBg(String nombreImagen, JDesktopPane panel, BufferedImage image){
        try {
            this.image = image;//ImageIO.read(this.getClass().getResourceAsStream("/image/" + nombreImagen));
            panel.setBorder((Border) this.image);
        } catch (Exception ex) {
            System.out.println("Wallpaper no disponible: " + ex.getStackTrace()[1].getMethodName());
            System.err.println(ex);
        }        
    }
    
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){
        int x0 = x+ (width-image.getWidth())/2;
        int y0 = y+ (height-image.getHeight())/2;
        g.drawImage(image, x0, y0, null);
    }
    
    public Insets getBorderInsets(Component c){
        return new Insets(0, 0, 0, 0);
    }
    public boolean isBorderOpaque(){
        return true;
    }
    
    private Image icono(String nombreImagen) {
        Image img = null;
        try {
            img = new ImageIcon(getClass().getResource("/image/" + nombreImagen)).getImage();
        } catch (Exception e) {
            System.err.println("No se encontró el ícono");
        }        
        return img;
    }
}
