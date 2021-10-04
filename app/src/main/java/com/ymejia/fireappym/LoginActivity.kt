package com.ymejia.fireappym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.ymejia.fireappym.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        setup()



    }

    private fun setup() {
        title = "Log in"

        binding.BtnLogin.setOnClickListener{
            if (binding.editTextEmail.text.isNotEmpty() && binding.editTextPassword.text.isNotEmpty()){

                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.editTextEmail.text.toString(),binding.editTextPassword.text.toString())
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            showHome( it.result?.user?.email ?: "",ProviderType.BASIC)

                            binding.editTextEmail.text.clear()
                            binding.editTextPassword.text.clear()
                        }
                        else{
                            showAlert()
                            binding.editTextEmail.text.clear()
                            binding.editTextPassword.text.clear()
                        }
                    }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("An Authentication Error")
        builder.setPositiveButton("Accept",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun showHome(email: String, provider: ProviderType){

        val homeIntent = Intent(this,HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }


}