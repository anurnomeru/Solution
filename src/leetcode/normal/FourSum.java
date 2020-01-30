package leetcode.normal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.collect.Lists;

/**
 * Created by Anur IjuoKaruKas on 2020/1/29
 * <p>
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
 * <p>
 * 注意：
 * <p>
 * 答案中不可以包含重复的四元组。
 * <p>
 * 示例：
 * <p>
 * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
 * <p>
 * 满足要求的四元组集合为：
 * [
 * [-1,  0, 0, 1],
 * [-2, -1, 1, 2],
 * [-2,  0, 0, 2]
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/4sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * -3 -2 -1 -1 0 0 1 2 5
 */
public class FourSum {

    public static void main(String[] args) {
        List<List<Integer>> lists = fourSum(new int[] {
            -3,
            -1,
            0,
            2,
            4,
            5
        }, 0);

        System.out.println();
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        int size = nums.length;
        if (size < 4) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList(size);
        for (int num : nums) {
            list.add(num);
        }

        Set<List<Integer>> result = new HashSet<>();
        list.sort(Integer::compareTo);

        int p1;
        int p2;
        int p3;
        int p4;

        p1 = 0;
        p4 = size - 1;

        boolean moveLeft = true;

        while (true) {
            p2 = p1 + 1;
            p3 = p4 - 1;

            if (p2 == p3) {
                if (moveLeft) {
                    moveLeft = false;
                    p1 = 1;
                    continue;
                } else {
                    break;
                }
            }
            inner:
            while (true) {
                int n1 = list.get(p1);
                int n2 = list.get(p2);
                int n3 = list.get(p3);
                int n4 = list.get(p4);
                int sumThisRound = n1 + n2 + n3 + n4;
                System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                if (sumThisRound == target) {
                    List<Integer> l = new ArrayList<>();
                    result.add(l);
                    l.add(n1);
                    l.add(n2);
                    l.add(n3);
                    l.add(n4);
                    p2++;
                } else if (sumThisRound < target) {
                    p3--;
                } else {
                    p2++;
                }
                if (p2 == p3) {
                    break inner;
                }
            }

            if (moveLeft) {
                p1++;
            } else {
                p4--;
            }
        }

        return new ArrayList<>(result);
    }
}
