package normal

import kotlin.math.min

/**
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 * 示例 1：
 *
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 *
 * 输入: "cbbd"
 * 输出: "bb"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
object FindLongestPalindrome {

    fun longestPalindrome(s: String): String {
        val length = s.length

        var longestPalindrome = ""
        for (i in 1 until length) {
            if (s[i] == s[i - 1]) {// 连续一样
                rlIter(s, i - 1, i).takeIf { it.length > longestPalindrome.length }?.also { longestPalindrome = it }
            }
            if (i + 1 < length && s[i - 1] == s[i + 1]) { // 左右一样
                rlIter(s, i - 1, i + 1).takeIf { it.length > longestPalindrome.length }?.also { longestPalindrome = it }
            }
        }
        if (longestPalindrome == "" && s.isNotEmpty()) {
            return s.substring(0, 1)
        }

        return longestPalindrome
    }

    private fun rlIter(s: String, left: Int, right: Int): String {
        var leftPointer = left
        var rightPointer = right

        val maxMoved = Math.min(left, s.length - 1 - right)

        for (i in 0 until maxMoved) {
            if (s[leftPointer - 1] != s[rightPointer + 1]) {
                break
            }
            leftPointer--
            rightPointer++
        }
        return s.substring(leftPointer, rightPointer + 1)
    }
}

fun main() {
    println(FindLongestPalindrome.longestPalindrome("a"))
}
