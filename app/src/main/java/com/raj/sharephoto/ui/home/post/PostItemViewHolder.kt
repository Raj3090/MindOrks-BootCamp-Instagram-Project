package com.raj.sharephoto.ui.home.post

import androidx.recyclerview.widget.RecyclerView
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.data.model.Post
import com.raj.sharephoto.databinding.ItemViewPostBinding
import com.raj.sharephoto.di.component.DaggerViewHolderComponent
import com.raj.sharephoto.di.module.ViewHolderModule
import com.raj.sharephoto.utils.display.Toaster
import javax.inject.Inject

class PostItemViewHolder(private val binding: ItemViewPostBinding): RecyclerView.ViewHolder(binding.root) {


    @Inject
    lateinit var viewModel: PostItemViewModel

    init {
        DaggerViewHolderComponent.builder()
            .applicationComponent(
                (binding.root.context.applicationContext as InstagramApplication)
                    .applicationComponent
            )
            .viewHolderModule(ViewHolderModule(this))
            .build()
            .inject(this)

        viewModel.messageIdListener = {
            Toaster.show(
                binding.root.context,
                binding.root.context.getString(it)
            )
        }

        viewModel.messageListener = {
            Toaster.show(binding.root.context, it)
        }
    }

    fun bind(data: Post) {
        viewModel.updateData(data)
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }


}