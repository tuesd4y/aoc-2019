package me.tuesd4y.adventofcode.day2

import me.tuesd4y.adventofcode.Day

class Day2 : Day(2) {

    override fun partA() {
        val ops = input().readText().split(",").map { it.replace("\n", "") }
            .map { it.toInt() }.toMutableList()

        ops[1] = 12
        ops[2] = 2

        loop@ for (i in (0..ops.size / 4).map { it * 4 }) {
            println(" $i: ${ops[i]}")

            when(ops[i]) {
                1 -> ops[ops[i+3]] = ops[ops[i+1]] + ops[ops[i+2]]
                2 -> ops[ops[i+3]] = ops[ops[i+1]] * ops[ops[i+2]]
                99 -> break@loop
             }
        }

        println(ops[0])
    }

    override fun partB() {
        val ogOps = input().readText().split(",").map { it.replace("\n", "") }
            .map { it.toInt() }.toMutableList()

        for (x in 0..99) {
            for (y in 0..99) {
                val ops = ogOps.toMutableList()
                ops[1] = x
                ops[2] = y

                loop@ for (i in (0..ops.size / 4).map { it * 4 }) {
                    when (ops[i]) {
                        1 -> ops[ops[i + 3]] = ops[ops[i + 1]] + ops[ops[i + 2]]
                        2 -> ops[ops[i + 3]] = ops[ops[i + 1]] * ops[ops[i + 2]]
                        99 -> break@loop
                    }
                }

                if(ops[0] == 19690720) {
                    println(x*100+y)
                }
            }
        }
    }
}