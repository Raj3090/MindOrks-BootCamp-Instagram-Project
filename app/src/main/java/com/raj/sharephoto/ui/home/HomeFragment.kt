package com.raj.sharephoto.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.raj.sharephoto.InstagramApplication

import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.HomeFragmentBinding
import com.raj.sharephoto.di.component.DaggerFragmentComponent
import com.raj.sharephoto.di.module.FragmentModule
import com.raj.sharephoto.ui.home.post.PostAdapter
import com.raj.sharephoto.utils.display.Toaster
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    companion object {

        const val TAG = "HomeFragment"

        fun newInstance() : HomeFragment{

            val fragment=HomeFragment()
            val args=Bundle()
            fragment.arguments=args
            return fragment

        }
    }

    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var postsAdapter: PostAdapter

    lateinit var binding: HomeFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setUpObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.home_fragment, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        feed.layoutManager=linearLayoutManager
        feed.adapter=postsAdapter
        viewModel.onViewCreated()
    }


    private fun setUpObserver() {
        viewModel.post.observe(this, Observer {
            it.data?.run {
                postsAdapter.appendData(this)
            }
        })

        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
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

    private fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    private fun showMessage(message: String) = context?.let { Toaster.show(it, message) }


}
