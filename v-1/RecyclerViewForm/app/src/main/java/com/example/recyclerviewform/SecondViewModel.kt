package com.example.recyclerviewform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewform.database.UserDao
import com.example.recyclerviewform.database.UserTable
import kotlinx.coroutines.launch

class SecondViewModel(val firstData:String,val secondData:String,val userId:Long,val database:UserDao):ViewModel() {

    var table:LiveData<List<UserTable>>
    private val _navigateToDetail=MutableLiveData<Long>()
    val navigateToDetail:LiveData<Long>
    get() = _navigateToDetail

    init {
        startInsert()
        startUpdate()
        table=database.getAllTable()
    }

    private fun startInsert(){
        viewModelScope.launch {
            if(firstData!="Empty" && secondData!="Empty" && userId==0L){
                val table=UserTable(firstData = firstData,secondData = secondData)
                insert(table)
            }
        }
    }

    private suspend fun insert(table:UserTable){
        database.insert(table)
    }

    fun startUpdate(){
        viewModelScope.launch {
            if(userId!=0L){
                updateCustomeTable(firstData,secondData,userId)
            }
        }
    }

    suspend fun updateCustomeTable(firstData:String,secondData:String,userId:Long){
        database.updateCustomTable(firstData,secondData,userId)
    }


}