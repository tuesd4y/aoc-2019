package me.tuesd4y.adventofcode.day4

import me.tuesd4y.adventofcode.Level

class Level8 : Level(8) {

    override fun run() {
        val data = input().readText()
        val first = data.split("-").first().toInt()
        val second = data.split("-")[1].toInt()

        var count = 0
        for (i in first until second) {
            val s = "$i"
            val digs = (0..5).map { s.substring(it..it).toInt() }
            if (digs.sorted() == digs && ((0..4).any {
                    digs[it] == digs[it + 1]
                })) {
                count++
                continue
            }
        }
        println(count)
    }
}