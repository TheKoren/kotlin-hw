package model

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