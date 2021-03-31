package br.com.chicorialabs.drinkcoffee

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import br.com.chicorialabs.drinkcoffee.databinding.ActivityMainBinding
import br.com.chicorialabs.drinkcoffee.viewmodel.DrinkCoffeeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val cupImageview: ImageView by lazy {
        binding.mainCupImageview
    }

    private val quantidadeTxt: TextView by lazy {
        binding.mainQuantityTxt
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mViewModel = ViewModelProvider(this).get(DrinkCoffeeViewModel::class.java)

        quantidadeTxt.text = mViewModel.coffeeCounter.value.toString()


    }
}