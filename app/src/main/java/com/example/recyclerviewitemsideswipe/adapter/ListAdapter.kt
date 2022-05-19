package com.example.recyclerviewitemsideswipe.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewitemsideswipe.R
import com.example.recyclerviewitemsideswipe.item.ItemViewHolder

class ListAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private var list = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_item,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.index = list[position]

        holder.onDeleteClick = {
            removeItem(it)
        }

        holder.updateView()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(list: List<Int>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    private fun removeItem(viewHolder: RecyclerView.ViewHolder){

        val position = viewHolder.adapterPosition

        //remove Data
        list.remove(position)

        // remove item
        notifyItemRemoved(position)
    }
}