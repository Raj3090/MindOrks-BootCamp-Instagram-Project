package com.raj.sharephoto.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.ActivityMainBinding
import com.raj.sharephoto.di.component.DaggerActivityComponent
import com.raj.sharephoto.di.module.ActivityModule
import com.raj.sharephoto.ui.home.HomeFragment
import com.raj.sharephoto.ui.photo.PhotoFragment
import com.raj.sharephoto.ui.profile.ProfileFragment
import com.raj.sharephoto.utils.common.Event
import com.raj.sharephoto.utils.rx.RxBus
import com.raj.sharephoto.utils.rx.SchedulerProvider
import javax.inject.Inject
import com.raj.sharephoto.utils.rx.events.PostSubmit
import io.reactivex.functions.Consumer


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    lateinit var binding: ActivityMainBinding
    private var activeFragment: Fragment? = null

     var updatePostList:Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies();
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.setLifecycleOwner(this)
        binding.viewModel=mainViewModel
        setUpOberver()
        setupView()


    }

    private fun setUpOberver() {

        mainViewModel.navigation.observe(this, Observer {
            it.getIfNotHandled()?.run {
                when (this) {
                    BottomMenuNavigation.HOME -> showHome()
                    BottomMenuNavigation.PROFILE -> showProfile()
                    BottomMenuNavigation.ADD_PHOTOS -> showAddPhoto()
                    else -> showHome()
                }
            }
        })


    }

    private fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as InstagramApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)

    }


    private fun setupView() {
        findViewById<BottomNavigationView>(R.id.bottomNavigation).itemIconTintList = null
        mainViewModel.onViewCreated()
    }

    private fun showHome() {
        if (activeFragment is HomeFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(HomeFragment.TAG) as HomeFragment?

        if (fragment == null) {
            fragment = HomeFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, HomeFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment

        if(updatePostList) {
            (activeFragment as HomeFragment).viewModel.onViewCreated()
            updatePostList=false
        }
    }

    private fun showProfile() {
        if (activeFragment is ProfileFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(ProfileFragment.TAG) as ProfileFragment?

        if (fragment == null) {
            fragment = ProfileFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, ProfileFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment



    }

    private fun showAddPhoto() {
        if (activeFragment is PhotoFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(PhotoFragment.TAG) as PhotoFragment?

        if (fragment == null) {
            fragment = PhotoFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, PhotoFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        updatePostList=true
        findViewById<BottomNavigationView>(R.id.bottomNavigation).selectedItemId=R.id.itemHome
    }
}
