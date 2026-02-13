package com.example.tibiatracker.activity.activity.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.transition.Visibility
import com.example.tibiatracker.R

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
        var progressBar : ProgressBar = findViewById(R.id.pb_progress_bar)



        btRegistrar.setOnClickListener{
            var email : EditText = findViewById(R.id.ti_email)
            var senha : EditText = findViewById(R.id.ti_senha)

            if (!email.text.toString().isNullOrEmpty() && !senha.text.toString().isNullOrEmpty()){
                progressBar.visibility = View.VISIBLE
                LoginActivity.auth.createUserWithEmailAndPassword(email.text.toString(),senha.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(this, "Conta Criada com Sucesso !", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }.addOnFailureListener{
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Falha ao registrar, tente novamente !", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "registerResult: "+it.localizedMessage )
                }
            }
        }
        btEntrar.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
