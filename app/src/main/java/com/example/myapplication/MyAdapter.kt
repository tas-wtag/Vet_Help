package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MyAdapter.MyViewHolder
import java.util.*


class MyAdapter(var context: Context, var mList: ArrayList<Model>, private val clickListener: (Model) -> Unit) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.vet_details, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = mList[position]
        holder.username.text = model.username
        holder.email.text = model.email
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView
        var email: TextView
        var button: Button
        var model: Model
        fun bind(model: Model) {
            this.model = model
        }

        init {
            username = itemView.findViewById(R.id.dummyNameItem)
            email = itemView.findViewById(R.id.dummyEmailItem)
            button = itemView.findViewById(R.id.button_)
            model=Model()
            button.setOnClickListener { clickListener(model) }
        }

    }
}