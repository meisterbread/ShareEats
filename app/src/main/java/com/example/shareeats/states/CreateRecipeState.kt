package com.example.shareeats.states

import com.example.shareeats.model.Users

sealed class CreateRecipeState{

    data object Success : CreateRecipeState()
    data object Error : CreateRecipeState()

    data class GetDataSuccess(val userInfo : Users?) : CreateRecipeState()


}
