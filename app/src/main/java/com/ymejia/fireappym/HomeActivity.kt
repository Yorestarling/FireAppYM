package com.ymejia.fireappym

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.ymejia.fireappym.databinding.ActivityHomeBinding

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)




        val bundle = intent.extras
        val provider = bundle?.getString("provider")
        val firstName = bundle?.getString("firstName")
        val email = bundle?.getString("email")

        setup(email ?: "",
            provider ?:"",
            firstName ?:"",
        )
        //dateProfile()
    }

    private fun setup(email: String,
                      provider:String,
                      firstName: String,
                      ){

        title="Profile"
        binding.txtName.text = firstName
        binding.txtEmailAdd.text = email


        binding.btnSignout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }

    }



    private fun dateProfile(){
        val username : String = binding.txtName.text.toString()

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(username).get().addOnSuccessListener {

            if(it.exists()){

                val firstName = it.child("firstName").value
                val lastName  = it.child("lastName").value
                val phone = it.child("phone").value
                val email  = it.child("email").value
                val gender = it.child("gender").value
                val birth = it.child("birth").value
                val country  = it.child("country").value
                val state = it.child("state").value
                val address  = it.child("address").value
                val password = it.child("password").value
                val rePassword  = it.child("rePassword").value

                binding.txtName.text = firstName.toString()
                binding.txtLastName.text = lastName.toString()
                binding.txtPhone.text = phone.toString()
                binding.txtEmailAdd.text = email.toString()
                binding.txtGender.text = gender.toString()
                binding.txtBirthday.text = birth.toString()
                binding.txtCountry.text = country.toString()
                binding.txtState.text = state.toString()
                binding.txtAddress.text = address.toString()



            }else{
                Toast.makeText(this,"User Doesn't Exist",Toast.LENGTH_SHORT).show()

            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }

}