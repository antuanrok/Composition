package com.example.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level


/**
 * A simple [Fragment] subclass.
 * Use the [GameFinishedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFinishedFragment : Fragment() {

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding is null")

    private lateinit var gameResult: GameResult

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
        binding.butRetry.setOnClickListener{
            retryGame()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
      /*  gameResult = GameResult(
            requireArguments().getBoolean(KEY_WINNER),
            requireArguments().getInt(KEY_COUNT_RIGHT_ANSWERS),
            requireArguments().getInt(KEY_COUNT_OF_QUESTIONS),
            GameSettings(
                requireArguments().getInt(KEY_MAXSUMVALUE),
                requireArguments().getInt(KEY_MIN_COUNT_OF_RIGHT_ANSWERS),
                requireArguments().getInt(KEY_MIN_PERCENT_OF_RIGHT_ANSWER),
                requireArguments().getInt(KEY_GAME_TIME_IN_SECONDS)
            )
        )*/
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    private fun retryGame () {
      //  requireActivity().supportFragmentManager.popBackStack(ChooseLevelFragment.NAME,0)
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME,FragmentManager.POP_BACK_STACK_INCLUSIVE)
       // requireActivity().supportFragmentManager.popBackStack()
    }

    companion object {
        private const val KEY_WINNER = "winner"
        private const val KEY_COUNT_RIGHT_ANSWERS = "countRightAnswers"
        private const val KEY_COUNT_OF_QUESTIONS = "countOfQuestions"

        //  private const val KEY_GAME_SETTINGS = "gameSettings"
        private const val KEY_MAXSUMVALUE = "maxSumValue"
        private const val KEY_MIN_COUNT_OF_RIGHT_ANSWERS = "minCountOfRightAnswers"
        private const val KEY_MIN_PERCENT_OF_RIGHT_ANSWER = "minPercentOfRightAnswer"
        private const val KEY_GAME_TIME_IN_SECONDS = "gameTimeInSeconds"
        private const val KEY_GAME_RESULT = "gameResult"

        fun newInstance(result: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT,result)
                 /*   putBoolean(KEY_WINNER, result.winner)
                    putInt(KEY_COUNT_RIGHT_ANSWERS, result.countRightAnswers)
                    putInt(KEY_COUNT_OF_QUESTIONS, result.countOfQuestions)
                    putInt(KEY_MAXSUMVALUE, result.gameSettings.maxSumValue)
                    putInt(
                        KEY_MIN_COUNT_OF_RIGHT_ANSWERS,
                        result.gameSettings.minCountOfRightAnswers
                    )
                    putInt(
                        KEY_MIN_PERCENT_OF_RIGHT_ANSWER,
                        result.gameSettings.minPercentOfRightAnswer
                    )
                    putInt(KEY_GAME_TIME_IN_SECONDS, result.gameSettings.gameTimeInSeconds)*/

                }
            }
        }
    }

}