package com.xr6sfoftware.tastyapp.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.xr6sfoftware.tastyapp.R
import com.xr6sfoftware.tastyapp.databinding.SplashScreenBinding
import com.xr6sfoftware.tastyapp.ui.main.activities.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
@author Hernán Carrera
@version 1.0
Splash Animation Activity for App init.
 */
class SplashScreen :  AppCompatActivity() {

    private lateinit var viewBinding : SplashScreenBinding
    private lateinit var animationArray: Array<Animation>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        animationArray = arrayOf(
            AnimationUtils.loadAnimation(this, R.anim.splash_title_animation),
            AnimationUtils.loadAnimation(this, R.anim.splash_logo_animation),
        )

        viewBinding.splashScreenText.startAnimation(animationArray.get(0))
        viewBinding.splashScreenImage.startAnimation(animationArray.get(1))

        viewBinding.splashScreenImageFood0.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_food_anim_right_to_left))
        viewBinding.splashScreenImageFood1.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_food_anim_left_to_right))
        viewBinding.splashScreenImageFood2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_food_anim_right_to_left))

        viewBinding.splashScreenImage.animation.setAnimationListener (object: Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation?) {
                        //TODO("Not yet implemented")
                    }
                    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                    override fun onAnimationEnd(animation: Animation?) {
                        lifecycleScope.launch{
                            delay(500)

                            val mainActivityIntent : Intent = Intent(this@SplashScreen, MainActivity::class.java)
                            startActivity(mainActivityIntent)
                            overridePendingTransition(R.anim.slide_out_bottom,R.anim.slide_in_bottom)
                            finish()
                        }
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                        //TODO("Not yet implemented")
                    }
                }
            )


    }

}