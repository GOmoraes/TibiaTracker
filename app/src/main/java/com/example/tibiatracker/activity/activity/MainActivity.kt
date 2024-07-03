package com.example.tibiatracker.activity.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tibiatracker.R
import com.example.tibiatracker.activity.repository.MainRepositoryImpl
import com.example.tibiatracker.activity.service.ApiClient
import com.example.tibiatracker.activity.view_model.MainViewModel
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var tvTitulo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViewModel()
        setObserver()
        setViews()
    }

    private fun setViewModel(){
        val mainService = ApiClient.tibiaData(this)
        viewModel = ViewModelProvider(
            this, MainViewModel.MainViewModelFactory(
                MainRepositoryImpl(mainService)
            )
        ).get(MainViewModel::class.java)

    }

    private fun setObserver(){

        viewModel.CharPorNomeResponse.observe(this, Observer { resultado ->

            tvTitulo.text = resultado.character.character.level.toString()

        })
    }

    private fun setViews(){
        tvTitulo = findViewById(R.id.tv_titulo)

        viewModel.getCharPorNome("Trollefar")
    }
}
