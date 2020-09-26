package com.example.pottytime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pottytime.R
import com.example.pottytime.data.Toilet
import com.example.pottytime.databinding.RecyclerviewItemBinding


class ToiletListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ToiletListAdapter.ToiletViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    private var toilet = emptyList<Toilet>() // Cached copy of words

    inner class ToiletViewHolder constructor (itemView: View,
                                            private val recyclerItemListBinding : RecyclerviewItemBinding
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun setData(toilet : Toilet) {
            recyclerItemListBinding.toilet = toilet;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToiletViewHolder {

        val dataBinding =
            RecyclerviewItemBinding.inflate(layoutInflater, parent, false)

        return ToiletViewHolder(dataBinding.root, dataBinding)
    }

    override fun onBindViewHolder(holder: ToiletViewHolder, position: Int) {
        val current : Toilet = toilet[position]
        holder.setData(current)
    }

    internal fun setWords(words: List<Toilet>) {
        this.toilet = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = toilet.size


}