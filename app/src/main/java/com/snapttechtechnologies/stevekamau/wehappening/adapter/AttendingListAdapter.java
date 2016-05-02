package com.snapttechtechnologies.stevekamau.wehappening.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.model.AttendingListItems;
import com.snapttechtechnologies.stevekamau.wehappening.utils.ShareBottomDialog;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by steve on 10/25/15.
 */

public class AttendingListAdapter extends ArrayAdapter<AttendingListItems> {
    Context context;
    java.util.Date date1;
    java.util.Date date2;
    int layoutResourceId;
    private BaseAnimatorSet bas_in;
    private BaseAnimatorSet bas_out;
    private ExpandableListView elv;
    ArrayList<AttendingListItems> data = new ArrayList<AttendingListItems>();

    public AttendingListAdapter(Context context, int layoutResourceId, ArrayList<AttendingListItems> data) {
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
            holder.txtLeft = (TextView) row.findViewById(R.id.txt_left);
            holder.txtWhere = (TextView) row.findViewById(R.id.txtWhere);
            holder.img = (ImageView) row.findViewById(R.id.img_options);
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final ShareBottomDialog dialog = new ShareBottomDialog(context, elv);
                    dialog.showAnim(bas_in)//
                            .show();
                }
            });
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

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("E d MMMM yyyy");
        String CurrentDate = sdf.format(date);
        String FinalDate = holder.txtTime.getText().toString();
        SimpleDateFormat dates = new SimpleDateFormat("E d MMMM yyyy");
        //Setting dates
        try {
            date1 = dates.parse(CurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = dates.parse(FinalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Comparing dates
        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);
        //Convert long to String
        String dayDifference = Long.toString(differenceDates);

        if (dayDifference.contains("1")) {
            holder.txtLeft.setText(dayDifference + " day left");
        } else {
            holder.txtLeft.setText(dayDifference + " days left");
        }

        return row;
    }


    static class ImageHolder {
        ImageView imgIcon, img;
        TextView txtTitle, txtDetail, txtTime, txtWhere, txtLeft;
    }
}


