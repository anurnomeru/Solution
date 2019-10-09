package structure


/**
 * Created by Anur IjuoKaruKas on 2019/10/8
 */
object DicTree {

    private val NONE = Node('?')

    val HEAD = NONE

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
            when (prev.jumping) {
                null ->   // 跳到根下
                    if (HEAD.next.containsKey(child.value)) {
                        child.jumping = HEAD.next[child.value]
                    }
                else ->
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
    DicTree.add("his")
    DicTree.add("her")
    DicTree.add("him")
    DicTree.add("this")
    DicTree.add("dkflhisherthishim")
    DicTree.build()

    println()
}