package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.example.shareeats.databinding.ActivitySigninBinding
import com.example.shareeats.states.AuthenticationStates
import com.example.shareeats.viewmodel.AuthenticationViewModel

class SigninActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySigninBinding
    private lateinit var viewModel : AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = AuthenticationViewModel()
        viewModel.getStates().observe(this@SigninActivity){
            renderUi(it)
        }

        viewModel.isSignedIn()

        with(binding){
            btnSignin.setOnClickListener {
                viewModel.signIn(
                    binding.tieEmail.text.toString(),
                    binding.tiePass.text.toString()
                )
            }

            tvSignup.apply {
                text = addClickableLink("Don't have an account? Sign up", "Sign up"){
                    SignupActivity.launch(this@SigninActivity)
                }
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    private fun renderUi(states : AuthenticationStates) {
        when(states) {
//            is AuthenticationStates.IsSignedIn -> {
//                if(states.isSignedIn) {
//                    MainActivity.launch(this@SigninActivity)
//                    finish()
//                }
//            }
            is AuthenticationStates.SignedIn -> {
                MainActivity.launch(this@SigninActivity)
                finish()
            }
            AuthenticationStates.Error -> {}
            else -> {}
        }
    }

    private fun addClickableLink(fullText: String, linkText: String, callback: () -> Unit): SpannableString {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                callback.invoke()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.typeface = Typeface.DEFAULT_BOLD
            }

        }

        val startIndex = fullText.indexOf(linkText, 0, true)

        return SpannableString(fullText).apply {
            setSpan(clickableSpan, startIndex, startIndex + linkText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, SigninActivity::class.java))
    }
}