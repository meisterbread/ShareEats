package com.example.shareeats.states

import com.example.shareeats.model.Users

sealed class AuthenticationStates {

    data class Default(val user: Users?) : AuthenticationStates()
    data class IsSignedIn(val isSignedIn: Boolean) : AuthenticationStates()
    data object SignedUp : AuthenticationStates()
    data object SignedIn : AuthenticationStates()
    data object ProfileUpdated : AuthenticationStates()
    data object EmailUpdated : AuthenticationStates()
    data object PasswordUpdated : AuthenticationStates()
    data object VerificationEmailSent : AuthenticationStates()
    data object PasswordResetEmailSent : AuthenticationStates()
    data object LogOut : AuthenticationStates()
    data object UserDeleted : AuthenticationStates()
    data object Error : AuthenticationStates()

}