package de.insertmo;

/**
 * Created by Michon on 9/22/2014.
 */

public class Node implements Comparable<Node> {
    public Character character;
    public Integer weight;
    public Node left;
    public Node right;

    public Node(Character c, Integer w) {
        character = c;
        weight = w;
    }

    public Node(Node l, Node r) {
        left = l;
        right = r;
        weight = l.weight + r.weight;
    }

    @Override
    public int compareTo(Node o) {
        return this.weight.compareTo(o.weight);
    }

    @Override
    public String toString() {
        if (character != null) {
            return String.format("%s=%d", character, weight);
        }
        else {
            return String.format("(%s,%s)=%d", left, right, weight);
        }
    }
}
