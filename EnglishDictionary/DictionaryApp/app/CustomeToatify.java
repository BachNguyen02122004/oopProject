package DictionaryApp.app;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
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
        text.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: white; -fx-padding: 10px;");

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 5px; -fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 20px;");
        root.setOpacity(0);

        toastStage.setScene(new javafx.scene.Scene(root));
        toastStage.getScene().setFill(null);

        toastStage.show();

        FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);
        fadeInTransition.play();

        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), root);
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
        fadeOutTransition.setDelay(Duration.seconds(2));
        fadeOutTransition.setOnFinished(e -> toastStage.close());

        fadeInTransition.setOnFinished(e -> fadeOutTransition.play());
    }
}
