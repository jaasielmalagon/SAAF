package objects;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

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

    public String getImageUrl(String imgName) {
        if (imgName != null) {
            return this.cloudinary.url().transformation(new Transformation().gravity("face").width(100).crop("thumb")).imageTag("personas_avante/"+imgName+".jpg");
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