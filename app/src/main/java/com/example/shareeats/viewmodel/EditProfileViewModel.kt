package com.example.shareeats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shareeats.model.Users
import com.example.shareeats.states.EditProfileState
import com.example.shareeats.states.ProfileStates
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class EditProfileViewModel {

    private val editState = MutableLiveData<EditProfileState>()

    private var storageRef = Firebase.storage.reference
    private var databaseRef = Firebase.database.reference

    private var auth = Firebase.auth

    fun getState() : LiveData<EditProfileState> = editState



        init {

            val objectListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val userInfo = snapshot.getValue<Users>()

                    editState.value = EditProfileState.Default(userInfo)

                }

                override fun onCancelled(error: DatabaseError) {

                    editState.value = EditProfileState.Error

                }
            }

            databaseRef
                .child("users")
                .child(auth.currentUser?.uid.toString())
                .addListenerForSingleValueEvent(objectListener)



        }

    fun overrideData (img : ByteArray, newUserName : String?, newBio : String?) {

        val editID = auth.currentUser?.uid.toString()

        val storageRef = storageRef.child("User").child("Images").child("$newUserName.jpg")

                        databaseRef
                            .child("users")
                            .child(editID)
                            .child("name")
                            .setValue(newUserName)
                            .addOnSuccessListener {

                                databaseRef
                                    .child("users")
                                    .child(editID)
                                    .child("bio")
                                    .setValue(newBio)
                                    .addOnSuccessListener {

                                        storageRef.putBytes(img).addOnSuccessListener {

                                            storageRef.downloadUrl.addOnSuccessListener {

                                                databaseRef
                                                    .child("users")
                                                    .child(editID)
                                                    .child("imageURL")
                                                    .setValue(it.toString())
                                                    .addOnSuccessListener {


                                                        editState.value = EditProfileState.EditProfileSuccess

                                                    }

                                            }


                                    }.addOnFailureListener {

                                        editState.value = EditProfileState.Error

                                    }

                            }.addOnFailureListener {


                            }

             }
    }
}

