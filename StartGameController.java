package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


public class StartGameController {
    @FXML
    public ImageView masterImg;
    public ImageView firstPlayerImg;
    public ImageView secondPlayerImg;
    public ImageView thirdPlayerImg;
    public ImageView fourthPlayerImg;
    public Label masterName;
    public Label firstPlayerName;
    public Label secondPlayerName;
    public Label thirdPlayerName;
    public Label fourthPlayerName;
    public Label firstPlayerScore;
    public Label secondPlayerScore;
    public Label thirdPlayerScore;
    public Label fourthPlayerScore;
    public Label gameState;

    public AnchorPane questionPane;
    public Label questionLabel;

    public GridPane questionGrid;
    public ArrayList<ArrayList<Boolean>> questionGridMask;

    public void displayQuestion(String theme, int cost, String text)
    {
        questionLabel.textProperty().setValue("Theme: "+theme+". Cost: "+Integer.toString(cost) + "\n"+text);
        questionPane.setVisible(true);
    }
    public void displayAnswer(String answer)
    {
        questionLabel.textProperty().setValue("Answer: "+answer);
    }
    public void removeQuestion()
    {
        questionPane.setVisible(false);
        questionLabel.textProperty().setValue("placeholder");
    }
    public Node getNode(int i, int j)
    {
        for (Node node: questionGrid.getChildren()) {
            if (GridPane.getColumnIndex(node) == j && GridPane.getRowIndex(node)==i) {
                return node;
            }
        }
        return null;
    }
    public void setMaskElement(int i, int j, boolean value)
    {
        questionGridMask.get(i).set(j,value);
    }
    public boolean getMaskElement(int i, int j)
    {
        return questionGridMask.get(i).get(j);
    }
    public void repaintGrid() {
        for (int i=0; i!=questionGridMask.size(); i++)
        {
            for (int j=0; j!=questionGridMask.get(i).size(); j++)
            {
                Button qbutton = (Button) getNode(i,j+1);
                qbutton.setDisable(!questionGridMask.get(i).get(j));
                qbutton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int row = GridPane.getRowIndex(qbutton);
                        int column = GridPane.getColumnIndex(qbutton);
                        ClientSocket.sendChosenQuestion(row,column-1);
                    }
                });
            }
        }
    }
    public void iknowanswerAction(ActionEvent e) {
        ClientSocket.sendIWantToAnswer();
    }

}
