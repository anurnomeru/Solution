package normal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anur IjuoKaruKas on 2019/5/3
 *
 * 给定一个整数数组 A，返回其中元素之和可被 K 整除的（连续、非空）子数组的数目。
 *
 *
 * 示例：
 *
 * 输入：A = [4,5,0,-2,-3,1], K = 5
 * 输出：7
 * 解释：
 * 有 7 个子数组满足其元素之和可被 K = 5 整除：
 * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
 *
 *
 * 提示：
 *
 * 1 <= A.length <= 30000
 * -10000 <= A[i] <= 10000
 * 2 <= K <= 10000
 */
public class SubarraysDivByK {

    public static void main(String[] args) {
        SubarraysDivByK subarraysDivByK = new SubarraysDivByK();
        System.out.println(subarraysDivByK.subarraysDivByK(new int[] {
            4,
            5,
            0,
            -2,
            -3,
            1
        }, 5));
    }

    public int subarraysDivByK(int[] A, int K) {
        int length = A.length;

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            int tempLength = length - i;
            int[] temp = new int[tempLength];

            System.arraycopy(A, i, temp, 0, tempLength);
            list.addAll(sumIter(temp));
        }

        int count = 0;
        for (Integer i : list) {
            if (i % K == 0) {
                count++;
            }
        }

        return count;
    }

    public List<Integer> sumIter(int[] A) {
        List<Integer> list = new ArrayList<>();

        int temp = 0;
        for (int i = 0; i < A.length; i++) {
            temp += A[i];
            list.add(temp);
        }

        return list;
    }
}
