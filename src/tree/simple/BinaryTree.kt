package tree.simple

import java.util.Random

/**
 * Created by Anur IjuoKaruKas on 2019/6/29
 */
object BinaryTree {

    class BinaryTree(var baseNode: Node? = null) {

        fun add(i: Int) {
            if (baseNode == null) baseNode = Node(i)
            else add(baseNode!!, i)
        }

        fun traverse(doSomeThing: (Node, Int) -> Unit) {
            if (baseNode == null) println("喵喵喵？？")
            else traverse(baseNode!!, doSomeThing)
        }

        private fun add(node: Node, i: Int) {
            if (i == node.value) println("喵喵喵？树中已有此节点")
            if (i < node.value) {
                if (node.left == null) node.left = Node(i) else add(node.left!!, i)
            } else {
                if (node.right == null) node.right = Node(i) else add(node.right!!, i)
            }
        }

        private fun traverse(node: Node, doSomeThing: (Node, Int) -> Unit, times: Int = 0) {
            if (node.left != null) traverse(node.left!!, doSomeThing, times + 1)
            doSomeThing(node, times)
            if (node.right != null) traverse(node.right!!, doSomeThing, times + 1)
        }
    }
}


class Node(val value: Int, var left: Node? = null, var right: Node? = null)

fun main() {
    val tree: BinaryTree.BinaryTree = BinaryTree.BinaryTree()
    val random = Random()

    for (i in (1..1000000)) {
        tree.add(random.nextInt())
    }

    tree.traverse { node, times -> println("喵喵喵${node.value}, $times") }
}