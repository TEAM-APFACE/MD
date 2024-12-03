package com.teamapface.ui.saved

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.teamapface.R
import com.bumptech.glide.Glide

class SavedResultsAdapter(
    private val savedResults: List<SavedResult>,
    private val onItemClick: (SavedResult) -> Unit
) : RecyclerView.Adapter<SavedResultsAdapter.SavedResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_result, parent, false)
        return SavedResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedResultViewHolder, position: Int) {
        val result = savedResults[position]
        holder.tvCondition.text = result.condition

        // Using Glide to load the image
        Glide.with(holder.itemView.context)
            .load(Uri.parse(result.imageUri)) // Safely parse the image URI
            .into(holder.imgResult)

        // Set the onClickListener to trigger the passed callback
        holder.cardView.setOnClickListener { onItemClick(result) }
    }

    override fun getItemCount(): Int = savedResults.size

    // ViewHolder to hold the UI components
    class SavedResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgResult: ImageView = view.findViewById(R.id.img_result)
        val tvCondition: TextView = view.findViewById(R.id.tv_condition)
        val cardView: CardView = view.findViewById(R.id.card_saved_result) // Corrected the reference
    }
}
