package parsing

import java.io.File
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import model.SatelliteData

/**
 * A utility class for parsing satellite data from JSON files.
* This class can be used to parse JSON data into a list of SatelliteData objects.
*/
class DataParser {
    companion object {
        /**
         * Parse satellite data from a JSON file. It is not currently in use but is kept for potential future use.
         *
         * @param filePath The path to the JSON file to parse.
         * @return A list of SatelliteData objects parsed from the JSON file.
         */
        @Suppress("unused")
        fun parseTLEFile(filePath: String): List<SatelliteData> {
            val jsonString = File(filePath).readText()
            return parseSatelliteDataFromJson(jsonString)
        }

        /**
         * Parse satellite data from a JSON string.
         *
         * @param jsonString The JSON string to parse.
         * @return A list of SatelliteData objects parsed from the JSON string.
         */
        private fun parseSatelliteDataFromJson(jsonString: String): List<SatelliteData> {
            val gson = Gson()
            val listType = object : TypeToken<List<SatelliteData>>() {}.type
            return gson.fromJson(jsonString, listType)
        }

        /**
         * Parse satellite data from a JSON string.
         *
         * @param jsonString The JSON string to parse.
         * @return A list of SatelliteData objects parsed from the JSON string.
         */
        fun parseJsonFile(jsonString: String): List<SatelliteData> {
            return parseSatelliteDataFromJson(jsonString)
        }
    }
}