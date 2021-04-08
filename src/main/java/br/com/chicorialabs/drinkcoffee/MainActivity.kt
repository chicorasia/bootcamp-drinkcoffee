package br.com.chicorialabs.drinkcoffee

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.chicorialabs.drinkcoffee.databinding.ActivityMainBinding
import br.com.chicorialabs.drinkcoffee.utils.PreferencesUtils.Companion.KEY_COFFEE_COUNT
import br.com.chicorialabs.drinkcoffee.viewmodel.DrinkCoffeeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "coffee_counter")

class MainActivity : AppCompatActivity() {

//    TODO 006: eliminar referências ao ViewModel
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


        mViewModel = ViewModelProvider(this).get(DrinkCoffeeViewModel::class.java)

        initQuantity()
        initObserver()

//        TODO 002: modificar o onCLickListener para gravar diretamente, sem usar o viewModel
        cupImageview.setOnClickListener {
            mViewModel.incrementCounter()
            lifecycleScope.launch {
                val counter = mViewModel.coffeeCounter.value
                save(KEY_COFFEE_COUNT, counter ?: 0)
            }
        }

//        TODO 004: Modificar o OnLongClickListener para gravar o valor Default nas preferências usando o reset();
        cupImageview.setOnLongClickListener {
            lifecycleScope.launch {
                save(KEY_COFFEE_COUNT, 0)
            }
            mViewModel.resetCounter()
        }
    }

//    TODO 005: eliminar o método initObserver(), que vai se tornar redundante
    private fun initObserver() {
        mViewModel.coffeeCounter.observe(this, {
            quantityTxt.text = it.toString()
        })
    }

//    TODO 001: Modificar o método initQuantity() para coletar o Flow
    private fun initQuantity() {
        lifecycleScope.launch {
            mViewModel.setCoffeCounterTo(read(KEY_COFFEE_COUNT).first() ?: 0)
        }
        quantityTxt.text = mViewModel.coffeeCounter.value.toString()
    }

    suspend fun save(key: String, value: Int) {
        val prefKey = intPreferencesKey(key)
        dataStore.edit { coffeCount ->
            coffeCount[prefKey] = value
        }
    }

    suspend fun read(key: String) : Flow<Int?> {
        val prefKey = intPreferencesKey(key)
        return dataStore.data.map { coffeeCount ->
            coffeeCount[prefKey]
        }

    }

//    TODO 003: Criar um método reset( ) : Boolean para gravar 0 nas preferências


}