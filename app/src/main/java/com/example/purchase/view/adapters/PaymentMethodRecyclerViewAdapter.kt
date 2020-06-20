package com.example.purchase.view.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.purchase.R
import com.example.purchase.viewmodel.PaymentMethodsViewModel
import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PaymentMethodRecyclerViewAdapter(
    private val viewModel: PaymentMethodsViewModel
) : RecyclerView.Adapter<PaymentMethodRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_payment_methods, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = viewModel.data[position]
        holder.contentView.text = item.name
        Picasso.get().load(item.image).into(holder.image)

        holder.itemView.setOnClickListener {
            notifyItemChanged(position)
            notifyItemChanged(viewModel.positionItemSelected)
            if (viewModel.positionItemSelected == position) {
                viewModel.positionItemSelected = -1
                viewModel.setNoItemSelected()
            } else {
                viewModel.positionItemSelected = position
                viewModel.setItemSelected()
            }
        }
        if (viewModel.positionItemSelected == position) {
            markAsSelected(holder)
        } else {
            markAsUnselected(holder)
        }
    }

    private fun markAsSelected(holder: ViewHolder) {
        holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.item_list_selected))
    }

    private fun markAsUnselected(holder: ViewHolder) {
        holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.white))
    }

    override fun getItemCount(): Int = viewModel.data.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.findViewById(R.id.content)
        val image: ImageView = view.findViewById(R.id.itemCardImage)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}