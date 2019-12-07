package me.tuesd4y.adventofcode.day7

import me.tuesd4y.adventofcode.Day
import me.tuesd4y.adventofcode.help.LogLevel
import me.tuesd4y.adventofcode.intcode.IntCodeProgram

class Day7 : Day(7) {

    val data: String

    init {
        data = input().readText()
    }

    override fun partA() {
        var max = 0
        var maxCombo = 0
        for (i in 0..99999) {
            val chars = String.format("%05d", i).toCharArray()
            if (chars.distinct().size != 5 || chars.any { it > '4' })
                continue
            val amps = chars.map { it - '0' to IntCodeProgram(data) }

            var out = 0
            amps.forEachIndexed { it, (param, amp) ->
                l { "Calling Amp $it with ($param, $out)" }
                amp.input.add(param)
                amp.input.add(out)
                amp.workUntil{ false}
                out = amp.output.last()
                l { "It returned $out\n" }
            }

//            l(out)
            if (out > max) {
                max = out
                maxCombo = i
            }
        }

        println(maxCombo)
        println(max)
    }

    override fun partB() {
        var max = 0
        var maxCombo = 0
        for (i in 0..99999) {
            val chars = String.format("%05d", i).toCharArray()
            if (chars.distinct().size != 5 || chars.any { it <= '4' })
                continue

            val amps = chars.map { IntCodeProgram(data) }
            amps.forEachIndexed { idx, amp -> amp.input.add(chars[idx] - '0') }

            var out = 0
            var currentAmp = 0

            while(amps.all { !it.isFinished }) {
                l("Adding input $out")
                amps[currentAmp].input.add(out)
                amps[currentAmp].workUntil { it == 4}
                out = amps[currentAmp].output.last()

                l("out [$currentAmp] -> in [${currentAmp+1}]: $out", LogLevel.Info2)

                if(currentAmp < 4){
//                    amps[currentAmp + 1].input.add(out)
                    currentAmp++
                } else {
//                    amps[0].input.add(out)
                    currentAmp = 0
                }
                l("")

                if (out > max) {
                    max = out
                    maxCombo = i
                }

            }
        }

        println(maxCombo)
        println(max)
    }
}

