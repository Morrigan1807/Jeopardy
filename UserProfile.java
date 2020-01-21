package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class UserProfile {
    private static String name;
    private static byte[] image;
    public UserProfile(String name, byte[] image)
    {
        this.name = name;
        this.image = image;
    }
    public UserProfile(Image user_image)
    {
        this.name = "Player";

        BufferedImage bImage = SwingFXUtils.fromFXImage(user_image, null);
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "jpg", s);
            this.image = s.toByteArray();
            s.close();

        }
        catch (Exception e)
        {

        }
    }
    public static void setName(String new_name) {
        name = new_name;
    }

    public static  void setImage(byte[] new_image) {
        image = new_image;
    }

    public static byte[] getImage() {
        return image;
    }

    public static String getName() {
        return name;
    }
}
