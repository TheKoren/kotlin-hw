package analysis.data

/**
 * A data class for storing information about a constellation of satellites.
 * This class can be used to track altitudes and perform calculations related to the constellation.
 */
class ConstellationInfo {
    private val altitudes = mutableListOf<Double>()

    /**
     * Add the altitude of a satellite to the constellation's data.
     *
     * @param altitude The altitude of the satellite.
     */
    fun addSatellite(altitude: Double) {
        altitudes.add(altitude)
    }

    /**
     * Get the number of satellites in the constellation.
     *
     * @return The number of satellites.
     */
    fun getNumberOfSatellites(): Int {
        return altitudes.size
    }

    /**
     * Calculate and get the mean altitude of satellites in the constellation.
     *
     * @return The mean altitude of the satellites.
     */
    fun getMeanAltitude(): Double {
        return altitudes.average()
    }
}