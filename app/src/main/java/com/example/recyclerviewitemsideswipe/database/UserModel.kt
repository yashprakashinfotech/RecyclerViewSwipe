package com.example.recyclerviewitemsideswipe.database

import java.util.*

data class UserModel(
    var id : Int = getAutoId() ,
    var username : String = "",
    var designation : String = "",
    var userId : String = "",
    var bloodGroup : String = ""
){
    companion object{
        fun getAutoId(): Int{
            val random = Random()
            return random.nextInt(100)
        }
    }
}
