package com.example.shareeats.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.example.shareeats.R
import java.util.Timer
import kotlin.concurrent.schedule

class SplashActivity() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SplashActivity> {
        override fun createFromParcel(parcel: Parcel): SplashActivity {
            return SplashActivity(parcel)
        }

        override fun newArray(size: Int): Array<SplashActivity?> {
            return arrayOfNulls(size)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Timer().schedule(3000) {
            startActivity(Intent(this@SplashActivity, MainMenuActivity::class.java))
            finish()
        }
    }
}