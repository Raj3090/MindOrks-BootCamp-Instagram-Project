package com.raj.sharephoto.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.raj.sharephoto.InstagramApplication

import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.ProfileFragmentBinding
import com.raj.sharephoto.di.component.DaggerFragmentComponent
import com.raj.sharephoto.di.module.FragmentModule
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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

    }

    private fun setUpObservers() {


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
