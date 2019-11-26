package leetcode.normal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Anur IjuoKaruKas on 2019/11/26
 *
 * 15. 三数之和
 *
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 *
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/3sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ThreeSum {

    /**
     * 写那么恶心其实就一句 答案中不可以包含重复的三元组。
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        int length = nums.length;
        if (length < 3) {
            return list;
        }

        List<Integer> numsL = new ArrayList<>();
        HashMap<Integer, Integer> possibleMap = new HashMap<>();
        for (int num : nums) {
            numsL.add(num);
            possibleMap.compute(num, (k, v) -> v == null ? 1 : v + 1);
        }
        numsL.sort(Integer::compareTo);

        for (int i = 0; i < length; i++) {
            Integer iNum = numsL.get(i);
            Integer count = possibleMap.get(iNum);
            if (count == 1) {
                possibleMap.remove(iNum);
            } else {
                possibleMap.put(iNum, count - 1);
            }
            if (i != length - 1 && numsL.get(i + 1)
                                        .equals(iNum)) {
                continue;
            }

            Integer effectCurrent = null;
            for (int j = 0; j < i; j++) {
                Integer jNum = numsL.get(j);
                if (effectCurrent != null && effectCurrent.equals(jNum)) {
                    continue;
                }
                Integer numPossible = 0 - iNum - jNum;

                if (possibleMap.containsKey(numPossible)) {
                    List<Integer> result = new ArrayList<>();
                    result.add(iNum);
                    result.add(jNum);
                    result.add(numPossible);
                    effectCurrent = jNum;
                    list.add(result);
                }
            }
        }

        return list;
    }

    public static void main(String[] args) {
        System.out.println(threeSum(new int[] {
            0,
            0,
            0
        }));
    }
}

