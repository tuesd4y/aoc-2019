package me.tuesd4y.adventofcode.day8

import me.tuesd4y.adventofcode.Day
import me.tuesd4y.util.split

class Day8 : Day(8) {

    val data: String = input().readText()

    val w = 25
    val h = 6

    val layers = input().readText()
        .toCharArray()
        .dropLast(1) // drop terminating newline
        .map { it - '0' }
        .split(w * h)

    override fun partA() {
        val ml = layers.minBy { it.count { dig -> dig == 0 } }!!
        println(ml.count { it == 1 } * ml.count { it == 2 })
    }

    override fun partB() {
        layers.drop(1).fold(
            layers.first().toMutableList(),
            { acc, l ->
                l.forEachIndexed { idx, v ->
                    if (acc[idx] == 2) {
                        acc[idx] = v
                    }
                }
                acc
            }).split(w)
            .forEach { println(it.joinToString("") { i -> if (i == 0) "⬛" else "⬜" }) }
    }
}

