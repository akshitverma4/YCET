package com.example.android.ycet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        holder.userName.text = user.id
    }
}
