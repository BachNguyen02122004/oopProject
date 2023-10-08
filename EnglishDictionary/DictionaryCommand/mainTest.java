package DictionaryCommand;


import DictionaryCommand.DictionaryCommandLine;

public class mainTest {
    public static void main(String[] args) {
        DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();
        dictionaryCommandLine.dictionaryBasic();
        dictionaryCommandLine.addWord("bach", "bách nè");
        System.out.println(dictionaryCommandLine.dictionaryLookup());

    }
}


