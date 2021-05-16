package com.example.myapplication

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var signUpAsVet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signUpAsVet=findViewById(R.id.signUpAsVet)

        signUpAsVet.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, VetSignUpActivity::class.java)
            startActivity(intent)
            finish()
        })
}
}


