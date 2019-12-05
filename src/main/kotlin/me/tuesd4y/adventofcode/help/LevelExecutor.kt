package me.tuesd4y.adventofcode.help

import me.tuesd4y.adventofcode.Day

/**
 * Helper class for simply executing the methods on a given Day class
 */
object LevelExecutor {
    @JvmStatic
    fun main(args: Array<String>) {
        val d = args.first().toInt()
        val day = Class.forName("me.tuesd4y.adventofcode.day$d.Day$d").newInstance() as Day

        if(args.size > 1) {
            if(args[2] == "a") {
                day.partA()
            } else {
                day.partB()
            }
        } else {
            execDay(day)
        }
    }

    fun execDay(day: Day) {
        day.partA()
        day.partB()
    }
}