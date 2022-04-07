package com.example.hw1calculator.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hw1calculator.CalculatorApplication
import com.example.hw1calculator.R
import com.example.hw1calculator.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = requireNotNull(_binding) { "binding is null" }
    private val viewModel by viewModels<CalculatorViewModel> {
        CalculatorViewModelFactory(requireActivity().application,
            (requireActivity().application as CalculatorApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentCalculatorBinding.inflate(layoutInflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEquation()
        observeResult()
        initButtons()
    }

    private fun observeEquation() {
        viewModel.getEquation().observe(viewLifecycleOwner) { equation ->
            binding.equationTextView.text = equation
            textSizeController(equation.length)
            scrollY()
        }
    }

    private fun observeResult() {
        viewModel.getResult().observe(viewLifecycleOwner) { equationResult ->
            binding.equationResultTextView.text = equationResult
        }
    }

    private fun scrollY() {
        if (binding.scrollView != null) {
            binding.scrollView?.smoothScrollBy(0, +100)
        }
    }

    private fun textSizeController(length: Int) {
        when {
            length > 11 -> {
                binding.equationTextView.textSize =
                    resources.getDimension(R.dimen.fragment_text_size_small)
                        .pixelsToSp()
            }
            length in 8..11 -> {
                binding.equationTextView.textSize =
                    resources.getDimension(R.dimen.fragment_text_size_medium)
                        .pixelsToSp()
            }
            else -> {
                binding.equationTextView.textSize =
                    resources.getDimension(R.dimen.fragment_text_size_big)
                        .pixelsToSp()
            }
        }
    }

    private fun Float.pixelsToSp(): Float {
        return this / resources.displayMetrics.scaledDensity
    }

    private fun initButtons() = with(binding) {
        with(viewModel) {
            equationTextView.setOnClickListener {
                findNavController().navigate(
                    CalculatorFragmentDirections.toCalculationHistory()
                )
            }
            buttonOne.setOnClickListener {
                addSymbol('1')
            }
            buttonTwo.setOnClickListener {
                addSymbol('2')
            }
            buttonThree.setOnClickListener {
                addSymbol('3')
            }
            buttonFour.setOnClickListener {
                addSymbol('4')
            }
            buttonFive.setOnClickListener {
                addSymbol('5')
            }
            buttonSix.setOnClickListener {
                addSymbol('6')
            }
            buttonSeven.setOnClickListener {
                addSymbol('7')
            }
            buttonEight.setOnClickListener {
                addSymbol('8')
            }
            buttonNine.setOnClickListener {
                addSymbol('9')
            }
            buttonZero.setOnClickListener {
                addSymbol('0')
            }
            buttonDot.setOnClickListener {
                addSymbol('.')
            }
            buttonOpenBracket.setOnClickListener {
                addSymbol('(')
            }
            buttonCloseBracket.setOnClickListener {
                addSymbol(')')
            }
            buttonMultiply.setOnClickListener {
                addSymbol('*')
            }
            buttonDivide.setOnClickListener {
                addSymbol('/')
            }
            buttonPlus.setOnClickListener {
                addSymbol('+')
            }
            buttonMinus.setOnClickListener {
                addSymbol('-')
            }
            buttonDelete.setOnClickListener {
                clearAll()
            }
            buttonEquals.setOnClickListener {
                calculate()
            }
            buttonBackspace.setOnClickListener {
                backspace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
