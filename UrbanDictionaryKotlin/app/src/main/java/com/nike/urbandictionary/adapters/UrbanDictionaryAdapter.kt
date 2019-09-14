package com.nike.urbandictionary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.nike.urbandictionary.R
import com.nike.urbandictionary.models.UrbanDictionaryDefinition

class UrbanDictionaryAdapter(private val context: Context, private var definitions: List<UrbanDictionaryDefinition>) : RecyclerView.Adapter<UrbanDictionaryAdapter.UrbanDictionaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrbanDictionaryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.definition_item, parent, false)
        return UrbanDictionaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: UrbanDictionaryViewHolder, position: Int) {
        holder.definition.text = definitions[position].wordDefinition
        holder.thumbsup.text = definitions[position].thumbsUpCount
        holder.thumbsdown.text = definitions[position].thumbsDownCount
    }

    override fun getItemCount(): Int {
        return definitions.size
    }

    class UrbanDictionaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var definition: TextView = itemView.findViewById(R.id.definition)
        var thumbsup: TextView = itemView.findViewById(R.id.thumbsUp)
        var thumbsdown: TextView = itemView.findViewById(R.id.thumbsDown)
    }
}
