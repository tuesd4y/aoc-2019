package me.tuesd4y.adventofcode.day1

import me.tuesd4y.adventofcode.Level

class Level2 : Level(2) {

    override fun run() {
        val sum = input().lines()
            .map(String::toInt)
            .mapToInt(FuelCalculator::recursiveFuelForWeight)
            .sum()
        println(sum)

        println(FuelCalculator.recursiveFuelForWeight(100756))
    }
}