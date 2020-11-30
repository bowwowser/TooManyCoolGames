package com.example.toomanycoolgames.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toomanycoolgames.R
import com.example.toomanycoolgames.TMKGApplication
import com.example.toomanycoolgames.data.Result
import com.example.toomanycoolgames.databinding.SearchFragmentBinding
import proto.Game

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory((activity?.application as TMKGApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)

        viewModel.searchResults.observe(viewLifecycleOwner, { results ->
            binding.progressGamesList.visibility = View.GONE
            when (results) {
                is Result.Success<List<Game>> -> initializeViews(results.data)
                is Result.Error -> showError(results.exception)
            }
        })

        return binding.root
    }

    private fun initializeViews(searchResults: List<Game>) {
        binding.currentGames.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = IGDBSearchResultAdapter(searchResults)
        }
    }

    private fun showError(exception: Exception) {
        binding.textError.apply {
            visibility = View.VISIBLE
            text = getString(R.string.formatErrorWithMessage, exception.message)
        }
    }
}