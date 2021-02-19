package com.example.recyclerviewform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerviewform.database.UserDao
import java.lang.IllegalArgumentException

class SecondFactory(val firstData:String,val secondData:String,val userId:Long,val dataSource:UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SecondViewModel::class.java)){
            return SecondViewModel(firstData,secondData,userId,dataSource) as T
        }
        throw IllegalArgumentException("Unknown model class")
    }
}