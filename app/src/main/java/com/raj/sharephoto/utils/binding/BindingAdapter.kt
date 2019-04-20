package com.raj.sharephoto.utils.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.mindorks.bootcamp.instagram.utils.common.Status
import com.raj.sharephoto.utils.common.Resource

@BindingAdapter("app:loadImage")
fun loadImage(view: ImageView, url: String?) {
    url?.let { Glide.with(view.context).load(it).into(view) }
}

@BindingAdapter("app:textInputLayoutValidation")
fun textInputLayoutValidation(view: TextInputLayout, resourceLiveData: LiveData<Resource<Int>>) {
    resourceLiveData.value?.run {
        when (status) {
            Status.ERROR -> view.error = data?.let { view.context.getString(it) }
            else -> view.isErrorEnabled = false
        }
    }
}