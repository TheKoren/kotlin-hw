package analysis

import model.SatelliteData
import kotlin.math.PI
import kotlin.math.pow

/**
 * Extension functions for various data types.
 */
// This extension function is used to extract the first part of a string by splitting it based on non-alphanumeric characters.
fun String.getFirstPart(): String {
    val regex = Regex("[^a-zA-Z0-9]+")
    val parts = this.split(regex)
    return if (parts.isNotEmpty()) parts[0] else this
}

/**
 * Calculate the semi major axis of a satellite based on its mean motion.
 *
 * @return The semi major axis of the satellite in kilometers.
 */
private fun SatelliteData.calculateSemiMajorAxis(): Double {
    val eccentricity = this.ECCENTRICITY
    val meanMotion = this.MEAN_MOTION / Constants.DAY_IN_SECONDS
    return (Constants.GRAVITY_CONSTANT / (meanMotion * 2 * PI).pow(2)).pow(1 / 3.0) / (1 - eccentricity * eccentricity)
}

/**
 * Calculate the apogee altitude of a satellite.
 *
 * @return The apogee altitude of the satellite in kilometers.
 */
fun SatelliteData.calculateApogeeAltitude(): Double {
    val semiMajorAxis = this.calculateSemiMajorAxis()
    val eccentricity = this.ECCENTRICITY
    return (1 + eccentricity) * semiMajorAxis - Constants.EARTH_RADIUS_KM
}

/**
 * Calculate the perigee altitude of a satellite.
 *
 * @return The perigee altitude of the satellite in kilometers.
 */
fun SatelliteData.calculatePerigeeAltitude(): Double {
    val semiMajorAxis = this.calculateSemiMajorAxis()
    val eccentricity = this.ECCENTRICITY
    return (1 - eccentricity) * semiMajorAxis - Constants.EARTH_RADIUS_KM
}
