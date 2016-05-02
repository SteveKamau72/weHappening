package com.snapttechtechnologies.stevekamau.wehappening.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.model.AttendingListItems;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by steve on 11/7/15.
 */
public class AttendingOrNotListAdapter extends ArrayAdapter<AttendingListItems> {
    Context context;
    int layoutResourceId;
    ArrayList<AttendingListItems> data = new ArrayList<AttendingListItems>();

    public AttendingOrNotListAdapter(Context context, int layoutResourceId, ArrayList<AttendingListItems> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ImageHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
            holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
            holder.txtDetail = (TextView) row.findViewById(R.id.txtDetail);
            holder.txtTime = (TextView) row.findViewById(R.id.txtTime);
            holder.txtWhere = (TextView) row.findViewById(R.id.txtWhere);
            row.setTag(holder);
        } else {
            holder = (ImageHolder) row.getTag();
        }
        AttendingListItems picture = data.get(position);
        holder.txtTitle.setText(picture._name);
        holder.txtDetail.setText(picture._detail);
        holder.txtDetail.setVisibility(View.GONE);
        holder.txtTime.setText(picture._time);
        holder.txtWhere.setText(picture._where);

//convert byte to bitmap take from contact class

        byte[] outImage = picture._image;
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imgIcon.setImageBitmap(theImage);
        return row;
    }

    static class ImageHolder {
        ImageView imgIcon;
        TextView txtTitle, txtDetail, txtTime, txtWhere;
    }
}



