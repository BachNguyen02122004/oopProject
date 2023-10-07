import java.util.*;
public class DictionaryManagement {
    private List<Word> dictionaryWords;

    public List<Word> getDictionaryWords() {
        return dictionaryWords;
    }

    public void setDictionaryWords(List<Word> dictionaryWords) {
        this.dictionaryWords = dictionaryWords;
    }

    public DictionaryManagement() {
        dictionaryWords = new ArrayList<>();
    }

    public void insertFromCommandLine() {
        // nhap xuat
        Scanner sc = new Scanner(System.in);
        long NumberWords = sc.nextLong();
        sc.nextLine();

        // thao tac xu ly
        for(int i = 0; i<NumberWords; i++){
            String word_target = sc.nextLine();
            String word_explain = sc.nextLine();
            Word newWord = new Word(word_target, word_explain);
            dictionaryWords.add(newWord);
    }

    }
}
