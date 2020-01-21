package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import sample.gamepackage.QPackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ChangeProfileController {
    public TextField user_name;
    public ImageView user_image;

    public void initialize()
    {
        user_name.textProperty().setValue(UserProfile.getName());
        ByteArrayInputStream image_in = new ByteArrayInputStream(UserProfile.getImage());

        user_image.setImage(new Image(image_in));
    }
    public void profileChangeFinished() {
        BufferedImage bImage = SwingFXUtils.fromFXImage(user_image.getImage(), null);
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "jpg", s);
            byte[] res = s.toByteArray();
            s.close();
            UserProfile.setImage(res);
            UserProfile.setName(user_name.textProperty().getValue());
        }
        catch (Exception e)
        {

        }
        user_name.getScene().getWindow().hide();
    }

    public void changeAvatarButton()
    {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image (.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showOpenDialog(user_name.getScene().getWindow());
        try {
            Image img = new Image(file.toURI().toString());
            //img.heightProperty().
            user_image.setImage(img);
        } catch (Exception e){
            System.out.println(e.getMessage()+"\n"+"path:"+file.getAbsolutePath());
        }



    }

}
