package me.tuesd4y.adventofcode

/**
 * Helper class for simply executing the run method in a given level
 */
object LevelExecutor {
    @JvmStatic
    fun main(args: Array<String>) {
        val l = args.first().toInt()
        executeLevel(l)
    }

    fun executeLevel(l: Int) {
        val d = (l + 1) / 2

        val level = Class.forName("me.tuesd4y.adventofcode.day$d.Level$l").newInstance() as Level
        level.run()
    }
}