package com.example.toomanycoolgames

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("srcCompatOn", "srcCompatOff", "customIsOn", requireAll = false)
fun setOnOffSrcCompat(
    view: FloatingActionButton,
    onRes: Drawable,
    offRes: Drawable,
    isOn: Boolean
) {
    view.setImageDrawable(if (isOn) onRes else offRes)
}