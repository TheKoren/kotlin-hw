package model

/**
 * Data class representing satellite information.
 *
 * This data class holds information about a satellite, including its name, ID, orbital parameters,
 * and other characteristics.
 *
 * @property OBJECT_NAME The name of the satellite.
 * @property OBJECT_ID The unique identifier of the satellite.
 * @property EPOCH The epoch time of the satellite's orbital elements.
 * @property MEAN_MOTION The mean motion of the satellite in orbits per day.
 * @property ECCENTRICITY The eccentricity of the satellite's orbit.
 * @property INCLINATION The inclination of the satellite's orbit in degrees.
 * @property RA_OF_ASC_NODE The right ascension of the ascending node in degrees.
 * @property ARG_OF_PERICENTER The argument of pericenter in degrees.
 * @property MEAN_ANOMALY The mean anomaly in degrees.
 * @property EPHEMERIS_TYPE The ephemeris type.
 * @property CLASSIFICATION_TYPE The classification type of the satellite.
 * @property NORAD_CAT_ID The NORAD Catalog Number.
 * @property ELEMENT_SET_NO The element set number.
 * @property REV_AT_EPOCH The revolution number at epoch.
 * @property BSTAR The ballistic coefficient (BSTAR) for the satellite.
 * @property MEAN_MOTION_DOT The first derivative of mean motion.
 * @property MEAN_MOTION_DDOT The second derivative of mean motion.
 */
data class SatelliteData (
    val OBJECT_NAME: String,
    val OBJECT_ID: String,
    val EPOCH: String,
    val MEAN_MOTION: Double,
    val ECCENTRICITY: Double,
    val INCLINATION: Double,
    val RA_OF_ASC_NODE: Double,
    val ARG_OF_PERICENTER: Double,
    val MEAN_ANOMALY: Double,
    val EPHEMERIS_TYPE: Int,
    val CLASSIFICATION_TYPE: String,
    val NORAD_CAT_ID: Int,
    val ELEMENT_SET_NO: Int,
    val REV_AT_EPOCH: Int,
    val BSTAR: Double,
    val MEAN_MOTION_DOT: Double,
    val MEAN_MOTION_DDOT: Double
)