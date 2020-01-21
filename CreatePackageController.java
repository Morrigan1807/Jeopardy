package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import sample.gamepackage.PricedQuestion;
import sample.gamepackage.QPackage;
import sample.gamepackage.QTableRound;
import sample.gamepackage.Theme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CreatePackageController {

    public AnchorPane mainPane;

    public QPackage new_package;

    public TextArea question1;
    public TextArea question2;
    public TextArea question3;
    public TextArea question4;
    public TextArea question5;
    public TextArea answer1;
    public TextArea answer2;
    public TextArea answer3;
    public TextArea answer4;
    public TextArea answer5;
    public TextArea cost1;
    public TextArea cost2;
    public TextArea cost3;
    public TextArea cost4;
    public TextArea cost5;

    public TextField creator_name;
    public TextField package_name;
    public ComboBox<String> theme_list;
    public ComboBox<String> round_list;
    public TextField theme_name;

    @FXML
    public void initialize(){
        ObservableList<String> t_list = FXCollections.observableArrayList("Theme 1", "Theme 2", "Theme 3", "Theme 4", "Theme 5", "Theme 6");
        theme_list.setItems(t_list);
        ObservableList<String> r_list;
        r_list = FXCollections.observableArrayList("Round 1", "Round 2", "Round 3");
        round_list.setItems(r_list);
        theme_list.setValue(t_list.get(0));
        round_list.setValue(r_list.get(0));
        cost1.textProperty().setValue("100");
        cost2.textProperty().setValue("200");
        cost3.textProperty().setValue("300");
        cost4.textProperty().setValue("400");
        cost5.textProperty().setValue("500");

        //initialise package
        new_package = new QPackage();
        new_package.setRounds(new ArrayList<QTableRound>());
        for (int i = 0; i < 3; i++){
            new_package.getRounds().add(new QTableRound());
            new_package.getRound(i).setThemes(new ArrayList<Theme>());
            for (int j = 0; j < 6; j++)
            {
                new_package.getRound(i).getThemes().add(new Theme());
                new_package.getRound(i).getTheme(j).setQuestions(new ArrayList<PricedQuestion>());
                for (int k = 0; k < 5; k++){
                    new_package.getRound(i).getTheme(j).getQuestions().add(new PricedQuestion((i+1)*(k+1)*100));
                }
            }
        }
        question1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                        theme_list.getSelectionModel().getSelectedIndex()).getQuestion(0).setQuestion(newValue);
            }
        });
        question2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                        theme_list.getSelectionModel().getSelectedIndex()).getQuestion(1).setQuestion(newValue);
            }
        });
        question3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                        theme_list.getSelectionModel().getSelectedIndex()).getQuestion(2).setQuestion(newValue);
            }
        });
        question4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                        theme_list.getSelectionModel().getSelectedIndex()).getQuestion(3).setQuestion(newValue);
            }
        });
        question5.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                        theme_list.getSelectionModel().getSelectedIndex()).getQuestion(4).setQuestion(newValue);
            }
        });
        answer1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                        theme_list.getSelectionModel().getSelectedIndex()).getQuestion(0).setAnswer(newValue);
            }
        });
        answer2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                        theme_list.getSelectionModel().getSelectedIndex()).getQuestion(1).setAnswer(newValue);
            }
        });
        answer3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                        theme_list.getSelectionModel().getSelectedIndex()).getQuestion(2).setAnswer(newValue);
            }
        });
        answer4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                        theme_list.getSelectionModel().getSelectedIndex()).getQuestion(3).setAnswer(newValue);
            }
        });
        answer5.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                        theme_list.getSelectionModel().getSelectedIndex()).getQuestion(4).setAnswer(newValue);
            }
        });
        cost1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                try {
                    new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                            theme_list.getSelectionModel().getSelectedIndex()).getQuestion(0).setCost(Integer.parseInt(newValue));
                }
                catch (Exception e) { }
            }
        });
        cost2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                try {
                    new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                            theme_list.getSelectionModel().getSelectedIndex()).getQuestion(1).setCost(Integer.parseInt(newValue));
                }
                catch (Exception e) { }
            }
        });
        cost3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                try {
                    new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                            theme_list.getSelectionModel().getSelectedIndex()).getQuestion(2).setCost(Integer.parseInt(newValue));
                }
                catch (Exception e) { }
            }
        });
        cost4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                try {
                    new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                            theme_list.getSelectionModel().getSelectedIndex()).getQuestion(3).setCost(Integer.parseInt(newValue));
                }
                catch (Exception e) { }
            }
        });
        cost5.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                try {
                    new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).getTheme(
                            theme_list.getSelectionModel().getSelectedIndex()).getQuestion(4).setCost(Integer.parseInt(newValue));
                }
                catch (Exception e) { }
            }
        });
        theme_name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                        getTheme(theme_list.getSelectionModel().getSelectedIndex()).setTheme_name(t1);
            }
        });
        creator_name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.setCreator(newValue);
            }
        });
        package_name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                new_package.setPack_name(newValue);
            }
        });

        round_list.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
               // question1.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()));
            }
        });
    }
    @FXML
    public void roundthemeChanged(ActionEvent event)    {
        theme_name.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getTheme_name());
        question1.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(0).getQuestion());
        question2.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(1).getQuestion());
        question3.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(2).getQuestion());
        question4.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(3).getQuestion());
        question5.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(4).getQuestion());
        answer1.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(0).getAnswer());
        answer2.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(1).getAnswer());
        answer3.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(2).getAnswer());
        answer4.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(3).getAnswer());
        answer5.textProperty().setValue(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(4).getAnswer());
        cost1.textProperty().setValue(Integer.toString(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(0).getCost()));
        cost2.textProperty().setValue(Integer.toString(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(1).getCost()));
        cost3.textProperty().setValue(Integer.toString(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(2).getCost()));
        cost4.textProperty().setValue(Integer.toString(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(3).getCost()));
        cost5.textProperty().setValue(Integer.toString(new_package.getRound(round_list.getSelectionModel().getSelectedIndex()).
                getTheme(theme_list.getSelectionModel().getSelectedIndex()).getQuestion(4).getCost()));
    }
    public void save_package_button(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Jeopardy package (.jqp)", "*.jqp");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(creator_name.getScene().getWindow());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.getAbsoluteFile()));
            oos.writeObject(new_package);
            oos.close();
        } catch (Exception e){}
    }
}
