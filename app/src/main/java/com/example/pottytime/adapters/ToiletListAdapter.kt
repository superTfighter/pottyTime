package com.example.pottytime.adapters

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pottytime.R
import com.example.pottytime.data.Toilet
import com.example.pottytime.data.ToiletType
import com.example.pottytime.databinding.RecyclerviewItemBinding


class ToiletListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ToiletListAdapter.ToiletViewHolder>() {

    lateinit var clickListener: OnToiletSelected
    lateinit var longClickListener: OnToiletLongSelected
    private val layoutInflater = LayoutInflater.from(context)
    private var toilet = mutableListOf<Toilet>() // Cached copy of words


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
                ToiletType.KFC ->{
                    recyclerItemListBinding.image.setImageResource(R.drawable.kfc)
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

        holder.itemView.setOnLongClickListener {
            val alertDialog =
                AlertDialog.Builder(it.context)
            alertDialog.setTitle("Delete " + current.name)
            alertDialog.setMessage("Are you sure you want to remove permanently this item?")
            alertDialog.setPositiveButton("Cancel") {
                    dialog, _ -> dialog.cancel()
            }
            alertDialog.setNegativeButton("Yes") {
                    dialog, _ ->
                toilet.removeAt(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                longClickListener.onToiletLongSelected(current);
            }

            val dialog = alertDialog.create()
            dialog.show()

            true
        }

        holder.itemView.setOnClickListener{
            clickListener.onToiletSelected(current)
        }
    }

    internal fun setToilets(toilets: List<Toilet>) {
        this.toilet = toilets.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = toilet.size

    interface OnToiletSelected {
        fun onToiletSelected(toilet: Toilet)
    }

    interface OnToiletLongSelected {
        fun onToiletLongSelected(toilet: Toilet)
    }


}