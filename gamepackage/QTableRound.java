package sample.gamepackage;

import java.io.Serializable;
import java.util.ArrayList;

public class QTableRound implements Serializable {
    private ArrayList<Theme> themes;

    public ArrayList<Theme> getThemes() {
        return themes;
    }
    public Theme getTheme(int index) {return themes.get(index);}
    public void setThemes(ArrayList<Theme> themes) {
        this.themes = themes;
    }
}
