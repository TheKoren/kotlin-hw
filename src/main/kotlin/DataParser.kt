import java.io.File
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DataParser {
    companion object {
        fun parseTLEFile(filePath: String): List<SatelliteData> {
            val jsonString = File(filePath).readText()
            return parseSatelliteDataFromJson(jsonString)
        }

        private fun parseSatelliteDataFromJson(jsonString: String): List<SatelliteData> {
            val gson = Gson()
            val listType = object : TypeToken<List<SatelliteData>>() {}.type
            return gson.fromJson(jsonString, listType)
        }
    }
}