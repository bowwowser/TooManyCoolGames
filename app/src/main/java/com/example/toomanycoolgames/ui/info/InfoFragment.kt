package com.example.toomanycoolgames.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.bumptech.glide.Glide
import com.example.toomanycoolgames.databinding.InfoActivityFunBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment() {

    private var _binding: InfoActivityFunBinding? = null
    private val binding get() = _binding!!
    private val infoViewModel: InfoViewModel by viewModels()

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

        infoViewModel.gameInfo.observe(requireActivity(), { game ->
            binding.apply {
                Glide.with(root.context)
                    .load(imageBuilder(game.coverId, ImageSize.HD, ImageType.PNG))
                    .into(infoGameCover)
                infoProgressLoad.visibility = View.INVISIBLE
            }
        })

        return binding.root
    }
}