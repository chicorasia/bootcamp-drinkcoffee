package br.com.chicorialabs.drinkcoffee.utils

import android.content.Context

class PreferencesUtils(context: Context) {

//    TODO 001A: comentar esse campo e métodos e ver onde o código quebra *
//    val prefs = context.getSharedPreferences(KEY_COFFEE_COUNT, Context.MODE_PRIVATE)

    companion object{
        const val KEY_COFFEE_COUNT = "br.com.chicorialabs.drinkcoffee.COFFEE_COUNTER"
        const val DEFAULT_VALUE = 0

    }
//
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