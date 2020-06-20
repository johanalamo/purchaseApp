package com.example.purchase.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.purchase.R
import kotlinx.android.synthetic.main.fragment_banks_list.*
import kotlinx.android.synthetic.main.fragment_installments.*


/**
 * A simple [Fragment] subclass.
 * Use the [InstallmentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InstallmentsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_installments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
        setListeners()
    }

    fun initFragment() {
        this.activity?.let {
            activity?.title = resources.getString(R.string.installment_title)
        }
    }

    fun setListeners() {
        btnInstallmentNext.setOnClickListener {
            val action =
                InstallmentsFragmentDirections.actionInstallmentsFragmentToConfirmFragment()
            findNavController().navigate(action)
        }
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = InstallmentsFragment()
    }
}