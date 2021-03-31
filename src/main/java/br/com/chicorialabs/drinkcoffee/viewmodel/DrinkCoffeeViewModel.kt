package br.com.chicorialabs.drinkcoffee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrinkCoffeeViewModel : ViewModel() {

    private val _coffeeCounter = MutableLiveData<Int>(0)
    val coffeeCounter: LiveData<Int>
        get() = _coffeeCounter

    private fun incrementCounter(){
        _coffeeCounter.value?.plus(1)
    }

    private fun resetCounter() {
        _coffeeCounter.value = 0
    }

}