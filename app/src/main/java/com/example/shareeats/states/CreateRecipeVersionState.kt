package com.example.shareeats.states

import com.example.shareeats.model.Users

sealed class CreateRecipeVersionState{

    data object Success : CreateRecipeVersionState()

    data object Error : CreateRecipeVersionState()

    data class GetDataSuccess(val userInfo : Users?) : CreateRecipeState()


}
