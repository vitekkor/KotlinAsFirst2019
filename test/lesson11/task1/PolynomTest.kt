package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

class PolynomTest {

    private fun assertApproxEquals(expected: Polynom, actual: Polynom, eps: Double) {
        assertEquals(expected.degree(), actual.degree())
        for (i in 0..expected.degree()) {
            assertEquals(expected.coeff(i), actual.coeff(i), eps)
        }
    }

    @Test
    @Tag("Easy")
    fun getValue() {
        val p = Polynom(1.0, 3.0, 2.0)
        assertEquals(42.0, p.getValue(5.0), 1e-10)
    }

    @Test
    @Tag("Easy")
    fun coeff() {
        val p = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -3.0, -4.0, 2.0, 0.0, 255.0)
        assertEquals(1.0, p.coeff(2), 1e-10)
        assertEquals(3.0, p.coeff(1), 1e-10)
        assertEquals(2.0, p.coeff(0), 1e-10)
        assertEquals(1.0, r.coeff(5), 1e-10)
        assertEquals(-3.0, r.coeff(4), 1e-10)
        assertEquals(-4.0, r.coeff(3), 1e-10)
        assertEquals(2.0, r.coeff(2), 1e-10)
        assertEquals(0.0, r.coeff(1), 1e-10)
        assertEquals(255.0, r.coeff(0), 1e-10)
    }

    @Test
    @Tag("Easy")
    fun degree() {
        val p = Polynom(1.0, 1.0, 1.0)
        assertEquals(2, p.degree())
        val q = Polynom(0.0)
        assertEquals(0, q.degree())
        val r = Polynom(0.0, 1.0, 2.0)
        assertEquals(1, r.degree())
        val p1 = Polynom(0.0, 0.0, 0.0)
        assertEquals(0, p1.degree())
    }

    @Test
    @Tag("Easy")
    fun plus() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -1.0, 2.0, 6.0)
        assertApproxEquals(r, p1 + p2, 1e-10)
        assertApproxEquals(r, p2 + p1, 1e-10)
        assertApproxEquals(Polynom(3.0, 2.0), Polynom("-x^2+0x+0") + Polynom("x^2+3x+2"), 1e-10)
    }

    @Test
    @Tag("Easy")
    operator fun unaryMinus() {
        val p = Polynom(1.0, -1.0, 2.0)
        val r = Polynom(-1.0, 1.0, -2.0)
        assertApproxEquals(r, -p, 1e-11)
    }

    @Test
    @Tag("Easy")
    fun minus() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -3.0, -4.0, 2.0)
        assertApproxEquals(r, p1 - p2, 1e-10)
        assertApproxEquals(-r, p2 - p1, 1e-10)
    }

    @Test
    @Tag("Normal")
    fun times() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, 1.0, -5.0, -3.0, 10.0, 8.0)
        val p11 = Polynom(1.0, 0.0, 1.0, 0.0, 1.0)
        val p22 = Polynom(1.0, 2.0)
        val r2 = Polynom(1.0, 2.0, 1.0, 2.0, 1.0, 2.0)
        assertApproxEquals(r, p1 * p2, 1e-10)
        assertApproxEquals(r, p2 * p1, 1e-10)
        assertApproxEquals(r2, p11 * p22, 1e-10)
    }

    @Test
    @Tag("Hard")
    fun div() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -5.0)
        val p11 = Polynom(1.0, 0.0, 1.0, 0.0, 1.0)
        val p22 = Polynom(1.0, 2.0)
        val r2 = Polynom(1.0, 2.0, 1.0, 2.0, 1.0, 2.0)
        assertApproxEquals(p11, r2 / p22, 1e-10)
        assertApproxEquals(p22, r2 / p11, 1e-10)
        assertApproxEquals(r, p1 / p2, 1e-10)
        assertApproxEquals(Polynom(1.0, 2.0), Polynom(2.0, 4.0) / Polynom(0.0, 2.0), 1e-10)
        assertApproxEquals(Polynom(2.0), Polynom(2.0, 0.0, 0.0, 3.0) / Polynom(1.0, 0.0, 1.0, 0.0), 1e-10)
        assertApproxEquals(Polynom(-1.0), Polynom("-x^2+0x+0") / Polynom("x^2+3x+2"), 1e-10)
    }

    @Test
    @Tag("Hard")
    fun rem() {
        val p1 = Polynom(1.0, -2.0, -1.0, 4.0)
        val p2 = Polynom(1.0, 3.0, 2.0)
        val r = Polynom(1.0, -5.0)
        val q = Polynom(12.0, 14.0)
        assertApproxEquals(q, p1 % p2, 1e-10)
        assertApproxEquals(Polynom(-2.0, 3.0), Polynom(2.0, 0.0, 0.0, 3.0) % Polynom(1.0, 0.0, 1.0, 0.0), 1e-10)
        assertApproxEquals(p1, p2 * r + q, 1e-10)
    }

    @Test
    @Tag("Easy")
    fun equals() {
        assertEquals(Polynom(1.0, 2.0, 3.0), Polynom(1.0, 2.0, 3.0))
        assertEquals(Polynom(0.0, 2.0, 3.0), Polynom(2.0, 3.0))
        assertEquals(Polynom(0.0, 0.0, 0.0), Polynom(0.0))
    }

    @Test
    @Tag("Normal")
    fun hashCodeTest() {
        assertEquals(Polynom(1.0, 2.0, 3.0).hashCode(), Polynom(1.0, 2.0, 3.0).hashCode())
    }
}