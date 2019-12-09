@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Класс "табличная функция".
 *
 * Общая сложность задания -- средняя.
 * Объект класса хранит таблицу значений функции (y) от одного аргумента (x).
 * В таблицу можно добавлять и удалять пары (x, y),
 * найти в ней ближайшую пару (x, y) по заданному x,
 * найти (интерполяцией или экстраполяцией) значение y по заданному x.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class TableFunction {
    private val table = mutableListOf<Pair<Double, Double>>()
    /**
     * Количество пар в таблице
     */
    val size: Int get() = table.size

    /**
     * Добавить новую пару.
     * Вернуть true, если пары с заданным x ещё нет,
     * или false, если она уже есть (в этом случае перезаписать значение y)
     */
    fun add(x: Double, y: Double): Boolean {
        val pair = table.find { it.first == x }
        return if (pair != null) {
            table.remove(pair)
            table.add(x to y)
            false
        } else {
            table.add(x to y)
            true
        }
    }

    /**
     * Удалить пару с заданным значением x.
     * Вернуть true, если пара была удалена.
     */
    fun remove(x: Double): Boolean {
        val pair = table.find { it.first == x }
        return if (pair != null) {
            table.remove(pair)
            true
        } else false
    }

    /**
     * Вернуть коллекцию из всех пар в таблице
     */
    fun getPairs(): Collection<Pair<Double, Double>> = table

    /**
     * Вернуть пару, ближайшую к заданному x.
     * Если существует две ближайшие пары, вернуть пару с меньшим значением x.
     * Если таблица пуста, бросить IllegalStateException.
     */
    fun findPair(x: Double): Pair<Double, Double>? {
        check(table.isNotEmpty())
        return table.minBy { abs(it.first - x) + abs(it.second - x) }
    }

    /**
     * Вернуть значение y по заданному x.
     * Если в таблице есть пара с заданным x, взять значение y из неё.
     * Если в таблице есть всего одна пара, взять значение y из неё.
     * Если таблица пуста, бросить IllegalStateException.
     * Если существуют две пары, такие, что x1 < x < x2, использовать интерполяцию.
     * Если их нет, но существуют две пары, такие, что x1 < x2 < x или x < x2 < x1, использовать экстраполяцию.
     */
    fun getValue(x: Double): Double {
        TODO()
    }
    /**
     * check(this.size != 0)
    val pair = table.find { it.first == x }
    when {
    pair != null -> return pair.second
    this.size == 1 -> return table[0].second
    else -> {
    val sorted = table.sortedBy { it.first }
    val x2 = sorted.first { it.first > x }.first
    val x1 = sorted.asReversed().first { it.first < x2 }.first
    if (x1 < x)
    }
    }
     */

    /**
     * Таблицы равны, если в них одинаковое количество пар,
     * и любая пара из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean = other is TableFunction && this.hashCode() == other.hashCode()

    override fun hashCode(): Int {
        var result = this.size
        for ((first, second) in this.getPairs()) {
            result += first.roundToInt() + second.roundToInt()
        }
        return result
    }
}