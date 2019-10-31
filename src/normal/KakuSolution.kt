package normal

import kotlin.math.max


/**
 * Created by Anur IjuoKaruKas on 2019/10/31
 */
object KakuSolution {

    private val leftKaku = '('
    private val rightKaku = ')'

    fun solution(s: String) {
        val toCharArray = s.toCharArray()
        calcRecursive(toCharArray, true)
    }

    fun calcRecursive(s: CharArray, needSplit: Boolean): Int {
        if (s.isEmpty()) return 0

        if (needSplit) {
            for (i in s.indices) {
                if (s[i] != leftKaku && s[i] != rightKaku) {
                    val leftArray = CharArray(i)
                    val rightArray = CharArray(s.size - 1 - i)
                    System.arraycopy(s, 0, leftArray, 0, leftArray.size)
                    System.arraycopy(s, i + 1, rightArray, 0, rightArray.size)

                    // 左右拆解
                    val leftResult = calcRecursive(leftArray, false)
                    val rightResult = calcRecursive(rightArray, true)

                    return 0
                }
            }

            return calcRecursive(s, false)
        } else {
            val max = s.size - 1

            var oriPointer = 0
            var sentinelPointer = 0

            while (oriPointer <= max) {

                if (s[oriPointer] == leftKaku) {            // 发现判断条件
                    sentinelPointer = oriPointer + 1            // 烧饼归位

                    out@ while (true) {
                        if (sentinelPointer > max) {
                            break@out
                        } else if (s[sentinelPointer] == '1') {
                            sentinelPointer++
                        } else if (s[sentinelPointer] == rightKaku) {// 匹配成功
                            s[sentinelPointer] = '1'
                            s[oriPointer] = '1'

                            oriPointer = max(0, oriPointer - 2)
                            break@out
                        } else {
                            break@out
                        }
                    }
                }
                oriPointer++
            }

            println(s)
        }

        return 1
    }
}

fun main() {
    KakuSolution.solution("(()()()()()())(6()()())(8(2()()()()())()()()())(")
}