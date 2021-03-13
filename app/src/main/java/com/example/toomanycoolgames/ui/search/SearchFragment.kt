package com.example.toomanycoolgames.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.igdb.exceptions.RequestException
import com.example.toomanycoolgames.R
import com.example.toomanycoolgames.data.TMKGResult
import com.example.toomanycoolgames.databinding.SearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import proto.Game
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)

        viewModel.searchResults.observe(viewLifecycleOwner, { results ->
            when (results) {
                is TMKGResult.Success -> updateResults(results.data)
                is TMKGResult.Error -> showError(results.exception)
            }
            binding.progressGamesList.visibility = View.GONE
        })

        binding.apply {
            searchView.queryHint = checkApiTokenFreshness()
            // TODO improve search logic
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    currentGames.adapter = null
                    searchView.clearFocus()
                    if (query == null) {
                        return false
                    }
                    progressGamesList.visibility = View.VISIBLE
                    viewModel.searchForGames(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }

        return binding.root
    }

    private fun updateResults(searchResults: List<Game>) {
        binding.currentGames.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = IGDBSearchResultAdapter(searchResults)
        }
    }

    private fun showError(exception: RequestException) {
        binding.textError.apply {
            visibility = View.VISIBLE
            text = getString(R.string.formatErrorWithMessage, exception.statusCode.toString())
        }
    }

    private fun checkApiTokenFreshness(): String {
        val regeneratedDate = 1614066539L // REPLACE when regenerated
        val expiresIn = 4890271L // REPLACE when regenerated
        val expireDate = regeneratedDate + expiresIn
        val currentMillis = Calendar.getInstance().timeInMillis

        return if (currentMillis >= expireDate) {
            "API Key expired"
        } else {
            "Access Token expires in ${(expireDate - currentMillis) / 86_400} days"
        }
    }
}