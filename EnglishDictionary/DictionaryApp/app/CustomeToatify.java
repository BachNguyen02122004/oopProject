package DictionaryApp.app;

import javafx.animation.FadeTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class CustomeToatify {
    public static void showToast(Stage stage, String message) {
        Stage toastStage = new Stage();
        toastStage.initOwner(stage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label text = new Label(message);
        text.setStyle("-fx-text-fill: white;");

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 5px; -fx-background-color: rgba(9,158,222,0.87); -fx-padding: 20px; -fx-font-size: 17px");
        root.setOpacity(0.6);

        toastStage.setScene(new Scene(root));
        toastStage.getScene().setFill(null);
//        toastStage.setX(844);
//        toastStage.setY(569);

        toastStage.show();

        FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);
        fadeInTransition.play();

        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), root);
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
        fadeOutTransition.setDelay(Duration.seconds(1));
        fadeOutTransition.setOnFinished(e -> toastStage.close());

        fadeInTransition.setOnFinished(e -> fadeOutTransition.play());
    }
}
