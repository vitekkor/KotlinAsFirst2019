package lesson11.task1

import ru.spbstu.wheels.getOrNull
import kotlin.math.abs

/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */
fun main() {
    print(listOf(1, 2, 3) == listOf(4, 2, 3))
}

private fun compare2Lists(list1: List<Int>, list2: List<Int>): Boolean {
    for (i in list1.indices) {
        if (list1[i] < list2[i]) return false
    }
    return true
}

class UnsignedBigInteger(val list: MutableList<Int>) : Comparable<UnsignedBigInteger> {

    /**
     * Конструктор из строки
     */
    constructor(s: String) : this(s.split("").drop(1).dropLast(1).map { it.toInt() }.toMutableList())

    /**
     * Конструктор из целого
     */
    constructor(i: Int) : this(i.toString())

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger {
        val maxSize = maxOf(this.list.size, other.list.size)
        val max = if (maxSize == this.list.size) this.list else other.list
        val min = if (maxSize == this.list.size) other.list else this.list
        val result = MutableList(maxSize) { 0 }
        val different = abs(this.list.size - other.list.size)
        var mod = 0
        for (i in maxSize - 1 downTo 0) {
            result[i] = (max[i] + (min.getOrNull(abs(different - i)) ?: 0) + mod) % 10
            mod = (max[i] + (min.getOrNull(abs(different - i)) ?: 0) + mod) / 10
        }
        if (mod != 0) result.add(0, mod)
        return UnsignedBigInteger(result)
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        if (other > this) throw ArithmeticException()
        if (other == this) return UnsignedBigInteger(mutableListOf(0))
        val result = MutableList(list.size) { 0 }
        val different = abs(this.list.size - other.list.size)
        var i = list.size - 1
        while (i in list.size - 1 downTo 0) {
            if (list[i] >= (other.list.getOrNull(abs(different - i)) ?: 0)) {
                list[i] = list[i] - (other.list.getOrNull(abs(different - i)) ?: 0)
                if (other.list.getOrNull(abs(different - i)) != null) other.list[abs(different - i)] = 0
            } else {
                var j = i - 1
                while (list[j] == 0) {
                    j--
                }
                val required = j
                while (j != i + 1) {
                    if (j != i) list[j] -= 1
                    if (j != required) list[j] += 10
                    j++
                }
                list[i] = list[i] - (other.list.getOrNull(abs(different - i)) ?: 0)
                other.list[abs(different - i)] = 0
            }
            i--
        }
        return UnsignedBigInteger(list.dropWhile { it == 0 }.toMutableList())
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean = other is UnsignedBigInteger && list.hashCode() == other.list.hashCode()

    override fun hashCode(): Int = list.hashCode()

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int = when {
        this == other -> 0
        this.list.size > other.list.size || this.list.size == other.list.size && compare2Lists(
            this.list,
            other.list
        ) -> 1
        else -> -1
    }


    /**
     * Преобразование в строку
     */
    override fun toString(): String = list.joinToString("")

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int {
        try {
            return this.toString().toInt()
        } catch (e: NumberFormatException) {
            throw ArithmeticException()
        }
    }

}