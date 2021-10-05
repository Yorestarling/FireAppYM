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


                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
        setup()

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
                           // Toast.makeText(this, "USER Added Successfully", Toast.LENGTH_SHORT).show()
                            showHome(
                                it.result?.user?.email ?: "",
//                                 ?: "",
//                                it.result?.user?.email ?: "",
//                                it.result?.user?.email ?: "",
//                                it.result?.user?.email ?: "",
//                                it.result?.user?.email ?: "",
//                                it.result?.user?.email ?: "",
//                                it.result?.user?.email ?: "",
//                                it.result?.user?.email ?: "",
                                ProviderType.BASIC)
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


    private fun showHome(
//        firstName: String,
//                         lastName: String,
//                         phone: String,
                         email: String,
//                         gender: String,
//                         birthday: String,
//                         country: String,
//                         state: String,
//                         address: String,
                         provider: ProviderType){

        val homeIntent = Intent(this,HomeActivity::class.java).apply {
//            putExtra("firstName",firstName)
//            putExtra("lastName",lastName)
//            putExtra("phone",phone)
            putExtra("email",email)
//            putExtra("gender",gender)
//            putExtra("birthday",birthday)
//            putExtra("country",country)
//            putExtra("state",state)
//            putExtra("address",address)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }


}