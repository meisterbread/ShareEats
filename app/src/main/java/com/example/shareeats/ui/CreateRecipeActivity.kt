package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
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

        binding.btnCancel.setOnClickListener{

            MainActivity.launch(this@CreateRecipeActivity)
            finish()

        }

    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, CreateRecipeActivity::class.java))
    }
}