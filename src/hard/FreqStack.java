package hard;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anur IjuoKaruKas on 2019/5/5
 */
public class FreqStack {

    private Node markNode = new Node();

    public FreqStack() {
        Map m = new HashMap<>();
    }

    public static void main(String[] args) {
        FreqStack freqStack = new FreqStack();
        freqStack.push(1);
        freqStack.push(2);
        freqStack.push(3);
        freqStack.push(4);

        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
    }

    public void push(int x) {
        Node node = new Node();
        node.i = x;
        if (markNode.next == null && markNode.prev == null) {
            markNode.next = node;
            markNode.prev = node;

            node.prev = markNode;
            node.next = markNode;
        } else {
            Node lastNode = markNode.prev;

            lastNode.next = node;

            node.prev = lastNode;
            node.next = markNode;

            markNode.prev = node;
        }
    }

    public int pop() {
        if (markNode.prev == null) {
            throw new RuntimeException();
        }
        Node lastNode = markNode.prev;

        if (lastNode.prev == markNode) {
            markNode.next = null;
            markNode.prev = null;
            return lastNode.i;
        } else {
            lastNode.prev.next = markNode;
            markNode.prev = lastNode.prev;

            return lastNode.i;
        }
    }

    public static class Node {

        public Integer i;

        public Node next = null;

        public Node prev = null;
    }
}