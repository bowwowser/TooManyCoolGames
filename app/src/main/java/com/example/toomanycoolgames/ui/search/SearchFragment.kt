package com.example.toomanycoolgames.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.toomanycoolgames.R
import com.example.toomanycoolgames.data.api.checkApiTokenFreshness
import com.example.toomanycoolgames.databinding.SearchFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by activityViewModels()
    private lateinit var resultPagesAdapter: SearchResultPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = searchViewModel
        }

        initializeViews()
        return binding.root
    }

    private fun initializeViews() {
        bindSearchView()
        bindTabPager()

        searchViewModel.searchException.observe(viewLifecycleOwner, this::showError)
        // TODO hack; find better way to clear progress circle
        searchViewModel.gamesTabTitle.observe(viewLifecycleOwner) {
            binding.progressGamesList.visibility = View.GONE
        }
    }

    // TODO improve search logic
    private fun bindSearchView() = binding.apply {
        searchView.queryHint = checkApiTokenFreshness()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return if (query == null) {
                    false
                } else {
                    startSearch(query)
                    true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun startSearch(query: String) {
        binding.apply {
            textError.text = ""
            progressGamesList.visibility = View.VISIBLE
        }
        searchViewModel.searchForGames(query)
    }

    private fun bindTabPager() = binding.apply {
        resultPagesAdapter = SearchResultPagerAdapter(this@SearchFragment)
        pagerResults.adapter = resultPagesAdapter
        TabLayoutMediator(tabsResults, pagerResults) { tab, position ->
            when (position) {
                0 -> searchViewModel.gamesTabTitle.observe(viewLifecycleOwner) { tab.text = it }
                1 -> searchViewModel.othersTabTitle.observe(viewLifecycleOwner) { tab.text = it }
            }
        }.attach()
    }

    private fun showError(exception: Exception) {
        // TODO change to persistent Snackbar
        binding.textError.apply {
            visibility = View.VISIBLE
            text = getString(R.string.formatErrorWithMessage, exception.toString())
        }
    }
}