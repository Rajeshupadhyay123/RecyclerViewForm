package com.example.recyclerviewform

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewform.database.UserDao
import com.example.recyclerviewform.database.UserTable
import kotlinx.coroutines.launch

class UpdateViewModel(val userId:Long,val database:UserDao):ViewModel() {

    private val _table: LiveData<UserTable>

    init {
        _table=database.getTable(userId)
    }

    var table=_table

}