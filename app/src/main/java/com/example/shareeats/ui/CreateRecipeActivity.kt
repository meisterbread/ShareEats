package com.example.shareeats.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareeats.R
import com.example.shareeats.databinding.ActivityCreateRecipeBinding

class CreateRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){

        }
    }
}