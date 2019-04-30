package com.raj.sharephoto.ui.profile.edit

import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.databinding.ActivityEditProfileBinding
import com.raj.sharephoto.di.component.DaggerActivityComponent
import com.raj.sharephoto.di.module.ActivityModule
import com.raj.sharephoto.utils.common.Event
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.IOException
import javax.inject.Inject


class EditProfileActivity : AppCompatActivity() {

    companion object {
        val CAMERA = 100
        val GALLERY = 200
    }

    @Inject
    lateinit var viewModel: EditProfileViewModel

    lateinit var binding:ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        insertDependencies()
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, com.raj.sharephoto.R.layout.activity_edit_profile)
        binding.setLifecycleOwner(this)
        binding.viewModel=viewModel
        setUpObserver()
    }

    private fun insertDependencies() {
     DaggerActivityComponent.builder()
         .applicationComponent((application as InstagramApplication).applicationComponent)
         .activityModule(ActivityModule(this))
         .build().inject(this)

    }

    private fun setUpObserver(){
        viewModel.profileNavigation.observe(this, Observer<Event<Bundle>> {
            it.getIfNotHandled()?.run {
               finish()
            }
        })

        viewModel.selectProfilePhotoDialog.observe(this, Observer<Event<Bundle>> {
            it.getIfNotHandled()?.run {
                showPictureDialog();
            }
        })


    }


    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems,
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    0 -> choosePhotoFromGallary()
                    1 -> takePhotoFromCamera()
                }
            })
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            return
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                val contentURI = data.data
                contentURI?.let {
                    viewModel.uri=getRealPathFromUri(contentURI)
                }
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    profilePic.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@EditProfileActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        } else if (requestCode == CAMERA) {
            val thumbnail = data?.extras?.get("data") as Bitmap
            profilePic.setImageBitmap(thumbnail)
            Toast.makeText(this@EditProfileActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun getRealPathFromUri(contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = contentResolver.query(contentUri, proj, null, null, null)
            assert(cursor != null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            return cursor!!.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }

}
