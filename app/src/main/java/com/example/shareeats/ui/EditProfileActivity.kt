package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareeats.R
import com.example.shareeats.databinding.ActivityEditProfileBinding
import com.example.shareeats.databinding.ActivityProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityEditProfileBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, EditProfileActivity::class.java))
    }

}