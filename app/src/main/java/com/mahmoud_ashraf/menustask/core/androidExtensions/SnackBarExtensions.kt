package com.mahmoud_ashraf.menustask.core.androidExtensions

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

@JvmOverloads
fun showErrorSnackBar(
    view: View,
    errorMessage: String,
    actionName: String? = null,
    color: Int = Color.RED,
    actionClickListener: View.OnClickListener? = null,
    length: Int = Snackbar.LENGTH_LONG,
): Snackbar {
    val snackBar = Snackbar.make(view, errorMessage, length)
        .setBackgroundTint(color)
        .setAction(actionName, actionClickListener)
        .addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
            }
        })

    snackBar.show()
    return snackBar
}
