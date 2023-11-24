package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareeats.R
import com.example.shareeats.databinding.ActivityCreatedRecipeBinding
import com.example.shareeats.databinding.ActivityCreatedRecipeVersionsBinding
import com.example.shareeats.databinding.ActivityMainBinding
import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users
import com.example.shareeats.states.CreatedRecipeState
import com.example.shareeats.states.CreatedRecipeVersionState
import com.example.shareeats.ui.adapters.CreatedRecipeVersionsAdapter
import com.example.shareeats.ui.adapters.CreatedRecipesAdapter
import com.example.shareeats.ui.adapters.HomeAdapter
import com.example.shareeats.viewmodel.CreatedRecipeVersionViewModel
import com.example.shareeats.viewmodel.CreatedRecipeViewModel
import com.example.shareeats.viewmodel.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreatedRecipeVersionsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreatedRecipeVersionsBinding
    private lateinit var adapters: CreatedRecipeVersionsAdapter
    private lateinit var mainViewModel : CreatedRecipeVersionViewModel

    private var recipe: Recipe? = null
    private var userInfo: Users? = null

    private var auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityCreatedRecipeVersionsBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recipe = intent.getParcelableExtra("Recipe")
        userInfo = intent.getParcelableExtra("userName")

        binding.recylerviewCreatedRecipes.layoutManager = LinearLayoutManager(this)

        mainViewModel = CreatedRecipeVersionViewModel()

        var recipeID = recipe?.id.toString()

        mainViewModel.getVersion(recipeID)

        mainViewModel.getStates().observe(this@CreatedRecipeVersionsActivity) {

            handleState(it)

        }

        binding.imgProfile.setOnClickListener {

            MainActivity.launch(this@CreatedRecipeVersionsActivity)
            finish()

        }

    }

    private fun handleState(it: CreatedRecipeVersionState?) {

        when(it) {
            is CreatedRecipeVersionState.Default -> {

                adapters = CreatedRecipeVersionsAdapter(this, it.data)
                binding.recylerviewCreatedRecipes.adapter = adapters

            }



            CreatedRecipeVersionState.Error -> {

            }

            else -> {}
        }

    }


    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, CreatedRecipeActivity::class.java))
    }
}