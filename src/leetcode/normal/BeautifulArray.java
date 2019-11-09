package leetcode.normal;

import java.util.Arrays;

/**
 * Created by Anur IjuoKaruKas on 2019/11/5
 *
 * 对于某些固定的 N，如果数组 A 是整数 1, 2, ..., N 组成的排列，使得：
 *
 * 对于每个 i < j，都不存在 k 满足 i < k < j 使得 A[k] * 2 = A[i] + A[j]。
 *
 * 那么数组 A 是漂亮数组。
 *
 * 给定 N，返回任意漂亮数组 A（保证存在一个）。
 *
 * 示例 1：
 *
 * 输入：4
 * 输出：[2,1,4,3]
 * 示例 2：
 *
 * 输入：5
 * 输出：[3,1,2,5,4]
 *
 * 提示：
 *
 * 1 <= N <= 1000
 *
 * 执行用时 :1 ms, 在所有 java 提交中击败了 98.64% 的用户
 * 内存消耗 : 36.5 MB , 在所有 java 提交中击败了68.97% 的用户
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/beautiful-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 错误思路：
 *
 * 我们为将每个元素声明为类，值n类中包含属性 2*n 的值，左边不可放置的值的set，以及右边不可放置的值的set，
 * 每次插入倒序插入，只需扫描set即可（这个解法越做越复杂）
 *
 * 正确思路：
 *
 * 可知 有  k   满足 i     < k  < j    使得 A[k] * 2 = A[i] + A[j]。
 *    则有  k+b  满足 i+b < k+b < j+b  使得 A[k+b] * 2 = A[i+b] + A[j+b]。
 *    也有  k*b  满足 i*b < k*b < j*b  使得 A[k*b] * 2 = A[i*b] + A[j*b]。
 *
 *    又有 k * 2 必定为偶数
 *    且 奇数+偶数一定等于奇数
 *
 *    所以序列 1,2,3,4,5 可拆解为 奇数部分 135  与偶数部分 24 ，必定不存在一个数 k，
 *    使得奇数部分的任意一个数 + 偶数部分任意一个数，等于偶数
 *
 *    且，135 之间的关系，也就是 1+5 = 3*2  可以等值于  246 之间的关系，此时b为1
 *    因为：有  k+b  满足 i+b < k+b < j+b  使得 A[k+b] * 2 = A[i+b] + A[j+b]。
 *
 *    又可以等值为 123 之间的关系
 *    因为：有  k*b  满足 i*b < k*b < j*b  使得 A[k*b] * 2 = A[i*b] + A[j*b]。
 *
 *    故 123 又可以如上拆解为 132，依次循环
 *
 *    123456789
 *    ——>
 *    13579 2468
 *    ->
 *  可以视作 12345 1234
 *    故可以 13524 1324
 *    ->
 *   最终结果： 15937 2648
 *
 */
public class BeautifulArray {

    private int mask = 1;

    public int[] beautifulArray(int N) {
        int middle = N / 2 ;
        if ((N & mask) == mask) {// 代表是奇数
            middle += 1;
        }
        int[] arr = new int[N];

        int step = 1;
        for (int i = 0; i < middle; i++) {//
            arr[i] = step;
            step += 2;
        }

        int range = N - middle;
        step = 2;
        for (int i = 0; i < range ; i++) {
            arr[middle + i] = step;
            step += 2;
        }

        specifyRecursive(arr, 0, middle - 1);
        specifyRecursive(arr, middle, arr.length - 1);
        return arr;
    }

    private void specifyRecursive(int[] arr, int from, int to) {
        int specifyNum = to - from + 1;
        if (specifyNum > 2) {
            int middleIndex = specifyNum / 2 - 1;
            if ((specifyNum & mask) == mask) {// 代表奇数个数，比如11，分为 6和5
                middleIndex += 1;
            }

            int[] sorted = new int[specifyNum];

            int sentinel = from;
            for (int i = 0; i <= middleIndex; i++) {
                sorted[i] = arr[sentinel];
                sentinel += 2;
            }

            sentinel = from + 1;
            for (int i = 1; i <= specifyNum - 1 - middleIndex; i++) {
                sorted[i + middleIndex] = arr[sentinel];
                sentinel += 2;
            }

            System.arraycopy(sorted, 0, arr, from, specifyNum);

            int newTo = from + middleIndex;
            specifyRecursive(arr, from, newTo);
            specifyRecursive(arr, newTo + 1, to);
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new BeautifulArray().beautifulArray(1000)));
    }
}
