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
import androidx.lifecycle.lifecycleScope
import br.com.chicorialabs.drinkcoffee.databinding.ActivityMainBinding
import br.com.chicorialabs.drinkcoffee.utils.PreferencesUtils.Companion.DEFAULT_COFFEE_COUNT_VALUE
import br.com.chicorialabs.drinkcoffee.utils.PreferencesUtils.Companion.KEY_COFFEE_COUNT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "coffee_counter")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        initQuantity()

        cupImageview.setOnClickListener {
            lifecycleScope.launch {
                var counter = read(KEY_COFFEE_COUNT).first() ?: 0
                counter++
                save(KEY_COFFEE_COUNT, counter)
            }
        }

        cupImageview.setOnLongClickListener {
            lifecycleScope.launch {
                reset(KEY_COFFEE_COUNT, DEFAULT_COFFEE_COUNT_VALUE)
            }
            true
        }
    }


    private fun initQuantity() {
        lifecycleScope.launch {
            read(KEY_COFFEE_COUNT).collect { count ->
                quantityTxt.text = count.toString()
            }
        }
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

    suspend fun reset(key: String, value: Int) {
        save(key, value)
    }

}