package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareeats.databinding.ActivityMainMenuBinding
import com.example.shareeats.states.AuthenticationStates
import com.example.shareeats.viewmodel.AuthenticationViewModel

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainMenuBinding
    private lateinit var viewModel : AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainMenuBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = AuthenticationViewModel()
        viewModel.getStates().observe(this@MainMenuActivity){
            renderUi(it)
        }

        binding.btnSignin.setOnClickListener {

            SigninActivity.launch(this@MainMenuActivity)

        }


        binding.btnSignup.setOnClickListener {


            SignupActivity.launch(this@MainMenuActivity)


        }

        viewModel.isSignedIn()




    }

    private fun renderUi(states : AuthenticationStates) {
        when(states) {
            is AuthenticationStates.IsSignedIn -> {
                if (states.isSignedIn) {
                    MainActivity.launch(this@MainMenuActivity)
                    finish()
                }
            }

            AuthenticationStates.Error ->{


            }

            else -> {}
        }

    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, MainMenuActivity::class.java))
    }
}