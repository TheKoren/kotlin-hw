package analysis

import analysis.data.ConstellationInfo
import analysis.data.SatelliteInfo
import model.SatelliteData
import java.time.LocalDate
import java.time.Period
import kotlin.math.PI
import kotlin.math.pow

object SatelliteCalculator {
    private const val EARTH_RADIUS_KM = 6371.0
    private const val GRAVITY_CONSTANT = 398600.4418
    private const val DAY_IN_SECONDS = 86400

    private const val LEO_ALTITUDE_THRESHOLD = 1000.0
    private const val GEO_ALTITUDE_THRESHOLD = 35786.0

    private const val OLD_AGE_THRESHOLD = 30

    /**
     * @see <a href="https://www.spaceacademy.net.au/watch/track/leopars.htm">SpaceAcademy</a>
      */
    fun calculateAltitude(meanMotion: Double): Double {
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

    fun analyzeConstellations(satelliteDataList: List<SatelliteData>): Map<String, ConstellationInfo> {
        val constellationMap = mutableMapOf<String, ConstellationInfo>()

        for (satelliteData in satelliteDataList) {
            val altitude = calculateAltitude(satelliteData.MEAN_MOTION)
            val constellationName = satelliteData.OBJECT_NAME.getFirstPart()
            val constellationInfo = constellationMap.getOrPut(constellationName) { ConstellationInfo() }
            constellationInfo.addSatellite(altitude)
        }
        return constellationMap
    }

    fun findLongestRunningSatellites(satelliteDataList: List<SatelliteData>) : List<SatelliteInfo> {
        val currentDate = LocalDate.now()
        val longRunningSatellites = mutableListOf<SatelliteInfo>()

        for (satelliteData in satelliteDataList) {
            val launchDate = satelliteData.OBJECT_ID.getFirstPart()
            val age = currentDate.year - launchDate.toInt()
            if (age >= OLD_AGE_THRESHOLD) {
                longRunningSatellites.add(SatelliteInfo(satelliteData.NORAD_CAT_ID, age))
            }
        }
        return longRunningSatellites
    }

    fun analyzeLaunchYears(satelliteDataList: List<SatelliteData>) : Map<Int, Int> {
        val launchDateMap = mutableMapOf<Int, Int>()

        for (satelliteData in satelliteDataList) {
            val launchDate = satelliteData.OBJECT_ID.getFirstPart().toInt()
            val currentCount = launchDateMap[launchDate]
            if (currentCount != null) {
                launchDateMap[launchDate] = currentCount + 1
            } else {
                launchDateMap[launchDate] = 1
            }
        }
        return launchDateMap
    }
}