package me.tuesd4y.adventofcode.day14

import me.tuesd4y.adventofcode.Day

class Day14 : Day(14) {

    data class Formula(val input: Map<String, Long>, val output: Pair<String, Long>) {
        companion object {
            fun fromText(text: String): Formula {
                val (i, o) = text.split(" => ")
                val inputs = i.split(", ")
                return Formula(inputs.map {
                    val (v, key) = it.split(" ")
                    key to v.toLong()
                }.toMap(), o.let {
                    val (v, key) = it.split(" ")
                    key to v.toLong()
                })
            }
        }
    }


    val formulas = input().readLines().map(Formula.Companion::fromText).map { it.output.first to it }.toMap()
    fun addToMap(map: MutableMap<String, Long>, v: Pair<String, Long>) {
        map[v.first] = (map[v.first] ?: 0) + v.second
    }

    override fun partA() {
        println(oreNeeded(1))
    }

    fun oreNeeded(i: Long): Long {
        val needed = mutableMapOf("FUEL" to i)

        while (!needed.all { it.key == "ORE" || it.value <= 0 }) {
            val toReplace = needed.entries.filter { it.key != "ORE" && it.value > 0 }.first()

            val formula = formulas[toReplace.key]!!
            val requiredCount = toReplace.value
            val formulaResCount = formula.output.second

            val formulaTimes = requiredCount / formulaResCount + if (requiredCount % formulaResCount == 0L) 0 else 1
            formula.input.entries.forEach { (k, v) -> addToMap(needed, k to v * formulaTimes) }
            formula.output.let { (k, v) -> addToMap(needed, k to -v * formulaTimes) }
        }

        return needed["ORE"]!!
    }

    override fun partB() {
        var min = 1_000_000_000_000L / 378_929L
        var max = 1_000_000_000L

        var i = 0
        while (min < max - 1) {
            // simple bisection approximation for ore value.

            println(i++)
            val avg = (min + max) / 2
            val avgOre = oreNeeded(avg)

            if (avgOre > 1_000_000_000_000L) {
                max = avg
            } else {
                min = avg
            }
        }

        println("$min")

        // max is just a little too much, so min is the correct output!
        // we should also still be a little under a trillion ore used.
        println(oreNeeded(min))
    }
}

