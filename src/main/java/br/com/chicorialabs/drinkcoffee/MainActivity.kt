package br.com.chicorialabs.drinkcoffee

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.chicorialabs.drinkcoffee.databinding.ActivityMainBinding
import br.com.chicorialabs.drinkcoffee.utils.PreferencesUtils
import br.com.chicorialabs.drinkcoffee.utils.PreferencesUtils.Companion.KEY_COFFEE_COUNT
import br.com.chicorialabs.drinkcoffee.viewmodel.DrinkCoffeeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//    TODO 002: criar um objeto DataStore como atributo da MainActivity*

lateinit var dataStore: DataStore<Preferences>

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: DrinkCoffeeViewModel

    private val cupImageview: ImageView by lazy {
        binding.mainCupImageview
    }

    private val quantityTxt: TextView by lazy {
        binding.mainQuantityTxt
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//    TODO 003: inicializar o dataStore, com o nome desejado

        dataStore = createDataStore(name = "coffee_counter")

        mViewModel = ViewModelProvider(this).get(DrinkCoffeeViewModel::class.java)

        initQuantity()
        initObserver()

//    TODO 006: chamar o métodos save() no OnClickListener()
        cupImageview.setOnClickListener {
//            PreferencesUtils(this).saveCoffeeCount(mViewModel.coffeeCounter.value)
            mViewModel.incrementCounter()
            lifecycleScope.launch {
                val counter = mViewModel.coffeeCounter.value
                save(KEY_COFFEE_COUNT, counter ?: 0)
            }
        }

//    TODO 007: chamar o método save() no OnLongClickListener(), passando 0 como valor
        cupImageview.setOnLongClickListener {
//            PreferencesUtils(this).saveCoffeeCount(0)
            lifecycleScope.launch {
                save(KEY_COFFEE_COUNT, 0)
            }
            mViewModel.resetCounter()
        }
    }

    private fun initObserver() {
        mViewModel.coffeeCounter.observe(this, {
            quantityTxt.text = it.toString()
        })
    }

//    TODO 008: chamar o método read() no initQuantity() para iniciar o contador com o valor gravado
    private fun initQuantity() {
//        mViewModel.setCoffeCounterTo(PreferencesUtils(this).loadCoffeeCount())
        lifecycleScope.launch {
            mViewModel.setCoffeCounterTo(read(KEY_COFFEE_COUNT) ?: 0)
        }
        quantityTxt.text = mViewModel.coffeeCounter.value.toString()

    }

//    TODO 004: criar uma função para gravar uma preferência, recebe uma chave e um valor

    suspend fun save(key: String, value: Int) {
        val dataStoreKey = preferencesKey<Int>(key)
        dataStore.edit { coffeeCounter ->
            coffeeCounter[dataStoreKey] = value
        }
    }

//    TODO 005: criar uma função para ler uma preferência, recebe somente uma chave
    suspend fun read(key: String) : Int? {
        val dataStoreKey = preferencesKey<Int>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }



}