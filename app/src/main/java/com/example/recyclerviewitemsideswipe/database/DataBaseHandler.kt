package com.example.recyclerviewitemsideswipe.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class DataBaseHandler(context: Context) : SQLiteOpenHelper(context, databaseName,null, databaseVersion){

    companion object{
        const val databaseVersion = 1
        const val databaseName = "UserDB"
        const val tableName = "Users"
        const val colId = "id"
        const val colUsername = "username"
        const val colDesignation = "designation"
        const val colUserId = "userId"
        const val colBloodGroup = "bloodGroup"
    }
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + tableName +" ("+
                colId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                colUsername +" " +"VARCHAR(256)," +
                colDesignation+" " + "VARCHAR(256)," +
                colUserId+" " + "VARCHAR(256)," +
                colBloodGroup +" "+ "VARCHAR(256)"+ ")"

        db!!.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun insertData(userModel: UserModel) : Long{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(colId,userModel.id)
        cv.put(colUsername,userModel.username)
        cv.put(colDesignation,userModel.designation)
        cv.put(colUserId,userModel.userId)
        cv.put(colBloodGroup,userModel.bloodGroup)
        val result = db.insert(tableName,null,cv)

        db.close()
        return result

    }

    fun updateUser(userModel: UserModel) : Int{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(colUsername,userModel.username)
        cv.put(colDesignation,userModel.designation)
        cv.put(colUserId,userModel.userId)
        cv.put(colBloodGroup,userModel.bloodGroup)

        val success = db.update(tableName,cv,"id=" + userModel.id,null)

        db.close()
        return success
    }

    fun deleteUserById(id : Int) : Int{

        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(colId,id)
        val result = db.delete(tableName,"id=$id",null)
        db.close()
        return result
    }

    @SuppressLint("Recycle", "Range")
    fun readAllData(): ArrayList<UserModel> {
        val db = this.readableDatabase
        val query = "Select * from $tableName"

        val userList : ArrayList<UserModel> = ArrayList()

        val cursor : Cursor?

        try {
            cursor = db.rawQuery(query,null)
        }
        catch (e:Exception){
            db.execSQL(query)
            e.printStackTrace()
            return ArrayList()
        }

        var id : Int
        var username : String
        var designation : String
        var userId : String
        var bloodGroup : String


        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex(colId))
                username = cursor.getString(cursor.getColumnIndex(colUsername))
                designation = cursor.getString(cursor.getColumnIndex(colDesignation))
                userId = cursor.getString(cursor.getColumnIndex(colUserId))
                bloodGroup = cursor.getString(cursor.getColumnIndex(colBloodGroup))

                val users = UserModel(id = id,username = username,designation = designation, userId = userId,bloodGroup = bloodGroup)
                userList.add(users)

            }while (cursor.moveToNext())
        }

        return userList

    }

}