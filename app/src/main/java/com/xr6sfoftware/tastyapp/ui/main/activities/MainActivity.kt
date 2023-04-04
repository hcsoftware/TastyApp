package com.xr6sfoftware.tastyapp.ui.main.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xr6sfoftware.tastyapp.R
import com.xr6sfoftware.tastyapp.repositories.RecipesRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {

    @Inject lateinit var recipesRepository: RecipesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = resources.getString(R.string.main_act_title)
    }

}

