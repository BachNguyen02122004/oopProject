import java.util.*;
public class DictionaryCommandLine extends DictionaryManagement {
    public DictionaryCommandLine() {
        super();
    }

    public void showAllWords() {
        List<Word> listWord = getDictionaryWords();
        System.out.println("NO   | English | Vietnamese");
        for (int i = 0; i < listWord.size(); i++) {
            Word word = listWord.get(i);
            System.out.println(i + 1 + "   | " + word.getWord_targer() + " | " + word.getWord_explain());
        }
    }

    public void dictionaryBasic() {
        insertFromCommandLine();
        showAllWords();
    }

}