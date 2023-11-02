package DictionaryApp.app;

import DictionaryCommand.DictionaryManagement;
import DictionaryCommand.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    ListView<String> definitionListView;

    private ObservableList<String> allWords;

    private Map<String, String> wordToDefinitionMap = new HashMap<>();

    @FXML
    public void initialize() {
        allWords = FXCollections.observableArrayList();
        loadWordsFromFile("EnglishDictionary/resources/dictionaries.txt");
        wordListView.setItems(allWords);

        wordListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showWordDefinition(newValue);
        });

        searchTF.setOnKeyReleased(this::handleSearch);
    }

    public void loadWordsFromFile(String filePath) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String word_target = "";
            StringBuilder word_explain = new StringBuilder();
            String wordLine;
            while ((wordLine = reader.readLine()) != null) {
                if (wordLine.startsWith("|")) {
                    if (word_target.length() > 0) {
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


            if (!word_target.isEmpty() && !word_explain.isEmpty()) {
                Word newWord = new Word(word_target, word_explain.toString().trim());
                allWords.add(newWord.getWord_target());
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSearch(KeyEvent keyEvent) {
        String searchText = searchTF.getText().toLowerCase();
        List<String> filteredWords = allWords.stream()
                .filter(word -> word.toLowerCase().startsWith(searchText))
                .collect(Collectors.toList());
        wordListView.setItems(FXCollections.observableArrayList(filteredWords));
    }

    private void showWordDefinition(String word) {
        if (word != null && wordToDefinitionMap.containsKey(word)) {
            String definition = wordToDefinitionMap.get(word);
            ObservableList<String> definitions = FXCollections.observableArrayList(definition.split("\n"));
            definitionListView.setItems(definitions);
        }
    }
}
