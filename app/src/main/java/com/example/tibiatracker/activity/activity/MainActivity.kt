package com.example.tibiatracker.activity.activity

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tibiatracker.R
import com.example.tibiatracker.activity.repository.MainRepositoryImpl
import com.example.tibiatracker.activity.repository.TibiaDataRepositoryImpl
import com.example.tibiatracker.activity.service.ApiClient
import com.example.tibiatracker.activity.view_model.MainViewModel
import com.example.tibiatracker.activity.view_model.TibiaDataViewModel

class MainActivity : ComponentActivity() {

    private lateinit var tibiaDataViewModel: TibiaDataViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var tvTitulo: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setViewModel()
        setObserver()
        setViews()
    }

    private fun setViewModel(){
        val tibiaDataService = ApiClient.tibiaData(this)
        tibiaDataViewModel = ViewModelProvider(
            this, TibiaDataViewModel.MainViewModelFactory(
                TibiaDataRepositoryImpl(tibiaDataService)
            )
        ).get(TibiaDataViewModel::class.java)

        val mainService = ApiClient.tibiaTracker(this)
        mainViewModel = ViewModelProvider(
            this, MainViewModel.MainViewModelFactory(
                MainRepositoryImpl(mainService)
            )
        ).get(MainViewModel::class.java)

    }

    private fun setObserver(){

        tibiaDataViewModel.CharPorNomeResponse.observe(this, Observer { resultado ->

            tvTitulo.text = resultado.character.character.level.toString()

        })

        mainViewModel.AccountResponse.observe(this, Observer { resultado ->
            var account = resultado
            Toast.makeText(this, account.contaNome, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setViews(){
        tvTitulo = findViewById(R.id.tv_titulo)

        tibiaDataViewModel.getCharPorNome("Trollefar")
        mainViewModel.getAccount(10)
    }
}
