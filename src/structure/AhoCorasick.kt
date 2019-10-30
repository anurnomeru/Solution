package structure

import java.util.concurrent.atomic.AtomicInteger


/**
 * Created by Anur IjuoKaruKas on 2019/10/8
 */
object AhoCorasick {

    private val NONE = Node('?')

    val HEAD = NONE

    fun contain(str: String): Boolean {
        val strChar = str.toCharArray()
        return matchIter(HEAD, strChar, 0)
    }

    val counter = AtomicInteger(0)

    fun matchIter(prev: Node, strChar: CharArray, index: Int): Boolean {
        counter.incrementAndGet()
        val c = strChar[index]

        // 能继续往下走
        return if (prev.next.containsKey(c)) {
            val nextNode = prev.next[c]!!

            if (nextNode.endFlag) {
                println("匹配 ${nextNode.word} ")
            }
            if (index + 1 == strChar.size) {
                println("char遍历次数总和 ${counter.get()}")
                println("文本长度 ${strChar.size}")
                return false
            }

            matchIter(nextNode, strChar, index + 1)


        }
        // 不能继续往下走
        else {
            when {
                // 可以跳
                prev.jumping != null && prev.jumping!!.next.containsKey(c) -> {
                    val nextNode = prev.jumping!!.next[c]!!

                    if (nextNode.endFlag) {
                        println("匹配 ${nextNode.word} ")
                    }
                    if (index + 1 == strChar.size) {
                        return false
                    }
                    matchIter(nextNode, strChar, index + 1)

                }
                // 不能跳
                else -> {
                    if (prev == HEAD) {
                        if (index + 1 == strChar.size) {
                            println("char遍历次数总和 ${counter.get()}")
                            println("文本长度 ${strChar.size}")
                            return false
                        }
                        matchIter(HEAD, strChar, index + 1)
                    } else {
                        matchIter(HEAD, strChar, index)
                    }
                }
            }
        }
    }

    fun add(str: String) {
        addIter(HEAD, str.toCharArray(), 0)
    }

    private fun addIter(prev: Node, arr: CharArray, index: Int): Node? {
        val endFlag = arr.size - 1 == index

        val c = arr[index]
        val current = prev.next.compute(c) { _, n ->
            n ?: Node(c)
        }!!.also { it.endFlag = it.endFlag || endFlag }

        return if (!endFlag) {
            addIter(current, arr, index + 1)
        } else {
            current.word = String(arr)
            null
        }
    }

    fun build() {
        buildIter(HEAD)
    }

    /**
     * 构造失败路径
     */
    private fun buildIter(prev: Node) {
        for (child in prev.next.values) {
            when {
                prev == HEAD -> {
                }
                prev.jumping == null ->   // 跳到根下
                    if (HEAD.next.containsKey(child.value)) {
                        child.jumping = HEAD.next[child.value]
                    }
                prev.jumping != null ->
                    if (prev.jumping!!.next.containsKey(child.value)) {
                        child.jumping = prev.jumping!!.next[child.value]
                    } else if (HEAD.next.containsKey(child.value)) {
                        child.jumping = HEAD.next[child.value]
                    }
            }

            buildIter(child)
        }
    }

    class Node(val value: Char, var endFlag: Boolean = false, var next: HashMap<Char, Node> = HashMap(), var jumping: Node? = null, var word: String? = null)

}

fun main() {
    AhoCorasick.add("FINAL")
    AhoCorasick.add("作品")
    AhoCorasick.add("高分")
    AhoCorasick.add("最终幻想")
    AhoCorasick.add("出品")
    AhoCorasick.add("Anur")
    AhoCorasick.add("大猫")
    AhoCorasick.add("其他词语")
    AhoCorasick.add("萌萌哒")
    AhoCorasick.build()

    AhoCorasick.contain("《最终幻想14》是史克威尔艾尼克斯出品的全球经典游戏品牌FINAL FANTASY系列的最新作品,IGN获得9.2高分!全球累计用户突破1600万!")
}