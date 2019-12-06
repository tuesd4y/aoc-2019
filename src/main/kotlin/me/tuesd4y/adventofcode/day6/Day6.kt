package me.tuesd4y.adventofcode.day6

import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder
import me.tuesd4y.adventofcode.Day

class Day6 : Day(6) {

    override fun partA() {
        println(input().readLines()
            .map {
                val (f, s) = it.split(")")
                s to f
            }.toMap()
            .let { m ->
                m.map {
                    sequence {
                        var i: String? = it.key
                        while (m[i] != null) {
                            yield(i)
                            i = m[i]
                        }
                    }.toList().size
                }.sum()
            })
    }

    fun countPre(graph: Graph<String>, node: String): Int {
        var n: String? = graph.predecessors(node).firstOrNull()
        var c = 0
        while (n != null) {
            c++
            n = graph.predecessors(n).firstOrNull()
        }
        return c
    }

    fun findPreUntil(graph: Graph<String>, start: String, end: String): MutableList<String> {
        val l = mutableListOf<String>()
        var n: String? = graph.predecessors(start).firstOrNull()
        while (n != null && n != end) {
            l.add(n)
            n = graph.predecessors(n).firstOrNull()
        }
        return l
    }

    override fun partB() {
        val g = GraphBuilder.directed().build<String>()

        input().lines()
            .map { it.split(")") }
            .forEach { (src, target) -> g.putEdge(src, target) }

        val you = "YOU"
        val san = "SAN"

        val preY = findPreUntil(g, you, "COM")
        val preS = findPreUntil(g, san, "COM")

        val common = preY.filter { preS.contains(it) }
        preY.removeAll { it in common }
        preS.removeAll { it in common }

        l(preY)
        l(preS)

        println(preY.size + preS.size)
    }
}

