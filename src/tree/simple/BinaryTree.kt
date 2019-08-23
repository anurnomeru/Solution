package tree.simple

import java.util.Random

/**
 * Created by Anur IjuoKaruKas on 2019/6/29
 */
object BinaryTree {

    class Node(val value: Int, var left: Node? = null, var right: Node? = null)

    class BinaryTree(private var baseNode: Node? = null) {

        fun add(i: Int) {
            baseNode?.let { doAdd(it, i) } ?: let { baseNode = Node(i) }
        }

        fun traverse(doSomeThing: (Node, Int) -> Unit) {
            baseNode?.let { doTraverse(it, doSomeThing) }
        }

        private fun doAdd(node: Node, i: Int) {
            when {
                i == node.value -> println("喵喵喵？树中已有此节点")
                i < node.value -> node.left?.let {doAdd(it, i)  }?:let { node.left == null }
                else ->  if (node.right == null) node.right = Node(i) else doAdd(node.right!!, i)
            }
        }

        private fun doTraverse(node: Node, doSomeThing: (Node, Int) -> Unit, times: Int = 0) {
            node.left?.let { doTraverse(it, doSomeThing, times + 1) }
            doSomeThing(node, times)
            node.right?.let { doTraverse(it, doSomeThing, times + 1) }
        }
    }

}

fun main() {


    val tree: BinaryTree.BinaryTree = BinaryTree.BinaryTree()
    val random = Random()

    for (i in (1..1000000)) {
        tree.add(random.nextInt())
    }

    tree.traverse { node, times -> println("喵喵喵${node.value}, $times") }
}