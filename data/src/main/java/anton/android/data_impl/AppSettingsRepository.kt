package anton.android.data_impl

import android.content.Context
import anton.android.data_impl.models.Settings
import com.google.gson.GsonBuilder
import javax.inject.Inject

class AppSettingsRepository @Inject constructor(
    private val context: Context
) {

    fun getSettings(path: String): Settings {

        val json = context.assets.let {
            val inputStream = it.open(path)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            String(buffer)
        }

        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

        return gson.fromJson(json, Settings::class.java)
    }
}