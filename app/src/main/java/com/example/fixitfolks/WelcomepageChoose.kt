package com.example.fixitfolks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.fixitfolks.databinding.FragmentWelcomepageChooseBinding

class WelcomepageChoose : Fragment() {

    private lateinit var binding: FragmentWelcomepageChooseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomepageChooseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = Bundle()




        binding.userChooseId.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager?.beginTransaction()
            val welcomePage = WelcomePage()
            bundle.putString("user", "customer")
            welcomePage.arguments = bundle
            fragmentManager?.add(R.id.frame_fragment,welcomePage)
            fragmentManager?.commit()

        }

        binding.providerChooseId.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager?.beginTransaction()
            val welcomePage = WelcomePage()
            bundle.putString("user", "provider")
            welcomePage.arguments = bundle
            fragmentManager?.add(R.id.frame_fragment,welcomePage)
            fragmentManager?.commit()
        }

    }

}