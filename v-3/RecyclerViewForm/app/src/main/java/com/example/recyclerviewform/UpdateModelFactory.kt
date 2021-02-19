package com.example.recyclerviewform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerviewform.database.UserDao
import java.lang.IllegalArgumentException

class UpdateModelFactory(val userId:Long,val dataSource:UserDao):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UpdateViewModel::class.java)){
            return UpdateViewModel(userId,dataSource) as T
        }
        throw IllegalArgumentException("Unknown Model Class")
    }
}