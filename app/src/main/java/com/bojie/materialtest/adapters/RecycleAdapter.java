package com.bojie.materialtest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bojie.materialtest.pojo.Information;
import com.bojie.materialtest.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by bojiejiang on 4/25/15.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();
    private Context mContext;

    public RecycleAdapter(Context context, List<Information> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_row, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Information current = data.get(i);
        myViewHolder.tv_title.setText(current.title);
        myViewHolder.iv_icon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_icon;
        TextView tv_title;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.listText);
            iv_icon = (ImageView) itemView.findViewById(R.id.listIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void delete(int postion) {
        data.remove(postion);
        notifyItemRemoved(postion);
    }

}
