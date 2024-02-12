package com.gap.tinkoffeducation.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.gap.tinkoffeducation.R
import com.gap.tinkoffeducation.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (initiallyStateInternet()) {
            setOnButtonClickListener()
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }

    }

    private fun initiallyStateInternet(): Boolean {
        val state = isNetworkAvailable()
        if (!state) {
            noInternetOnUser()
        } else {
            yesInternetOnUser()
        }
        return state
    }

    private fun noInternetOnUser() {
        with(binding) {
            btnFeatured.visibility = View.INVISIBLE
            btnFavourite.visibility = View.INVISIBLE
            llNoInternet.visibility = View.VISIBLE
            btnRetryInternet.setOnClickListener {
                initiallyStateInternet()
            }
        }
    }

    private fun yesInternetOnUser() {
        with(binding) {
            btnFeatured.visibility = View.VISIBLE
            btnFavourite.visibility = View.VISIBLE
            llNoInternet.visibility = View.GONE
        }
        launchFragment(FavouriteFragment())
    }


    private fun setOnButtonClickListener() {
        with(binding) {
            btnFavourite.setOnClickListener {
                launchFragment(FavouriteFragment())
                setTitle(getString(R.string.favourites))
            }
            btnFeatured.setOnClickListener {
                launchFragment(FeaturedFragment())
                setTitle(getString(R.string.featured))
            }
        }
    }


    private fun setTitle(title: String) {
        binding.topAppBar.title = title
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_fragment_container,
                fragment
            )
            .commit()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onBackPressed() {
        val favouriteButton =
            findViewById<Button>(R.id.btn_favourite)
        favouriteButton.visibility = View.VISIBLE
        val featuredButton =
            findViewById<Button>(R.id.btn_featured)
        featuredButton.visibility = View.VISIBLE
        val toolbar =
            findViewById<AppBarLayout>(R.id.appBarLayout)
        toolbar.visibility = View.VISIBLE
        super.onBackPressed()
    }

}