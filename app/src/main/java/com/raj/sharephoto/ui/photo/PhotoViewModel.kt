package com.raj.sharephoto.ui.photo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raj.sharephoto.data.repository.PhotoRepository
import com.raj.sharephoto.utils.common.Resource

class PhotoViewModel(val photoRepository: PhotoRepository) : ViewModel() {

    val uris: MutableLiveData<Resource<List<String>>> = MutableLiveData<Resource<List<String>>>()

    fun loadImagesUri() {
        uris.postValue(Resource.success(photoRepository.getAllPhotosUriFromGallery()))
    }

}
