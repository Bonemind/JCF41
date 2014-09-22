package de.insertmo;

import java.util.*;

public class Main {

    static final String INPUT = "Hello world";

    public static void main(String[] args) {
        Map count = countCharacters(INPUT);
        System.out.println(count);

        Queue sorted = sortCount(count);
        System.out.println(sorted);

        Node tree = buildTree(sorted);
        System.out.println(tree);

        Map<Character, String> codes = new HashMap<Character, String>();
        getCodes(tree, codes, "");
        System.out.println(codes);

        String encoded = encode(INPUT, codes);
        System.out.println(encoded);

        String decoded = decode(encoded, tree);
        System.out.println(decoded);
    }

    public static Map<Character, Integer> countCharacters(String input) {
        Map<Character, Integer> count = new HashMap<Character, Integer>();
        for (Character c : input.toCharArray()) {
            count.put(c, count.containsKey(c) ? count.get(c) + 1 : 1);
        }
        return count;
    }

    public static Queue<Node> sortCount(Map<Character, Integer> count) {
        Queue<Node> list = new PriorityQueue<Node>();
        for (Map.Entry<Character, Integer> pair : count.entrySet()) {
            list.add(new Node(pair.getKey(), pair.getValue()));
        }
        return list;
    }

    public static Node buildTree(Queue<Node> nodes) {
        while (nodes.size() > 1) {
            nodes.add(new Node(nodes.remove(), nodes.remove()));
        }
        return nodes.remove();
    }

    public static void getCodes(Node node, Map<Character, String> codes, String root) {
        // If the current node is a character node, store code.
        if (node.character != null) {
            codes.put(node.character, root);
        }

        // Traverse left/right, if appropriate.
        if (node.left != null) {
            getCodes(node.left, codes, root + "0");
        }
        if (node.right != null) {
            getCodes(node.right, codes, root + "1");
        }
    }

    public static String encode(String input, Map<Character, String> codes) {
        String output = "";
        for (Character c : input.toCharArray()) {
            output += codes.get(c);
        }
        return output;
    }

    public static String decode(String input, Node tree) {
        Node current = tree;
        String output = "";
        for (Character c : input.toCharArray()) {
            // Traverse left/right.
            if (c == '0') {
                current = current.left;
            }
            else if (c == '1') {
                current = current.right;
            }

            // If we've reached a character node, add to output and return to root of the tree.
            if (current.character != null) {
                output += current.character;
                current = tree;
            }
        }
        return output;
    }
}
