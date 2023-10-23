import analysis.SatelliteCalculator
import model.SatelliteData
import network.SatelliteDataFetcher
import parsing.DataParser

fun main() {

    val satelliteDataFetcher = SatelliteDataFetcher()
    val catalog = "stations"

    val responseData = satelliteDataFetcher.fetchSatelliteData(catalog)
    val satelliteDataList : List<SatelliteData>
    if (responseData != null) {
        println(responseData)
        satelliteDataList = responseData.let { DataParser.parseJsonile(it) }
    } else {
        println("Failed to fetch satellite data")
        return
    }

    val topSatellitesByMeanMotion = SatelliteCalculator.getTopSatellitesByMeanMotion(satelliteDataList)
    topSatellitesByMeanMotion.take(5).forEachIndexed { index, satelliteData ->
        println("Top ${index + 1}: NORAD Catalog Number: ${satelliteData.NORAD_CAT_ID}, Mean Motion: ${satelliteData.MEAN_MOTION}")
    }

    val (leoCount, geoCount) = SatelliteCalculator.countLEOandGEO(satelliteDataList)
    println("Number of Satellites in LEO: $leoCount")
    println("Number of Satellites in GEO: $geoCount")

    val constellations = SatelliteCalculator.analyzeConstellations(satelliteDataList)
    constellations.forEach { constellationInfo ->
        println("Constellation: ${constellationInfo.key}, Number of Satellites: ${constellationInfo.value.getNumberOfSatellites()}, Mean Altitude: ${constellationInfo.value.getMeanAltitude()}")
    }

    val longestRunningSatellites = SatelliteCalculator.findLongestRunningSatellites(satelliteDataList)
    longestRunningSatellites.forEachIndexed { index, satelliteInfo ->
        println("Top ${index+1}: NORAD Catalog Number: ${satelliteInfo.noradCatalogNumber}, Age in Years: ${satelliteInfo.ageInYears}")
    }

}
