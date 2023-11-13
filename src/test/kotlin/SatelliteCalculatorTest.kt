import analysis.SatelliteCalculator
import analysis.data.ConstellationInfo
import analysis.data.SatelliteInfo
import model.SatelliteData
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

/**
 * Unit tests for the [SatelliteCalculator] class.
 */
class SatelliteCalculatorTest {

    @Test
    fun testCalculateAltitude() {
        val meanMotion = 16.30
        val expectedAltitude = 200.0
        val altitude = SatelliteCalculator.calculateAltitude(meanMotion)
        assertEquals(expectedAltitude, altitude, 0.5)
    }

    @Test
    fun testCountLEOandGEO() {
        val satelliteDataList = listOf(
            SatelliteData(MEAN_MOTION = 15.0),
            SatelliteData(MEAN_MOTION = 16.0),
            SatelliteData(MEAN_MOTION = 10.0),
            SatelliteData(MEAN_MOTION = 1.0),
            SatelliteData(MEAN_MOTION = 14.0)
        )

        val expectedLEOCount = 3
        val expectedGEOCount = 1
        val expectedMEOCount = 1

        val (leoCount, geoCount, meoCount) = SatelliteCalculator.countLEOandGEOandMEO(satelliteDataList)
        assertEquals(expectedLEOCount, leoCount)
        assertEquals(expectedGEOCount, geoCount)
        assertEquals(expectedMEOCount, meoCount)
    }

    @Test
    fun testGetNoradCatalogNumber() {
        val satelliteDataList = listOf(
            SatelliteData(NORAD_CAT_ID = 12345),
            SatelliteData(NORAD_CAT_ID = 67890),
            SatelliteData(NORAD_CAT_ID = 54321)
        )

        val expectedCatalogNumbers = listOf(12345, 67890, 54321)

        val catalogNumbers = SatelliteCalculator.getNoradCatalogNumber(satelliteDataList)
        assertEquals(expectedCatalogNumbers, catalogNumbers)
    }

    @Test
    fun testGetTopSatellitesByMeanMotion() {
        val satelliteDataList = listOf(
            SatelliteData(MEAN_MOTION = 10.0),
            SatelliteData(MEAN_MOTION = 11.0),
            SatelliteData(MEAN_MOTION = 5.0),
            SatelliteData(MEAN_MOTION = 16.0),
            SatelliteData(MEAN_MOTION = 11.0)
        )

        val expectedSortedSatellites = satelliteDataList.sortedByDescending { it.MEAN_MOTION }

        val sortedSatellites = SatelliteCalculator.getTopSatellitesByMeanMotion(satelliteDataList)
        assertEquals(expectedSortedSatellites, sortedSatellites)
    }

    @Test
    fun testAnalyzeConstellations() {
        val satelliteDataList = listOf(
            SatelliteData(OBJECT_NAME = "ConstellationA", MEAN_MOTION = 10.0),
            SatelliteData(OBJECT_NAME = "ConstellationB", MEAN_MOTION = 11.0),
            SatelliteData(OBJECT_NAME = "ConstellationA", MEAN_MOTION = 5.0),
            SatelliteData(OBJECT_NAME = "ConstellationC", MEAN_MOTION = 16.0),
            SatelliteData(OBJECT_NAME = "ConstellationB", MEAN_MOTION = 11.0)
        )

        val expectedConstellationMap = mapOf(
            "ConstellationA" to ConstellationInfo(),
            "ConstellationB" to ConstellationInfo(),
            "ConstellationC" to ConstellationInfo()
        )

        expectedConstellationMap["ConstellationA"]?.addSatellite(SatelliteCalculator.calculateAltitude(10.0))
        expectedConstellationMap["ConstellationB"]?.addSatellite(SatelliteCalculator.calculateAltitude(11.0))
        expectedConstellationMap["ConstellationA"]?.addSatellite(SatelliteCalculator.calculateAltitude(5.0))
        expectedConstellationMap["ConstellationC"]?.addSatellite(SatelliteCalculator.calculateAltitude(16.0))
        expectedConstellationMap["ConstellationB"]?.addSatellite(SatelliteCalculator.calculateAltitude(11.0))

        val constellationMap = SatelliteCalculator.analyzeConstellations(satelliteDataList)
        assertEquals(expectedConstellationMap.size, constellationMap.size)

        for (constellationName in expectedConstellationMap.keys) {
            val expectedInfo = expectedConstellationMap[constellationName]
            val actualInfo = constellationMap[constellationName]

            assertEquals(expectedInfo?.getNumberOfSatellites(), actualInfo?.getNumberOfSatellites())
            assertEquals(expectedInfo?.getMeanAltitude(), actualInfo?.getMeanAltitude())
        }
    }

    @Test
    fun testFindLongestRunningSatellites() {
        val satelliteDataList = listOf(
            SatelliteData(OBJECT_ID = "1960-067A", NORAD_CAT_ID = 12345),
            SatelliteData(OBJECT_ID = "2011-068A", NORAD_CAT_ID = 8788),
            SatelliteData(OBJECT_ID = "2020-069A", NORAD_CAT_ID = 9922)
        )

        val currentDate = LocalDate.now()
        val expectedLongRunningSatellites = listOf(
            SatelliteInfo(noradCatalogNumber = 12345, ageInYears = currentDate.year - 1960),
        )

        val longRunningSatellites = SatelliteCalculator.findLongestRunningSatellites(satelliteDataList)
        assertEquals(expectedLongRunningSatellites, longRunningSatellites)
    }

    @Test
    fun testAnalyzeLaunchYears() {
        val satelliteDataList = listOf(
            SatelliteData(OBJECT_ID = "1960-067A"),
            SatelliteData(OBJECT_ID = "2011-068A"),
            SatelliteData(OBJECT_ID = "2020-069A")
        )

        val expectedLaunchDateMap = mapOf(
            1960 to 1,
            2011 to 1,
            2020 to 1
        )

        val launchDateMap = SatelliteCalculator.analyzeLaunchYears(satelliteDataList)
        assertEquals(expectedLaunchDateMap, launchDateMap)
    }
}
