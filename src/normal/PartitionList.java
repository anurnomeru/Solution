package normal;

/**
 * Created by Anur IjuoKaruKas on 2019/10/30
 *
 * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
 *
 * 你应当保留两个分区中每个节点的初始相对位置。
 *
 * 示例:
 *
 * 输入: head = 1->4->3->2->5->2, x = 3
 * 输出: 1->2->2->4->3->5
 *
 * 2->1, x=2
 *
 * [3,1,2] ，3
 *
 * 执行用时 :
 * 0 ms
 * , 在所有 java 提交中击败了 100.00% 的用户
 * 内存消耗 :
 * 36.1 MB
 * , 在所有 java 提交中击败了 57.14% 的用户
 */
public class PartitionList {

    public static void main(String[] args) {
        PartitionList p = new PartitionList();
        ListNode head = new ListNode(3);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        p.partition(head, 3);
    }

    public ListNode partition(ListNode head, int x) {
        if (head == null) {
            return null;
        }
        HEAD.next = head;
        partitionIter(null, HEAD, x);
        return HEAD.next;
    }

    public void partitionIter(ListNode largerPrev, ListNode prev, int x) {
        ListNode now = prev.next;
        if (now == null) {// 指针到了空，直接跳出递归
            return;
        }

        if (largerPrev == null) {
            if (now.val >= x) {// 找到第一个大于等于x的节点
                largerPrev = prev;
            }
            partitionIter(largerPrev, now, x);
        } else {
            if (now.val >= x) {
                // ignore
                partitionIter(largerPrev, now, x);
            } else {
                prev.next = now.next;// 从单向链表中抹去
                now.next = largerPrev.next;// 插到之前的列表去
                largerPrev.next = now;
                partitionIter(now, prev, x);
            }
        }
    }

    public static ListNode HEAD = new ListNode(0);

    // Definition for singly-linked list.
    public static class ListNode {

        int val;

        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
