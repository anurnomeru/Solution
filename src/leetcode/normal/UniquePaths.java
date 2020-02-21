package leetcode.normal;

import java.util.HashMap;

/**
 * Created by Anur IjuoKaruKas on 2020/2/21
 * <p>
 * 62. 不同路径
 * <p>
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 * <p>
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 * <p>
 * 问总共有多少条不同的路径？
 * <p>
 * 例如，上图是一个7 x 3 的网格。有多少可能的路径？
 * <p>
 * 说明：m 和 n 的值均不超过 100。
 * <p>
 * 示例 1:
 * <p>
 * 输入: m = 3, n = 2
 * 输出: 3
 * 解释:
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向右 -> 向下
 * 2. 向右 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向右
 * 示例 2:
 * <p>
 * 输入: m = 7, n = 3
 * 输出: 28
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-paths
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class UniquePaths {

    static HashMap<Integer, HashMap<Integer, Integer>> MAPPING = new HashMap<>();

    static HashMap<Integer, Integer> DEFAULT = new HashMap<>();

    // m为长，n为宽
    public static int uniquePaths(int m, int n) {
        Integer result = MAPPING.getOrDefault(m, DEFAULT)
                                .getOrDefault(n, null);

        if (result == null) {
            if ((m == 1 && n == 2) || (m == 2 && n == 1) || (m == 1 && n == 1)) {
                return 1;
            }

            int path1 = 0;
            int path2 = 0;

            if (m != 1) {
                path1 = uniquePaths(m - 1, n);
            }
            if (n != 1) {
                path2 += uniquePaths(m, n - 1);
            }

            result = path1 + path2;
            Integer finalResult = result;
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
