package me.tuesd4y.adventofcode.help

import java.lang.Appendable

class IntBoard<T>(default: T) : Board<T, Int>(default) {

    fun print(appendable: Appendable = System.out,
              stringMapper: (T) -> String = { if (it == default) "⬛" else "⬜" }) {
        if (map.isEmpty()) {
            println("Map is empty")
            return
        }

        val xMin = map.keys.minBy { it.first }!!.first
        val yMin = map.keys.minBy { it.second }!!.second
        val xMax = map.keys.maxBy { it.first }!!.first
        val yMax = map.keys.maxBy { it.second }!!.second


        val w = xMax - xMin + 1
        val h = yMax - yMin + 1

        for (i in 0 until w * h) {
            if (i % w == 0)
                appendable.appendln()

            val pos = Pos(xMin + i % w, yMin + i / w)
            appendable.append(stringMapper(this[pos]))
        }
    }
}