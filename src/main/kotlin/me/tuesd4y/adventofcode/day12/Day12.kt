package me.tuesd4y.adventofcode.day12

import me.tuesd4y.adventofcode.Day
import me.tuesd4y.adventofcode.help.MathHelpers
import kotlin.math.abs

class Day12 : Day(12) {

    data class Position(var x: Int, var y: Int, var z: Int) {
        operator fun plusAssign(vel: Velocity) {
            x += vel.x
            y += vel.y
            z += vel.z
        }
    }

    data class Velocity(var x: Int, var y: Int, var z: Int)
    data class Moon(val pos: Position, val vel: Velocity) {
        constructor(pos: Position) : this(pos, Velocity(0, 0, 0))

        val pot: Int
            get() = abs(pos.x) + abs(pos.y) + abs(pos.z)
        val kin: Int
            get() = abs(vel.x) + abs(vel.y) + abs(vel.z)
        val energy: Int
            get() = pot * kin
    }


    val _moons = input().readLines()
        .map {
            val (x, y, z) = it.split("=", ",", ">").map { item ->
                try {
                    item.toInt()
                } catch (ex: NumberFormatException) {
                    null
                }
            }.filterNotNull()
            Moon(Position(x, y, z))
        }

    override fun partA() {
        val moons = _moons.toList()
        var tick = 0
        val endTick = 1000
        while (tick < endTick) {
            tick++
            l("tick $tick")
            // update velocities
            for (i in moons.indices) {
                for (j in i + 1 until moons.size) {
                    val start = moons[i]
                    val end = moons[j]
                    if (start == end) continue

                    val xDiff = start.pos.x.compareTo(end.pos.x)
                    start.vel.x -= xDiff
                    end.vel.x += xDiff
                    val yDiff = start.pos.y.compareTo(end.pos.y)
                    start.vel.y -= yDiff
                    end.vel.y += yDiff
                    val zDiff = start.pos.z.compareTo(end.pos.z)
                    start.vel.z -= zDiff
                    end.vel.z += zDiff
                }
            }
            // move moons
            for (m in moons) {
                m.pos += m.vel
                l(m)
            }
        }

        println("total energy: ${moons.sumBy { it.energy }}")
    }

    override fun partB() {
        val startMoons = _moons.toList()
        val startX = startMoons.map { it.pos.x }
        val startY = startMoons.map { it.pos.y }
        val startZ = startMoons.map { it.pos.z }
        val moons = startMoons.toList()
        
        var xFound = 0
        var yFound = 0
        var zFound = 0
        
        var tick = 0
        while (xFound == 0 || yFound == 0 || zFound == 0) {
            tick++
            l("tick $tick")
            // look for a repeating x axis
            for (i in moons.indices) {
                for (j in i + 1 until moons.size) {
                    val start = moons[i]
                    val end = moons[j]
                    if (start == end) continue

                    val xDiff = start.pos.x.compareTo(end.pos.x)
                    start.vel.x -= xDiff
                    end.vel.x += xDiff

                    val yDiff = start.pos.y.compareTo(end.pos.y)
                    start.vel.y -= yDiff
                    end.vel.y += yDiff

                    val zDiff = start.pos.z.compareTo(end.pos.z)
                    start.vel.z -= zDiff
                    end.vel.z += zDiff
                }
            }
            // move moons
            moons.forEachIndexed { idx, m ->
                m.pos += m.vel
            }

            if (moons.mapIndexed { idx, m -> idx to m }.all { (idx, m) -> m.pos.x == startX[idx] }) {
                l("found x axis equal at tick $tick")
                if(xFound == 0) {
                    xFound = tick + 1
                }
            }
            if (moons.mapIndexed { idx, m -> idx to m }.all { (idx, m) -> m.pos.y == startY[idx] }) {
                l("found y axis equal at tick $tick")
                if(yFound == 0) {
                    yFound = tick + 1
                }
            }
            if (moons.mapIndexed { idx, m -> idx to m }.all { (idx, m) -> m.pos.z == startZ[idx] }) {
                l("found z axis equal at tick $tick")
                if(zFound == 0) {
                    zFound = tick + 1
                }
            }
        }
        l("ticks: $xFound, $yFound, $zFound")

        println(MathHelpers.lcm(xFound.toLong(), yFound.toLong(), zFound.toLong()))
    }
}

