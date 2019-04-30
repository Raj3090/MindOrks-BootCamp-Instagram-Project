package com.raj.sharephoto.data.repository

import com.raj.sharephoto.data.model.User
import com.raj.sharephoto.data.local.db.DatabaseService
import com.raj.sharephoto.data.model.Post
import com.raj.sharephoto.data.remote.NetworkService
import com.raj.sharephoto.data.remote.request.PostLikeModifyRequest
import com.raj.sharephoto.data.remote.request.PostRequest
import com.raj.sharephoto.data.remote.response.PostResponse
import com.raj.sharephoto.data.remote.response.UploadPhotoResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {


    fun fetchHomePostList(firstPostId: String?, lastPostId: String?, user: User): Single<List<Post>> {
        return networkService.doHomePostListCall(
            firstPostId,
            lastPostId,
            user.id,
            user.accessToken
        ).map { it.data }
    }

    fun makeLikePost(post: Post, user: User): Single<Post> {
        return networkService.doPostLikeCall(
            PostLikeModifyRequest(post.id),
            user.id,
            user.accessToken
        ).map {
            post.likedBy?.apply {
                this.find { postUser -> postUser.id == user.id } ?: this.add(
                    Post.User(
                        user.id,
                        user.name,
                        user.profilePicUrl
                    )
                )
            }
            return@map post
        }
    }

    fun makeUnlikePost(post: Post, user: User): Single<Post> {
        return networkService.doPostUnlikeCall(
            PostLikeModifyRequest(post.id),
            user.id,
            user.accessToken
        ).map {
            post.likedBy?.apply {
                this.find { postUser -> postUser.id == user.id }?.let { this.remove(it) }
            }
            return@map post
        }
    }

    fun uploadPostImage(user: User,body: MultipartBody.Part): Single<UploadPhotoResponse> {
        return networkService.doPostUploadImageCall(
            body,
            user.id,
            user.accessToken
        )
    }


    fun uploadPost(user: User,body: PostRequest): Single<PostResponse> {
        return networkService.doUploadPostCall(
            body,
            user.id,
            user.accessToken
        )
    }

}