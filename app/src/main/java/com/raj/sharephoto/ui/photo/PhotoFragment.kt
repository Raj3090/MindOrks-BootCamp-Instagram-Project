package com.raj.sharephoto.ui.photo

import android.Manifest
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

        fun newInstance() : PhotoFragment{
            val args = Bundle()
            val fragment = PhotoFragment()
            fragment.arguments = args
            return fragment
        }
    }


    @Inject
    lateinit var viewModel: PhotoViewModel

    @Inject
    lateinit var photoAdapter:PhotoAdapter

    @Inject
    lateinit var gridManager:GridLayoutManager

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




    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                } else {
                    viewModel.loadImagesUri()
                }
            }
        }
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
        photo_grid.layoutManager=gridManager
        photo_grid.adapter=photoAdapter

        setupPermissions()


    }

    private fun setupPermissions() {
        // Initialize a list of required permissions to request runtime
        val list = listOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        // Initialize a new instance of ManagePermissions class
        val  managePermissions = ManagePermissions(this.requireActivity(),list,PERMISSION_CODE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (managePermissions.isPermissionsGranted() != PackageManager.PERMISSION_GRANTED) {
                managePermissions.showAlert()
            } else {
                viewModel.loadImagesUri()
            }
        }
    }

}
