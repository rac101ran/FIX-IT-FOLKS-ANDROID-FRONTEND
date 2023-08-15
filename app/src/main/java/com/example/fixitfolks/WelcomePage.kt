package com.example.fixitfolks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fixitfolks.databinding.FragmentWelcomePageBinding
import com.example.fixitfolks.ui.customers.LoginUser
import com.example.fixitfolks.ui.customers.SignUpUser
import com.example.fixitfolks.ui.providers.LoginProvider
import com.example.fixitfolks.ui.providers.SignupProvider


class WelcomePage : Fragment() {

    private lateinit var binding : FragmentWelcomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments?.getString("user") == "provider") {
            binding.btnLogin.text = "LOGIN PROVIDER"
            binding.registerText.text = "Are you a new provider ? Register"
        }else {
            binding.btnLogin.text = "LOGIN CUSTOMER"
            binding.registerText.text = "Are you a new customer ? Register"
        }

        binding.btnLogin.setOnClickListener {
            if(arguments?.getString("user") == "provider") {
                val fragmentManager = activity?.supportFragmentManager?.beginTransaction()
                val loginProvider = LoginProvider()
                fragmentManager?.replace(R.id.frame_fragment,loginProvider)
                fragmentManager?.commit()
            }else {
                val fragmentManager = activity?.supportFragmentManager?.beginTransaction()
                val loginUser = LoginUser()
                fragmentManager?.replace(R.id.frame_fragment,loginUser)
                fragmentManager?.commit()
            }
        }

        binding.registerText.setOnClickListener {
            if(arguments?.getString("user") == "provider") {
                val fragmentManager = activity?.supportFragmentManager?.beginTransaction()
                val signupProvider = SignupProvider()
                fragmentManager?.replace(R.id.frame_fragment,signupProvider)
                fragmentManager?.commit()
            }else {
                val fragmentManager = activity?.supportFragmentManager?.beginTransaction()
                val signupUser = SignUpUser()
                fragmentManager?.replace(R.id.frame_fragment,signupUser)
                fragmentManager?.commit()
            }
        }
    }
}