package com.example.shareeats.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    var id : String? = null,
    var imageUrl : String? = null,
    var name: String? = null,
    var cookingTime: String? = null,
    var ingredients: String? = null,
    var instructions: String? = null,
    var createdBy : String? = null,
    var userCreatedID : String? = null

) : Parcelable
