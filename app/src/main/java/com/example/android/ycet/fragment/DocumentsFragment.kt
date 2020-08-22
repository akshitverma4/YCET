package com.example.android.ycet.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.ycet.R
import com.example.android.ycet.adapters.DocumentAdapter
import com.example.android.ycet.models.Document
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_documents.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 */
class DocumentsFragment : Fragment() {

    private val personCollectionRef = FirebaseFirestore.getInstance()
    private lateinit var userAdapter: DocumentAdapter
    private var mUsers: List<Document>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retrieveAllUsers()
        mUsers = ArrayList()
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_documents, container, false)
        return view
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
                                    context!!,
                                    mUsers!!
                                )
                            search.adapter = userAdapter
                            search.layoutManager = LinearLayoutManager(activity)

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
