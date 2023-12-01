package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shareeats.R
import com.example.shareeats.databinding.ActivityEditProfileBinding
import com.example.shareeats.databinding.ActivityProfileBinding
import com.example.shareeats.model.Users
import com.example.shareeats.states.EditProfileState
import com.example.shareeats.viewmodel.EditProfileViewModel
import java.io.ByteArrayOutputStream

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditProfileBinding

    private lateinit var viewModel : EditProfileViewModel

    private var userInfo : Users? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityEditProfileBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = EditProfileViewModel()

        viewModel.getState().observe(this@EditProfileActivity){

            handleState(it)

        }

        val galleryIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
            if(it.resultCode == RESULT_OK){
                val imageUri = it.data?.data
                val imageStream = imageUri?.let { it1 -> contentResolver.openInputStream(it1) }
                val photo = BitmapFactory.decodeStream(imageStream)
                binding.ivProfile.setImageBitmap(photo)
            }
        })

        binding.ivProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            galleryIntentLauncher.launch(intent)
        }

        binding.btnSave.setOnClickListener {

            if (checkPrompt()) {

                val newName = binding.tieName.text.toString()
                val newBio = binding.tieBio.text.toString()

                val bitmap = (binding.ivProfile.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

                viewModel.overrideData(baos.toByteArray(), newName, newBio)
            }

        }

        binding.btnCancel.setOnClickListener {

            ProfileActivity.launch(this@EditProfileActivity)
            finish()

        }


    }

    private fun handleState(it: EditProfileState?) {

        when(it){

            is EditProfileState.Default ->{

                Glide.with(this@EditProfileActivity)
                    .load(it.userInfo?.imageURL)
                    .apply(RequestOptions().centerCrop().override(200, 200))
                    .into(binding.ivProfile)

                binding.tieName.setText(it.userInfo?.name)
                binding.tieBio.setText(it.userInfo?.bio)



            }

            EditProfileState.EditProfileSuccess ->{

                ProfileActivity.launch(this@EditProfileActivity)
                finish()

            }

            EditProfileState.Error -> {



            }


            else -> {}
        }


    }

    private fun checkPrompt() : Boolean {

        var errorCount = 0

        if(binding.tieName.text.isNullOrEmpty()){

            errorCount++
            binding.tieName.error = "This field is required."

        }

        if(binding.tieBio.text.isNullOrEmpty()){

            errorCount++
            binding.tieBio.error = "This field is required."

        }


        if (errorCount <= 0) {

            return true
            Toast.makeText(this, "Profile Edit Success.", Toast.LENGTH_SHORT).show()

        }
        else

        {


        }

        return false

    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, EditProfileActivity::class.java))
    }

}