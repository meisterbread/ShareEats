package com.example.shareeats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shareeats.model.Users
import com.example.shareeats.states.ProfileStates
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProfileViewModel {

    private val profileState = MutableLiveData<ProfileStates>()

    private var databaseRef = Firebase.database.reference
    private var storageRef = Firebase.storage.reference
    private var auth = Firebase.auth



    fun getStates(): LiveData<ProfileStates> = profileState

    init {

        val objectListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val userInfo = snapshot.getValue<Users>()

                profileState.value = ProfileStates.Default(userInfo)

            }

            override fun onCancelled(error: DatabaseError) {

                profileState.value = ProfileStates.Error

            }
        }

        databaseRef
            .child("users")
            .child(auth.currentUser?.uid.toString())
            .addListenerForSingleValueEvent(objectListener)



    }
}