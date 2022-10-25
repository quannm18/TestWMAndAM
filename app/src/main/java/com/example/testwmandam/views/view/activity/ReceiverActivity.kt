package com.example.testwmandam.views.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.testwmandam.databinding.ActivityReceiver2Binding

class ReceiverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceiver2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityReceiver2Binding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_receiver2)
    }
}