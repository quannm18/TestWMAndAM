package com.example.testwmandam.views.view.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testwmandam.R
import com.example.testwmandam.databinding.ActivityMainBinding
import com.example.testwmandam.model.TimeModel
import com.example.testwmandam.viewmodel.TimeDBViewModel
import com.example.testwmandam.views.adapter.MyAdapter
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val mTimeViewModel: TimeDBViewModel by lazy {
        ViewModelProvider(
            this,
            TimeDBViewModel.TimeDBViewModelFactory(application)
        )[TimeDBViewModel::class.java]
    }
    private val myAdapter: MyAdapter by lazy {
        MyAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            val hour: Int = binding.edHour.text.toString().toInt()
            val minute: Int = binding.edMinute.text.toString().toInt()
            if (hour != null && minute != null) {
                mTimeViewModel.addDataAndSetTime(
                    TimeModel(
                        hour = hour,
                        minute = minute
                    )
                )
            }
//            val mycalendar = Calendar.getInstance()
//            val mycalendar2 = Calendar.getInstance()
//            mycalendar2.set(Calendar.HOUR_OF_DAY, hour)
//            mycalendar2.set(Calendar.MINUTE, minute)
//            mycalendar2.set(Calendar.SECOND, 0)
//
//            val sdf = SimpleDateFormat("HH:mm")
//            val hms = sdf.format(mycalendar.time)
//            val now = sdf.format(mycalendar2.time)
//            Log.e(
//                javaClass.simpleName,
//                "${hour}:${minute}- $now -  - $hms - ${now >= "08:11"}"
//            )
        }

        lifecycleScope.launchWhenResumed {
            mTimeViewModel.loadAll.collectLatest {
                myAdapter.submitData(it)
            }
        }

        myAdapter.event.observe(this) {
            when (it) {
                is TimeModel -> {
                    mTimeViewModel.delete(it.id)
                }
            }
        }

        binding.rcvMain.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = myAdapter
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}