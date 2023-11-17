package com.example.shareeats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shareeats.model.Recipe
import com.example.shareeats.states.CreateRecipeState
import com.example.shareeats.states.HomeState
import com.example.shareeats.states.RecipeDetailsState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class RecipeDetailsViewModel {

    private val recipeDetailState = MutableLiveData<RecipeDetailsState>()

    private var databaseRef = Firebase.database.reference
    private var storageRef = Firebase.storage.reference
    private val recipeList = ArrayList<Recipe>()
    private var auth = Firebase.auth

    fun getStates(): LiveData<RecipeDetailsState> = recipeDetailState



}

