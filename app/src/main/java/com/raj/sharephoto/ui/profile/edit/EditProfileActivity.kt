package com.raj.sharephoto.ui.profile.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.ActivityEditProfileBinding
import com.raj.sharephoto.di.component.DaggerActivityComponent
import com.raj.sharephoto.di.module.ActivityModule
import javax.inject.Inject

class EditProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var editProfileViewModel: EditProfileViewModel

    lateinit var binding:ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        insertDependencies()
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_edit_profile)
        binding.setLifecycleOwner(this)
        binding.viewModel=editProfileViewModel
    }

    private fun insertDependencies() {
     DaggerActivityComponent.builder()
         .applicationComponent((application as InstagramApplication).applicationComponent)
         .activityModule(ActivityModule(this))
         .build().inject(this)

    }

    private fun setUpObserver(){

    }
}
