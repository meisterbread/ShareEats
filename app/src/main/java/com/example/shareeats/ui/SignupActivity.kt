package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareeats.R
import com.example.shareeats.databinding.ActivitySignupBinding
import com.example.shareeats.states.AuthenticationStates
import com.example.shareeats.viewmodel.AuthenticationViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding
    private lateinit var viewModel : AuthenticationViewModel

    private var auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AuthenticationViewModel()
        viewModel.getStates().observe(this@SignupActivity) {
            handleState(it)
        }

        binding.btnSignup.setOnClickListener {
            viewModel.signUp(
                binding.tieEmail.text.toString(),
                binding.tiePassword.text.toString(),
            )
        }

    }

    private fun handleState(state : AuthenticationStates) {
        when(state) {
            is AuthenticationStates.SignedUp -> auth.currentUser?.let {
                viewModel.createUserRecord(
                    binding.tieName.text.toString(),
                    binding.tieEmail.text.toString(),
                )
            }

            is AuthenticationStates.ProfileUpdated -> {
                MainActivity.launch(this@SignupActivity)
                finish()
            }
            else -> {}
        }
    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, SignupActivity::class.java))
    }
}