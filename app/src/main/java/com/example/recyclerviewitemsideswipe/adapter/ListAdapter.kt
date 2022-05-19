package com.example.recyclerviewitemsideswipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewitemsideswipe.R
import com.example.recyclerviewitemsideswipe.database.UserModel
import com.example.recyclerviewitemsideswipe.item.ItemViewHolder
import java.lang.ref.WeakReference

class ListAdapter(context: Context) : RecyclerView.Adapter<ItemViewHolder>() {
//class ListAdapter(context: Context) : RecyclerView.Adapter<ListAdapter.>() {

    private var list = ArrayList<UserModel>()

    private var onClickDeleteItem : ((UserModel)-> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_item,parent,false)
        return ItemViewHolder(view)
    }

//    override fun onBindViewHolder(holder: com.example.recyclerviewitemsideswipe.item.ItemViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val userList = list[position]

        holder.bindView(userList)
        holder.cardDelete.setOnClickListener { onClickDeleteItem!!.invoke(userList)}
//        holder.icDelete.setOnClickListener { onClickDeleteItem!!.invoke(userList)}

//        holder.index = list[position]
//
//        holder.onDeleteClick = {
//            removeItem(it)
//        }

//        holder.updateView()
    }

    override fun getItemCount(): Int {
        return list.size
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun reload(list: List<Int>){
//        this.list.clear()
//        this.list.addAll(list)
//        notifyDataSetChanged()
//    }

//    private fun removeItem(viewHolder: RecyclerView.ViewHolder){
//
//        val position = viewHolder.adapterPosition
//
//        //remove Data
//        list.remove(position)
//
//        // remove item
//        notifyItemRemoved(position)
//    }

    fun setOnClickDeleteItem(callback : (UserModel)->Unit){
        this.onClickDeleteItem = callback
    }

    fun addItem(item: ArrayList<UserModel>){
        this.list = item
        notifyDataSetChanged()
    }


//    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        private val view = WeakReference(itemView)
//        private lateinit var textView: TextView
//        private lateinit var textViewDelete : TextView
//        var index = 0
//
//        var onDeleteClick : ((RecyclerView.ViewHolder) -> Unit)? = null
//
//        private var userName : TextView = itemView.findViewById(R.id.userName)
//        private var userDesignation : TextView = itemView.findViewById(R.id.userDesignation)
//        private var userId : TextView = itemView.findViewById(R.id.userId)
//        private var bloodGroup : TextView = itemView.findViewById(R.id.bloodGroup)
//        var cardDelete : CardView = itemView.findViewById(R.id.cardDelete)
//        //    var icDelete : ImageView = itemView.findViewById(R.id.icDelete)
//        var textEdit : LinearLayout = itemView.findViewById(R.id.textViewEdit)
//
//        fun bindView(user : UserModel){
//            userName.text = user.username
//            userDesignation.text = user.designation
//            userId.text = user.userId
//            bloodGroup.text = user.bloodGroup
//        }
//
//
//        init {
//            view.get()?.let {
//
//                it.setOnClickListener{
//                    // Click item to reset swiped position
//                    if (view.get()?.scrollX != 0){
//                        view.get()?.scrollTo(0,0)
//                    }
//
//                }
//
//                textView = it.findViewById(R.id.textView)
//                textViewDelete = it.findViewById(R.id.cardDelete)
//
//                textViewDelete.setOnClickListener {
//                    onDeleteClick?.let {
//                            onDeleteClick -> onDeleteClick(this)
//                    }
//                }
//            }
//        }
//
//        fun updateView(){
//
//            view.get()!!.scrollTo(0,0)
//            textView.text = "index $index"
//        }
//    }




}