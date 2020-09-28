package com.example.pottytime.validators

import android.widget.EditText
import org.w3c.dom.Text

class CodeValidator(private val text: String) : ValidatorInterface {

    override fun validate(): Boolean {

        if(text.isEmpty())
        {
            return false;
        }

        try
        {
            val value : Int = text.toInt()
        }
        catch (e: NumberFormatException)
        {
            return  false;
        }

        return true;

    }
}