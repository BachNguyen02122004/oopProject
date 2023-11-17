package DictionaryApp.app;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.util.ResourceBundle;

public class Translate extends SceneSwitch implements Initializable {

    @FXML
    private AnchorPane translate;

    @FXML
    private TextArea userInput;

    @FXML
    private TextArea userTranslate;

    @FXML
    private ComboBox langFrom;

    @FXML
    private ComboBox langTo;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        userTranslate.setEditable(false);
    }


    private static String translate(String langFrom, String langTo, String text) throws IOException {
        String urlStr = "https://script.google.com/macros/s/AKfycbyaLMzgcYq4DEA02vlHasaOgKtgWQRy5fKjztUk6JQZ7i-7L4tb4MCeG9DZ0snlAMBofQ/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    @FXML
    private void onTranslateButtonClick() {
        try {
            String inputText = userInput.getText();

            String sourceLangValue = (String) langFrom.getValue() == null ? "English" : (String) langFrom.getValue();
            String targetLangValue = (String) langTo.getValue() == null ? "Vietnamese" : (String) langTo.getValue();

            System.out.println(sourceLangValue);
            System.out.println(targetLangValue);


            String sourceLang = "";
            String targetLang = "";

            if (sourceLangValue.equals("English")) {
                sourceLang = "en";
            } else if (sourceLangValue.equals("Vietnamese")) {
                sourceLang = "vi";
            } else {
                sourceLang = "ja";
            }

            if (targetLangValue.equals("English")) {
                targetLang = "en";
            } else if (targetLangValue.equals("Vietnamese")) {
                targetLang = "vi";
            } else {
                targetLang = "ja";
            }

            String translatedText = translate(sourceLang, targetLang, inputText);

            if (sourceLangValue.equals(targetLangValue)) {
                userTranslate.setText("Lỗi: Ngôn ngữ nguồn và ngôn ngữ đích không được giống nhau.");
                userTranslate.setStyle("-fx-text-fill: red; -fx-font-style: italic normal;");
            } else {
                userTranslate.setStyle("-fx-text-fill: black; -fx-font-style: normal;");

                userTranslate.setText(translatedText);
            }

        } catch (IOException e) {
            userTranslate.setText("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            userTranslate.setText("Lỗi không xác định: " + e.getMessage());
        }
    }
}

//    @FXML
//    private void onTranslateButtonClick() {
//        String inputText = userInput.getText();
//        String sourceLang = getLanguageCode(langFrom.getValue(), "en");
//        String targetLang = getLanguageCode(langTo.getValue(), "vi");
//
//        if (sourceLang.equals(targetLang)) {
//            userTranslate.setText("Lỗi: Ngôn ngữ nguồn và ngôn ngữ đích không được giống nhau.");
//            userTranslate.setStyle("-fx-text-fill: red; -fx-font-style: italic normal;");
//            return;
//        }
//        userTranslate.setStyle("-fx-text-fill: black; -fx-font-style: normal;");
//
//        Task<String> translateTask = new Task<>() {
//            @Override
//            protected String call() throws IOException {
//                return translate(sourceLang, targetLang, inputText);
//            }
//        };
//
//        translateTask.setOnSucceeded(e -> userTranslate.setText(translateTask.getValue()));
//        translateTask.setOnFailed(e -> userTranslate.setText("Lỗi: " + translateTask.getException().getMessage()));
//
//        new Thread(translateTask).start();
//    }
//
//    private String getLanguageCode(Object langValue, String defaultLang) {
//        if (langValue == null) {
//            return defaultLang;
//        }
//
//        switch (langValue.toString()) {
//            case "English":
//                return "en";
//            case "Vietnamese":
//                return "vi";
//            case  "Chinese":
//                return "zh";
//            default:
//                return "ja";
//        }
//    }
//}