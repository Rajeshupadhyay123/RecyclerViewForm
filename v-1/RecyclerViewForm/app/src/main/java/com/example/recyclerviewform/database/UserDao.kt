package com.example.recyclerviewform.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun insert(table: UserTable)

    @Update
    suspend fun update(table: UserTable)

    @Query("select *from user_table where userId= :key")
    fun getTable(key:Long):LiveData<UserTable>

    @Query("update user_table set firstData=:firstData,secondData=:secondData where userId=:key")
    suspend fun updateCustomTable(firstData:String,secondData:String,key:Long)

    @Query("select *from user_table order by userId desc")
    fun getAllTable(): LiveData<List<UserTable>>

}