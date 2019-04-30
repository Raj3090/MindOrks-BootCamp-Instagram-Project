package com.raj.sharephoto.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.raj.sharephoto.InstagramApplication

import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.ProfileFragmentBinding
import com.raj.sharephoto.di.component.DaggerFragmentComponent
import com.raj.sharephoto.di.module.FragmentModule
import com.raj.sharephoto.ui.main.MainActivity
import com.raj.sharephoto.ui.profile.edit.EditProfileActivity
import com.raj.sharephoto.utils.common.Event
import kotlinx.android.synthetic.main.profile_fragment.*
import javax.inject.Inject

class ProfileFragment : Fragment() {

    companion object {
        const val TAG = "ProfileFragment"
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
         viewModel.fetchProfileInfo()
    }

    private fun setUpObservers() {

       viewModel.name.observe(this , Observer {
           it?.run {
              name.setText(it)
           }
       })

        viewModel.tagline.observe(this , Observer {
            it?.run {
                tagline.setText(it)
            }
        })

        viewModel.editNavigation.observe(this, Observer<Event<Bundle>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(activity?.applicationContext, EditProfileActivity::class.java))
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

}
