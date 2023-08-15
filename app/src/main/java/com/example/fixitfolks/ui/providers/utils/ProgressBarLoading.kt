package com.example.fixitfolks.ui.providers.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.fixitfolks.R

class ProgressBarLoading(private val activity: Activity) {
    private lateinit var alertDialog : AlertDialog
    fun startLoadingDialog() {
        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setView(activity.layoutInflater.inflate(R.layout.dialog_progress_bar,null))
        builder.setCancelable(true);
        alertDialog = builder.create()
        alertDialog.show()
    }
    fun dismissDialog() {
        alertDialog.dismiss()
    }
}