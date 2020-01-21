package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Vector;

public class StartGameMasterController {
    @FXML
    public ImageView master_image;
    public Label master_name;
    public Label view_state;
    public Spinner player1score;
    public Spinner player2score;
    public Spinner player3score;
    public Spinner player4score;
    public GridPane questionGrid;
    public Button acceptButton;
    public Button declineButton;
    public Button backToQuestionsButton;
    public Button prevRoundButton;
    public Button nextRoundButton;
    public AnchorPane questionPane;
    public Label questionText;
    public Label player1name;
    public Label player2name;
    public Label player3name;
    public Label player4name;
    public QGameServer game;
    public Thread serverThread;
    public void initialize() {

        ByteArrayInputStream image_in = new ByteArrayInputStream(UserProfile.getImage());
        master_image.setImage(new Image(image_in));
        master_name.textProperty().setValue(UserProfile.getName());
        player1score.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-100000, 100000, 0));
        player2score.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-100000, 100000, 0));
        player3score.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-100000, 100000, 0));
        player4score.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-100000, 100000, 0));

        view_state.textProperty().setValue("The game is about to start!");
        this.game = new QGameServer();
        QGameServer.current_round =0;
        loadRoundToGrid(0);
        prevRoundButton.setDisable(true);
        acceptButton.setDisable(true);
        declineButton.setDisable(true);
        backToQuestionsButton.setDisable(true);
        GameServerSocket.controller = this;
        System.out.println(GameServerSocket.controller==null);
        serverThread = new Thread(new GameServerSocket());
        serverThread.setDaemon(true);
        serverThread.start();
    }
    public void matchNamesToStored() {
        ArrayList<Label> playerlabels = new ArrayList<>();
        playerlabels.add(player1name);
        playerlabels.add(player2name);
        playerlabels.add(player3name);
        playerlabels.add(player4name);
        for (int i=0; i<GameServerSocket.ar.size(); i++)
        {
            playerlabels.get(i).textProperty().setValue(QGameServer.players.get(i).getPlayer_name());
        }
    }
    public void loadRoundToGrid(int round_num) {
        for (int i=0; i<this.game.game_package.getRound(round_num).getThemes().size(); i++)
        {
            Label theme_label = (Label) getNode(i,0);
            theme_label.textProperty().setValue(this.game.game_package.getRound(round_num).getTheme(i).getTheme_name());
            for (int j=0; j<this.game.game_package.getRound(round_num).getTheme(i).getQuestions().size(); j++)
            {
                if (this.game.questionGridMask.get(round_num).get(i).get(j)) {
                    Button question_button = (Button) getNode(i, j+1);
                    question_button.setDisable(false);
                    question_button.setText(Integer.toString(this.game.getQuestionCost(round_num,i,j)));
                    question_button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            game.current_question = QGameServer.game_package.getRound(round_num).getTheme(GridPane.getRowIndex(question_button)).getQuestion(GridPane.getColumnIndex(question_button)-1);
                            game.question_row_index = GridPane.getRowIndex(question_button);
                            game.question_col_index = GridPane.getColumnIndex(question_button)-1;
                            questionText.textProperty().setValue(game.current_question.getQuestion()+"\nAnswer: "+game.current_question.getAnswer());
                            questionPane.setVisible(true);
                            QGameServer.game_state = "questionasked";
                            nextRoundButton.setVisible(false);
                            prevRoundButton.setVisible(false);

                            GameServerSocket.sendQuestion();
                        }
                    });
                }
                else
                {
                    Button question_button = (Button) getNode(i, j+1);
                    //question_button.setText("");
                    question_button.setDisable(true);
                }
            }
            QGameServer.game_state = "grid";
            if (QGameServer.players.size()!=0) {
                view_state.textProperty().setValue("Round " + Integer.toString(QGameServer.current_round + 1) +
                        ". " + QGameServer.players.get(QGameServer.active_player_num).getPlayer_name() + ", your turn!");
            }
            else{
                view_state.textProperty().setValue("Round " + Integer.toString(QGameServer.current_round + 1));
            }
            GameServerSocket.sendRound();
            GameServerSocket.sendMask();
        }
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
    public void answerIsGiven() {
        acceptButton.setDisable(false);
        declineButton.setDisable(false);
        if (QGameServer.players.size()!=0) {
            view_state.textProperty().setValue(QGameServer.players.get(QGameServer.answering_player).player_name + " answers the question!");
        }
        else{
            view_state.textProperty().setValue("Please answer");
        }
        QGameServer.game_state = "answergiven";
        GameServerSocket.sendState();
    }
    public void acceptAnswer(ActionEvent e) {
        QGameServer.active_player_num = QGameServer.answering_player;
        if (QGameServer.answering_player == 0)
            player1score.increment(this.game.current_question.getCost());
        if (QGameServer.answering_player == 1)
            player2score.increment(this.game.current_question.getCost());
        if (QGameServer.answering_player == 2)
            player3score.increment(this.game.current_question.getCost());
        if (QGameServer.answering_player == 3)
            player4score.increment(this.game.current_question.getCost());
        questionText.textProperty().setValue(this.game.current_question.getAnswer());
        game.questionGridMask.get(game.current_round).get(QGameServer.question_row_index).set(QGameServer.question_col_index, false);
        if (checkIfRoundIsGoing()) {
            this.loadRoundToGrid(game.current_round);}
        GameServerSocket.updateScore(QGameServer.answering_player);
        if (QGameServer.players.size()!=0) {
            view_state.textProperty().setValue("You are right, "+QGameServer.players.get(QGameServer.answering_player).getPlayer_name()+"!");
        }
        else{
            view_state.textProperty().setValue("Right answer");
        }
        QGameServer.answering_player = -1;
        acceptButton.setDisable(true);
        declineButton.setDisable(true);
        GameServerSocket.sendAnswer();

        QGameServer.game_state = "answeraccepted";
        backToQuestionsButton.setDisable(false);
    }
    public void declineAnswer(ActionEvent e) {
        if (QGameServer.answering_player == 0)
            player1score.decrement(this.game.current_question.getCost());
        if (QGameServer.answering_player == 1)
            player2score.decrement(this.game.current_question.getCost());
        if (QGameServer.answering_player == 2)
            player3score.decrement(this.game.current_question.getCost());
        if (QGameServer.answering_player == 3)
            player4score.decrement(this.game.current_question.getCost());
        questionText.textProperty().setValue(this.game.current_question.getAnswer());
        game.questionGridMask.get(game.current_round).get(QGameServer.question_row_index).set(QGameServer.question_col_index, false);
        if (checkIfRoundIsGoing()) {this.loadRoundToGrid(game.current_round);}
        GameServerSocket.updateScore(QGameServer.answering_player);
        QGameServer.game_state = "answeraccepted";
        if (QGameServer.players.size()!=0) {
            view_state.textProperty().setValue("Sorry, "+QGameServer.players.get(QGameServer.answering_player).getPlayer_name()+", your answer is wrong!");
        }
            else{
            view_state.textProperty().setValue("Wrong answer");
        }
        QGameServer.answering_player = -1;


        acceptButton.setDisable(true);
        declineButton.setDisable(true);
        GameServerSocket.sendAnswer();
        backToQuestionsButton.setDisable(false);
    }
    public boolean checkIfRoundIsGoing() {
        boolean check = false;
        for (int i=0; i<game.questionGridMask.get(game.current_round).size(); i++)
        {
            for (int j=0; j< game.questionGridMask.get(game.current_round).get(i).size(); j++)
            {
                if (game.questionGridMask.get(game.current_round).get(i).get(j)==true)
                {
                    check = true;
                    return true;
                }
            }
        }
        if (check==false)
        {
            if (game.current_round == QGameServer.game_package.getRounds().size()-1)
            {
                int winner_index = 0;
                Vector<Integer> curscores = new Vector<>();
                curscores.add((int)player1score.getValue());
                curscores.add((int)player2score.getValue());
                curscores.add((int)player3score.getValue());
                curscores.add((int)player4score.getValue());
                for (int i=0; i<QGameServer.players.size(); i++)
                {
                    if (curscores.get(i)>curscores.get(winner_index)) {
                        winner_index = i;
                    }
                }
                view_state.textProperty().setValue(QGameServer.players.get(winner_index).getPlayer_name() + " is winner!");
                QGameServer.game_state = "gameover";
                GameServerSocket.sendState();
            }
            else
            {
                game.current_round +=1;
                this.loadRoundToGrid(game.current_round);
                int low_score_index = 0;
                Vector<Integer> curscores = new Vector<>();
                curscores.add((int)player1score.getValue());
                curscores.add((int)player2score.getValue());
                curscores.add((int)player3score.getValue());
                curscores.add((int)player4score.getValue());
                for (int i=0; i<QGameServer.players.size(); i++)
                {
                    if (curscores.get(i)<curscores.get(low_score_index)) {
                        low_score_index = i;
                    }
                }
                QGameServer.active_player_num = low_score_index;
                QGameServer.game_state = "grid";
                view_state.textProperty().setValue("Round " + Integer.toString(game.current_round) +
                        "! Choosing Player: "+QGameServer.players.get(low_score_index).getPlayer_name());
                GameServerSocket.sendMask();

            }
        }
        return false;
    }
    public void backToQuestionsAction(ActionEvent e) {
        QGameServer.current_question = null;
        QGameServer.game_state = "grid";
        questionPane.setVisible(false);
        questionText.textProperty().setValue("placeholder");
        backToQuestionsButton.setDisable(true);
        if (QGameServer.players.size()!=0) {
            view_state.textProperty().setValue(QGameServer.players.get(QGameServer.active_player_num).getPlayer_name()+", your turn!");
        }
        else{
            view_state.textProperty().setValue("Round " + Integer.toString(QGameServer.current_round + 1));
        }
        GameServerSocket.backToQuestions();
        QGameServer.game_state = "grid";
        nextRoundButton.setVisible(true);
        prevRoundButton.setVisible(true);
    }
    public void prevRoundAction(ActionEvent e) {
        this.game.current_round -= 1;
        if (this.game.current_round == 0)
        {
            this.prevRoundButton.setDisable(true);
        }
        this.nextRoundButton.setDisable(false);
        this.loadRoundToGrid(this.game.current_round);
        if (QGameServer.players.size()!=0) {
            view_state.textProperty().setValue("Round "+Integer.toString(QGameServer.current_round+1)+
                    ". "+QGameServer.players.get(QGameServer.active_player_num).getPlayer_name()+", your turn!");
        }
        else{
            view_state.textProperty().setValue("Round " + Integer.toString(QGameServer.current_round + 1));
        }
        QGameServer.game_state = "grid";
        GameServerSocket.sendMask();
    }
    public void nextRoundAction(ActionEvent e) {
        this.game.current_round +=1;
        if (this.game.current_round == this.game.game_package.getRounds().size()-1) {
            this.nextRoundButton.setDisable(true);
        }
        if (QGameServer.players.size()!=0) {
            view_state.textProperty().setValue("Round "+Integer.toString(QGameServer.current_round+1)+
                    ". "+QGameServer.players.get(QGameServer.active_player_num).getPlayer_name()+", your turn!");
        }
        else{
            view_state.textProperty().setValue("Round " + Integer.toString(QGameServer.current_round + 1));
        }

        this.prevRoundButton.setDisable(false);
        this.loadRoundToGrid(this.game.current_round);
        QGameServer.game_state = "grid";
        GameServerSocket.sendMask();
    }

    public void showQuestion(int i, int j) {
        Button qbutton = (Button) getNode(i,j+1);
        qbutton.fire();

    }
}
