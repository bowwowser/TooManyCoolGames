package com.example.toomanycoolgames.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toomanycoolgames.data.room.TMKGGame
import com.example.toomanycoolgames.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        viewModel.trackedGames.observe(viewLifecycleOwner, this::initializeViews)

        return binding.root
    }

    // TODO: data binding?
    private fun initializeViews(games: List<TMKGGame>) {
        binding.apply {
            progressGamesList.visibility = View.GONE
            currentGames.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = TMKGGameListAdapter(games)
            }
        }
    }
}