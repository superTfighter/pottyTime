package com.example.pottytime.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pottytime.R
import com.example.pottytime.adapters.ToiletListAdapter
import com.example.pottytime.data.Toilet
import com.example.pottytime.database.ToiletDatabase
import com.example.pottytime.fragments.ToiletDetailsFragment
import com.example.pottytime.fragments.ToiletListFragment
import com.example.pottytime.repositories.ToiletRepository
import com.example.pottytime.viewmodels.ToiletViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.Serializable

class MainActivity : AppCompatActivity() , ToiletListAdapter.OnToiletSelected {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissionsIfNecessary( arrayOf<String>(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
        ));

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, ToiletListFragment.newInstance(), "toiletList")
                .commit()
        }
    }

    @Override
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun requestPermissionsIfNecessary(permissions : Array<String> ) {

        for( permission in permissions)
        {
            if (ContextCompat.checkSelfPermission(this@MainActivity,permission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,permission)) {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), 1)
                } else {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), 1)
                }
            }
        }
    }


    //Change Fragment
    override fun onToiletSelected(toilet: Toilet) {

        val detailsFragment =
            ToiletDetailsFragment.newInstance(toilet)
        supportFragmentManager
            .beginTransaction()
            // 2
            .replace(R.id.root_layout, detailsFragment, "toiletDetails")
            // 3
            .addToBackStack(null)
            .commit()
    }

}
