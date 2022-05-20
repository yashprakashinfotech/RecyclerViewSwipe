package com.example.recyclerviewitemsideswipe.activity

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.recyclerviewitemsideswipe.R
import com.example.recyclerviewitemsideswipe.adapter.ListAdapter
import com.example.recyclerviewitemsideswipe.database.DataBaseHandler
import com.example.recyclerviewitemsideswipe.database.UserModel
import com.example.recyclerviewitemsideswipe.helper.KeyClass
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val swipeRefreshLayout : SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeRefreshLayout)
    }

    private val recyclerView : RecyclerView by lazy {
        findViewById(R.id.recycleView)
    }

    private lateinit var fabAdd : FloatingActionButton
    private lateinit var dataBaseHandler : DataBaseHandler

    private val listAdapter = ListAdapter(this)
    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialization
        fabAdd = findViewById(R.id.fabAdd)
        dataBaseHandler = DataBaseHandler(this)
        getUserData()

        fabAdd.setOnClickListener {
            val i = Intent(this,FormActivity::class.java)
            i.putExtra(KeyClass.KEY_CAME_FROM,true)
            startActivity(i)
        }

        listAdapter.setOnClickDeleteItem {
            deleteUser(it.id)
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false

//            val list = mutableListOf<Int>()
//            for (i in 0 until 50){
//                list.add(i)
//            }

//            listAdapter.reload(list)
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = listAdapter

//        val list = mutableListOf<Int>()
//        for (i in 0 until 50){
//            list.add(i)
//        }

//        listAdapter.reload(list)
        setItemTouchHelper()
    }

    private fun setItemTouchHelper() {

        ItemTouchHelper(object : ItemTouchHelper.Callback(){

            // the limit of swipe, same as the delete button in item 100dp
            private val limitScrollX = dipTopx(215f,this@MainActivity)
            private var currentScrollX = 0
            private var currentScrollXWhenInActive = 0
            private var initXWhenInActive = 0f
            private var firstInActive = false

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = 0
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags,swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return Integer.MAX_VALUE.toFloat()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    if (dX == 0f){
                        currentScrollX = viewHolder.itemView.scrollX
                        firstInActive = true
                    }

                    if (isCurrentlyActive){
                        // swipe with finger

                        var scrollOffset = currentScrollX + (-dX).toInt()
                        if (scrollOffset > limitScrollX){
                            scrollOffset = limitScrollX
                        }
                        else if (scrollOffset < 0){
                            scrollOffset = 0
                        }

                        viewHolder.itemView.scrollTo(scrollOffset,0)
                    }
                    else{
                        // swipe with auto animation
                        if (firstInActive){
                            firstInActive = false
                            currentScrollXWhenInActive = viewHolder.itemView.scrollX
                            initXWhenInActive = dX
                        }

                        if (viewHolder.itemView.scrollX < limitScrollX){

                            viewHolder.itemView.scrollTo((currentScrollXWhenInActive * dX / initXWhenInActive).toInt(), 0)
                        }
                    }
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)

                if (viewHolder.itemView.scrollX > limitScrollX ){
                    viewHolder.itemView.scrollTo(limitScrollX,0)
                }
                else if (viewHolder.itemView.scrollX < 0){
                    viewHolder.itemView.scrollTo(0,0)
                }
            }

        }).apply {
            attachToRecyclerView(recyclerView)
        }


    }

    private fun dipTopx(dipValue: Float,context: Context): Int{
        return (dipValue * context.resources.displayMetrics.density).toInt()
    }


    private fun deleteUser(id: Int){

        val profileAlertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        profileAlertDialog.setTitle(R.string.delete)
        profileAlertDialog.setCancelable(true)
        profileAlertDialog.setMessage(R.string.alert_message)
        profileAlertDialog.setPositiveButton(R.string.yes) { dialog, _ ->
            dataBaseHandler.deleteUserById(id)
            getUserData()
            dialog.dismiss()

        }.setNegativeButton(R.string.no) { dialog, _ ->
            dialog.dismiss()
        }
        profileAlertDialog.show()

    }

    // update data
//    private fun updateUser(){
//        val username = etUsername.text.toString()
//        val designation = etDesignation.text.toString()
//        val userId = etUserId.text.toString()
//        val bloodGroup = etBloodGroup.text.toString()
//
//        if (
//            username == userNameRV &&
//            designation == designationRV &&
//            userId == userIdRV &&
//            bloodGroup == bloodGroupRV
//        ){
//            Toast.makeText(this,"Record not changed", Toast.LENGTH_SHORT).show()
//        }
//        else{
//            val user = UserModel(id = idRV, username = username, designation = designation, userId = userId, bloodGroup = bloodGroup)
//            val status = dataBaseHandler.updateUser(user)
//
//            if (status > -1){
//                clearEditText()
//                val i = Intent(this,MainActivity::class.java)
//                startActivity(i)
//            }else{
//                Toast.makeText(this,"Update failed", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//    }
//
//    // clear edit text
//    private fun clearEditText() {
//        etUsername.setText("")
//        etDesignation.setText("")
//        etUserId.setText("")
//        etBloodGroup.setText("")
//    }

    private fun getUserData(){
        val userListData = dataBaseHandler.readAllData()

        // Display Data in recyclerView
        listAdapter.addItem(userListData)

        // If Data Not Available
//        emptyView()
    }

//    private fun emptyView(){
//        // If Data Not Available
//        val empty = (userList.adapter as UserAdapter).itemCount
//        if (empty == 0){
//            emptyView.visibility = View.VISIBLE
//        }
//        else{
//            emptyView.visibility = View.GONE
//        }
//    }

}