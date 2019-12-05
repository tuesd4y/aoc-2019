package me.tuesd4y.adventofcode.day5

import me.tuesd4y.adventofcode.Level

class Level10 : Level(10) {
    val debug = false

    override fun run() {
        val data = input().readText()
        val input = 5
//        val data = "3,9,8,9,10,9,4,9,99,-1,8"
        val ops = data.split(",").map { it.replace("\n", "") }
            .map { it.toInt() }.toMutableList()

        var i = 0

        loop@ while(i < ops.size) {
            debug("currently at $i")

            val longOpCode = ops[i]
            val op = longOpCode.rem(100)
            val a = longOpCode.rem(1000)/100
            val b = longOpCode.rem(10000)/1000
            val c = longOpCode/10000

//            println("opcode $op")

            when(op) {
                1 -> {
                    val x = if(a == 0) ops[ops[i + 1]] else ops[i+1]
                    val y = if(b == 0) ops[ops[i + 2]] else ops[i+2]
                    ops[ops[i+3]] = x + y
                    i += 4
                    debug("add $x to $y into ${ops[i+3]}")
                }
                2 -> {
                    val x = if(a == 0) ops[ops[i + 1]] else ops[i+1]
                    val y = if(b == 0) ops[ops[i + 2]] else ops[i+2]
                    ops[ops[i+3]] = x * y
                    i += 4
                }
                3 -> {
                    // take input and save to x
                    println("get input -> $input")
                    ops[ops[i+1]] = input
                    i+=2
                }
                4 -> {
                    // take input and save to x
                    println(if(a == 0) ops[ops[i + 1]] else ops[i+1])
                    i+=2
                }
                5 -> {
                    val x = if(a == 0) ops[ops[i + 1]] else ops[i+1]
                    val y = if(b == 0) ops[ops[i + 2]] else ops[i+2]
                    if(x != 0) {
                        i = y
                    } else {
                        i += 3
                    }
                }
                6 -> {
                    val x = if(a == 0) ops[ops[i + 1]] else ops[i+1]
                    val y = if(b == 0) ops[ops[i + 2]] else ops[i+2]
                    if(x == 0) {
                        i = y
                    } else {
                        i += 3
                    }
                }
                7 -> {
                    val x = if (a == 0) ops[ops[i + 1]] else ops[i + 1]
                    val y = if (b == 0) ops[ops[i + 2]] else ops[i + 2]
                    val z = ops[i + 3]
                    val v = if (x < y) 1 else 0
                    ops[z] = v
                    i += 4
                }
                8 -> {
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
    fun debug(string: String) {
        if(debug) {
            println(string)
        }
    }
}

