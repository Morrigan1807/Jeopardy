package sample.gamepackage;

import java.io.Serializable;

public class PricedQuestion implements Serializable {
    private String question;
    private String answer;
    private int cost;

    public PricedQuestion(){
        question = "";
        answer = "";
        cost = 0;
    }
    public PricedQuestion(int new_cost){
        question = "";
        answer = "";
        cost = new_cost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
