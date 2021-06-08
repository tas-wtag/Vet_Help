package com.example.myapplication.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class AppointmentResponseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.response_of_appointment_activity)

        val response:EditText=findViewById(R.id.response)
        val bundle: Bundle? = intent.extras
        if(bundle!=null){
        val appointment_response = bundle.get("time")
            response.setText(appointment_response.toString())
        }
    }
}