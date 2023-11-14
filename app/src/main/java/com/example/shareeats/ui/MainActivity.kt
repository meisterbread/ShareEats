package com.example.shareeats.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.shareeats.R
import com.example.shareeats.databinding.ActivityMainBinding
import com.example.shareeats.states.AuthenticationStates
import com.example.shareeats.ui.fragments.FavoritesFragment
import com.example.shareeats.ui.fragments.HomeFragment
import com.example.shareeats.ui.fragments.SearchFragment
import com.example.shareeats.viewmodel.AuthenticationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewmodel : AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment.newInstance())

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){

                R.id.homeFragment -> {

                    replaceFragment(HomeFragment.newInstance())

                    true
                }
                R.id.favoritesFragment -> {

                    replaceFragment(FavoritesFragment.newInstance())

                    true
                }
                R.id.searchFragment ->{

                    replaceFragment(SearchFragment.newInstance())

                    true
                }

                else -> {false}
            }
        }

        binding.btnImg.setOnClickListener {

            CreateRecipeActivity.launch(this@MainActivity)

        }

        viewmodel = AuthenticationViewModel()
        viewmodel.getStates().observe(this@MainActivity) {
            handleState(it)
        }

        viewmodel.getUserProfile()

    }

    private fun handleState(state : AuthenticationStates) {

        when(state) {
            is AuthenticationStates.Default -> {
            }
            AuthenticationStates.Error -> {

            }
            AuthenticationStates.LogOut -> {
                SigninActivity.launch(this@MainActivity)
                finish()
            }
            AuthenticationStates.UserDeleted -> {
                SigninActivity.launch(this@MainActivity)
                finish()
            }
            AuthenticationStates.VerificationEmailSent -> {

            }
            else -> {}
        }
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.host_fragment, fragment)
        fragmentTransaction.commit()

    }

    companion object {
        fun launch(activity : Activity) = activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}