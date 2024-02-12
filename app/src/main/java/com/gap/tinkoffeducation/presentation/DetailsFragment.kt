package com.gap.tinkoffeducation.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gap.tinkoffeducation.R
import com.gap.tinkoffeducation.databinding.FragmentDetailsBinding
import com.gap.tinkoffeducation.presentation.viewModels.DetailsViewModel
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw RuntimeException("DetailsFragment == null")
    private var id: Int = UNDEFINED_ID

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = requireArguments().getInt(ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workWithUI()
        workWithViewModel()
    }

    private fun workWithUI() {
        hideActivityUI()
        binding.ivbBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnRetryInternet.setOnClickListener {
            viewModel.loadData(id)
        }
    }

    private fun workWithViewModel() {
        viewModel.loadData(id)
        viewModel.filmLD.observe(viewLifecycleOwner) {

            with(binding) {
                Picasso.get().load(it.poster).into(imPoster)
                tvCountries.text = it.countries
                tvGenres.text = it.genres.replaceFirstChar { it.uppercase() }
                tvDescription.text = it.description
                tvName.text = it.name

            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.filmsStateLD.observe(viewLifecycleOwner) { it ->
            with(binding) {
                val listViews = listOf(
                    tvName, imPoster, tvDescription,
                    tvGenresMain, tvGenres, tvCountriesMain, tvCountries
                )
                if (!it) {
                    llNoInternet.visibility = View.VISIBLE
                    listViews.forEach {
                        it.visibility = View.INVISIBLE
                    }
                } else {
                    llNoInternet.visibility = View.GONE
                    listViews.forEach {
                        it.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun hideActivityUI() {
        val favouriteButton =
            requireActivity().findViewById<Button>(R.id.btn_favourite)
        favouriteButton.visibility = View.GONE
        val featuredButton =
            requireActivity().findViewById<Button>(R.id.btn_featured)
        featuredButton.visibility = View.GONE
        val toolbar =
            requireActivity().findViewById<AppBarLayout>(R.id.appBarLayout)
        toolbar.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ID = "id"
        private const val UNDEFINED_ID = -1
        fun newInstance(id: Int): Fragment {
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                }
            }
        }
    }

}