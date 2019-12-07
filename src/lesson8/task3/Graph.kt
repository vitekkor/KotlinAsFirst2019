package lesson8.task3

import lesson8.task2.Square
import java.util.*

class Graph {
    private data class Vertex(val name: String) {
        val neighbors = mutableSetOf<Vertex>()
    }

    private val vertices = mutableMapOf<String, Vertex>()

    private operator fun get(name: String) = vertices[name] ?: throw IllegalArgumentException()

    fun addVertex(name: String) {
        vertices[name] = Vertex(name)
    }

    private fun connect(first: Vertex, second: Vertex) {
        first.neighbors.add(second)
        second.neighbors.add(first)
    }

    fun connect(first: String, second: String) = connect(this[first], this[second])

    /**
     * Пример
     *
     * По двум вершинам рассчитать расстояние между ними = число дуг на самом коротком пути между ними.
     * Вернуть -1, если пути между вершинами не существует.
     *
     * Используется поиск в ширину
     */
    fun bfs(start: String, finish: String) = bfs(this[start], this[finish])

    private fun bfs(start: Vertex, finish: Vertex): Int {
        val queue = ArrayDeque<Vertex>()
        queue.add(start)
        val visited = mutableMapOf(start to 0)
        while (queue.isNotEmpty()) {
            val next = queue.poll()
            val distance = visited[next]!!
            if (next == finish) return distance
            for (neighbor in next.neighbors) {
                if (neighbor in visited) continue
                visited[neighbor] = distance + 1
                queue.add(neighbor)
            }
        }
        return -1
    }

    fun bffs(start: String, finish: String): List<Square> {
        if (start == finish) return listOf(Square(start.split(" ")[0].toInt(), start.split(" ")[1].toInt()))
        val way = bffs(this[start], this[finish])
        val distance = way[Vertex("great2be")]
        val result = mutableListOf<Square>()
        outer@ for (i in distance!! downTo 0) {
            val check = way.filter { it.value == i }
            val current =
                if (result.isEmpty()) this[finish] else this["${result.last().column} ${result.last().row}"]
            for ((key) in check) if (key in current.neighbors) {
                result.add(Square(key.name.split(" ")[0].toInt(), key.name.split(" ")[1].toInt()))
                continue@outer
            }
        }
        return result.reversed()
    }

    private fun bffs(start: Vertex, finish: Vertex): Map<Vertex, Int> {
        val queue = ArrayDeque<Vertex>()
        queue.add(start)
        val visited = mutableMapOf(start to 0)
        while (queue.isNotEmpty()) {
            val next = queue.poll()
            val distance = visited[next]!!
            if (next == finish) {
                visited[Vertex("great2be")] = distance
                return visited
            }
            for (neighbor in next.neighbors) {
                if (neighbor in visited) continue
                visited[neighbor] = distance + 1
                queue.add(neighbor)
            }
        }
        return mutableMapOf()
    }

    /**
     * Пример
     *
     * По двум вершинам рассчитать расстояние между ними = число дуг на самом коротком пути между ними.
     * Вернуть -1, если пути между вершинами не существует.
     *
     * Используется поиск в глубину
     */
    fun dfs(start: String, finish: String): Int = dfs(this[start], this[finish], setOf()) ?: -1

    private fun dfs(start: Vertex, finish: Vertex, visited: Set<Vertex>): Int? =
        if (start == finish) 0
        else {
            val min = start.neighbors
                .filter { it !in visited }
                .mapNotNull { dfs(it, finish, visited + start) }
                .min()
            if (min == null) null else min + 1
        }
}
