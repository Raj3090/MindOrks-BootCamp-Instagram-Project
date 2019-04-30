package com.raj.sharephoto.ui.photo.share

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.ActivitySharePhotoBinding
import com.raj.sharephoto.di.component.DaggerActivityComponent
import com.raj.sharephoto.di.module.ActivityModule
import javax.inject.Inject

class SharePhotoActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: SharePhotoViewModel

    lateinit var binding : ActivitySharePhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        insertDependencies()
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_share_photo)
        binding.setLifecycleOwner(this)
        binding.viewModel=viewModel

        val noteId = intent?.extras?.getString("ImageUri")
        noteId?.let {
            viewModel.setCurrentUri(it)
        }
    }

    private fun insertDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as InstagramApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }
}
