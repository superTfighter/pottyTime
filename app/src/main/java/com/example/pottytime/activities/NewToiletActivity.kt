package com.example.pottytime.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pottytime.R
import com.example.pottytime.data.ToiletType
import com.example.pottytime.databinding.ActivityNewToiletBinding
import com.example.pottytime.viewmodels.NewToiletViewModel

class NewToiletActivity : AppCompatActivity() {


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityNewToiletBinding = DataBindingUtil.setContentView(this,R.layout.activity_new_toilet);
        binding.lifecycleOwner = this;

        // Assign the component to a property in the binding class.
        binding.viewmodel = NewToiletViewModel(application)
        createSpinner(binding);
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


