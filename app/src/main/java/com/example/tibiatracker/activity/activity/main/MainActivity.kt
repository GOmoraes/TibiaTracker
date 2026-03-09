package com.example.tibiatracker.activity.activity.main

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tibiatracker.R
import com.example.tibiatracker.activity.activity.login.LoginActivity
import com.example.tibiatracker.activity.fragment.CharFragment
import com.example.tibiatracker.activity.fragment.HomeFragment
import com.example.tibiatracker.activity.fragment.PerfilFragment
import com.example.tibiatracker.activity.model.AccountResponse
import com.example.tibiatracker.activity.repository.MainRepositoryImpl
import com.example.tibiatracker.activity.repository.TibiaDataRepositoryImpl
import com.example.tibiatracker.activity.service.ApiClient
import com.example.tibiatracker.activity.utils.Serial
import com.example.tibiatracker.activity.view_model.MainViewModel
import com.example.tibiatracker.activity.view_model.TibiaDataViewModel
import com.google.android.gms.dynamic.SupportFragmentWrapper
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {

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
        mainViewModel.getAccountByEmail(LoginActivity.auth.currentUser!!.email.toString())
        if (check == "não") {
            var email = LoginActivity.auth.currentUser!!.email.toString()
            mainViewModel.getAccountByEmail(email)
        }else{
            getAccount()
        }
        setViews()
        fragmentOpen()
    }

    private fun fragmentOpen(){

        openFragment(HomeFragment())

        findViewById<View>(R.id.btn_home).setOnClickListener {
            openFragment(HomeFragment())
        }

        findViewById<View>(R.id.btn_search).setOnClickListener {
            openFragment(CharFragment())
        }

        findViewById<View>(R.id.btn_profile).setOnClickListener {
            openFragment(PerfilFragment())
        }
    }
    fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
    fun getAccount() :AccountResponse? {
        try {
            val fis = this.openFileInput("account.tmp")
            val `is` = ObjectInputStream(fis)
            val simpleClass: AccountResponse = `is`.readObject() as AccountResponse
            `is`.close()
            fis.close()

            account = simpleClass

            return simpleClass
        }catch (e:Exception){
            Log.e(TAG, "getAccount: "+e.localizedMessage, )
            return null
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
            account = resultado


            val fos = this.openFileOutput("account.tmp", Context.MODE_PRIVATE)
            val os = ObjectOutputStream(fos)
            os.writeObject(account)
            os.close()
            fos.close()
        })

        mainViewModel.actionError.observe(this, Observer { resultado ->
            registrarConta(LoginActivity.auth.currentUser!!.email.toString())
        })
    }

    private fun registrarConta(email : String){
            var conta : AccountResponse = AccountResponse(
                contaID = (0..1000000000).random(),
                contaChar = null,
                contaNome = null,
                contaEmail = email,
                __v = null,
                contaDescricao = null,
                _id = null

            )
            mainViewModel.postAccount(conta)
        }

    private fun setViews(){
//        tvTitulo = findViewById(R.id.tv_titulo)

//        tibiaDataViewModel.getCharPorNome("Royal Pojap")
        //mainViewModel.getAccount(LoginActivity.auth.currentUser.toString())
        //mainViewModel.getAccount("668d97479cb94a0ad59d17b9")

//        mainViewModel.postAccount(conta)


    }
}
