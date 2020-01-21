package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class GameServerSocket implements Runnable{
    // Vector to store active clients
    static Vector<ClientHandler> ar = new Vector<>();
    public static StartGameMasterController controller;
    // counter for clients
    static int i = 0;

    public static void sendMask() {
        for (int i=0; i<ar.size(); i++)
        {
            ar.get(i).sendMask(QGameServer.current_round);
        }
    }

    public static void sendQuestion() {
        for (int i=0; i<ar.size(); i++)
        {
            ar.get(i).sendQuestion(QGameServer.question_row_index,QGameServer.question_col_index,
                    QGameServer.game_package.getRound(QGameServer.current_round).getTheme(QGameServer.question_row_index).getTheme_name(),
                    QGameServer.current_question.getCost(),QGameServer.current_question.getQuestion());
        }
    }
    public static void sendRound() {
        for (int i=0; i<ar.size(); i++) {
            ar.get(i).sendRound(QGameServer.current_round);
        }
        sendState();
    }
    public static void sendAnswer() {
        for (int i=0; i<ar.size(); i++)
        {
            ar.get(i).sendAnswer(QGameServer.current_question.getAnswer());
        }
        sendState();
    }

    public static void backToQuestions() {
        for (int i=0; i<ar.size(); i++)
        {
            ar.get(i).sendBackToGrid();
        }
        sendState();
    }

    public void run()
    {
        // server is listening on port 1234
        try {
            ServerSocket ss = new ServerSocket(1234);
            Socket s;
            // running infinite loop for getting
            // client request
            while (true)
            {
                // Accept the incoming request
                s = ss.accept();

                // obtain input and output streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                // Create a new handler object for handling this request.
                QGameServer.players.add(new QGamePlayer());
                ClientHandler mtch = new ClientHandler(s,"client " + i, dis, dos);
                mtch.position = QGameServer.players.size()-1;
                mtch.controller = controller;
                // Create a new Thread with this object.
                Thread t = new Thread(mtch);

                // add this client to active clients list
                ar.add(mtch);
                // start the thread.
                t.start();

                // increment i for new client.
                // i is used for naming only, and can be replaced
                // by any naming scheme
                i++;
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (Exception e){}
                updatePlayers();
                sendRound();
                sendMask();

            }
        }
        catch (IOException e) {

        }
    }
    public static void updatePlayers() {
        for (int i=0; i<QGameServer.players.size(); i++)
        {
            ar.get(i).sendMasterName();
            for (int j=0; j<QGameServer.players.size(); j++)
            {
                ar.get(i).sendPlayerName(i,QGameServer.players.get(i).getPlayer_name());
            }
        }
        sendState();
    }
    public static void updateScore(int player_num) {
        for (int i=0; i<QGameServer.players.size(); i++)
        {
            int score =0;
            if (player_num == 0) score = (int)controller.player1score.getValue();
            if (player_num == 1) score = (int)controller.player2score.getValue();
            if (player_num == 2) score = (int)controller.player3score.getValue();
            if (player_num == 3) score = (int)controller.player4score.getValue();
            ar.get(i).sendPlayerScore(player_num, score);
        }
        sendState();
    }

    public static void sendState() {
        for (int i=0; i<ar.size(); i++) {
            ar.get(i).sendState(controller.view_state.getText());
        }
    }
}
