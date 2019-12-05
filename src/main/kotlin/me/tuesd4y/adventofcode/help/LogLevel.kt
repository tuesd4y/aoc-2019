package me.tuesd4y.adventofcode.help

enum class LogLevel(val prio: Int) {
    Info(0),
    Info2(1),
    Warn(10),
    Error(100),
    None(Integer.MAX_VALUE)
}