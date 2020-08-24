package com.example.android.ycet.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.ycet.R
import com.example.android.ycet.adapters.DocumentAdapter
import com.example.android.ycet.models.Document
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_documents.*
import kotlinx.android.synthetic.main.item_exercise_status.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DocumentActivity : AppCompatActivity(){

    private val personCollectionRef = FirebaseFirestore.getInstance()
    private lateinit var userAdapter: DocumentAdapter
    private var mUsers: List<Document>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_documents)
        retrieveAllUsers()
        mUsers = ArrayList()
    }
    private fun retrieveAllUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //val currentUsersId = FirebaseAuth.getInstance().currentUser!!.uid
                val querySnapshot = personCollectionRef.collection("docs")

                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result)  {
                            val boards = document.toObject(Document::class.java)
                            (mUsers as ArrayList<Document>).add(boards)
                            userAdapter =
                                DocumentAdapter(
                                    this@DocumentActivity,
                                    mUsers!!
                                )
                            search.adapter = userAdapter
                            search.layoutManager = LinearLayoutManager(this@DocumentActivity)

                        }
                    }


            }

            catch(e: Exception) {
                withContext(Dispatchers.Main) {
                    //oast.makeText(this@Zx, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}