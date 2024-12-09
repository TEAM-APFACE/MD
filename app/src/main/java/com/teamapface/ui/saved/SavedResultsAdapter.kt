package com.teamapface.ui.saved

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamapface.R
import com.teamapface.utils.model.SavedResult

class SavedResultsAdapter(
    private var results: List<SavedResult>,
    private val onClick: (SavedResult) -> Unit
) : RecyclerView.Adapter<SavedResultsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.imageViewSaved)
        private val textViewCondition = view.findViewById<TextView>(R.id.textViewCondition)

        fun bind(result: SavedResult) {
            imageView.setImageURI(Uri.parse(result.imageUri))
            textViewCondition.text = result.condition

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
        results = newResults
        notifyDataSetChanged()
    }
}

