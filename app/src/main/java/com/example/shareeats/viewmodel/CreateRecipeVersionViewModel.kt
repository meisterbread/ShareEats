package com.example.shareeats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shareeats.model.Recipe
import com.example.shareeats.states.CreateRecipeState
import com.example.shareeats.states.CreateRecipeVersionState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class CreateRecipeVersionViewModel {

    private val createRecipeVersionState = MutableLiveData<CreateRecipeVersionState>()

    private var databaseRef = Firebase.database.reference
    private var storageRef = Firebase.storage.reference

    private var auth = Firebase.auth

    fun getState() : LiveData<CreateRecipeVersionState> = createRecipeVersionState

    fun addRecipeToVersions(img : ByteArray,
                            name : String,
                            cookingTime : String,
                            ingredients : String,
                            instructions : String,
                            createdBy : String,
                            recipeID : String) {
1
        val databaseRef = databaseRef.child("Recipe").child(recipeID).child("Versions").push()
        val id = databaseRef.key


        val storageRef = storageRef.child("Recipe").child("Images").child("$name.jpg")


        storageRef.putBytes(img).addOnSuccessListener {

            storageRef.downloadUrl.addOnSuccessListener {



                val add = Recipe(id,
                    it.toString(),
                    name,
                    cookingTime,
                    ingredients,
                    instructions,
                    createdBy)

                databaseRef.setValue(add).addOnSuccessListener {

                    createRecipeVersionState.value = CreateRecipeVersionState.Success

                } .addOnFailureListener {

                    createRecipeVersionState.value = CreateRecipeVersionState.Error

                }

            }

        }




    }

    fun addRecipeToUsers(img : ByteArray,
                         name : String,
                         cookingTime : String,
                         ingredients : String,
                         instructions : String,
                         createdBy : String,
                         recipeID : String) {

        val databaseRef = databaseRef.child("users")
            .child(auth.currentUser?.uid.toString())
            .child("created_recipes")
            .push()
        val id = databaseRef.key


        val storageRef = storageRef.child("Recipe").child("Images").child("$name.jpg")


        storageRef.putBytes(img).addOnSuccessListener {

            storageRef.downloadUrl.addOnSuccessListener {



                val add = Recipe(id,
                    it.toString(),
                    name,
                    cookingTime,
                    ingredients,
                    instructions,
                    createdBy)

                databaseRef.setValue(add).addOnSuccessListener {

                    createRecipeVersionState.value = CreateRecipeVersionState.Success

                } .addOnFailureListener {

                    createRecipeVersionState.value = CreateRecipeVersionState.Error

                }

            }

        }




    }

}