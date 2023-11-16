package com.example.shareeats.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var id : String? = null,
    var imageURL : String? = null,
    var name : String? = null,
    var email : String? = null,
    var bio : String? = null
) : Parcelable
