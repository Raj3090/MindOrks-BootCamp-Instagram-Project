package com.raj.sharephoto.ui.profile.post

import androidx.recyclerview.widget.RecyclerView
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.data.model.UploadedPostData
import com.raj.sharephoto.databinding.PostGridItemBinding
import com.raj.sharephoto.di.component.DaggerViewHolderComponent
import com.raj.sharephoto.di.module.ViewHolderModule
import javax.inject.Inject

class MyPostItemViewHolder(val binding: PostGridItemBinding) :RecyclerView.ViewHolder(binding.root) {

    @Inject
    lateinit var viewModel: MyPostItemViewModel

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


    fun bind(uri: UploadedPostData) {
        viewModel.updateData(uri)
        binding.viewModel=viewModel
        binding.executePendingBindings()
    }

}