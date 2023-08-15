package com.example.fixitfolks.ui.providers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.fixitfolks.R
import com.example.fixitfolks.databinding.ActivityProviderMainPageBinding
import com.example.fixitfolks.ui.customers.MyServices

class ProviderMainPage : AppCompatActivity() {
    private lateinit var binding : ActivityProviderMainPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProviderMainPageBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.provider_frame_layout, LiveServiceProvider()).commit()

        binding.clickLiveProvider.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.provider_frame_layout, LiveServiceProvider()).commit()
        }
        binding.servicesId.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.provider_frame_layout, MyServices()).commit()
        }
    }
}