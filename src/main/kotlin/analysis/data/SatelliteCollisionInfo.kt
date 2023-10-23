package analysis.data

import model.SatelliteData

data class SatelliteCollisionInfo(
    val satellite1: SatelliteData,
    val satellite2: SatelliteData,
    val risk: Double
)