// package normal;
//
// import java.util.ArrayList;
// /**
//  * Created by Anur IjuoKaruKas on 2018/10/14
//  *
//  * 给定一个二进制数组, 找到含有相同数量的 0 和 1 的最长连续子数组。
//  *
//  * 示例 1:
//  *
//  * 输入: [0,1]
//  * 输出: 2
//  * 说明: [0, 1] 是具有相同数量0和1的最长连续子数组。
//  * 示例 2:
//  *
//  * 输入: [0,1,0]
//  * 输出: 2
//  * 说明: [0, 1] (或 [1, 0]) 是具有相同数量0和1的最长连续子数组。
//  * 注意: 给定的二进制数组的长度不会超过50000。
//  */
// public class FindMaxLength {
//
//     private int[] nums;
//
//     /**
//      * 1、要找到最长的连续子数组，首先要明确存不存在数组交叉的情况。
//      * 试想存在这种情况： 举例：110011，可知是存在交叉情况的。
//      * 由于说的是最长的情况，所以110011，得出解： 1100,0011。
//      *
//      * 2、由于要找到最长的连续子数组，所以不需要找出 大于已存在头，且小于已存在尾的子数组
//      * 也就是 00110011，只有一个解，即 00110011。
//      *
//      * 3、基于上面两个条件从大的开始找，找到小的更为合理。
//      *
//      * 4、如何找？子数组内相加为数组长度/2即可，且数组长度只会为2的倍数（倍数 > 0）。
//      */
//     public int solution(int[] nums) {
//         this.nums = nums;
//     }
//
//
//     public boolean match(int start, int end) {
//
//         ArrayList arrayList = new ArrayList();
//         arrayList.remove(3);
//
//         int iterTimes = end - start + 1;
//
//         int result = 0;
//         for (int i = 0; i < iterTimes; i++) {
//             result += nums[start + i];
//         }
//
//         return result * 2 == iterTimes;
//     }
//
//     public static class Pair {
//
//         int start;
//
//         int end;
//
//         public Pair() {
//         }
//
//         public Pair(int start, int end) {
//             this.start = start;
//             this.end = end;
//         }
//     }
// }
