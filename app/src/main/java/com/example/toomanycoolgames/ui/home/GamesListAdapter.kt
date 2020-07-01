package com.example.toomanycoolgames.ui.home

import ImageSize
import ImageType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toomanycoolgames.databinding.ItemGameBinding
import imageBuilder
import proto.Game

class GamesListAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<GamesListAdapter.GameHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val gameBinding =
            ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameHolder(gameBinding)
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(games[position])
    }

    class GameHolder(
        private val gameBinding: ItemGameBinding
    ) : RecyclerView.ViewHolder(gameBinding.root) {

        fun bind(game: Game) {
            gameBinding.gameName.text = game.name

            Glide.with(gameBinding.root.context)
                .load(imageBuilder(game.cover.imageId, ImageSize.COVER_BIG, ImageType.PNG))
                .into(gameBinding.gameCover)
        }
    }
}