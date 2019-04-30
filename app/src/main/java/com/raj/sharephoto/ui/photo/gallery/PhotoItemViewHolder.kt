package com.raj.sharephoto.ui.photo.gallery

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.data.model.Post
import com.raj.sharephoto.databinding.PhotoGridItemBinding
import com.raj.sharephoto.di.component.DaggerViewHolderComponent
import com.raj.sharephoto.di.module.ViewHolderModule
import com.raj.sharephoto.ui.main.MainActivity
import com.raj.sharephoto.ui.photo.share.SharePhotoActivity
import com.raj.sharephoto.utils.display.Toaster
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


        viewModel.imageUri = {
            val intent=Intent(binding.root.context, SharePhotoActivity::class.java)
            intent.putExtra("ImageUri",it)
            binding.root.context.startActivity(intent)
        }





    }


    fun bind(uri: String) {
        viewModel.updateData(uri)
        binding.viewModel=viewModel
        binding.executePendingBindings()
    }

}