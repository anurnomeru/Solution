package normal


/**
 * Created by Anur IjuoKaruKas on 2019/10/30
 *
 * 给定一个只包含整数的有序数组，每个元素都会出现两次，唯有一个数只会出现一次，找出这个数。
 * 示例 1:*
 * 输入: [1,1,2,3,3,4,4,8,8]
 * 输出: 2
 * 示例 2:*
 * 输入: [3,3,7,7,10,11,11]
 * 输出: 10
 * 注意: 您的方案应该在 O(log n)时间复杂度和 O(1)空间复杂度中运行。
 *
 * 执行用时 :204 ms
 *, 在所有 kotlin 提交中击败了 100.00% 的用户
 * 内存消耗 : 36.1 MB
 *, 在所有 kotlin 提交中击败了 100.00% 的用户
 */
class SingleNonDuplicate {

    fun singleNonDuplicate(nums: IntArray): Int {
        var num = 0
        for (i in nums.indices) {
            num = nums[i].inv().xor(num)
        }
        return num.inv()
    }

    fun singleNonDuplicateV2(nums: IntArray): Int {
        var num = 0
        for (i in nums.indices) {
            num = nums[i].inv().xor(num)
        }
        return num.inv()
    }
}