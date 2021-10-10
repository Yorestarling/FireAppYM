package com.ymejia.fireappym

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.ymejia.fireappym.databinding.ActivityLoginBinding
import com.ymejia.fireappym.databinding.DialogForgotPasswordBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        auth = FirebaseAuth.getInstance()
        setup()

        binding.BtnForgot.setOnClickListener {


            startActivity(Intent(this,ForgotActivity::class.java))

//            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//            builder.setTitle("Forgot Password")
//            val view: View = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
//
//
//            builder.setView(view)
//            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ -> })
//
//
//            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ -> })
//            builder.show()
        }


    }




    private fun setup() {
        title = "Log in"

        binding.BtnLogin.setOnClickListener {
            if (binding.editTextEmail.text.isNotEmpty() && binding.editTextPassword.text.isNotEmpty()) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.editTextEmail.text.toString(),
                    binding.editTextPassword.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?:"",
                                it.result?.user?.uid ?:"",
                                ProviderType.BASIC)

                           // startActivity(Intent(this,HomeActivity::class.java))
                            finish()

                            binding.editTextEmail.text.clear()
                            binding.editTextPassword.text.clear()
                        } else {
                            showAlert()
                            binding.editTextEmail.text.clear()
                            binding.editTextPassword.text.clear()
                        }
                    }
            }
            else if(binding.editTextEmail.text.isNullOrBlank()){
                binding.editTextEmail.error = "EMAIL REQUIRED"
            }
            else if(binding.editTextPassword.text.isNullOrBlank()){
                binding.editTextPassword.error = "PASSWORD REQUIRED"
            }
        }
    }





    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("An Authentication Error")
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun showHome(email: String,usersId: String, provider: ProviderType) {

        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("UserId", usersId)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

}