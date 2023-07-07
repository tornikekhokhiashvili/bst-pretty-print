package com.epam.rd.autocode.bstprettyprint;

import java.util.ArrayList;
import java.util.List;

public class Tree implements PrintableTree {
    public static final Tree instance = new Tree();

    public Tree() {
    }

    public static Tree getInstance() {
        return instance;
    }

    @Override
    public void add(int i) {
        root = addRecursive(root, i);
    }

    @Override
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder();
        prettyPrintHelper(root, sb, "",true,0);
        return sb.toString();
    }
    private static void printNestedStructure(int depth) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < depth; i++) {
            sb.append(" ");
        }

        if (depth < 10) {
            sb.append("┌");
            System.out.println(sb.toString() + depth);

            sb.append("│");
            printNestedStructure(depth + 1);

            sb.setCharAt(sb.length() - 2, '┤');
            System.out.println(sb.toString());
        }

        sb.setCharAt(sb.length() - 2, '└');
        System.out.println(sb.toString() + (depth + 1));
    }

    private void prettyPrintHelper(Node node, StringBuilder sb, String indent, boolean isLast, int depth) {
        if (node == null) {
            return;
        }

        if (depth > 0) {
            sb.append(indent);

            for (int i = 0; i < depth - 1; i++) {
                sb.append("│  ");
            }

            if (isLast) {
                sb.append("└─");
                indent += "   ";
            } else {
                sb.append("├─");
                indent += "│  ";
            }

            sb.append(node.value).append("\n");
        } else {
            sb.append(node.value).append("\n");
        }

        prettyPrintHelper(node.left, sb, indent, false, depth + 1);
        prettyPrintHelper(node.right, sb, indent, true, depth + 1);
    }

    private Node root;

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        }

        return current;
    }
}
