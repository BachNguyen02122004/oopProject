package DictionaryApp.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {
    @FXML
    private ListView<String> prize; // Sử dụng ListView<String>

    private ObservableList<String> prizeList = FXCollections.observableArrayList();

    @FXML
    private Button testBtn;

    @FXML
    private TextArea questionField;

    @FXML
    private TextField choiceA;
    @FXML
    private TextField choiceB;
    @FXML
    private TextField choiceC;
    @FXML
    private TextField choiceD;

    public String key;

    private int activeIndex = -1; // -1 nghĩa là không có phần tử nào được chọn ban đầu

    private ArrayList<ArrayList<String>> questions = new ArrayList<>();

    public boolean isGameOver = false;

    public void addPrize() {
        prizeList.addAll(
                "1. 200.000", "2. 400.000", "3. 600.000", "4. 1.000.000",
                "5. 2.000.000", "6. 3.000.000", "7. 6.000.000", "8. 10.000.000",
                "9. 14.000.000", "10. 22.000.000", "11. 30.000.000",
                "12. 40.000.000", "13. 60.000.000", "14. 85.000.000", "15. 150.000.000"
        );
        Collections.reverse(prizeList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPrize();
        prize.setItems(prizeList);
        questionField.setEditable(false);
        insertQuestion("EnglishDictionary/resources/gameApp.txt");
        setQuestion();
        choiceA.setEditable(false);
        choiceB.setEditable(false);
        choiceC.setEditable(false);
        choiceD.setEditable(false);

        prize.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    getStyleClass().remove("active");
                } else {
                    setText(item);
                    // Quản lý class active dựa trên activeIndex
                    if (getIndex() == activeIndex) {
                        if (!getStyleClass().contains("active")) {
                            getStyleClass().add("active");
                        }
                    } else {
                        getStyleClass().remove("active");
                    }
                }
            }

        });

        testBtn.setOnAction(actionEvent -> {
            if (!prizeList.isEmpty()) {
                // Cập nhật activeIndex
                activeIndex++;
                if (activeIndex >= prizeList.size()) {
                    activeIndex = 0;
                }
                // Làm mới ListView để áp dụng thay đổi
                prize.refresh();
            }
        });

        choiceA.setOnMouseClicked(event -> {
            System.out.println("A");
            update(choiceA);
        });
        choiceB.setOnMouseClicked(event -> {
            System.out.println("B");

            update(choiceB);
        });
        choiceC.setOnMouseClicked(event -> {
            System.out.println("C");

            update(choiceC);
        });
        choiceD.setOnMouseClicked(event -> {
            System.out.println("D");

            update(choiceD);
        });
    }

    public void insertQuestion(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
//            ArrayList<ArrayList<String>> questions = new ArrayList<>();
            String question = "";
            List<String> answer = new ArrayList<>();
            String key = "";
            String wordLine;
            int i = 0;
            while ((wordLine = br.readLine()) != null) {
                if (i % 6 == 0) {
                    question = wordLine;
                } else if (i % 6 != 5) {
                    answer.add(wordLine);
                } else {
                    key = wordLine;
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add(question);
                    for (String a : answer) {
                        tmp.add(a);
                    }
                    tmp.add(key);
                    questions.add(tmp);
                    answer.clear();
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(TextField choice) {
        if (!isGameOver) {
            if (!choice.getText().substring(0, 1).equals(key)) {
                isGameOver = true;
                CustomeToatify.showToast((Stage) questionField.getScene().getWindow(), "Bạn đã thua vui lòng khởi động lại trò chơi");

            } else {
                setQuestion();
            }
        }
    }

    public void setQuestion() {
        Random rand = new Random();
        int maxRand = questions.size();
        int randomNumber = rand.nextInt(maxRand);
        questionField.setText(questions.get(randomNumber).get(0));
        choiceA.setText(questions.get(randomNumber).get(1));
        choiceB.setText(questions.get(randomNumber).get(2));
        choiceC.setText(questions.get(randomNumber).get(3));
        choiceD.setText(questions.get(randomNumber).get(4));
        key = questions.get(randomNumber).get(5);
        questions.remove(randomNumber);
//        for (ArrayList<String> q : questions) {
//            for (String a : q) {
//                System.out.println(a);
//            }
//        }
    }

}
