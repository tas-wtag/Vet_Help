package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MyAppointmentAdapter(var context: Context,
                           var appointmentList: ArrayList<AppointmentData>,
                           private val clickListener: (AppointmentData) -> Unit) :
        RecyclerView.Adapter<MyAppointmentAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.appointment_details, parent, false)
        return MyViewHolder(v)
    }


    override fun getItemCount(): Int {
        return appointmentList.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var emailVet: TextView
        var emailPet: TextView
        var button: Button
        var appointmentData: AppointmentData
        fun bind(appointmentData: AppointmentData) {
            this.appointmentData = appointmentData
        }

        init {
            emailVet = itemView.findViewById(R.id.dummyEmailItemVet)
            emailPet = itemView.findViewById(R.id.dummyNameItemPet)
            button = itemView.findViewById(R.id.confirmButton)
            appointmentData=AppointmentData()
            button.setOnClickListener { clickListener(appointmentData) }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = appointmentList[position]
        holder.emailVet.text = model.emailVet
        holder.emailPet.text = model.emailPet
        holder.bind(model)
    }

}