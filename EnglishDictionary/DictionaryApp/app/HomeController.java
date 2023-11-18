package DictionaryApp.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Pane helloViewPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadView("../view/hello-view.fxml");
    }

    private void loadView(String resourcePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            Parent view = loader.load();

            // Remove the previous view from the helloViewPane
            helloViewPane.getChildren().clear();

            // Add the new view to the helloViewPane
            helloViewPane.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearchSceneClick() {
        loadView("../view/hello-view.fxml");
    }

    @FXML
    private void handleAddImageClick() {
        loadView("../view/addWord.fxml");
    }

    @FXML
    private void handleTranslateImageClick() {
        loadView("../view/translate.fxml");
    }

    // game
    @FXML
    private void handleOpenBookImageClick() {

    }
}