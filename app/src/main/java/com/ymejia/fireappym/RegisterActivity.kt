package com.ymejia.fireappym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ymejia.fireappym.databinding.ActivityMainBinding
import com.ymejia.fireappym.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference
    private  lateinit var firebaseUser: FirebaseUser
    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var databases: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        //Spinner
//        val spinner = binding.spinner
//        val list = listOf("Gender", "Male", "Female")
//        val adapterlist = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
//        spinner.adapter = adapterlist

        //DatePicker

        auth = FirebaseAuth.getInstance()
        databases = FirebaseDatabase.getInstance()
        databaseReference = databases?.reference!!.child("Users")

        binding.txtBirth.setOnClickListener { showDatePicker() }
        register()
    }

        //DatePickerFun
        private fun showDatePicker() {
            val datePicker =
                DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
            datePicker.show(supportFragmentManager, "datePicker")
        }

        fun onDateSelected(day: Int, month: Int, year: Int) {
            binding.txtBirth.setText("$day-$month-$year")
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.register_menu, menu)
            return super.onCreateOptionsMenu(menu)
        }

    private fun register() {


        binding.BtnRegisterin.setOnClickListener {

            if(TextUtils.isEmpty(binding.txtPersonName.text.toString())) {
                binding.txtPersonName.setError("Please enter first name ")
                return@setOnClickListener
            } else if(TextUtils.isEmpty(binding.txtPersonLastName.text.toString())) {
                binding.txtPersonLastName.setError("Please enter last name ")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.txtPhone.text.toString())) {
                binding.txtPhone.setError("Please enter Phone number ")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.txtEmailAddress.text.toString())) {
                binding.txtEmailAddress.setError("Please enter Phone number ")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.txtGenders.text.toString())) {
                binding.txtGenders.setError("Please enter Gender ")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.txtBirth.text.toString())) {
                binding.txtBirth.setError("Please enter Birthday ")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.txtCountry.text.toString())) {
                binding.txtCountry.setError("Please enter country ")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.txtState.text.toString())) {
                binding.txtState.setError("Please enter State ")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.txtAddress.text.toString())) {
                binding.txtAddress.setError("Please enter address ")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.TxtPassword.text.toString())) {
                binding.TxtPassword.setError("Please enter Password ")
                return@setOnClickListener
            }else if (binding.TxtPassword.text.toString().length <= 5) {
                binding.TxtPassword.error = "The password must be at least 6 characters"
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.TxtRePassword.text.toString())) {
                binding.TxtRePassword.setError("Please enter Re-password ")
                return@setOnClickListener
            }else if (binding.TxtPassword.text.toString() != binding.TxtRePassword.text.toString()) {
                binding.TxtRePassword.error = "invalid Re-password please enter again "
                return@setOnClickListener
            }




            auth.createUserWithEmailAndPassword(binding.txtEmailAddress.text.toString(), binding.TxtPassword.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val currentUser = auth.currentUser
                        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))
                        currentUSerDb?.child("Firstname")?.setValue(binding.txtPersonName.text.toString())
                        currentUSerDb?.child("Lastname")?.setValue(binding.txtPersonLastName.text.toString())
                        currentUSerDb?.child("Phone")?.setValue(binding.txtPhone.text.toString())
                        currentUSerDb?.child("Email")?.setValue(binding.txtEmailAddress.text.toString())
                        currentUSerDb?.child("Gender")?.setValue(binding.txtGenders.text.toString())
                        currentUSerDb?.child("Birthday")?.setValue(binding.txtBirth.text.toString())
                        currentUSerDb?.child("Country")?.setValue(binding.txtCountry.text.toString())
                        currentUSerDb?.child("State")?.setValue(binding.txtState.text.toString())
                        currentUSerDb?.child("Address")?.setValue(binding.txtAddress.text.toString())
                        currentUSerDb?.child("Password")?.setValue(binding.TxtPassword.text.toString())
                        currentUSerDb?.child("Repassword")?.setValue(binding.TxtRePassword.text.toString())

                        Toast.makeText(this@RegisterActivity, "Registration Success. ", Toast.LENGTH_LONG).show()
                        finish()

                    } else {
                        Toast.makeText(this@RegisterActivity, "Registration failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }


}