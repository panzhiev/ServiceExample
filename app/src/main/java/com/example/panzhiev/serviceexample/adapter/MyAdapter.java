package com.example.panzhiev.serviceexample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.panzhiev.serviceexample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Tim on 19.03.2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Integer> arrayList;
    public String TAG = "MY_ADAPTER";

    public MyAdapter(Context mContext, ArrayList arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        int image = arrayList.get(position);

        Picasso.with(holder.iv_item_person.getContext())
                .load(image)
                .into(holder.iv_item_person);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_item_person;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder");
            this.iv_item_person = (ImageView) itemView.findViewById(R.id.iv_item);
        }
    }
}
