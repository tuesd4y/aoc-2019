package me.tuesd4y.adventofcode.day5

import me.tuesd4y.adventofcode.Day

class Day5 : Day(5) {

    init {
        l.shutUp()
        l.hi()
    }

    private fun r(input: Int) {
        val data = input().readText()
        val ops = data.split(",").map { it.replace("\n", "") }
            .map { it.toInt() }.toMutableList()

        var i = 0

        loop@ while(i < ops.size) {
            l.log("currently at $i", -1)

            val longOpCode = ops[i]
            val op = longOpCode.rem(100)
            val a = longOpCode.rem(1000)/100
            val b = longOpCode.rem(10000)/1000
            val c = longOpCode/10000


            when(op) {
                1 -> {
                    l("add ($longOpCode)")
                    val x = if(a == 0) ops[ops[i + 1]] else ops[i+1]
                    val y = if(b == 0) ops[ops[i + 2]] else ops[i+2]
                    ops[ops[i+3]] = x + y
                    i += 4
                }
                2 -> {
                    l("mult ($longOpCode)")
                    val x = if(a == 0) ops[ops[i + 1]] else ops[i+1]
                    val y = if(b == 0) ops[ops[i + 2]] else ops[i+2]
                    ops[ops[i+3]] = x * y
                    i += 4
                }
                3 -> {
                    l("in ($longOpCode)")
                    // take input and save to x
                    l("get input -> $input")
                    ops[ops[i+1]] = input
                    i+=2
                }
                4 -> {
                    l("out ($longOpCode)")
                    // print input
                    println(if(a == 0) ops[ops[i + 1]] else ops[i+1])
                    i+=2
                }
                5 -> {
                    l("jump if zero ($longOpCode)")
                    val x = if(a == 0) ops[ops[i + 1]] else ops[i+1]
                    val y = if(b == 0) ops[ops[i + 2]] else ops[i+2]
                    if(x != 0) {
                        i = y
                    } else {
                        i += 3
                    }
                }
                6 -> {
                    l("jump if not zero ($longOpCode)")
                    val x = if(a == 0) ops[ops[i + 1]] else ops[i+1]
                    val y = if(b == 0) ops[ops[i + 2]] else ops[i+2]
                    if(x == 0) {
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
                99 -> break@loop
            }
        }

    }

    override fun partA() {
        r(5)
    }

    override fun partB() {
        r(1)
    }
}

