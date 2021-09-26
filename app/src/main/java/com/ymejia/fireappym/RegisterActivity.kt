package com.ymejia.fireappym

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.ymejia.fireappym.databinding.ActivityMainBinding
import com.ymejia.fireappym.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        //Spinner
        val spinner = binding.spinner
        val list = listOf("Gender", "Male", "Female")
        val adapterlist = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        spinner.adapter = adapterlist

        //DatePicker

        binding.txtBirth.setOnClickListener { showDatePicker() }
    }

    //DatePickerFun
    private fun showDatePicker() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager,"datePicker")
    }

    fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.txtBirth.setText("$day-$month-$year")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.register_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


}