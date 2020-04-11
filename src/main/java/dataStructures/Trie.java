package dataStructures;

import java.util.HashMap;

/**
 * Tries is a tree that stores strings. Maximum number of children of a node is equal to size
 * of alphabet. Trie supports search, insert and delete operations in O(L) time where L is
 * length of key.
 *
 * Self Balancing BST : The time complexity of search, insert and delete operations in a
 * self-balancing Binary Search Tree (BST) (like Red-Black Tree, AVL Tree, Splay Tree, etc)
 * is O(L Log n) where n is total number words and L is length of word. The advantage of Self
 * balancing BSTs is that they maintain order which makes operations like minimum, maximum,
 * closest (floor or ceiling) and k-th largest faster.
 *
 * With Trie, we can insert and find strings in O(L) time where L represent the length of a
 * single word. This is obviously faster than BST. This is also faster than Hashing because
 * of the ways it is implemented. We do not need to compute any hash function. No collision
 * handling is required. We can efficiently do prefix search (or auto-complete) with Trie
 *
 * The main disadvantage of tries is that they need a lot of memory for storing the strings.
 * For each node we have too many node pointers(equal to number of characters of the alphabet)
 *
 * reference: https://www.geeksforgeeks.org/advantages-trie-data-structure/?ref=rp
 */
public class Trie {

    private class Node{
        HashMap<Character, Node> children;
        boolean isWord; // is true if the node represents end of a word

        public Node(){
            children = new HashMap<>();
            isWord = false;
        }
    }

    private Node root;

    public Trie(){
        root = new Node();
    }

    public void insert(String key){
        Node curr = root;

        for(int i=0; i<key.length(); ++i){
            char c = key.charAt(i);

            if(!curr.children.containsKey(c))
                curr.children.put(c, new Node());

            curr = curr.children.get(c);
        }

        curr.isWord = true;
    }

    public boolean search(String key){
        Node curr = root;

        for(int i=0; i< key.length(); ++i){
            char c = key.charAt(i);

            if(!curr.children.containsKey(c))
                return false;

            curr = curr.children.get(c);
        }

        return (curr != null && curr.isWord);
    }

    private Node delete(Node node, String key, int idx){
        if(node == null)
            return null;

        if(idx == key.length()) { // last character
            node.isWord = false; // the node is no longer end of word
            if(node.children.size() == 0) // Node is not prefix of any other word
                return null; // delete it
            else // Node is prefix so don't delete it
                return node;
        }

        // not last character
        Node n = delete(node.children.getOrDefault(key.charAt(idx), null), key, idx+1);
        if(n == null) // child is deleted then remove it
            node.children.remove(key.charAt(idx));

        // Node is not prefix of any other word and is not the end of a word then delete it
        if(node.children.size() == 0 && !node.isWord)
            return null;
        else // Node is a prefix or end of another word, thus don't delete it
            return node;
    }

    public void delete(String key){
        delete(root, key, 0);
    }

    public static void main(String[] args) {
        String[] keys = { "the", "a", "there",
                "answer", "any", "by",
                "bye", "their", "hero", "heroplane" };

        Trie trie = new Trie();
        for(String s : keys)
            trie.insert(s);

        System.out.println(trie.search("the")); // true
        System.out.println(trie.search("these")); // false

        trie.delete("heroplane");
        System.out.println(trie.search("heroplane")); // false
        System.out.println(trie.search("hero")); // true
    }
}
