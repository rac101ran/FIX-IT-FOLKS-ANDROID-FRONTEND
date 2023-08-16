package com.example.fixitfolks.ui.customers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fixitfolks.R
import com.example.fixitfolks.databinding.FragmentLoginUserBinding
import com.example.fixitfolks.ui.providers.utils.ProgressBarLoading
import com.example.fixitfolks.viewModel.CustomerExplorerViewModel
import kotlinx.coroutines.launch

class LoginUser : Fragment() {

    private lateinit var loginFragment : FragmentLoginUserBinding
    private lateinit var customerExplorerViewModel: CustomerExplorerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginFragment = FragmentLoginUserBinding.inflate(layoutInflater)
        customerExplorerViewModel = ViewModelProvider(this)[CustomerExplorerViewModel::class.java]
        return loginFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressDialog = ProgressBarLoading(requireActivity())

        loginFragment.userLoginButtonId.setOnClickListener {
            customerExplorerViewModel.viewModelScope.launch {
                progressDialog.startLoadingDialog()
                val response = customerExplorerViewModel.loginUserRequest(loginFragment.usernameLoginId.text.toString() , loginFragment.userLoginPasswordId.text.toString())
                if(response!=null && response.status.lowercase() == "success") {
                    Log.e("data",response.user.toString())
                    val sharedPreferences = context?.getSharedPreferences("admin",Context.MODE_PRIVATE)
                    sharedPreferences?.edit()?.putString("username",response.user.username)?.apply()
                    sharedPreferences?.edit()?.putInt("user_id",response.user.user_id)?.apply()
                    sharedPreferences?.edit()?.putString("address",response.user.address)?.apply()
                    sharedPreferences?.edit()?.putString("landmark",response.user.landmark)?.apply()
                    sharedPreferences?.edit()?.putString("phone_number",response.user.phone_number.toString())?.apply()
                    sharedPreferences?.edit()?.putString("password",response.user.password)?.apply()
                    progressDialog.dismissDialog()
                    startActivity(Intent(context,Homepage::class.java))
                }else {
                    progressDialog.dismissDialog()
                }
            }
        }

    }
}