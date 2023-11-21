package Tries;

import java.util.*;

public class Trie {
    protected final Map<Character, Trie> children;
    protected String definition;
    protected boolean isEndOfWord;

    public Trie() {
        this(null);
        isEndOfWord = false;
    }

//     add từng char của từ vào cây
//    protected void add(char character) {
//        String s;
//        if (this.content == null) s = Character.toString(character);
//        else s = this.content + character;
//        children.put(character, new Trie(s));
//    }
//
//    private Trie(String content) {
//        this.content = content;
//        children = new HashMap<Character, Trie>();
//    }
//
//    public void insert(String word) {
//        // bắt lỗi null
//        if (word == null) throw new IllegalArgumentException("Null diagnoses entries are not valid.");
//        Trie node = this;
//        for (char x : word.toCharArray()) {
//            if (!node.children.containsKey(x)) {
//                node.add(x);
//            }
//            node = node.children.get(x);
//        }
//        node.isEndOfWord = true;
//    }
//
//    public List<String> autoComplete(String prefix) {
//        Trie trieNode = this;
//        for (char c : prefix.toCharArray()) {
//            if (!trieNode.children.containsKey(c)) return null;
//            trieNode = trieNode.children.get(c);
//        }
//        return trieNode.allPrefixes();
//    }
//
//    protected List<String> allPrefixes() {
//        List<String> results = new ArrayList<String>();
//        if (this.isEndOfWord) results.add(this.content);
//        for (Map.Entry<Character, Trie> entry : children.entrySet()) {
//            Trie child = entry.getValue();
//            Collection<String> childPrefixes = child.allPrefixes();
//            results.addAll(childPrefixes);
//        }
//        return results;
//    }

    private Trie(String definition) {
        this.definition = definition;
        isEndOfWord = false;
        children = new HashMap<Character, Trie>();
    }

    public void insert(String word, String meaning) {
        Trie head = this;
        for (char x : word.toCharArray()) {
            head.children.putIfAbsent(x, new Trie());
            head = head.children.get(x);
        }
        head.isEndOfWord = true;
        head.definition = meaning;
    }

    private void collectWords(StringBuffer tmp, Trie head, List<String> words) {
        if (head.isEndOfWord) {
            words.add(tmp.toString());
        }
        for (char x : head.children.keySet()) {
            tmp.append(x);
            collectWords(tmp, head.children.get(x), words);
            tmp.deleteCharAt(tmp.length() - 1);
        }
    }

    //hien thi danh sach tim kiem
    public List<String> searchWithWord(String word) {
        List<String> words = new ArrayList<>();
        Trie head = this;
        StringBuffer startsWith = new StringBuffer(word);

        for (char x : word.toCharArray()) {
            if (head.children.containsKey(x)) {
                head = head.children.get(x);
            } else {
                return words;
            }
        }

        collectWords(startsWith, head, words);
        return words;
    }

}