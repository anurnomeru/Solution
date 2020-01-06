package leetcode.normal;

import java.util.Arrays;

/**
 * Created by Anur IjuoKaruKas on 2020/1/6
 *
 * https://leetcode-cn.com/problems/3sum-closest/
 *
 * 执行用时 :55 ms, 在所有 Java 提交中击败了 9.23% 的用户
 * 内存消耗 :37.2 MB, 在所有 Java 提交中击败了 80.19% 的用户
 *
 * 16. 最接近的三数之和
 */
public class ThreeSumClosest {

    public static void main(String[] args) {
        System.out.println(threeSumClosest(new int[] {
            13,2,0,-14,-20,19,8,-5,-13,-3,20,15,20,5,13,14,-17,-7,12,-6,0,20,-19,-1,-15,-2,8,-2,-9,13,0,-3,-18,-9,-9,-19,17,-14,-19,-4,-16,2,0,9,5,-7,-4,20,18,9,0,12,-1,10,-17,-11,16,-13,-14,-3,0,2,-18,2,8,20,-15,3,-13,-12,-2,-19,11,11,-10,1,1,-10,-2,12,0,17,-19,-7,8,-19,-17,5,-5,-10,8,0,-12,4,19,2,0,12,14,-9,15,7,0,-16,-5,16,-12,0,2,-16,14,18,12,13,5,0,5,6

        }, -59));
    }

    public static int threeSumClosest(int[] nums, int target) {
        int[] ints = Arrays.stream(nums)
                           .sorted()
                           .toArray();

        int length = ints.length;
        Integer mostClosestIndex = null;
        Integer mostClosestAbs = null;
        Integer mostClosest = null;

        for (int firstPointer = 0; firstPointer < length; firstPointer++) {
            int first = ints[firstPointer];
            for (int secondPointer = firstPointer + 1; secondPointer < length; secondPointer++) {
                int second = ints[secondPointer];

                Integer minAbs = null;
               inner: for (int thirdPointer = mostClosestIndex != null ? mostClosestIndex : length - 1; thirdPointer > secondPointer; thirdPointer--) {
                    int third = ints[thirdPointer];
                    int sum = first + second + third;
                    if (sum == target) {
                        return sum;
                    } else {
                        int abs = Math.abs(sum - target);
                        if (mostClosestAbs == null || mostClosestAbs > abs) {
                            mostClosestIndex = thirdPointer;
                            mostClosest = sum;
                            mostClosestAbs = abs;
                        }

                        if (minAbs == null || minAbs >= abs) {
                            minAbs = abs;
                        } else {
                            break inner;
                        }
                    }
                }
            }
        }

        return mostClosest == null ? target : mostClosest;
    }
}
