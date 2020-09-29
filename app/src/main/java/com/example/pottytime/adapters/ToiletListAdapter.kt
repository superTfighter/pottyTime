package com.example.pottytime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pottytime.R
import com.example.pottytime.data.Toilet
import com.example.pottytime.data.ToiletType
import com.example.pottytime.databinding.RecyclerviewItemBinding
import java.util.function.ToLongBiFunction


class ToiletListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ToiletListAdapter.ToiletViewHolder>() {

    lateinit var listener: OnToiletSelected
    private val layoutInflater = LayoutInflater.from(context)
    private var toilet = emptyList<Toilet>() // Cached copy of words


    inner class ToiletViewHolder constructor (
        itemView: View,
        private val recyclerItemListBinding : RecyclerviewItemBinding) : RecyclerView.ViewHolder(itemView) {

        fun setImage(toiletType: ToiletType?){

            when (toiletType) {
                ToiletType.BURGERKING -> {
                    recyclerItemListBinding.image.setImageResource(R.drawable.burgerking)
                }
                ToiletType.MCDONALDS -> {
                    recyclerItemListBinding.image.setImageResource(R.drawable.mcdonaldshu)
                }
                else -> {
                    recyclerItemListBinding.image.setImageResource(R.drawable.other)
                }
            }
        }

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

        holder.setImage(current.type);
        holder.setData(current)

        holder.itemView.setOnClickListener{
            listener.onToiletSelected(current)
        }
    }

    internal fun setToilets(toilets: List<Toilet>) {
        this.toilet = toilets
        notifyDataSetChanged()
    }

    override fun getItemCount() = toilet.size

    interface OnToiletSelected {
        fun onToiletSelected(toilet: Toilet)
    }


}