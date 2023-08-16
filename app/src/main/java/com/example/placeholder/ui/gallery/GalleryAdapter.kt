package com.example.placeholder.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.placeholder.data.Receipt
import com.example.placeholder.databinding.ItemGalleryBinding

class GalleryAdapter() : ListAdapter<Receipt, GalleryAdapter.ReceiptViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Receipt>() {
            override fun areItemsTheSame(oldItem: Receipt, newItem: Receipt): Boolean {
                return oldItem.receiptName == newItem.receiptName
            }

            override fun areContentsTheSame(oldItem: Receipt, newItem: Receipt): Boolean {
                return oldItem == newItem
            }
        }
    }

    /**
     * Receipt view holder
     *
     * @property binding of the item_gallery.xml layout
     * @constructor Create Receipt view holder to hold each receipt binding
     */
    class ReceiptViewHolder(private var binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(receipt: Receipt) {
            Glide.with(binding.root)
                .load(receipt.receiptImage)
                .into(binding.receiptImageView)
            binding.amountTextView.text = "Amount: \$${receipt.receiptAmount}"
            binding.categoryTextView.text = "Category: ${receipt.receiptCategory}"
        }
    }

    /**
     * On create view holder
     *
     * @param viewGroup
     * @param viewType
     * @return the created ReceiptViewHolder
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ReceiptViewHolder {
        val viewHolder = ReceiptViewHolder(
            ItemGalleryBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            // TODO: Implement click listener to edit this receipt
        }
        return viewHolder
    }

    /**
     * On bind view holder
     *
     * @param holder of the ReceiptViewHolder to be bound to position
     * @param position of the receipt
     */
    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



}