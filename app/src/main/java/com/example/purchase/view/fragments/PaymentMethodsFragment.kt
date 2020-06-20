package com.example.purchase.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.purchase.R
import com.example.purchase.view.adapters.PaymentMethodRecyclerViewAdapter
import com.example.purchase.viewmodel.FlowControlViewModel
import com.example.purchase.viewmodel.PaymentMethodsViewModel
import kotlinx.android.synthetic.main.fragment_payment_methods_list.*
import timber.log.Timber

/**
 * A fragment representing a list of Items.
 */
class PaymentMethodsFragment : Fragment() {

    private lateinit var flowControlViewModel: FlowControlViewModel

    private lateinit var paymentMethodsViewModel: PaymentMethodsViewModel


    private lateinit var adapter: PaymentMethodRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flowControlViewModel = activity?.run {
            ViewModelProviders.of(this).get(FlowControlViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_payment_methods_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paymentMethodsViewModel =
            ViewModelProviders.of(this).get(PaymentMethodsViewModel::class.java)

        setListeners()
        setObservers()
        initFragment()
        loadData()
    }

    fun loadData() {
        showLoader()
        paymentMethodsViewModel.loadData()
    }

    fun initFragment() {
        this.activity?.let {
            activity?.title = resources.getString(R.string.payment_method_title)
        }
        paymentMethodsViewModel.positionItemSelected = -1
    }

    fun setListeners() {
        btnPaymentMethodNext.setOnClickListener {
            flowControlViewModel.paymentMethod = paymentMethodsViewModel.data[paymentMethodsViewModel.positionItemSelected]
            val action =
                PaymentMethodsFragmentDirections.actionPaymentMethodsFragmentToBanksFragment()
            findNavController().navigate(action)
        }
        btnTryAgain.setOnClickListener {
            loadData()
        }
    }

    fun loadAdapter() {
        paymentMethodlist.layoutManager = LinearLayoutManager(context)
        adapter = PaymentMethodRecyclerViewAdapter(paymentMethodsViewModel)
        paymentMethodlist.adapter = adapter
    }

    fun setObservers() {
        paymentMethodsViewModel.stateLiveData.observe(viewLifecycleOwner, Observer { status ->
            updateUI(status)
        })
    }

    fun updateUI(status: PaymentMethodsViewModel.STATE) {
        Timber.d("updateUI -> status:%s", status)
        when (status) {
            PaymentMethodsViewModel.STATE.NOT_INITIALIZED -> showNotInitialized()
            PaymentMethodsViewModel.STATE.ERROR_CONNECTION -> showNoConnection()
            PaymentMethodsViewModel.STATE.NO_ELEMENTS -> showNoElements()
            PaymentMethodsViewModel.STATE.SUCCESS ->  {
                loadAdapter()
                showPaymentMethods()
            }
            PaymentMethodsViewModel.STATE.NO_ITEM_SELECTED -> showNoItemSelected()
            PaymentMethodsViewModel.STATE.ITEM_SELECTED -> showItemSelected()
        }
    }

    fun showNotInitialized() {
        paymentMethodlist.visibility = View.GONE
        txtPaymentMethodGeneralMessage.visibility = View.VISIBLE
        txtPaymentMethodGeneralMessage.text = resources.getString(R.string.general_not_initialized)
        btnTryAgain.visibility = View.GONE
        btnPaymentMethodNext.isEnabled = false
    }

    fun showNoConnection() {
        paymentMethodlist.visibility = View.GONE
        txtPaymentMethodGeneralMessage.visibility = View.VISIBLE
        txtPaymentMethodGeneralMessage.text = resources.getString(R.string.general_network_error)
        btnTryAgain.visibility = View.VISIBLE
        btnPaymentMethodNext.isEnabled = false
        hideLoader()
    }

    fun showPaymentMethods() {
        paymentMethodlist.visibility = View.VISIBLE
        txtPaymentMethodGeneralMessage.visibility = View.GONE
        btnTryAgain.visibility = View.GONE
        btnPaymentMethodNext.isEnabled = false
        hideLoader()
    }

    fun showNoElements() {
        paymentMethodlist.visibility = View.GONE
        txtPaymentMethodGeneralMessage.visibility = View.VISIBLE
        txtPaymentMethodGeneralMessage.text = resources.getString(R.string.payment_method_not_found)
        btnTryAgain.visibility = View.GONE
        btnPaymentMethodNext.isEnabled = false
        hideLoader()
    }

    fun showNoItemSelected() {
        paymentMethodlist.visibility = View.VISIBLE
        txtPaymentMethodGeneralMessage.visibility = View.GONE
        btnTryAgain.visibility = View.GONE
        btnPaymentMethodNext.isEnabled = false
    }

    fun showItemSelected() {
        paymentMethodlist.visibility = View.VISIBLE
        txtPaymentMethodGeneralMessage.visibility = View.GONE
        btnTryAgain.visibility = View.GONE
        btnPaymentMethodNext.isEnabled = true
    }

    fun showLoader() {
        Timber.d("alamo showLoaderrrrrrrrrrrrrrrrr")
        Timber.d(Throwable())
        loader.visibility = View.VISIBLE
    }

    fun hideLoader() {
        Timber.d("alamo hideLoaderrrrrrrrrrrrrrrrr")
        Timber.d(Throwable())
        loader.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance(columnCount: Int) = PaymentMethodsFragment()
    }
}