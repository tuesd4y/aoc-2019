package me.tuesd4y.adventofcode.intcode

import me.tuesd4y.adventofcode.help.Log
import me.tuesd4y.adventofcode.help.LogLevel

class IntCodeProgram(code: String) {
    val l = Log(LogLevel.Info2)
    var i = 0
    // todo maybe this should be a queue?
    val input = mutableListOf<Int>()
    val output = mutableListOf<Long>()
    var inputCounter = 0
    var isFinished = false

    var relBase = 0L

    val d = code.split(",").map { it.replace("\n", "") }
        .map { it.toLong() }.toMutableList()

    val ops = Memory(d.mapIndexed{idx, v -> idx.toLong() to v }.toMap())
    
    class Memory(map: Map<Long, Long>) {
        val backingMap = map.toMutableMap()

        val size = map.size
        operator fun get(i: Long): Long {
            return backingMap[i] ?: 0
        }

        operator fun set(i: Long, v: Long) {
            backingMap[i] = v
        }
    }

    fun workUntilOutput() = workUntil { it == 4 }

    fun workUntil(endCondition: (Int) -> Boolean = { false }) {
        loop@ while (i < ops.size) {
            l.log("currently at $i", LogLevel.Error)

            val longOpCode = ops[i.toLong()]
            val op = longOpCode.rem(100).toInt()
            val a = longOpCode.rem(1000) / 100
            val b = longOpCode.rem(10000) / 1000
            val c = longOpCode / 10000


            l.log("op=$op", LogLevel.Error)

            when (op) {
                1 -> {
                    l("add ($longOpCode)")
                    val x = if (a == 0L) ops[ops[i + 1L]] else if (a == 1L) ops[i + 1L] else ops[relBase + ops[i + 1L]]
                    val y = if (b == 0L) ops[ops[i + 2L]] else if (b == 1L) ops[i + 2L] else ops[relBase + ops[i + 2L]]
                    if (c != 2L) ops[ops[i + 3L]] = x + y
                    else ops[relBase + ops[i + 3L]] = x + y
                    i += 4
                }
                2 -> {
                    l("mult ($longOpCode)")
                    val x = if (a == 0L) ops[ops[i + 1L]] else if(a == 1L) ops[i + 1L] else ops[relBase + ops[i+1L]]
                    val y = if (b == 0L) ops[ops[i + 2L]] else if(b == 1L) ops[i + 2L] else ops[relBase + ops[i+2L]]
                    if (c != 2L) ops[ops[i + 3L]] = x * y
                    else ops[relBase + ops[i + 3L]] = x * y
                    i += 4
                }
                3 -> {
                    l("in ($longOpCode)")
                    val currInput = input[inputCounter]
                    inputCounter++
                    // take input and save to x
                    l("get input -> $currInput from $input")
                    if (a != 2L) ops[ops[i + 1L]] = currInput.toLong()
                    else ops[relBase + ops[i + 1L]] = currInput.toLong()
                    i += 2
                }
                4 -> {
                    l("out ($longOpCode)")
                    // print output
                    val out = if (a == 0L) ops[ops[i + 1L]] else if(a == 1L) ops[i + 1L] else ops[relBase + ops[i+1L]]
                    l("output: $out")
                    output.add(out)
                    i += 2
                }
                5 -> {
                    l("jump if not zero ($longOpCode)")
                    val x = if (a == 0L) ops[ops[i + 1L]] else if(a == 1L) ops[i + 1L] else ops[relBase + ops[i+1L]]
                    val y = if (b == 0L) ops[ops[i + 2L]] else if(b == 1L) ops[i + 2L] else ops[relBase + ops[i+2L]]
                    if (x != 0L) {
                        i = y.toInt()
                    } else {
                        i += 3
                    }
                }
                6 -> {
                    l("jump if zero ($longOpCode)")
                    val x = if (a == 0L) ops[ops[i + 1L]] else if(a == 1L) ops[i + 1L] else ops[relBase + ops[i+1L]]
                    val y = if (b == 0L) ops[ops[i + 2L]] else if(b == 1L) ops[i + 2L] else ops[relBase + ops[i+2L]]
                    if (x == 0L) {
                        i = y.toInt()
                    } else {
                        i += 3
                    }
                }
                7 -> {
                    l("if smaller ($longOpCode)")
                    val x = if (a == 0L) ops[ops[i + 1L]] else if(a == 1L) ops[i + 1L] else ops[relBase + ops[i+1L]]
                    val y = if (b == 0L) ops[ops[i + 2L]] else if(b == 1L) ops[i + 2L] else ops[relBase + ops[i+2L]]
                    val z = if( c == 2L) relBase + ops[i+3L] else ops[i + 3L]
                    val v = if (x < y) 1L else 0L
                    ops[z] = v
                    i += 4
                }
                8 -> {
                    l("if equal ($longOpCode)")
                    val x = if (a == 0L) ops[ops[i + 1L]] else if(a == 1L) ops[i + 1L] else ops[relBase + ops[i+1L]]
                    val y = if (b == 0L) ops[ops[i + 2L]] else if(b == 1L) ops[i + 2L] else ops[relBase + ops[i+2L]]
                    val z = if( c == 2L) relBase + ops[i+3L] else ops[i + 3L]
                    val v = if (x == y) 1L else 0L
                    ops[z] = v
                    i += 4
                }
                9 -> {
                    l("adjust relbase ($longOpCode)")
                    val r = if(a == 0L) ops[ops[i + 1L]] else if(a == 1L) ops[i + 1L] else ops[relBase + ops[i+1L]]
                    relBase += r
                    i += 2
                }
                99 -> {
                    isFinished = true
                    break@loop
                }
            }

            if (endCondition(op)) {
                break
            }
        }
    }
}