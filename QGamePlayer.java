package sample;

import javafx.scene.image.Image;

public class QGamePlayer {
    public String player_name;
    public Image player_img;
    //public int score;
    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public Image getPlayer_img() {
        return player_img;
    }

    public void setPlayer_img(Image player_img) {
        this.player_img = player_img;
    }
    /*
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

     */
}
