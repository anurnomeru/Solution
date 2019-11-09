package leetcode.hard;

import java.util.Random;

/**
 * Created by Anur IjuoKaruKas on 2019/11/4
 *
 * 1206 - 跳表
 *
 * 不使用任何库函数，设计一个跳表。
 *
 * 跳表是在 O(log(n)) 时间内完成增加、删除、搜索操作的数据结构。跳表相比于树堆与红黑树，其功能与性能相当，并且跳表的代码长度相较下更短，其设计思想与链表相似。
 *
 * 例如，一个跳表包含 [30, 40, 50, 60, 70, 90]，然后增加 80、45 到跳表中，以下图的方式操作：
 *
 * Artyom Kalinin [CC BY-SA 3.0], via Wikimedia Commons
 *
 * 跳表中有很多层，每一层是一个短的链表。在第一层的作用下，增加、删除和搜索操作的时间复杂度不超过 O(n)。跳表的每一个操作的平均时间复杂度是 O(log(n))，空间复杂度是 O(n)。
 *
 * 在本题中，你的设计应该要包含这些函数：
 *
 * bool search(int target) : 返回target是否存在于跳表中。
 * void add(int num): 插入一个元素到跳表。
 * bool erase(int num): 在跳表中删除一个值，如果 num 不存在，直接返回false. 如果存在多个 num ，删除其中任意一个即可。
 * 了解更多 : https://en.wikipedia.org/wiki/Skip_list
 *
 * 注意，跳表中可能存在多个相同的值，你的代码需要处理这种情况。
 *
 * 样例:
 *
 * Skiplist skiplist = new Skiplist();
 *
 * skiplist.add(1);
 * skiplist.add(2);
 * skiplist.add(3);
 * skiplist.search(0);   // 返回 false
 * skiplist.add(4);
 * skiplist.search(1);   // 返回 true
 * skiplist.erase(0);    // 返回 false，0 不在跳表中
 * skiplist.erase(1);    // 返回 true
 * skiplist.search(1);   // 返回 false，1 已被擦除
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/design-skiplist
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 执行用时 : 45 ms, 在所有 java 提交中击败了 100.00% 的用户
 * 内存消耗 :48.2 MB, 在所有 java 提交中击败了 100.00% 的用户
 */
public class Skiplist {

    private AnurNode TOP_HEAD;

    private AnurNode SENTINEL = new AnurNode(null);

    private static Random r = new Random();

    public boolean search(int target) {
        AnurNode lastSmallerOrEqRecursive = findLastSmallerOrEqRecursive(target, TOP_HEAD);
        if (lastSmallerOrEqRecursive == null) {
            return false;
        }
        return lastSmallerOrEqRecursive.val == target;
    }

    public boolean erase(int num) {
        AnurNode lastSmallerOrEqRecursive = findLastSmallerOrEqRecursive(num, TOP_HEAD);
        if (lastSmallerOrEqRecursive == null) {
            return false;
        }
        if (lastSmallerOrEqRecursive.val != num) {
            return false;
        }

        while (lastSmallerOrEqRecursive != null) {
            if (lastSmallerOrEqRecursive.prev != null) {
                if (lastSmallerOrEqRecursive == TOP_HEAD) {
                    TOP_HEAD = lastSmallerOrEqRecursive.prev;
                }
                lastSmallerOrEqRecursive.prev.next = lastSmallerOrEqRecursive.next;
            }
            if (lastSmallerOrEqRecursive.next != null) {
                if (lastSmallerOrEqRecursive == TOP_HEAD) {
                    TOP_HEAD = lastSmallerOrEqRecursive.next;
                }
                lastSmallerOrEqRecursive.next.prev = lastSmallerOrEqRecursive.prev;
            }

            if (lastSmallerOrEqRecursive == TOP_HEAD) {
                TOP_HEAD = lastSmallerOrEqRecursive.shita;
            }
            lastSmallerOrEqRecursive = lastSmallerOrEqRecursive.shita;
        }
        return true;
    }

    private AnurNode findLastSmallerOrEqRecursive(int val, AnurNode compare) {
        if (compare == null) {
            return null;
        }
        if (compare.val == val) {// 等于就直接返回了
            return compare;
        }
        if (compare.prev != null && compare.prev.val >= val) {// 先左
            return findLastSmallerOrEqRecursive(val, compare.prev);
        }
        if (compare.next != null && compare.next.val <= val) {// 后右
            return findLastSmallerOrEqRecursive(val, compare.next);
        }
        if (compare.shita != null) {// 最后下
            return findLastSmallerOrEqRecursive(val, compare.shita);
        }
        return compare;
    }

    public void add(int num) {
        AnurNode shitaNode = null;
        AnurNode node = new AnurNode(num);

        AnurNode result = doAddRecursive(num, TOP_HEAD);
        if (result == null) {// 初始化
            TOP_HEAD = node;
        } else {
            AnurNode sentinel = SENTINEL.sentinel;
            boolean addNext = true;

            while (sentinel != null) {
                if (addNext) {// 是否要加下一层
                    if (sentinel.val > num) {// 代表要插入到左边
                        AnurNode modoPrev = sentinel.prev;
                        if (modoPrev == null) {// 到了最左边，
                            if (sentinel == TOP_HEAD) {// 到头了
                                TOP_HEAD = node;
                            }
                        } else {
                            modoPrev.next = node;
                            node.prev = modoPrev;
                        }

                        sentinel.prev = node;
                        node.next = sentinel;
                    } else {// 代表要插入到右边
                        AnurNode modoNext = sentinel.next;
                        if (modoNext != null) {
                            modoNext.prev = node;
                            node.next = modoNext;
                        }

                        sentinel.next = node;
                        node.prev = sentinel;
                    }

                    if (shitaNode != null) {
                        shitaNode.ue = node;
                        node.shita = shitaNode;
                    }

                    shitaNode = node;
                    node = new AnurNode(num);
                }
                addNext = addNext && random();
                sentinel = sentinel.sentinel;
            }

            if (addNext && shitaNode != null) {// 给一个机会网上拓展多一层
                shitaNode.ue = node;
                node.shita = shitaNode;
                TOP_HEAD = node;
            }
        }
        SENTINEL.sentinel = null;
    }

    /**
     * 其实插入和查找可以用同一个，但是为了性能 = = ，还是拆开来写
     * sentinel 主要用于标记 每一层最后一个下来的路径，方便加索引
     */
    private AnurNode doAddRecursive(int val, AnurNode compare) {
        if (compare == null) {
            return null;
        }
        if (compare.prev != null && compare.prev.val > val) {// 先左
            return doAddRecursive(val, compare.prev);
        }
        if (compare.next != null && compare.next.val <= val) {// 后右
            return doAddRecursive(val, compare.next);
        }
        if (compare.shita != null) {// 最后下
            AnurNode sentinelFromResult = doAddRecursive(val, compare.shita);
            sentinelFromResult.sentinel = compare;
            return compare;
        }
        SENTINEL.sentinel = compare;
        return compare;
    }

    private boolean random() {
        return r.nextBoolean();
    }

    private static class AnurNode {

        public AnurNode(Integer val) {
            this.val = val;
        }

        public Integer val;

        public AnurNode ue;

        public AnurNode shita;

        public AnurNode prev;

        public AnurNode next;

        public AnurNode sentinel;
    }

    /**
     * ["Skiplist","add","add","add","add","add","erase","erase","add","search","search","add","erase","search","add","add","add","erase","search","erase","search","search","search","erase","erase","search","erase","add","add","erase","add","search","search","search","search","search"]
     * [[],[9],[4],[5],[6],[9],[2],[1],[2],[7],[4],[5],[6],[5],[6],[7],[4],[3],[6],[3],[4],[3],[8],[7],[6],[7],[4],[1],[6],[3],[4],[7],[6],[1],[0],[3]]
     */
    public static void main(String[] args) {
        Object[][] commond = new Object[2][];
        commond[0] = new String[] {
            "add",
            "add",
            "add",
            "add",
            "add",
            "erase",
            "erase",
            "add",
            "search",
            "search",
            "add",
            "erase",
            "search",
            "add",
            "add",
            "add",
            "erase",
            "search",
            "erase",
            "search",
            "search",
            "search",
            "erase",
            "erase",
            "search",
            "erase",
            "add",
            "add",
            "erase",
            "add",
            "search",
            "search",
            "search",
            "search",
            "search"
        };
        commond[1] = new Integer[] {
            9,
            4,
            5,
            6,
            9,
            2,
            1,
            2,
            7,
            4,
            5,
            6,
            5,
            6,
            7,
            4,
            3,
            6,
            3,
            4,
            3,
            8,
            7,
            6,
            7,
            4,
            1,
            6,
            3,
            4,
            7,
            6,
            1,
            0,
            3
        };

        Skiplist skiplist = new Skiplist();
        for (int i = 0; i < commond[0].length; i++) {
            if (commond[0][i].equals("add")) {
                skiplist.add((Integer) commond[1][i]);
                System.out.print("null");
                System.out.print(",");
            } else if (commond[0][i].equals("erase")) {
                System.out.print(skiplist.erase((Integer) commond[1][i]));
                System.out.print(",");
            } else {
                System.out.print(skiplist.search((Integer) commond[1][i]));
                System.out.print(",");
            }
        }
    }
}

