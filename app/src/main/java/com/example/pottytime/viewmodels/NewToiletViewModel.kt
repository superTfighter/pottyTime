package com.example.pottytime.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.pottytime.activities.MainActivity
import com.example.pottytime.activities.NewToiletActivity
import com.example.pottytime.data.LocationData
import com.example.pottytime.data.Toilet
import com.example.pottytime.data.ToiletType
import com.example.pottytime.database.ToiletDatabase
import com.example.pottytime.repositories.ToiletRepository
import com.example.pottytime.validators.CodeValidator
import com.example.pottytime.validators.LocationValidator
import com.example.pottytime.validators.NameValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewToiletViewModel(application: Application) : AndroidViewModel(application) {

    var isGPSEnabled = false
    var isPermissionGranted = false
    var name: MutableLiveData<String> = MutableLiveData()
    var lat: MutableLiveData<String> = MutableLiveData()
    var lon: MutableLiveData<String> = MutableLiveData()
    var code: MutableLiveData<String> = MutableLiveData()

    private lateinit var type : ToiletType
    private val locationData = LocationData(application)
    private val repository: ToiletRepository

    init {
        val toiletDao = ToiletDatabase.getDatabase(application, viewModelScope).toiletDao()
        repository = ToiletRepository(toiletDao)
    }

    fun onSaveButtonClick(v : View)
    {
        val activity : NewToiletActivity = v.context as NewToiletActivity
        val replyIntent = Intent()

        val nameString = name.value.toString()
        val latString : String = lat.value.toString()
        val lonString : String = lon.value.toString()
        val codeString : String = code.value.toString()

        if (NameValidator(nameString).validate() && LocationValidator(latString,lonString).validate() && CodeValidator(codeString).validate()) {

            val t = Toilet(null,nameString,latString.toDouble(),lonString.toDouble(),codeString, type)
            this.insert(t)

            activity.setResult(Activity.RESULT_OK, replyIntent)
            activity.finish()
        }else if(NameValidator(nameString).validate() && CodeValidator(lonString).validate() && latString.isEmpty() && lonString.isEmpty())
        {
            val t = Toilet(null,nameString,null,null,codeString, type)
            this.insert(t)

            activity.setResult(Activity.RESULT_OK, replyIntent)
            activity.finish()
        }
        else
        {
            Log.d("Loc validation" ,LocationValidator(latString,lonString).validate().toString())

            if(!NameValidator(nameString).validate())
            {
                activity.showToaster("Please enter a valid name!")
            }
            else if(!CodeValidator(codeString).validate())
            {
                activity.showToaster("Please enter a valid code!")
            }else if(!LocationValidator(latString,lonString).validate())
            {
                activity.showToaster("Please enter a valid location!")
            }

        }

    }

    fun onSaveLocationClick(v : View)
    {
        val activity : NewToiletActivity = v.context as NewToiletActivity

        if(!isGPSEnabled)
        {
            activity.showToaster("GPS is not enabled!")
        }
        else
        {
            if(isPermissionGranted){

              locationData.observe(activity, Observer {
                  this.lon.value = it.longitude.toString()
                  this.lat.value = it.latitude.toString()
              })

            }else{
                activity.showToaster("Permission not granted or you need to restart the app!")
            }

        }

    }

    fun onSelectItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long)
    {
        type = ToiletType.values()[pos]
    }

    private fun insert(toilet: Toilet) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(toilet)
    }


}