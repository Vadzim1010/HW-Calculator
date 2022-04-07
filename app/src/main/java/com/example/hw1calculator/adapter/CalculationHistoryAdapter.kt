package com.example.hw1calculator.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw1calculator.R
import com.example.hw1calculator.databinding.CalculationHistoryItemBinding
import com.example.hw1calculator.model.Equation

class CalculationHistoryAdapter(
    private val itemClick: (String) -> Unit,
) : ListAdapter<Equation, CalculationHistoryViewHolder>(comparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CalculationHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CalculationHistoryViewHolder(
            binding = CalculationHistoryItemBinding.inflate(layoutInflater, parent, false),
            itemClick = itemClick
        )
    }

    override fun onBindViewHolder(holder: CalculationHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val comparator = object : DiffUtil.ItemCallback<Equation>() {
            override fun areItemsTheSame(
                oldItem: Equation,
                newItem: Equation,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Equation,
                newItem: Equation,
            ): Boolean {
                return oldItem.body == newItem.body && oldItem.result == newItem.result
            }
        }
    }
}

class CalculationHistoryViewHolder(
    private val binding: CalculationHistoryItemBinding,
    private val itemClick: (String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(equation: Equation) = with(binding) {
        calculationHistoryTextView.text = "${equation.body} = ${equation.result}"
        cardView.setOnClickListener {
            itemClick(equation.result)
        }
    }
}
