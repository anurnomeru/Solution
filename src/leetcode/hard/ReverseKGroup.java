package leetcode.hard;

import io.netty.handler.codec.dns.DefaultDnsOptEcsRecord;

/**
 * Created by Anur IjuoKaruKas on 2020/1/31
 * <p>
 * 25. K 个一组翻转链表
 * <p>
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * <p>
 * k 是一个正整数，它的值小于或等于链表的长度。
 * <p>
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * <p>
 * 示例 :
 * <p>
 * 给定这个链表：1->2->3->4->5
 * <p>
 * 当 k = 2 时，应当返回: 2->1->4->3->5
 * <p>
 * 当 k = 3 时，应当返回: 3->2->1->4->5
 * <p>
 * 说明 :
 * <p>
 * 你的算法只能使用常数的额外空间。
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-nodes-in-k-group
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * 1 2 3 4 5 6
 * <p>
 * 1 2 3 4 5 6
 * <p>
 * 6->5->4
 */
public class ReverseKGroup {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6))))));
        ListNode listNode1 = doReverseRec(listNode, 0, 2);

        System.out.println();
    }

    private static ListNode SENTINEL = new ListNode(999);

    /**
     * return the current new head(old tail) of a reserve list
     */
    public static ListNode doReverseRec(ListNode prevNode, int prevCount, int k) {
        if (prevNode.next == null) {
            return null;
        }
        ListNode thisNode = prevNode.next;
        int thisCount = prevCount + 1;
        boolean isTailNode = thisCount == k;
        ListNode tailNode = doReverseRec(thisNode, thisCount, k);

        if (tailNode == null && !isTailNode) {
            return null;
        } else if (tailNode == null && k == 1) {
            return thisNode;
        }

        if (isTailNode) {
            System.out.println(thisNode.val + " next is " + (tailNode == null ? null : thisNode.val));
            thisNode.next = tailNode;
        } else {
            System.out.println(thisNode.val + " next is " + (prevNode.val));
            thisNode.next = prevNode;
        }

        return isTailNode ? thisNode : tailNode;
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
