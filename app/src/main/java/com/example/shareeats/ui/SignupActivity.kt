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
import com.example.shareeats.databinding.ActivitySignupBinding
import com.example.shareeats.states.AuthenticationStates
import com.example.shareeats.viewmodel.AuthenticationViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream

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

        val galleryIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
            if(it.resultCode == RESULT_OK){
                val imageUri = it.data?.data
                val imageStream = imageUri?.let { it1 -> contentResolver.openInputStream(it1) }
                val photo = BitmapFactory.decodeStream(imageStream)
                binding.ivAddImage.setImageBitmap(photo)
            }
        })

        binding.ivAddImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            galleryIntentLauncher.launch(intent)
        }

    }

    private fun handleState(state : AuthenticationStates) {
        when(state) {
            is AuthenticationStates.SignedUp -> auth.currentUser?.let {

                if (binding.ivAddImage != null){

                    binding.ivAddImage.setImageResource(R.drawable.user_default)

                }
                    val bitmap = (binding.ivAddImage.drawable as BitmapDrawable).bitmap
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)


                viewModel.createUserRecord(
                    auth.currentUser?.uid.toString(),
                    baos.toByteArray(),
                    binding.tieName.text.toString(),
                    binding.tieEmail.text.toString(),
                    binding.tieBio.text.toString()
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