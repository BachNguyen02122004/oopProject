package DictionaryCommand;

import DictionaryCommand.DictionaryManagement;
import DictionaryCommand.Word;

import java.util.*;

public class DictionaryCommandLine extends DictionaryManagement {
    public DictionaryCommandLine() {
        super();
    }

    public void showAllWords() {
        // Lấy từ
        List<Word> words = getDictionaryWords();

        // Sắp xếp thứ tự từ
        words.sort((word1, word2) -> word1.getWord_target().compareToIgnoreCase(word2.getWord_target()));

        // Tính chiều dài tối đa của từ tiếng Anh
        int maxLength = 7;  // Độ dài mặc định của từ "English"
        for (Word word : words) {
            if (word.getWord_target().length() > maxLength) {
                maxLength = word.getWord_target().length();
            }
        }
        // In ra từ
        System.out.printf("%-4s | %-" + maxLength + "s | %s%n", "No", "English", "Vietnamese");
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            System.out.printf("%-4d | %-" + maxLength + "s | %s%n", (i + 1), word.getWord_target(), word.getWord_explain());
        }
    }
    
    public void dictionaryBasic() {
        insertFromFile();
        sortDictionary();
//        showAllWords();
    }

}