package normal

object ReserveK {

    var head: Node? = null

    fun print() {
        var gonaPrint = head
        while (gonaPrint != null) {
            println(gonaPrint.value)
            gonaPrint = gonaPrint.next
        }
    }

    fun reserve() {
        if (head == null) return
        doReserve(head!!)
    }

    private fun doReserve(current: Node) {
        val next = current.next
        if (next == null) {
            head = current
            return
        }
        doReserve(next)
        next.next = current
        current.next = null
    }


    fun insert(value: Int) {
        if (head == null) {
            head = Node(value)
        } else doInsert(head!!, value)
    }

    private fun doInsert(prev: Node, value: Int) {
        prev.next?.let { doInsert(it, value) } ?: let { prev.next = Node(value) }
    }

    class Node(val value: Int) {
        var next: Node? = null
    }
}

fun main() {
    ReserveK.insert(1)
    ReserveK.insert(2)
    ReserveK.insert(3)
    ReserveK.insert(4)
    ReserveK.insert(5)
    ReserveK.insert(6)
    ReserveK.insert(7)
    ReserveK.insert(8)
    ReserveK.insert(9)
    ReserveK.insert(10)
    ReserveK.insert(11)
    ReserveK.insert(12)
    ReserveK.reserve()
    ReserveK.print()

    "".hashCode()

}
