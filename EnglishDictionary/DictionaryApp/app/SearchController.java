package DictionaryApp.app;

import DictionaryCommand.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    public void initialize() {
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
                                setBackground(Background.fill(Color.INDIGO));
                            } else {
                                setBackground(Background.fill(Color.rgb(237,238,240)));
                            }
                        } else {
                            setBackground(Background.fill(Color.rgb(237,238,240)));
                        }
                    }
                };
            }
        });

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
}