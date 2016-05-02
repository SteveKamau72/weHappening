package com.snapttechtechnologies.stevekamau.wehappening.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.app.AppController;
import com.snapttechtechnologies.stevekamau.wehappening.model.Model;
import com.snapttechtechnologies.stevekamau.wehappening.model.ModelTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by steve on 10/21/15.
 */
public class UpcomingAdapter extends BaseAdapter implements Filterable {
    List<Model> mStringFilterList;
    ValueFilter valueFilter;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Activity activity;
    private LayoutInflater inflater;
    private List<Model> modelItems;
    private int mDefaultImageId;

    public UpcomingAdapter(Activity activity, List<Model> modelItems) {
        this.activity = activity;
        this.modelItems = modelItems;
        mStringFilterList = modelItems;
    }


    public void setDefaultImageResId(int defaultImage) {
        mDefaultImageId = defaultImage;
    }

    @Override
    public int getCount() {
        return modelItems.size();
    }

    @Override
    public Object getItem(int location) {
        return modelItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView details = (TextView) convertView.findViewById(R.id.details);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView where = (TextView) convertView.findViewById(R.id.where);
        // getting movie data for the row
        Model m = modelItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());
        where.setText(m.getWhere());

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(Long.parseLong(m.getTime()) * 1000L);
        String dates = DateFormat.format("E d MMMM yyyy kk:mm", cal).toString();

        time.setText(dates);

        // genre
        String detailsStr = "";
        for (String str : m.getDetails()) {
            detailsStr += str + ", ";
        }
        detailsStr = detailsStr.length() > 0 ? detailsStr.substring(0,
                detailsStr.length() - 2) : detailsStr;
        details.setText(detailsStr);

        // release year
        date.setText(String.valueOf(m.getDate()));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Model> filterList = new ArrayList<Model>();
                for (int i = 0; i < mStringFilterList.size(); i++)
                    if ((mStringFilterList.get(i).getTitle().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        Model m = new Model(mStringFilterList.get(i)
                                .getTitle(), mStringFilterList.get(i)
                                .getThumbnailUrl(), mStringFilterList.get(i)
                                .getWhere(), mStringFilterList.get(i)
                                .getDate(), mStringFilterList.get(i)
                                .getTime(), mStringFilterList.get(i)
                                .getDetails());

                        filterList.add(m);
                    }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            modelItems = (List<Model>) results.values;
            notifyDataSetChanged();
        }

    }

}


