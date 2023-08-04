package com.xr6sfoftware.tastyapp.ui.main.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.xr6sfoftware.tastyapp.R
import com.xr6sfoftware.tastyapp.databinding.SearchFragmentBinding
import com.xr6sfoftware.tastyapp.databinding.SplashScreenBinding

class SplashScreen :  AppCompatActivity() {

    private lateinit var viewBinding : SplashScreenBinding
    private lateinit var animationArray: Array<Animation>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        //setContentView(R.layout.splash_screen)


        animationArray = arrayOf(
            AnimationUtils.loadAnimation(this, R.anim.top_animation),
            AnimationUtils.loadAnimation(this, R.anim.middle_animation),
            AnimationUtils.loadAnimation(this, R.anim.bottom_animation),
        )

        viewBinding.text1.startAnimation(animationArray.get(0))
        viewBinding.text2.startAnimation(animationArray.get(1))
        viewBinding.text3.startAnimation(animationArray.get(2))

        viewBinding.text1.animation.setAnimationListener (object: Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        val mainActivityIntent : Intent = Intent(this@SplashScreen, MainActivity::class.java)
                        startActivity(mainActivityIntent)
                        finish()
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                        TODO("Not yet implemented")
                    }
                }
            )


    }

}