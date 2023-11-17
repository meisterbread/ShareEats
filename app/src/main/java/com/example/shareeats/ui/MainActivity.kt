package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareeats.databinding.ActivityMainBinding
import com.example.shareeats.model.Users
import com.example.shareeats.states.HomeState
import com.example.shareeats.ui.adapters.HomeAdapter
import com.example.shareeats.viewmodel.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapters: HomeAdapter
    private lateinit var mainViewModel : HomeViewModel

    private var auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recylerviewHome.layoutManager = LinearLayoutManager(this)



        mainViewModel = HomeViewModel()
        mainViewModel.getStates().observe(this@MainActivity) {

            handleState(it)

        }



        binding.btnAddRecipe.setOnClickListener {

            CreateRecipeActivity.launch(this@MainActivity)

        }

        binding.imgProfile.setOnClickListener {

            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            intent.putExtra("userID", auth.currentUser?.uid)
            startActivity(intent)



        }



    }

    private fun handleState(it: HomeState) {

        when(it) {
            is HomeState.Default -> {

                adapters = HomeAdapter(this, it.data)
                binding.recylerviewHome.adapter = adapters
                binding.tvSubheader.text = "What will you cook, @${it.userInfo?.username}?"
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