package com.raj.sharephoto.ui.profile

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.raj.sharephoto.InstagramApplication

import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.ProfileFragmentBinding
import com.raj.sharephoto.di.component.DaggerFragmentComponent
import com.raj.sharephoto.di.module.FragmentModule
import com.raj.sharephoto.ui.main.MainActivity
import com.raj.sharephoto.ui.photo.gallery.PhotoAdapter
import com.raj.sharephoto.ui.profile.edit.EditProfileActivity
import com.raj.sharephoto.ui.profile.post.MyPostAdapter
import com.raj.sharephoto.utils.common.Event
import kotlinx.android.synthetic.main.photo_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.*
import javax.inject.Inject

class ProfileFragment : Fragment() {

    companion object {
        const val TAG = "ProfileFragment"
        const val UPDATE_PROFILE_REQUEST = 101
        fun newInstance() : ProfileFragment {
            val fragment=ProfileFragment()
            val args=Bundle()
            fragment.arguments=args
            return fragment
        }
    }

    @Inject
    lateinit var viewModel: ProfileViewModel

    lateinit var binding: ProfileFragmentBinding


    @Inject
    lateinit var photoAdapter: MyPostAdapter

    @Inject
    lateinit var gridManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setUpObservers()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.profile_fragment, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel=viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        my_post_grid.layoutManager=gridManager
        my_post_grid.adapter=photoAdapter

        viewModel.fetchProfileInfo()
    }

    private fun setUpObservers() {

        viewModel.editNavigation.observe(this, Observer<Event<Bundle>> {
            it.getIfNotHandled()?.run {
                startActivityForResult(Intent(activity?.applicationContext, EditProfileActivity::class.java),UPDATE_PROFILE_REQUEST)
            }
        })

        viewModel.urls.observe(this, Observer {
            it?.data?.let {
                photoAdapter.appendData(it)
            }
        })

    }

    private fun injectDependencies() {
        DaggerFragmentComponent
            .builder()
            .applicationComponent((context?.applicationContext as InstagramApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()
            .inject(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        if (requestCode == UPDATE_PROFILE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                viewModel.fetchProfileInfo()
            }
        }
    }

}
