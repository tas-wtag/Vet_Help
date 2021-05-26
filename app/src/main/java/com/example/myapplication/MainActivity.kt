package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {
    lateinit var signUpAsVet: Button
    lateinit var signUpAsPetOwner: Button
    lateinit var mEmail: EditText
    lateinit var mPassword: EditText
    lateinit var mLoginBtn: Button
    lateinit var mProgressBar: ProgressBar
    lateinit var forgotTextLink: TextView
    var mfAuth: FirebaseAuth? = null
    var flag:Boolean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signUpAsVet=findViewById(R.id.signUpAsVet)
        signUpAsPetOwner=findViewById(R.id.signUpAsPetOwner)
        mEmail = findViewById(R.id.email)
        mPassword = findViewById(R.id.password)
        mProgressBar = findViewById(R.id.progressBarMain)
        mfAuth = FirebaseAuth.getInstance()
        mLoginBtn = findViewById(R.id.login)
        forgotTextLink = findViewById(R.id.forgotPassword)

        mLoginBtn.setOnClickListener(View.OnClickListener {
            val memail = mEmail.getText().toString().trim { it <= ' ' }
            val mpassword = mPassword.text.toString().trim { it <= ' ' }

            if (TextUtils.isEmpty(memail)) {
                mEmail.setError("Email is Required.")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(mpassword)) {
                mPassword.setError("Password is Required.")
                return@OnClickListener
            }
            if (mpassword.length < 6) {
                mPassword.setError("Password Must be >= 6 Characters")
                return@OnClickListener
            }
            mProgressBar.setVisibility(View.VISIBLE)

            mfAuth!!.signInWithEmailAndPassword(memail, mpassword).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                    val database = FirebaseDatabase.getInstance()
                    val myRef = database.getReference()
                    task.getResult()?.user?.let { it1 ->
                        myRef.child("vets").child(it1.uid).addListenerForSingleValueEvent( object :ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    startActivity(Intent(applicationContext, VetHomePageActivity::class.java))
                                } else {
                                    val userId: String? = task.getResult()?.user?.uid
                                    intent= Intent(applicationContext, PetHomePageActivity::class.java)
                                    intent.putExtra("emailPet", memail)
                                    intent.putExtra("userId",userId)
                                    startActivity(intent)
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {}
                        })
                    }


                } else {
                    Toast.makeText(
                            this,
                            "Error ! " + task.exception!!.message,
                            Toast.LENGTH_SHORT
                    ).show()
                    mProgressBar.setVisibility(View.GONE)
                }
            }
        })
        signUpAsVet.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, VetSignUpActivity::class.java)
            flag = true
            intent.putExtra("flag", flag)
            startActivity(intent)
        })

        signUpAsPetOwner.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PetOwnerSignUpActivity::class.java)
            startActivity(intent)
        })

        forgotTextLink.setOnClickListener(View.OnClickListener { v ->
            val resetMail = EditText(v.context)
            val passwordResetDialog = AlertDialog.Builder(v.context)
            passwordResetDialog.setTitle("Reset Password ?")
            passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.")
            passwordResetDialog.setView(resetMail)
            passwordResetDialog.setPositiveButton("Yes") { dialog, which -> // extract the email and send reset link
                val mail = resetMail.text.toString()
                mfAuth!!.sendPasswordResetEmail(mail).addOnSuccessListener {
                    Toast.makeText(
                            this,
                            "Reset Link Sent To Your Email.",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                    this,
                                    "Error ! Reset Link is Not Sent" + e.message,
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
            }
            passwordResetDialog.setNegativeButton("No") { dialog, which ->
            }
            passwordResetDialog.create().show()
        })
}
       fun logOut(view: View?) {
        FirebaseAuth.getInstance().signOut() //logout
    }
}


