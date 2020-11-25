package com.example.toomanycoolgames.ui.info

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.navArgs
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.bumptech.glide.Glide
import com.example.toomanycoolgames.R
import com.example.toomanycoolgames.databinding.InfoActivityFunBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: InfoActivityFunBinding
    private val args: InfoActivityArgs by navArgs()
    private val infoViewModel: InfoViewModel by viewModels(
        factoryProducer = { SavedStateViewModelFactory(application, this, args.toBundle()) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        infoViewModel.gameInfo.observe(this, { game ->
            binding = DataBindingUtil.setContentView(this, R.layout.info_activity_fun)
            binding.apply {
                lifecycleOwner = this@InfoActivity
                viewModel = infoViewModel

                Glide.with(root.context)
                    .load(imageBuilder(game.coverId, ImageSize.HD, ImageType.PNG))
                    .into(infoGameCover)
                infoProgressLoad.visibility = View.INVISIBLE
            }
        })
    }
}