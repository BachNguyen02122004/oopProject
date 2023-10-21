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
        String path = "EnglishDictionary/resources/dictionariesCommandLine.txt";

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
    public void dictionaryLookup() {
        // check tìm thấy từ hay ko
        boolean ok = false;
        int l = 0, r = dictionaryWords.size() - 1;
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập từ cần tìm kiếm :");
        String word_target = sc.nextLine();
        while (l <= r) {
            int m = (l + r) / 2;
            if (dictionaryWords.get(m).getWord_target().equals(word_target)) {
                ok = true;
                String ans = dictionaryWords.get(m).getWord_explain();
                System.out.println(ans);
                break;
            } else if (dictionaryWords.get(m).getWord_target().compareTo(word_target) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        if(!ok) {
            System.out.println("Không tìm thấy từ!!!");
        }
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
    public void addWord() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập từ tiếng Anh: ");
        String word_target = sc.nextLine();
        System.out.print("Nhập nghĩa tiếng Việt: ");
        String word_explain = sc.nextLine();
        Word newWord = new Word(word_target, word_explain);
        System.out.println("Thêm thành công");
        dictionaryWords.add(newWord);
        sortDictionary();
        exportToFile();
    }

    // hàm xóa từ
//    public void removeWord(String word_target) {
//        int length = dictionaryWords.size();
//        for (int i = 0; i < length; i++) {
//            if (dictionaryWords.get(i).getWord_target().equals(word_target)) {
//                dictionaryWords.remove(i);
//                i--;
//                length--;
//            }
//        }
//        exportToFile();
//    }

    // Sửa từ bằng dòng lệnh
    public void updateWord() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Từ tiếng Anh muốn sửa: ");
        String word_target = sc.nextLine();

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
            System.out.println("Bạn muốn sửa gì?");
            System.out.println("Nhập số 1: Sửa từ tiếng Anh");
            System.out.println("Nhập số 2: Sửa nghĩa tiếng Việt");
            int choice = sc.nextInt();
            sc.nextLine();  // Tránh trôi lệnh

            switch (choice) {
                case 1:
                    System.out.print("Nhập từ tiếng Anh mới: ");
                    String new_word_target = sc.nextLine();
                    dictionaryWords.get(index).setWord_target(new_word_target);
                    break;
                case 2:
                    System.out.print("Nhập nghĩa tiếng Việt mới: ");
                    String new_word_explain = sc.nextLine();
                    dictionaryWords.get(index).setWord_explain(new_word_explain);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại!");
                    return;
            }

            System.out.println("Sửa từ thành công");
            sortDictionary();
            exportToFile();
        } else {
            System.out.println("Không tìm thấy từ cần sửa.");
        }
    }

    // hàm xóa từ bằng dòng lệnh
    public void removeWord() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập từ muốn xóa: ");
        String word_target = sc.nextLine();
        int length = dictionaryWords.size();
        for (int i = 0; i < length; i++) {
            if (dictionaryWords.get(i).getWord_target().equals(word_target)) {
                dictionaryWords.remove(i);
                i--;
                length--;
            }
        }
        System.out.println("Xóa thành công");
        exportToFile();
    }

    // lấy dữ liệu từ file
    public void exportToFile() {
        String path = "EnglishDictionary/resources/dictionariesCommandLine.txt";

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

    // hàm tìm kiếm từ hoặc cụm từ
    public void dictionarySearch(String word_searching) {
        for (Word word : dictionaryWords) {
            if (word.getWord_target().startsWith(word_searching)) {
                System.out.println(word.getWord_target());
            }
        }
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
                } else if(i % 3 == 1) {
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
                System.out.println((k + 1) + ". " +  questions.get(randomNumber).get(0));
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

}
