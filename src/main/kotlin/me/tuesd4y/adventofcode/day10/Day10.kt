package me.tuesd4y.adventofcode.day10

import me.tuesd4y.adventofcode.Day
import kotlin.math.PI
import kotlin.math.abs

class Day10: Day(10) {

    val data: String = input().readText()
    // debug data
//    val data: String = """.#..##.###...#######
//##.############..##.
//.#.######.########.#
//.###.#######.####.#.
//#####.##.#.##.###.##
//..#####..#.#########
//####################
//#.####....###.#.#.##
//##.#################
//#####.##.###..####..
//..######..##.#######
//####.##.####...##..#
//.#####..#.######.###
//##...#.##########...
//#.##########.#######
//.####.#.###.###.#.##
//....##.##.###..#####
//.#.#.###########.###
//#.#.#.#####.####.###
//###.##.####.##.#..##""".trim()

    data class Asteroid(val x: Int, val y: Int) {
        operator fun minus(other: Asteroid): Asteroid {
            return Asteroid(this.x - other.x, this.y - other.y)
        }

        operator fun div(other: Int): Asteroid {
            return Asteroid(x / other, y / other)
        }

        operator fun plus(other: Asteroid): Asteroid {
            return Asteroid(this.x + other.x, this.y + other.y)
        }
    }

    val asteroids: List<Asteroid> = data.split("\n").mapIndexed { x, v ->
        v.toCharArray().mapIndexed { y, c -> if (c == '#') Asteroid(y,x) else null }
    }.flatten().filterNotNull()

    private tailrec fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b) // Not bad for one line of code :)
    }

    lateinit var station: Asteroid

    override fun partA() {
        val a = asteroids.map { station ->
            station to asteroids.filter { it != station }.count {
                val delta = it - station

                val g = gcd(abs(delta.x), abs(delta.y))

                if (g == 0) return@count true

                val newDelta = delta / g
                if (newDelta == delta) {
                    true
                } else {
                    var los = station.copy() + newDelta
                    var isVisible = true
                    while (los != it) {
                        if (asteroids.any { a -> a == los }) {
                            isVisible = false
                        }
                        los += newDelta
                    }
                    isVisible
                }
            }
        }.sortedByDescending { it.second }

        station = a.first().first
        println(a.first())
    }

    override fun partB() {
        val aWithT = asteroids.filter { it != station }.map {
            val delta = it - station

            val g = gcd(abs(delta.x), abs(delta.y))

            if (g == 0) return@map it to atan(delta)

            val newDelta = delta / g
            if (newDelta == delta) {
                it to atan(delta)
            } else {
                var los = station.copy() + newDelta
                var isVisible = true
                while (los != it) {
                    if (asteroids.any { a -> a == los }) {
                        isVisible = false
                    }
                    los += newDelta
                }

                if (isVisible) {
                    it to atan(newDelta)
                } else it to null
            }
        }.filter { it.second != null }.map {
            it.first to it.second!! * 180.0 / Math.PI
        }.sortedBy { it.second }

        println(aWithT[199])

    }

    fun atan(a: Asteroid): Double {
        val atan = Math.atan2(-a.x.toDouble(), a.y.toDouble())
        return when {
            atan < PI -> atan + PI
            else -> atan
        }
    }
}

