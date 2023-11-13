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
    println("3. Space Stations")

    print("Enter the number of the dataset on which you want to perform analysis: ")
    val catalog : String = when (scanner.nextInt()) {
        1 -> {
            "active"
        }
        2 -> {
            "last-30-days"
        }
        3 -> {
            "stations"
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
        println("# of satellite data entries: ${satelliteDataList.size}")
    } else {
        println("Failed to fetch satellite data")
        return
    }

    println("Available Calculations:")
    println("1. Top 5 Satellites by Mean Motion")
    println("2. Count of LEO and GEO Satellites")
    println("3. Analyze Constellations")
    println("4. Find Longest-Running Satellites")
    println("5. Launch date of Satellites")

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
            println("Number of Satellites in LEO: $leoCount. LEO percentage: ${(leoCount * 100 / satelliteDataList.size).toDouble()}%")
            println("Number of Satellites in GEO: $geoCount. GEO percentage: ${(geoCount * 100 / satelliteDataList.size).toDouble()}%")
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
        5 -> {
            val launchDateMap = SatelliteCalculator.analyzeLaunchYears(satelliteDataList)
            launchDateMap.forEach { entry ->
                println("Launch Year: ${entry.key}, Number of satellites: ${entry.value}")
            }
        }
        else -> {
            println("Invalid choice.")
            return
        }
    }

}
