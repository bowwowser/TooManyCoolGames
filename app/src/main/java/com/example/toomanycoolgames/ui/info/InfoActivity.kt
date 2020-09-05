package com.example.toomanycoolgames.ui.info

import ImageSize
import ImageType
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.toomanycoolgames.R
import com.example.toomanycoolgames.data.Result
import com.example.toomanycoolgames.databinding.InfoActivityBasicBinding
import imageBuilder
import proto.Game

class InfoActivity : AppCompatActivity() {

    private var _binding: InfoActivityBasicBinding? = null
    private val binding get() = _binding!!

    private val args: InfoActivityArgs by navArgs()
    private val viewModel: InfoViewModel by viewModels(
        factoryProducer = { SavedStateViewModelFactory(application, this, args.toBundle()) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = InfoActivityBasicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.gameInfo.observe(this, Observer { infoResult ->
            when (infoResult) {
                is Result.Success -> initializeViews(infoResult.data)
                is Result.Error -> showError(infoResult.exception)
            }
        })
    }

    private fun initializeViews(game: Game) {
        binding.apply {
            infoToolbar.title = game.name
            infoSummary.text = game.summary

            Glide.with(root.context)
                .load(imageBuilder(game.cover.imageId, ImageSize.COVER_BIG, ImageType.PNG))
                .into(infoCover)
        }
    }

    private fun showError(exception: Exception) {
        binding.infoSummary.text = getString(R.string.formatErrorWithMessage, exception.message)
    }
}