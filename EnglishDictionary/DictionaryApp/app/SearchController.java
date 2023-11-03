package DictionaryApp.app;

import DictionaryCommand.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import org.w3c.dom.Text;

//import javax.swing.text.html.ImageView;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchController {
    @FXML
    TextField searchTF;
    @FXML
    ListView<String> wordListView;
    @FXML
    TextArea definitionListView;

    private ObservableList<String> allWords;
    private Map<String, String> wordToDefinitionMap = new HashMap<>();

    @FXML
    private ImageView changeDef;

    @FXML
    public void initialize() {
        definitionListView.setEditable(false);
        allWords = FXCollections.observableArrayList();
        loadWordsFromFile("EnglishDictionary/resources/dictionaries.txt");
        wordListView.setItems(allWords);
        // edit color word_search
        wordListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);

                        if (item != null && !empty) {
                            if (isSelected()) {
                                // color select_item
                                setBackground(Background.fill(Color.rgb(131, 233, 247)));
                                setTextFill(Color.BLACK);
                            } else {
                                setBackground(Background.fill(Color.rgb(237, 238, 240)));
                            }
                        }
                    }
                };
            }
        });

        wordListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showWordDefinition(newValue);
        });
        searchTF.setOnKeyReleased(this::handleSearch);

        // Bắt sự kiện click cho ImageView
        changeDef.setOnMouseClicked(event -> handlePencilClick());
    }

    @FXML
    private void handlePencilClick() {
        definitionListView.setEditable(true);
    }

    // Bắt sự kiện người dùng nhập vào(Hàm update)
    @FXML
    private void handleSaveClick() {
        String selectedWord = wordListView.getSelectionModel().getSelectedItem(); // lấy từ được chọn
        String updatedDefinition = definitionListView.getText(); // lấy dữ liệu người dùng nhập
        definitionListView.setEditable(false);

        if (selectedWord != null) {
            wordToDefinitionMap.put(selectedWord, updatedDefinition); // push từ chọn và sự thay đổi vào map
            saveWordsToFile();

            CustomeToatify.showToast((Stage) searchTF.getScene().getWindow(), "Đã lưu thay đổi!");
        }
    }

    // render lại từ điển
    private void saveWordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("EnglishDictionary/resources/dictionaries.txt"))) {
            for (Map.Entry<String, String> entry : wordToDefinitionMap.entrySet()) {
                writer.write("|" + entry.getKey() + "\n");
                writer.write(entry.getValue() + "\n\n");
            }
        } catch (IOException e) {
            System.out.println("Có lỗi xảy ra");
        }
    }

    public void loadWordsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word_target = "";
            StringBuilder word_explain = new StringBuilder();
            String wordLine;

            while ((wordLine = reader.readLine()) != null) {
                if (wordLine.startsWith("|")) {
                    if (!word_target.isEmpty()) {
                        Word newWord = new Word(word_target, word_explain.toString().trim());
                        allWords.add(newWord.getWord_target());
                        wordToDefinitionMap.put(newWord.getWord_target(), newWord.getWord_explain());
                    }

                    word_target = wordLine.replace("|", "").trim();
                    word_explain = new StringBuilder();
                } else {
                    word_explain.append(wordLine).append("\n");
                }
            }

            if (!word_target.isEmpty()) {
                Word newWord = new Word(word_target, word_explain.toString().trim());
                allWords.add(newWord.getWord_target());
                wordToDefinitionMap.put(newWord.getWord_target(), newWord.getWord_explain());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSearch(KeyEvent keyEvent) {
        String searchText = searchTF.getText().toLowerCase();
        List<String> filteredWords = wordToDefinitionMap.keySet().stream()
                .filter(word -> word.toLowerCase().startsWith(searchText))
                .collect(Collectors.toList());
        wordListView.setItems(FXCollections.observableArrayList(filteredWords));
    }

    private void showWordDefinition(String word) {
        if (word != null && wordToDefinitionMap.containsKey(word)) {
            String definition = wordToDefinitionMap.get(word);
            definitionListView.setText(definition);
        }
    }

    @FXML
    private void handleEraser(MouseEvent event) {
        String selectedWord = wordListView.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Remove");
            confirm.setHeaderText("You're deleting a word");
            confirm.setContentText("Are you sure?");
            if (confirm.showAndWait().get() == ButtonType.OK) {
                wordToDefinitionMap.remove(selectedWord);
                saveWordsToFile();
            }
        }
    }
}