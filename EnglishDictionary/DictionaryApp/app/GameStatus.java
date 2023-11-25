package DictionaryApp.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameStatus  {

    @FXML
    private AnchorPane mainOver;

    @FXML
    private ImageView gameImage;
    @FXML
    private Button prizeCollect;

    public void hanldePlayAgain() throws IOException {
        Stage currentStage = (Stage) mainOver.getScene().getWindow();
        currentStage.close();


        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/game.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.show();
    }



    public void handlequit(){
        Stage currentStage = (Stage) mainOver.getScene().getWindow();
        currentStage.close();
    }





}
