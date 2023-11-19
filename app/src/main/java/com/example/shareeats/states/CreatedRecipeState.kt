package com.example.shareeats.states

import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users
import com.example.shareeats.viewmodel.CreatedRecipeViewModel

sealed class CreatedRecipeState{

    data class Default(val data : ArrayList<Recipe>, val userInfo : Users?) : CreatedRecipeState()

    data object Error : CreatedRecipeState()

}
