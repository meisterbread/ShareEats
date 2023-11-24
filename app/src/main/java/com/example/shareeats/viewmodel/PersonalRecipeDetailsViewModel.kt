package com.example.shareeats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shareeats.model.Recipe
import com.example.shareeats.states.ProfileStates
import com.example.shareeats.states.PersonalRecipeDetailsState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PersonalRecipeDetailsViewModel {

    private val personalRecipeDetailState = MutableLiveData<PersonalRecipeDetailsState>()

    private var databaseRef = Firebase.database.reference
    private var storageRef = Firebase.storage.reference
    private val recipeList = ArrayList<Recipe>()
    private var auth = Firebase.auth

    fun getStates(): LiveData<PersonalRecipeDetailsState> = personalRecipeDetailState

    fun deleteRecipe(recipe: Recipe?) {

        val recipeID = recipe?.id.toString()
        val recipeInUserID = recipe?.userCreatedID.toString()
        val userID = auth.currentUser?.uid.toString()

        databaseRef.child("Recipe").child(recipeID).removeValue().addOnSuccessListener {

            databaseRef
                .child("users")
                .child(userID)
                .child("created_recipes")
                .child(recipeInUserID)
                .removeValue()
                .addOnSuccessListener {


                personalRecipeDetailState.value = PersonalRecipeDetailsState.DeleteSuccess


            }



        }

    }
}