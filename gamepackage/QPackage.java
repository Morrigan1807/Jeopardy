package sample.gamepackage;

import java.io.Serializable;
import java.util.ArrayList;

public class QPackage implements Serializable {
    private ArrayList<QTableRound> rounds;
    private String creator;
    private String pack_name;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPack_name() {
        return pack_name;
    }

    public void setPack_name(String pack_name) {
        this.pack_name = pack_name;
    }

    public ArrayList<QTableRound> getRounds() {
        return rounds;
    }
    public QTableRound getRound(int index) {return rounds.get(index);}
    public void setRounds(ArrayList<QTableRound> rounds) {
        this.rounds = rounds;
    }
}
