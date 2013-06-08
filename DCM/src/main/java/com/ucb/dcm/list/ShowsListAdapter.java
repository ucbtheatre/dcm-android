package com.ucb.dcm.list;


import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
import com.ucb.dcm.R;
import com.ucb.dcm.data.Show;

import java.util.ArrayList;

public class ShowsListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private ArrayList<Show> mShows;
    private LayoutInflater inflater;

    public ShowsListAdapter(ArrayList<Show> shows, LayoutInflater inflater){
        this.mShows = shows;
        this.inflater = inflater;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getCount() {
        return mShows.size();
    }

    @Override
    public Object getItem(int position) {
        return mShows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // mShows.get(position).ID;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_show, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.show_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(mShows.get(position).name);

        return convertView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }


    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return mShows.get(position).name.subSequence(0, 1).charAt(0);
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.list_show_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.header_title);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + mShows.get(position).name.subSequence(0, 1).charAt(0);
        holder.text.setText(headerText);
        return convertView;
    }


    @Override
    public boolean isEmpty() {
        return mShows.size() == 0;
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
    }

}
