package me.tuesd4y.adventofcode.day4

import me.tuesd4y.adventofcode.Level

class Level7 : Level(7) {

    override fun run() {
        val data = input().readText()
        val first = data.split("-").first().toInt()
        val second = data.split("-")[1].toInt()

        var count = 0
        for (i in first until second) {
            val s = "$i"
            val digs = (0..5).map { s.substring(it..it).toInt() }
                if (digs.sorted() == digs && ((1..3).any {
                        digs[it] == digs[it + 1]  && digs[it] != digs[it + 2] && digs[it-1] != digs[it]
                    } || (digs[3] != digs[4] && digs[4] == digs[5])|| (digs[0] != digs[2] && digs[0] == digs[1]))) {
                    count++
                    continue
            }
        }
        println(count)
    }

}