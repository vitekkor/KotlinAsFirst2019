@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val result = mutableMapOf<Int, MutableList<String>>()
    for ((student, grade) in grades) {
        result.getOrPut(grade, { mutableListOf() }).add(student)
    }
    return result
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((str, _) in a) {
        if (a[str] != b[str]) return false
    }
    return true
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit {
    for ((str, _) in b) {
        if (a[str] == b[str]) a.remove(str)
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.toSet().intersect(b.toSet()).toList()

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val result = mutableMapOf<String, String>()
    for ((service, phone) in mapA) {
        result.put(service, phone)
    }
    for ((service, phone) in mapB) {
        if (result[service] != null) {
            if (result[service] != phone) result.put(service, result[service] + ", " + phone)
        } else result.put(service, phone)
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val result = mutableMapOf<String, Double>()
    val costs = mutableMapOf<String, Double>()
    for ((act, cost) in stockPrices) {
        result.put(act, (result[act] ?: 0.0) + cost)
        costs.put(act, (costs[act] ?: 0.0) + 1.0)
    }
    for ((act, cost) in result) {
        result.put(act, cost / costs[act]!!)
    }
    return result
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var cheaperThenAll = Double.MAX_VALUE
    var result: String? = null
    for ((name, thing) in stuff) {
        if (thing.first == kind && thing.second <= cheaperThenAll) {
            cheaperThenAll = thing.second
            result = name
        }
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val result = word.indices.any {
        word[it].toUpperCase() !in chars && word[it].toLowerCase() !in chars
    }
    return !result
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    for (element in list) {
        result[element] = (result[element] ?: 0) + 1
    }
    return result.filter { (_, i) -> i >= 2 }
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    var answer: Boolean
    for (i in words.indices) {
        words.indices.forEach {
            answer =
                it != i &&
                        ((words[it] != words[i] &&
                                (words[i].contains(words[it]) || words[i].contains(words[it].reversed()))) ||
                                (words[it] == words[i]))
            if (answer) return true
        }
    }
    return false
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val result = mutableMapOf<String, MutableSet<String>>()
    for ((friend, hands) in friends) {
        result.getOrPut(friend, { mutableSetOf() }).addAll(hands)
        if (result[friend] != null) {
            do {
                val toAdd = mutableSetOf<String>()
                val lastSize = result[friend]!!.size
                result[friend]!!.forEach {
                    if (friends[it] != null) friends[it]!!.forEach { element ->
                        if (element != friend) toAdd.add(element)
                    }
                }
                result[friend]!!.addAll(toAdd)
            } while (lastSize < result[friend]!!.size)
        }
    }
    for ((_, hands) in friends) {
        hands.forEach { if (friends[it] == null) result.getOrPut(it, { mutableSetOf() }) }
    }
    return result
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val result = Pair(-1, -1)
    var kkey = 0
    list.indices.forEach {
        if (list.indices.any { key ->
                kkey = key
                number - list[it] == list[key] && it != key
            }) {
            val first = minOf(it, kkey)
            val second = maxOf(it, kkey)
            return Pair(first, second)
        }
    }
    return result
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> { //Set<String>
    val answer = mutableMapOf<Int, MutableMap<Int, Int>>()
    val result = mutableMapOf<Int, MutableMap<Int, MutableSet<String>>>()
    for (i in 0..capacity) {   // заполняем 1 строку и 1 столбец матрицы 0
        answer[0] = mutableMapOf(i to 0) // т.к если 0 элементов, то макс стоимость рюкзака 0
        result[0] = mutableMapOf(i to mutableSetOf()) // аналогичная матрица, но для названий сокровищ
    }
    for (i in 0..treasures.size) {
        answer[i] = mutableMapOf(0 to 0) // и если максимальная вместимость 0, то макс стоимость тоже рюкзака 0
        result[i] = mutableMapOf(0 to mutableSetOf())
    }
    for (j in 1..treasures.size) { // проходимся по всем элементам от 1-го до N-го
        for (i in 0..capacity) {
            val mj = treasures[analogString(treasures, j)]!!.first // масса j-го сокровища
            val pj = treasures[analogString(treasures, j)]!!.second // стоимость j-го сокровища
            if (mj > i) {
                answer[j]!![i] = answer[j - 1]!![i] ?: 0
                result[j]!![i] = result[j - 1]!![i] ?: mutableSetOf()
            }
            // если это сокровище не получается положить в рюкзак
            // то рассматриваем уже последовательность не от 1 до j, а от 1 до j-1
            else {
                answer[j]!![i] = maxOf(answer[j - 1]!![i] ?: 0, pj + (answer[j - 1]!![i - mj] ?: 0))
                if (answer[j]!![i] == answer[j - 1]!![i]) result[j]!![i] = result[j - 1]!![i] ?: mutableSetOf()
                else result[j]!!.getOrPut(i, { mutableSetOf() }).add(analogString(treasures, j))
            }
            // Если сокровище можно положить
            // то ищем макс стоимость между тем вариантом, когда соровище входит
            // (при этом вместимость уменьшаем на массу сокровища, прибавляем его стоимость и
            // рассамтриваем последовательность от 1 до j-1)
            // и когда сокровище не входит (рассамтриваем последовательность от 1 до j-1)
        }
    }
    //return answer[treasures.size]!![capacity]!!
    return result[treasures.size]!![capacity]!!
}

fun analogString(map: Map<String, Pair<Int, Int>>, number: Int): String {
    //возвращает скоровище по порядковому номеру (от 1 до N)
    var i = 0
    for ((element, _) in map) {
        i++
        if (i == number) return element
    }
    return ""
}
