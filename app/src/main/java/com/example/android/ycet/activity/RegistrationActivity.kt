package com.example.android.ycet.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.android.ycet.R
import com.example.android.ycet.classes.FireStore
import com.example.android.ycet.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_register.*


class RegistrationActivity : BaseActivity(){
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
        val firstName: String = nameEditText.text.toString().trim { it <= ' ' }
        val lastName: String = passwordEditText.text.toString().trim { it <= ' ' }

        if (nameEditText.text.toString().isEmpty()){
            nameEditText.error="Please enter name"
            nameEditText.requestFocus()
            return
        }

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

        if (passwordEditText.text.toString().isEmpty()) {
            passwordEditText.error = "Please enter password"
            passwordEditText.requestFocus()
            return
        }

        if (passwordConfirmEditText.text.toString() != passwordEditText.text.toString()){
            passwordConfirmEditText.error="Password and Confirm password should be same"
            passwordConfirmEditText.requestFocus()
            return
        }



        auth.createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser? = task.result!!.user
                    // Registered Email
                    val registeredEmail = firebaseUser!!.email!!



                    val users = User(firebaseUser.uid,firstName,lastName,registeredEmail)

                    // call the registerUser function of FirestoreClass to make an entry in the database.
                    FireStore().registerUser(this@RegistrationActivity, users)
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    val user = auth.currentUser
                    user?.sendEmailVerification()

                } else {
                    Toast.makeText(
                        baseContext, "Sign Up failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}


