package sample;

import javafx.application.Platform;
import javafx.scene.control.Button;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
    int position;
    public static StartGameMasterController controller;

    // constructor
    public ClientHandler(Socket s, String name,
                         DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin=true;
    }

    @Override
    public void run() {
        try
        {
            String received;
            received = dis.readUTF();
            System.out.println(received);
            QGameServer.players.get(position).setPlayer_name(received);
            Platform.runLater(()-> {
                controller.matchNamesToStored();
            });

            /*receiving image TODO
            * */
        } catch (IOException e) {

            e.printStackTrace();
        }
        while (true)
        {
            //тут должна быть только прослушка на случай "игрок хочет ответить". Для всего остального понадобятся внепоточные методы на отправку
            //тут ещё должна быть прослушка на выбор вопроса активным игроком.
                // receive the string
                try {
                    String received = dis.readUTF();
                    if (received.equals("iwanttoanswer"))
                    {
                        if (QGameServer.answering_player==-1 && QGameServer.game_state.equals("questionasked"))
                        {
                            QGameServer.answering_player = position;
                            QGameServer.game_state = "answergiven";
                            Platform.runLater(()->{
                                controller.answerIsGiven();
                            });
                        }
                    }
                    if (received.equals("pressedquestion"))
                    {
                        if (position == QGameServer.active_player_num && QGameServer.game_state.equals("grid"))
                        {
                            int i = dis.readInt();
                            int j = dis.readInt();
                            Button qbutton = (Button) controller.getNode(i,j+1);
                            Platform.runLater(()->{
                                qbutton.fire();
                            });
                        }
                        else{
                            dis.readInt(); dis.readInt();
                        }
                    }
                }
                catch (IOException e)
                {

                }

        }
    /*
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }*/
    }

    public void sendMasterName() {
        try {
            System.out.println("Sending: "+controller.master_name.getText());
            dos.writeUTF("master");
            dos.writeUTF(controller.master_name.getText());
        }catch (IOException e){}
    }
    public void sendPlayerName(int position, String name) {
        try {
            dos.writeUTF("playername");
            dos.writeInt(position);
            dos.writeUTF(name);
        } catch (IOException e){}
    }
    public void sendPlayerScore(int position, int score) {
        try {
            dos.writeUTF("playerscore");
            dos.writeInt(position);
            dos.writeInt(score);
        }catch (IOException e){}
    }
    public void sendQuestion(int row, int col, String theme, int cost, String text) {
        try {
            dos.writeUTF("questiontext");
            dos.writeInt(row);
            dos.writeInt(col);
            dos.writeUTF(theme);
            dos.writeInt(cost);
            dos.writeUTF(text);
        }catch (IOException e){}
    }
    public void sendAnswer(String answer) {
        try {
            dos.writeUTF("questionanswer");
            dos.writeUTF(answer);
        }catch (IOException e){}
    }
    public void sendBackToGrid() {
        try {
            dos.writeUTF("answered");
        } catch (IOException e){}
    }
    public void sendRound(int round) {
        try {
            dos.writeUTF("round");
            for (int i=0; i<QGameServer.game_package.getRound(round).getThemes().size(); i++)
            {
                dos.writeUTF(QGameServer.game_package.getRound(round).getTheme(i).getTheme_name());
                for (int j=0; j<QGameServer.game_package.getRound(round).getTheme(i).getQuestions().size();j++)
                {
                    dos.writeInt(QGameServer.game_package.getRound(round).getTheme(i).getQuestion(j).getCost());
                }
            }
        } catch (IOException e ){}
    }
    public void sendMask(int round)
    {
        try {
            dos.writeUTF("mask");
            for (int i=0; i<QGameServer.questionGridMask.get(round).size(); i++)
            {
                for (int j=0; j<QGameServer.questionGridMask.get(round).get(i).size(); j++)
                {
                    dos.writeBoolean(QGameServer.questionGridMask.get(round).get(i).get(j));
                }
            }
        }catch (IOException e){}
    }

    public void sendState(String text) {
        try
        {
            dos.writeUTF("gamestate");
            dos.writeUTF(text);
        } catch (IOException e) {}
    }
}
