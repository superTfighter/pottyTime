package com.example.pottytime.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pottytime.R
import com.example.pottytime.adapters.ToiletListAdapter
import com.example.pottytime.data.Toilet
import com.example.pottytime.viewmodels.ToiletViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var toiletViewModel: ToiletViewModel
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ToiletListAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this,2)

        toiletViewModel = ViewModelProvider(this).get(ToiletViewModel::class.java)

        toiletViewModel.allWords.observe(this, Observer { toilets ->
            // Update the cached copy of the words in the adapter.
            toilets?.let { adapter.setToilets(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewToiletActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK)
        {
            val toilet : Serializable? = data?.getSerializableExtra("Toilet");

            //toilet is Toilet?;

            Log.w("New Toilet", toilet.toString());

        }
        else
        {
            /*Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()*/
        }
    }
}
