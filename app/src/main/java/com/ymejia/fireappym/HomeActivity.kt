package com.ymejia.fireappym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.ymejia.fireappym.databinding.ActivityHomeBinding
import com.ymejia.fireappym.util.LoadingDialog

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var databases: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)


        auth = FirebaseAuth.getInstance()
        databases = FirebaseDatabase.getInstance()
        databaseReference = databases?.reference!!.child("Users")

        loadProfile()


        val loading = LoadingDialog(this)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed(object :Runnable{

            override fun run() {
                loading.isDismiss()
            }

        },1000)

    }


    private fun loadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        binding.txtEmailAdd.text = user?.email

        userreference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.txtName.text = snapshot.child("Firstname").value.toString()
                binding.txtLastName.text = snapshot.child("Lastname").value.toString()
                binding.txtPhone.text = snapshot.child("Phone").value.toString()
                binding.txtGender.text = snapshot.child("Gender").value.toString()
                binding.txtBirthday.text = snapshot.child("Birthday").value.toString()
                binding.txtCountry.text = snapshot.child("Country").value.toString()
                binding.txtState.text = snapshot.child("State").value.toString()
                binding.txtAddress.text = snapshot.child("Address").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        binding.btnSignout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
            finish()
        }
    }

}