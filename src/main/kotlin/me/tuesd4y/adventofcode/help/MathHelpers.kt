package me.tuesd4y.adventofcode.help

object MathHelpers {
    fun gcd(a: Int, b: Int) = gcd(a.toLong(), b.toLong()).toInt()
    tailrec fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b) // Not bad for one line of code :)
    }

    fun lcm(a: Long, b: Long) = a * b / gcd(a, b)
    fun lcm(a: Long, b: Long, c: Long) = lcm(a, lcm(b,c))
}