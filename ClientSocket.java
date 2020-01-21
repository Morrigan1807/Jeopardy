package sample;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSocket {
    static Socket socket;
    final static int port=1234;
    static DataInputStream dis;
    static DataOutputStream dos;
    static Thread receiver;
    static StartGameController controller;
    public static void loadStreams()
    {
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e) {
        }
    }
    public static void sendPlayerName()
    {
        try {
            dos.writeUTF(UserProfile.getName());
        }
        catch (IOException e)
        {

        }
    }
    public static void sendIWantToAnswer() {
        try{
            dos.writeUTF("iwanttoanswer");
        } catch (IOException e){}
    }
    public static void sendChosenQuestion(int row, int column) {
        try{
            dos.writeUTF("pressedquestion");
            dos.writeInt(row);
            dos.writeInt(column);
        } catch (IOException e){}
    }
    public static void launchReceiver() {
        receiver = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Receiver is up");
                while (true)
                {
                    try {
                        String type_of_input = dis.readUTF();

                        if (type_of_input.equals("master"))
                        {
                            String name = dis.readUTF();

                            Platform.runLater(() -> {
                                controller.masterName.textProperty().setValue(name);
                            });
                        }
                        if (type_of_input.equals("playername"))
                        {
                            int position = dis.readInt();
                            String name = dis.readUTF();

                            Platform.runLater(() -> {
                                if (position == 0) {
                                    controller.firstPlayerName.textProperty().setValue(name);
                                }
                                if (position == 1) {
                                    controller.secondPlayerName.textProperty().setValue(name);
                                }
                                if (position == 2) {
                                    controller.thirdPlayerName.textProperty().setValue(name);
                                }
                                if (position == 3) {
                                    controller.fourthPlayerName.textProperty().setValue(name);
                                }
                            });
                        }
                        if (type_of_input.equals("playerscore"))
                        {
                            int position = dis.readInt();
                            int score = dis.readInt();
                            Platform.runLater(() -> {
                                if (position == 0) {
                                    controller.firstPlayerScore.textProperty().setValue(Integer.toString(score));
                                }
                                if (position == 1) {
                                    controller.secondPlayerName.textProperty().setValue(Integer.toString(score));
                                }
                                if (position == 2) {
                                    controller.thirdPlayerName.textProperty().setValue(Integer.toString(score));
                                }
                                if (position == 3) {
                                    controller.fourthPlayerName.textProperty().setValue(Integer.toString(score));
                                }
                            });
                        }
                        if (type_of_input.equals("gamestate"))
                        {
                            String state = dis.readUTF();
                            Platform.runLater(()->{
                                controller.gameState.textProperty().setValue(state);
                            });
                        }
                        if (type_of_input.equals("questiontext"))
                        {
                            int questionrow = dis.readInt();
                            int questioncol = dis.readInt();
                            String questiontheme = dis.readUTF();
                            int questioncost = dis.readInt();
                            String questiontext = dis.readUTF();
                            Platform.runLater(()-> {
                                controller.setMaskElement(questionrow, questioncol, false);
                                controller.repaintGrid();
                                controller.displayQuestion(questiontheme, questioncost, questiontext);
                            });

                        }
                        if (type_of_input.equals("questionanswer"))
                        {
                            String questionanswer = dis.readUTF();
                            Platform.runLater(()-> {
                                controller.displayAnswer(questionanswer);
                            });
                        }
                        if (type_of_input.equals("answered"))
                        {
                            Platform.runLater(()-> {
                                controller.removeQuestion();
                            });
                        }
                        if (type_of_input.equals("round"))
                        {
                            ArrayList<String> storedThemes = new ArrayList<>();
                            ArrayList<ArrayList<Integer>> storedCosts = new ArrayList<>();
                            for (int i = 0; i<6; i++)
                            {
                                String theme = dis.readUTF();
                                storedThemes.add(theme);
                                storedCosts.add(new ArrayList<>());
                                for (int j=0; j<5; j++)
                                {
                                    int cost = dis.readInt();
                                    storedCosts.get(i).add(cost);
                                }
                            }
                            Platform.runLater(()->{
                                for (int i=0; i<storedThemes.size(); i++)
                                {
                                    ((Label)controller.getNode(i,0)).textProperty().setValue(storedThemes.get(i));
                                    for (int j=0; j<storedCosts.get(i).size(); j++)
                                    {
                                        ((Button) controller.getNode(i, j+1)).setText(Integer.toString(storedCosts.get(i).get(j)));
                                    }
                                }
                            });
                        }
                        if (type_of_input.equals("mask"))
                        {
                            ArrayList<ArrayList<Boolean>> storedMask = new ArrayList<>();
                            for (int i = 0; i<6; i++)
                            {
                                final int fi = i;
                                storedMask.add(new ArrayList<>());
                                for (int j=0; j<5; j++)
                                {
                                    boolean maskij= dis.readBoolean();
                                    storedMask.get(i).add(maskij);
                                }
                            }

                            Platform.runLater(()->{
                                controller.questionGridMask=new ArrayList<>();
                                for (int i=0; i<storedMask.size(); i++)
                                {
                                    controller.questionGridMask.add(new ArrayList<>());
                                    for (int j=0; j<storedMask.get(i).size(); j++)
                                    {
                                        controller.questionGridMask.get(i).add(true);
                                        controller.setMaskElement(i,j,storedMask.get(i).get(j));
                                    }
                                }
                            });
                            Platform.runLater(()-> {
                                controller.repaintGrid();
                            });
                        }


                    }
                    catch (IOException e) {}

                }
            }
        });
        receiver.setDaemon(true);
        receiver.start();
    }


}
