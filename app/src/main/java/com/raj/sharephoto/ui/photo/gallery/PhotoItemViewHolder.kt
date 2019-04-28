package com.raj.sharephoto.ui.photo.gallery

import androidx.recyclerview.widget.RecyclerView
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.data.model.Post
import com.raj.sharephoto.databinding.PhotoGridItemBinding
import com.raj.sharephoto.di.component.DaggerViewHolderComponent
import com.raj.sharephoto.di.module.ViewHolderModule
import javax.inject.Inject

class PhotoItemViewHolder(val binding: PhotoGridItemBinding) :RecyclerView.ViewHolder(binding.root) {

    @Inject
    lateinit var viewModel: PhotoItemViewModel

    init {
        DaggerViewHolderComponent.builder()
            .applicationComponent(
                (binding.root.context.applicationContext as InstagramApplication)
                    .applicationComponent
            )
            .viewHolderModule(ViewHolderModule(this))
            .build()
            .inject(this)
    }


    fun bind(uri: String) {
        viewModel.updateData(uri)
        binding.viewModel=viewModel
        binding.executePendingBindings()
    }

}