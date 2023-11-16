package com.example.shareeats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users
import com.example.shareeats.states.CreateRecipeState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class CreateRecipeViewModel : ViewModel() {

    private val createRecipeState = MutableLiveData<CreateRecipeState>()

    private var databaseRef = Firebase.database.reference
    private var storageRef = Firebase.storage.reference

    private var auth = Firebase.auth

    fun getState() : LiveData<CreateRecipeState> = createRecipeState

    fun getUserData(){

        val objectListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val userInfo = snapshot.getValue<Users>()

                createRecipeState.value = CreateRecipeState.GetDataSuccess(userInfo)

            }

            override fun onCancelled(error: DatabaseError) {

                createRecipeState.value = CreateRecipeState.Error

            }
        }

        databaseRef.child("users/" + auth.currentUser?.uid).addListenerForSingleValueEvent(objectListener)


    }

    fun addRecipe(img : ByteArray,
                  name : String,
                  cookingTime : String,
                  ingredients : String,
                  instructions : String,
                  createdBy : String) {

        val databaseRef = databaseRef.child("Recipe").push()
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

                    createRecipeState.value = CreateRecipeState.Success

                } .addOnFailureListener {

                    createRecipeState.value = CreateRecipeState.Error

                }

            }

        }




    }



}