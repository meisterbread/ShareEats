package com.example.shareeats.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var id : String? = null,
    var imageURL : String? = null,
    var name : String? = null,
    var username : String? = null,
    var email : String? = null,
    var bio : String? = null,
    var favorites : List<String>? = null
) : Parcelable
