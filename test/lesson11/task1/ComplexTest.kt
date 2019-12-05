package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ComplexTest {

    private fun assertApproxEquals(expected: Complex, actual: Complex, eps: Double) {
        assertEquals(expected.re, actual.re, eps)
        assertEquals(expected.im, actual.im, eps)
    }

    @Test
    fun plus() {
        assertApproxEquals(Complex("4-2i"), Complex("1+2i") + Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex("55-2i"), Complex("55+2i") + Complex("0-4i"), 1e-10)
        assertApproxEquals(Complex("55+2i"), Complex("55+2i") + Complex("0+0i"), 1e-10)
    }

    @Test
    operator fun unaryMinus() {
        assertApproxEquals(Complex(-2.0, 1.0), -Complex(2.0, -1.0), 1e-10)
        assertApproxEquals(Complex("-1-2i"), -Complex("1+2i"), 1e-10)
        assertApproxEquals(Complex("-55-2i"), -Complex("55+2i"), 1e-10)
        assertApproxEquals(Complex("0+0i"), -Complex("0+0i"), 1e-10)
    }

    @Test
    fun minus() {
        assertApproxEquals(Complex("4-2i"), Complex("1+2i") + Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex("55+2i"), Complex("55-2i") - Complex("0-4i"), 1e-10)
        assertApproxEquals(Complex("55+2i"), Complex("55+2i") - Complex("0+0i"), 1e-10)
    }

    @Test
    fun times() {
        assertApproxEquals(Complex("11+2i"), Complex("1+2i") * Complex("3-4i"), 1e-10)
    }

    @Test
    fun div() {
        assertApproxEquals(Complex("2.6+0.8i"), Complex("11-8i").div(Complex("3-4i")), 1e-10)
    }

    @Test
    fun equals() {
        assertApproxEquals(Complex(1.0, 2.0), Complex("1+2i"), 1e-12)
        assertApproxEquals(Complex(1.0, 0.0), Complex(1.0), 1e-12)
    }
}