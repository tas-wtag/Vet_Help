package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.AppointmentDataModel
import java.util.ArrayList

class MyAppointmentAdapter(var context: Context,
                           var appointmentList: ArrayList<AppointmentDataModel>,
                           private val clickListener: (AppointmentDataModel) -> Unit) :
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
        var appointmentData: AppointmentDataModel
        fun bind(appointmentData: AppointmentDataModel) {
            this.appointmentData = appointmentData
        }

        init {
            emailVet = itemView.findViewById(R.id.dummyEmailItemVet)
            emailPet = itemView.findViewById(R.id.dummyNameItemPet)
            button = itemView.findViewById(R.id.confirmButton2)
            appointmentData= AppointmentDataModel()
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