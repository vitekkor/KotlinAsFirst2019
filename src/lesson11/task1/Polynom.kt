@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

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
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg coefficients: Double) {
    private val coefficients = coefficients.toMutableList()
    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = coefficients.first()

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
    operator fun times(other: Polynom): Polynom = TODO()

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom = TODO()

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = TODO()

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean = other is Polynom && this.hashCode() == other.hashCode()

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int {
        var result = 1
        for (k in coefficients) {
            result += 31 * k.toInt() + k.toInt()
        }
        return result * 31
    }
}
