import org.junit.Assert.assertEquals
import org.junit.Test

class SatelliteCalculatorTest {

    @Test
    fun testCalculateAltitude() {
        val meanMotion = 16.30
        val expectedAltitude = 200.0
        val altitude = SatelliteCalculator.calculateAltitude(meanMotion)
        assertEquals(expectedAltitude, altitude, 0.5)
    }
}