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
import Model.RecommenedModel;

public class RecommenedAdapter extends RecyclerView.Adapter<RecommenedAdapter.ViewHolder> {
    Context context;
    List<RecommenedModel> recommenedModelList;

    public RecommenedAdapter(Context context, List<RecommenedModel> recommenedModelList) {
        this.context = context;
        this.recommenedModelList = recommenedModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommened_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(recommenedModelList.get(position).getImg_Url()).into(holder.recommenedimg);
        holder.name.setText(recommenedModelList.get(position).getName());
        holder.des.setText(recommenedModelList.get(position).getDescription());
        holder.rate.setText(recommenedModelList.get(position).getRating());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(context, ViewAllActivity.class);
                i.putExtra("type",recommenedModelList.get(holder.getAdapterPosition()).getType());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommenedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView recommenedimg;
        TextView name,des,rate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommenedimg=itemView.findViewById(R.id.recommened_img);
            name=itemView.findViewById(R.id.recommended_name);
            des=itemView.findViewById(R.id.recommended_des);
            rate=itemView.findViewById(R.id.recommended_rating);
        }
    }
}
