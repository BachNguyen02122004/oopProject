package DictionaryCommand;

import DictionaryCommand.Word;

import java.util.List;
public class Dictionary extends Word {
    protected List<Word> ListWord;

    public Dictionary(String word_target, String word_explain) {
        super(word_target, word_explain);
    }
}


