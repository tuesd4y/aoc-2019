package me.tuesd4y.adventofcode.day3

import me.tuesd4y.adventofcode.Level
import kotlin.math.abs

class Level6 : Level(6) {
    data class Step(val direction: String, val distance: Int)

    data class Position(val x: Int, val y: Int) {

        fun addStep(step: Step): Position = when (step.direction) {
            "L" -> Position(x - step.distance, y)
            "R" -> Position(x + step.distance, y)
            "U" -> Position(x, y + step.distance)
            "D" -> Position(x, y - step.distance)
            else -> throw IllegalArgumentException("Invalid direction: ${step.direction}")
        }

        fun manhattan(p2: Position) = abs(x - p2.x) + abs(y - p2.y)

    }

    data class Segment(val p1: Position, val p2: Position, val previousDistance: Int)


    override fun run() {
        val data = input().readLines()
        val steps1 = data[0].split(",").map { Step(it.substring(0, 1), it.substring(1).toInt()) }
        val steps2 = data[1].split(",").map { Step(it.substring(0, 1), it.substring(1).toInt()) }

//        val intersections = getIntersectionPositions(stepsToSegments(steps1), stepsToSegments(steps2))
//        println(intersections.map { it.manhattanFromOrigin() }.sorted()[1])
        val intersections = getIntersectionDistances(stepsToSegments(steps1), stepsToSegments(steps2))
        println(intersections.sorted().drop(1).min())
    }

    private fun stepsToSegments(
        steps: List<Step>,
        segments: List<Segment> = emptyList(),
        position: Position = Position(0, 0)
    ): List<Segment> {
        return if (steps.isEmpty()) segments
        else {
            val newPosition = position.addStep(steps[0])
            val s = segments.lastOrNull()
            val previousDistance = if (s != null) (s.previousDistance + s.p1.manhattan(s.p2))
            else 0
            val nextSegment = Segment(position, newPosition, previousDistance)
            stepsToSegments(steps.drop(1), segments + nextSegment, newPosition)
        }
    }

    private fun intersectionPosition(s1: Segment, s2: Segment): Position? {
        return if (intersects(s1, s2) || intersects(s2, s1)) {
            val xPositions = listOf(s1.p1.x, s1.p2.x, s2.p1.x, s2.p2.x).sorted()
            val yPositions = listOf(s1.p1.y, s1.p2.y, s2.p1.y, s2.p2.y).sorted()
            Position(xPositions[1], yPositions[1])
        } else null
    }

    private fun getIntersectionDistances(segments1: List<Segment>, segments2: List<Segment>): List<Int> {
        return segments1.flatMap { s1 ->
            segments2.map { s2 ->
                intersectionDistance(s1, s2)
            }
        }.filterNotNull()
    }

    private fun intersectionDistance(s1: Segment, s2: Segment): Int? {
        return if (intersects(s1, s2) || intersects(s2, s1)) {
            val xPositions = listOf(s1.p1.x, s1.p2.x, s2.p1.x, s2.p2.x).sorted()
            val yPositions = listOf(s1.p1.y, s1.p2.y, s2.p1.y, s2.p2.y).sorted()
            val extraXDistance = abs(s1.p1.x - xPositions[1]).coerceAtLeast(abs(s2.p1.x - xPositions[1]))
            val extraYDistance = abs(s1.p1.y - yPositions[1]).coerceAtLeast(abs(s2.p1.y - yPositions[1]))
            s1.previousDistance + s2.previousDistance + extraXDistance + extraYDistance
        } else null
    }

    private fun intersects(s1: Segment, s2: Segment): Boolean {
        return (s1.p1.x.coerceAtMost(s1.p2.x) <= s2.p1.x
                && s1.p1.x.coerceAtLeast(s1.p2.x) >= s2.p1.x
                && s2.p1.y.coerceAtMost(s2.p2.y) <= s1.p1.y
                && s2.p1.y.coerceAtLeast(s2.p2.y) >= s1.p1.y)
    }
}