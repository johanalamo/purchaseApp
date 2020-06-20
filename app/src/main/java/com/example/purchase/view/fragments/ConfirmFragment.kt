package com.example.purchase.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.purchase.R
import com.example.purchase.databinding.FragmentConfirmBinding
import com.example.purchase.viewmodel.FlowControlViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_confirm.*

/**
 * A simple [Fragment] subclass.
 * Use the [ConfirmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmFragment : Fragment() {

    private lateinit var flowControlViewModel: FlowControlViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        flowControlViewModel = activity?.run {
            ViewModelProviders.of(this).get(FlowControlViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        inflater.inflate(R.layout.fragment_confirm, container, false)
        val binding = FragmentConfirmBinding.inflate(inflater, container, false)
        binding.flowControlViewModel = flowControlViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
        setListeners()
    }

    fun initFragment() {
        this.activity?.let {
            activity?.title = resources.getString(R.string.confirm_title)
        }
        Picasso.get().load(flowControlViewModel?.paymentMethod?.image).into(paymentMethodImage)
    }

    fun setListeners() {
        btnConfirm.setOnClickListener {
            var snack = Snackbar.make(
                txtConfirmAmount,
                resources.getString(R.string.confirm_purchase_done),
                Snackbar.LENGTH_LONG
            )
            snack.show()

            val action =
                ConfirmFragmentDirections.actionConfirmFragmentToAmountFragment()
            findNavController().navigate(action)
        }
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = ConfirmFragment()
    }
}