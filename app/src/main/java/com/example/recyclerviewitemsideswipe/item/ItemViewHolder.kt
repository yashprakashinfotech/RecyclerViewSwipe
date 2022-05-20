package com.example.recyclerviewitemsideswipe.item

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewitemsideswipe.R
import com.example.recyclerviewitemsideswipe.database.UserModel
import java.lang.ref.WeakReference

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)
    private lateinit var textView: TextView
    private lateinit var textViewDelete : CardView
    var index = 0

    var onDeleteClick : ((RecyclerView.ViewHolder) -> Unit)? = null

    private var userName : TextView = itemView.findViewById(R.id.userName)
    private var userDesignation : TextView = itemView.findViewById(R.id.userDesignation)
    private var userId : TextView = itemView.findViewById(R.id.userId)
    private var bloodGroup : TextView = itemView.findViewById(R.id.bloodGroup)
    var cardDelete : CardView = itemView.findViewById(R.id.cardDelete)
//    var icDelete : ImageView = itemView.findViewById(R.id.icDelete)
    var textEdit : CardView = itemView.findViewById(R.id.cardEdit)

    fun bindView(user : UserModel){
        userName.text = user.username
        userDesignation.text = user.designation
        userId.text = user.userId
        bloodGroup.text = user.bloodGroup
    }


    init {
        view.get()?.let {

            it.setOnClickListener{
                // Click item to reset swiped position
                if (view.get()?.scrollX != 0){
                    view.get()?.scrollTo(0,0)
                }

            }

//            textView = it.findViewById(R.id.textView)
            textViewDelete = it.findViewById(R.id.cardDelete)

            textViewDelete.setOnClickListener {
                onDeleteClick?.let {
                    onDeleteClick -> onDeleteClick(this)
                }
            }
        }
    }

//    fun updateView(){
//
//        view.get()!!.scrollTo(0,0)
//        textView.text = "index $index"
//    }
}