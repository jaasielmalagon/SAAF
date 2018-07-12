package objects;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

/**
 *
 * @author Root
 */
public class CloudinaryImages {

    /*TO UPLOAD IMAGE
    cloudinary.uploader().upload("sample.jpg",ObjectUtils.asMap("transformation",new Transformation().crop("limit").tags("samples").width(3000).height(2000))); 
     */
 /*TO MANIPULATE IMAGE 
    cloudinary.url().transformation(new Transformation().crop("fill").gravity("faces").width(300).height(200)).format("jpg").imageTag("sample");
     */
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
            return this.cloudinary.url().format("png")
                    .transformation(new Transformation().width(100).height(100).crop("fit"))
                    .generate(imgName);
        } else {
            return "Image not found... ";
        }
    }
}
