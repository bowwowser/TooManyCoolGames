package com.example.toomanycoolgames.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toomanycoolgames.data.Result
import com.example.toomanycoolgames.databinding.HomeFragmentBinding
import proto.Game

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        val model: HomeViewModel by viewModels()
        model.searchResults.observe(viewLifecycleOwner, Observer { results ->
            when (results) {
                is Result.Success<List<Game>> -> initializeViews(results.data)
                is Result.Error -> showError(results.exception)
            }
        })

        return binding.root
    }

    private fun showError(exception: Exception) {
        binding.textError.visibility = View.VISIBLE
        binding.textError.text = "Error!!!\n${exception.message}"
    }

    private fun initializeViews(
        searchResults: List<Game>
    ) {
        val gamesAdapter: RecyclerView.Adapter<*> = GamesListAdapter(searchResults)
        binding.currentGames.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = gamesAdapter
        }
    }
}