package com.example.pottytime.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pottytime.R
import com.example.pottytime.adapters.ToiletListAdapter
import com.example.pottytime.viewmodels.ToiletViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var toiletViewModel: ToiletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ToiletListAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this,2)

        toiletViewModel = ViewModelProvider(this).get(ToiletViewModel::class.java)

        toiletViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })


    }
}
