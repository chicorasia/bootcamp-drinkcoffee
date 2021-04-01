package br.com.chicorialabs.drinkcoffee.utils

import android.content.Context

//TODO 001: criar uma classe PreferencesUtils

class PreferencesUtils(context: Context) {

    val prefs = context.getSharedPreferences(KEY_COFFEE_COUNT,
            Context.MODE_PRIVATE)

    //todo 002: criar um companion object com as constantes
    companion object {
        const val KEY_COFFEE_COUNT = "br.com.chicorialabs.drinkcoffee.COFFEE_COUNTER"
        const val DEFAULT_COUNT = 0

    }

//    TODO 003: criar um método LoadCoffeeCount que retorna um int
    fun loadCoffeeCount(): Int {
        return prefs.getInt(KEY_COFFEE_COUNT, DEFAULT_COUNT)
    }

    //    TODO 004: criar um método saveCoffeeCount para salvar um int nas preferences
    fun saveCoffeeCount(coffeeCounter: Int?) {
        with (prefs.edit()) {
            putInt(
                KEY_COFFEE_COUNT,
                coffeeCounter ?: 0)
            commit()
        }
    }



}