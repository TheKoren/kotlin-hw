package analysis.data

/**
 * Data class representing information about a satellite, including its NORAD Catalog Number and age in years.
 *
 * @property noradCatalogNumber The unique identifier for the satellite in the NORAD Catalog.
 * @property ageInYears The age of the satellite in years since its launch.
 */
data class SatelliteInfo(
    val noradCatalogNumber: Int,
    val ageInYears: Int
)