package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareeats.databinding.ActivityMainBinding
import com.example.shareeats.states.HomeState
import com.example.shareeats.ui.adapters.HomeAdapter
import com.example.shareeats.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapters: HomeAdapter
    private lateinit var mainViewModel : HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recylerviewHome.layoutManager = LinearLayoutManager(this)



        mainViewModel = HomeViewModel()
        mainViewModel.getStates().observe(this@MainActivity) {

            handleState(it)

        }



        binding.btnImg.setOnClickListener {

            CreateRecipeActivity.launch(this@MainActivity)

        }

        binding.imgProfile.setOnClickListener {

            ProfileActivity.launch(this@MainActivity)

        }



    }

    private fun handleState(it: HomeState) {

        when(it) {
            is HomeState.Default -> {

                adapters = HomeAdapter(this, it.data)
                binding.recylerviewHome.adapter = adapters
                binding.tvSubheader.text = "What will you cook, ${it.userInfo?.name}?"
            }



            HomeState.Error -> {

            }

            else -> {}
        }
    }


    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}