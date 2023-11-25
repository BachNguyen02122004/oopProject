    package DictionaryApp.app;

    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.media.Media;
    import javafx.scene.media.MediaPlayer;
    import javafx.stage.Stage;

    import java.io.*;
    import java.net.URL;
    import java.util.*;

    public class GameController extends FamousPeople implements Initializable  {
        @FXML
        private ListView<String> prize; // Sử dụng ListView<String>

        @FXML
        private AnchorPane mainPane;
        private ObservableList<String> prizeList = FXCollections.observableArrayList();

        @FXML
        private Button callAPs;

        @FXML
        private Button audiencesBtn;
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

        private int activeIndex;//  nghĩa là không có phần tử nào được chọn ban đầu

        private ArrayList<ArrayList<String>> questions = new ArrayList<>();

        protected boolean isGameOver = false;
        public boolean win = false;
        @FXML
        private ImageView halfHelp;
        @FXML
        private Button priceCollect;

        @FXML
        private ImageView changeQuestionHelp;

        private boolean halfHelpUsed = false; // Biến này kiểm tra xem halfHelp đã được sử dụng hay chưa


        private boolean ChangeQuestionHelpUsed = false; // Biến này kiểm tra xem ChangeQuestionHelpUsed đã được sử dụng hay chưa

        private Set<Integer> usedQuestions = new HashSet<>(); // Tập hợp này lưu trữ các chỉ số của câu hỏi đã được sử dụng

        
        public void addPrize() {
            prizeList.addAll(
                    "1. 200.000", "2. 400.000", "3. 600.000", "4. 1.000.000",
                    "5. 2.000.000", "6. 3.000.000", "7. 6.000.000", "8. 10.000.000",
                    "9. 14.000.000", "10. 22.000.000", "11. 30.000.000",
                    "12. 40.000.000", "13. 60.000.000", "14. 85.000.000", "15. 150.000.000"
            );
            Collections.reverse(prizeList);
            activeIndex = prizeList.size() - 1;
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


            choiceA.setOnMouseClicked(event -> {
                if (showConfirm()) {
                    try {
                        update(choiceA);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            choiceB.setOnMouseClicked(event -> {
                if (showConfirm()) {
                    try {
                        update(choiceB);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            choiceC.setOnMouseClicked(event -> {
                if (showConfirm()) {
                    try {
                        update(choiceC);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            choiceD.setOnMouseClicked(event -> {
                if (showConfirm()) {
                    try {
                        update(choiceD);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });


            halfHelp.setOnMouseClicked(event -> useHalfHelp());

            changeQuestionHelp.setOnMouseClicked(event -> useChangeQuestionHelp());
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

        // Phương thức để hiển thị hộp thoại xác nhận
        private boolean showConfirm() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác Nhận");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn chọn đáp án này không?");

            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }

        // Hàm thêm âm thanh
        private void playSound(String resourcePath) {
            String musicFile = resourcePath;
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(mediaPlayer.getStatus());
            mediaPlayer.play();

        }

        public void update(TextField choice) throws IOException {

            if (!isGameOver) {
                if (!choice.getText().substring(0, 1).equals(key)) {
                    isGameOver = true;
                    playSound("D:/oopProject/EnglishDictionary/DictionaryApp/media/traloisai.mp3");
                    // Show game over screen
                  gameStatus("../view/gameOver.fxml");
                } else {

                    if (!prizeList.isEmpty()) {
                        activeIndex--;
                        if(prizeList.get(activeIndex + 1).substring(1, 1).equals('.')) {
                            priceCollect.setText(String.valueOf(prizeList.get(activeIndex + 1)).substring(2));
                        }
                        else {
                            priceCollect.setText(String.valueOf(prizeList.get(activeIndex + 1)).substring(3));
                        }
                        if (activeIndex < 0) {
                            activeIndex = 0;
                            win = true;
                            playSound("D:/oopProject/EnglishDictionary/DictionaryApp/media/horeo.mp3");
                            // Show game over screen with win state
                            gameStatus("../view/winGame.fxml");
                        }
                        if(win == false) {
                            playSound("D:/oopProject/EnglishDictionary/DictionaryApp/media/traloidung.mp3");
                        }
                        prize.refresh();
                    }

                        setQuestion();
                }
            }
        }

        public void changeQuestion(){
            Random rand = new Random();
            int randomNumber;
            do {
                randomNumber = rand.nextInt(questions.size());
            } while (usedQuestions.contains(randomNumber)); // Lặp cho đến khi tìm thấy một câu hỏi chưa được sử dụng

            setQuestion(randomNumber); // Sử dụng câu hỏi mới
        }

        public void setQuestion(int questionIndex) {
            // Thêm chỉ số của câu hỏi vào tập hợp các câu hỏi đã sử dụng
            usedQuestions.add(questionIndex);

            ArrayList<String> selectedQuestion = questions.get(questionIndex);
            questionField.setText(selectedQuestion.get(0));
            choiceA.setText(selectedQuestion.get(1));
            choiceB.setText(selectedQuestion.get(2));
            choiceC.setText(selectedQuestion.get(3));
            choiceD.setText(selectedQuestion.get(4));
            key = selectedQuestion.get(5);

            resetChoices();
        }

        // Cập nhật phương thức setQuestion ban đầu để sử dụng setQuestion mới với chỉ số ngẫu nhiên
        public void setQuestion() {
            Random rand = new Random();
            int questionIndex;
            do {
                questionIndex = rand.nextInt(questions.size());
            } while (usedQuestions.contains(questionIndex)); // Kiểm tra nếu câu hỏi đã được sử dụng

            setQuestion(questionIndex);
        }

        public void resetChoices() {
            // Làm cho tất cả các lựa chọn trở nên hiển thị trở lại
            choiceA.setVisible(true);
            choiceB.setVisible(true);
            choiceC.setVisible(true);
            choiceD.setVisible(true);
        }

        public void removeHalfQuestion() {
            List<TextField> choices = Arrays.asList(choiceA, choiceB, choiceC, choiceD);
            Collections.shuffle(choices); // Trộn ngẫu nhiên thứ tự để loại bỏ trở nên ngẫu nhiên

            TextField correctChoice = null;
            for (TextField choice : choices) {
                if (choice.getText().substring(0, 1).equals(key)) {
                    correctChoice = choice;
                    break;
                }
            }

            int removedChoices = 0;
            for (TextField choice : choices) {
                if (choice != correctChoice && removedChoices < 2) {
                    choice.setVisible(false);
                    removedChoices++;
                }
            }
        }

        public void useHalfHelp() {
            if (!halfHelpUsed) {
                removeHalfQuestion();
                halfHelpUsed = true; // Đánh dấu rằng halfHelp đã được sử dụng
                halfHelp.getStyleClass().add("helpOpacity");
            }
        }

        public void useChangeQuestionHelp(){
            if(!ChangeQuestionHelpUsed){
                changeQuestion();
                ChangeQuestionHelpUsed = true; // Đánh dấu rằng đã được sử dụng
                changeQuestionHelp.getStyleClass().add("helpOpacity");
            }
        }

        public void handleClickCall() {

            System.out.println("Click thành công");
            CustomeToatify.showToast((Stage) questionField.getScene().getWindow(), getFamousPs() + " đã chọn phương án: " + key);
            callAPs.setDisable(true);


            callAPs.setPickOnBounds(false);
        }


        public void ConsultingHandler() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tổ tư vấn tại chỗ");
            alert.setHeaderText("Kết quả của khán giả lựa chọn:");

            String[] optionLabels = {"Đáp án A: ", "Đáp án B: ", "Đáp án C: ", "Đáp án D: "};
            String[] checkKey = {"A", "B", "C", "D"};
            int[] optionPercentages = {0, 0, 0, 0};

            Random random = new Random();
            int index = 0;
            for (String x : checkKey) {
                if (x.equals(key)) {
                    break;
                }
                index++;
            }
            // tìm vị trí đa đúng
            int correctOptionIndex = index;
            System.out.println(correctOptionIndex);

            int totalPercent = 100;
            int highestPercent = random.nextInt(51) + 50; // Cho đa đúng  lớn hơn 50%
            optionPercentages[correctOptionIndex] = highestPercent;

            for (int i = 0; i < 4; i++) {
                if (i != correctOptionIndex) {
                    optionPercentages[i] = random.nextInt(totalPercent - highestPercent); // Phần trăm cho các đáp án khác
                    totalPercent -= optionPercentages[i];
                }
            }

            StringBuilder contentText = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                contentText.append(optionLabels[i])
                        .append(" (").append(optionPercentages[i]).append("%)").append("\n");
            }

            alert.setContentText(contentText.toString());

            // show alert
            alert.showAndWait();

            audiencesBtn.setDisable(true);

        }

        public void gameStatus(String path) throws IOException {
            // Xóa trạng thái cũ
            System.out.println(isGameOver);
            Stage currentStage = (Stage) mainPane.getScene().getWindow();
            currentStage.close(); // Đóng cửa sổ hiện tại
            // Tạo Stage mới cho giao diện GameOver.fxml
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }
