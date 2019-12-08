package me.tuesd4y.adventofcode.intcode

import me.tuesd4y.adventofcode.help.Log
import me.tuesd4y.adventofcode.help.LogLevel

class IntCodeProgram(code: String) {
    val l = Log(LogLevel.Info2)
    var i = 0
    // todo maybe this should be a queue?
    val input = mutableListOf<Int>()
    val output = mutableListOf<Int>()
    var inputCounter = 0
    var isFinished = false

    val ops = code.split(",").map { it.replace("\n", "") }
        .map { it.toInt() }.toMutableList()

    fun workUntil(endCondition: (Int) -> Boolean = { false }) {
        loop@ while (i < ops.size) {
            l.log("currently at $i", LogLevel.Error)

            val longOpCode = ops[i]
            val op = longOpCode.rem(100)
            val a = longOpCode.rem(1000) / 100
            val b = longOpCode.rem(10000) / 1000
            val c = longOpCode / 10000


            when (op) {
                1 -> {
                    l("add ($longOpCode)")
                    val x = if (a == 0) ops[ops[i + 1]] else ops[i + 1]
                    val y = if (b == 0) ops[ops[i + 2]] else ops[i + 2]
                    ops[ops[i + 3]] = x + y
                    i += 4
                }
                2 -> {
                    l("mult ($longOpCode)")
                    val x = if (a == 0) ops[ops[i + 1]] else ops[i + 1]
                    val y = if (b == 0) ops[ops[i + 2]] else ops[i + 2]
                    ops[ops[i + 3]] = x * y
                    i += 4
                }
                3 -> {
                    l("in ($longOpCode)")
                    val currInput = input[inputCounter]
                    inputCounter++
                    // take input and save to x
                    l("get input -> $currInput from $input")
                    ops[ops[i + 1]] = currInput
                    i += 2
                }
                4 -> {
                    l("out ($longOpCode)")
                    // print output
                    val out = if (a == 0) ops[ops[i + 1]] else ops[i + 1]
                    l("output: $out")
                    output.add(out)
                    i += 2
                }
                5 -> {
                    l("jump if zero ($longOpCode)")
                    val x = if (a == 0) ops[ops[i + 1]] else ops[i + 1]
                    val y = if (b == 0) ops[ops[i + 2]] else ops[i + 2]
                    if (x != 0) {
                        i = y
                    } else {
                        i += 3
                    }
                }
                6 -> {
                    l("jump if not zero ($longOpCode)")
                    val x = if (a == 0) ops[ops[i + 1]] else ops[i + 1]
                    val y = if (b == 0) ops[ops[i + 2]] else ops[i + 2]
                    if (x == 0) {
                        i = y
                    } else {
                        i += 3
                    }
                }
                7 -> {
                    l("if smaller ($longOpCode)")
                    val x = if (a == 0) ops[ops[i + 1]] else ops[i + 1]
                    val y = if (b == 0) ops[ops[i + 2]] else ops[i + 2]
                    val z = ops[i + 3]
                    val v = if (x < y) 1 else 0
                    ops[z] = v
                    i += 4
                }
                8 -> {
                    l("if equal ($longOpCode)")
                    val x = if (a == 0) ops[ops[i + 1]] else ops[i + 1]
                    val y = if (b == 0) ops[ops[i + 2]] else ops[i + 2]
                    val z = ops[i + 3]
                    val v = if (x == y) 1 else 0
                    ops[z] = v
                    i += 4
                }
                99 -> {
                    isFinished = true
                    break@loop
                }
            }

            if(endCondition(op)) {
                break
            }
        }
    }
}