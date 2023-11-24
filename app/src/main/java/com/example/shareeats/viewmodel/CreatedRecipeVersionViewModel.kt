package com.example.shareeats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users
import com.example.shareeats.states.CreatedRecipeState
import com.example.shareeats.states.CreatedRecipeVersionState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class CreatedRecipeVersionViewModel {

    private val createdRecipeVersionState = MutableLiveData<CreatedRecipeVersionState>()

    private var databaseRef = Firebase.database.reference
    private var storageRef = Firebase.storage.reference
    private val recipeList = ArrayList<Recipe>()

    private var auth = Firebase.auth

    fun getStates() : LiveData<CreatedRecipeVersionState> = createdRecipeVersionState
    fun getVersion(recipeID : String){
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                recipeList.clear()

                for (data in snapshot.children) { //loops through each object

                    data.getValue<Recipe>()?.let {

                        recipeList.add(it)

                    }

                }

                val objectListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val userInfo = snapshot.getValue<Users>()

                        createdRecipeVersionState.value = CreatedRecipeVersionState.Default(recipeList, userInfo)

                    }

                    override fun onCancelled(error: DatabaseError) {

                        createdRecipeVersionState.value = CreatedRecipeVersionState.Error

                    }
                }

                databaseRef
                    .child("users")
                    .child(auth.currentUser?.uid.toString())
                    .addListenerForSingleValueEvent(objectListener)


            }

            override fun onCancelled(error: DatabaseError) {

                createdRecipeVersionState.value = CreatedRecipeVersionState.Error

            }

        }
        databaseRef
            .child("Recipe")
            .child(recipeID)
            .child("Versions")
            .addValueEventListener(listener)
    }

}