package DictionaryApp.app;

import DictionaryApp.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SceneSwitch {
    @FXML
    private AnchorPane hello;
    @FXML
    private AnchorPane addWord;

    @FXML
    private AnchorPane translate;

    public SceneSwitch() {

    }


    public SceneSwitch(AnchorPane currentAnchorPane, String fxml) throws IOException {
        System.out.println(currentAnchorPane);
        AnchorPane nextAnchorPane =
                FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource(fxml)));
        System.out.println(nextAnchorPane.getId());
        currentAnchorPane.getChildren().removeAll();
        currentAnchorPane.getChildren().setAll(nextAnchorPane);
    }

    @FXML
    private void addWordScene(MouseEvent event) throws IOException {
        new SceneSwitch(hello, "./view/addWord.fxml");
    }

    @FXML
    public void searchScene(MouseEvent event) throws IOException {
        new SceneSwitch(addWord, "./view/hello-view.fxml");
    }

    @FXML
    public void translateScene(MouseEvent event) throws IOException {
        new SceneSwitch(hello, "./view/translate.fxml");
    }

    public void searchScene2(MouseEvent event) throws IOException {
        new SceneSwitch(translate, "./view/hello-view.fxml");
    }
}
