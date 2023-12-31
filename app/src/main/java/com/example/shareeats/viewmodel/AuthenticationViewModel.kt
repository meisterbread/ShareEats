package com.example.shareeats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shareeats.model.Users
import com.example.shareeats.states.AuthenticationStates
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AuthenticationViewModel: ViewModel() {

    private val auth = Firebase.auth
    private val ref = Firebase.database.reference
    private val storageRef = Firebase.storage.reference
    private val states = MutableLiveData<AuthenticationStates>()

    fun getStates(): LiveData<AuthenticationStates> = states

    fun isSignedIn(){
        states.value = AuthenticationStates.IsSignedIn(auth.currentUser != null)
    }

    fun signUp(email: String, password: String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful) states.value = AuthenticationStates.SignedUp
            else states.value = AuthenticationStates.Error

        }.addOnFailureListener {
            states.value = AuthenticationStates.Error
        }
    }

    fun signIn(email : String, password : String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) states.value = AuthenticationStates.SignedIn
            else states.value = AuthenticationStates.Error

        }.addOnFailureListener {
            states.value = AuthenticationStates.Error
        }
    }

    fun getUserProfile(){
        val objectListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue<Users>()
                states.value = AuthenticationStates.Default(users)
            }

            override fun onCancelled(error: DatabaseError) {
                states.value = AuthenticationStates.Error
            }
        }
        ref.child("users/" + auth.currentUser?.uid).addValueEventListener(objectListener)
    }

    fun logOut() {
        auth.signOut()
        states.value = AuthenticationStates.LogOut
    }

    fun deleteUser() {
        auth.currentUser?.delete()?.addOnCompleteListener {
            if(it.isSuccessful) states.value = AuthenticationStates.UserDeleted
            else states.value = AuthenticationStates.Error

        }?.addOnFailureListener {
            states.value = AuthenticationStates.Error
        }
    }

    fun createUserRecord(
        id: String,
        imageURL: ByteArray,
        name: String,
        username : String,
        email: String,
        bio: String,
        favorites: List<String>?
    ) {

        val storageRef = storageRef.child("User").child("Images").child("$name.jpg")


        storageRef.putBytes(imageURL).addOnSuccessListener {

            storageRef.downloadUrl.addOnSuccessListener {

                // error in case maulit : it.toString() if bytearray, not imageURL.toString().

                val users = Users(
                    id,
                    it.toString(),
                    name,
                    username,
                    email,
                    bio,
                    favorites

                )
                ref.child("users/" + auth.currentUser?.uid).setValue(users).addOnCompleteListener {
                    if (it.isSuccessful) states.value = AuthenticationStates.ProfileUpdated
                    else states.value = AuthenticationStates.Error
                }
            }
        }
    }



}