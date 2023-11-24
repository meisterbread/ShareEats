package com.example.shareeats.states

import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users

sealed class CreatedRecipeVersionState{

    data class Default(val data : ArrayList<Recipe>, val userInfo : Users?) : CreatedRecipeVersionState()

    data object Error : CreatedRecipeVersionState()

}
