package me.tuesd4y.adventofcode.day9

import me.tuesd4y.adventofcode.Day
import me.tuesd4y.adventofcode.help.LogLevel
import me.tuesd4y.adventofcode.intcode.IntCodeProgram

class Day9 : Day(9) {

    val data: String = input().readText()

    override fun partA() {

        val a = IntCodeProgram(data)
        a.input.add(1)
        a.workUntil {false}
        println(a.output.joinToString("\n"))
    }

    override fun partB() {
        val a = IntCodeProgram(data)
        a.input.add(2)
        a.workUntil {false}
        println(a.output.joinToString("\n"))
    }
}

