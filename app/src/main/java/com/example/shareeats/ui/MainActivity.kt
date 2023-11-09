package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareeats.databinding.ActivityMainBinding
import com.example.shareeats.states.AuthenticationStates
import com.example.shareeats.viewmodel.AuthenticationViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewmodel : AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel = AuthenticationViewModel()
        viewmodel.getStates().observe(this@MainActivity) {
            handleState(it)
        }

        viewmodel.getUserProfile()

        with(binding){

        }
    }

    private fun handleState(state : AuthenticationStates) {

        when(state) {
            is AuthenticationStates.Default -> {
            }
            AuthenticationStates.Error -> {

            }
            AuthenticationStates.LogOut -> {
                SigninActivity.launch(this@MainActivity)
                finish()
            }
            AuthenticationStates.UserDeleted -> {
                SigninActivity.launch(this@MainActivity)
                finish()
            }
            AuthenticationStates.VerificationEmailSent -> {

            }
            else -> {}
        }
    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}