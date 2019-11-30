@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import org.junit.internal.runners.model.EachTestNotifier

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int) {
    fun neighbour(other: Cell): Boolean =
        row - 1 == other.row || row + 1 == other.row || column - 1 == other.column || column + 1 == other.column
}

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
    fun indexOf(element: E): Cell
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl(height, width, e)

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
data class MatrixImpl<E>(override val height: Int, override val width: Int, val e: E) : Matrix<E> {
    private val list = MutableList(height) { MutableList(width) { e } }

    init {
        require(height > 0 && width > 0)
    }

    override fun get(row: Int, column: Int): E = list[row][column]

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        list[row][column] = value
    }

    override fun set(cell: Cell, value: E) {
        set(cell.row, cell.column, value)
    }

    override fun equals(other: Any?): Boolean =
        other is MatrixImpl<*> && height == other.height && width == other.width && list == other.list

    override fun toString(): String = buildString {
        append('[')
        for (i in 0 until height) {
            append('[')
            for (j in 0 until width) {
                if (j != 0) append(',')
                append(get(i, j))
            }
            append(']')
            if (i != height - 1) append(',')
        }
        append(']')
    }

    override fun hashCode(): Int = list.hashCode()
    override fun indexOf(element: E): Cell {
        for (i in 0 until height) {
            for (j in 0 until width) if (this[i, j] == element) return Cell(i, j)
        }
        return Cell(-1, -1)
    }
}

