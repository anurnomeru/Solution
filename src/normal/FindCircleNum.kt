package normal


/**
 * Created by Anur IjuoKaruKas on 2019/10/31
 *
 * 班上有 N 名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知 A 是 B 的朋友，B 是 C 的朋友，那么我们可以认为 A 也是 C 的朋友。所谓的朋友圈，是指所有朋友的集合。
 * 给定一个 N * N 的矩阵 M，表示班级中学生之间的朋友关系。如果M[i][j] = 1，表示已知第 i 个和 j 个学生互为朋友关系，否则为不知道。你必须输出所有学生中的已知的朋友圈总数。
 *
 * 示例 1:
 *
 * 输入:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * 输出: 2
 * 说明：已知学生0和学生1互为朋友，他们在一个朋友圈。
 * 第2个学生自己在一个朋友圈。所以返回2。
 *
 *
 * 示例 2:
 *
 * 输入:
 * [[1,1,0],
 * [1,1,1],
 * [0,1,1]]
 * 输出: 1
 * 说明：已知学生0和学生1互为朋友，学生1和学生2互为朋友，所以学生0和学生2也是朋友，所以他们三个在一个朋友圈，返回1。
 * 注意：
 *
 * N 在[1,200]的范围内。
 * 对于所有学生，有M[i][i] = 1。
 * 如果有M[i][j] = 1，则有M[j][i] = 1。
 *
 * 执行用时 :308 ms, 在所有 kotlin 提交中击败了100.00%的用户
 * 内存消耗 :43.1 MB, 在所有 kotlin 提交中击败了100.00%的用户
 *
 */
object FindCircleNum {
    fun findCircleNum(M: Array<IntArray>): Int {
        var color = 2
        var hasMarked = IntArray(M.size)
        for (i in M.indices) {
            if (colorRecursive(M, i, hasMarked, color)) {
                color++
            }
        }
        return color - 2
    }

    /**
     * 心得：审题很重要！！不要想当然！
     *
     * index 来表示当前为第几个学生
     * hasMarked 来进行排重，避免重复扫描
     * color 用于染色
     */
    private fun colorRecursive(M: Array<IntArray>, index: Int, hasMarked: IntArray, color: Int): Boolean {
        if (hasMarked[index] != 0) {
            return false
        }

        hasMarked[index] = 1
        val friendShip = M[index]
        for (i in friendShip.indices) {
            if (hasMarked[i] == 0 && friendShip[i] == 1) {// 需要同时满足未被标记过，下标为1
                colorRecursive(M, i, hasMarked, color)
            }
        }
        return true
    }
}

fun main() {
    var arr1 = intArrayOf(1, 1, 0)
    var arr2 = intArrayOf(1, 1, 0)
    var arr3 = intArrayOf(0, 0, 1)
    var arr = arrayOf(arr1, arr2, arr3)

    println(FindCircleNum.findCircleNum(arr))
}