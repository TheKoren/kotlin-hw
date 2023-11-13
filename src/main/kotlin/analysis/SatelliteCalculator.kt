package analysis

import analysis.data.ConstellationInfo
import analysis.data.SatelliteInfo
import model.SatelliteData
import java.time.LocalDate
import kotlin.math.PI
import kotlin.math.cbrt
import kotlin.math.pow

/**
 * A utility object for calculating and analyzing satellite-related information.
 */
object SatelliteCalculator {
    private const val EARTH_RADIUS_KM = 6371.0
    private const val GRAVITY_CONSTANT = 398600.4418
    private const val DAY_IN_SECONDS = 86400

    private const val LEO_ALTITUDE_THRESHOLD = 1000.0
    private const val GEO_ALTITUDE_THRESHOLD = 35786.0

    private const val OLD_AGE_THRESHOLD = 30

    /**
     * Calculate the altitude of a satellite based on its mean motion.
     *
     * @param meanMotion The mean motion of the satellite in orbits per day.
     * @see <a href="https://www.spaceacademy.net.au/watch/track/leopars.htm">SpaceAcademy</a>
     * @return The altitude of the satellite in kilometers.
     */
    fun calculateAltitude(meanMotion: Double): Double {
        val period = DAY_IN_SECONDS / meanMotion
        return cbrt((period.pow(2)* GRAVITY_CONSTANT) / (2 * PI).pow(2)) - EARTH_RADIUS_KM
    }

    /**
     * Count the number of LEO (Low Earth Orbit) and GEO (Geostationary Earth Orbit) and MEO (Medium Earth Orbit) satellites in a list.
     *
     * @param satelliteDataList A list of SatelliteData objects.
     * @return A triple of counts, where the first element is the count of LEO satellites, and the second is the count of GEO satellites and the third is MEO satellites.
     */
    fun countLEOandGEOandMEO(satelliteDataList: List<SatelliteData>): Triple<Int, Int, Int> {
        var leoCount = 0
        var geoCount = 0
        var meoCount = 0

        for (satelliteData in satelliteDataList) {
            val meanMotion = satelliteData.MEAN_MOTION
            val altitude = calculateAltitude(meanMotion)

            if (altitude < LEO_ALTITUDE_THRESHOLD) {
                leoCount++
            } else if (altitude > GEO_ALTITUDE_THRESHOLD) {
                geoCount++
            } else {
                meoCount++
            }
        }

        return Triple(leoCount, geoCount, meoCount)
    }

    /**
     * Get a list of NORAD Catalog Numbers from a list of SatelliteData objects.
     *
     * @param satelliteDataList A list of SatelliteData objects.
     * @return A list of NORAD Catalog Numbers.
     */
    fun getNoradCatalogNumber(satelliteDataList: List<SatelliteData>): List<Int> {
        return satelliteDataList.map {it.NORAD_CAT_ID}
    }

    /**
     * Get a list of SatelliteData objects sorted by mean motion in descending order.
     *
     * @param satelliteDataList A list of SatelliteData objects.
     * @return A sorted list of SatelliteData objects.
     */
    fun getTopSatellitesByMeanMotion(satelliteDataList: List<SatelliteData>): List<SatelliteData> {
        return satelliteDataList.sortedByDescending { it.MEAN_MOTION }
    }

    /**
     * Analyze constellations and calculate satellite counts for each constellation.
     *
     * @param satelliteDataList A list of SatelliteData objects.
     * @return A map where keys are constellation names and values are ConstellationInfo objects.
     */
    fun analyzeConstellations(satelliteDataList: List<SatelliteData>): Map<String, ConstellationInfo> {
        return satelliteDataList.groupBy { it.OBJECT_NAME.getFirstPart() }
            .mapValues { ConstellationInfo().apply { it.value.forEach { addSatellite(calculateAltitude(it.MEAN_MOTION)) } } }
    }

    /**
     * Find and list the longest-running satellites based on their launch year.
     *
     * @param satelliteDataList A list of SatelliteData objects.
     * @return A list of SatelliteInfo objects representing long-running satellites.
     */
    fun findLongestRunningSatellites(satelliteDataList: List<SatelliteData>) : List<SatelliteInfo> {
        val currentDate = LocalDate.now()
        return satelliteDataList.filter {
            val launchDate = it.OBJECT_ID.getFirstPart().toInt()
            currentDate.year - launchDate >= OLD_AGE_THRESHOLD
        }.map { SatelliteInfo(it.NORAD_CAT_ID, currentDate.year - it.OBJECT_ID.getFirstPart().toInt()) }
            .also { if (it.isEmpty()) println("No old satellites found for this dataset.") }
    }

    /**
     * Analyze launch years and count the number of satellites launched in each year.
     *
     * @param satelliteDataList A list of SatelliteData objects.
     * @return A map where keys are launch years and values are the counts of satellites launched in each year.
     */
    fun analyzeLaunchYears(satelliteDataList: List<SatelliteData>) : Map<Int, Int> {
        return satelliteDataList.groupingBy { it.OBJECT_ID.getFirstPart().toInt() }
            .eachCount()
    }
}