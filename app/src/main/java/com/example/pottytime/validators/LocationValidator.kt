package com.example.pottytime.validators

import android.widget.EditText

class LocationValidator(private val lat: String, private val lon: String) : ValidatorInterface{

    override fun validate() : Boolean
    {

        if((lat.isEmpty() && lon.isNotEmpty()) || (lat.isNotEmpty() && lon.isEmpty()))
        {
            return false;
        }

        try
        {
            val latVal : Double = lat.toDouble()
            val lonVal : Double = lon.toDouble()
        }
        catch (e: NumberFormatException)
        {
            return  false;
        }

        return true;
    }


}