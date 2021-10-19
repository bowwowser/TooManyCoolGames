package com.example.toomanycoolgames.ui.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toomanycoolgames.data.model.TMKGReleaseDate
import com.example.toomanycoolgames.databinding.ItemReleaseDateBinding
import proto.RegionRegionEnum

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
                releaseRegionIcon.text = getRegionIconFromOrdinal(releaseDate.regionOrdinal)
            }
        }
    }
}

fun getRegionIconFromOrdinal(ordinal: Int) : String {
    return when (RegionRegionEnum.forNumber(ordinal)) {
        RegionRegionEnum.NORTH_AMERICA -> "\uD83C\uDDFA\uD83C\uDDF8"
        RegionRegionEnum.EUROPE -> "\uD83C\uDDEA\uD83C\uDDFA"
        RegionRegionEnum.AUSTRALIA -> "\uD83C\uDDE6\uD83C\uDDFA"
        RegionRegionEnum.NEW_ZEALAND -> "\uD83C\uDDF3\uD83C\uDDFF"
        RegionRegionEnum.JAPAN -> "\uD83C\uDDEF\uD83C\uDDF5"
        RegionRegionEnum.CHINA -> "\uD83C\uDDE8\uD83C\uDDF3"
        RegionRegionEnum.ASIA -> "\uD83C\uDF0F"
        RegionRegionEnum.WORLDWIDE -> "\uD83C\uDF10"
        else -> "\uD83D\uDEAB"
    }
}