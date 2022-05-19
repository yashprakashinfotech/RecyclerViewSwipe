package com.example.recyclerviewitemsideswipe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.recyclerviewitemsideswipe.R
import com.example.recyclerviewitemsideswipe.database.DataBaseHandler
import com.example.recyclerviewitemsideswipe.database.UserModel
import com.example.recyclerviewitemsideswipe.helper.KeyClass

class FormActivity : AppCompatActivity() {

    private lateinit var etUsername : EditText
    private lateinit var etDesignation : EditText
    private lateinit var etUserId : EditText
    private lateinit var etBloodGroup : EditText
    private lateinit var btnUpdate : Button
    private lateinit var btnSubmit : Button
    private lateinit var imgEdit : ImageView
    private lateinit var imgBack : ImageView
    private lateinit var txtList : TextView

    private var isCameFrom = false
    private var idRV = 0
    private var userNameRV = ""
    private var designationRV = ""
    private var userIdRV = ""
    private var bloodGroupRV = ""

    private lateinit var dataBaseHandler: DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        // initialize the variables
        initView()

        dataBaseHandler = DataBaseHandler(this)

        imgBack.setOnClickListener {
            onBackPressed()
        }

        val bundle = intent.extras
        isCameFrom = bundle!!.getBoolean(KeyClass.KEY_CAME_FROM)

        // Came For Add New User
        if (isCameFrom){
            setAllFieldEnable()
            imgEdit.visibility = View.GONE
            btnUpdate.visibility = View.GONE

            btnSubmit.setOnClickListener {
                addUser()
            }
        }
        else{
            val bundle1 : Bundle? = intent.extras!!
            idRV = bundle1!!.getInt(KeyClass.KEY_ID)
            userNameRV = bundle1.getString(KeyClass.KEY_USERNAME)!!
            designationRV = bundle1.getString(KeyClass.KEY_DESIGNATION)!!
            userIdRV = bundle1.getString(KeyClass.KEY_USER_ID)!!
            bloodGroupRV = bundle1.getString(KeyClass.KEY_BLOOD_GROUP)!!

            etUsername.setText(userNameRV)
            etDesignation.setText(designationRV)
            etUserId.setText(userIdRV)
            etBloodGroup.setText(bloodGroupRV)

            btnSubmit.visibility = View.GONE
            imgEdit.setOnClickListener {
                setAllFieldEnable()
            }

            btnUpdate.setOnClickListener {
                updateUser()
            }
        }

    }

    private fun initView(){
        etUsername = findViewById(R.id.etUserName)
        etDesignation = findViewById(R.id.etDesignation)
        etUserId = findViewById(R.id.etUserId)
        etBloodGroup = findViewById(R.id.etBloodGroup)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnSubmit = findViewById(R.id.btnSubmit)
        imgEdit = findViewById(R.id.imgEdit)
        imgBack = findViewById(R.id.imgBack)
        txtList = findViewById(R.id.txtList)
    }

    // enable the all field
    private fun setAllFieldEnable(){

        etUsername.isEnabled = true
        etDesignation.isEnabled = true
        etUserId.isEnabled = true
        etBloodGroup.isEnabled = true
        etBloodGroup.isEnabled = true
        btnUpdate.isEnabled = true

    }

    private fun addUser(){
        val username = etUsername.text.toString()
        val designation = etDesignation.text.toString()
        val userId = etUserId.text.toString()
        val bloodGroup = etBloodGroup.text.toString()

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(designation) && !TextUtils.isEmpty(userId) && !TextUtils.isEmpty(bloodGroup)){

            val user = UserModel(username = username, designation = designation, userId = userId, bloodGroup = bloodGroup)
            val status = dataBaseHandler.insertData(user)
            if (status > -1){
                Toast.makeText(this,"User Added", Toast.LENGTH_SHORT).show()
                val i = Intent(this,MainActivity::class.java)
                startActivity(i)
                clearEditText()
            }
            else{
                Toast.makeText(this,"User Not Added", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this,"Please Fill All Data", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUser(){
        val username = etUsername.text.toString()
        val designation = etDesignation.text.toString()
        val userId = etUserId.text.toString()
        val bloodGroup = etBloodGroup.text.toString()

        if (
            username == userNameRV &&
            designation == designationRV &&
            userId == userIdRV &&
            bloodGroup == bloodGroupRV
        ){
            Toast.makeText(this,"Record not changed", Toast.LENGTH_SHORT).show()
        }
        else{
            val user = UserModel(id = idRV, username = username, designation = designation, userId = userId, bloodGroup = bloodGroup)
            val status = dataBaseHandler.updateUser(user)

            if (status > -1){
                clearEditText()
                val i = Intent(this,MainActivity::class.java)
                startActivity(i)
            }else{
                Toast.makeText(this,"Update failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun clearEditText() {
        etUsername.setText("")
        etDesignation.setText("")
        etUserId.setText("")
        etBloodGroup.setText("")
    }
}