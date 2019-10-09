package structure


/**
 * Created by Anur IjuoKaruKas on 2019/10/8
 */
object Kmp {

    fun solution(tar: String, src: String) {
        val tarArr = tar.toCharArray()
        val commonIndex = IntArray(tarArr.size)

        var matchedIndex = 0
        for (i in tarArr.indices) {
            if (i != matchedIndex && tarArr[i] == tar[matchedIndex]) {
                commonIndex[i] = matchedIndex
                matchedIndex++
            } else {
                commonIndex[i] = 0
                matchedIndex = 0

                if (tar[matchedIndex] == tarArr[0]) matchedIndex++
            }
        }

        val srcArr = src.toCharArray()

        var tarIndex = 0
        var matchingIndex = 0

        while (matchingIndex < srcArr.size) {
            if (tarArr[tarIndex] == srcArr[matchingIndex]) {
                if (tarIndex != tarArr.size - 1) {
                    tarIndex++
                } else {
                    println("from ${matchingIndex - tarIndex} to ${matchingIndex}")
                    tarIndex = commonIndex[tarIndex]
                }
            } else {
                tarIndex = commonIndex[tarIndex]
            }
            matchingIndex++
        }
    }
}

fun main() {
    Kmp.solution("Anur", "anurAnru-Anur-rrrrrAnnur-Anur-")
}