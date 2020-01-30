package leetcode.normal;

/**
 * Created by Anur IjuoKaruKas on 2020/1/30
 * <p>
 * 19. 删除链表的倒数第N个节点
 * <p>
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 * <p>
 * 示例：
 * <p>
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * <p>
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * 说明：
 * <p>
 * 给定的 n 保证是有效的。
 * <p>
 * 进阶：
 * <p>
 * 你能尝试使用一趟扫描实现吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class RemoveNthFromEnd {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        removeNthFromEnd(listNode, 1);
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode sentinal = new ListNode(999);
        sentinal.next = head;
        removeRecursive(sentinal, n);
        return sentinal.next;
    }

    public static int removeRecursive(ListNode prev, int removeN) {
        ListNode thisNode = prev.next;
        if (thisNode == null) {
            return 1;
        } else {
            int thisNodeIndex = removeRecursive(thisNode, removeN);

            if (thisNodeIndex == removeN) {
                prev.next = thisNode.next;
                return -1;
            } else if (thisNodeIndex == -1) {
                return -1;
            }

            return thisNodeIndex + 1;
        }
    }

    public static void print(ListNode node) {
        if (node == null) {
            return;
        } else {
            System.out.println(node.val);
            print(node.next);
        }
    }

    public static class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}



