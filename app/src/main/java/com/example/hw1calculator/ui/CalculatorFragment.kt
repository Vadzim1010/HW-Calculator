package com.example.hw1calculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hw1calculator.Calculator
import com.example.hw1calculator.databinding.FragmentCalculatorBinding


class CalculatorFragment : Fragment() {

    private val calculator = Calculator()
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalculatorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
    }

    private fun initButtons() = with(binding) {
        var equation = ""

        button1.setOnClickListener {
            equation += "1"
            equationTextView.text = equation
        }
        button2.setOnClickListener {
            equation += "2"
            equationTextView.text = equation
        }
        button3.setOnClickListener {
            equation += "3"
            equationTextView.text = equation
        }
        button4.setOnClickListener {
            equation += "4"
            equationTextView.text = equation
        }
        button5.setOnClickListener {
            equation += "5"
            equationTextView.text = equation
        }
        button6.setOnClickListener {
            equation += "6"
            equationTextView.text = equation
        }
        button7.setOnClickListener {
            equation += "7"
            equationTextView.text = equation
        }
        button8.setOnClickListener {
            equation += "8"
            equationTextView.text = equation
        }
        button9.setOnClickListener {
            equation += "9"
            equationTextView.text = equation
        }
        button0.setOnClickListener {
            equation += "0"
            equationTextView.text = equation
        }
        buttonDot.setOnClickListener {
            equation += "."
            equationTextView.text = equation
        }
        buttonOpenBracket.setOnClickListener {
            equation += "("
            equationTextView.text = equation
        }
        buttonCloseBracket.setOnClickListener {
            equation += ")"
            equationTextView.text = equation
        }
        buttonMultiply.setOnClickListener {
            equation += "*"
            equationTextView.text = equation
        }
        buttonDivide.setOnClickListener {
            equation += "/"
            equationTextView.text = equation
        }
        buttonPlus.setOnClickListener {
            equation += "+"
            equationTextView.text = equation
        }
        buttonMinus.setOnClickListener {
            equation += "-"
            equationTextView.text = equation
        }
        buttonDelete.setOnClickListener {
            equation = ""
            equationTextView.text = equation
        }
        buttonEquals.setOnClickListener {
            equationTextView.text = calculator.calculate(equation)
            equation = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

