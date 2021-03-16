package com.example.toomanycoolgames.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.imageBuilder
import com.bumptech.glide.Glide
import com.example.toomanycoolgames.R
import com.example.toomanycoolgames.data.room.TMKGGame
import com.example.toomanycoolgames.databinding.ItemGameBinding

class TMKGGameListAdapter(private val games: List<TMKGGame>) :
    ListAdapter<TMKGGame, TMKGGameListAdapter.GameHolder>(GameComparator()) {

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

        fun bind(game: TMKGGame) {
            gameBinding.apply {
                gameName.text = game.name

                // TODO improve code logic here
                // avoids errors, but probs more idiomatic way to handle this
                Glide.with(root.context)
                    .load(
                        if (game.coverId.isBlank()) null else imageBuilder(
                            game.coverId,
                            ImageSize.COVER_BIG
                        )
                    )
                    .fallback(R.drawable.ic_baseline_bookmark_border_24)
                    .into(gameCover)

                gameItem.setOnClickListener { view ->
                    view.findNavController()
                        .navigate(HomeFragmentDirections.viewTrackedGameInfo(game.igdbId))
                }
            }
        }
    }

    class GameComparator : DiffUtil.ItemCallback<TMKGGame>() {
        override fun areItemsTheSame(oldItem: TMKGGame, newItem: TMKGGame): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TMKGGame, newItem: TMKGGame): Boolean {
            return oldItem.gameId == newItem.gameId
        }
    }
}