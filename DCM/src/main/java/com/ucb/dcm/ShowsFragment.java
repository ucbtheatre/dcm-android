package com.ucb.dcm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;
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

        ArrayList<Show> shows = DataService.getSharedService().getShows(null);
        mAdpt = new ShowsListAdapter(shows, getActivity().getLayoutInflater());
        setListAdapter(mAdpt);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_shows, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent displayShow = new Intent(getActivity(), ViewShowFragment.class);
        Show show = (Show) getListAdapter().getItem(position);
        displayShow.putExtra(ViewShowFragment.SHOW_KEY, show);
        startActivity(displayShow);
    }
}
