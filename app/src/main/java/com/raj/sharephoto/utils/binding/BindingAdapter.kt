package com.raj.sharephoto.utils.binding

import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
import com.mindorks.bootcamp.instagram.utils.common.Status
import com.raj.sharephoto.R
import com.raj.sharephoto.ui.main.BottomMenuNavigationListener
import com.raj.sharephoto.utils.common.GlideHelper
import com.raj.sharephoto.utils.common.LoadMoreListener
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

@BindingAdapter("app:onNavigationItemSelected")
fun onNavigationItemSelected(view: BottomNavigationView, listener: BottomMenuNavigationListener) {
    view.setOnNavigationItemSelectedListener { listener.onMenuSelected(it.itemId) }
}

@BindingAdapter("app:url", "app:headers")
fun loadProtectedImage(view: ImageView, url: String?, headers: Map<String, String>) {
    url?.let {
        Glide.with(view.context).load(GlideHelper.getProtectedUrl(it, headers)).into(view)
    }
}

@BindingAdapter("app:onLoadMore")
fun onLoadMore(view: RecyclerView, listener: LoadMoreListener) {
    view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            view.layoutManager?.run {
                if (this is LinearLayoutManager
                    && itemCount > 0
                    && itemCount == findLastVisibleItemPosition() + 1
                ) listener.onLoadMore()
            }
        }
    })
}

@BindingAdapter("app:url", "app:headers", "app:circular")
fun loadProtectedImageCircular(view: ImageView, url: String?, headers: Map<String, String>, circular: Boolean) {
    url?.let {
        val requestBuilder = Glide.with(view.context).load(GlideHelper.getProtectedUrl(it, headers))
        if (circular) requestBuilder.apply(RequestOptions.circleCropTransform())
        requestBuilder.into(view)
    }
}

@BindingAdapter("app:url", "app:headers", "app:placeholderWidth", "app:placeholderHeight")
fun loadProtectedImage(
    view: ImageView, url: String?, headers: Map<String, String>,
    placeholderWidth: Int?, placeholderHeight: Int?
) {
    url?.let {
        val requestOptions = Glide.with(view.context)
            .load(GlideHelper.getProtectedUrl(it, headers))
        if (placeholderWidth !== null && placeholderHeight !== null) {
            val params = view.layoutParams as ViewGroup.LayoutParams
            params.width = placeholderWidth
            params.height = placeholderHeight
            view.layoutParams = params
            requestOptions
                .apply(RequestOptions.overrideOf(placeholderWidth, placeholderHeight))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_photo))
        }
        requestOptions.into(view)
    }
}