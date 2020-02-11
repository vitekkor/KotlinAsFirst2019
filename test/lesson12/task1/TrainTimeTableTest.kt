package lesson12.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

class TrainTimeTableTest {

    @Test
    @Tag("Normal")
    fun addTrain() {
        val ttt = TrainTimeTable("СПб")
        assertTrue(ttt.addTrain("N1", Time(6, 35), Stop("Пушкин", Time(7, 4))))
        assertTrue(ttt.addTrain("N2", Time(6, 18), Stop("Пушкин", Time(6, 45))))
        assertFalse(ttt.addTrain("N1", Time(6, 25), Stop("Пушкин", Time(6, 49))))
        assertFalse(ttt.addTrain("N1", Time(6, 35), Stop("Пушкин", Time(6, 4))))
        assertFalse(ttt.addTrain("N1", Time(6, 35), Stop("Купчино", Time(7, 4))))
        assertTrue(ttt.addTrain("N3", Time(12, 18), Stop("Царское Село", Time(6, 45))))
        assertEquals(
            listOf(
                Train("N2", Stop("СПб", Time(6, 18)), Stop("Пушкин", Time(6, 45))),
                Train("N1", Stop("СПб", Time(6, 35)), Stop("Пушкин", Time(7, 4))),
                Train("N3", Stop("СПб", Time(12, 18)), Stop("Царское Село", Time(6, 45)))
            ), ttt.trains()
        )
    }

    @Test
    @Tag("Normal")
    fun removeTrain() {
        val ttt = TrainTimeTable("СПб")
        assertTrue(ttt.addTrain("N1", Time(6, 35), Stop("Пушкин", Time(7, 4))))
        assertTrue(ttt.addTrain("N2", Time(6, 18), Stop("Пушкин", Time(6, 45))))
        assertFalse(ttt.removeTrain("N3"))
        assertTrue(ttt.removeTrain("N1"))
        assertEquals(1, ttt.trains().size)
        assertTrue(ttt.removeTrain("N2"))
        assertFalse(ttt.removeTrain("N2"))
        assertEquals(0, ttt.trains().size)
    }

    @Test
    @Tag("Hard")
    fun addStop() {
        val ttt = TrainTimeTable("СПб")
        assertTrue(ttt.addTrain("N1", Time(6, 35), Stop("Пушкин", Time(7, 4))))
        assertTrue(ttt.addTrain("N2", Time(6, 18), Stop("Пушкин", Time(6, 45))))
        assertFalse(ttt.addStop("N1", Stop("СПб", Time(6, 31))))
        assertTrue(ttt.addStop("N1", Stop("Купчино", Time(6, 46))))
        assertEquals(
            listOf(Stop("СПб", Time(6, 31)), Stop("Купчино", Time(6, 46)), Stop("Пушкин", Time(7, 4))),
            ttt.getTrain("N1").stops
        )
        assertThrows(IllegalArgumentException::class.java) {
            ttt.addStop("N1", Stop("Неверная", Time(7, 11)))
        }
        assertThrows(IllegalArgumentException::class.java) {
            ttt.addStop("N1", Stop("СПб", Time(6, 50)))
        }
        assertFalse(ttt.addStop("N1", Stop("Купчино", Time(6, 44))))
        assertFalse(ttt.addStop("N1", Stop("Пушкин", Time(7, 0))))
        assertEquals(
            listOf(Stop("СПб", Time(6, 31)), Stop("Купчино", Time(6, 44)), Stop("Пушкин", Time(7, 0))),
            ttt.getTrain("N1").stops
        )
        assertThrows(IllegalArgumentException::class.java) {
            ttt.addStop("N1", Stop("Пушкин", Time(6, 40)))
        }
        ttt.addStop("N1", Stop("Пушкин", Time(16, 10)))
        assertEquals(Time(16, 10), ttt.getTrain("N1").stops.last().time)
        val ttt2 = TrainTimeTable("Москва")
        ttt2.addTrain("N1", Time(7, 0), Stop("Воронеж", Time(14, 22)))
        ttt2.addTrain("N2", Time(0, 20), Stop("Санкт-Петербург", Time(8, 59)))
        assertTrue(ttt2.addStop("N1", Stop("Мичуринск", Time(8, 59))))
        assertFalse(ttt2.addStop("N1", Stop("Мичуринск", Time(8, 59))))
        assertEquals(
            listOf(Stop("Москва", Time(7, 0)), Stop("Мичуринск", Time(8, 59)), Stop("Воронеж", Time(14, 22))),
            ttt2.getTrain("N1").stops
        )
        assertThrows(IllegalArgumentException::class.java) { ttt2.addStop("N1", Stop("Бологое", Time(6, 59))) }
        assertThrows(IllegalArgumentException::class.java) { ttt2.addStop("N1", Stop("Мичуринск", Time(14, 59))) }
        assertThrows(IllegalArgumentException::class.java) { ttt2.addStop("N1", Stop("Тула", Time(7, 0))) }
        assertThrows(IllegalArgumentException::class.java) { ttt2.addStop("N1", Stop("Рязань", Time(14, 22))) }
        assertTrue(ttt2.addStop("N1", Stop("Рязань", Time(12, 22))))
        assertTrue(ttt2.addStop("N1", Stop("Тула", Time(10, 22))))
        assertEquals(
            listOf(
                Stop("Москва", Time(7, 0)),
                Stop("Мичуринск", Time(8, 59)),
                Stop("Тула", Time(10, 22)),
                Stop("Рязань", Time(12, 22)),
                Stop("Воронеж", Time(14, 22))
            ),
            ttt2.getTrain("N1").stops
        )
        assertFalse(ttt2.addStop("N1", Stop("Рязань", Time(10, 21))))
        assertEquals(
            listOf(
                Stop("Москва", Time(7, 0)),
                Stop("Мичуринск", Time(8, 59)),
                Stop("Рязань", Time(10, 21)),
                Stop("Тула", Time(10, 22)),
                Stop("Воронеж", Time(14, 22))
            ),
            ttt2.getTrain("N1").stops
        )
        assertThrows(IllegalArgumentException::class.java) { ttt2.addStop("N1", Stop("Воронеж", Time(10, 21))) }
        assertFalse(ttt2.addStop("N1", Stop("Тула", Time(10, 20))))
        assertThrows(IllegalArgumentException::class.java) { ttt2.addStop("N2", Stop("Санкт-Петербург", Time(0, 19))) }
        assertEquals(
            listOf(
                Stop("Москва", Time(7, 0)),
                Stop("Мичуринск", Time(8, 59)),
                Stop("Тула", Time(10, 20)),
                Stop("Рязань", Time(10, 21)),
                Stop("Воронеж", Time(14, 22))
            ),
            ttt2.getTrain("N1").stops
        )
    }

    @Test
    @Tag("Normal")
    fun removeStop() {
        val ttt = TrainTimeTable("СПб")
        assertTrue(ttt.addTrain("N1", Time(6, 35), Stop("Пушкин", Time(7, 4))))
        assertTrue(ttt.addTrain("N2", Time(6, 18), Stop("Пушкин", Time(6, 45))))
        assertTrue(ttt.addStop("N1", Stop("Купчино", Time(6, 48))))
        assertFalse(ttt.removeStop("N1", "Неверная"))
        assertFalse(ttt.removeStop("N1", "СПб"))
        assertFalse(ttt.removeStop("N1", "Пушкин"))
        assertTrue(ttt.removeStop("N1", "Купчино"))
        val ttt2 = TrainTimeTable("Москва")
        ttt2.addTrain("N1", Time(7, 0), Stop("Воронеж", Time(14, 22)))
        assertTrue(ttt2.addStop("N1", Stop("Мичуринск", Time(8, 59))))
        assertTrue(ttt2.addStop("N1", Stop("Рязань", Time(12, 22))))
        assertTrue(ttt2.addStop("N1", Stop("Тула", Time(10, 22))))
        assertTrue(ttt2.addStop("N1", Stop("Тул", Time(10, 23))))
        assertTrue(ttt2.addStop("N1", Stop("Ту", Time(10, 24))))
        assertTrue(ttt2.addStop("N1", Stop("Т", Time(10, 25))))
        for ((name) in ttt2.getTrain("N1").stops) {
            if (name in listOf("Москва", "Воронеж")) assertFalse(ttt2.removeStop("N1", name)) else
                assertTrue(ttt2.removeStop("N1", name))
        }
        assertEquals(
            listOf(Stop("Москва", Time(7, 0)), Stop("Воронеж", Time(14, 22))),
            ttt2.getTrain("N1").stops
        )
    }

    @Test
    @Tag("Hard")
    fun trains() {
        val ttt = TrainTimeTable("СПб")
        assertTrue(ttt.addTrain("N1", Time(6, 35), Stop("Пушкин", Time(7, 6))))
        assertTrue(ttt.addTrain("N2", Time(6, 18), Stop("Пушкин", Time(6, 45))))
        assertTrue(ttt.addTrain("N3", Time(6, 42), Stop("Пушкин", Time(7, 10))))
        assertTrue(ttt.addStop("N1", Stop("Купчино", Time(6, 52))))
        assertTrue(ttt.addStop("N2", Stop("Купчино", Time(6, 32))))
        assertTrue(ttt.addStop("N3", Stop("Купчино", Time(6, 49))))
        assertEquals(
            listOf(
                Train("N3", Stop("СПб", Time(6, 42)), Stop("Купчино", Time(6, 49)), Stop("Пушкин", Time(7, 10))),
                Train("N1", Stop("СПб", Time(6, 35)), Stop("Купчино", Time(6, 52)), Stop("Пушкин", Time(7, 6)))
            ), ttt.trains(Time(6, 30), "Купчино")
        )
    }

    @Test
    @Tag("Hard")
    fun testEquals() {
        val ttt1 = TrainTimeTable("СПб")
        assertTrue(ttt1.addTrain("N1", Time(6, 35), Stop("Пушкин", Time(7, 4))))
        assertTrue(ttt1.addTrain("N2", Time(6, 18), Stop("Пушкин", Time(6, 45))))
        assertTrue(ttt1.addStop("N2", Stop("Купчино", Time(6, 31))))
        assertTrue(ttt1.addStop("N2", Stop("Шушары", Time(6, 35))))
        val ttt2 = TrainTimeTable("СПб")
        assertTrue(ttt2.addTrain("N2", Time(6, 18), Stop("Пушкин", Time(6, 45))))
        assertTrue(ttt2.addTrain("N1", Time(6, 35), Stop("Пушкин", Time(7, 4))))
        assertTrue(ttt2.addStop("N2", Stop("Шушары", Time(6, 35))))
        assertTrue(ttt2.addStop("N2", Stop("Купчино", Time(6, 31))))
        assertTrue(ttt1 == ttt2)
    }
}