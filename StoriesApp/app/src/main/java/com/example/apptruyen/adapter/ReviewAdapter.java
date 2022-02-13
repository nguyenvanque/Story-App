package com.example.apptruyen.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptruyen.R;
import com.example.apptruyen.model.Review;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
Context context;
ArrayList<Review> list;

    public ReviewAdapter(Context context, ArrayList<Review> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_review,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     Review reviewTruyen=list.get(position);
     holder.txtName.setText(reviewTruyen.getName());
     holder.txtDescription.setText(reviewTruyen.getMessage());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(reviewTruyen.getIdreview()));
        String time = DateFormat.format("dd/MM/yyyy  hh:mm aa", calendar).toString();
        holder.txtTime.setText(time);
        holder.ratingBar.setRating(Float.parseFloat(reviewTruyen.getRateNumber()));
        holder.txtNumberrate.setText(reviewTruyen.getRateNumber());

        if(!reviewTruyen.getHinh().equals(""))
        Picasso.with(context).load(reviewTruyen.getHinh()).placeholder(R.drawable.ic_gallery_grey).into(holder.avatarIv);
        else holder.avatarIv.setImageResource(R.drawable.avatarc);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
       TextView txtName,txtTime,txtDescription,txtNumberrate;
       CircleImageView avatarIv;
       RatingBar ratingBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
            txtTime=itemView.findViewById(R.id.txtTime);
            txtDescription=itemView.findViewById(R.id.txtDescription);
            avatarIv=itemView.findViewById(R.id.avatarIv);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            txtNumberrate=itemView.findViewById(R.id.txtNumberrate);
        }
    }
}
