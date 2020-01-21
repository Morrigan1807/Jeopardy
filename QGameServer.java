package sample;

import sample.gamepackage.PricedQuestion;
import sample.gamepackage.QPackage;

import java.util.ArrayList;

public class QGameServer {
    public static QPackage game_package;
    public static ArrayList<ArrayList<ArrayList<Boolean>>> questionGridMask;
    public static int current_round;
    public static String game_state;
    public static boolean game_is_paused;
    public static ArrayList<QGamePlayer> players;
    public static PricedQuestion current_question;
    public static int question_row_index;
    public static int question_col_index;
    public static int active_player_num;
    public static int answering_player;
    public QGameServer()
    {
        questionGridMask = new ArrayList<>();
        for (int i=0; i<game_package.getRounds().size(); i++)
        {
            questionGridMask.add(new ArrayList<>());
            for (int j=0; j<game_package.getRound(i).getThemes().size(); j++)
            {
                questionGridMask.get(i).add(new ArrayList<>());
                for (int k=0; k<game_package.getRound(i).getTheme(j).getQuestions().size(); k++)
                {
                    questionGridMask.get(i).get(j).add(true);
                }
            }
        }
        current_round = 0;
        game_state = "start";
        game_is_paused = true;
        players = new ArrayList<>();
        active_player_num = 0;
        answering_player = -1;
    }
    public QGameServer(QPackage new_package)
    {
        this.game_package = new_package;
        questionGridMask = new ArrayList<>();
        for (int i=0; i<game_package.getRounds().size(); i++)
        {
            questionGridMask.add(new ArrayList<>());
            for (int j=0; j<game_package.getRound(i).getThemes().size(); j++)
            {
                questionGridMask.get(i).add(new ArrayList<>());
                for (int k=0; k<game_package.getRound(i).getTheme(j).getQuestions().size(); k++)
                {
                    questionGridMask.get(i).get(j).add(true);
                }
            }
        }
        current_round = 0;
        game_state = "start";
        game_is_paused = true;
        players = new ArrayList<>();
    }
    public String getQuestionText(int round, int theme, int question)
    {
        return this.game_package.getRound(round).getTheme(theme).getQuestion(question).getQuestion();
    }
    public String getQuestionAnswer(int round, int theme, int question)
    {
        return this.game_package.getRound(round).getTheme(theme).getQuestion(question).getAnswer();
    }
    public int getQuestionCost(int round, int theme, int question)
    {
        return this.game_package.getRound(round).getTheme(theme).getQuestion(question).getCost();
    }
}
