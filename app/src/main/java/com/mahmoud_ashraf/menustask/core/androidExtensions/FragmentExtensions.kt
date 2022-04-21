package com.mahmoud_ashraf.menustask.core.androidExtensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> Fragment.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, Observer { it?.let(observer) })
}