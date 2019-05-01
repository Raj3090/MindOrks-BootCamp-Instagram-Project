package com.raj.sharephoto.ui.profile.edit

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.ActivityEditProfileBinding
import com.raj.sharephoto.di.component.DaggerActivityComponent
import com.raj.sharephoto.di.module.ActivityModule
import com.raj.sharephoto.utils.common.Event
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class EditProfileActivity : AppCompatActivity() {

    companion object {
        val REQUEST_TAKE_PHOTO = 100
        val GALLERY = 200
    }

    @Inject
    lateinit var viewModel: EditProfileViewModel

    lateinit var binding:ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        insertDependencies()
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        binding.setLifecycleOwner(this)
        binding.viewModel=viewModel
        viewModel.loadProfileData()
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

        viewModel.profileUpdateSuccessfullNavigation.observe(this, Observer<Event<Bundle>> {
            it.getIfNotHandled()?.run {
                val intent=Intent()
                setResult(Activity.RESULT_OK,intent)
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
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }



    private fun takePhotoFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }


    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath =absolutePath

        }
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
                }

            }

        } else if (requestCode == REQUEST_TAKE_PHOTO) {
            currentPhotoPath?.let {
                viewModel.uri=currentPhotoPath
            }
            Glide.with(this).load(currentPhotoPath).into(profilePic);
        }
    }

    fun getRealPathFromUri(contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = contentResolver.query(contentUri, proj, null, null, null)
            assert(cursor != null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }

}
