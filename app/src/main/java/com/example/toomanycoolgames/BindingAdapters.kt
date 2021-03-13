package com.example.toomanycoolgames

import android.graphics.drawable.Drawable
import android.widget.ImageButton
import androidx.databinding.BindingAdapter

@BindingAdapter("srcCompatOn", "srcCompatOff", "customIsOn", requireAll = false)
fun setOnOffSrcCompat(
    view: ImageButton,
    onRes: Drawable,
    offRes: Drawable,
    isOn: Boolean
) {
    view.setImageDrawable(if (isOn) onRes else offRes)
}