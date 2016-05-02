package com.snapttechtechnologies.stevekamau.wehappening.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by steve on 9/8/15.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    public static int selected_item = 0;
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position == selected_item) {
            holder.title.setTextColor(Color.parseColor("#00aaff"));
            holder.imgViewIcon.setBackgroundResource(R.drawable.ic_circle);
        } else {
            holder.title.setTextColor(Color.parseColor("#6a6a6a"));
            holder.imgViewIcon.setBackgroundResource(0);
        }
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.imgViewIcon.setImageResource(current.getIcon());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgViewIcon;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            String fontPath = "fonts/Centurygothic.ttf";
            Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            title.setTypeface(tf);

            imgViewIcon = (ImageView) itemView.findViewById(R.id.item_icon);

        }
    }
}


