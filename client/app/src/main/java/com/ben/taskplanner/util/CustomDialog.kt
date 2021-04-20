package com.ben.taskplanner.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.ben.taskplanner.R

class CustomDialog(context: Context): Dialog(context) {

    init {
        customDialog = Dialog(context)
    }

    fun showDialog(message: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null, false)
        dialogView.findViewById<TextView>(R.id.desc_text).text = message
        customDialog?.setContentView(dialogView)
        customDialog?.setCanceledOnTouchOutside(false)
        customDialog?.setCancelable(false)
        customDialog?.show()
    }

    companion object {
        var customDialog: Dialog? = null
        fun dismissDialog() = customDialog?.let {
            customDialog!!.dismiss()
        }
    }
}