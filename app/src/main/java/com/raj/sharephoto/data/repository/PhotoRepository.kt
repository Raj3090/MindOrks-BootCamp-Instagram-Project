package com.raj.sharephoto.data.repository

import android.content.ContentResolver
import android.provider.MediaStore
import com.raj.sharephoto.data.model.User
import com.raj.sharephoto.data.local.db.DatabaseService
import com.raj.sharephoto.data.model.Post
import com.raj.sharephoto.data.remote.NetworkService
import com.raj.sharephoto.data.remote.request.PostLikeModifyRequest
import io.reactivex.Single
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val contentResolver: ContentResolver
) {

    fun getAllPhotosUriFromGallery() :
            ArrayList<String> {

        val listOfAllImages = ArrayList<String>()

        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)

        cursor?.let {
            while (cursor.moveToNext()) {
                val absolutePathOfImage =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                listOfAllImages.add(absolutePathOfImage);
            }
            cursor.close()
        }

        return listOfAllImages

    }


}