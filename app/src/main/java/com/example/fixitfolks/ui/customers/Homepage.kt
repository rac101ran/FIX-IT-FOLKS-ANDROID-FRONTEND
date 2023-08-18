package com.example.fixitfolks.ui.customers

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.fixitfolks.R
import com.example.fixitfolks.databinding.ActivityHomepageBinding

class Homepage : AppCompatActivity() {

   private  lateinit var binding : ActivityHomepageBinding
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityHomepageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(binding.frameCustomerLayout.id, CustomerExplorerPage()).commit()


//        binding.wishlistId.setOnClickListener {
//            supportFragmentManager.beginTransaction().replace(binding.frameCustomerLayout.id, SearchFragment()).commit()
//        }

        binding.myServicesId.setOnClickListener {
            binding.explorerText3.setTextColor(R.color.blue)
            binding.explorerText1.setTextColor(R.color.gray)
            supportFragmentManager.beginTransaction().replace(binding.frameCustomerLayout.id, MyServices()).commit()
        }

        binding.explorerId.setOnClickListener {
            binding.explorerText3.setTextColor(R.color.gray)
            binding.explorerText1.setTextColor(R.color.blue)
            supportFragmentManager.beginTransaction().replace(binding.frameCustomerLayout.id, CustomerExplorerPage()).commit()
        }


    }

}
