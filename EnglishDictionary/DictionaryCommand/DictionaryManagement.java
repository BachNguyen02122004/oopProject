package DictionaryCommand;

import DictionaryCommand.Word;

import java.io.*;
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

    public void insertFromFile() {
        String path = "EnglishDictionary/resources/dictionaries.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String word_target = "";
            StringBuilder word_explain = new StringBuilder();
            String wordLine ;
            while ((wordLine = br.readLine()) != null) {
                if (wordLine.startsWith("|")) {
                    if (word_target.length() > 0) {
                        Word newWord = new Word(word_target, word_explain.toString().trim());
                        dictionaryWords.add(newWord);
                    }
                    word_target = wordLine.replace("|", "").trim();
                    word_explain = new StringBuilder();
                } else {
                    word_explain.append(wordLine).append("\n");
                }
            }


            if (!word_target.isEmpty() && !word_explain.isEmpty()) {
                Word newWord = new Word(word_target, word_explain.toString().trim());
                dictionaryWords.add(newWord);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // binary
    public void sortDictionary() {
        Collections.sort(dictionaryWords, new Comparator<Word>() {
            @Override
            public int compare(Word word1, Word word2) {
                // Compare word_target (English words) for sorting
                return word1.getWord_target().compareTo(word2.getWord_target());
            }
        });
    }


    public String dictionaryLookup() {
        int l = 0, r = dictionaryWords.size() - 1;
        Scanner sc = new Scanner(System.in);
        String word_target = sc.nextLine();
        while (l <= r) {
            int m = (l + r) / 2;
            if (dictionaryWords.get(m).getWord_target().equals(word_target)) {
                String ans = dictionaryWords.get(m).getWord_explain();
                return ans;
            } else if (dictionaryWords.get(m).getWord_target().compareTo(word_target) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        return "Không tìm thấy từ!!!";
    }

    public void addWord(String word_target, String word_explain) {

        Word newWord = new Word(word_target, word_explain);
        dictionaryWords.add(newWord);
        updateFile();
    }

    public void updateFile() {
        String path = "EnglishDictionary/resources/dictionaries.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Word word : dictionaryWords) {
                bw.write("|" + word.getWord_target());
                bw.newLine();
                bw.write(word.getWord_explain());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
