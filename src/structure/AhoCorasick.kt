package structure


/**
 * Created by Anur IjuoKaruKas on 2019/10/8
 */
object AhoCorasick {

    private val NONE = Node('?')

    val HEAD = NONE

    fun containOne(str: String): Boolean {
        val strChar = str.toCharArray()
        return matchIter(HEAD, strChar, 0)
    }

    fun matchIter(prev: Node, strChar: CharArray, index: Int): Boolean {
        val c = strChar[index]

        // 能继续往下走
        return if (prev.next.containsKey(c)) {
            val nextNode = prev.next[c]!!

            println(c)
            return if (nextNode.endFlag) {
                true
            } else {
                if (index + 1 == strChar.size) {
                    return false
                }

                matchIter(nextNode, strChar, index + 1)
            }

        }
        // 不能继续往下走
        else {
            when {
                // 可以跳
                prev.jumping != null && prev.jumping!!.next.containsKey(c) -> {
                    val nextNode = prev.jumping!!.next[c]!!

                    println("jump $c")
                    return if (nextNode.endFlag) {
                        true
                    } else {
                        if (index + 1 == strChar.size) {
                            return false
                        }
                        matchIter(nextNode, strChar, index + 1)
                    }
                }
                // 不能跳
                else -> {
                    if (prev == HEAD) {
                        if (index + 1 == strChar.size) {
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

    class Node(val value: Char, var endFlag: Boolean = false, var next: HashMap<Char, Node> = HashMap(), var jumping: Node? = null)

}

fun main() {
    AhoCorasick.add("black")
    AhoCorasick.add("yellow")
    AhoCorasick.add("blue")
    AhoCorasick.add("anur")
    AhoCorasick.add("apple")
    AhoCorasick.add("plan")
    AhoCorasick.build()

    println(AhoCorasick.containOne("anublapplan"))
}