package com.example.myapplication

import android.content.Intent
import android.view.View.*
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap

class PetOwnerSignUpActivity :  AppCompatActivity(){
    lateinit var pFirstName: EditText
    lateinit var pLastName: EditText
    lateinit var pEmail: EditText
    lateinit var pPassword: EditText
    lateinit var pCity: EditText
    lateinit var pRegister: Button
    var pfAuth: FirebaseAuth? = null
    lateinit var pProgressBar: ProgressBar
    var pfStore: FirebaseFirestore? = null
    var pUserID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petowner_signup)

        pFirstName = findViewById(R.id.enterFirstNamePO)
        pLastName = findViewById(R.id.enterLastNamePO)
        pCity = findViewById(R.id.enterAddressPO)
        pEmail = findViewById(R.id.enterEmailAddressPO)
        pPassword = findViewById(R.id.enterPasswordPO)
        pRegister = findViewById(R.id.registerPO)
        pfAuth = FirebaseAuth.getInstance()
        pfStore = FirebaseFirestore.getInstance()
        pProgressBar = findViewById(R.id.progressBarPO)

        pRegister.setOnClickListener(OnClickListener {
            val emailPO = pEmail.getText().toString().trim { it <= ' ' }
            val passwordPO = pPassword.getText().toString().trim { it <= ' ' }
            val firstNamePO = pFirstName.getText().toString()
            val lastNamePO = pLastName.getText().toString()
            val cityPO = pCity.getText().toString()
            if (TextUtils.isEmpty(emailPO)) {
                pEmail.setError("Email is Required.")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(passwordPO)) {
                pPassword.setError("Password is Required.")
                return@OnClickListener
            }
            if (passwordPO.length < 6) {
                pPassword.setError("Password Must be >= 6 Characters")
                return@OnClickListener
            }
            pProgressBar.setVisibility(VISIBLE)

            pfAuth!!.createUserWithEmailAndPassword(emailPO, passwordPO)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            // send verification link
                            val fuser = pfAuth!!.currentUser
                            fuser!!.sendEmailVerification().addOnSuccessListener {
                                Toast.makeText(
                                        this,
                                        "Verification Email Has been Sent.",
                                        Toast.LENGTH_SHORT
                                ).show()
                                task.getResult()?.getUser()?.let { it1 -> onAuthSuccess(it1) }
                            }
                                    .addOnFailureListener { e ->
                                        Log.d(
                                                VetSignUpActivity.TAG,
                                                "onFailure: Email not sent " + e.message
                                        )
                                    }
                            Toast.makeText(this, "User Created.", Toast.LENGTH_SHORT).show()
                            pUserID = pfAuth!!.currentUser!!.uid
                            val documentReference = pfStore!!.collection("users").document(
                                    pUserID!!
                            )
                            val user: MutableMap<String, Any> = HashMap()
                            user["fName"] = firstNamePO
                            user["lName"] = lastNamePO
                            user["email"] = emailPO
                            user["city"] = cityPO
                            documentReference.set(user).addOnSuccessListener {
                                Log.d(
                                        VetSignUpActivity.TAG,
                                        "onSuccess: user Profile is created for $pUserID"
                                )
                            }
                                    .addOnFailureListener { e -> Log.d(VetSignUpActivity.TAG, "onFailure: $e") }
                            startActivity(Intent(applicationContext, VetHomePageActivity::class.java))
                        } else {
                            Toast.makeText(
                                    this,
                                    "Error ! " + task.exception!!.message,
                                    Toast.LENGTH_SHORT
                            ).show()
                            pProgressBar.setVisibility(GONE)
                        }
                    }
        })

}

    private fun onAuthSuccess(firebaseUser: FirebaseUser) {
        val email: String? = firebaseUser.getEmail()
        var username = email
        if (email != null && email.contains("@")) {
            username = email.split("@").toTypedArray()[0]
        }
        val user = VetSignUpActivity.User(username, email)
        val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        mDatabase.child("pets").child(firebaseUser.getUid()).setValue(user)
        startActivity(Intent(this, MainActivity::class.java))
    }
}