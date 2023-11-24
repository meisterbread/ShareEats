package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shareeats.databinding.ActivityPersonalRecipeDetailsBinding
import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users
import com.example.shareeats.states.PersonalRecipeDetailsState
import com.example.shareeats.viewmodel.PersonalRecipeDetailsViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PersonalRecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPersonalRecipeDetailsBinding
    private var recipe: Recipe? = null
    private var userInfo: Users? = null

    private var auth = Firebase.auth

    private lateinit var personalRecipeDetailsViewModel : PersonalRecipeDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityPersonalRecipeDetailsBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recipe = intent.getParcelableExtra("Recipe")
        userInfo = intent.getParcelableExtra("userName")

        Glide.with(this@PersonalRecipeDetailsActivity)
            .load(recipe?.imageUrl)
            .apply(RequestOptions().centerCrop().override(150, 150))
            .into(binding.imgRecipe)

        binding.tvName.text = recipe?.name
        binding.tvCookingtime.text = "Cooking Time : ${recipe?.cookingTime}"
        binding.tvIngredientsBody .text = recipe?.ingredients
        binding.tvInstructionsBody.text = recipe?.instructions

        personalRecipeDetailsViewModel = PersonalRecipeDetailsViewModel()
        personalRecipeDetailsViewModel.getStates().observe(this@PersonalRecipeDetailsActivity){

            handleState(it)

        }

        binding.btnDelete.setOnClickListener {

            personalRecipeDetailsViewModel.deleteRecipe(recipe)
            Toast.makeText(this, "Successfully Deleted.", Toast.LENGTH_SHORT).show()

        }

        binding.btnBack.setOnClickListener {

            CreatedRecipeActivity.launch(this@PersonalRecipeDetailsActivity)
            finish()

        }


    }

    private fun handleState(it: PersonalRecipeDetailsState?) {

        when(it){

            is PersonalRecipeDetailsState.DeleteSuccess -> {

                MainActivity.launch(this@PersonalRecipeDetailsActivity)
                finish()

            }

            PersonalRecipeDetailsState.Error -> {



            }

            else -> {}
        }

    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, PersonalRecipeDetailsActivity::class.java))
    }
}