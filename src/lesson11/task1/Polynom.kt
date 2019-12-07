@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import java.lang.IllegalArgumentException
import kotlin.math.abs
import kotlin.math.pow

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- сложная.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+14
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg coefficients: Double) {
    private val coefficients = coefficients.toList()

    /**
     * Конструктор из строки an * x^n + an-1 * x^n-1...a1 * x + a0
     * Все коэффициенты должны быть, т.е Polynom(1.0, 0.0) == "x + 0.0"
     */
    constructor(s: String) : this(*s.split(Regex("""x\^?\d*""")).map {
        when (it) {
            "" -> 1.0
            "-" -> -1.0
            else -> it.toDouble()
        }
    }.toDoubleArray())

    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = coefficients[coefficients.size - i - 1]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double =
        this.coefficients.indices.fold(0.0, { sum, it -> sum + coefficients[it] * x.pow(coefficients.size - 1 - it) })

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int {
        val size = this.coefficients.size - 1
        return size - (this.coefficients.indices.firstOrNull { this.coefficients[it] != 0.0 } ?: size)
    }

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        val max = maxOf(this.coefficients.size, other.coefficients.size)
        val maxK = if (max == this.coefficients.size) this.coefficients else other.coefficients
        val minK = if (max == this.coefficients.size) other.coefficients else this.coefficients
        val different = abs(this.coefficients.size - other.coefficients.size)
        val result = mutableListOf<Double>()
        for (k in maxK.indices) {
            if (k < different) result.add(maxK[k]) else result.add(maxK[k] + minK[k - different])
        }
        return Polynom(*result.toDoubleArray())
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom = Polynom(*this.coefficients.map { -it }.toDoubleArray())

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom = this.plus(other.unaryMinus())

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        var result = Polynom(0.0)
        val biggest = if (this.degree() >= other.degree()) this else other
        val smallest = if (this.degree() < other.degree()) this else other
        for (i in biggest.coefficients.indices) {
            val k = mutableListOf<Double>()
            for (j in smallest.coefficients) {
                k.add(j * biggest.coefficients[i])
            }
            while (k.size != biggest.coefficients.size - i + smallest.coefficients.size - 1) k.add(0.0)
            result += Polynom(*k.toDoubleArray())
        }
        return result
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom {
        when {
            this < other -> return Polynom(0.0)
            other == Polynom(0.0) -> throw IllegalArgumentException()
            this == Polynom(0.0) -> return Polynom(0.0)
            this == other -> return Polynom(1.0)
            other.degree() == 0 -> return Polynom(*this.coefficients.map { it / other.coefficients.last() }.toDoubleArray())
        }
        var dividend = this
        var result = Polynom(0.0)
        while (dividend >= other) {
            val list = MutableList(dividend.degree() - other.degree() + 1) { 0.0 }
            list[0] = dividend.coeff(dividend.degree())
            dividend -= other * Polynom(*list.toDoubleArray())
            result += Polynom(*list.toDoubleArray())
        }
        return result
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom {
        when {
            this < other -> return this
            other == Polynom(0.0) -> throw IllegalArgumentException()
            this == Polynom(0.0) -> return Polynom(0.0)
            this == other -> return Polynom(0.0)
            other.degree() == 0 -> return Polynom(0.0)
        }
        var dividend = this
        var result = Polynom(0.0)
        while (dividend >= other) {
            val list = MutableList(dividend.degree() - other.degree() + 1) { 0.0 }
            list[0] = dividend.coeff(dividend.degree())
            dividend -= other * Polynom(*list.toDoubleArray())
            result += Polynom(*list.toDoubleArray())
        }
        return dividend
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = other is Polynom && this.hashCode() == other.hashCode()

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int {
        var result = 1
        for (k in coefficients.dropWhile { it == 0.0 }) {
            result += 32 * k.toInt()
        }
        return result + coefficients.dropWhile { it == 0.0 }.size
    }

    operator fun compareTo(other: Polynom): Int = this.degree() - other.degree()


}
