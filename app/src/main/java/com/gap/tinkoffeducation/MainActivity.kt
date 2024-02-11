package com.gap.tinkoffeducation

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gap.tinkoffeducation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initiallyStateInternet()
        setOnButtonClickListener()

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
            .addToBackStack(null)
            .commit()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}