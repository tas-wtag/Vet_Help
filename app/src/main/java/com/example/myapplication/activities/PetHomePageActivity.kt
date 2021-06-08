package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.VetListFragment
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseAuth


@Suppress("DEPRECATION")
class PetHomePageActivity : AppCompatActivity() {
    val fragmentTransaction= supportFragmentManager.beginTransaction()
    val myFragment: VetListFragment = VetListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_homepage)

        val checkReqBtn:Button?=findViewById(R.id.checkReq)
        val showVetListbtn:Button?=findViewById(R.id.buttonVetList)
        val bundle: Bundle? = intent.extras
        val emailPet = bundle?.get("emailPet")
        val userId= bundle?.get("userId")

        checkReqBtn?.setOnClickListener(View.OnClickListener {
            intent= Intent(applicationContext, AppointmentResponseActivity::class.java)
            startActivity(intent)
        })
        showVetListbtn?.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("emailPet", emailPet as String?)
            bundle.putString("userId", userId as String?)
            myFragment.arguments = bundle
            fragmentTransaction.replace(R.id.placeholder2, myFragment).commit()
            checkReqBtn?.visibility=View.GONE
            showVetListbtn.visibility=View.GONE
        })
    }
        fun logout(view: View?) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }




