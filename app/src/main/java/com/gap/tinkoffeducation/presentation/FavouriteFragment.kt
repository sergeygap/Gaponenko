package com.gap.tinkoffeducation.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gap.tinkoffeducation.R
import com.gap.tinkoffeducation.databinding.FragmentFavouriteBinding
import com.gap.tinkoffeducation.presentation.adapters.FavouriteAdapter
import com.gap.tinkoffeducation.presentation.viewModels.FavouriteViewModel


class FavouriteFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[FavouriteViewModel::class.java]
    }

    private var clickCounter = 0

    private val adapter: FavouriteAdapter by lazy {
        FavouriteAdapter(requireContext())
    }
    private val binding: FragmentFavouriteBinding
        get() = _binding ?: throw RuntimeException("FavouriteFragment == null")
    private var _binding: FragmentFavouriteBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workWithAdapter()
        workWithViewModel()
        workWithSwipeToRefresh()
    }

    private fun workWithAdapter() {
        binding.rvFavourite.adapter = adapter
        adapter.onFilmsClickListener = object :FavouriteAdapter.OnFilmsClickListener {
            override fun onFilmsClick(id: Int) {
                launchFilmsDetailsFragment(id)
            }
        }
        adapter.onReachEndListener = object : FavouriteAdapter.OnReachEndListener {
            override fun onReachEnd() {
                viewModel.getListFilms()
            }
        }
    }
    private fun workWithViewModel() {
        viewModel.getListFilms()
        viewModel.filmsLD.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.filmsStateLD.observe(viewLifecycleOwner) {
            if (!it) {
                binding.rvFavourite.visibility = View.INVISIBLE
                binding.llNoInternet.visibility = View.VISIBLE
                binding.swipeToRefreshLayout.visibility = View.INVISIBLE
                clickCounter = 0
            } else {
                binding.rvFavourite.visibility = View.VISIBLE
                binding.llNoInternet.visibility = View.GONE
                binding.swipeToRefreshLayout.visibility = View.VISIBLE
                binding.btnRetryInternet.setOnClickListener {
                    clickCounter++
                    if (clickCounter == 3) {
                        viewModel.updateFilmsList()
                    } else {
                        viewModel.getListFilms()
                    }
                }
            }
        }
    }

    private fun workWithSwipeToRefresh() {
        binding.swipeToRefreshLayout.setOnRefreshListener {
            viewModel.updateFilmsList()
            binding.swipeToRefreshLayout.isRefreshing = false
        }
    }

    private fun launchFilmsDetailsFragment(id: Int) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, DetailsFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}