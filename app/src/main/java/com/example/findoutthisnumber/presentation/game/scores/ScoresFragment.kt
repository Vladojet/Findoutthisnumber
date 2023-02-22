package com.example.findoutthisnumber.presentation.game.scores

import android.graphics.Insets.add
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findoutthisnumber.data.repository.ScoresRepositoryImpl
import com.example.findoutthisnumber.databinding.FragmentScoresBinding
import com.example.findoutthisnumber.presentation.game.scores.recycler.ScoresFragmentAdapter
import com.example.findoutthisnumber.presentation.game.scores.viewmodel.ScoresFragmentViewModel
import com.example.findoutthisnumber.presentation.game.scores.viewmodel.ScoresViewModelFactory
import com.example.findoutthisnumber.presentation.models.ScoreModel

class ScoresFragment : Fragment() {

    private lateinit var viewModel: ScoresFragmentViewModel

    private lateinit var binding: FragmentScoresBinding

    private lateinit var courseRV: RecyclerView
    private lateinit var adapter: ScoresFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentScoresBinding.inflate(layoutInflater).apply { binding = this }.root
    }

    companion object {
        fun newInstance() = ScoresFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = ScoresViewModelFactory(ScoresRepositoryImpl(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)[ScoresFragmentViewModel::class.java]
        courseRV = binding.scoresListRecyclerView
        adapter = ScoresFragmentAdapter()
        getList()
        buildRecyclerView()
    }

    private fun buildRecyclerView() {


        val manager = LinearLayoutManager(requireContext())
        courseRV.setHasFixedSize(true)
        courseRV.layoutManager = manager
        courseRV.adapter = adapter
    }

    private fun getList() {

        /* TODO read from db  */
        /*viewModel.getScoreList()
        viewModel.numberScore.observe(viewLifecycleOwner) {
            scoresList = it
        }*/

        var scoresList =  listOf(ScoreModel(1, "You win", 23),
            ScoreModel(2, "You win", 42),
            ScoreModel(3, "You loose", 60),
            ScoreModel(4, "You loose", 12),
            ScoreModel(5, "You win", 56),
            ScoreModel(6, "You win", 81),
            ScoreModel(7, "You loose", 33),
            ScoreModel(8, "You loose", 75),
            ScoreModel(9, "You win", 18),
            ScoreModel(10, "You loose", 7)
        )
        adapter.setList(scoresList)
    }
}