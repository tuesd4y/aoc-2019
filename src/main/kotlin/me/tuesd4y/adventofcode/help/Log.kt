package me.tuesd4y.adventofcode.help

import java.io.PrintStream
import kotlin.math.min

class Log(
    var minPrio: Int = 0,
    private val stream: PrintStream = System.out,
    private val prefix: (() -> String)? = null
) {

    constructor(
        minPrio: LogLevel,
        stream: PrintStream = System.out,
        prefix: (() -> String)? = null
    ) : this(minPrio.prio, stream, prefix) {

    }

    fun shutUp() { minPrio = LogLevel.None.prio }
    fun stop() = shutUp()
    fun hoids() = shutUp()
    fun maul() = shutUp()
    fun hi() {minPrio = LogLevel.Info.prio}

    fun log(message: String, prio: LogLevel) {
        log(message, prio.prio)
    }

    fun log(messageSrc: () -> String, prio: LogLevel) {
        log(messageSrc, prio.prio)
    }

    fun log(message: String) {
        log(message, 0)
    }

    fun log(messageSrc: () -> String) {
        log(messageSrc, 0)
    }

    fun log(message: String, priority: Int) {
        if (priority >= minPrio)
            l(message)
    }

    fun log(messageSrc: () -> String, priority: Int) {
        if (priority >= minPrio)
            l(messageSrc())
    }

    private fun l(msg: String) {
        if (prefix != null) {
            stream.println("${prefix.invoke()} $msg")
        } else {
            stream.println(msg)
        }
    }

    operator fun invoke(message: String) {
        log(message)
    }

    operator fun invoke(message: String, prio: Int) {
        log(message, prio)
    }

    operator fun invoke(message: String, prio: LogLevel) {
        log(message, prio)
    }

    operator fun plusAssign(message: String) {
        log(message)
    }

    operator fun invoke(messageSource: () -> String) {
        log(messageSource)
    }

    operator fun invoke(any: Any?) {
        log(any.toString())
    }
}