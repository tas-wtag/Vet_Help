package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MyAdapter.MyViewHolder
import java.util.*

class MyAdapter(var context: Context, var mList: ArrayList<Model>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = mList[position]
        holder.username.text = model.username
        holder.email.text = model.email
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView
        var email: TextView

        init {
            username = itemView.findViewById(R.id.dummyNameItem)
            email = itemView.findViewById(R.id.dummyEmailItem)
        }
    }
}