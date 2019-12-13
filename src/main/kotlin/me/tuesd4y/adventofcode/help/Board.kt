package me.tuesd4y.adventofcode.help

open class Board<T, X>(val default: T) where X: Comparable<X> {

    val map = mapOf<Pos<X>, T>().toMutableMap()
    operator fun get(pos: Pos<X>) = map[pos] ?: default
    operator fun set(pos: Pos<X>, v: T) = map.set(pos, v)

    operator fun set(pos: Pair<X, X>, v: T) = map.set(Pos(pos), v)
    operator fun get(pos: Pair<X, X>) = this[Pos(pos)]

    operator fun set(x: X, y: X, v: T) = this.set(Pos(x, y), v)
    operator fun get(x: X, y: X) = this[Pos(x, y)]

    fun getPosForValue(v: T): Pos<X>? {
        return map.entries.firstOrNull { it.value == v }?.key
    }
}