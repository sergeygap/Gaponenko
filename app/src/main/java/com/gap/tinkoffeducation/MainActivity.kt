package com.gap.tinkoffeducation

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gap.tinkoffeducation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnButtonClickListener()
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

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}