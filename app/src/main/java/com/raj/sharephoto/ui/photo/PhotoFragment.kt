package com.raj.sharephoto.ui.photo

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.databinding.PhotoFragmentBinding
import com.raj.sharephoto.di.component.DaggerFragmentComponent
import com.raj.sharephoto.di.module.FragmentModule
import java.io.File
import javax.inject.Inject
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide
import com.raj.sharephoto.ui.photo.gallery.PhotoAdapter
import com.raj.sharephoto.utils.display.ManagePermissions
import kotlinx.android.synthetic.main.photo_fragment.*


class PhotoFragment : Fragment() {

    companion object {
        const val TAG = "PhotoFragment"
        const val PERMISSION_CODE = 1000

        fun newInstance(): PhotoFragment {
            val args = Bundle()
            val fragment = PhotoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var viewModel: PhotoViewModel

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    @Inject
    lateinit var gridManager: GridLayoutManager

    lateinit var binding: PhotoFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        setUpDependencies();
        super.onCreate(savedInstanceState)
        setUpObserver()
    }

    private fun setUpObserver() {
        viewModel.uris.observe(this, Observer {
            it.data?.run {

                photoAdapter.appendData(it.data)
            }
        })
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
        binding = DataBindingUtil.inflate(inflater, com.raj.sharephoto.R.layout.photo_fragment, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        photo_grid.layoutManager = gridManager
        photo_grid.adapter = photoAdapter

        if(checkAndRequestPermissions()){
            viewModel.loadImagesUri()
        }

    }


    private fun checkAndRequestPermissions(): Boolean {
        val camerapermission =
            ContextCompat.checkSelfPermission(activity?.applicationContext!!, Manifest.permission.CAMERA)
        val writepermission = ContextCompat.checkSelfPermission(
            activity?.applicationContext!!,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )


        val listPermissionsNeeded = ArrayList<String>()

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toTypedArray(), PERMISSION_CODE)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {

                val perms = HashMap<String, Int>()
                // Initialize the map with both permissions
                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED

                // Fill with actual results from user
                if (grantResults.size > 0) {
                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]
                    // Check for both permissions
                    if (perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
                        && perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                    )
                        viewModel.loadImagesUri()
                    //else any one or both the permissions are not granted
                } else {
                    //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                    //                        // shouldShowRequestPermissionRationale will return true
                    //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.CAMERA)
                        || ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    ) {
                        showDialogOK("Service Permissions are required for this app",
                            DialogInterface.OnClickListener { dialog, which ->
                                when (which) {
                                    DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                    DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
                                    // proceed with logic by disabling the related features or quit the app.

                                }
                            })
                    } else {
                        explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?")
                    }

                }
            }
        }
    }


    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(activity?.applicationContext!!)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }

    private fun explain(msg: String) {
        val dialog = AlertDialog.Builder(activity?.applicationContext!!)
        dialog.setMessage(msg)
            .setPositiveButton("Yes") { paramDialogInterface, paramInt ->
                //  permissionsclass.requestPermission(type,code);
                startActivity(
                    Intent(
                        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:com.raj.sharephoto")
                    )
                )
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt ->
                paramDialogInterface.dismiss()
            }
        dialog.show()
    }

}

