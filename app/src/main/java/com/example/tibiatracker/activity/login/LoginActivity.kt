package com.example.tibiatracker.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.tibiatracker.R
import com.example.tibiatracker.activity.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : ComponentActivity() {

    private lateinit var btRegistrar : Button
    private lateinit var btLogin : Button

    companion object{
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()


        setViews()
    }



    private fun setViews(){
        btRegistrar = findViewById(R.id.bt_registrar)
        btLogin = findViewById(R.id.bt_login)


        btRegistrar.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
        btLogin.setOnClickListener{
            var email : EditText = findViewById(R.id.ti_email)
            var senha : EditText = findViewById(R.id.ti_senha)

            if (!email.text.toString().isNullOrEmpty() && !senha.text.toString().isNullOrEmpty()){
                auth.signInWithEmailAndPassword(email.text.toString(),senha.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
