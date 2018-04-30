package com.example.heryatmo.msb_mob.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.Post;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> post;

    public PostAdapter(ArrayList<Post> post){
        this.post = post;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_post, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {
        holder.tv_nama.setText(post.get(position).getNama());
        holder.tv_kebutuhan.setText(post.get(position).getKebutuhan());
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView tv_nama, tv_kebutuhan;
        public ViewHolder(View view){
            super(view);
            tv_nama = (TextView) view.findViewById(R.id.txtViewName);
            tv_kebutuhan = (TextView) view.findViewById(R.id.txtViewPost);
        }
    }
}
