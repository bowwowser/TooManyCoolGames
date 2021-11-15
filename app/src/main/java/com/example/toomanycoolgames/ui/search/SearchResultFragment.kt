package com.example.toomanycoolgames.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.databinding.FragmentSearchResultBinding
import com.example.toomanycoolgames.ui.home.TMKGGameReleaseListAdapter
import kotlinx.coroutines.flow.emptyFlow

class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey("resultType") }?.apply {
            val resultObservable = when (getInt("resultType")) {
                ResultType.GAMES.ordinal -> viewModel.gamesResults
                ResultType.OTHER.ordinal -> viewModel.otherResults
                else -> emptyFlow() // TODO improve catch-all logic
            }.asLiveData()
            resultObservable.observe(viewLifecycleOwner, this@SearchResultFragment::displayResults)
        }
    }

    private fun displayResults(results: List<TMKGGameRelease>) {
        binding.resultsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = TMKGGameReleaseListAdapter(results)
        }
    }
}

class SearchResultPagerAdapter(hostFragment: Fragment) : FragmentStateAdapter(hostFragment) {
    override fun getItemCount(): Int = ResultType.values().size

    override fun createFragment(position: Int): Fragment {
        return SearchResultFragment().apply {
            arguments = Bundle().apply {
                putInt("resultType", position)
            }
        }
    }
}

enum class ResultType {
    GAMES,
    OTHER
}