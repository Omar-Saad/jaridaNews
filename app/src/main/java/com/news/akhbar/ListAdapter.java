package com.news.akhbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    Context context;
    ArrayList<String> country;
    ArrayList<String> img;
    ArrayList<String> urls;
    ArrayList<String> content;


    OnArticleListner articleListner;
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView tv;
        ImageView share;
        ToggleButton fav;
        OnArticleListner articleListner;

        public ViewHolder(@NonNull View itemView, OnArticleListner articleListner) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgCountry);
            tv = itemView.findViewById(R.id.tvCountry);
            share = itemView.findViewById(R.id.shareimg);
            fav = itemView.findViewById(R.id.toggleFavButton);
            this.articleListner = articleListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            articleListner.onArticleClickListner(getAdapterPosition());
        }

    }
        public ListAdapter(Context context, ArrayList<String> country, ArrayList<String> img, ArrayList<String> urls,ArrayList<String>content, OnArticleListner articleListner) {
            this.context = context;
            this.country = country;
            this.urls = urls;
            this.img = img;
            this.content = content;
            this.articleListner = articleListner;

        }


        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.custom_list, parent, false);

            return new ViewHolder(v, articleListner);
        }

        @Override
        public void onBindViewHolder(@NonNull final ListAdapter.ViewHolder holder, final int position) {
            ArrayList<String> favouritesList = new ArrayList<>();
            Picasso.get().load(img.get(position)).into(holder.imageView);
            // imageView.setImageResource(img.get(position));
            holder.tv.setText(country.get(position));
            //tv.setText("omar");
            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonTasks.share(context, urls.get(position));
                }
            });


            if(CommonTasks.isFva(context , country.get(position)))
                holder.fav.setBackgroundResource(R.drawable.ic_baseline_favorite_24);

            holder.fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {                        holder.fav.setBackgroundResource(R.drawable.ic_baseline_favorite_24);

                        CommonTasks.addFavourite(context, country.get(position), img.get(position), urls.get(position),content.get(position));
                    } else {
                        CommonTasks.removeFavourite(context, country.get(position), img.get(position), urls.get(position),content.get(position));
                        holder.fav.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);

                    }
                }
            });


        }


   @Override
    public int getItemCount() {
        return country.size();
    }



    public interface OnArticleListner{
        void onArticleClickListner(int position);
    }


}
