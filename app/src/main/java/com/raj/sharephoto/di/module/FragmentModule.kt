package com.raj.sharephoto.di.module

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.bootcamp.instagram.utils.ViewModelProviderFactory
import com.raj.sharephoto.data.repository.PostRepository
import com.raj.sharephoto.data.repository.UserRepository
import com.raj.sharephoto.ui.home.HomeViewModel
import com.raj.sharephoto.ui.home.post.PostAdapter
import com.raj.sharephoto.ui.photo.PhotoViewModel
import com.raj.sharephoto.ui.profile.ProfileViewModel
import com.raj.sharephoto.ui.signup.SignUpViewModel
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    fun providePhotoViewModel():PhotoViewModel{

        return PhotoViewModel()

    }

    @Provides
    fun provideProfileViewModel():ProfileViewModel{

        return ProfileViewModel()

    }


    @Provides
    fun provideSignUpViewModelViewModel(schedulerProvider: SchedulerProvider, networkHelper: NetworkHelper,
                                        compositeDisposable: CompositeDisposable, userRepository: UserRepository, postRepository: PostRepository
    ): HomeViewModel =

        ViewModelProviders.of(fragment, ViewModelProviderFactory(HomeViewModel::class){

            HomeViewModel(schedulerProvider,compositeDisposable,networkHelper,userRepository,postRepository)

        }).get(HomeViewModel::class.java)

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context)

    @Provides
    fun providePostsAdapter(): PostAdapter = PostAdapter(ArrayList())

}