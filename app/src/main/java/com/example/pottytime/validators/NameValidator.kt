package com.example.pottytime.validators

import android.widget.EditText

class NameValidator(private val text: String) : ValidatorInterface{

    override fun validate() : Boolean
    {
        if(text.isEmpty())
        {
            return false;
        }

        return true;
    }


}
