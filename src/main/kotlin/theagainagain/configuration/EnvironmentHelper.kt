package theagainagain.configuration

import com.google.inject.Inject
import org.apache.commons.lang.StringUtils

class EnvironmentHelper @Inject constructor(val env: EnvironmentProvider) {
    fun get(key: String, default: Int): Int {
        val value = env.get(key)
        return if(StringUtils.isEmpty(value)) {
            default
        }else {
            value.toInt()
        }
    }

    fun get(key: String, default: Long): Long {
        val value = env.get(key)
        return if(StringUtils.isEmpty(value)) {
            default
        }else {
            value.toLong()
        }
    }

    fun get(key: String, default: Boolean): Boolean {
        val value = env.get(key)
        return if(StringUtils.isEmpty(value)) {
            default
        }else {
            value.toBoolean()
        }
    }

    fun getKeys(): String {
        val stringBuilder: StringBuilder = StringBuilder("[")
        env.getKeys().forEach{
            stringBuilder
                    .append(it)
                    .append(",")
        }
        return stringBuilder
                .deleteCharAt(stringBuilder.lastIndex)
                .append("]")
                .toString()
    }
}
