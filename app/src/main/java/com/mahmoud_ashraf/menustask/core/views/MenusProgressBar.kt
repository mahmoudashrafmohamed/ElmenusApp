package com.mahmoud_ashraf.menustask.core.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.mahmoud_ashraf.menustask.R

class MenusProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.menus_progress_bar, this)
       visibility = GONE
    }
}