package com.example.pottytime.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pottytime.R
import com.example.pottytime.data.ToiletType
import com.example.pottytime.databinding.ActivityNewToiletBinding
import com.example.pottytime.viewmodels.NewToiletViewModel

class NewToiletActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityNewToiletBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_new_toilet)

        val binding: ActivityNewToiletBinding = DataBindingUtil.setContentView(this,R.layout.activity_new_toilet);
        binding.lifecycleOwner = this;

        // Assign the component to a property in the binding class.
        binding.viewmodel = NewToiletViewModel(application)
        createSpinner(binding);

        /*
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()

            type.getSelectedItem().toString();


            if (NameValidator(name).validate() || LocationValidator(lat).validate() || LocationValidator(lon).validate() || CodeValidator(code).validate()) {

            }
            else
            {

                val name = name.text.toString()
                val lat = lat.text.toString()
                val lon = lon.text.toString()
                val index : Int = type.getSelectedItemPosition();

                var t : Toilet = Toilet(null,name,lat.toDouble(),lon.toDouble(),code.text.toString(), ToiletType.values()[index])
                newToiletViewModel.insert(t);

                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }*/



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


