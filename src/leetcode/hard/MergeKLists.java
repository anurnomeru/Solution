package leetcode.hard;

import java.security.interfaces.ECKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Anur IjuoKaruKas on 2020/1/30
 * <p>
 * 23. 合并K个排序链表
 * <p>
 * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
 * <p>
 * 示例:
 * <p>
 * 输入:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 输出: 1->1->2->3->4->4->5->6
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-k-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MergeKLists {

    public static void main(String[] args) {
        //        ListNode listNode = mergeKLists(new ListNode[] {
        //            new ListNode(-3, new ListNode(1, new ListNode(4))),
        //            new ListNode(-1, new ListNode(1)),
        //            new ListNode(-2, new ListNode(-1, new ListNode(0, new ListNode(2))))
        //        });

        //    // [[],[-10,-9,-8,-7,-2,-1,0,1],[-4],[]]
        //        ListNode listNode = mergeKLists(new ListNode[] {
        //            null,
        //            new ListNode(-10, new ListNode(-9, new ListNode(-8, new ListNode(-7, new ListNode(-2, new ListNode(-1, new ListNode(0, new ListNode(1)))))))),
        //            new ListNode(4),
        //            null
        //        });

        // [[0,1,2],[-10,-8,-5,-4],[],[],[-3],[-10,-9,-6,-4,-3,-2,-2,-1,2],[-9,-9,-2,-1,0,1],[-9,-4,-3,-2,2,2,3,3,4]]

        ListNode listNode = mergeKLists(new ListNode[] {
            new ListNode(0, new ListNode(1, new ListNode(2, null))),
            new ListNode(-10, new ListNode(-8, new ListNode(-5, new ListNode(-4)))),
            new ListNode(-3),
            null,
            null,
            new ListNode(-10, new ListNode(-9, new ListNode(-6, new ListNode(-4, new ListNode(-3, new ListNode(-2, new ListNode(-2, new ListNode(-1, new ListNode(2))))))))),
            new ListNode(-9, new ListNode(-9, new ListNode(-2, new ListNode(-1, new ListNode(0, new ListNode(1)))))),
            new ListNode(-9, new ListNode(-4, new ListNode(-3, new ListNode(-2, new ListNode(2, new ListNode(2, new ListNode(3, new ListNode(3, new ListNode(4))))))))),
            });

        System.out.println();
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null) {
            return null;
        }
        lists = removeEmpty(lists);
        if (lists.length == 0) {
            return null;
        }

        originalSort(lists);
        int lastActive = lists.length - 1;

        ListNode sentinel = new ListNode(999);
        ListNode result = sentinel;
        while (true) {
            ListNode insert = lists[0];

            sentinel.next = insert;
            sentinel = insert;

            if (lastActive == 0) {
                break;
            }

            ListNode next = insert.next;
            System.arraycopy(lists, 1, lists, 0, lists.length - 1);

            if (next == null) {
                lists[lastActive] = null;
                lastActive--;
            } else {
                int nextVal = next.val;
                boolean cawada = false;
                inner:
                for (int i = 0; i < lastActive; i++) {
                    if (nextVal <= lists[i].val) {
                        System.arraycopy(lists, i, lists, i + 1, lists.length - i - 1);
                        lists[i] = next;
                        cawada = true;
                        break inner;
                    }
                }
                if (!cawada) {
                    lists[lastActive] = next;
                }
            }
        }

        return result.next;
    }

    private static ListNode[] removeEmpty(ListNode[] lists) {
        List<ListNode> list = new ArrayList<>();

        for (int i = 0; i < lists.length; i++) {
            ListNode temp = lists[i];
            if (temp != null) {
                list.add(temp);
            }
        }
        ListNode[] result = new ListNode[list.size()];

        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    public static void originalSort(ListNode[] lists) {
        if (lists.length > 2) {
            int length = lists.length;
            int mid = length / 2;
            int remain = length - mid;
            ListNode[] left = new ListNode[mid];
            ListNode[] right = new ListNode[remain];

            System.arraycopy(lists, 0, left, 0, mid);
            System.arraycopy(lists, mid, right, 0, remain);

            originalSort(left);
            originalSort(right);

            int leftLength = left.length;
            int rightLength = right.length;

            int leftPoint = 0;
            int rightPoint = 0;

            Integer leftNum = left[leftPoint].val;
            Integer rightNum = right[rightPoint].val;

            for (int i = 0; i < lists.length; i++) {
                if (leftNum == null || (rightNum != null && leftNum > rightNum)) {
                    lists[i] = right[rightPoint];
                    rightPoint++;
                    rightNum = rightPoint == rightLength ? null : right[rightPoint].val;
                } else {
                    lists[i] = left[leftPoint];
                    leftPoint++;
                    leftNum = leftPoint == leftLength ? null : left[leftPoint].val;
                }
            }
        } else if (lists.length == 2) {
            if (lists[1].val < lists[0].val) {
                ListNode node = lists[0];
                lists[0] = lists[1];
                lists[1] = node;
            }
        }
    }

    public static class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        ListNode(int x, ListNode node) {
            val = x;
            this.next = node;
        }
    }
}

