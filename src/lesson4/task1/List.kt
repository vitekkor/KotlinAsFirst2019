@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.minDivisor
import kotlin.math.*

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var result = 0.0
    for (element in v) {
        result += sqr(element)
    }
    return sqrt(result)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isNotEmpty()) list.sum() / list.size else 0.0

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val average = mean(list)
    for (i in 0 until list.size) {
        list[i] -= average
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var c = 0
    for (i in a.indices) {
        c += a[i] * b[i]
    }
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var result = 0
    for (i in p.indices) {
        result += p[i] * x.toDouble().pow(i.toDouble()).toInt()
    }
    return result
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    var sum = 0
    for (i in 0 until list.size) {
        sum += list[i]
        list[i] = sum
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var nn = n
    var result = listOf<Int>()
    while (nn > 1) {
        result += minDivisor(nn)
        nn /= minDivisor(nn)
    }
    return result
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var nn = n
    val result = mutableListOf<Int>()
    while (nn > 0) {
        result.add(0, nn % base)
        nn /= base
    }
    if (n == 0) result.add(0, 0)
    return result
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun returnWord(digit: Int): String {
    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    var result = ""
    if (digit > 9) result += alphabet[digit - 10] else result = "$digit"
    return result
}

fun revertString(string: String): String {
    var result = ""
    for (i in string.length - 1 downTo 0) {
        result += string[i]
    }
    return result
}

fun convertToString(n: Int, base: Int): String {
    var nn = n
    var division: Int
    var result = ""
    while (nn > 0) {
        division = nn % base
        result += returnWord(division)
        nn /= base
    }
    if (n == 0) result = "0"
    return revertString(result)
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var result = 0
    var ii = digits.size - 1
    for (element in digits) {
        result += element * (base.toDouble()).pow(ii).toInt()
        ii--
    }
    return result
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun returnDigit(Word: Char): Int {
    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    val digits = "0123456789"
    val result: Int
    result = if (Word.toInt() in 48..57) digits.indexOf(Word, 0) else
        alphabet.indexOf(Word, 0) + 10
    return result
}

fun decimalFromString(str: String, base: Int): Int {
    var result = 0
    for ((t, i) in (str.length - 1 downTo 0).withIndex()) {
        result += returnDigit(str[i]) * ((base.toDouble()).pow(t)).toInt()
    }
    return result
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun rim(number: Char, position: Int): String {
    val about10 = listOf("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX")
    val about100 = listOf("X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC")
    val about1000 = listOf("C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM")
    val notAbout1000 = "M"
    val digit = number.toInt() - 49
    return if (digit > -1) when (position) {
        1 -> about10[digit]
        2 -> about100[digit]
        3 -> about1000[digit]
        else -> {
            var preResult = ""
            for (i in 0..digit) {
                preResult += notAbout1000
            }
            preResult
        }
    } else "-1"
}

fun roman(n: Int): String {
    val nn = n.toString()
    var answer = ""
    for (i in nn.indices) {
        if (rim(nn[i], nn.length - i) != "-1") answer += rim(nn[i], nn.length - i) else continue
    }
    return answer
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun takeDigit(num: Char, next: Int, category: Int): String {
    val less10 = listOf("один ", "два ", "три ", "четыре ", "пять ", "шесть ", "семь ", "восемь ", "девять ")
    val exclusion = listOf(
        "десять ",
        "одиннадцать ",
        "двенадцать ",
        "тринадцать ",
        "цетырнадцать ",
        "пятнадцать ",
        "шестнадцать ",
        "семнадцать ",
        "восемнадцать ",
        "девятнадцать "
    )
    val less100 =
        listOf(
            "",
            "двадцать ",
            "тридцать ",
            "сорок ",
            "пятьдесят ",
            "шестьдесят ",
            "семьдесят ",
            "восемьдесят ",
            "девяносто "
        )
    val less1000 =
        listOf(
            "сто ",
            "двести ",
            "триста ",
            "четыреста ",
            "пятьсот ",
            "шетьсот ",
            "семьсот ",
            "восемсот ",
            "девятьсот "
        )
    val more1000 = listOf(
        "одна тысяча ",
        "две тысячи ",
        "три тысячи ",
        "четыре тысячи ",
        "пять тысяч ",
        "шеть тысяч ",
        "семь тысяч ",
        "восемь тысяч ",
        "девять тысяч "
    )
    val digit = num.toInt() - 49
    return if (digit > -1) when (category) {
        1 -> less10[digit]
        2 -> if (digit > 0) less100[digit] else exclusion[next]
        3 -> less1000[digit]
        else -> more1000[digit]
    } else "-1"
}

fun nn1exist(str: String): String {
    var result = ""
    var wantContinue = false
    var next: Int
    for (i in str.indices) {
        if (wantContinue) {
            wantContinue = false
            continue
        }
        next = if (i == str.length - 1) -1 else str[i + 1].toInt() - 48
        if (str[i] == '1' && i + 2 == str.length) wantContinue = true
        if (takeDigit(str[i], next, str.length - i) != "-1") result += takeDigit(
            str[i],
            next,
            str.length - i
        ) else continue
    }
    if (str.last().toInt() - 48 == 2) result = result.substring(0, result.length - 2) + "е "
    result += when (str.last().toInt() - 48) {
        1 -> "тысяча "
        in 2..4 -> "тысячи "
        else -> "тысяч "
    }
    return result
}

fun russian(n: Int): String {
    val nn: String
    var nn1 = ""
    var answer = ""
    var next: Int
    var wantContinue = false
    if (n > 10000) {
        nn1 = (n / 1000).toString()
        nn = (n % 1000).toString()
    } else nn = n.toString()
    if (nn1 != "") answer += nn1exist(nn1)
    for (i in nn.indices) {
        if (wantContinue) {
            wantContinue = false
            continue
        }
        next = if (i == nn.length - 1) -1 else nn[i + 1].toInt() - 48
        if (nn[i] == '1' && i + 2 == nn.length) wantContinue = true
        if (takeDigit(nn[i], next, nn.length - i) != "-1") answer += takeDigit(nn[i], next, nn.length - i) else continue
    }
    return answer.substring(0, answer.length - 1)
}