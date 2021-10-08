package com.ymejia.fireappym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ymejia.fireappym.databinding.ActivityMainBinding
import com.ymejia.fireappym.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference

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

        binding.txtBirth.setOnClickListener { showDatePicker() }


        binding.BtnRegisterin.setOnClickListener {

            if(binding.txtPersonName.text.isBlank()){
                binding.txtPersonName.error = "First Name is Required"
            }
            else if (binding.txtPersonLastName.text.isBlank()){
                binding.txtPersonLastName.error = "Last Name is Required"
            }
            else if (binding.txtPhone.text.isBlank()){
                binding.txtPhone.error = "Phone Number is Required"
            }
            else if (binding.txtGenders.text.isBlank()){
                binding.txtGenders.error = "Gender is Required"
            }
            else if (binding.txtBirth.text.isBlank()){
                binding.txtBirth.error = "Birthday date is Required"
            }

            else if (binding.txtCountry.text.isBlank()){
                binding.txtCountry.error = "Country is Required"
            }
            else if (binding.txtState.text.isBlank()){
                binding.txtState.error = "State is Required"
            }
            else if (binding.txtAddress.text.isBlank()){
                binding.txtAddress.error = "Address is Required"
            }
            else if (binding.TxtPassword.text.isBlank()){
                binding.TxtPassword.error = "Password is Required"
            }
            else if (binding.TxtRePassword.text.isBlank()){
                binding.TxtRePassword.error = "Re-Password is Required"
            }
            else if (binding.TxtPassword.text.toString() != binding.TxtRePassword.text.toString()){
                binding.TxtRePassword.error = "invalid Re-password please enter again "
            }
            else{

                val firstName = binding.txtPersonName.text.toString()
                val lastName = binding.txtPersonLastName.text.toString()
                val phone = binding.txtPhone.text.toString()
                val email = binding.txtEmailAddress.text.toString()
                val gender = binding.txtGenders.text.toString()
                val birthday = binding.txtBirth.text.toString()
                val country = binding.txtCountry.text.toString()
                val state = binding.txtState.text.toString()
                val address = binding.txtAddress.text.toString()
                val password = binding.TxtPassword.text.toString()
                val rePassword = binding.TxtRePassword.text.toString()

                database = FirebaseDatabase.getInstance().getReference("Users")


                val user =
                    Users(firstName, lastName, phone, email, gender, birthday, country, state, address,password,rePassword)
                database.child(firstName).setValue(user).addOnSuccessListener {


                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener{
                            if (it.isSuccessful){
                                Toast.makeText(this, "Now you can log in Successfully", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this,LoginActivity::class.java))
                                finish()
                            }
                            else{
                                showAlert()
                            }
                        }

                    binding.txtPersonName.text.clear()
                    binding.txtPersonLastName.text.clear()
                    binding.txtPhone.text.clear()
                    binding.txtEmailAddress.text.clear()
                    binding.txtGenders.text.clear()
                    binding.txtBirth.text.clear()
                    binding.txtCountry.text.clear()
                    binding.txtState.text.clear()
                    binding.txtAddress.text.clear()
                    binding.TxtPassword.text.clear()
                    binding.TxtRePassword.text.clear()

                }.addOnFailureListener {
                    Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun dateValidated(){

    }

    //DatePickerFun
    private fun showDatePicker() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.txtBirth.setText("$day-$month-$year")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.register_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun setup() {
        title = "Register"

        binding.BtnRegisterin.setOnClickListener{
            if (binding.txtEmailAddress.text.isNotEmpty() && binding.TxtPassword.text.isNotEmpty()){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.txtEmailAddress.text.toString(),binding.TxtPassword.text.toString())
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            Toast.makeText(this, "USER Added Successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,LoginActivity::class.java))
                            finish()
                        //
                        //                            showHome(
//                                it.result?.user?.email ?: "",
////                                 ?: "",
////                                it.result?.user?.email ?: "",
////                                it.result?.user?.email ?: "",
////                                it.result?.user?.email ?: "",
////                                it.result?.user?.email ?: "",
////                                it.result?.user?.email ?: "",
////                                it.result?.user?.email ?: "",
////                                it.result?.user?.email ?: "",
//                                ProviderType.BASIC)
                        }
                        else{
                            showAlert()
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


}