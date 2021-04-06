package br.com.chicorialabs.drinkcoffee.utils

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import br.com.chicorialabs.drinkcoffee.dataStore
import kotlinx.coroutines.flow.first

class PreferencesUtils {


    companion object{
        const val KEY_COFFEE_COUNT = "br.com.chicorialabs.drinkcoffee.COFFEE_COUNTER"
        const val DEFAULT_VALUE = 0

        suspend fun save(key: String, value: Int) {
            val dataStoreKey = preferencesKey<Int>(key)
            dataStore.edit { coffeeCount ->
                coffeeCount[dataStoreKey] = value
            }
        }

        suspend fun read(key: String): Int? {
            val dataStoreKey = preferencesKey<Int>(key)
            val preferences = dataStore.data.first()
            return preferences[dataStoreKey]
        }

    }

//    fun loadCoffeeCount() : Int {
//        return prefs.getInt(KEY_COFFEE_COUNT, DEFAULT_VALUE)
//    }
//
//    fun saveCoffeeCount(coffeCount: Int?) {
//        with (prefs.edit()) {
//            putInt(KEY_COFFEE_COUNT, coffeCount ?: 0)
//            commit()
//        }
//    }




}