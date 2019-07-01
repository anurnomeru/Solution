package normal

/**
 * Created by Anur IjuoKaruKas on 2019/6/30
 */
class Trie() {

    /** Initialize your data structure here. */
    class Node(val c: Char, var endFlag: Boolean, var firstChild: Node? = null, var nextBro: Node? = null)

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

        val node = findRow(nowChar, parent, { Node(nowChar, endFlag) }, endFlag)
        doInsert(word, node!!, pointer + 1)
    }


    /** Returns if the word is in the trie. */
    fun search(word: String): Boolean {
        return doSearch(word)
    }

    private fun doSearch(word: String, parent: Node = root, pointer: Int = 0): Boolean {
        if (pointer > word.length - 1) {
            return false
        }

        val endFlag = pointer == word.length - 1
        val nowChar = word[pointer]

        val node = findRow(nowChar, parent)

        if (node == null) {
            return false
        } else if (endFlag && node.endFlag) {
            return true
        }
        return doSearch(word, node, pointer + 1)
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    fun startsWith(prefix: String): Boolean {
        return doStartsWith(prefix)
    }

    private fun doStartsWith(word: String, parent: Node = root, pointer: Int = 0): Boolean {
        if (pointer > word.length - 1) {
            return false
        }

        val endFlag = pointer == word.length - 1
        val nowChar = word[pointer]

        val node = findRow(nowChar, parent) ?: return false

        if (endFlag) {
            return true
        }

        return doStartsWith(word, node, pointer + 1)
    }


    private fun findRow(c: Char, parent: Node, whileNotExist: (() -> Node)? = null, endFlag: Boolean = false): Node? {
        var temp = parent.firstChild
        when (temp?.c?.compareTo(c)) {
            null -> {
                parent.firstChild = whileNotExist?.invoke()
                return parent.firstChild
            }
            1 -> {
                parent.firstChild = whileNotExist?.invoke()
                parent.firstChild?.nextBro = temp
                return parent.firstChild
            }
            else -> {
                do {
                    when (temp!!.c.compareTo(c)) {
                        1 -> println("impossible!!!")
                        0 -> {
                            temp.endFlag = temp.endFlag || endFlag
                            return temp
                        }
                        -1 -> {
                            if (temp.nextBro == null || temp.nextBro?.c?.compareTo(c) == 1) {
                                return if (whileNotExist == null) {
                                    null
                                } else {
                                    val next = temp.nextBro
                                    temp.nextBro = whileNotExist.invoke()
                                    temp.nextBro!!.nextBro = next
                                    temp.nextBro
                                }
                            }
                        }
                    }
                    temp = temp.nextBro
                } while (temp != null)
            }
        }
        return null
    }
}

fun main() {
    val t = Trie()
    t.insert("archive")
    t.insert("accept")
    t.insert("admin")

    println(t.search("accept"))
    println(t.search("archive"))
    println(t.search("archiv"))

    println(t.startsWith("archiv"))
    println(t.startsWith("archi"))
    println(t.startsWith("adm"))

    println()
}