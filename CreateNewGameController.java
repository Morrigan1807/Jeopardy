package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.gamepackage.QPackage;

import java.io.*;

public class CreateNewGameController {
    public Label path_label;
    public QPackage qPackage;
    public void initialize(){
        qPackage = null;
    }
    @FXML
    public void choose_package_button()
    {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Jeopardy package (.jqp)", "*.jqp");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showOpenDialog(path_label.getScene().getWindow());
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.getAbsoluteFile()));
            qPackage = (QPackage) ois.readObject();
            ois.close();
            this.path_label.textProperty().setValue(file.getAbsolutePath());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void cancel_button()
    {
        this.path_label.getScene().getWindow().hide();
    }
    @FXML
    public void ok_button() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("startGameMaster.fxml"));
        //StartGameMasterController controller = loader.getController();
        //controller.mainPane = this.mainPane;
        QGameServer.game_package = this.qPackage;

        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Jeopardy!");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        Stage thisstage = (Stage) this.path_label.getScene().getWindow();
        thisstage.getOwner().hide();
        this.path_label.getScene().getWindow().hide();

    }

}
