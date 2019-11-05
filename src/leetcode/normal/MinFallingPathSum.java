package leetcode.normal;

/**
 * Created by Anur IjuoKaruKas on 2019/11/4
 * 931. 下降路径最小和
 *
 * 给定一个方形整数数组 A，我们想要得到通过 A 的下降路径的最小和。
 *
 * 下降路径可以从第一行中的任何元素开始，并从每一行中选择一个元素。在下一行选择的元素和当前行所选元素最多相隔一列。
 *
 * 示例：
 *
 * 输入：[[1,2,3],[4,5,6],[7,8,9]]
 * 输出：12
 * 解释：
 * 可能的下降路径有：
 * [1,4,7], [1,4,8], [1,5,7], [1,5,8], [1,5,9]
 * [2,4,7], [2,4,8], [2,5,7], [2,5,8], [2,5,9], [2,6,8], [2,6,9]
 * [3,5,7], [3,5,8], [3,5,9], [3,6,8], [3,6,9]
 * 和最小的下降路径是 [1,4,7]，所以答案是 12。
 *
 * 提示：
 *
 * 1 <= A.length == A[0].length <= 100
 * -100 <= A[i][j] <= 100
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-falling-path-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 执行用时 : 5 ms, 在所有 java 提交中击败了 84.00% 的用户
 * 内存消耗 : 37.5 MB, 在所有 java 提交中击败了 95.15% 的用户
 */
public class MinFallingPathSum {

    /**
     * 直接暴力 = =
     */
    public int minFallingPathSum(int[][] A) {
        int row = A.length;
        int columnSize = A[0].length;
        int columnMaxIndex = A[0].length - 1;

        if (row != 1) {// 最特殊的情况{
            for (int i = row - 2; i >= 0; i--) {
                for (int j = 0; j < columnSize; j++) {
                    int numPrev = 100;
                    int numNext = 100;
                    if (j != 0) {
                        numPrev = A[i + 1][j - 1];
                    }
                    if (j != columnMaxIndex) {
                        numNext = A[i + 1][j + 1];
                    }

                    int num = A[i + 1][j];

                    int min = Math.min(Math.min(numPrev, num), numNext);
                    A[i][j] = min + A[i][j];
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < columnSize; i++) {
            if (A[0][i] < min) {
                min = A[0][i];
            }
        }
        return min;
    }
}
