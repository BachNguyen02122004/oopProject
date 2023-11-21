package DictionaryCommand;


import Tries.Trie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;


public class DictionaryManagement extends Dictionary {
    private Trie trie = new Trie();

    private ObservableList<String> allWords = FXCollections.observableArrayList();

    public ObservableList<String> getAllWords() {
        return allWords;
    }

    public List<Word> getDictionaryWords() {
        return dictionaryWords;
    }


    public DictionaryManagement() {
        allWords = FXCollections.observableArrayList();
    }

    public void insertFromFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String word_target = "";
            StringBuilder word_explain = new StringBuilder();
            String wordLine;
            while ((wordLine = br.readLine()) != null) {
                if (wordLine.startsWith("|")) {
                    if (word_target.length() > 0) {
                        Word newWord = new Word(word_target, word_explain.toString().trim());
                        allWords.add(word_target);
                        dictionaryWords.add(newWord);
                    }
                    word_target = wordLine.replace("|", "").trim();
                    word_explain = new StringBuilder();
                } else {
                    word_explain.append(wordLine).append("\n");
                }
            }


            if (!word_target.isEmpty() && !word_explain.isEmpty() && allWords.contains(word_target)) {
                Word newWord = new Word(word_target, word_explain.toString().trim());
                allWords.add(word_target);
                dictionaryWords.add(newWord);
            }
            sortDictionary();
            Collections.sort(allWords);


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
    public boolean dictionaryLookup(String word_target) {
        // check tìm thấy từ hay ko
        boolean ok = false;
        int l = 0, r = dictionaryWords.size() - 1;
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập từ cần tìm kiếm :");

        while (l <= r) {
            int m = (l + r) / 2;
            if (dictionaryWords.get(m).getWord_target().equals(word_target)) {
//                ok = true;
                return true;
//                String ans = dictionaryWords.get(m).getWord_explain();
//                System.out.println(ans);
//                break;
            } else if (dictionaryWords.get(m).getWord_target().compareTo(word_target) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
//        if (!ok) {
//            System.out.println("Không tìm thấy từ!!!");
        return false;
//        }
    }

    // thêm từ vào file.txt
//    public void addWord(String word_target, String word_explain) {
//
//        Word newWord = new Word(word_target, word_explain);
//        dictionaryWords.add(newWord);
//        sortDictionary();
//        exportToFile();
//    }

    // Thêm từ vào file.txt bằng dòng lệnh
    public void addWord(String word_target, String word_explain) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Nhập từ tiếng Anh: ");
//        String word_target = sc.nextLine();
//        System.out.print("Nhập nghĩa tiếng Việt: ");
//        String word_explain = sc.nextLine();
        Word newWord = new Word(word_target, word_explain);
        allWords.add(word_target);
        dictionaryWords.add(newWord);
        System.out.println("Thêm thành công");
        sortDictionary();
        exportToFile();
    }


    // Sửa từ bằng dòng lệnh
    public void updateWord(String word_target, String word_explain) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Từ tiếng Anh muốn sửa: ");

        int index = -1; // Lưu vị trí từ muốn sửa

        // Tìm từ người dùng nhập vào
        for (int i = 0; i < dictionaryWords.size(); i++) {
            if (dictionaryWords.get(i).getWord_target().equalsIgnoreCase(word_target)) {
                index = i;
                break;
            }
        }

        // Nếu tìm thấy từ muốn sửa trong danh sách
        if (index != -1) {
//            System.out.println("Bạn muốn sửa gì?");
//            System.out.println("Nhập số 1: Sửa từ tiếng Anh");
//            System.out.println("Nhập số 2: Sửa nghĩa tiếng Việt");
//            int choice = sc.nextInt();
//            sc.nextLine();  // Tránh trôi lệnh

//            switch (choice) {
////                case 1:
//                    System.out.print("Nhập từ tiếng Anh mới: ");
//                    String new_word_target = sc.nextLine();
//                    dictionaryWords.get(index).setWord_target(new_word_target);
//                    break;
//                case 2:
            System.out.print("Nhập nghĩa tiếng Việt mới: ");

            dictionaryWords.get(index).setWord_explain(word_explain);
//                    break;
//                default:
//                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại!");
//                    return;
//            }

            System.out.println("Sửa từ thành công");
            sortDictionary();
            exportToFile();
        } else {
            System.out.println("Không tìm thấy từ cần sửa.");
        }
    }

    // hàm xóa từ bằng dòng lệnh
    public void removeWord(String word_target) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Nhập từ muốn xóa: ");
//        String word_target = sc.nextLine();
        trie = new Trie();
        int length = dictionaryWords.size();
        for (int i = 0; i < length; i++) {
            if (dictionaryWords.get(i).getWord_target().equals(word_target)) {
                dictionaryWords.remove(i);
                allWords.remove(i);
                i--;
                length--;
            }
        }
        System.out.println("Xóa thành công");
        updateTrie(dictionaryWords);
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

    // hàm tìm kiếm từ hoặc cụm từ trong command line
    public void dictionarySearch(List<Word> dicWord, String word_searching) {
        for (Word word : dictionaryWords) {
            if (word.getWord_target().startsWith(word_searching)) {
                System.out.println(word.getWord_target());
            }
        }
    }

    // tìm kiếm word dùng trie
    public ObservableList<String> searchWord(List<Word> dic, String key) {
        ObservableList<String> listWord = FXCollections.observableArrayList();
        try {
            List<String> results = trie.searchWithWord(key);
            if (results != null) {

                for (String x : results) {
                    listWord.add(x);
//                    System.out.println(results.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
        return listWord;
    }

    //game
    public void game() {
        String path = "EnglishDictionary/resources/game.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            ArrayList<ArrayList<String>> questions = new ArrayList<>();
            String question = "";
            String answer = "";
            String key = "";
            String wordLine;
            int i = 0;
            while ((wordLine = br.readLine()) != null) {
                if (i % 3 == 0) {
                    question = wordLine;
                } else if (i % 3 == 1) {
                    answer = wordLine;
                } else {
                    key = wordLine;
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add(question);
                    tmp.add(answer);
                    tmp.add(key);
                    questions.add(tmp);
                }
                i++;
            }
            Random rand = new Random();
            int n = questions.size();
            int k = 0;
            // select random question from the list
            while (k < n) {
                int maxRand = questions.size();
                int randomNumber = rand.nextInt(maxRand);
                System.out.println((k + 1) + ". " + questions.get(randomNumber).get(0));
                System.out.println(questions.get(randomNumber).get(1));
                Scanner sc = new Scanner(System.in);
                String ans = sc.nextLine();
                if (!ans.toUpperCase().equals(questions.get(randomNumber).get(2))) {
                    System.out.println("Bạn gà quá! Thua rồi!");
                    break;
                } else {
                    System.out.println("Bạn đã trả lời đúng");
                    questions.remove(randomNumber);
                }
                k++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTrie(List<Word> dic) {
        for (Word word : dic) trie.insert(word.getWord_target(), word.getWord_explain());
    }
}