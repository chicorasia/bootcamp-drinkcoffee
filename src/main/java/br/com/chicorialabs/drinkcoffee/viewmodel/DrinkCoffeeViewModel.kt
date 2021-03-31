package br.com.chicorialabs.drinkcoffee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrinkCoffeeViewModel : ViewModel() {

    private val _coffeeCounter = MutableLiveData<Int>()
    val coffeeCounter: LiveData<Int>
        get() = _coffeeCounter

    init {
        _coffeeCounter.value = 0
    }

    fun incrementCounter(){
        _coffeeCounter.value = _coffeeCounter.value?.plus(1)
    }

//    TODO 003: modificar o retorno do método resetCounter

    fun resetCounter() {
        _coffeeCounter.value = 0
    }

}