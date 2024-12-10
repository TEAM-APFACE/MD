package com.teamapface.ui.saved

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamapface.R
import com.teamapface.utils.model.SavedResult

class SavedResultsAdapter(
    private var results: List<SavedResult>,
    private val onClick: (SavedResult) -> Unit
) : RecyclerView.Adapter<SavedResultsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.imageViewSaved)
        private val textViewCondition = view.findViewById<TextView>(R.id.textViewCondition)
        private val textViewType = view.findViewById<TextView>(R.id.textViewType)

        fun bind(result: SavedResult) {
            // Use Glide to load the image
            Glide.with(itemView.context)
                .load(result.imageUri) // Directly use the URI
                .placeholder(R.drawable.image_placeholder) // Optional: Add a placeholder image
                .error(R.drawable.image_broken) // Optional: Add an error image
                .into(imageView)

            textViewCondition.text = result.condition
            textViewType.text = result.type // Bind modelType to textViewType

            itemView.setOnClickListener {
                onClick(result)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int = results.size

    fun updateResults(newResults: List<SavedResult>) {
        val diffResult = DiffUtil.calculateDiff(SavedResultsDiffCallback(results, newResults))
        results = newResults
        diffResult.dispatchUpdatesTo(this)
    }

    private class SavedResultsDiffCallback(
        private val oldList: List<SavedResult>,
        private val newList: List<SavedResult>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

