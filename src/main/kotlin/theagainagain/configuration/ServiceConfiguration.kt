package theagainagain.configuration

import com.google.inject.Inject

class ServiceConfiguration @Inject constructor(val env: EnvironmentHelper) {
    fun getPort(): Int {
        return env.get("PORT", 5000)
    }
}
