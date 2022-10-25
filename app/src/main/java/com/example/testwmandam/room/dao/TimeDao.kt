package com.example.testwmandam.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.testwmandam.model.TimeModel

@Dao
interface TimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timeModel: TimeModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timeList: List<TimeModel>)

    @Update(entity = TimeModel::class)
    fun update(timeModel: TimeModel)

    @Query("Delete from time_list")
    fun delete()

    @Query("Delete from time_list where id = :id")
    fun delete(id: Int)

    @Query("Select * from time_list order by hour, minute asc")
    fun loadAll(): PagingSource<Int, TimeModel>

    @Query("Select * from time_list order by hour, minute asc")
    suspend fun getAllList(): List<TimeModel>

    @Query("Select * from time_list order by hour, minute asc")
    fun timeLiveData(): LiveData<List<TimeModel>>
}