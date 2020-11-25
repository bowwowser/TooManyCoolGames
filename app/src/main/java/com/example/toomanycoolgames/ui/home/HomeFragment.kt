package com.example.toomanycoolgames.ui.home

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
import com.example.toomanycoolgames.databinding.HomeFragmentBinding
import proto.Game

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((activity?.application as TMKGApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        viewModel.searchResults.observe(viewLifecycleOwner, { results ->
            when (results) {
                is Result.Success<List<Game>> -> initializeViews(results.data)
                is Result.Error -> showError(results.exception)
            }
        })

        return binding.root
    }

    private fun showError(exception: Exception) {
        binding.textError.visibility = View.VISIBLE
        binding.textError.text = getString(R.string.formatErrorWithMessage, exception.message)
    }

    private fun initializeViews(
        searchResults: List<Game>
    ) {
        binding.progressGamesList.visibility = View.GONE
        binding.currentGames.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = GamesListAdapter(searchResults)
        }
    }
}