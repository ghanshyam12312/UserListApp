package ghanshyam.demo.anzusersapp.core.errorhandling

import com.demo.anzuserapp.BuildConfig
import timber.log.Timber

object Logger {
    init {
        var isTestEnvironment = false

        fun enableTestMode() { isTestEnvironment = true }
        if (BuildConfig.ENABLE_LOGS) {
            Timber.plant(Timber.DebugTree())
        }
    }

    @JvmStatic
    fun logError(tag: String?, message: String?) {
        if (BuildConfig.ENABLE_LOGS) message.let {
            Timber.tag(tag ?: "").e(it)
        }
    }
}