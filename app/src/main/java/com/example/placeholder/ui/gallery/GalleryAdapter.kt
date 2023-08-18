package com.example.placeholder.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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
                .transform(
                    FitCenter(),
                    RoundedCorners(20)
                )
                .into(binding.receiptImageView)
            binding.amountTextView.text = "Spent: \$${receipt.receiptAmount}"
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
        var isAnimating = false
        viewHolder.itemView.setOnLongClickListener { view ->
            // Animate the long click selection
            if (!isAnimating) {
                isAnimating = true
                view.animate()
                    .scaleXBy(0.075f)
                    .scaleYBy(0.075f)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .setDuration(200)
                    .withEndAction { // Long click completed, reset the view's size
                        view.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setInterpolator(AccelerateDecelerateInterpolator())
                            .setDuration(200)
                            .withEndAction {
                                isAnimating = false
                            }
                            .start()
                    }
                    .start()
            }

            true
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