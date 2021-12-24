package com.firejobcourse.apps.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firejobcourse.apps.R;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    private final Context context;
    private InfoInterface parent;
    private StorageReference storage;
    private final ArrayList<DataInfo> dataInfos = new ArrayList();

    public InfoAdapter(InfoInterface parent, Context context, StorageReference storage ) {
        this.parent = parent;
        this.context = context;
        this.storage = storage;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitData(ArrayList<DataInfo> data) {
        this.dataInfos.clear();
        this.dataInfos.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataInfo dataInfo = dataInfos.get(position);
        holder.tvTitle.setText(dataInfo.getJudul_info());

        StorageReference ref = storage.child(dataInfo.getLink_img());

        ref.getDownloadUrl().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downUri = task.getResult();
                String imageUrl = downUri.toString();

                Picasso.with(context)
                        .load(imageUrl)
                        .centerCrop()
                        .fit()
                        .into(holder.ivInfo);
            }else{
                Toast.makeText(context, "Gagal Load Image", Toast.LENGTH_SHORT).show();
            }
        });



        holder.itemView.setOnClickListener(view -> {
            parent.onClick(dataInfo);
        });

    }

    @Override
    public int getItemCount() {
        return dataInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivInfo = itemView.findViewById(R.id.ivInfo);
        }
    }
}
