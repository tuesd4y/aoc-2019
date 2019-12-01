package me.tuesd4y.adventofcode.day1

import me.tuesd4y.adventofcode.Level

class Level1 : Level(1) {

    override fun run() {
        val sum = input().lines()
            .map(String::toInt)
            .mapToInt(FuelCalculator::fuelForWeight)
            .sum()
        println(sum)
    }
}