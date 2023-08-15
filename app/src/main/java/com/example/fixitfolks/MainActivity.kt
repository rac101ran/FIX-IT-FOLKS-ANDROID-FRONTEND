package com.example.fixitfolks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import com.example.fixitfolks.models.ProviderOrder
import com.example.fixitfolks.ui.customers.LoginUser
import com.example.fixitfolks.ui.customers.SignUpUser
import com.example.fixitfolks.ui.providers.LoginProvider
import com.example.fixitfolks.ui.providers.SignupProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.frame_fragment, WelcomepageChoose()).commit()

       onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
           override fun handleOnBackPressed() {
               val fragment = supportFragmentManager.findFragmentById(R.id.frame_fragment)

               if(fragment is WelcomePage) {
                   supportFragmentManager.beginTransaction().replace(R.id.frame_fragment, WelcomepageChoose()).commit()
               }else if(fragment is SignupProvider || fragment is LoginProvider || fragment is SignUpUser || fragment is LoginUser) {
                   supportFragmentManager.beginTransaction().replace(R.id.frame_fragment, WelcomePage()).commit()
               }else {
                   this@MainActivity.finish()
               }
           }
       })
    }

}