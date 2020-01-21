package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

public class ConnectToGameController {
    public TextField ipAddressServer;
    @FXML
    public void cancel_button()
    {
        this.ipAddressServer.getScene().getWindow().hide();
    }
    @FXML
    public void ok_button() throws Exception
    {
        if (checkIfValidIpv4(this.ipAddressServer.getText())==false)
        {
            this.ipAddressServer.textProperty().setValue("Incorrect IP");
        }
        else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("startGame.fxml"));

                InetAddress ip = InetAddress.getByName(this.ipAddressServer.getText());
                //ClientSocket.sendPlayerImg();


                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Game!");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

                ClientSocket.socket = new Socket(ip,ClientSocket.port);
                System.out.println(loader.getController()==null);
                ClientSocket.controller = loader.getController();
                ClientSocket.loadStreams();
                ClientSocket.launchReceiver();
                ClientSocket.sendPlayerName();
                Stage thisstage = (Stage) this.ipAddressServer.getScene().getWindow();
                thisstage.getOwner().hide();
                this.ipAddressServer.getScene().getWindow().hide();
            }
            catch (Exception e)
            {
                this.ipAddressServer.textProperty().setValue("Server not found");

            }

        }
    }
    private static boolean checkIfValidIpv4(String text){
        StringTokenizer st = new StringTokenizer(text,".");
        for(int i = 0; i < 4; i++){
            if(!st.hasMoreTokens()){
                return false;
            }
            try{
            int num = Integer.parseInt(st.nextToken());
            if(num < 0 || num > 255){
                return false;
            }}
            catch (Exception e) {
                return false;
            }
        }
        if(st.hasMoreTokens()){
            return false;
        }
        return true;
    }
}
