package com.raj.sharephoto.ui.home.post

import androidx.databinding.ObservableField
import com.raj.sharephoto.R
import com.raj.sharephoto.data.model.Post
import com.raj.sharephoto.data.remote.Networking
import com.raj.sharephoto.data.repository.PostRepository
import com.raj.sharephoto.data.repository.UserRepository
import com.raj.sharephoto.utils.common.TimeUtils
import com.raj.sharephoto.utils.display.ScreenUtils
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.net.ssl.HttpsURLConnection

class PostItemViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val networkHelper: NetworkHelper,
    var post: Post? = null
) {

    lateinit var messageIdListener: (Int) -> Unit
    lateinit var messageListener: (String) -> Unit

    private val user = userRepository.getCurrentUser()!!
    private val screenWidth = ScreenUtils.getScreenWidth()
    private val screenHeight = ScreenUtils.getScreenHeight()

    val isLiked: ObservableField<Boolean> = ObservableField()
    val likesCount: ObservableField<Int> = ObservableField()

    fun getPlaceholderWidth(): Int = screenWidth

    fun getPlaceholderHeight(): Int = post?.run {
        return@run imageHeight?.let {
            return@let (calculateScaleFactor() * it).toInt()
        }
    } ?: screenHeight / 3

    fun getPostTime() = post?.run {
        return@run TimeUtils.getTimeAgo(createdAt)
    }

    private fun getLikesCount() = post?.run {
        return@run likedBy?.size
    }


    private fun calculateScaleFactor() = post?.run {
        return@run imageWidth?.let {
            return@let screenWidth.toFloat() / imageWidth
        }
    } ?: 1f

    val headers: Map<String, String> = mapOf(
        Pair(
            Networking.HEADER_API_KEY,
            Networking.API_KEY
        ),
        Pair(Networking.HEADER_USER_ID, user.id),
        Pair(Networking.HEADER_ACCESS_TOKEN, user.accessToken)
    )

    private fun getIsLiked(post: Post?) = post?.run {
        return@run likedBy?.let {
            return@let it.find { postUser ->
                postUser.id == this@PostItemViewModel.user.id
            } !== null
        }
    } ?: false

    fun onLikeClick() {
        post?.let { currentPost ->
            if (networkHelper.isNetworkConnected()) {
                val call =
                    if (getIsLiked(currentPost))
                        postRepository.makeUnlikePost(currentPost, user)
                    else
                        postRepository.makeLikePost(currentPost, user)

                compositeDisposable.add(call
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                        { if (currentPost.id == it.id) updateData(it) },
                        { handleNetworkError(it) }
                    )
                )
            } else {
                messageIdListener(R.string.network_connection_error)
            }
        }
    }

    fun updateData(post: Post) {
        this.post = post
        isLiked.set(getIsLiked(post))
        likesCount.set(getLikesCount())
    }

    private fun handleNetworkError(err: Throwable?) =
        err?.let {
            networkHelper.castToNetworkError(it).run {
                when (status) {
                    -1 ->
                        messageIdListener(R.string.network_default_error)
                    0 ->
                        messageIdListener(R.string.server_connection_error)
                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        messageIdListener(R.string.network_internal_error)
                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        messageIdListener(R.string.network_server_not_available)
                    else ->
                        messageListener(message)
                }
            }
        }
}