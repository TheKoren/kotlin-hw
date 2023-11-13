package network

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

/**
 * A utility class for fetching satellite data from an online API.
 */
class SatelliteDataFetcher {
    private val baseUrl = "https://celestrak.org/NORAD/elements/gp.php"
    private val client = OkHttpClient()

    /**
     * Fetch satellite data from the API.
     *
     * @param catalog The catalog name or identifier for the group of satellites to fetch.
     * @return A JSON string containing the satellite data if the request is successful, or null if there was an error.
     */
    fun fetchSatelliteData(catalog: String): String? {
        val apiUrl = "$baseUrl?GROUP=$catalog&FORMAT=json"

        return try {
            val request = Request.Builder()
                .url(apiUrl)
                .get()
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}