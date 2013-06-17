package com.ucb.dcm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.ucb.dcm.data.DataService;
import com.ucb.dcm.data.Show;
import com.ucb.dcm.list.ShowsListAdapter;

import java.util.ArrayList;


/**
 * Created by kurtguenther on 6/8/13.
 */
public class ShowsFragment extends SherlockListFragment {

    ShowsListAdapter mAdpt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Show> shows = getData();
        mAdpt = new ShowsListAdapter(shows, getActivity().getLayoutInflater());
        setListAdapter(mAdpt);
    }

    protected ArrayList<Show> getData(){
        return DataService.getSharedService().getShows(null);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        ArrayList<Show> shows = getData();
//        mAdpt = new ShowsListAdapter(shows, getActivity().getLayoutInflater());
//        setListAdapter(mAdpt);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retVal = inflater.inflate(R.layout.tab_shows, container, false);

        TextView empty = (TextView) retVal.findViewById(R.id.empty_title);
        empty.setText(getEmptyText());

        return retVal;
    }

    protected String getEmptyText() {
        return "No Matching Shows.";
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent displayShow = new Intent(getActivity(), ViewShowFragment.class);
        Show show = (Show) getListAdapter().getItem(position);
        displayShow.putExtra(ViewShowFragment.SHOW_KEY, show.ID);
        startActivity(displayShow);
    }

    public void setFilter(String filter){
        mAdpt.updateFilter(filter);
    }
}
