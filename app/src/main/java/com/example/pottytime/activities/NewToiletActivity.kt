package com.example.pottytime.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pottytime.R
import com.example.pottytime.data.ToiletType
import com.example.pottytime.databinding.ActivityNewToiletBinding
import com.example.pottytime.utils.GpsUtil
import com.example.pottytime.viewmodels.NewToiletViewModel

const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101

class NewToiletActivity : AppCompatActivity() {

    private  lateinit var newToiletViewModel : NewToiletViewModel;

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityNewToiletBinding = DataBindingUtil.setContentView(this,R.layout.activity_new_toilet);
        newToiletViewModel =  NewToiletViewModel(application);
        binding.lifecycleOwner = this;

        GpsUtil(this).turnGPSOn(object : GpsUtil.OnGpsListener {

            override fun gpsStatus(isGPSEnable: Boolean) {
               newToiletViewModel.isGPSEnabled = isGPSEnable
            }
        })

        if(!isPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_REQUEST
            )
        }

        newToiletViewModel.isPermissionGranted = isPermissionsGranted();

        createSpinner(binding);

        binding.viewmodel = newToiletViewModel;
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                newToiletViewModel.isGPSEnabled = true
               // invokeLocationAction()
            }
        }
    }

   /* private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> latLong.text = getString(R.string.enable_gps)

            isPermissionsGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() -> latLong.text = getString(R.string.permission_request)

            else -> ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQUEST
            )
        }
    }*/

   /* private fun startLocationUpdate() {
        newToiletViewModel.getLocationData().observe(this, Observer {
            latLong.text =  getString(R.string.latLong, it.longitude, it.latitude)
        })
    }*/

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                //invokeLocationAction()
            }
        }
    }

    fun showToaster(message: String)
    {
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun createSpinner(binding : ActivityNewToiletBinding )
    {
        val spinner: Spinner = binding.spinner;
        val listOfString = ArrayList<String>();
        ToiletType.values().forEach {
            listOfString.add(it.toString());
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item)
        adapter.addAll(listOfString)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = adapter;


    }


}


