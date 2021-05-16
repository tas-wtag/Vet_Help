package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth

class VetHomePageActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vet_homepage)
    }

    fun logout(view: View?) {
        FirebaseAuth.getInstance().signOut() //logout
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
}}