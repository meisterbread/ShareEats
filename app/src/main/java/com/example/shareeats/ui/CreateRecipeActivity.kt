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
import com.example.shareeats.databinding.ActivityCreateRecipeBinding
import com.example.shareeats.model.Users
import com.example.shareeats.states.CreateRecipeState
import com.example.shareeats.viewmodel.CreateRecipeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream

class CreateRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateRecipeBinding

    private lateinit var createRecipeViewModel: CreateRecipeViewModel

    private var auth = Firebase.auth
    private var databaseRef = Firebase.database.reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createRecipeViewModel = CreateRecipeViewModel()

        createRecipeViewModel.getState().observe(this@CreateRecipeActivity){

            renderUIState(it)

        }

        binding.btnCancel.setOnClickListener{

            MainActivity.launch(this@CreateRecipeActivity)
            finish()

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


            createRecipeViewModel.addRecipe(
                baos.toByteArray(),
                binding.tieRecipeName.text.toString(),
                binding.tieCookingTime.text.toString(),
                binding.tieIngredients.text.toString(),
                binding.tieInstructions.text.toString(),
                userID)

            createRecipeViewModel.addRecipeToUser(
                baos.toByteArray(),
                binding.tieRecipeName.text.toString(),
                binding.tieCookingTime.text.toString(),
                binding.tieIngredients.text.toString(),
                binding.tieInstructions.text.toString(),
                userID)

        }

    }

    private fun renderUIState(it: CreateRecipeState?) {

        when(it) {

            is CreateRecipeState.Success -> {

                MainActivity.launch(this@CreateRecipeActivity)

            }

            is CreateRecipeState.Error -> {



            }

            else -> {}
        }


    }


    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, CreateRecipeActivity::class.java))
    }
}