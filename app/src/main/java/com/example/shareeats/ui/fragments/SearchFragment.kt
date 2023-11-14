package com.example.shareeats.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shareeats.R
import com.example.shareeats.databinding.FragmentHomeBinding
import com.example.shareeats.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater)

        return binding.root
    }

    companion object {

        fun newInstance() : SearchFragment{

            return SearchFragment()

        }
    }
}