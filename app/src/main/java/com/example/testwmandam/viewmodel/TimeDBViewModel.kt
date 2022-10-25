package com.example.testwmandam.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.testwmandam.model.TimeModel
import com.example.testwmandam.room.repository.TimeRepository
import com.example.testwmandam.work.OneTimeWorker
import com.example.testwmandam.work.RepeatWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeDBViewModel(application: Application) :
    ViewModel() {
    private val timeRepository: TimeRepository by lazy {
        TimeRepository.newInstance(application)
    }
    private val workManager = WorkManager.getInstance(application)

    val loadAll: Flow<PagingData<TimeModel>> by lazy {
        Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            )
        ) {
            timeRepository.loadAll()
        }.flow.cachedIn(viewModelScope)
    }

    suspend fun getAllList(): List<TimeModel> {
        return withContext(Dispatchers.IO) {
            timeRepository.getAllList()
        }
    }

    val timeLiveData: LiveData<List<TimeModel>> = timeRepository.timeLiveData()

    fun insert(timeModel: TimeModel) {
        viewModelScope.launch(Dispatchers.IO) {
            timeRepository.insert(timeModel)
        }
    }

    fun insert(timeList: List<TimeModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            timeRepository.insert(timeList)
        }
    }

    fun update(timeModel: TimeModel) {
        viewModelScope.launch(Dispatchers.IO) {
            timeRepository.update(timeModel)
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == null) {
                timeRepository.delete()
            } else {
                timeRepository.delete(id)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun addDataAndSetTime(timeModel: TimeModel) {
        viewModelScope.async(Dispatchers.IO) {
            val jobInsert = async { insert(timeModel) }
            jobInsert.await()

            val jobWorkManager = async {
                val dataBuilder = Data.Builder().putInt("hour", timeModel.hour)
                    .putInt("minute", timeModel.minute)
                    .build()

                val oneTimeRequest = OneTimeWorkRequestBuilder<OneTimeWorker>()
                    .setInputData(dataBuilder)
                    .build()

                val repeatTimeRequest =
                    PeriodicWorkRequestBuilder<RepeatWorker>(1425, TimeUnit.MINUTES)
                        .setInputData(dataBuilder)
                        .build()

                val myCalendar = Calendar.getInstance()
                val inputCalendar = Calendar.getInstance()

                inputCalendar.set(Calendar.HOUR_OF_DAY, timeModel.hour)
                inputCalendar.set(Calendar.MINUTE, timeModel.minute)
                inputCalendar.set(Calendar.SECOND, 0)

                val sdf = SimpleDateFormat("HH:mm")
                val now = sdf.format(myCalendar.time)
                val inputTime = sdf.format(inputCalendar.time)

                if (now >= inputTime) {
                    workManager.enqueue(oneTimeRequest)
                }
                workManager.enqueue(repeatTimeRequest)
            }

            jobWorkManager.await()


        }
    }

    class TimeDBViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TimeDBViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TimeDBViewModel(application) as T
            }
            throw IllegalAccessException("Unable constructor viewModel")
        }
    }
}

