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

    //
    public void dictionaryAdvanced() {
        // biến check continue
        boolean ctn = true;
        while (ctn) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Welcome to My Applications!!!");
            System.out.println("[0] Exit");
            System.out.println("[1] Add");
            System.out.println("[2] Remove");
            System.out.println("[3] Update");
            System.out.println("[4] Display");
            System.out.println("[5] Lookup");
            System.out.println("[6] Search");
            System.out.println("[7] Game");
            System.out.println("[8] Import from file");
            System.out.println("[9] Export to file");
            System.out.println("Your action :");
            int option = sc.nextInt();
            // tránh trôi lệnh
            sc.nextLine();

            switch (option) {
                case 0:
                    // thoát khỏi vòng lặp
                    ctn = false;
                    break;
                case 1:
                    addWord();
                    break;
                case 2:
                    removeWord();
                    break;
                case 3:
                    updateWord();
                    break;
                case 4:
                    showAllWords();
                    break;
                case 5:
                    // sửa hàm từ String thành void
                    dictionaryLookup();
                    break;
                case 6:
                    String wordSearch = sc.nextLine();
                    dictionarySearch(wordSearch);
                    break;
                case 7:
                    System.out.println("Play game!!!");
                    break;
                case 8:
                    insertFromFile();
                    System.out.println("Import from file successful!");
                    break;
                case 9:
                    exportToFile();
                    System.out.println("Export to file successful!");
                    break;
                default:
                    System.out.println("Action not supported");
            }
        }

    }
    public void dictionaryBasic() {
        insertFromFile();
        sortDictionary();
        showAllWords();
    }

}