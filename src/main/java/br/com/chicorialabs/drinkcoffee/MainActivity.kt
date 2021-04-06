package br.com.chicorialabs.drinkcoffee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import br.com.chicorialabs.drinkcoffee.databinding.ActivityMainBinding
import br.com.chicorialabs.drinkcoffee.utils.PreferencesUtils
import br.com.chicorialabs.drinkcoffee.viewmodel.DrinkCoffeeViewModel


class MainActivity : AppCompatActivity() {


//    TODO 002: criar um objeto DataStore como atributo da MainActivity
//    TODO 003: inicializar o dataStore, com o nome desejado
//    TODO 004: criar uma função para gravar uma preferência, recebe uma chave e um valor
//    TODO 005: criar uma função para ler uma preferência, recebe somente uma chave
//    TODO 006: chamar o métodos save() no OnClickListener()
//    TODO 007: chamar o método save() no OnLongClickListener(), passando 0 como valor
//    TODO 008: chamar o método read() no initQuantity() para iniciar o contador com o valor gravado

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


        cupImageview.setOnClickListener {
            mViewModel.incrementCounter()
            PreferencesUtils(this).saveCoffeeCount(mViewModel.coffeeCounter.value)

        }

        cupImageview.setOnLongClickListener {
            PreferencesUtils(this).saveCoffeeCount(0)
            mViewModel.resetCounter()
        }
    }

    private fun initObserver() {
        mViewModel.coffeeCounter.observe(this, {
            quantityTxt.text = it.toString()
        })
    }

    private fun initQuantity() {
        mViewModel.setCoffeCounterTo(PreferencesUtils(this).loadCoffeeCount())
        quantityTxt.text = mViewModel.coffeeCounter.value.toString()

    }

}