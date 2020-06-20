package com.example.purchase.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.purchase.R
import kotlinx.android.synthetic.main.fragment_banks_list.*

/**
 * A fragment representing a list of Items.
 */
class BanksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_banks_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
        setListeners()
    }

    fun initFragment() {
        this.activity?.let {
            activity?.title = resources.getString(R.string.bank_title)
        }
    }

    fun setListeners() {
        btnBankNext.setOnClickListener {
            val action =
                BanksFragmentDirections.actionBanksFragmentToInstallmentsFragment()
            findNavController().navigate(action)
        }
    }

    companion object {

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance() = BanksFragment()
    }
}