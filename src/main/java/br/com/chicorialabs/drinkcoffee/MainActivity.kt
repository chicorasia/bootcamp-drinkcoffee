package br.com.chicorialabs.drinkcoffee

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.chicorialabs.drinkcoffee.databinding.ActivityMainBinding
import br.com.chicorialabs.drinkcoffee.viewmodel.DrinkCoffeeViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

        dataStore = createDataStore(name = "coffee_count")

        mViewModel = ViewModelProvider(this).get(DrinkCoffeeViewModel::class.java)

        initQuantity()
        initObserver()


        cupImageview.setOnClickListener {
            lifecycleScope.launch {
                mViewModel.incrementCounter()
            }


        }

        cupImageview.setOnLongClickListener {
            runBlocking {
                mViewModel.resetCounter()
            }
        }
    }


    private fun initObserver() {
        mViewModel.coffeeCounter.observe(this, {
            quantityTxt.text = it.toString()
        })
    }

    private fun initQuantity() {
        lifecycleScope.launch {
            mViewModel.loadCounter()
        }
    }

}