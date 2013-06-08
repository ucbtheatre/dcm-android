package com.ucb.dcm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<Show> shows = DataService.getSharedService().getShows(null);
        mAdpt = new ShowsListAdapter(shows, getActivity().getLayoutInflater());
        setListAdapter(mAdpt);
        return inflater.inflate(R.layout.tab_shows, container, false);
    }

    ShowsListAdapter mAdpt;

}
