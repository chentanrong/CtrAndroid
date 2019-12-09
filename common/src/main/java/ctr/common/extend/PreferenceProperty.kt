package ctr.common.extend

import android.content.SharedPreferences
import android.preference.Preference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Sunjie on 2018/1/23.
 */

open class PreferenceProperty<T>(val sharedPreferences: SharedPreferences, val key: String, val defaultValue: T) : ReadWriteProperty<Any, T> {
    @Suppress("UNCHECKED_CAST")
    public override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return findPreference(sharedPreferences, key, defaultValue)
    }

    public override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        putPreference(sharedPreferences, key, value)
    }

    private fun getKey(preference: Preference, thisRef: Any, property: KProperty<*>): String {
        var key = preference.key
        if (key.isEmpty()) {
            key = thisRef::class.qualifiedName + "_" + property.name
        }
        return key
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        private fun <U> findPreference(sharedPreferences: SharedPreferences, key: String, default: U): U {
            val res: Any? = when (default) {
                is String -> sharedPreferences.getString(key, default)
                is Boolean -> sharedPreferences.getBoolean(key, default)
                is Int -> sharedPreferences.getInt(key, default)
                is Float -> sharedPreferences.getFloat(key, default)
                is Long -> sharedPreferences.getLong(key, default)
                else -> null
            }
            return res as U
        }

        private fun <U> putPreference(sharedPreferences: SharedPreferences, key: String, value: U) {
            val edit = sharedPreferences.edit()
            when (value) {
                is String -> edit.putString(key, value)
                is Boolean -> edit.putBoolean(key, value)
                is Int -> edit.putInt(key, value)
                is Long -> edit.putLong(key, value)
                is Float -> edit.putFloat(key, value)
                else -> edit.remove(key)
            }
            edit.apply()
        }
    }
}