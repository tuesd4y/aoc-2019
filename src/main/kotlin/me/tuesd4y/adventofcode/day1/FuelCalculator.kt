package me.tuesd4y.adventofcode.day1

object FuelCalculator {
    fun fuelForWeight(weight: Int) = weight / 3 - 2

    fun recursiveFuelForWeight(weight: Int): Int {
        var sum = 0
        var last = fuelForWeight(weight)
        while (last > 0) {
            sum += last
            last = fuelForWeight(last)
        }
        return sum
    }
}