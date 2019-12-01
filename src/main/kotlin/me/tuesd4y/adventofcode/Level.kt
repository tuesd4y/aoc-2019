package me.tuesd4y.adventofcode

import java.io.PrintWriter
import java.nio.file.Paths

abstract class Level(val level: Int) {
    fun input() = javaClass.getResourceAsStream("/input${level}.txt")
        .bufferedReader()

    private val out: PrintWriter

    init {
        // setup printWriter
        val folder = Paths.get("out").toFile()
        if (!folder.exists())
            folder.mkdir()

        val file = Paths.get("out/output$level.txt").toFile()
        if (file.exists())
            file.delete()

        file.createNewFile()

        out =  file.printWriter()
    }
    fun output() = out

    abstract fun run()
}