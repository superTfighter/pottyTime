package com.example.pottytime.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
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
    private var toilet = mutableListOf<Toilet>()


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

        holder.itemView.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    holder.itemView.setBackgroundResource(R.drawable.rounder_linearlayout_background_selected)
                }
                MotionEvent.ACTION_UP -> {

                    val pressTime : Long = motionEvent.eventTime - motionEvent.downTime;

                    if(pressTime < 300){
                        clickListener.onToiletSelected(current)
                    }
                    else
                    {

                        val alertDialog =
                            AlertDialog.Builder(view.context)
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
                    }

                    holder.itemView.setBackgroundResource(R.drawable.rounder_linearlayout_background);
                }
                MotionEvent.ACTION_CANCEL->{
                    holder.itemView.setBackgroundResource(R.drawable.rounder_linearlayout_background);
                }
                MotionEvent.ACTION_SCROLL ->{
                    holder.itemView.setBackgroundResource(R.drawable.rounder_linearlayout_background);
                }

            }
            true
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