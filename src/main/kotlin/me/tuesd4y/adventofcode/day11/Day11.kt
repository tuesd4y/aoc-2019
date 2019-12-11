package me.tuesd4y.adventofcode.day11

import me.tuesd4y.adventofcode.Day
import me.tuesd4y.adventofcode.help.Board
import me.tuesd4y.adventofcode.help.IntBoard
import me.tuesd4y.adventofcode.intcode.IntCodeProgram

typealias Pos = Pair<Int, Int>

class Day11 : Day(11) {
    val data: String = input().readText()

    override fun partA() {

        val program = IntCodeProgram(data)
        val board = IntBoard(0)

        var pos = 0 to 0
        var dir = 0

        program.input.add(0)
        program.l.shutUp()

        var i = 0

        while (!program.isFinished) {
            i++
            program.workUntilOutput()
            program.workUntilOutput()
            val (color, direction) = program.output.takeLast(2).map { it.toInt() }
            board[pos] = color

            if (direction == 0) {
                dir--
            } else dir++
            if (dir < 0) dir += 4
            if (dir > 3) dir -= 4

            if (dir == 0 || dir == 2) {
                pos = pos.copy(second = pos.second + dir - 1)
            } else {
                pos = pos.copy(first = pos.first + if (dir == 1) 1 else -1)
            }
            program.input.add(board[pos])
        }

        println(board.map.count { it.value == 1 })
        println(board.map.count())
        board.print()
    }

    override fun partB() {
        val program = IntCodeProgram(data)
        val board = IntBoard(0)

        var pos = 0 to 0
        board[pos] = 1
        var dir = 0

        program.input.add(1)
        program.l.shutUp()

        var i = 0

        while (!program.isFinished) {
            i++
            program.workUntilOutput()
            program.workUntilOutput()
            val (color, direction) = program.output.takeLast(2).map { it.toInt() }
            board[pos] = color

            if (direction == 0) {
                dir--
            } else dir++
            if (dir < 0) dir += 4
            if (dir > 3) dir -= 4

            if (dir == 0 || dir == 2) {
                pos = pos.copy(second = pos.second + dir - 1)
            } else {
                pos = pos.copy(first = pos.first + if (dir == 1) 1 else -1)
            }
            program.input.add(board[pos])
        }

        board.print()
    }
}

