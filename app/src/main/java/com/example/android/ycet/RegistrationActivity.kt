package com.example.android.ycet

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.*


class RegistrationActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)

        auth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener {
            signUpUser()
        }

    }
    private fun signUpUser() {


        if (emailEditText.text.toString().isEmpty()) {
            emailEditText.error = "Please enter email"
            emailEditText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()) {
            emailEditText.error = "Please enter valid email"
            emailEditText.requestFocus()
            return
        }

        if (nameEditText.text.toString().isEmpty()) {
            nameEditText.error = "Please enter name"
            nameEditText.requestFocus()
            return
        }

        if (passwordEditText.text.toString().isEmpty()) {
            passwordEditText.error = "Please enter password"
            passwordEditText.requestFocus()
            return
        }



        auth.createUserWithEmailAndPassword((emailEditText.text.toString()), passwordEditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }
                else
                {
                    Toast.makeText(baseContext, "Sign Up failed. Try again after some time.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}



