package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.VetListFragment
import com.google.firebase.auth.FirebaseAuth
import java.lang.reflect.Array.newInstance


class PetHomePageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_homepage)

        val checkReqBtn:Button?=findViewById(R.id.checkReq)
        val showVetListbtn:Button?=findViewById(R.id.buttonVetList)


        val buttonLogout:Button=findViewById(R.id.buttonVetDetail)

        checkReqBtn?.setOnClickListener(View.OnClickListener {
            intent= Intent(applicationContext, AppointmentResponseActivity::class.java)
            startActivity(intent)
        })
        showVetListbtn?.setOnClickListener(View.OnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.placeholder2,VetListFragment()).commit()
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




