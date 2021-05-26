package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.models.AppointmentDataModel
import com.example.myapplication.adapters.MyAppointmentAdapter
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VetHomePageActivity: AppCompatActivity(){
    lateinit var recyclerView: RecyclerView
    val database = FirebaseDatabase.getInstance()
    val myReference = database.getReference("appointments")
    private var adapter: MyAppointmentAdapter? = null
    private var list: ArrayList<AppointmentDataModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vet_homepage)

        recyclerView = findViewById(R.id.recycleviewVet)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        list = ArrayList()
        adapter = MyAppointmentAdapter(this, list!!) { appointmentData -> itemClicked(appointmentData) }

        recyclerView.setAdapter(adapter)
        myReference.addValueEventListener(object : ValueEventListener {
            val bundle: Bundle? = intent.extras
            val emailVet = bundle?.get("emailVet")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val appointmentData = dataSnapshot.getValue(AppointmentDataModel::class.java)
                    if (appointmentData != null && appointmentData.emailVet?.equals(emailVet) == true) {
                        list!!.add(appointmentData)
                    }
                }
                adapter!!.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun itemClicked(appointmentData: AppointmentDataModel) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun logout(view: View?) {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}



