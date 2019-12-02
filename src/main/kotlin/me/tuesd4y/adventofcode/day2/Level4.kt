package me.tuesd4y.adventofcode.day2

import me.tuesd4y.adventofcode.Level

class Level4 : Level(4) {

    override fun run() {
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