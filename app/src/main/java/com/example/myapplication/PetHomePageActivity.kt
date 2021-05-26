package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore


class PetHomePageActivity : AppCompatActivity() {

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
        adapter = MyAdapter(this, list!!) { model -> itemClicked(model) }

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

    fun itemClicked(model: Model) {

        val bundle: Bundle? = intent.extras
        val emailPet = bundle?.get("emailPet")
        val userId = bundle?.get("userId").toString()
        //var userID:String=userId.toString()
       // if (userID.contains("@")) {
           // userID = userID.split("@").toTypedArray()[1]
       // }
        val emailVet: String? =model.email
        val user = VetSignUpActivity.UserAppointment(emailPet as String?, emailVet)
        val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        mDatabase.child("appointments").child(userId).setValue(user)

        startActivity(Intent(this, VetHomePageActivity::class.java))
    }
        fun logout(view: View?) {
            FirebaseAuth.getInstance().signOut() //logout
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }



