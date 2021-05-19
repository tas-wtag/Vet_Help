package com.example.myapplication

import android.content.Intent
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


class PetHomePageActivity: AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("vets")
    private var adapter: MyAdapter? = null
    private var list: ArrayList<Model>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_homepage)

        recyclerView = findViewById(R.id.recycleview)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        list = ArrayList()
        adapter = MyAdapter(this, list)

        recyclerView.setAdapter(adapter)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val model = dataSnapshot.getValue(Model::class.java)
                    if (model != null) {
                        list!!.add(model)
                    }
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun logout(view: View?) {
        FirebaseAuth.getInstance().signOut() //logout
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}


