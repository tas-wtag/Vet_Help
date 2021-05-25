package com.example.myapplication

import android.content.Intent
import android.graphics.ColorSpace
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var list: ArrayList<AppointmentData>? = null
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
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val appointmentData = dataSnapshot.getValue(AppointmentData::class.java)
                    if (appointmentData != null) {
                        list!!.add(appointmentData)
                    }
                }
                adapter!!.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun itemClicked(appointmentData: AppointmentData) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun logout(view: View?) {
        FirebaseAuth.getInstance().signOut() //logout
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}



