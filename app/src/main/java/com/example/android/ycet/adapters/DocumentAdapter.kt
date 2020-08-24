package com.example.android.ycet.adapters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.android.ycet.R
import com.example.android.ycet.models.Document


class DocumentAdapter(
    private val mContext: Context, private val mList: List<Document>
):RecyclerView.Adapter<DocumentAdapter.ViewHolder>()
{
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var userName:TextView = itemView.findViewById(R.id.tvItem)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(mContext).inflate(R.layout.item_exercise_status,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user:Document = mList[position]
        holder.userName.text = user.name
        holder.userName.setOnClickListener {
            val url = user.id
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(url), "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            val newIntent = Intent.createChooser(intent, "Open File")
            try {
               startActivity(mContext,newIntent, Bundle())
            } catch (e: ActivityNotFoundException) {
                // Instruct the user to install a PDF reader here, or something
            }
            Toast.makeText(mContext,"clicked",Toast.LENGTH_SHORT).show()
        }
    }
}
