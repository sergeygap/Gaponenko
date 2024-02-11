package com.gap.tinkoffeducation.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gap.tinkoffeducation.databinding.FragmentFavouriteBinding
import com.gap.tinkoffeducation.presentation.adapters.FavouriteAdapter
import com.gap.tinkoffeducation.presentation.viewModels.FavouriteViewModel


class FavouriteFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[FavouriteViewModel::class.java]
    }

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
        adapter.onNewsClickListener = object :FavouriteAdapter.OnNewsClickListener {
            override fun onNewsClick(id: Int) {
                //launchNewsDetailsFragment(id)
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
    }

    private fun workWithSwipeToRefresh() {
        binding.swipeToRefreshLayout.setOnRefreshListener {
            viewModel.updateNewsList()
            binding.swipeToRefreshLayout.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}