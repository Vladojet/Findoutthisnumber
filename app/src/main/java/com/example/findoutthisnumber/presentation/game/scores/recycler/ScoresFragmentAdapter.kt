package com.example.findoutthisnumber.presentation.game.scores.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findoutthisnumber.databinding.GameScoreItemBinding
import com.example.findoutthisnumber.presentation.models.ScoreModel

class ScoresFragmentAdapter() : RecyclerView.Adapter<ScoresFragmentAdapter.ViewHolder>() {

    private var linksList = mutableListOf<ScoreModel>()

    fun setList(scoreList: List<ScoreModel>) {
        linksList = scoreList as MutableList<ScoreModel>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GameScoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(linksList[position])
    }

    override fun getItemCount() = linksList.size

    inner class ViewHolder(
        private val binding: GameScoreItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(entity: ScoreModel) {
            binding.gameNumberPlaceholder.text = entity.gameId.toString()
            binding.gameResultPlaceholder.text = entity.matchResult
            binding.userNumberPlaceholder.text = entity.userNumber.toString()
        }

    }
}