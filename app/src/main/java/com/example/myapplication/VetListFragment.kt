package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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
    var listener:FragmentActivity?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            this.listener = context as FragmentActivity
        }
    }
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.vetlist_fragment, parent, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = requireView().findViewById(R.id.recycleView)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        list = ArrayList()
        adapter = MyAdapter(context, list!!) { model -> itemClicked(
            model as VetDataModel
        ) }
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
        val emailPet = arguments?.getString("emailPet")
        val userId = arguments?.getString("userId").toString()
        val emailVet: String? =model.email
        val user = VetSignUpActivity.UserAppointment(emailPet, emailVet)
        val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        mDatabase.child("appointments").child(userId).setValue(user)
        val intent = Intent(activity, AppointmentResponseActivity::class.java)
        startActivity(intent)
    }
}

