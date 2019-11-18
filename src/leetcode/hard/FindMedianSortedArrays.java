package leetcode.hard;

/**
 * Created by Anur IjuoKaruKas on 2019/11/18
 *
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 *
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 *
 * 示例 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * 则中位数是 2.0
 * 示例 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * 则中位数是 (2 + 3)/2 = 2.5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindMedianSortedArrays {

    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[] {
            1,
            2
        }, new int[] {
            3,
        }));
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int length = length1 + length2;

        boolean odd = (length & 1) == 1;

        int middle = (length / 2);
        if (!odd) {
            middle--;
        }
        // 如果是偶数，那么中位数为 中间靠右与靠左两个数字，即 长度/2 -1 长度为10，则10/2=5， 第5,6为中位数，但是下标要减一
        // 如果是奇数，那么中位数为 中间那个数，即 11/2 = 5, 第六个数为中位数，不需要减一

        int num1Pointer = 0;
        int num2Pointer = 0;
        for (int i = 0; i < middle; i++) {
            if (num1Pointer == length1) {
                num2Pointer++;
            } else if (num2Pointer == length2) {
                num1Pointer++;
            } else {
                int i1 = nums1[num1Pointer];
                int i2 = nums2[num2Pointer];

                if (i1 > i2) {
                    num2Pointer++;
                } else {
                    num1Pointer++;
                }
            }
        }

        int iterTimes = odd ? 1 : 2;

        int nums = 0;
        for (int i = 0; i < iterTimes; i++) {
            boolean b1 = num1Pointer == length1;
            boolean b2 = num2Pointer == length2;

            if (b1 && b2) {
                break;
            }

            if (b1) {
                nums += nums2[num2Pointer];
                num2Pointer++;
            } else if (b2) {
                nums += nums1[num1Pointer];
                num1Pointer++;
            } else {
                int i1 = nums1[num1Pointer];
                int i2 = nums2[num2Pointer];

                if (i1 > i2) {
                    nums += nums2[num2Pointer];
                    num2Pointer++;
                } else {
                    nums += nums1[num1Pointer];
                    num1Pointer++;
                }
            }
        }

        return odd ? nums : nums / 2d;
    }
}
