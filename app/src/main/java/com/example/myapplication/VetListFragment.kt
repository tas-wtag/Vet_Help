package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.activities.AppointmentResponseActivity
import com.example.myapplication.activities.VetSignUpActivity
import com.example.myapplication.adapters.MyAdapter
import com.example.myapplication.models.VetDataModel
import com.google.firebase.database.*

@Suppress("UNREACHABLE_CODE")
class VetListFragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    val database = FirebaseDatabase.getInstance()
   private var adapter: MyAdapter? = null
    val myRef = database.getReference("vets")
    private var list: ArrayList<VetDataModel>? = null

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.myapplication.R.layout.vetlist_fragment, parent, false)
        adapter = context?.let { MyAdapter(it, list!!) { model -> itemClicked(model) } }
        recyclerView = recyclerView.findViewById(com.example.myapplication.R.id.recycleView)
        recyclerView.setAdapter(this.adapter)

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

        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context))

        list = ArrayList()

    }
    fun itemClicked(model: VetDataModel) {
        val emailPet = arguments?.getString("emailPet")
        val userId = arguments?.getString("userId").toString()
        val emailVet: String? =model.email
        val user = VetSignUpActivity.UserAppointment(emailPet, emailVet)
        val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        mDatabase.child("appointments").child(userId).setValue(user)
        val intent = Intent(activity, AppointmentResponseActivity::class.java)
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}





