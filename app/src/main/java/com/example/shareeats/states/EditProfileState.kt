package com.example.shareeats.states

import com.example.shareeats.model.Users
import com.firebase.ui.auth.data.model.User

sealed class EditProfileState{

    data class Default(val userInfo : Users?) : EditProfileState()

    data object EditProfileSuccess : EditProfileState()

    data object Error : EditProfileState()

}
