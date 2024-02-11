package com.gap.tinkoffeducation

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
            }
            btnFeatured.setOnClickListener {
                launchFragment(FeaturedFragment())
            }
        }
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

}