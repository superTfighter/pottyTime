package com.example.pottytime.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.pottytime.R
import com.example.pottytime.data.Toilet
import com.example.pottytime.data.ToiletType

class NewToiletActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var lat: EditText
    private lateinit var lon: EditText
    private lateinit var type : Spinner

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_toilet)


        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()

            name = findViewById(R.id.name)
            lat = findViewById(R.id.lat)
            lon = findViewById(R.id.lon)
            type = findViewById(R.id.spinner)

            type.getSelectedItem().toString();

            if (TextUtils.isEmpty(name.text) || TextUtils.isEmpty(lat.text) || TextUtils.isEmpty(lon.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {

                val name = name.text.toString()
                val lat = lat.text.toString()
                val lon = lon.text.toString()
                val index : Int = type.getSelectedItemPosition();


                var t : Toilet = Toilet(null,name,lat.toDouble(),lon.toDouble(),"0123456", ToiletType.values()[index])

                replyIntent.putExtra("Toilet", t.javaClass)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
        val spinner: Spinner = findViewById(R.id.spinner)
        val listOfString = ArrayList<String>();

        ToiletType.values().forEach {
            listOfString.add(it.toString());
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item)
        adapter.addAll(listOfString)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = adapter;


    }

    companion object {
        const val Name = "com.example.android.wordlistsql.REPLY"
        const val Latitude = "com.example.android.wordlistsql.REPLY"
        const val Longitude = "com.example.android.wordlistsql.REPLY"
        const val toiletType  = "com.example.android.wordlistsql.REPLY"
    }
}


