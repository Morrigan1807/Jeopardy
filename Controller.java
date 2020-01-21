package sample;


import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;

public class Controller {

    public AnchorPane mainPane;
    public Text user_name;
    public ImageView user_image;
    public void initialize()
    {
        new UserProfile(user_image.getImage());
    }
    @FXML
    public void onChangeProfile(ActionEvent actionEvent) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("changeProfile.fxml"));
        ChangeProfileController controller = loader.getController();
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainPane.getScene().getWindow());
        stage.setTitle("Change profile");
        stage.setScene(new Scene(root));
        //stage.setOnCloseRequest(e -> controller.profileChangeFinished());
        stage.setResizable(false);
        stage.showAndWait();
        user_name.textProperty().setValue(UserProfile.getName());
        ByteArrayInputStream image_in = new ByteArrayInputStream(UserProfile.getImage());

        user_image.setImage(new Image(image_in));
    }
    @FXML
    public void onCreatePackage(ActionEvent actionEvent) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createPackage.fxml"));
        CreatePackageController controller = loader.getController();
        //controller.mainPane = this.mainPane;
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Package creation");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainPane.getScene().getWindow());
        stage.setResizable(false);
        stage.showAndWait();
        //this.mainPane.getScene().getWindow().hide();
    }
    public void onCreateNewGame(ActionEvent actionEvent) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createNewGame.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Create new game");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainPane.getScene().getWindow());
        stage.setResizable(false);
        stage.showAndWait();
    }

    public void onConnectToGame(ActionEvent actionEvent) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("connectToGame.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Connect to game");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainPane.getScene().getWindow());
        stage.setResizable(false);
        stage.showAndWait();
    }
    public void exit_button()
    {
        this.user_name.getScene().getWindow().hide();
    }

}
