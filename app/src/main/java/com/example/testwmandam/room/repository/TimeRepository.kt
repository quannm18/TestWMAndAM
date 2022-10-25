package com.example.testwmandam.room.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.example.testwmandam.model.TimeModel
import com.example.testwmandam.room.dao.TimeDao
import com.example.testwmandam.room.db.RoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TimeRepository(application: Application) {
    companion object {
        private var instance: TimeRepository? = null

        fun newInstance(application: Application): TimeRepository {
            if (instance == null) {
                instance = TimeRepository(application)
            }
            return instance!!
        }
    }

    private val timeDao: TimeDao = RoomDB.getInstance(application).timeDao()

    fun insert(timeModel: TimeModel) {
        timeDao.insert(timeModel)
    }

    fun insert(timeList: List<TimeModel>) {
        timeDao.insert(timeList)
    }

    fun update(timeModel: TimeModel) {
        timeDao.update(timeModel)
    }

    fun delete() {
        timeDao.delete()
    }

    fun delete(id: Int) {
        timeDao.delete(id)
    }

    fun loadAll(): PagingSource<Int, TimeModel> {
        return timeDao.loadAll()
    }

    suspend fun getAllList(): List<TimeModel> {
        return withContext(Dispatchers.IO) {
            timeDao.getAllList()
        }
    }

    fun timeLiveData(): LiveData<List<TimeModel>> {
        return timeDao.timeLiveData()
    }
}