package com.example.toomanycoolgames.ui.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toomanycoolgames.data.room.TMKGReleaseDate
import com.example.toomanycoolgames.databinding.ItemReleaseDateBinding

class ReleaseDateAdapter(private val releaseDates: List<TMKGReleaseDate>) :
    RecyclerView.Adapter<ReleaseDateAdapter.RDHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RDHolder {
        val gameBinding =
            ItemReleaseDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RDHolder(gameBinding)
    }

    override fun getItemCount() = releaseDates.size

    override fun onBindViewHolder(holder: RDHolder, position: Int) {
        holder.bind(releaseDates[position])
    }

    class RDHolder(
        private val releaseDateBinding: ItemReleaseDateBinding
    ) : RecyclerView.ViewHolder(releaseDateBinding.root) {

        fun bind(releaseDate: TMKGReleaseDate) {
            releaseDateBinding.apply {
                releasePlatformName.text = releaseDate.platformName
                releaseDateHuman.text = releaseDate.releaseDateHuman
            }
        }
    }
}