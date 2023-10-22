import kotlin.math.PI
import kotlin.math.pow

object SatelliteCalculator {
    private const val EARTH_RADIUS_KM = 6371.0
    private const val GRAVITY_CONSTANT = 398600.4418
    private const val DAY_IN_SECONDS = 86400

    private const val LEO_ALTITUDE_THRESHOLD = 1000.0
    private const val GEO_ALTITUDE_THRESHOLD = 35786.0

    /**
     * @see <a href="https://www.spaceacademy.net.au/watch/track/leopars.htm">SpaceAcademy</a>
      */
    private fun calculateAltitude(meanMotion: Double): Double {
        val period = DAY_IN_SECONDS / meanMotion
        return Math.cbrt((period.pow(2)* GRAVITY_CONSTANT) / (2 * PI).pow(2)) - EARTH_RADIUS_KM
    }

    fun countLEOandGEO(satelliteDataList: List<SatelliteData>): Pair<Int, Int> {
        var leoCount = 0
        var geoCount = 0

        for (satelliteData in satelliteDataList) {
            val meanMotion = satelliteData.MEAN_MOTION
            val altitude = calculateAltitude(meanMotion)

            if (altitude < LEO_ALTITUDE_THRESHOLD) {
                leoCount++
            } else if (altitude > GEO_ALTITUDE_THRESHOLD) {
                geoCount++
            }
        }

        return Pair(leoCount, geoCount)
    }

    fun getNoradCatalogNumber(satelliteDataList: List<SatelliteData>): List<Int> {
        return satelliteDataList.map {it.NORAD_CAT_ID}
    }

    fun getTopSatellitesByMeanMotion(satelliteDataList: List<SatelliteData>): List<SatelliteData> {
        return satelliteDataList.sortedByDescending { it.MEAN_MOTION }
    }
}