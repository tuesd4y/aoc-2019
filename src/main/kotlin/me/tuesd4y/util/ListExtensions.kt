package me.tuesd4y.util

fun <T> List<T>.split(size: Int): List<List<T>> = this.mapIndexed { idx, it ->
    idx / size to it
}.groupBy { it.first }
    .map { (_, valuesWithId) ->
        valuesWithId.map(Pair<Int, T>::second)
    }