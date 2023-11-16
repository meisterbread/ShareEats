package com.example.shareeats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users
import com.example.shareeats.states.CreateRecipeState
import com.example.shareeats.states.HomeState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class HomeViewModel {

    private val homeState = MutableLiveData<HomeState>()

    private var databaseRef = Firebase.database.reference
    private var storageRef = Firebase.storage.reference
    private val recipeList = ArrayList<Recipe>()

    private var auth = Firebase.auth

    fun getStates(): LiveData<HomeState> = homeState

    init {

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                recipeList.clear()
                //snapshot - object that handles the current data from the db
                //since snapshot will handle a list, it needs to loop through each record it will retrieve
                //and give to our array list (contactList)

                //needs for loop

                for (data in snapshot.children) { //loops through each object

                    // this will clear the list first before showing the new list
                    // if there will be new contacts added

                    data.getValue<Recipe>()?.let {

                        recipeList.add(it)

                    }

                }

                val objectListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val userInfo = snapshot.getValue<Users>()

                        homeState.value = HomeState.Default(recipeList, userInfo)
                        homeState.value = HomeState.Default(recipeList, userInfo)

                    }

                    override fun onCancelled(error: DatabaseError) {

                        homeState.value = HomeState.Error

                    }
                }

                databaseRef
                    .child("users")
                    .child(auth.currentUser?.uid.toString())
                    .addListenerForSingleValueEvent(objectListener)

                //after retrieving the record, the state will be default


            }

            override fun onCancelled(error: DatabaseError) {

                homeState.value = HomeState.Error

            }

        }
        databaseRef
            .child("Recipe")
            .orderByKey()
            .addValueEventListener(listener)


    }

//    fun getUserData(){
//
//        val objectListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                val userInfo = snapshot.getValue<Users>()
//
//                homeState.value = HomeState.Default(userInfo)
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//                homeState.value = HomeState.Error
//
//            }
//        }
//
//        databaseRef
//            .child("users")
//            .child(auth.currentUser?.uid.toString())
//            .addListenerForSingleValueEvent(objectListener)
//
//
//    }

}