package com.healthcareapp.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.healthcareapp.myapplication.DBItem;
import com.healthcareapp.myapplication.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context mContext;
    private List<DBItem> mItems;

    public ItemAdapter(Context mContext, List<DBItem> mItems){
        this.mItems = mItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_dropdown_item, parent, false);

        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DBItem item= mItems.get(position);
        holder.nameItem.setText(item.getName());
        if (item.getImageURL().equals("default")){
            holder.itemImage.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(mContext).load(item.getImageURL()).into(holder.itemImage);
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nameItem;
        public ImageView itemImage;

        public ViewHolder(View itemView) {
            super(itemView);

            nameItem = itemView.findViewById(R.id.nameItem);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}
