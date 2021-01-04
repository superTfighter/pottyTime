package com.example.pottytime.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pottytime.R
import com.example.pottytime.activities.NewToiletActivity
import com.example.pottytime.adapters.ToiletListAdapter
import com.example.pottytime.data.Toilet
import com.example.pottytime.viewmodels.ToiletViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ToiletListFragment() : Fragment() , ToiletListAdapter.OnToiletLongSelected {

    private lateinit var toiletViewModel : ToiletViewModel
    private lateinit var listener: ToiletListAdapter.OnToiletSelected
    private lateinit var listener2: ToiletListAdapter.OnToiletLongSelected

    companion object {
        fun newInstance(): ToiletListFragment {
            return ToiletListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ToiletListAdapter.OnToiletSelected) {
            listener = context;
        } else {
            throw ClassCastException(
                "$context must implement OnToiletSelected."
            )
        }

        listener2 = this;
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_toilet_list, container, false)
        val activity = activity as Context

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)

        val adapter = ToiletListAdapter(activity)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        adapter.clickListener = this.listener;
        adapter.longClickListener = this.listener2;

        toiletViewModel = ViewModelProvider(requireActivity()).get(ToiletViewModel::class.java)

        toiletViewModel.allToilets.observe(requireActivity(),
            Observer {
                toilets -> toilets?.let {
                adapter.setToilets(it)
            }
        })

        val fab = view.findViewById<ExtendedFloatingActionButton>(R.id.extended_fab)
        fab?.setOnClickListener {
            val intent = Intent(requireActivity(), NewToiletActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onToiletLongSelected(toilet: Toilet) {
        toiletViewModel.deleteToilet(toilet);
    }

}