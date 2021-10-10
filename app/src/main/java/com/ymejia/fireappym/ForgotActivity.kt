package com.ymejia.fireappym

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.ymejia.fireappym.databinding.ActivityForgotBinding

class ForgotActivity : AppCompatActivity() {

    lateinit var binding : ActivityForgotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_forgot)


        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot)



        binding.btnsutmit.setOnClickListener{
            val email: String = binding.txtemail.text.toString().trim{it <= ' '}
            if(email.isEmpty())
            {
                Toast.makeText(this@ForgotActivity,"Please Enter email Address.", Toast.LENGTH_SHORT).show()
            }
            else
            {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            Toast.makeText(this@ForgotActivity,"EMAIL SENT", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this@ForgotActivity,task.exception!!.message.toString(),
                                Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

    }
}