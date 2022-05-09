package lesson4

import java.util.*

/**
 * Префиксное дерево для строк
 */
class KtTrie : AbstractMutableSet<String>(), MutableSet<String> {

    private class Node {
        val children: SortedMap<Char, Node> = sortedMapOf()
    }

    private val root = Node()

    override var size: Int = 0
        private set

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun String.withZero() = this + 0.toChar()

    private fun findNode(element: String): Node? {
        var current = root
        for (char in element) {
            current = current.children[char] ?: return null
        }
        return current
    }

    override fun contains(element: String): Boolean =
        findNode(element.withZero()) != null

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        for (char in element.withZero()) {
            val child = current.children[char]
            if (child != null) {
                current = child
            } else {
                modified = true
                val newChild = Node()
                current.children[char] = newChild
                current = newChild
            }
        }
        if (modified) {
            size++
        }
        return modified
    }

    override fun remove(element: String): Boolean {
        val current = findNode(element) ?: return false
        if (current.children.remove(0.toChar()) != null) {
            size--
            return true
        }
        return false
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    override fun iterator(): MutableIterator<String> {
        return TrieIterator()
    }

    inner class TrieIterator internal constructor() : MutableIterator<String> {

        private val nodes = Stack<Pair<Node, Char>>()
        private var current: Node? = null

        init {
            nodes.push(root to 0.toChar())
        }

        /**
         * временные затраты: O(1)
         * ресурсоемкость: O(1)
         */
        override fun hasNext(): Boolean {
            nodes.reversed().forEach {
                if (it.hasNext()) return true
            }
            return false
        }

        /**
         * временные затраты: O(1)
         * ресурсоемкость: O(1)
         */
        override fun next(): String {
            var current = nodes.pop()
            this.current = current.first

            while (!current.hasNext()) {
                if (nodes.empty()) throw NoSuchElementException()
                current = nodes.pop()
            }

            val next = current.next()
            nodes.push(next)
            fill()
            return nodes.subList(0, nodes.lastIndex).joinToString("") { it.second.toString() }
        }

        /**
         * временные затраты: O(1)
         * ресурсоемкость: O(1)
         */
        override fun remove() {
            if (current == null) throw IllegalStateException()

            var last = nodes.pop()
            val hasAnotherAtThisBranch = last.first.children.size > 1

            if (hasAnotherAtThisBranch) {
                last.first.children.remove(last.second)
                nodes.push(last)
            } else {
                while (last.first.children.size == 1 && nodes.isNotEmpty()) {
                    last.first.children.remove(last.second)
                    last = nodes.pop()
                }

                nodes.push(last.previousOrFirst())
                last.first.children.remove(last.second)
            }
            size--
            current = null
        }

        private fun Pair<Node, Char>.hasNext(): Boolean {
            val nextIdx = first.children.keys.indexOf(second) + 1
            return first.children.keys.size > nextIdx
        }

        private fun Pair<Node, Char>.next(): Pair<Node, Char> {
            val nextId = first.children.keys.indexOf(second) + 1
            return first to first.children.keys.toList()[nextId]!!
        }

        private fun Pair<Node, Char>.previousOrFirst(): Pair<Node, Char> {
            val prevId = first.children.keys.indexOf(second).let { if (it < 1) 0 else it - 1 }
            return first to first.children.keys.toList()[prevId]!!
        }

        private fun fill() {
            var current = nodes.peek()
            while (current.second != 0.toChar()) {
                val nextNode = current.first.children[current.second]!!
                val nextKey = nextNode.children.firstKey()

                val next = nextNode to nextKey
                nodes.push(next)
                current = next
            }
        }
    }
}
