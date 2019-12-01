@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.truncate

/**
 * Класс "вещественное число с фиксированной точкой"
 *
 * Общая сложность задания - сложная.
 * Объект класса - вещественное число с заданным числом десятичных цифр после запятой (precision, точность).
 * Например, для ограничения в три знака это может быть число 1.234 или -987654.321.
 * Числа можно складывать, вычитать, умножать, делить
 * (при этом точность результата выбирается как наибольшая точность аргументов),
 * а также сравнить на равенство и больше/меньше, преобразовывать в строку и тип Double.
 *
 * Вы можете сами выбрать, как хранить число в памяти
 * (в виде строки, целого числа, двух целых чисел и т.д.).
 * Представление числа должно позволять хранить числа с общим числом десятичных цифр не менее 9.
 */
fun fromString(s: String, number: Boolean): Int {
    if (s.length > 10 && !s.matches(Regex("""\d+.?\d+"""))) throw NumberFormatException()
    val reg = Regex("""\d+""")
    return if (number) s.replace(".", "").toInt() else reg.findAll(s).elementAt(1).value.length
}

class FixedPointNumber(val number: Int, val precision: Int) : Comparable<FixedPointNumber> {
    /**
     * Точность - число десятичных цифр после запятой.
     */


    /**
     * Конструктор из строки, точность выбирается в соответствии
     * с числом цифр после десятичной точки.
     * Если строка некорректна или цифр слишком много,
     * бросить NumberFormatException.
     *
     * Внимание: этот или другой конструктор можно сделать основным
     */
    constructor(s: String) : this(fromString(s, true), fromString(s, false))

    /**
     * Конструктор из вещественного числа с заданной точностью
     */
    constructor(d: Double, p: Int) : this((truncate(d * 10.0.pow(p))).toInt(), p)

    /**
     * Конструктор из целого числа (предполагает нулевую точность)
     */
    constructor(i: Int) : this(i, 0)

    /**
     * Сложение.
     *
     * Здесь и в других бинарных операциях
     * точность результата выбирается как наибольшая точность аргументов.
     * Лишние знаки отрбрасываются, число округляется по правилам арифметики.
     */
    operator fun plus(other: FixedPointNumber): FixedPointNumber {
        val p = maxOf(this.precision, other.precision)
        return FixedPointNumber(
            this.number * 10.0.pow(abs(p - this.precision)).toInt() + other.number * 10.0.pow(abs(p - other.precision)).toInt(),
            p
        )
    }

    /**
     * Смена знака
     */
    operator fun unaryMinus(): FixedPointNumber = FixedPointNumber(number * -1, precision)

    /**
     * Вычитание
     */
    operator fun minus(other: FixedPointNumber): FixedPointNumber =
        FixedPointNumber(this.toDouble() - other.toDouble(), maxOf(this.precision, other.precision))

    /**
     * Умножение
     */
    operator fun times(other: FixedPointNumber): FixedPointNumber {
        val p = maxOf(this.precision, other.precision)
        val pow = 10.0.pow(p)
        return FixedPointNumber(round(this.toDouble() * other.toDouble() * pow) / pow, p)
    }

    /**
     * Деление
     */
    operator fun div(other: FixedPointNumber): FixedPointNumber {
        val p = maxOf(this.precision, other.precision)
        val pow = 10.0.pow(p)
        return FixedPointNumber(round(this.toDouble() / other.toDouble() * pow) / pow, p)
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        other is FixedPointNumber && number == other.number && precision == other.precision

    override fun hashCode(): Int {
        var result = 1
        result = 31 * result + number
        result = 31 * result + precision
        return result
    }

    /**
     * Сравнение на больше/меньше
     */
    override fun compareTo(other: FixedPointNumber): Int = number - other.number

    /**
     * Преобразование в строку
     */
    override fun toString(): String = this.toDouble().toString()

    /**
     * Преобразование к вещественному числу
     */
    fun toDouble(): Double = number / 10.0.pow(precision)
}