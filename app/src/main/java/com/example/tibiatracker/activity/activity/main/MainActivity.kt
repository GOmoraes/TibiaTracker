package com.example.tibiatracker.activity.activity.main

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tibiatracker.R
import com.example.tibiatracker.activity.activity.login.LoginActivity
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
    private var account : AccountResponse? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var check = "não"
        try {
            check = intent.extras!!.getSerializable(Serial.account) as String
        }catch (e:Exception){
            Log.e(TAG, "onCreate: "+e.localizedMessage, )
        }



        setViewModel()
        setObserver()
        if (check == "não") {
            var email = LoginActivity.auth.currentUser!!.email.toString()
            mainViewModel.getAccountByEmail(email)
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

            var rlRegisterNomer = findViewById<RelativeLayout>(R.id.rl_register_nome)
            rlRegisterNomer.visibility = View.GONE
        })

        mainViewModel.actionError.observe(this, Observer { resultado ->
            if (resultado == "Conta não encontrada"){
                Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show()
                registrarNome(LoginActivity.auth.currentUser!!.email.toString())
                var rlRegisterNomer = findViewById<RelativeLayout>(R.id.rl_register_nome)
                rlRegisterNomer.visibility = View.VISIBLE
            }else if (resultado == "Conta não criada"){
                Toast.makeText(this, resultado, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun registrarNome(email : String){
        var btRegistarNome = findViewById<Button>(R.id.bt_criar_nome)
        var nome : EditText = findViewById(R.id.ti_usuario_nome)
        
        btRegistarNome.setOnClickListener{
            if (!nome.text.toString().isNullOrEmpty()){
            
                var conta : AccountResponse = AccountResponse(
                    contaID = (0..1000000000).random(),
                    contaChar = null,
                    contaNome = nome.text.toString(),
                    contaEmail = LoginActivity.auth.currentUser!!.email.toString(),
                    __v = null,
                    contaDescricao = null,
                    _id = null
    
                )

                mainViewModel.postAccount(conta)
            }else{
                Toast.makeText(this, "É necessário preencher Nome de Usuário", Toast.LENGTH_SHORT).show()
            }
        }
        
        
        
        

    }

    private fun setViews(){
        tvTitulo = findViewById(R.id.tv_titulo)

        //tibiaDataViewModel.getCharPorNome("Royal Pojap")
        //mainViewModel.getAccount(LoginActivity.auth.currentUser.toString())
        //mainViewModel.getAccount("668d97479cb94a0ad59d17b9")

        //mainViewModel.postAccount(conta)


    }
}
