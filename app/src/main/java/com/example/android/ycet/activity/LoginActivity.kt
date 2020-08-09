package com.example.android.ycet.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.android.ycet.R
import com.example.android.ycet.classes.FireStore
import com.example.android.ycet.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.emailEditText
import kotlinx.android.synthetic.main.fragment_login.nameEditText

class LoginActivity: BaseActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)
        auth = FirebaseAuth.getInstance()

        signInButton.setOnClickListener {view ->
            doLogin(view)
        }
        not_a_member_sign_up_now.setOnClickListener {
            startActivity(Intent(this,
                RegistrationActivity::class.java))
            finish()
        }
    }

    private fun doLogin(view:View) {
        if (nameEditText.text.toString().isEmpty()) {
            nameEditText.error = "Please enter email"
            nameEditText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(nameEditText.text.toString()).matches()) {
            nameEditText.error = "Please enter valid email"
            nameEditText.requestFocus()
            return
        }

        if (emailEditText.text.toString().isEmpty()) {
            emailEditText.error = "Please enter password"
            emailEditText.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(nameEditText.text.toString(), emailEditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                   updateUI(user)
                    FireStore().signInUser(this@LoginActivity)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // ...
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()

            } else {
                Toast.makeText(
                    baseContext, "Please verify your email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
    fun signInSuccess(user: User){
        startActivity(Intent(this, DashboardActivity::class.java))
        this.finish()
    }
}