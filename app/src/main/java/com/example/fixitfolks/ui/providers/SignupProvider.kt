package com.example.fixitfolks.ui.providers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fixitfolks.databinding.FragmentSIgnupProviderBinding


class SignupProvider : Fragment() {

    private lateinit var binding : FragmentSIgnupProviderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSIgnupProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.providerSignUp.setOnClickListener {
             val intent = Intent(requireContext(), ProviderMainPage::class.java)
             startActivity(intent)
        }

    }
}