package com.example.composition.presentation

import android.content.res.ColorStateList
import android.graphics.ColorSpace.Rgb
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.databinding.FragmentWelcomeBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level


/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            this.add(binding.tvOption1)
            this.add(binding.tvOption2)
            this.add(binding.tvOption3)
            this.add(binding.tvOption4)
            this.add(binding.tvOption5)
            this.add(binding.tvOption6)
        }
    }

    private val viewModelFacory by lazy {
        GameViewModelFacory(requireActivity().application, level)
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFacory)[GameViewModel::class.java]
    }

    private lateinit var level: Level
  //  private lateinit var result: GameResult

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenersToOptions()
        // viewModel.startGame(level)
    }

    private fun setClickListenersToOptions() {
        for (tvOpt in tvOptions) {
            tvOpt.setOnClickListener() {
                viewModel.chooseAnswer(tvOpt.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0..tvOptions.size - 1) {
                if (i <= it.options.size - 1) {
                    tvOptions[i].text = it.options[i].toString()
                }
            }
        }
        viewModel.percentOfRightAnswer.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughOfCountRightAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(colorByState(it))
        }
        viewModel.enoughOfPercentRightAnswers.observe(viewLifecycleOwner) {
            val color = colorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.formatedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }

    }

    private fun colorByState(state: Boolean): Int {
        val colorResId = if (state) {
            android.R.color.holo_green_dark
        } else {
            android.R.color.holo_red_dark
        }
        return ContextCompat.getColor(requireContext(), colorResId)
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
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        //  level = requireArguments().getSerializable(KEY_LEVEL) as Level
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private fun launchGameFinishedFragment(result: GameResult) {
       /* requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(result))
            .addToBackStack(null)
            .commit()*/
        val arguments = Bundle().apply {
            putParcelable(GameFinishedFragment.KEY_GAME_RESULT, result)
        }
        findNavController().navigate(R.id.action_gameFragment_to_gameFinishedFragment,arguments)
    }

    companion object {

        const val NAME = "GameFragment"

        const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    // putSerializable(KEY_LEVEL, level)
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}