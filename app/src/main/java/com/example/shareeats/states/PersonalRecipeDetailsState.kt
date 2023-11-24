package com.example.shareeats.states

sealed class PersonalRecipeDetailsState{

    data object DeleteSuccess : PersonalRecipeDetailsState()

    data object EditSuccess : PersonalRecipeDetailsState()

    data object Error : PersonalRecipeDetailsState()


}
