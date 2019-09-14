package com.nike.urbandictionary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nike.urbandictionary.R;
import com.nike.urbandictionary.models.UrbanDictionaryDefinition;

import java.util.List;

public class UrbanDictionaryAdapter extends RecyclerView.Adapter<UrbanDictionaryAdapter.UrbanDictionaryViewHolder> {

    private Context context;
    private List<UrbanDictionaryDefinition> definitions = null;

    public UrbanDictionaryAdapter(Context context, List<UrbanDictionaryDefinition> definitions) {
        this.context = context;
        this.definitions = definitions;
    }

    @NonNull
    @Override
    public UrbanDictionaryAdapter.UrbanDictionaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.definition_item, parent, false);
        return new UrbanDictionaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UrbanDictionaryAdapter.UrbanDictionaryViewHolder holder, int position) {
        holder.definition.setText(definitions.get(position).getWordDefinition());
        holder.thumbsup.setText(definitions.get(position).getThumbsUpCount());
        holder.thumbsdown.setText(definitions.get(position).getThumbsDownCount());
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    class UrbanDictionaryViewHolder extends RecyclerView.ViewHolder {

        TextView definition;
        TextView thumbsup;
        TextView thumbsdown;

        UrbanDictionaryViewHolder(@NonNull View itemView) {
            super(itemView);
            definition = itemView.findViewById(R.id.definition);
            thumbsup = itemView.findViewById(R.id.thumbsUp);
            thumbsdown = itemView.findViewById(R.id.thumbsDown);

        }
    }
}
