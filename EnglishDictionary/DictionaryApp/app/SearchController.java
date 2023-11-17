package DictionaryApp.app;

import DictionaryCommand.Dictionary;
import DictionaryCommand.DictionaryManagement;
import DictionaryCommand.Word;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.*;

public class SearchController extends SceneSwitch implements Initializable {
    @FXML
    TextField searchTF;
    @FXML
    ListView<String> wordListView;
    @FXML
    TextArea definitionListView;
    @FXML
    AnchorPane hello;

    private final String path = "EnglishDictionary/resources/dictionaries.txt";

//    public static ObservableList<String> allWords;
//    public List<Word> wordToDefinitionMap = new ArrayList<>();

    public static DictionaryManagement dictionaryManagement = new DictionaryManagement();


    @FXML
    private ImageView changeDef;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        definitionListView.setEditable(false);
//        allWords = FXCollections.observableArrayList();
        if (dictionaryManagement.getAllWords().isEmpty()) {
            dictionaryManagement.insertFromFile(path);
        }
//        wordToDefinitionMap = dictionaryManagement.getDictionaryWords();
//        allWords = dictionaryManagement.getAllWords();
        dictionaryManagement.updateTrie(dictionaryManagement.getDictionaryWords());
        wordListView.setItems(dictionaryManagement.getAllWords());
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
        CustomeToatify.showToast((Stage) searchTF.getScene().getWindow(), "Đang thay đổi");
    }

    // Bắt sự kiện người dùng nhập vào(Hàm update)
    @FXML
    private void handleSaveClick() {
        String selectedWord = wordListView.getSelectionModel().getSelectedItem(); // lấy từ được chọn
        String updatedDefinition = definitionListView.getText();
        System.out.println(updatedDefinition);// lấy dữ liệu người dùng nhập
        definitionListView.setEditable(false);

        if (selectedWord != null) {
            for (Word word : dictionaryManagement.getDictionaryWords()) {
                if (word.getWord_target().equals(selectedWord)) {
                    System.out.println(updatedDefinition);
                    word.setWord_explain(updatedDefinition);// push từ chọn và sự thay đổi vào map
                }
            }
//            for(Word x : dictionaryManagement.getDictionaryWords()){
//                if(x.getWord_explain().equals(updatedDefinition))
//                    System.out.println(x.getWord_target() + " " + x.getWord_explain());
//            }
            dictionaryManagement.exportToFile();


            CustomeToatify.showToast((Stage) searchTF.getScene().getWindow(), "Đã lưu thay đổi");
        }
    }

    // render lại từ điển
//    public static void saveWordsToFile() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("EnglishDictionary/resources/dictionaries.txt"))) {
//            for (Map.Entry<String, String> entry : wordToDefinitionMap.entrySet()) {
//                writer.write("|" + entry.getKey() + "\n");
//                writer.write(entry.getValue() + "\n\n");
//            }
//            Collections.sort(allWords);
//        } catch (IOException e) {
//            System.out.println("Có lỗi xảy ra");
//        }
//    }

//    public static void loadWordsFromFile(String filePath) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String word_target = "";
//            StringBuilder word_explain = new StringBuilder();
//            String wordLine;
//
//            while ((wordLine = reader.readLine()) != null) {
//                if (wordLine.startsWith("|")) {
//                    if (!word_target.isEmpty()) {
//                        Word newWord = new Word(word_target, word_explain.toString().trim());
//                        allWords.add(newWord.getWord_target());
//                        wordToDefinitionMap.put(newWord.getWord_target(), newWord.getWord_explain());
//                    }
//
//                    word_target = wordLine.replace("|", "").trim();
//                    word_explain = new StringBuilder();
//                } else {
//                    word_explain.append(wordLine).append("\n");
//                }
//            }
//
//            if (!word_target.isEmpty()) {
//                Word newWord = new Word(word_target, word_explain.toString().trim());
//                allWords.add(newWord.getWord_target());
//                wordToDefinitionMap.put(newWord.getWord_target(), newWord.getWord_explain());
//            }
//
//            Collections.sort(allWords);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private void handleSearch(KeyEvent keyEvent) {
        String searchText = searchTF.getText().toLowerCase();
      //  System.out.println(searchText);
//        for(int i =0; i<10; i++){
//            System.out.println(dictionaryManagement.getDictionaryWords().get(i).getWord_target());
//        }

        ObservableList<String> filteredWords = dictionaryManagement.searchWord(dictionaryManagement.getDictionaryWords(), searchText);
        wordListView.setItems(FXCollections.observableArrayList(filteredWords));
        for(String x : filteredWords){
            System.out.println(x);
        }
    }




    private void showWordDefinition(String word) {
        if (word != null) {
            for (Word search : dictionaryManagement.getDictionaryWords()) {
                if (search.getWord_target() != null && word.equals(search.getWord_target())) {
                    String definition = search.getWord_explain();
                    definitionListView.setText(definition);
                }
            }
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
                    dictionaryManagement.removeWord(selectedWord);
                    ObservableList<String> wordList = wordListView.getItems();
                    int index = wordList.indexOf(selectedWord);
                    if (index != -1) {
                        wordList.remove(index);
                        wordListView.getSelectionModel().clearSelection();
                        definitionListView.clear();
                        
                    }
            } else {
                Alert notExplained = new Alert(Alert.AlertType.WARNING);
                notExplained.setTitle("Word Not Explained");
                notExplained.setHeaderText(null);
                notExplained.setContentText("This word has not been explained yet.");
                notExplained.showAndWait();
            }
        }
    }

}