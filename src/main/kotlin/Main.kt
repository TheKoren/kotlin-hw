import analysis.SatelliteCalculator
import model.SatelliteData
import network.SatelliteDataFetcher
import parsing.DataParser
import java.util.*

fun main() {

    val scanner = Scanner(System.`in`)
    println("Available Datasets")
    println("1. Active satellites")
    println("2. Last 30 Days' Launches")
    println("3. SpaceStations")

    print("Enter the number of the dataset on which you want to perform analysis: ")
    val catalog : String = when (scanner.nextInt()) {
        1 -> {
            "stations"
        }
        2 -> {
            "last-30-days"
        }
        3 -> {
            "active"
        }
        else -> {
            println("Invalid choice.")
            return
        }
    }
    val satelliteDataFetcher = SatelliteDataFetcher()
    val responseData = satelliteDataFetcher.fetchSatelliteData(catalog)
    val satelliteDataList : List<SatelliteData>
    if (responseData != null) {
        satelliteDataList = responseData.let { DataParser.parseJsonFile(it) }
    } else {
        println("Failed to fetch satellite data")
        return
    }

    println("Available Calculations:")
    println("1. Top 5 Satellites by Mean Motion")
    println("2. Count of LEO and GEO Satellites")
    println("3. Analyze Constellations")
    println("4. Find Longest-Running Satellites")

    print("Enter the number of the calculation you want to perform: ")
    when (scanner.nextInt()) {
        1 -> {
            val topSatellitesByMeanMotion = SatelliteCalculator.getTopSatellitesByMeanMotion(satelliteDataList)
            topSatellitesByMeanMotion.take(5).forEachIndexed { index, satelliteData ->
                println("Top ${index + 1}: NORAD Catalog Number: ${satelliteData.NORAD_CAT_ID}, Mean Motion: ${satelliteData.MEAN_MOTION}")
            }
        }
        2 -> {
            val (leoCount, geoCount) = SatelliteCalculator.countLEOandGEO(satelliteDataList)
            println("Number of Satellites in LEO: $leoCount")
            println("Number of Satellites in GEO: $geoCount")
        }
        3 -> {
            val constellations = SatelliteCalculator.analyzeConstellations(satelliteDataList)
            constellations.forEach { constellationInfo ->
                println("Constellation: ${constellationInfo.key}, Number of Satellites: ${constellationInfo.value.getNumberOfSatellites()}, Mean Altitude: ${constellationInfo.value.getMeanAltitude()}")
            }
        }
        4 -> {
            val longestRunningSatellites = SatelliteCalculator.findLongestRunningSatellites(satelliteDataList)
            longestRunningSatellites.forEachIndexed { index, satelliteInfo ->
                println("Top ${index + 1}: NORAD Catalog Number: ${satelliteInfo.noradCatalogNumber}, Age in Years: ${satelliteInfo.ageInYears}")
            }
        }
        else -> {
            println("Invalid choice.")
            return
        }
    }

}
