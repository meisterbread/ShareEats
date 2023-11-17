package com.example.shareeats.states

import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users

sealed class RecipeDetailsState{

    data object FavoriteSuccess : RecipeDetailsState()
    data object Error : RecipeDetailsState()

}
