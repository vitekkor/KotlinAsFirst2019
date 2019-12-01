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
    print("999999999999999999999".toInt())
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
        for (k in maxSize - 1 downTo 0) {
            result[k] = (max[k] + (min.getOrNull(different - k) ?: 0) + mod) % 10
            mod = (max[k] + (min.getOrNull(different - k) ?: 0) + mod) / 10
        }
        if (mod != 0) result.add(0, mod)
        return UnsignedBigInteger(result)
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

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
    override fun compareTo(other: UnsignedBigInteger): Int = TODO()

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