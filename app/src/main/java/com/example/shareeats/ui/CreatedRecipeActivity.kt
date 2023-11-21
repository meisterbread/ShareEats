package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareeats.databinding.ActivityCreatedRecipeBinding
import com.example.shareeats.states.CreatedRecipeState
import com.example.shareeats.states.HomeState
import com.example.shareeats.ui.adapters.CreatedRecipesAdapter
import com.example.shareeats.viewmodel.CreatedRecipeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreatedRecipeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreatedRecipeBinding
    private lateinit var adapters: CreatedRecipesAdapter
    private lateinit var mainViewModel : CreatedRecipeViewModel

    private var auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityCreatedRecipeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recylerviewCreatedRecipes.layoutManager = LinearLayoutManager(this)

        mainViewModel = CreatedRecipeViewModel()
        mainViewModel.getStates().observe(this@CreatedRecipeActivity) {

            handleState(it)

        }


    }

    private fun handleState(it: CreatedRecipeState?) {

        when(it) {
            is CreatedRecipeState.Default -> {

                adapters = CreatedRecipesAdapter(this, it.data)
                binding.recylerviewCreatedRecipes.adapter = adapters
                binding.tvSubheader.text = "@${it.userInfo?.username}'s recipes"

            }



            CreatedRecipeState.Error -> {

            }

            else -> {}
        }

    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, CreatedRecipeActivity::class.java))
    }
}