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
import androidx.lifecycle.viewModelScope
import com.example.pottytime.activities.MainActivity
import com.example.pottytime.activities.NewToiletActivity
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

    private val repository: ToiletRepository
    var name: String = "";
    var lat: String = "";
    var lon: String = "";
    var code: String = "";
    private lateinit var type : ToiletType

    init {
        val wordsDao = ToiletDatabase.getDatabase(application, viewModelScope).toiletDao()
        repository = ToiletRepository(wordsDao)
    }

    fun onSaveButtomClick(v : View)
    {
        Log.d("Name value: ", name);

        val activity : NewToiletActivity = v.context as NewToiletActivity;
        val replyIntent = Intent()

        if (NameValidator(name).validate() && LocationValidator(lat).validate() && LocationValidator(lon).validate() && CodeValidator(code).validate()) {

            val t = Toilet(null,name,lat.toDouble(),lon.toDouble(),code, type)
            this.insert(t);

            activity.setResult(Activity.RESULT_OK, replyIntent)
            activity.finish()

        }
        else
        {
            //TODO: ALERT USER
            Log.d("Name validation" ,NameValidator(name).validate().toString());
            Log.d("Loc1 validation" ,LocationValidator(lat).validate().toString());
            Log.d("Loc2 validation" ,LocationValidator(lon).validate().toString());
            Log.d("Code validation" ,CodeValidator(code).validate().toString());

        }

    }

    fun onSelectItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long)
    {
        type = ToiletType.values()[pos];
    }

    fun insert(toilet: Toilet) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(toilet)
    }


}