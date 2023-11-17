package com.example.shareeats.states

import com.example.shareeats.model.Users

sealed class ProfileStates{

    data class Default(val userInfo: Users?) : ProfileStates()

    data object Error : ProfileStates()

}
