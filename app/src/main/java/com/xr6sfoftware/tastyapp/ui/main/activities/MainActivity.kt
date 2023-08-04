package com.xr6sfoftware.tastyapp.ui.main.activities

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import com.xr6sfoftware.tastyapp.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = resources.getString(R.string.main_act_title)
    }

    override fun onBackPressed() {

        val navController: NavController =
            Navigation.findNavController(this, R.id.ma_fragment_container)
        val id = navController.currentDestination!!.id
        if (id == R.id.searchFragment) { // change the fragment id
            showQuitDialog()
        } else if (id == R.id.recipeFragment) { // change the fragment id
            navController.popBackStack()
        }

    }


    private fun showQuitDialog() {

        val contentHolder = ViewHolder(R.layout.dialog_quit_app)

        val dialog = DialogPlus.newDialog(this)
            .setGravity(Gravity.BOTTOM)
            .setOnClickListener { dialog, view -> when (view.id) {
                R.id.continue_button -> {dialog.dismiss()}
                R.id.close_button -> {finish()}
            } }
            .setHeader(R.layout.dialog_header)
            .setContentHolder(contentHolder)
            .create()
        dialog.show()

    }


    val FragmentManager.currentNavigationFragment: Fragment?
        get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()


}

