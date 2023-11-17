package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shareeats.R
import com.example.shareeats.databinding.ActivityProfileBinding
import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users
import com.example.shareeats.states.AuthenticationStates
import com.example.shareeats.states.ProfileStates
import com.example.shareeats.viewmodel.AuthenticationViewModel
import com.example.shareeats.viewmodel.ProfileViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var authViewModel: AuthenticationViewModel
    private lateinit var profileViewModel : ProfileViewModel

    private var auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        authViewModel = AuthenticationViewModel()
        profileViewModel = ProfileViewModel()

        profileViewModel.getStates().observe(this@ProfileActivity){

            handleState(it)

        }

        authViewModel.getStates().observe(this@ProfileActivity){
            renderUi(it)
        }


        binding.editBtn.setOnClickListener {

            EditProfileActivity.launch(this@ProfileActivity)

        }

        binding.logoutBtn.setOnClickListener {

            authViewModel.logOut()

        }

    }

    private fun handleState(it: ProfileStates?) {

        when(it){

            is ProfileStates.Default -> {


                binding.nameTv.text = it.userInfo?.name
                binding.usernameTv.text = "@${it.userInfo?.username}"
                binding.bioTv.text = it.userInfo?.bio


            }

            is ProfileStates.Error -> {



            }


            else -> {}
        }

    }

    private fun renderUi(states : AuthenticationStates) {
        when(states) {

            is AuthenticationStates.LogOut-> {

                    SigninActivity.launch(this@ProfileActivity)
                    finish()

            }

            AuthenticationStates.Error -> {}
            else -> {}
        }
    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, ProfileActivity::class.java))
    }
}