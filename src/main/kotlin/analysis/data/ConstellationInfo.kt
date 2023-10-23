package analysis.data

class ConstellationInfo {
    private val altitudes = mutableListOf<Double>()

    fun addSatellite(altitude: Double) {
        altitudes.add(altitude)
    }

    fun getNumberOfSatellites(): Int {
        return altitudes.size
    }

    fun getMeanAltitude(): Double {
        return altitudes.average()
    }
}