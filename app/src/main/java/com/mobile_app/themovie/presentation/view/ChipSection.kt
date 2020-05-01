package com.mobile_app.themovie.presentation.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.mobile_app.themovie.R
import kotlinx.android.synthetic.main.view_chip_section.view.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ChipSection @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defRes) {

    private val chips = ArrayList<ChipView>()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_chip_section, this)
    }

    /**
     * adding one more option to section
     * @param chipTitle - displayed title of chip
     */
    fun addChip(chipTitle: String) {
        val chipView = LayoutInflater.from(context).inflate(R.layout.view_chip, this, false) as ChipView
        chipView.text = chipTitle
        chips.add(chipView)
        vChipGroup.addView(chipView)
    }

    fun clear() {
        vChipGroup.removeAllViews()
        chips.clear()
    }
}