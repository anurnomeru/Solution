package normal

/**
 * Created by Anur IjuoKaruKas on 2019/7/2
 *
 * 给定两个以升序排列的整形数组 nums1 和 nums2, 以及一个整数 k。
 *
 * 定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2。
 *
 * 找到和最小的 k 对数字 (u1,v1), (u2,v2) ... (uk,vk)。
 *
 * 示例 1:
 *
 * 输入: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
 * 输出: [1,2],[1,4],[1,6]
 * 解释: 返回序列中的前 3 对数：
 * [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
 * 示例 2:
 *
 * 输入: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
 * 输出: [1,1],[1,1]
 * 解释: 返回序列中的前 2 对数：
 *      [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
 * 示例 3:
 *
 * 输入: nums1 = [1,2], nums2 = [3], k = 3
 * 输出: [1,3],[2,3]
 * 解释: 也可能序列中所有的数对都被返回:[1,3],[2,3]
 *
 */
class KSmallestPairs {

    fun kSmallestPairs(nums1: IntArray, nums2: IntArray, k: Int): List<List<Int>> {
        val result: MutableList<List<Int>> = mutableListOf()
        doSearchWithPointer(nums1, nums2, k, 0, 0, result)
        return result
    }

    private fun doSearchWithPointer(nums1: IntArray, nums2: IntArray, k: Int, nums1Pointer: Int, nums2Pointer: Int, result: MutableList<List<Int>>) {
        if (k == 0 || nums1.isEmpty() || nums2.isEmpty()) return

        result.add(listOf(nums1[nums1Pointer], nums2[nums2Pointer]))

        val canMoveNum1Point = nums1Pointer < nums1.size - 1
        val canMoveNum2Point = nums2Pointer < nums2.size - 1


        if (canMoveNum1Point && canMoveNum2Point) {



            if (nums1[nums1Pointer] >= nums2[nums2Pointer]) doSearchWithPointer(nums1, nums2, k - 1, nums1Pointer, nums2Pointer + 1, result)
            else doSearchWithPointer(nums1, nums2, k - 1, nums1Pointer + 1, nums2Pointer, result)
        } else if (canMoveNum2Point) doSearchWithPointer(nums1, nums2, k - 1, nums1Pointer, nums2Pointer + 1, result)
        else if (canMoveNum1Point) doSearchWithPointer(nums1, nums2, k - 1, nums1Pointer + 1, nums2Pointer, result)
        else return
    }
}

fun main() {
    val solution = KSmallestPairs()
    val l = solution.kSmallestPairs(intArrayOf(1, 7, 11), intArrayOf(2, 4, 6), 3)

    print("")

}

