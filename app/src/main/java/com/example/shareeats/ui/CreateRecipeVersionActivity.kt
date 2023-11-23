package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.shareeats.R
import com.example.shareeats.databinding.ActivityCreateRecipeVersionBinding
import com.example.shareeats.databinding.ActivityCreatedRecipeBinding
import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users
import com.example.shareeats.states.CreateRecipeState
import com.example.shareeats.states.CreateRecipeVersionState
import com.example.shareeats.viewmodel.CreateRecipeVersionViewModel
import com.example.shareeats.viewmodel.RecipeDetailsViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream

class CreateRecipeVersionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateRecipeVersionBinding

    private var recipe: Recipe? = null
    private var userInfo: Users? = null

    private var auth = Firebase.auth

    private lateinit var recipeVersionViewModel: CreateRecipeVersionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityCreateRecipeVersionBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recipe = intent.getParcelableExtra("Recipe")
        userInfo = intent.getParcelableExtra("userName")

        var recipeID = recipe?.id.toString()

        recipeVersionViewModel = CreateRecipeVersionViewModel()
        recipeVersionViewModel.getState().observe(this@CreateRecipeVersionActivity){

            handleState(it)

        }

        val galleryIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
            if(it.resultCode == RESULT_OK){
                val imageUri = it.data?.data
                val imageStream = imageUri?.let { it1 -> contentResolver.openInputStream(it1) }
                val photo = BitmapFactory.decodeStream(imageStream)
                binding.imageView.setImageBitmap(photo)
            }
        })

        binding.imageView.setImageResource(R.drawable.share_eat_logo)

        binding.imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            galleryIntentLauncher.launch(intent)
        }


        binding.btnAdd.setOnClickListener {

            val bitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

            val userID = auth.currentUser?.uid.toString()

            recipeVersionViewModel.addRecipeToVersions(
                baos.toByteArray(),
                binding.tieRecipeName.text.toString(),
                binding.tieCookingTime.text.toString(),
                binding.tieIngredients.text.toString(),
                binding.tieInstructions.text.toString(),
                userID,
                recipeID)

            recipeVersionViewModel.addRecipeToUsers(
                baos.toByteArray(),
                binding.tieRecipeName.text.toString(),
                binding.tieCookingTime.text.toString(),
                binding.tieIngredients.text.toString(),
                binding.tieInstructions.text.toString(),
                userID,
                recipeID)



        }

    }

    private fun handleState(it: CreateRecipeVersionState?) {
        when(it) {

            is CreateRecipeVersionState.Success -> {

                MainActivity.launch(this@CreateRecipeVersionActivity)

            }

            is CreateRecipeVersionState.Error -> {



            }

            else -> {}
        }
    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, CreateRecipeVersionActivity::class.java))

    }

}