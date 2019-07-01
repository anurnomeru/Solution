package normal

/**
 * Created by Anur IjuoKaruKas on 2019/6/30
 */
class Trie() {

    /** Initialize your data structure here. */
    class Node(val c: Char, val endFlag: Boolean, var firstChild: Node? = null, var nextBro: Node? = null)

    val root: Node = Node(' ', false)

    /** Inserts a word into the trie. */
    fun insert(word: String) {
        doInsert(word)
    }

    /** Inserts a word into the trie. */
    private fun doInsert(word: String, parent: Node = root, pointer: Int = 0) {
        if (pointer > word.length - 1) {
            return
        }

        val endFlag = pointer == word.length - 1
        val nowChar = word[pointer]

//        when (parent.firstChild) {
//            null -> parent.firstChild = Node(nowChar, endFlag)
//            else -> {
//                var temp = parent.firstChild
//                while (true) {
//                    when (temp!!.c.compareTo(nowChar)) {
//                        0 -> doInsert(word, temp, pointer + 1)
//                        1 ->
//                    }
//                }
//            }
//        }
    }

    private fun findRow(c: Char, head: Node): Node {
        val temp = head
        val compareResult = temp.c.compareTo(c)
        while (true) {
            when (compareResult) {
                1 -> {
                }
                else -> {
                }
            }
        }

    }


    /** Returns if the word is in the trie. */
    fun search(word: String): Boolean {

    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    fun startsWith(prefix: String): Boolean {

    }

}