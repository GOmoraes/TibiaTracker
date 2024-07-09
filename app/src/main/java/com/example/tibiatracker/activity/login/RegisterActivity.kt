package com.example.tibiatracker.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tibiatracker.R
import com.example.tibiatracker.activity.activity.MainActivity
import com.example.tibiatracker.activity.repository.MainRepositoryImpl
import com.example.tibiatracker.activity.service.ApiClient
import com.example.tibiatracker.activity.view_model.MainViewModel
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class RegisterActivity : ComponentActivity() {

    private lateinit var btRegistrar : Button
    private lateinit var btEntrar : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        setViews()
    }



    private fun setViews(){
        btRegistrar = findViewById(R.id.bt_registrar)
        btEntrar = findViewById(R.id.bt_login)



        btRegistrar.setOnClickListener{
            var email : EditText = findViewById(R.id.ti_email)
            var senha : EditText = findViewById(R.id.ti_senha)

            if (!email.text.toString().isNullOrEmpty() && !senha.text.toString().isNullOrEmpty()){
                LoginActivity.auth.createUserWithEmailAndPassword(email.text.toString(),senha.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        startActivity(Intent(this,LoginActivity::class.java))
                        finish()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
        btEntrar.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}
