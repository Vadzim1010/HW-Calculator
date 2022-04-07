package com.example.hw1calculator.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw1calculator.CalculatorApplication
import com.example.hw1calculator.adapter.CalculationHistoryAdapter
import com.example.hw1calculator.databinding.FragmentCalculationHistoryBinding

class CalculationHistoryFragment : Fragment() {

    private var _binding: FragmentCalculationHistoryBinding? = null
    private val binding get() = requireNotNull(_binding) { "binding is null" }
    private var _calculationHistoryAdapter: CalculationHistoryAdapter? = null
    private val calculationHistoryAdapter get() = requireNotNull(_calculationHistoryAdapter)
    private val viewModel by viewModels<EquationHistoryViewModel> {
        EquationHistoryViewModelFactory(
            requireActivity().application,
            (requireActivity().application as CalculatorApplication).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentCalculationHistoryBinding.inflate(layoutInflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initButtons()
        observeHistory()
    }

    private fun observeHistory() {
        viewModel.getHistory().observe(viewLifecycleOwner) {
            calculationHistoryAdapter.submitList(it.toList())
        }
    }

    private fun initButtons() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRecycler() {
        binding.recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CalculationHistoryAdapter { historyItem ->
                viewModel.addSymbol("($historyItem)")
                findNavController().popBackStack()
            }.also { _calculationHistoryAdapter = it }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
