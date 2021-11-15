package com.ymejia.fireappym

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.FirebaseDatabase
import com.ymejia.fireappym.databinding.ActivityMainBinding
import com.ymejia.fireappym.util.LoadingDialog


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_FireAppYM)

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.BtnLogIn.setOnClickListener{
            val showLog = Intent(Intent(this,LoginActivity::class.java))
            startActivity(showLog)
        }

        binding.BtnRegister.setOnClickListener{
            val showReg = Intent(Intent(this,RegisterActivity::class.java))
            startActivity(showReg)
        }



    }
}