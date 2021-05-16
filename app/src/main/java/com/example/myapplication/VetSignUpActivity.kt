package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class VetSignUpActivity : AppCompatActivity() {
    lateinit var vFirstName: EditText
    lateinit var vLastName: EditText
    lateinit var vEmail: EditText
    lateinit var vPassword: EditText
    lateinit var vPhone: EditText
    var vCity: EditText? = null
    lateinit var vRegister: Button
    var fAuth: FirebaseAuth? = null
    lateinit var progressBar: ProgressBar
    var fStore: FirebaseFirestore? = null
    var userID: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vet_signup)
        vFirstName = findViewById(R.id.enterFirstNameVet)
        vLastName = findViewById(R.id.enterLastNameVet)
        vCity = findViewById(R.id.enterCity)
        vEmail = findViewById(R.id.enterEmailAddressVet)
        vPassword = findViewById(R.id.enterPasswordVet)
        vPhone = findViewById(R.id.editTextPhone)
        vRegister = findViewById(R.id.register)
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        progressBar = findViewById(R.id.progressBar)
        if (fAuth!!.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        vRegister.setOnClickListener(OnClickListener {
            val emailVet = vEmail.getText().toString().trim { it <= ' ' }
            val passwordVet = vPassword.getText().toString().trim { it <= ' ' }
            val firstNameVet = vFirstName.getText().toString()
            val lastNameVet = vLastName.getText().toString()
            val phoneVet = vPhone.getText().toString()
            if (TextUtils.isEmpty(emailVet)) {
                vEmail.setError("Email is Required.")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(passwordVet)) {
                vPassword.setError("Password is Required.")
                return@OnClickListener
            }
            if (passwordVet.length < 6) {
                vPassword.setError("Password Must be >= 6 Characters")
                return@OnClickListener
            }
            progressBar.setVisibility(VISIBLE)

            // register the user in firebase
            fAuth!!.createUserWithEmailAndPassword(emailVet, passwordVet)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        // send verification link
                        val fuser = fAuth!!.currentUser
                        fuser!!.sendEmailVerification().addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Verification Email Has been Sent.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                            .addOnFailureListener { e ->
                                Log.d(
                                    TAG,
                                    "onFailure: Email not sent " + e.message
                                )
                            }
                        Toast.makeText(this, "User Created.", Toast.LENGTH_SHORT).show()
                        userID = fAuth!!.currentUser!!.uid
                        val documentReference = fStore!!.collection("users").document(
                            userID!!
                        )
                        val user: MutableMap<String, Any> = HashMap()
                        user["fName"] = firstNameVet
                        user["lName"] = lastNameVet
                        user["email"] = emailVet
                        user["phone"] = phoneVet
                        documentReference.set(user).addOnSuccessListener {
                            Log.d(
                                TAG,
                                "onSuccess: user Profile is created for $userID"
                            )
                        }
                            .addOnFailureListener { e -> Log.d(TAG, "onFailure: $e") }
                        startActivity(Intent(applicationContext, VetHomePageActivity::class.java))
                    } else {
                        Toast.makeText(
                            this,
                            "Error ! " + task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        progressBar.setVisibility(GONE)
                    }
                }
        })
    }
    companion object {
        const val TAG = "TAG"
    }
}