package me.tuesd4y.adventofcode.day2

import me.tuesd4y.adventofcode.Level

class Level3 : Level(3) {

    override fun run() {
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
}