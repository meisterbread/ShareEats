package com.example.shareeats.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shareeats.R
import com.example.shareeats.databinding.FragmentFavoritesBinding
import com.example.shareeats.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    companion object {

        fun newInstance() : HomeFragment{

            return HomeFragment()

        }
    }
}