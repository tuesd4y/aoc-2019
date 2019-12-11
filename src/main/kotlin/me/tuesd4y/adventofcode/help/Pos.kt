package me.tuesd4y.adventofcode.help

data class Pos<T>(val x: T, val y: T) where T : Comparable<T> {
    val first = x
    val second = y

    constructor(pair: Pair<T,T>) : this(pair.first, pair.second)
}