@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.absoluteValue

/**
 * Класс "Величина с размерностью".
 *
 * Предназначен для представления величин вроде "6 метров" или "3 килограмма"
 * Общая сложность задания - средняя.
 * Величины с размерностью можно складывать, вычитать, делить, менять им знак.
 * Их также можно умножать и делить на число.
 *
 * В конструктор передаётся вещественное значение и строковая размерность.
 * Строковая размерность может:
 * - либо строго соответствовать одной из abbreviation класса Dimension (m, g)
 * - либо соответствовать одной из приставок, к которой приписана сама размерность (Km, Kg, mm, mg)
 * - во всех остальных случаях следует бросить IllegalArgumentException
 */
class DimensionalValue(value: Double, dimension: String) : Comparable<DimensionalValue> {
    /**
     * Величина с БАЗОВОЙ размерностью (например для 1.0Kg следует вернуть результат в граммах -- 1000.0)
     */
    val value = if (dimension.length > 1) when (dimension[0].toString()) {
        DimensionPrefix.KILO.abbreviation -> value * 1000
        DimensionPrefix.MILLI.abbreviation -> value * 0.001
        else -> throw IllegalArgumentException()
    } else value

    /**
     * БАЗОВАЯ размерность (опять-таки для 1.0Kg следует вернуть GRAM)
     */
    val dimension = when (dimension.last().toString()) {
        Dimension.NUTON.abbreviation -> Dimension.NUTON
        Dimension.GRAM.abbreviation -> Dimension.GRAM
        Dimension.SECOND.abbreviation -> Dimension.SECOND
        Dimension.METER.abbreviation -> Dimension.METER
        else -> throw IllegalArgumentException()
    }

    /**
     * Конструктор из строки. Формат строки: значение пробел размерность (1 Kg, 3 mm, 100 g и так далее).
     */
    constructor(s: String) : this(s.split(" ")[0].toDouble(), s.split(" ")[1])

    /**
     * Сложение с другой величиной. Если базовая размерность разная, бросить IllegalArgumentException
     * (нельзя складывать метры и килограммы)
     */
    operator fun plus(other: DimensionalValue): DimensionalValue {
        require(dimension == other.dimension)
        return DimensionalValue(value + other.value, dimension.abbreviation)
    }

    /**
     * Смена знака величины
     */
    operator fun unaryMinus(): DimensionalValue = DimensionalValue(-1 * value, dimension.abbreviation)

    /**
     * Вычитание другой величины. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun minus(other: DimensionalValue): DimensionalValue {
        require(dimension == other.dimension)
        return DimensionalValue(value - other.value, dimension.abbreviation)
    }

    /**
     * Умножение на число
     */
    operator fun times(other: Double): DimensionalValue = DimensionalValue(value * other, dimension.abbreviation)

    /**
     * Деление на число
     */
    operator fun div(other: Double): DimensionalValue = DimensionalValue(value / other, dimension.abbreviation)

    /**
     * Деление на другую величину. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun div(other: DimensionalValue): Double {
        require(dimension == other.dimension)
        return value / other.value
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        other is DimensionalValue && value == other.value && dimension == other.dimension

    override fun hashCode(): Int {
        var result = 1
        result = 31 * result + value.toBits().toInt()
        result = 31 * result + dimension.abbreviation.hashCode()
        return result
    }

    /** Сравнение на больше/меньше. Если базовая размерность разная, бросить IllegalArgumentException
     */
    override fun compareTo(other: DimensionalValue): Int {
        require(dimension == other.dimension)
        return when {
            value > other.value -> 1
            value < other.value -> -1
            else -> 0
        }
    }
}

/**
 * Размерность. В этот класс можно добавлять новые варианты (секунды, амперы, прочие), но нельзя убирать
 */
enum class Dimension(val abbreviation: String) {
    NUTON("N"),
    METER("m"),
    SECOND("s"),
    GRAM("g");
}

/**
 * Приставка размерности. Опять-таки можно добавить новые варианты (деци-, санти-, мега-, ...), но нельзя убирать
 */
enum class DimensionPrefix(val abbreviation: String, val multiplier: Double) {
    GIGA("G", 1000000000.0),
    MEGA("M", 1000000.0),
    KILO("K", 1000.0),
    SANTI("s", 0.01),
    MILLI("m", 0.001),
    NANO("n", 0.000000001)
}