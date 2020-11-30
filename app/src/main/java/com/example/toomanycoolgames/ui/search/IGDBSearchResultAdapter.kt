package com.example.toomanycoolgames.ui.search

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
import proto.Game

class IGDBSearchResultAdapter(private val games: List<Game>) :
    ListAdapter<TMKGGame, IGDBSearchResultAdapter.GameHolder>(GameComparator()) {

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
            gameBinding.apply {
                gameName.text = game.name

                // TODO improve code logic here
                // avoids errors, but probs more idiomatic way to handle this
                Glide.with(root.context)
                    .load(
                        if (game.cover.imageId.isNullOrBlank()) null else imageBuilder(
                            game.cover.imageId,
                            ImageSize.COVER_BIG
                        )
                    )
                    .fallback(R.drawable.ic_baseline_bookmark_border_24)
                    .into(gameCover)

                gameItem.setOnClickListener { view ->
                    view.findNavController()
                        .navigate(SearchFragmentDirections.viewSearchInfo(game.id))
                }
            }
        }
    }

    class GameComparator : DiffUtil.ItemCallback<TMKGGame>() {
        override fun areItemsTheSame(oldItem: TMKGGame, newItem: TMKGGame): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TMKGGame, newItem: TMKGGame): Boolean {
            return oldItem.id == newItem.id
        }
    }
}