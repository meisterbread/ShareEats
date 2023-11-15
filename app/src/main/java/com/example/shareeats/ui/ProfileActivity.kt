package com.example.shareeats.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareeats.R
import com.example.shareeats.databinding.ActivityProfileBinding
import com.example.shareeats.viewmodel.AuthenticationViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var viewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
}