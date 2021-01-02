package com.example.pottytime

import org.junit.Test
import org.junit.Assert.*
import com.example.pottytime.validators.CodeValidator
import com.example.pottytime.validators.LocationValidator
import com.example.pottytime.validators.NameValidator

class ValidatorUnitTests {

    @Test
    fun nameValidator_notEmpty(){

        val result = NameValidator("Peti").validate();
        assertEquals(true,result);
    }

    @Test
    fun nameValidator_empty() {

        val result = NameValidator("").validate();
        assertEquals(false, result);
    }

    @Test
    fun codeValidator_notEmpty(){

        val result = CodeValidator("testCode").validate();
        assertEquals(true,result);
    }

    @Test
    fun codeValidator_empty(){

        val result = CodeValidator("").validate();
        assertEquals(false,result);
    }

    @Test
    fun locationValidator_notEmptyAndDouble(){

        val result = LocationValidator("2.1122","3.11123123").validate();

        assertEquals(true,result);
    }

    @Test
    fun locationValidator_latEmpty(){

        val result = LocationValidator("","300").validate();

        assertEquals(false,result);
    }

    @Test
    fun locationValidator_lonEmpty(){

        val result = LocationValidator("2.1","").validate();

        assertEquals(false,result);
    }

    @Test
    fun locationValidator_bothEmpty(){

        val result = LocationValidator("","").validate();

        assertEquals(false,result);
    }

    @Test
    fun locationValidator_notDouble(){

        val result = LocationValidator("asd","ASDASD").validate();

        assertEquals(false,result);
    }






}