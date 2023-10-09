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
            String wordLine;
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

    // sắp xếp từ bằng binarySearch
    public void sortDictionary() {
        Collections.sort(dictionaryWords, new Comparator<Word>() {
            @Override
            public int compare(Word word1, Word word2) {
                // Compare word_target (English words) for sorting
                return word1.getWord_target().compareTo(word2.getWord_target());
            }
        });
    }

    // tìm kiếm từ trong file.txt
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

    // thêm từ vào file.txt
    public void addWord(String word_target, String word_explain) {

        Word newWord = new Word(word_target, word_explain);
        dictionaryWords.add(newWord);
        sortDictionary();
        exportToFile();
    }

    // lấy dữ liệu từ file
    public void exportToFile() {
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

    // hàm xóa từ
    public void removeWord(String word_target) {
        int length = dictionaryWords.size();
        for (int i = 0; i < length; i++) {
            if (dictionaryWords.get(i).getWord_target().equals(word_target)) {
                dictionaryWords.remove(i);
                i--;
                length--;
            }
        }
        exportToFile();
    }

    // hàm tìm kiếm từ hoặc cụm từ
    public void dictionarySearch(String word_searching){
        for(Word word:dictionaryWords){
            if(word.getWord_target().startsWith(word_searching)){
                System.out.println(word.getWord_target());
            }
        }
    }

}
