package com.mobile_app.themovie.presentation.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox

class ChipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatCheckBox(context, attrs, defStyle) {
    var value: Any? = null
}