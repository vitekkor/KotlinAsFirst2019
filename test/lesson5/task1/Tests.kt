package lesson5.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun shoppingListCostTest() {
        val itemCosts = mapOf(
            "Хлеб" to 50.0,
            "Молоко" to 100.0
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко"),
                itemCosts
            )
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                itemCosts
            )
        )
        assertEquals(
            0.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                mapOf()
            )
        )
    }

    @Test
    @Tag("Example")
    fun filterByCountryCode() {
        val phoneBook = mutableMapOf(
            "Quagmire" to "+1-800-555-0143",
            "Adam's Ribs" to "+82-000-555-2960",
            "Pharmakon Industries" to "+1-800-555-6321"
        )

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+999")
        assertEquals(0, phoneBook.size)
    }

    @Test
    @Tag("Example")
    fun removeFillerWords() {
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю Котлин".split(" "),
                "как-то"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю таки Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я люблю Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
    }

    @Test
    @Tag("Example")
    fun buildWordSet() {
        assertEquals(
            buildWordSet("Я люблю Котлин".split(" ")),
            mutableSetOf("Я", "люблю", "Котлин")
        )
        assertEquals(
            buildWordSet("Я люблю люблю Котлин".split(" ")),
            mutableSetOf("Котлин", "люблю", "Я")
        )
        assertEquals(
            buildWordSet(listOf()),
            mutableSetOf<String>()
        )
    }

    @Test
    @Tag("Easy")
    fun buildGrades() {
        assertEquals(
            mapOf<Int, List<String>>(),
            buildGrades(mapOf())
        )
        assertEquals(
            mapOf(5 to listOf("Михаил", "Семён"), 3 to listOf("Марат")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
                .mapValues { (_, v) -> v.sorted() }
        )
        assertEquals(
            mapOf(3 to listOf("Марат", "Михаил", "Семён")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 3, "Михаил" to 3))
                .mapValues { (_, v) -> v.sorted() }
        )
    }

    @Test
    @Tag("Easy")
    fun containsIn() {
        assertTrue(containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")))
        assertTrue(
            containsIn(
                mapOf("a" to "z", "b" to "sweet", "zorro" to "strike"),
                mapOf("a" to "z", "b" to "sweet", "xc" to "ccc", "zorro" to "strike")
            )
        )
        assertFalse(containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")))
    }

    @Test
    @Tag("Easy")
    fun subtractOf() {
        val from = mutableMapOf("a" to "z", "b" to "c")

        subtractOf(from, mapOf())
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("b" to "z"))
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("a" to "z"))
        assertEquals(from, mapOf("b" to "c"))
    }

    @Test
    @Tag("Easy")
    fun whoAreInBoth() {
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(emptyList(), emptyList())
        )
        assertEquals(
            listOf("Marat"),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Marat", "Kirill"))
        )
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Sveta", "Kirill"))
        )
    }

    @Test
    @Tag("Normal")
    fun mergePhoneBooks() {
        assertEquals(
            mapOf("Emergency" to "112"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Fire department" to "01", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112", "Fire department" to "01"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
    }

    @Test
    @Tag("Normal")
    fun averageStockPrice() {
        assertEquals(
            mapOf<String, Double>(),
            averageStockPrice(listOf())
        )
        assertEquals(
            mapOf("MSFT" to 100.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 45.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0, "NFLX" to 50.0))
        )
    }

    @Test
    @Tag("Normal")
    fun findCheapestStuff() {
        assertNull(
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "торт"
            )
        )
        assertEquals(
            "Мария",
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "печенье"
            )
        )
        assertEquals(
            "",
            findCheapestStuff(
                mapOf("" to ("" to 1.7976931348623157e+308)),
                ""
            )
        )
    }

    @Test
    @Tag("Normal")
    fun canBuildFrom() {
        assertFalse(canBuildFrom(emptyList(), "foo"))
        assertTrue(canBuildFrom(listOf('a', 'b', 'o'), "baobab"))
        assertFalse(canBuildFrom(listOf('a', 'm', 'r'), "Marat"))
        assertTrue(canBuildFrom(listOf('O'), "O"))
        assertTrue(canBuildFrom(listOf('n'), "N"))
    }

    @Test
    @Tag("Normal")
    fun extractRepeats() {
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(emptyList())
        )
        assertEquals(
            mapOf("a" to 2),
            extractRepeats(listOf("a", "b", "a"))
        )
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(listOf("a", "b", "c"))
        )
    }

    @Test
    @Tag("Normal")
    fun hasAnagrams() {
        assertFalse(hasAnagrams(emptyList()))
        assertTrue(hasAnagrams(listOf("рот", "свет", "тор")))
        assertFalse(hasAnagrams(listOf("рот", "свет", "код", "дверь")))
        assertFalse(hasAnagrams(listOf("ротобрашь", "свет", "тор")))
        assertTrue(hasAnagrams(listOf("", "")))
    }

    @Test
    @Tag("Hard")
    fun propagateHandshakes() {
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Mikhail"),
                "Mikhail" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Sveta"),
                    "Sveta" to setOf("Mikhail")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta", "John", "Ignat", "Vasiliy"),
                "Sveta" to setOf("Mikhail", "John", "Ignat", "Vasiliy"),
                "Mikhail" to setOf(),
                "John" to setOf(),
                "Ignat" to setOf(),
                "Vasiliy" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Sveta"),
                    "Sveta" to setOf("Mikhail", "John", "Ignat", "Vasiliy")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Marat", "Mikhail"),
                "Mikhail" to setOf("Sveta", "Marat")
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Mikhail", "Sveta"),
                    "Sveta" to setOf("Marat"),
                    "Mikhail" to setOf("Sveta")
                )
            )
        )
        assertEquals(
            mapOf(
                "0" to setOf("136"),
                "135" to setOf("0", "2cc", "136"),
                "136" to setOf(),
                "2cc" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "0" to setOf("136"),
                    "135" to setOf("0", "2cc")
                )
            )
        )
    }

    @Test
    @Tag("Hard")
    fun findSumOfTwo() {
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(emptyList(), 1)
        )
        assertEquals(
            Pair(0, 2),
            findSumOfTwo(listOf(1, 2, 3), 4)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(1, 2, 3), 6)
        )
        assertEquals(
            Pair(0, 1),
            findSumOfTwo(listOf(5, 0, 3), 5)
        )
        assertEquals(
            Pair(3, 4),
            findSumOfTwo(listOf(255, 622, 357, 0, 3, 3), 3)
        )
        assertEquals(
            Pair(0, 1),
            findSumOfTwo(listOf(0, 0), 0)
        )
    }

    @Test
    @Tag("Impossible")
    fun bagPacking() {
        assertEquals(
            setOf("Кубок"),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                850
            )
        )
        assertEquals(
            emptySet<String>(),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                450
            )
        )
        assertEquals(
            setOf("Kotel"),
            bagPacking(mapOf("Kotel" to (500 to 2500), "Kot;" to (200 to 2000), "hh" to (300 to 400)), 600)
        )
        assertEquals(
            setOf("0", "1"),
            bagPacking(
                mapOf("0" to (1 to 1), "1" to (2 to 1)),
                3
            )
        )
    }
}
