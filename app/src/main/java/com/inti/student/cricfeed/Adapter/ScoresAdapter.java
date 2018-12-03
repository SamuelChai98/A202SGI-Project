package com.inti.student.cricfeed.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inti.student.cricfeed.Interface.ItemClickListener;
import com.inti.student.cricfeed.Model.RSSObject;
import com.inti.student.cricfeed.R;

class ScoresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    public TextView scoresTitle;
    private ItemClickListener itemClickListener;

    public ScoresViewHolder(View itemView){
        super(itemView);

        scoresTitle = (TextView)itemView.findViewById(R.id.scoresTitle);

        //set event
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v){
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v){
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}

public class ScoresAdapter extends RecyclerView.Adapter<ScoresViewHolder>{
    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public ScoresAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    //inflate View used for each list item
    public ScoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row_scores, parent, false);
        return new ScoresViewHolder(itemView);
    }

    @Override
    //bind object values to View
    public void onBindViewHolder(@NonNull ScoresViewHolder holder, int position) {
        holder.scoresTitle.setText(rssObject.getItems().get(position).getTitle());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick){
                    //implement browser intent
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(browserIntent);
                }
            }
        });
    }

    @Override
    //return number of items on the list
    public int getItemCount() {
        return rssObject.items.size();
    }
}

