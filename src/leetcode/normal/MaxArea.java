package leetcode.normal;

/**
 * Created by Anur IjuoKaruKas on 2019/11/21
 *
 * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 说明：你不能倾斜容器，且 n 的值至少为 2。
 *
 * 图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 *
 * 示例:
 *
 * 输入: [1,8,6,2,5,4,8,3,7]
 * 输出: 49
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/container-with-most-water
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxArea {

    public static int maxArea(int[] height) {
        int leftPointer = 0;
        int rightPointer = height.length - 1;

        int max = 0;

        boolean moveLeftSwitcher = false;
        boolean moveRightSwitcher = false;

        while (leftPointer != rightPointer) {
            int leftVal = height[leftPointer];
            int rightVal = height[rightPointer];

            max = Math.max((rightPointer - leftPointer) * Math.min(leftVal, rightVal), max);

            if (moveLeftSwitcher) {
                if (leftVal > rightVal) {
                    moveLeftSwitcher = false;
                    moveRightSwitcher = true;
                } else {
                    leftPointer++;
                }
            } else if (moveRightSwitcher) {
                if (rightVal > leftVal) {
                    moveLeftSwitcher = true;
                    moveRightSwitcher = false;
                } else {
                    rightPointer--;
                }
            } else if (leftVal > rightVal) {
                moveRightSwitcher = true;
                rightPointer--;
            } else {
                moveLeftSwitcher = true;
                leftPointer++;
            }
        }

        return max;
    }

    /**
     * |
     * |       |
     * |    |       |
     * |   |    |       |
     */
    public static void main(String[] args) {
        System.out.println(maxArea(new int[] {
            1,
            2,
            4,
            3
        }));
    }
}
