public class Word {
    private String word_targer, word_explain;

    public Word(String word_targer, String word_explain) {
        this.word_targer = word_targer;
        this.word_explain = word_explain;
    }

    public String getWord_targer() {
        return word_targer;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public void setWord_targer(String word_targer) {
        this.word_targer = word_targer;
    }

}

