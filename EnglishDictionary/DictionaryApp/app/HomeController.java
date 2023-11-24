package DictionaryApp.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/game.fxml"));
            Parent popupView = fxmlLoader.load();

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.setTitle("Game");
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Set the scene for the stage and show it
            popupStage.setScene(new Scene(popupView, 1290, 680));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}