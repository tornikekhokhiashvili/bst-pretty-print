package com.epam.rd.autocode.bstprettyprint;

import java.util.*;

public class Tree implements PrintableTree {
    private final List<Node> tree;
    private Node root;
    private final StringBuilder result;

    private final char LEFT_DOWN = '┌';
    private final char RIGHT_DOWN = '┐';
    private final char LEFT_UP = '└';
    private final char RIGHT_UP = '┘';
    private final char LINE_LEFT = '┤';
    private final char LINE = '│';

    public Tree() {
        tree = new ArrayList();
        result = new StringBuilder();
    }

    @Override
    public void add(int i) {
        if (tree.isEmpty()) {
            root = new Node(i);
            tree.add(root);
        } else {
            findParents(i, root);
        }
    }

    private void findParents(int i, Node node) {
        if (i > node.getValue()) {
            if (node.getRightChild() != null) {
                node = node.getRightChild();
                findParents(i, node);
            } else {
                Node current = new Node(i, node);
                node.setRightChild(current);
                current.setParent(node);
                tree.add(current);
            }
        }
        if (i < node.getValue()) {
            if (node.getLeftChild() != null) {
                node = node.getLeftChild();
                findParents(i, node);
            } else {
                Node current = new Node(i, node);
                node.setLeftChild(current);
                current.setParent(node);
                tree.add(current);
            }
        }
    }

    @Override
    public String prettyPrint() {
        tree.sort(new ValueComparator());
        List<Integer> drawLine = new ArrayList<>();
        for (Node node : tree) {
            StringBuilder currentStr = new StringBuilder();
            int numberSpaces = sumSpaces(node, 0);
            for (int i = 0; i < numberSpaces; i++) {
                currentStr.append(" ");
            }
            if (node.getParent() != null) {
                if (node.getValue() < node.getParent().getValue()) {
                    drawLine.add(currentStr.length());
                    currentStr.append(LEFT_DOWN);
                } else {
                    currentStr.append(LEFT_UP);
                }
            }
            currentStr.append(node.getValue());
            if (node.getLeftChild() != null && node.getRightChild() != null) {
                drawLine.add(currentStr.length());
                currentStr.append(LINE_LEFT);
            } else if (node.getLeftChild() != null && node.getRightChild() == null) {
                currentStr.append(RIGHT_UP);
            } else if (node.getRightChild() != null && node.getLeftChild() == null) {
                drawLine.add(currentStr.length());
                currentStr.append(RIGHT_DOWN);
            }
            Iterator<Integer> iterator = drawLine.iterator();
            while (iterator.hasNext()) {
                int index = iterator.next();
                if (currentStr.charAt(index) == ' ') {
                    currentStr.setCharAt(index, LINE);
                } else if (currentStr.charAt(index) == LINE_LEFT) {
                } else if (currentStr.charAt(index) == LEFT_UP || currentStr.charAt(index) == RIGHT_UP) {
                    iterator.remove();
                }
            }

            currentStr.append("\n");
            result.append(currentStr);
        }
        return result.toString();
    }

    private int sumSpaces (Node node, int sun) {
        int sumSpaces = sun;
        Node parent;
        if (node != root) {
            parent = node.getParent();
            String value = String.valueOf(parent.getValue());
            sumSpaces += value.length() + 1;
            return sumSpaces(parent, sumSpaces);
        }
        return sumSpaces - 1;
    }

    static class ValueComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.getValue() - o2.getValue();
        }
    }
}
