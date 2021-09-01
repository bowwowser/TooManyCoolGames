package com.example.toomanycoolgames.ui.info

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.bumptech.glide.Glide
import com.example.toomanycoolgames.R
import com.example.toomanycoolgames.data.room.TMKGGameWithReleaseDates
import com.example.toomanycoolgames.databinding.InfoActivityFunBinding
import com.example.toomanycoolgames.logDebug
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment() {

    private var _binding: InfoActivityFunBinding? = null
    private val binding get() = _binding!!
    private val infoViewModel: InfoViewModel by viewModels()
    private lateinit var statusSpinnerAdapter: ArrayAdapter<CharSequence>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = InfoActivityFunBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = this@InfoFragment
            viewModel = infoViewModel
        }

        statusSpinnerAdapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.play_status,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        infoViewModel.gameInfo.observe(requireActivity(), this::initializeViews)

        infoViewModel.isExpanded.observe(requireActivity(), { isExpanded ->
            binding.infobox.cardGameSummary.infoGameSummary.apply {
                if (isExpanded) {
                    maxLines = Int.MAX_VALUE
                    ellipsize = null
                } else {
                    maxLines = 5
                    ellipsize = TextUtils.TruncateAt.END
                }
            }
        })

        return binding.root
    }

    private fun initializeViews(game: TMKGGameWithReleaseDates) {
        binding.apply {
            Glide.with(root.context)
                .load(imageBuilder(game.game.coverId, ImageSize.HD, ImageType.PNG))
                .into(infoGameCover)
            infoProgressLoad.visibility = View.INVISIBLE

            logDebug { "Release date size: ${game.releaseDates.size}" }

            binding.infobox.cardGameReleases.listReleaseDates.apply {
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(context)
                adapter = ReleaseDateAdapter(game.releaseDates)
            }
            binding.infobox.cardGameNotes.spinnerStatus.apply {
                adapter = statusSpinnerAdapter
                setSelection(game.game.playStatusPosition)
            }
        }
    }
}