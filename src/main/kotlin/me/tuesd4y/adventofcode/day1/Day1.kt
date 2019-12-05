package me.tuesd4y.adventofcode.day1

import me.tuesd4y.adventofcode.Day

class Day1 : Day(1) {

    override fun partA()

    {
        val sum = input().lines()
            .map(String::toInt)
            .mapToInt(FuelCalculator::fuelForWeight)
            .sum()
        println(sum)
    }

    override fun partB() {
        val sum = input().lines()
            .map(String::toInt)
            .mapToInt(FuelCalculator::recursiveFuelForWeight)
            .sum()
        println(sum)

        println(FuelCalculator.recursiveFuelForWeight(100756))
    }
}