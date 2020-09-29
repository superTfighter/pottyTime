package com.example.pottytime.validators

import android.widget.EditText
import org.w3c.dom.Text

class CodeValidator(private val text: String) : ValidatorInterface {

    override fun validate(): Boolean {

        if(text.isEmpty())
        {
            return false;
        }

        return true;
    }
}