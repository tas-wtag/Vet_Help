package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.MyAdapter
import com.example.myapplication.models.VetDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class PetHomePageActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("vets")
    private var adapter: MyAdapter? = null
    private var list: ArrayList<VetDataModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_homepage)
        val checkReqBtn:Button=findViewById(R.id.checkReq)
        recyclerView = findViewById(R.id.recycleview)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        checkReqBtn.setOnClickListener(View.OnClickListener {
            intent= Intent(applicationContext, AppointmentResponseActivity::class.java)
            startActivity(intent)
        })
        list = ArrayList()
        adapter = MyAdapter(this, list!!) { model -> itemClicked(model) }

        recyclerView.setAdapter(adapter)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val model = dataSnapshot.getValue(VetDataModel::class.java)
                    if (model != null) {
                        list!!.add(model)
                    }
                }
                adapter!!.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun itemClicked(model: VetDataModel) {
        val bundle: Bundle? = intent.extras
        val emailPet = bundle?.get("emailPet")
        val userId = bundle?.get("userId").toString()
        val emailVet: String? =model.email
        val user = VetSignUpActivity.UserAppointment(emailPet as String?, emailVet)
        val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        mDatabase.child("appointments").child(userId).setValue(user)
        startActivity(Intent(this, AppointmentResponseActivity::class.java))
    }
        fun logout(view: View?) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }



