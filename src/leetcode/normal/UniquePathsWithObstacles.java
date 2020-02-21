package leetcode.normal;

import java.util.HashMap;

/**
 * Created by Anur IjuoKaruKas on 2020/2/21
 * <p>
 * 63. 不同路径 II
 * <p>
 * <p>
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 * <p>
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 * <p>
 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 * <p>
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。
 * <p>
 * 说明：m 和 n 的值均不超过 100。
 * <p>
 * 示例 1:
 * <p>
 * 输入:
 * [
 *   [0,0,0],
 *   [0,1,0],
 *   [0,0,0]
 * ]
 * 输出: 2
 * 解释:
 * 3x3 网格的正中间有一个障碍物。
 * 从左上角到右下角一共有 2 条不同的路径：
 * 1. 向右 -> 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右 -> 向右
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-paths-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class UniquePathsWithObstacles {

    static HashMap<Integer, HashMap<Integer, Integer>> MAPPING = new HashMap<>();

    static HashMap<Integer, Integer> DEFAULT = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("抵达右下角的可能性为：" + uniquePathsWithObstacles(new int[][] {
            new int[] {
                0,
                0
            },
            new int[] {
                1,
                0
            },
            }));
    }

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        MAPPING = new HashMap<>();
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        return uniquePaths(m - 1, n - 1, obstacleGrid);
    }

    // m为长，n为宽
    public static int uniquePaths(int m, int n, int[][] obstacleGrid) {
        if (obstacleGrid[m][n] == 1) {
            return 0;
        }

        Integer result = MAPPING.getOrDefault(m, DEFAULT)
                                .getOrDefault(n, null);

        if (result == null) {
            if (m + n <= 1) {
                return obstacleGrid[0][0] == 1 || obstacleGrid[m][n] == 1 ? 0 : 1;
            }

            int path1 = 0;
            int path2 = 0;

            if (m != 0 && obstacleGrid[m - 1][n] != 1) {
                path1 = uniquePaths(m - 1, n, obstacleGrid);
            }
            if (n != 0 && obstacleGrid[m][n - 1] != 1) {
                path2 += uniquePaths(m, n - 1, obstacleGrid);
            }

            result = path1 + path2;
            Integer finalResult = result;
            System.out.println(String.format("到路径 (%s,%s) 的path为 %s", m, n, result));
            MAPPING.compute(m, (integer, map) -> {
                if (map == null) {
                    map = new HashMap<>();
                }
                map.put(n, finalResult);
                return map;
            });
        }

        return result;
    }
}
