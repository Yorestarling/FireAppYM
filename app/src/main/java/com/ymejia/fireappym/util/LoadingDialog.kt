package com.ymejia.fireappym.util

import android.app.Activity
import android.app.AlertDialog
import com.ymejia.fireappym.R

class LoadingDialog (val mActivity:Activity){
    private lateinit var isdialog: AlertDialog

    fun startLoading(){

        /**set View*/
        val infalter = mActivity.layoutInflater
        val dialogView = infalter.inflate(R.layout.loading_bar,null)
        /**set Dialog*/
        val bulider = AlertDialog.Builder(mActivity)
        bulider.setView(dialogView)
        bulider.setCancelable(false)
        isdialog = bulider.create()
        isdialog.show()
    }

    fun isDismiss(){
        isdialog.dismiss()
    }
}