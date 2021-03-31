package br.com.chicorialabs.drinkcoffee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import br.com.chicorialabs.drinkcoffee.databinding.ActivityMainBinding
import br.com.chicorialabs.drinkcoffee.viewmodel.DrinkCoffeeViewModel

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

        mViewModel = ViewModelProvider(this).get(DrinkCoffeeViewModel::class.java)

        initQuantity(mViewModel)
        initObserver()


        cupImageview.setOnClickListener {
            mViewModel.incrementCounter()
        }

        cupImageview.setOnLongClickListener {
            mViewModel.resetCounter()
        }
    }

    private fun initObserver() {
        mViewModel.coffeeCounter.observe(this, {
            quantityTxt.text = it.toString()
        })
    }

    private fun initQuantity(mViewModel: DrinkCoffeeViewModel) {
        quantityTxt.text = mViewModel.coffeeCounter.value.toString()
    }

}