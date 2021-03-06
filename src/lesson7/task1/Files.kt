@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    val inputText = File(inputName).readText()
    for (string in substrings) {
        result[string] = 0
        var previousEntry = inputText.indexOf(string, 0, true)
        while (previousEntry > -1) {
            result[string] = result[string]!! + 1
            previousEntry = inputText.indexOf(string, previousEntry + 1, true)
        }
    }
    return result
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines()) {
        if (line.isNotEmpty()) {
            if (!Regex("""([жЖшШчЧщЩ][ыЫяЯюЮ])""").containsMatchIn(line)) outputStream.write(line)
            else {
                outputStream.write(line[0].toString())
                for (i in 1 until line.length) {
                    outputStream.write(
                        if (Regex("""[жЖшШчЧщЩ]""").containsMatchIn(line[i - 1].toString()))
                            foo(line[i], line[i].isLowerCase()) else line[i].toString()
                    )
                }
            }
        }
        outputStream.newLine()
    }
    outputStream.close()
}

fun foo(char: Char, lower: Boolean): String {
    return when (char.toLowerCase()) {
        'ы' -> if (lower) "и" else "И"
        'ю' -> if (lower) "у" else "У"
        'я' -> if (lower) "а" else "А"
        else -> char.toString()
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val inputStream = theLongestLine(File(inputName).readLines(), true)
    val outputStream = File(outputName).bufferedWriter()
    val largestLength = inputStream.last().toInt()
    for (line in inputStream) {
        if (line.toIntOrNull() != null) continue
        outputStream.write(line.padStart(line.length + (largestLength - line.length) / 2, ' '))
        outputStream.newLine()
    }
    outputStream.close()
}

fun theLongestLine(inputLines: List<String>, wantToCenter: Boolean): List<String> {
    var count = 0
    val outputLines = mutableListOf<String>()
    for (i in inputLines.indices) {
        if (wantToCenter) {
            val lineWithOutSpaces = inputLines[i].trim()
            outputLines.add(lineWithOutSpaces)
            val currentLength = lineWithOutSpaces.length
            if (currentLength > count) count = currentLength
        } else {
            val splitted = inputLines[i].split(" ").filter { it != "" }
            val currentLength = inputLines[i].count { it != ' ' } + splitted.size - 1
            if (currentLength > count) count = currentLength
            outputLines.add(splitted.joinToString(" "))
        }
    }
    outputLines.add("$count")
    return outputLines
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val inputStream = theLongestLine(File(inputName).readLines(), false)
    val outputStream = File(outputName).bufferedWriter()
    val largestLength = inputStream.last().toInt()
    for (line in inputStream) {
        if (line.toIntOrNull() != null) continue
        if (line.isNotBlank()) {
            var currentLength = 0
            var countOfSpaces = 0
            val splitted = line.split(" ")
            val length = line.count { it != ' ' }
            val numberOfSpace =
                (largestLength - length) / if (splitted.size != 1) splitted.size - 1 else 1
            for (index in splitted.indices) {
                val word = splitted[index]
                currentLength += word.length
                if (index == splitted.lastIndex) {
                    outputStream.write(splitted[index])
                    continue
                }
                if (length + countOfSpaces + (splitted.size - 1 - index) * numberOfSpace != largestLength) {
                    outputStream.write(word.padEnd(word.length + numberOfSpace + 1, ' '))
                    countOfSpaces += 1
                } else outputStream.write(word.padEnd(word.length + numberOfSpace, ' '))
                countOfSpaces += numberOfSpace
            }
        }
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    val strings = File(inputName).readText().toLowerCase().split(Regex("""[^a-zа-яё]"""))
    val wordsNoRepeats = strings.toSet()
    for (word in wordsNoRepeats) {
        if (word == "") continue
        result[word] = strings.count { it == word }
    }
    if (result.size > 20)
        return result.toList().sortedBy { (_, value) -> value }.reversed().dropLast(result.size - 20).toMap()
    return result
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    File(outputName).bufferedWriter().use {
        for (char in File(inputName).readText()) {
            val newChar = if (dictionary[char.toLowerCase()] != null) {
                val charInDictionary = dictionary.getValue(char.toLowerCase()).toLowerCase()
                if (char.isUpperCase() && charInDictionary != "")
                    charInDictionary[0].toUpperCase() + charInDictionary.drop(1) else charInDictionary
            } else if (dictionary[char.toUpperCase()] != null) {
                val charInDictionary = dictionary.getValue(char.toUpperCase()).toLowerCase()
                if (char.isUpperCase() && charInDictionary != "")
                    charInDictionary[0].toUpperCase() + charInDictionary.drop(1) else charInDictionary
            } else if (char.toUpperCase() == 'I' && dictionary['ı'] != null) {
                val charInDictionary = dictionary.getValue('ı').toLowerCase()
                if (char.isUpperCase() && charInDictionary != "")
                    charInDictionary[0].toUpperCase() + charInDictionary.drop(1) else charInDictionary
            } else char.toString()
            it.write(newChar)
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val inputStream = File(inputName).readLines()
    val outputStream = File(outputName).bufferedWriter()
    val listOfChaoticWords = mutableListOf<String>()
    var longestChaoticWord = -1
    for (word in inputStream) {
        if (allCharsDifferent(word)) {
            listOfChaoticWords.add(word)
            longestChaoticWord = maxOf(longestChaoticWord, word.length)
        }
    }
    outputStream.write(listOfChaoticWords.filter { it.length == longestChaoticWord }.joinToString(", "))
    outputStream.close()
}

fun allCharsDifferent(String: String): Boolean {
    val list = mutableListOf<Char>()
    for (char in String.toLowerCase()) {
        if (list.isNotEmpty() && char in list) return false
        list.add(char)
    }
    return true
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val inputStream = File(inputName).readLines()
    val outputStream = File(outputName).bufferedWriter()
    outputStream.write("<html>\n" + "<body>\n" + "<p>")
    val info = mutableListOf(false, false, false)
    var openedP = true
    var paragraph = false
    for (line in inputStream) {
        var wantToContinue = false
        val changedLine = mutableListOf<String>()
        if (!openedP && line.isNotEmpty()) {
            outputStream.write("<p>")
            openedP = true
        }
        if (line.isEmpty()) {
            if (paragraph) {
                outputStream.write("</p>\n")
                openedP = false
                paragraph = false
            }
        } else {
            paragraph = true
            for (i in 1 until line.length) {
                if (wantToContinue) {
                    wantToContinue = false
                    continue
                }
                when {
                    line[i - 1] == '*' -> if (line[i] == '*') {
                        wantToContinue = true
                        if (!info[0]) {
                            changedLine.add("<b>")
                            info[0] = true
                        } else {
                            changedLine.add("</b>")
                            info[0] = false
                        }
                    } else if (!info[1]) {
                        changedLine.add("<i>")
                        info[1] = true
                    } else {
                        changedLine.add("</i>")
                        info[1] = false
                    }
                    line[i - 1] == '~' && line[i] == '~' -> {
                        wantToContinue = true
                        if (!info[2]) {
                            changedLine.add("<s>")
                            info[2] = true
                        } else {
                            changedLine.add("</s>")
                            info[2] = false
                        }
                    }
                    else -> changedLine.add(line[i - 1].toString())
                }
            }
            if (!wantToContinue)
                if (line.takeLast(1) == "*") if (!info[1]) {
                    changedLine.add("<i>")
                    info[1] = true
                } else {
                    changedLine.add("</i>")
                    info[1] = false
                } else changedLine.add(line.takeLast(1))
            outputStream.write(changedLine.joinToString(""))
        }
        outputStream.newLine()
    }
    if (openedP) outputStream.write("</p>\n")
    outputStream.write("</body>\n" + "</html>\n")
    outputStream.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    htmlLists(inputName, outputName, true)
}

fun htmlLists(inputName: String, outputName: String, wantToAdd: Boolean) {
    val inputStream = File(inputName).readLines()
    File(outputName).bufferedWriter().use {
        if (wantToAdd) it.write("<html>\n" + "<body>\n")
        val openLi = MutableList(6) { false }
        val openUl = MutableList(6) { false }
        val openOl = MutableList(6) { false }
        var previousSpaces = 0
        var u = 0
        var o = 0
        var l = 0
        var last = ""
        fun change(type: Char, spaces: Int, stack: MutableList<String>) {
            val nesting = if (type == 'u') openUl else openOl
            var i = if (type == 'u') u else o
            if (!nesting[i]) {
                when {
                    previousSpaces < spaces || l == 0 -> {
                        last = "$type"
                        nesting[i] = true
                        i++
                        stack.add("<" + "$type" + "l>\n")
                    }
                    previousSpaces == spaces -> {
                        last = "$type"
                        l--
                        openLi[l] = false
                        stack.add("</li>\n")
                    }
                    previousSpaces > spaces -> {
                        l--
                        openLi[l] = false
                        stack.add("</li>\n")
                        last = ""
                        i--
                        nesting[i] = false
                        stack.add("</" + "$type" + "l>\n")
                        if (l > 0) {
                            l--
                            openLi[l] = false
                            stack.add("</li>\n")
                        }
                    }
                }
            }
            if (type == 'u') {
                openUl.indices.forEach { index -> openUl[index] = nesting[index] }
                u = i
            } else {
                openOl.indices.forEach { index -> openOl[index] = nesting[index] }
                o = i
            }
        }
        for (line in inputStream) {
            val ul = line.matches(Regex("""\s*\*\s.*"""))
            val ol = line.matches(Regex("""\s*\d+\.\s.*"""))
            val changedLine = mutableListOf<String>()
            val spaces = multipleOfFour(line)
            if (spaces % 4 == 0) {
                when {
                    ul -> {
                        if (last == "o" && previousSpaces > spaces) change('o', spaces, changedLine) else
                            change('u', spaces, changedLine)
                        if (!openLi[l]) {
                            openLi[l] = true
                            l++
                            changedLine.add("<li>")
                        }
                        changedLine.add(line.replaceFirst(Regex("""\s*\*\s"""), ""))
                    }
                    ol -> {
                        if (last == "u" && previousSpaces > spaces) change('u', spaces, changedLine)
                        else change('o', spaces, changedLine)
                        if (!openLi[l]) {
                            openLi[l] = true
                            l++
                            changedLine.add("<li>")
                        }
                        changedLine.add(line.replaceFirst(Regex("""\s*\d+\.\s"""), ""))
                    }
                    else -> {
                        if (line.isEmpty()) {
                            if (l > 0) {
                                l--
                                openLi[l] = false
                                changedLine.add("</li>\n")
                            }
                            when (last) {
                                "u" -> {
                                    u--
                                    openUl[u] = false
                                    changedLine.add("</ul>\n")
                                }
                                "o" -> {
                                    o--
                                    openOl[o] = false
                                    changedLine.add("</ol>\n")
                                }
                            }
                            last = ""
                        }
                        changedLine.add(line)
                        changedLine.add("\n")
                    }
                }
            } else {
                changedLine.add(line)
                changedLine.add("\n")
            }
            if (inputStream.lastIndex == inputStream.indexOf(line)) {
                var previous = ""
                fun lastOfUs(type: Char) {
                    var i = if (type == 'u') u else o
                    i--
                    changedLine.add("</" + "$type" + "l>\n")
                    previous = "$type"
                    if (type == 'u') u = i else o = i
                }
                while (l > 0 || u > 0 || o > 0) {
                    if (l > 0) {
                        l--
                        openLi[l] = false
                        changedLine.add("</li>\n")
                    }
                    when {
                        u > o -> lastOfUs('u')

                        u < o -> lastOfUs('o')

                        else -> {
                            when (previous) {
                                "u" -> lastOfUs('u')
                                "o" -> lastOfUs('o')
                                "" -> if (last != "") lastOfUs(last[0])
                            }
                        }
                    }
                }
            }
            it.write(changedLine.joinToString(""))
            previousSpaces = spaces
        }
        if (wantToAdd) it.write("\n</body>\n" + "</html>")
    }
}

fun multipleOfFour(line: String): Int = (line.length - line.dropWhile { it == ' ' }.length)

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    htmlLists(inputName, outputName, false)
    val tmp = File(outputName).readText()
    val output = File(outputName).bufferedWriter()
    output.write(tmp)
    output.close()
    markdownToHtmlSimple(outputName, outputName)
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    var number = rhv
    val answer = (lhv * rhv).toString()
    val length = answer.length
    File(outputName).bufferedWriter().use {
        it.write(lhv.toString().padStart(length + 1))
        it.newLine()
        it.write("*" + rhv.toString().padStart(length))
        it.newLine()
        repeat(length + 1) { _ -> it.write("-") }
        var i = 0
        while (number != 0) {
            it.newLine()
            val num = (number % 10 * lhv).toString()
            if (i > 0) {
                it.write("+")
            }
            if (i == 1) i++
            repeat(length - num.length + 1 - i) { _ -> it.write(" ") }
            it.write(num)
            number /= 10
            i++
        }
        it.newLine()
        repeat(length + 1) { _ -> it.write("-") }
        it.newLine()
        it.write(answer.padStart(length + 1))
    }
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

