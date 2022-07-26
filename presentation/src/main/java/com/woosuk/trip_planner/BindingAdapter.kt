package com.woosuk.trip_planner

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("fit_center")
    fun fitCenter(button: Button,text:String){
        val width=button.width
        Log.d("가로크기",width.toString())

    }

    @JvmStatic
    @BindingAdapter("image")
    fun setImage(view: ImageView, url: String) {
        val context = view.context
        Glide.with(context)
            .load(url)
            .fitCenter()
            .into(view)
    }
}