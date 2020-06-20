package com.example.purchase.view.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.purchase.viewmodel.FlowControlViewModel
import com.example.purchase.R
import com.example.purchase.databinding.FragmentAmountBinding
import com.example.purchase.viewmodel.AmountViewModel
import kotlinx.android.synthetic.main.fragment_amount.*

class AmountFragment : Fragment() {

    companion object {
        fun newInstance() = AmountFragment()
    }

    private lateinit var flowControlViewModel: FlowControlViewModel

    private lateinit var amountViewModel: AmountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flowControlViewModel = activity?.run {
            ViewModelProviders.of(this).get(FlowControlViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        flowControlViewModel.init()

        amountViewModel = ViewModelProviders.of(this).get(AmountViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        inflater.inflate(R.layout.fragment_amount, container, false)
        val binding = FragmentAmountBinding.inflate(inflater, container, false)
        binding.flowControlViewModel = flowControlViewModel
        binding.amountViewModel = amountViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
        setListeners()
        setObservers()
    }

    fun initFragment() {
        this.activity?.let {
            activity?.title = resources.getString(R.string.amount_title)
        }
    }

    fun setListeners() {
        btnAmountNext.setOnClickListener {
            amountViewModel.amount.set(amountViewModel.amount.get()?.trimStart('0'))
            flowControlViewModel.amount = amountViewModel.amount.get().toString()
            val action = AmountFragmentDirections.actionAmountFragmentToPaymentMethodsFragment()
            findNavController().navigate(action)
        }

        editAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                amountViewModel.checkAmount()
            }
        })
    }

    fun setObservers() {
        amountViewModel.stateLiveData.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                AmountViewModel.STATE.AMOUNT_INVALID -> btnAmountNext.isEnabled = false
                AmountViewModel.STATE.AMOUNT_VALID -> btnAmountNext.isEnabled = true
            }
        })
    }
}