package me.tuesd4y.adventofcode.day13

import me.tuesd4y.adventofcode.Day
import me.tuesd4y.adventofcode.help.IntBoard
import me.tuesd4y.adventofcode.intcode.IntCodeProgram
import kotlin.math.sign

class Day13 : Day(13) {


    override fun partA() {
        val program = IntCodeProgram(input().readText())
        program.l.shutUp()
        val intBoard = IntBoard(0)

        while (!program.isFinished) {
            program.workUntil { it == 4 }
            program.workUntil { it == 4 }
            program.workUntil { it == 4 }
            val (x, y, v) = program.output.takeLast(3)
            intBoard[x.toInt(), y.toInt()] = v.toInt()
        }

        intBoard.print {
            when (it) {
                0 -> "⬜"
                else -> "⬛"
            }
        }

        println(intBoard.map.values.count { it == 2 })
    }

    override fun partB() {
        val program = IntCodeProgram(input().readText())
        program.l.shutUp()
        program.ops[0] = 2

        val intBoard = IntBoard(0)

        var i = 0
        var ballMoved = false

        while (!program.isFinished) {
            i++

            program.workUntil { it == 4 }
            program.workUntil { it == 4 }
            program.workUntil { it == 4 }

            val (x, y, v) = program.output.takeLast(3)
            intBoard[x.toInt(), y.toInt()] = v.toInt()
            if (v.toInt() == 4) {
                ballMoved = true
            }
            if (ballMoved) {
                val ball = intBoard.getPosForValue(4)
                val paddle = intBoard.getPosForValue(3)

                val joystick = if (paddle == null) {
                    1
                } else sign(((ball?.first ?: 0) - paddle.first).toDouble()).toInt()
                println("Ball x: ${ball?.x}, y: ${ball?.y}")
                println("Paddle x: ${paddle?.x}, y: ${paddle?.y}")
                println("move $joystick")

                program.input.add(joystick)
                ballMoved = false
            }
        }

        intBoard.print {
            when (it) {
                0 -> "⬜"
                else -> "⬛"
            }
        }
        println(intBoard[-1,0])

    }
}

