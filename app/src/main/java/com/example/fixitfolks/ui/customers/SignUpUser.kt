package com.example.fixitfolks.ui.customers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fixitfolks.R
import com.example.fixitfolks.databinding.FragmentSignUpUserBinding
import com.example.fixitfolks.ui.providers.utils.ProgressBarLoading
import com.example.fixitfolks.viewModel.CustomerExplorerViewModel
import kotlinx.coroutines.launch

class SignUpUser : Fragment() {


    private lateinit var binding : FragmentSignUpUserBinding
    private lateinit var viewModelCustomer : CustomerExplorerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpUserBinding.inflate(layoutInflater)
        viewModelCustomer = ViewModelProvider(this)[CustomerExplorerViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val barLoading = ProgressBarLoading(requireActivity())

        binding.userSignUp.setOnClickListener {
            barLoading.startLoadingDialog()
            viewModelCustomer.viewModelScope.launch {

                val response = viewModelCustomer.createSignUpRequest(name = binding.fullNameUserId.text.toString(),
                username = binding.usernameId.text.toString(), password = binding.userPasswordId.text.toString(),
                number = binding.userPhnId.text.toString())

                if(response!=null) {
                    barLoading.dismissDialog()
                    Toast.makeText(context,response.message,Toast.LENGTH_SHORT).show()
                    if(response.status.lowercase() == "success") {
                        startActivity(Intent(context,Homepage::class.java))
                    }
                }else {
                    barLoading.dismissDialog()
                    Toast.makeText(context,"could not sign up",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}