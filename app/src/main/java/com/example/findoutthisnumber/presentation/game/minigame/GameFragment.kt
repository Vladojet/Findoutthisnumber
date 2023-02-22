package com.example.findoutthisnumber.presentation.game.minigame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.findoutthisnumber.data.repository.ScoresRepositoryImpl
import com.example.findoutthisnumber.databinding.FragmentGameBinding
import com.example.findoutthisnumber.presentation.repo.ScoresRepository
import com.example.findoutthisnumber.presentation.utils.ActionState
import com.example.findoutthisnumber.presentation.utils.GameState


class GameFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameFragmentViewModel
    private lateinit var shouldWin: GameState
    private var shouldIncrease: ActionState

    init {
        shouldIncrease = ActionState.INCREASING
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentGameBinding.inflate(layoutInflater).apply { binding = this }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = GameViewModelFactory(ScoresRepositoryImpl(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)[GameFragmentViewModel::class.java]
        trackUi()
        trackGameState()
        initClickListeners()
        initNumbers()
    }

    private fun initNumbers() {
        viewModel.numberScore.observe(viewLifecycleOwner) {
            binding.userNumberPlaceHolder.text = it.toString()
        }

        viewModel.desiredNumber.observe(viewLifecycleOwner) { desNumb ->
            binding.desiredNumberPlaceHolder.text = desNumb.toString()
            binding.desiredNumberPlaceHolder.visibility = View.INVISIBLE
        }
    }

    private fun initClickListeners() {
        binding.buttonChooseOne.setOnClickListener (this)
        binding.buttonChooseFive.setOnClickListener (this)
        binding.buttonChooseTen.setOnClickListener (this)
        binding.buttonChooseTwenty.setOnClickListener (this)
        binding.buttonPlus.setOnClickListener (this)
        binding.buttonFindOut.setOnClickListener (this)
        binding.buttonTryAgain.setOnClickListener (this)

}

    private fun trackGameState() {
        viewModel.gameState.observe(viewLifecycleOwner) {
            shouldWin = it
        }
    }

    private fun trackUi() {
        viewModel.actionState.observe(viewLifecycleOwner) {
            shouldIncrease = it
        }
    }

    companion object {
        fun newInstance() = GameFragment()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            binding.buttonChooseOne.id -> {
                if (shouldIncrease.state) {
                    viewModel.incByOne()
                } else viewModel.decByOne()
            }
            binding.buttonChooseFive.id -> {
                if (shouldIncrease.state) {
                    viewModel.incByFive()
                } else viewModel.decByFive()
            }
            binding.buttonChooseTen.id -> {
                if (shouldIncrease.state) {
                    viewModel.incByTen()
                } else viewModel.decByTen()
            }
            binding.buttonChooseTwenty.id -> {
                if (shouldIncrease.state) {
                    viewModel.incByTwenty()
                } else viewModel.decByTwenty()
            }
            binding.buttonPlus.id -> {
                if (shouldIncrease.state) {
                    viewModel.changeSign()
                } else viewModel.changeSign()
            }
            binding.buttonFindOut.id -> {
                viewModel.compareNumbers()
                binding.desiredNumberPlaceHolder.visibility = View.VISIBLE
                if (shouldWin.gameState) {
                    Toast.makeText(activity, "You have won", Toast.LENGTH_LONG).show()
                } else Toast.makeText(activity, "You have lost", Toast.LENGTH_LONG).show()
            }
            binding.buttonTryAgain.id -> {
                viewModel.restartGame()
                Toast.makeText(activity, "Game have been restarted", Toast.LENGTH_LONG).show()
            }
            else -> false
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.gameState.removeObservers(viewLifecycleOwner)
    }
}