package leetcode.normal;

/**
 * Created by Anur IjuoKaruKas on 2020/1/31
 * <p>
 * 24. 两两交换链表中的节点
 * <p>
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 * <p>
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 * <p>
 *  
 * <p>
 * 示例:
 * <p>
 * 给定 1->2->3->4, 你应该返回 2->1->4->3.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/swap-nodes-in-pairs
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SwapPairs {

    public static void main(String[] args) {
        ListNode listNode = swapPairs(new ListNode(1, new ListNode(2)));
        System.out.println();
    }

    public static ListNode swapPairs(ListNode head) {
        ListNode listNode = new ListNode(999);
        listNode.next = head;
        swapRec(listNode, true);
        return listNode.next;
    }

    public static boolean swapRec(ListNode prev, boolean swap) {
        ListNode thisNode = prev.next;

        if (thisNode == null) {
            return !swap;
        }
        boolean result = swapRec(thisNode, !swap);
        if (result) {
            ListNode next = thisNode.next;
            if (next != null) {
                prev.next = next;
                thisNode.next = next.next;
                next.next = thisNode;
            }
        }
        return !result;
    }

    public static class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        ListNode(int x, ListNode node) {
            val = x;
            next = node;
        }
    }
}
