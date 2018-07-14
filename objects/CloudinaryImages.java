package objects;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Jaasiel Méndez Malagón on 2018/07/12
 */
public class CloudinaryImages {

    private Cloudinary cloudinary;
    private final String cloud_name = "grupoavante";
    private final String API_key = "942495139723745";
    private final String API_secret = "1hgpd51RUb6zmrB8k-HDxI_dZZs";
    private final String environment_variable = "cloudinary://" + this.API_key + ":" + this.API_secret + "@" + this.cloud_name + "/";

    public CloudinaryImages() {
        try {
            this.cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", this.cloud_name, "api_key", this.API_key, "api_secret", this.API_secret));
        } catch (Exception e) {
            System.out.println("objects.CloudinaryImages.<init>() : " + e);
        }
    }

    public ImageIcon getImage(String imgName) {
        if (imgName != null) {
            String url = this.cloudinary.url().transformation(new Transformation().gravity("face").width(100).crop("thumb")).imageTag("personas_avante/" + imgName + ".jpg");
            ImageIcon icon = null;
            try {                
                if (url != null) {
                    url = url.substring(10);
                    url = url.replace("' width='100'/>", "");
                    System.out.println(url);
                    URLConnection connection = new URL(url).openConnection();
                    connection.setDoInput(true);
                    connection.setRequestProperty("User-Agent", "xxxxxx");
                    BufferedImage img = ImageIO.read(connection.getInputStream());
                    icon = new ImageIcon(img);
                }
            } catch (IOException ex) {
                System.out.println("objects.CloudinaryImages.getImage() : " + ex);
            }
            return icon;
        } else {
            return null;
        }
    }
}


/*TO UPLOAD IMAGE
    cloudinary.uploader().upload("sample.jpg",ObjectUtils.asMap("transformation",new Transformation().crop("limit").tags("samples").width(3000).height(2000))); 
 */
 /*TO MANIPULATE IMAGE 
    cloudinary.url().transformation(new Transformation().crop("fill").gravity("faces").width(300).height(200)).format("jpg").imageTag("sample");
 */
