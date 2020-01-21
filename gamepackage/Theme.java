package sample.gamepackage;

import java.io.Serializable;
import java.util.ArrayList;

public class Theme implements Serializable {
    private ArrayList<PricedQuestion> questions;
    private String theme_name;

    public Theme(){
        theme_name = "";
    }
    public ArrayList<PricedQuestion> getQuestions() {
        return questions;
    }
    public PricedQuestion getQuestion(int index) { return questions.get(index);};
    public void setQuestions(ArrayList<PricedQuestion> questions) {
        this.questions = questions;
    }

    public String getTheme_name() {
        return theme_name;
    }

    public void setTheme_name(String theme_name) {
        this.theme_name = theme_name;
    }
}
