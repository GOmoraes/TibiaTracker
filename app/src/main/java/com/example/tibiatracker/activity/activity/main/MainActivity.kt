package com.example.tibiatracker.activity.activity.main

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tibiatracker.R
import com.example.tibiatracker.activity.model.AccountResponse
import com.example.tibiatracker.activity.repository.MainRepositoryImpl
import com.example.tibiatracker.activity.repository.TibiaDataRepositoryImpl
import com.example.tibiatracker.activity.service.ApiClient
import com.example.tibiatracker.activity.utils.Serial
import com.example.tibiatracker.activity.view_model.MainViewModel
import com.example.tibiatracker.activity.view_model.TibiaDataViewModel
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class MainActivity : ComponentActivity() {

    private lateinit var tibiaDataViewModel: TibiaDataViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var tvTitulo: TextView
    private var accountCheck = "não"
    private var account : AccountResponse? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var check = intent.extras!!.getSerializable(Serial.account) as String



        setViewModel()
        setObserver()
        if (check == "não") {
            mainViewModel.getAccount("668d8dd4bf4293c1fe1d124c")
        }else{
            getAccount()
        }
        setViews()
    }
    private fun getAccount(){
        try {
            val fis = this.openFileInput("account.tmp")
            val `is` = ObjectInputStream(fis)
            val simpleClass: AccountResponse = `is`.readObject() as AccountResponse
            `is`.close()
            fis.close()

            account = simpleClass
        }catch (e:Exception){
            Log.e(TAG, "getAccount: "+e.localizedMessage, )
        }
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


            val fos = this.openFileOutput("account.tmp", Context.MODE_PRIVATE)
            val os = ObjectOutputStream(fos)
            os.writeObject(account)
            os.close()
            fos.close()
        })

        mainViewModel.actionError.observe(this, Observer { resultado ->
            if (resultado == "Conta não encontrada"){
                Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setViews(){
        tvTitulo = findViewById(R.id.tv_titulo)

        tibiaDataViewModel.getCharPorNome("Trollefar")
        //mainViewModel.getAccount(LoginActivity.auth.currentUser.toString())
        //mainViewModel.getAccount("668d97479cb94a0ad59d17b9")
        var conta : AccountResponse = AccountResponse(
            contaID = 100,
            contaChar = "Royal Pojap",
            contaNome = "Guolmoraes",
            contaEmail = "guolmoraes@hotmail.com",
            __v = null,
            _id = null

        )
        //mainViewModel.postAccount(conta)


    }
}
