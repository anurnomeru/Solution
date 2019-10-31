package normal

import kotlin.math.max


/**
 * Created by Anur IjuoKaruKas on 2019/10/31
 *
 * 给一串字符串，求最大连续括号的数量，有效情况包括
 * ()()  -> 2
 * (())  -> 2
 * ()(() -> 1 因为括号不连续
 * ()(()) ->3
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
                            oriPointer++
                            break@out
                        } else if (s[sentinelPointer] == '1') {
                            sentinelPointer++
                        } else if (s[sentinelPointer] == rightKaku) {// 匹配成功
                            s[sentinelPointer] = '1'
                            s[oriPointer] = '1'

                            // 匹配成功后进行回溯
                            var backTo = oriPointer
                            inner@ for (j in backTo downTo 0) {
                                if (s[j] != '1') {
                                    backTo = j
                                    break@inner
                                }
                            }
                            oriPointer = max(0, backTo)// 进行回溯
                            break@out
                        } else {
                            oriPointer++
                            break@out
                        }
                    }
                } else {
                    oriPointer++
                }
            }

            println(s)
        }

        return 1
    }
}

fun main() {
    KakuSolution.solution("((((()()()))))")
}