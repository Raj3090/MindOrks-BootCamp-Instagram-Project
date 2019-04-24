package com.raj.sharephoto.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.PhotoFragmentBinding
import com.raj.sharephoto.di.component.DaggerFragmentComponent
import com.raj.sharephoto.di.module.FragmentModule
import javax.inject.Inject

class PhotoFragment : Fragment() {

    companion object {
        const val TAG = "PhotoFragment"

        fun newInstance() : PhotoFragment{
            val args = Bundle()
            val fragment = PhotoFragment()
            fragment.arguments = args
            return fragment
        }
    }


    @Inject
     lateinit var viewModel: PhotoViewModel

    lateinit var binding: PhotoFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpDependencies();
        super.onCreate(savedInstanceState)
        setUpObserver()
    }

    private fun setUpObserver() {

    }

    private fun setUpDependencies() {
        DaggerFragmentComponent
            .builder()
            .applicationComponent((context?.applicationContext as InstagramApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.photo_fragment, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

}
