package com.example.meow.test;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Meow on 15.05.2017.
 */

public class ListViewAdapter extends BaseAdapter {



    public ArrayList<Details> resultList;
    Activity activity;

    public ListViewAdapter(Activity activity, ArrayList<Details> resultList) {
        super();
        this.activity = activity;
        this.resultList = resultList;
    }



    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView tv_result;
        TextView tv_selfrate;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.details_item, null);
            holder = new ViewHolder();
            holder.tv_result = (TextView) convertView.findViewById(R.id.tv_actionDetails);
            holder.tv_selfrate = (TextView) convertView.findViewById(R.id.tv_selfrateDet);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Details details = resultList.get(position);
        holder.tv_result.setText(details.getAction());
        holder.tv_selfrate.setText(details.getSelfrate());
        return convertView;

    }
}
