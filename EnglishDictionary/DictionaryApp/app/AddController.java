package DictionaryApp.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static DictionaryApp.app.SearchController.*;

public class AddController extends SceneSwitch implements Initializable {
    @FXML
    AnchorPane addWord;
    @FXML
    TextField newWord;
    @FXML
    TextArea newWordDef;
    @FXML
    Button submitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitButton.setDisable(true);
        newWord.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (newWord.getText().isEmpty() || newWordDef.getText().isEmpty())
                    submitButton.setDisable(true);
                else submitButton.setDisable(false);
            }
        });

        newWordDef.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (newWord.getText().isEmpty() || newWordDef.getText().isEmpty())
                    submitButton.setDisable(true);
                else submitButton.setDisable(false);
            }
        });
    }


    @FXML
    private void submitAdd(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Add");
        confirm.setHeaderText("You're adding a word");
        confirm.setContentText("Are you sure?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            String newAddWord = newWord.getText().trim();
            String newAddWordDef = "- " +  newWordDef.getText().trim();
            wordToDefinitionMap.put(newAddWord, newAddWordDef);
            saveWordsToFile();
        }
    }
}
