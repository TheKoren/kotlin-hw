import analysis.SatelliteCalculator
import network.SatelliteDataFetcher
import parsing.DataParser

fun main() {

    // TODO: Use Datafetcher through http
    val satelliteDataFetcher = SatelliteDataFetcher()
    val catalog = "stations"

    val responseData = satelliteDataFetcher.fetchSatelliteData(catalog)
    if (responseData != null) {
        println(responseData)
    } else {
        println("Failed to fetch satellite data")
    }
    val tleDataFilePath = "data/active.json"
    val satelliteDataList = DataParser.parseTLEFile(tleDataFilePath)

    val noradCatalogNumbers = SatelliteCalculator.getNoradCatalogNumber(satelliteDataList)
    println("NORAD Catalog Numbers of all satellites: $noradCatalogNumbers")

    val topSatellitesByMeanMotion = SatelliteCalculator.getTopSatellitesByMeanMotion(satelliteDataList)
    topSatellitesByMeanMotion.take(5).forEachIndexed { index, satelliteData ->
        println("Top ${index + 1}: NORAD Catalog Number: ${satelliteData.NORAD_CAT_ID}, Mean Motion: ${satelliteData.MEAN_MOTION}")
    }

    val (leoCount, geoCount) = SatelliteCalculator.countLEOandGEO(satelliteDataList)
    println("Number of Satellites in LEO: $leoCount")
    println("Number of Satellites in GEO: $geoCount")



}
