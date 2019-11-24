@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence", "SENSELESS_COMPARISON")

package lesson6.task1

import lesson2.task2.daysInMonth
import lesson5.task1.canBuildFrom

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
/**fun main() {
println("Введите время в формате ЧЧ:ММ:СС")
val line = readLine()
if (line != null) {
val seconds = timeStrToSeconds(line)
if (seconds == -1) {
println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
} else {
println("Прошло секунд с начала суток: $seconds")
}
} else {
println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
}
}*/


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    if (!str.matches(Regex("""\d{1,2} [а-я]+ \d+"""))) return ""
    val months = listOf(
        "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
        "августа", "сентября", "октября", "ноября", "декабря"
    )
    val partsOfDate = str.split(" ")
    val day = partsOfDate[0].toInt()
    val month = months.indexOf(partsOfDate[1]) + 1
    val year = partsOfDate[2].toInt()
    return if (day <= daysInMonth(month, year) && month != 0) String.format("%02d.%02d.%d", day, month, year)
    else ""

}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    if (!digital.matches(Regex("""(\d{2}\.){2}\d+"""))) return ""
    val months = listOf(
        "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
        "августа", "сентября", "октября", "ноября", "декабря"
    )
    val partsOfDate = digital.split(".")
    val day = partsOfDate[0].toInt()
    val month = partsOfDate[1].toInt()
    val year = partsOfDate[2].toInt()
    return if (month in 1..12 && day <= daysInMonth(month, year)) String.format(
        "%d %s %d", day, months[month - 1], year
    ) else ""
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val filterSpaces = phone.filter { it != ' ' }
    if ((Regex("""\([^\d -]""")).containsMatchIn(filterSpaces) ||
        !(Regex("""[+\d\-()]+""")).matches(filterSpaces) || phone == "+"
    ) return ""
    return filterSpaces.filter { it !in listOf('-', '(', ')') }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (!jumps.matches(Regex("""(\d+[\- %]*)+"""))) return -1
    var result = -1
    val attempts = jumps.split(" ")
    for (attempt in attempts) {
        if (attempt.toIntOrNull() != null) result = maxOf(attempt.toInt(), result)
    }
    return result
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (!jumps.matches(Regex("""(\d+ [%+-]+ ?)+"""))) return -1
    val attempts = jumps.split(" ")
    var result = -1
    for (attempt in 1 until attempts.size step 2) {
        if (attempts[attempt].any { it == '+' }) result = maxOf(result, attempts[attempt - 1].toInt())
    }
    return result
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    require(expression.matches(Regex("""(\d+ [+-] )*\d+""")))
    val expressionToCalculate = expression.split(" ")
    var result = expressionToCalculate[0].toInt()
    for (i in 2 until expressionToCalculate.size step 2) {
        val digit = expressionToCalculate[i].toInt()
        result += if (expressionToCalculate[i - 1] == "-") -1 * digit else digit
    }
    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    if (!str.matches(Regex("""(.+ +)+.+"""))) return -1
    val partsOfString = str.split(" ")
    var sumOfLength = partsOfString[0].length
    for (i in 1 until partsOfString.size) {
        if (partsOfString[i].toLowerCase() == partsOfString[i - 1].toLowerCase()) return sumOfLength - partsOfString[i].length
        sumOfLength += partsOfString[i].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (!description.matches(Regex("""(.+ \d+(\.\d+)*(; .+ \d+(\.\d+)*)*)+"""))) return ""
    val products = description.split("; ")
    var result = ""
    var mostExpensive = -1.0
    for (product in products) {
        if (product.split(" ")[1].toDouble() > mostExpensive) {
            mostExpensive = product.split(" ")[1].toDouble()
            result = product.split(" ")[0]
        }
    }
    return result
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (!roman.matches(Regex("""[IVXLCDM]+"""))) return -1
    val valid = listOf(4, 9, 40, 90, 400, 900)
    var digit = 0
    var result = 0
    while (digit < roman.length - 1) {
        result += romanToArabic(roman[digit], roman[digit + 1])
        if (romanToArabic(roman[digit], roman[digit + 1]) in valid) digit++
        digit++
    }
    if (digit != roman.length) result += romanToArabic(roman[roman.length - 1], 'I')
    if (lesson4.task1.roman(result) != roman) result = -1
    return result
}

fun numbersOfTwoCharacters(secondChar: Char): Int = when (secondChar) {
    'V', 'L', 'D' -> 4
    'X', 'C', 'M' -> 9
    else -> 1
}

fun romanToArabic(chr: Char, nextChr: Char): Int = when (chr) {
    'I' -> numbersOfTwoCharacters(nextChr)
    'X' -> if (nextChr == 'L' || nextChr == 'C') numbersOfTwoCharacters(nextChr) * 10 else numbersOfTwoCharacters('I') * 10
    'C' -> if (nextChr == 'D' || nextChr == 'M') numbersOfTwoCharacters(nextChr) * 100 else numbersOfTwoCharacters('I') * 100
    'V' -> 5
    'L' -> 50
    'D' -> 500
    'M' -> 1000
    else -> -1
}


/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val positionOfBrackets = verifyBrackets(commands)
    if (positionOfBrackets[-1] != null) throw IllegalArgumentException()
    var sensor = cells / 2
    val result = MutableList(cells) { 0 }
    var current = 0
    var count = 0
    while (count < limit && current < commands.length) {
        check(sensor in 0 until result.size)
        when (commands[current]) {
            '+' -> result[sensor]++
            '-' -> result[sensor]--
            '>' -> sensor++
            '<' -> sensor--
            '[' -> if (result[sensor] == 0) {
                current = positionOfBrackets.getValue(current)
            }
            ']' -> if (result[sensor] != 0) {
                var temp = 0
                positionOfBrackets.forEach { if (it.value == current) temp = it.key }
                current = temp
            }
        }
        current++
        count++
    }
    check(sensor in 0 until result.size)
    return result
}

fun verifyBrackets(commands: String): Map<Int, Int> {
    val mapOfBrackets = mutableMapOf<Int, Int>()
    val validCharacters = listOf('+', '-', ' ', '>', '<')
    val listOfNestedBrackets = mutableListOf<Int>()
    for (i in commands.indices) {
        when (commands[i]) {
            '[' -> {
                mapOfBrackets[i] = -1
                listOfNestedBrackets.add(i)
            }
            ']' -> {
                if (listOfNestedBrackets.isNotEmpty()) if (listOfNestedBrackets.last() != null) {
                    mapOfBrackets[listOfNestedBrackets.last()] = i
                    listOfNestedBrackets.removeAt(listOfNestedBrackets.lastIndex)
                } else return mapOf(-1 to -1) else return mapOf(-1 to -1)
            }
            !in validCharacters -> return mapOf(-1 to -1)
        }
    }
    if (mapOfBrackets.any { it.value == -1 }) return mapOf(-1 to -1)
    return mapOfBrackets
}