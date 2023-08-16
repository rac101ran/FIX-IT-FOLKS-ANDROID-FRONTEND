package com.example.fixitfolks.ui.customers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.fixitfolks.databinding.ActivityHomepageBinding

class Homepage : AppCompatActivity() {

   private  lateinit var binding : ActivityHomepageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityHomepageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(binding.frameCustomerLayout.id, CustomerExplorerPage()).commit()



        binding.wishlistId.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(binding.frameCustomerLayout.id, SearchFragment()).commit()
        }

        binding.explorerId.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(binding.frameCustomerLayout.id, CustomerExplorerPage()).commit()
        }


    }

}
