package DictionaryApp.app;

import DictionaryCommand.DictionaryManagement;
import DictionaryCommand.DictionaryCommandLine;

import javafx.fxml.FXML;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SearchController {
    @FXML
    TextField searchTF;

    DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();


    public void getSearchedWord() {
        String searchWord = searchTF.getText();
        dictionaryCommandLine.dictionaryBasic();
    }

}