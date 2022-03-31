package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Activity.R;

import java.util.List;

import Activity.ViewAllActivity;
import Model.ExploreModel;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {
    Context context;
    List<ExploreModel> exploreModelList;

    public ExploreAdapter(Context context, List<ExploreModel> exploreModelList) {
        this.context = context;
        this.exploreModelList = exploreModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(exploreModelList.get(position).getImg_Url()).into(holder.expimg);
        holder.name.setText(exploreModelList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(context, ViewAllActivity.class);
                i.putExtra("type",exploreModelList.get(holder.getAdapterPosition()).getType());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exploreModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView expimg;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expimg=itemView.findViewById(R.id.exp_img);
            name=itemView.findViewById(R.id.exp_name);

        }
    }
}
