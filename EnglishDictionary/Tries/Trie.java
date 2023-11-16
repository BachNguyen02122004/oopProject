package Tries;

import java.util.*;

public class Trie {
    protected final Map<Character, Trie> children;
    protected String content;
    protected boolean isEndOfWord = false;

    public Trie() {
        this(null);
    }

//     add từng char của từ vào cây
    protected void add(char character) {
        String s;
        if (this.content == null) s = Character.toString(character);
        else s = this.content + character;
        children.put(character, new Trie(s));
    }

    private Trie(String content) {
        this.content = content;
        children = new HashMap<Character, Trie>();
    }

    public void insert(String word) {
        // bắt lỗi null
        if (word == null) throw new IllegalArgumentException("Null diagnoses entries are not valid.");
        Trie node = this;
        for (char x : word.toCharArray()) {
            if (!node.children.containsKey(x)) {
                node.add(x);
            }
            node = node.children.get(x);
        }
        node.isEndOfWord = true;
    }

    public List<String> autoComplete(String prefix) {
        Trie trieNode = this;
        for (char c : prefix.toCharArray()) {
            if (!trieNode.children.containsKey(c)) return null;
            trieNode = trieNode.children.get(c);
        }
        return trieNode.allPrefixes();
    }

    protected List<String> allPrefixes() {
        List<String> results = new ArrayList<String>();
        if (this.isEndOfWord) results.add(this.content);
        for (Map.Entry<Character, Trie> entry : children.entrySet()) {
            Trie child = entry.getValue();
            Collection<String> childPrefixes = child.allPrefixes();
            results.addAll(childPrefixes);
        }
        return results;
    }
}