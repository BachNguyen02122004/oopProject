package DictionaryApp.app;

import DictionaryApp.model.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.io.IOException;

import static DictionaryApp.app.SearchController.*;

public class AddController {
    @FXML
    AnchorPane addWord;
    @FXML
    TextField newWord;
    @FXML
    TextArea newWordDef;

    @FXML
    public void searchScene(MouseEvent event) throws IOException {
        new SceneSwitch(addWord, "./view/hello-view.fxml");
    }

    @FXML
    private void submitAdd(ActionEvent event) {
        String newAddWord = newWord.getText();
        String newAddWordDef = newWordDef.getText();
        wordToDefinitionMap.put(newAddWord, newAddWordDef);
        saveWordsToFile();
    }
}
