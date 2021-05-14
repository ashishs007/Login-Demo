package com.login.base

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.login.R
import com.login.utils.Constants
import java.text.SimpleDateFormat
import java.util.*


open class BaseActivity : AppCompatActivity() {


    lateinit var alertBuilder: AlertDialog.Builder



    fun onInfo(message: String?) {
        if (message != null)
            onInfo(message, false)
    }

    private fun onInfo(message: String, finishOnOk: Boolean) {
        getAlertDialogBuilder(getString(R.string.app_name), message).setPositiveButton(
            getString(R.string.ok),
            if (finishOnOk)
                DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss();finish() }
            else
                null).show()
    }

    private fun getAlertDialogBuilder(title: String?, message: String): AlertDialog.Builder {
        alertBuilder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
        return alertBuilder
    }

    fun configureToolBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        if (supportActionBar != null) {
            supportActionBar!!.title = ""
        }
    }

    fun enableLoadingBar(enable: Boolean) {
        if (enable) {
            loadProgressBar()
        } else {
            dismissProgressBar()
        }
    }

    private fun loadProgressBar() {
        if (!this.isFinishing) {
            handleProgressLoader(true)
        }

    }

    private fun dismissProgressBar() {
        handleProgressLoader(false)
    }

     fun handleProgressLoader(isLoading: Boolean){
         val progressView=findViewById<View>(R.id.progress_layout)
         if (progressView != null) {
             if (isLoading) {
                 progressView.visibility = View.VISIBLE
                 getWindow().setFlags(
                     WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                     WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

             }else {
                 progressView.visibility = View.GONE
                 getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
             }
         }

     }
    fun dropDownDialog(title: String, listData: Array<String>, @IdRes id: Int) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this@BaseActivity)
        builder.setTitle(title)
        builder.setItems(listData) { dialog, which ->
            dialog.dismiss()
            onDropDownItemSelected(id, which)
        }
        builder.show()
    }

    open fun onDropDownItemSelected(@IdRes id: Int, position: Int) {

    }
     fun openDateDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                val selectedTime = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, monthOfYear)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }.time
                onDOBSelected(SimpleDateFormat(Constants.FORMATE_dd_MMMM_yyyy, Locale.US).format(selectedTime))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // min age allowed is 18 yrs for DOB
        calendar.add(Calendar.YEAR, -18)
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }
    open fun onDOBSelected(Dob:String) {

    }
}