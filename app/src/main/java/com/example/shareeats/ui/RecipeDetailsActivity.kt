package com.example.shareeats.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shareeats.databinding.ActivityRecipeDetailsBinding
import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users
import com.example.shareeats.states.HomeState
import com.example.shareeats.viewmodel.RecipeDetailsViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecipeDetailsBinding
    private var recipe: Recipe? = null
    private var userInfo: Users? = null


    private lateinit var recipeDetailsViewModel: RecipeDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recipe = intent.getParcelableExtra("Recipe")
        userInfo = intent.getParcelableExtra("userName")

        Glide.with(this@RecipeDetailsActivity)
            .load(recipe?.imageUrl)
            .apply(RequestOptions().centerCrop().override(150, 150))
            .into(binding.imgRecipe)

        binding.tvName.text = recipe?.name
        binding.tvCreator.text = "Created by : ${userInfo?.name}"
        binding.tvCookingtime.text = "Cooking Time : ${recipe?.cookingTime}"
        binding.tvIngredientsBody .text = recipe?.ingredients
        binding.tvInstructionsBody.text = recipe?.instructions

        recipeDetailsViewModel = RecipeDetailsViewModel()


        binding.imgArrow.setOnClickListener {

            MainActivity.launch(this@RecipeDetailsActivity)
            finish()

        }


        binding.btnFavorite.setOnClickListener {


        }



    }

}