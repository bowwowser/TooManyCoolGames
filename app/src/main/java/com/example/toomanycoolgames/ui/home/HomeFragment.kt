package com.example.toomanycoolgames.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toomanycoolgames.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        initializeViews(root)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    private fun initializeViews(root: View) {
        val gamesLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val gamesAdapter: RecyclerView.Adapter<*> =

        val listGames = root.findViewById<RecyclerView>(R.id.list_current_games).apply {
            setHasFixedSize(true)
            layoutManager = gamesLayoutManager

        }
    }
}