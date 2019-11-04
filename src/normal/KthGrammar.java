package normal;

/**
 * Created by Anur IjuoKaruKas on 2019/11/1
 *
 * 在第一行我们写上一个 0。接下来的每一行，将前一行中的0替换为01，1替换为10。
 *
 * 给定行数 N 和序数 K，返回第 N 行中第 K个字符。（K从1开始）
 *
 *
 * 例子:
 *
 * 输入: N = 1, K = 1
 * 输出: 0
 *
 * 输入: N = 2, K = 1
 * 输出: 0
 *
 * 输入: N = 2, K = 2
 * 输出: 1
 *
 * 输入: N = 4, K = 5
 * 输出: 1
 *
 * 解释:
 * 第一行: 0
 * 第二行: 0         1
 * 第三行: 01       1 0
 * 第四行: 0110     1001
 * 第五行：01101001 10010110
 * 第六行：01101001 10010110 10010110 01101001
 * 01101001 10010110 10010110 0110100110010110011010010110
 * 注意：
 *
 * N 的范围 [1, 30].
 * K 的范围 [1, 2^(N-1)].
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/k-th-symbol-in-grammar
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 执行用时 :0 ms, 在所有 java 提交中击败了 100.00% 的用户
 * 内存消耗 :32.9 MB, 在所有 java 提交中击败了 86.42% 的用户
 */
public class KthGrammar {

    /**
     * 观察规律可得
     * 1、如果将 2的n次方折半，则得到左右镜像，譬如
     * 0110 1001
     * 0110 = ~1001
     * 其中任意一部分，又有 01 = ~10   10 = ~01
     *
     * 也就是说有偏移量 x，折中点 n，可得到 int[x] = ~int[n+x]
     *
     * 2、如果我们通过位运算，很容易得出上述折中点（其实就是2的n次方）
     *
     * 3、跳出循环，当结果等于1 或者 0时直接跳出循环
     *
     * 4、循环时计算取反次数，折算一次，取反一次
     */
    public int kthGrammar(int N, int K) {
        if (K == 0) {
            return 0;
        }
        K = K - 1;// 马丹 题目中的1 指的是下标为0的那个元素！
        int J = min2NNum(K);

        int shiftCount = 0;
        while (K != 0 && K != 1) {
            K = K - J; // 折半
            J = J >> 1; // mask右移
            if (K < J) {
                J = min2NNum(K);
            }
            shiftCount++;
        }

        return shiftCount % 2 == 0 ? K : K == 1 ? 0 : 1;
    }

    public int min2NNum(int num) {
        num = num | (num >> 1);
        num = num | (num >> 2);
        num = num | (num >> 3);
        num = num | (num >> 4);
        return (num >> 1) + 1;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.print(new KthGrammar().kthGrammar(1, i));
        }
    }
}
