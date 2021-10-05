package com.ymejia.fireappym

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
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
        val lastName = bundle?.getString("lastName")
        val phone = bundle?.getString("phone")
        val email = bundle?.getString("email")
        val gender = bundle?.getString("gender")
        val birthday = bundle?.getString("birthday")
        val country = bundle?.getString("country")
        val state = bundle?.getString("state")
        val address = bundle?.getString("address")

        setup(email ?: "",
            provider ?:"",
            firstName ?:"",
            lastName ?:"",
            phone ?:"",
            gender ?:"",
            birthday ?:"",
            country ?:"",
            state ?:"",
            address ?:"",
        )



    }









    private fun setup(email: String,
                      provider:String,
                      firstName: String,
                      lastName: String,
                      phone: String,
                      gender: String,
                      birthDay: String,
                      country: String,
                      state: String,
                      address: String){

        title="Profile"
        binding.txtName.text = firstName
        binding.txtLastName.text = lastName
        binding.txtPhone.text = phone
        binding.txtEmailAdd.text = email
        binding.txtGender.text = gender
        binding.txtBirthday.text = birthDay
        binding.txtCountry.text = country
        binding.txtState.text = state
        binding.txtAddress.text = address



        binding.btnSignout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()



        }

    }
}