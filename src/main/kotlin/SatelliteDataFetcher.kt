import okhttp3.OkHttpClient
import okhttp3.Request

class SatelliteDataFetcher {
    private val baseUrl = "https://celestrak.org/NORAD/elements/gp.php"
    private val client = OkHttpClient()

    fun fetchSatelliteData(catalog: String): String? {
        val apiUrl = "$baseUrl?GROUP=$catalog&FORMAT=json"

        val request = Request.Builder()
            .url(apiUrl)
            .get()
            .build()
        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                return response.body?.string()
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}