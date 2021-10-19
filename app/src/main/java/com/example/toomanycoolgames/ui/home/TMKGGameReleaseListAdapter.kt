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
import com.example.toomanycoolgames.data.model.TMKGGameRelease
import com.example.toomanycoolgames.databinding.ItemGameBinding

class TMKGGameReleaseListAdapter(private val games: List<TMKGGameRelease>) :
    ListAdapter<TMKGGameRelease, TMKGGameReleaseListAdapter.GameHolder>(GameComparator()) {

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

        fun bind(gameValue: TMKGGameRelease) {
            val game = gameValue.game
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

                gameStateEmoji.text =
                    root.context.resources.getStringArray(R.array.play_status_emoji_icons)[game.playStatusPosition]

                gameItem.setOnClickListener { view ->
                    view.findNavController()
                        .navigate(HomeFragmentDirections.viewTrackedGameInfo(game.apiId))
                }
            }
        }
    }

    class GameComparator : DiffUtil.ItemCallback<TMKGGameRelease>() {
        override fun areItemsTheSame(oldItem: TMKGGameRelease, newItem: TMKGGameRelease): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TMKGGameRelease, newItem: TMKGGameRelease): Boolean {
            return oldItem.game.gameId == newItem.game.gameId
        }
    }
}