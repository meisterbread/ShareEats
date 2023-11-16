package com.example.shareeats.states

import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users

sealed class HomeState{

    data class Default(val data : ArrayList<Recipe>, val userInfo : Users?) : HomeState()
    data object Error : HomeState()


}
